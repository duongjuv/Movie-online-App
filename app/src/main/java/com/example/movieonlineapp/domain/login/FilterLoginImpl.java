package com.example.movieonlineapp.domain.login;

import android.content.Context;

import android.util.Log;
import android.widget.Toast;

import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.response.UserApiResponse;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.domain.model.User;
import com.example.movieonlineapp.ui.personal.user.UserItemViewModel;

import java.util.List;

public class FilterLoginImpl implements FilterLogin {
    private final Context context;

    public FilterLoginImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isFieldEmpty(String data) {
        return data.trim().isEmpty(); //kiểm tra một string có rỗng không
    }

    @SuppressWarnings("unchecked")
    @Override
    public void login(String userName, String passWord, LoginCallback callback) {
        // Kiểm tra DataLocalManager và MySharedPreferences đã được khởi tạo
        if (DataLocalManager.getInstance() == null ||
                DataLocalManager.getInstance().mySharedPreferences == null) {
            // Khởi tạo nếu chưa có
            DataLocalManager.init(context);
        }
        DataSource.DataSourceCallback resultCallback = result -> {
            if (result instanceof ResponseResult.Success) {
                UserApiResponse userApiResponse = ((ResponseResult.Success<UserApiResponse>) result).data;
                if (userApiResponse != null && userApiResponse.getMessage()
                        .equals(context.getString(R.string.txt_LoginSuccess))) {
                    // Chỉ in log khi thành công

                    // Xử lý khi đăng nhập thành công
                    callback.onLoginSuccess();
                    String emailToken = userApiResponse.getData().getUser().getEmail() + "_token";
                    Log.d("TOKEN: ", emailToken);

                    // Lưu giữ giá trị emailToken và token
                    DataLocalManager.getInstance().mySharedPreferences.putValue(DataLocalManager.KEY_EMAIL, emailToken);
                    String valueToken = userApiResponse.getData().getToken();
                    DataLocalManager.getInstance().mySharedPreferences.putValue(emailToken, valueToken);

                    // Lưu dữ liệu người dùng vào ViewModel
                    UserItemViewModel model = UserItemViewModel.getInstance();
                    model.setUserData(userApiResponse.getData().getUser());

                    // Lưu giữ userId
                    DataLocalManager.getInstance().mySharedPreferences.putValue(DataLocalManager.ID_USER,
                            String.valueOf(userApiResponse.getData().getUser().getId()));

                } else {
                    Log.d("API_RESPONSE", "Login failed - Incorrect credentials");
                    String message = context.getString(R.string.txt_errorUserAndPassIncorrect);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    callback.onLoginFailure(message);
                }
            } else {
                // Chỉ in log khi phản hồi thất bại
                Log.d("API_RESPONSE", "Failure response received");
                callback.onLoginFailure(context.getString(R.string.txt_LoginFail));
            }
        };
        // Gọi API để đăng nhập
        RemoteDataSource.login(resultCallback, userName, passWord);
    }


    // Interface Callback để xử lý kết quả đăng nhập
    public interface LoginCallback {
        void onLoginSuccess();

        void onLoginFailure(String msg);
    }
}
