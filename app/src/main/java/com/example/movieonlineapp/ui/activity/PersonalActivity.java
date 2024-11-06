package com.example.movieonlineapp.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.TransactionResponseRegister;
import com.example.movieonlineapp.data.response.UpdateRoleVipResponse;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.domain.model.User;
import com.example.movieonlineapp.ui.personal.user.UserItemViewModel;
import com.example.movieonlineapp.utils.Utils;

import java.util.concurrent.ExecutorService;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayoutPayment, linearLayoutUpdateVip, linearLayoutNotification;
    private User mUser = null;
    private TextView txtAmount, txtTypeAccount, txtPhone, txtPass, txtEmail, txtFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setupViews();
        UserItemViewModel model = UserItemViewModel.getInstance();
        model.getUserData().observe(this, user -> {
            updateUserData(user);
            mUser = user;
        });
        setOnClick();

    }

    @SuppressLint("SetTextI18n")
    private void updateUserData(User user) {
        txtAmount.setText(user.getCore());
        txtEmail.setText(user.getEmail());
        if(user.getRoleUser().getRoleId() == 3){
            txtTypeAccount.setText(R.string.txt_tkOfften);
        }
        else{
            txtTypeAccount.setText(R.string.txt_tkVip);
        }
        txtFullName.setText(user.getFullName());
        txtPhone.setText(user.getPhone());
    }

    private void setupViews() {
        linearLayoutNotification = findViewById(R.id.layoutNotification);
        linearLayoutPayment = findViewById(R.id.layoutPayment);
        linearLayoutUpdateVip= findViewById(R.id.layoutUpdateVip);
        txtAmount = findViewById(R.id.txtAmount);
        txtEmail = findViewById(R.id.txtEmailPersonal);
        txtTypeAccount = findViewById(R.id.txtTypeAccount);
        txtPhone = findViewById(R.id.txtPhoneNumber);
        txtPass = findViewById(R.id.txtChangePassWord);
        txtFullName = findViewById(R.id.txtFullNamePersonal);

    }

    private void setOnClick() {
        txtPass.setOnClickListener(this);
        linearLayoutPayment.setOnClickListener(this) ;
        linearLayoutNotification.setOnClickListener(this);
        linearLayoutUpdateVip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.layoutNotification){
            notification();
        }
        else if(v.getId() == R.id.layoutPayment){
            // Tạo AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tạo số tiền nạp");

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_payment, null);
            builder.setView(dialogView);
            EditText editTextTransferContent = dialogView.findViewById(R.id.editTextTransferContent);
            EditText editTextDepositAmount = dialogView.findViewById(R.id.editTextDepositAmount);
            //ImageView imageViewQRCode = dialogView.findViewById(R.id.imageViewQRCode);
            //TextView textViewAccountInfo = dialogView.findViewById(R.id.textViewAccountInfo);
            // Thiết lập các nút cho dialog
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng nhấn xác nhận
                    String transferContent = editTextTransferContent.getText().toString();
                    String transferDeposit = editTextDepositAmount.getText().toString();
                    DataLocalManager.getInstance().mySharedPreferences.putValue(DataLocalManager.KEY_CONTENT, transferContent);
                    DataLocalManager.getInstance().mySharedPreferences.putValue(DataLocalManager.KEY_DEPOSIT, transferDeposit);
                    createPayment(new CreateTransactionCallback() {
                        @Override
                        public void onCreateSuccess() {
                            Toast.makeText(PersonalActivity.this, "Yêu cầu nạp tiền thành công!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCreateFailure(String msg) {
                            Toast.makeText(PersonalActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            // Hiện dialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        else if(v.getId() == R.id.layoutUpdateVip){
            showUpgradeVipDialog();
        }
        else if(v.getId() == R.id.txtChangePassWord){

        }
    }

    private void showUpgradeVipDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update VIP");
        builder.setMessage("Bạn có muốn nâng cấp lên VIP?");
        builder.setPositiveButton("Có", (dialog, which) -> updateVip(new UpdateVipCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCreateSuccess() {
                Toast.makeText(
                        PersonalActivity.this, "Bạn đã trở thành VIP", Toast.LENGTH_SHORT).show();
                String amountStr = String.valueOf(Integer.parseInt(mUser.getCore()) - 200000);
                txtAmount.setText(amountStr);
                txtTypeAccount.setText("Tài khoản Vip");
            }

            @Override
            public void onCreateFailure(String msg) {
                Toast.makeText(PersonalActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
        }));

        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressWarnings("unchecked")
    private void updateVip(UpdateVipCallBack callback) {
        ExecutorService executorService =
                ((MyApplication) getApplicationContext()).detailMovieExecutorService;
        LocalDataSource localDataSource = new LocalDataSource();
        RemoteDataSource<UpdateRoleVipResponse> remoteDataSource =
                new RemoteDataSource<>(UpdateRoleVipResponse.class, executorService);
        SelectedDataSource selectedDataSource = Utils.checkInternetState(this);
        Repository repository = new Repository.Builder()
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        DataSource.DataSourceCallback resultCallback = result -> {
            if (result instanceof ResponseResult.Success) {
                UpdateRoleVipResponse updateRoleVipResponse =
                        ((ResponseResult.Success<UpdateRoleVipResponse>) result).data;

                if (updateRoleVipResponse.getMessage().equals("Update role VIP successfully")) {
                    callback.onCreateSuccess();
                } else if(updateRoleVipResponse.getMessage().equals("Not enough core to upgrade to VIP")){
                    callback.onCreateFailure("Không đủ tiền vui lòng nạp thêm");
                }
            } else {
                callback.onCreateFailure("Lỗi khi update vip");
            }
        };

        repository.loadData(resultCallback);
    }

    @SuppressWarnings("unchecked")
    private void createPayment(CreateTransactionCallback callback) {
        ExecutorService executorService =
                ((MyApplication) getApplicationContext()).detailMovieExecutorService;
        LocalDataSource localDataSource = new LocalDataSource();
        RemoteDataSource<TransactionResponseRegister> remoteDataSource =
                new RemoteDataSource<>(TransactionResponseRegister.class, executorService);
        SelectedDataSource selectedDataSource = Utils.checkInternetState(this);
        Repository repository = new Repository.Builder()
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        DataSource.DataSourceCallback resultCallback = result -> {
            if (result instanceof ResponseResult.Success) {

                TransactionResponseRegister transactionResponseRegister =
                        ((ResponseResult.Success<TransactionResponseRegister>) result).data;

                if (transactionResponseRegister.getMessage().equals("Transaction created successfully")) {
                    callback.onCreateSuccess();
                } else {

                    callback.onCreateFailure("Khởi tạo yêu cầu thất bại");
                }
            } else {

                callback.onCreateFailure("Lỗi khi khởi tạo");
            }
        };

        repository.loadData(resultCallback);
    }

    private void notification() {
    }
    public interface CreateTransactionCallback {
        void onCreateSuccess();
        void onCreateFailure(String msg);
    }

    public interface UpdateVipCallBack{
        void onCreateSuccess();
        void onCreateFailure(String msg);
    }
}