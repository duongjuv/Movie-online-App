package com.example.movieonlineapp.ui.detail.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.CommentRegisterResponse;
import com.example.movieonlineapp.data.response.CreateFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.response.TransactionResponseRegister;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.domain.model.User;
import com.example.movieonlineapp.ui.detail.adapter.CommentAdapter;
import com.example.movieonlineapp.ui.detail.viewmodel.MovieItemViewModel;
import com.example.movieonlineapp.ui.home.adapter.CategoryEachMovieListAdapter;
import com.example.movieonlineapp.ui.home.decoration.HorizontalSpacingItemDecoration;
import com.example.movieonlineapp.ui.personal.user.UserItemViewModel;
import com.example.movieonlineapp.utils.Utils;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class DetailMovieActivity extends AppCompatActivity {

    private ImageView imgPoster;
    private Button btnStartMovie, btnCommentMovie;
    private RecyclerView recyclerViewCategory, recyclerViewComments;
    private TextView txtActors, ratingStartMovie, timeMovie, txtMovieName, txtSummaryMovie;
    private ImageView imgBack, imgFavouriteMovie;
    private UserItemViewModel viewmodel;
    private MovieItemViewModel model;
    public static boolean isVip = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewmodel = UserItemViewModel.getInstance();
        viewmodel.getUserData().observe(this, user->{
        });
        initView();
        setupViewModel();
    }

    private void setupViewModel() {

        SelectedDataSource selectedDataSource = Utils.checkInternetState(getApplicationContext());
        ExecutorService executorService =
                ((MyApplication) getApplication()).detailMovieExecutorService;
        RemoteDataSource<MovieApiResponse> remoteDataSource =
                new RemoteDataSource<>(MovieApiResponse.class, executorService);
        LocalDataSource localDataSource = new LocalDataSource();
        Repository repository = new Repository.Builder()
                .setSelectedDataSource(selectedDataSource)
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        model = MovieItemViewModel.getInstance(repository);
        model.getMovieSelected().observe(this, this::bindData);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void bindData(MovieApiResponse.MovieData movie) {
        if (!isVip) {
            Toast.makeText(this, "Bạn không có quyền xem phim này, " +
                    "vui lòng nâng cấp tài khoản VIP", Toast.LENGTH_SHORT).show();
        }
        ratingStartMovie.setText(movie.getAverageRating());
        timeMovie.setText(movie.getDuration() + "");
        Glide.with(imgPoster.getContext())
                .load(movie.getUrlImage())
                .into(imgPoster);
        if (movie.getActors() != null) {
            StringBuilder actors = new StringBuilder();
            for (MovieApiResponse.Actor actor : movie.getActors()) {
                actors.append(actor.getName()).append(", ");
            }

            if (actors.length() > 0) { // Xóa dấu phẩy và khoảng trắng ở cuối
                actors.setLength(actors.length() - 2);
            }
            txtActors.setText(actors.toString());
        }
        txtMovieName.setText(movie.getTitle());
        txtSummaryMovie.setText(movie.getContent());
        if (movie.getCategories() != null) {
            List<MovieApiResponse.Category> categories = movie.getCategories();
            Log.d("MovieApiResponse.Category", categories.toString());
            List<String> categoryNames = new ArrayList<>();
            for (MovieApiResponse.Category category : categories) {
                categoryNames.add(category.getName());
            }
            CategoryEachMovieListAdapter mAdapterCategory
                    = new CategoryEachMovieListAdapter(categoryNames);

            recyclerViewCategory.setAdapter(mAdapterCategory);

        }

        if (movie.getComments() != null ) {
            List<MovieApiResponse.Comment> commentList = movie.getComments();
            Log.d("MoveDetail", commentList.toString());
            CommentAdapter commentAdapter = new CommentAdapter(this, commentList);

            recyclerViewComments.setLayoutManager
                    (new LinearLayoutManager(this,
                            LinearLayoutManager.VERTICAL, false));
            int spaceInDp = 5;
            int spaceInPx = (int) (spaceInDp * getResources().getDisplayMetrics().density);
            recyclerViewComments.addItemDecoration(new HorizontalSpacingItemDecoration(spaceInPx));
            recyclerViewComments.setAdapter(commentAdapter);
            recyclerViewComments.setHasFixedSize(true);

        } else {
            //Toast.makeText(this, "No comments available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        setupView();
        setLayoutRecycler();
        setOnClick();
    }

    private void setLayoutRecycler() {
        recyclerViewCategory.setLayoutManager
                (new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL, false));
    }

    @SuppressWarnings("unchecked")
    private void createMovieFavourite(CreateFavouriteCallback callback) {
        ExecutorService executorService =
                ((MyApplication) getApplicationContext()).detailMovieExecutorService;
        LocalDataSource localDataSource = new LocalDataSource();
        RemoteDataSource<CreateFavouriteMovieResponse> remoteDataSource =
                new RemoteDataSource<>(CreateFavouriteMovieResponse.class, executorService);
        SelectedDataSource selectedDataSource = Utils.checkInternetState(this);
        Repository repository = new Repository.Builder()
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        DataSource.DataSourceCallback resultCallback = result -> {
            if (result instanceof ResponseResult.Success) {

                CreateFavouriteMovieResponse createFavouriteMovieResponse =
                        ((ResponseResult.Success<CreateFavouriteMovieResponse>) result).data;

                if (createFavouriteMovieResponse.getMessage().equals("Create favorite successfully")) {
                    callback.onCreateSuccess();
                } else {

                    callback.onCreateFailure("Khởi tạo yêu thích thất bại");
                }
            } else {

                callback.onCreateFailure("Lỗi khi khởi tạo");
            }
        };

        repository.loadData(resultCallback);
    }

    private void startVideo() {
        Intent intent = new Intent(this, StartMovieActivity.class);
        startActivity(intent);
    }

    public interface CreateFavouriteCallback {
        void onCreateSuccess();
        void onCreateFailure(String msg);
    }

    private void setupView() {
        ScrollView scrollView = findViewById(R.id.scrollView);
        ratingStartMovie = findViewById(R.id.txtRatingComment);
        timeMovie = findViewById(R.id.txtTimeMovieDetail);
        imgPoster = findViewById(R.id.imagePosterMovie);
        txtActors = findViewById(R.id.txtActorDetail);
        txtMovieName = findViewById(R.id.txtMovieNameDetail);
        txtSummaryMovie = findViewById(R.id.txtSummery);
        imgBack = findViewById(R.id.imageBackNavigation);
        imgFavouriteMovie = findViewById(R.id.imgFavouriteMovie);
        recyclerViewCategory = findViewById(R.id.recyclerViewListCategory);
        recyclerViewComments = findViewById(R.id.recyclerComment);
        scrollView.setVisibility(View.VISIBLE);
        btnStartMovie = findViewById(R.id.btnStartMovie);
        btnCommentMovie = findViewById(R.id.btnCommentMovie);
    }

    private void setOnClick() {
        btnStartMovie.setOnClickListener(v -> startVideo());
        btnCommentMovie.setOnClickListener(v->commentMovie(Objects.requireNonNull(viewmodel.getUserData().getValue())));
        imgBack.setOnClickListener(v -> finish());
        imgFavouriteMovie.setOnClickListener(v -> createMovieFavourite(new CreateFavouriteCallback() {
            @Override
            public void onCreateSuccess() {
                Toast.makeText(getApplicationContext(),
                        "Yêu thích thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCreateFailure(String msg) {
                Toast.makeText(getApplicationContext(),
                        "Yêu thích thất bại!", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @SuppressLint("SetTextI18n")
    private void commentMovie(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflate layout cho dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);
        builder.setView(dialogView);

        // Khai báo các thành phần trong dialog
        ImageView imageViewAvatar = dialogView.findViewById(R.id.imgAvatarComment);
        TextView textViewEmail = dialogView.findViewById(R.id.textViewEmail);
        EditText editTextRating = dialogView.findViewById(R.id.edtRating);
        EditText editTextComment = dialogView.findViewById(R.id.edtComment);
        Button buttonSubmit = dialogView.findViewById(R.id.buttonSubmit);

        // Hiển thị email của người dùng
        textViewEmail.setText("Email: " + user.getEmail());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Thiết lập hành động cho nút "Gửi"
        buttonSubmit.setOnClickListener(v -> {
            String rating = editTextRating.getText().toString();
            String comment = editTextComment.getText().toString();
            // Xử lý việc gửi đánh giá và bình luận tại đây
            DataLocalManager.getInstance()
                    .mySharedPreferences.putValue(DataLocalManager.KEY_RATING, rating);
            DataLocalManager.getInstance()
                    .mySharedPreferences.putValue(DataLocalManager.KEY_COMMENT, comment);
            startComment(new CommentCallback() {
                @Override
                public void onCreateSuccess() {
                    Toast.makeText(DetailMovieActivity.this,
                            "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCreateFailure(String msg) {
                    Toast.makeText(DetailMovieActivity.this,
                            msg, Toast.LENGTH_SHORT).show();
                }
            });
            // Đóng dialog sau khi gửi
            dialog.dismiss();
        });

    }

    @SuppressWarnings("unchecked")
    public void startComment(CommentCallback callback){
        ExecutorService executorService =
                ((MyApplication) getApplicationContext()).detailMovieExecutorService;
        LocalDataSource localDataSource = new LocalDataSource();
        RemoteDataSource<CommentRegisterResponse> remoteDataSource =
                new RemoteDataSource<>(CommentRegisterResponse.class, executorService);
        SelectedDataSource selectedDataSource = Utils.checkInternetState(this);
        Repository repository = new Repository.Builder()
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        DataSource.DataSourceCallback resultCallback = result -> {
            if (result instanceof ResponseResult.Success) {

                CommentRegisterResponse commentRegisterResponse =
                        ((ResponseResult.Success<CommentRegisterResponse>) result).data;

                if (commentRegisterResponse.getMessage().equals("Create comment successfully")) {
                    callback.onCreateSuccess();
                } else {
                    callback.onCreateFailure("Đánh giá thất bại");
                }
            } else {

                callback.onCreateFailure("Lỗi khi đánh giá");
            }
        };

        repository.loadData(resultCallback);
    }
    public interface CommentCallback{
        void onCreateSuccess();
        void onCreateFailure(String msg);
    }
}