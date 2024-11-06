package com.example.movieonlineapp.data.source.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.data.request.CommentRequest;
import com.example.movieonlineapp.data.request.FavouriteRequest;
import com.example.movieonlineapp.data.request.TransactionRequest;
import com.example.movieonlineapp.data.request.WatchHistoryRequest;
import com.example.movieonlineapp.data.response.CommentRegisterResponse;
import com.example.movieonlineapp.data.response.CreateFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.ListFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.ListMovieApiResponse;
import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.response.TransactionResponseRegister;
import com.example.movieonlineapp.data.response.UpdateRoleVipResponse;
import com.example.movieonlineapp.data.response.UserApiResponse;
import com.example.movieonlineapp.data.response.WatchHistoryApiResponse;
import com.example.movieonlineapp.data.response.WatchHistoryResponseRegister;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;
import com.example.movieonlineapp.data.request.LoginRequest;
import com.example.movieonlineapp.ui.detail.activity.DetailMovieActivity;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteDataSource<T> implements DataSource<T> {
    private static ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private Class<T> type;
    private final ApiService service;

    public RemoteDataSource(Class<T> type, ExecutorService executorService) {
        this.type = type;
        mExecutorService = executorService;
        this.service = RetrofitClient.getApiService();
    }

    public RemoteDataSource(ExecutorService executorService) {
        mExecutorService = executorService;
        this.service = RetrofitClient.getApiService();
    }

    @Override
    public void loadData(DataSourceCallback<T> callback) {
        int userId = Integer.parseInt(DataLocalManager.getInstance()
                .mySharedPreferences.getValue(DataLocalManager.ID_USER));

        if (type == UserApiResponse.class) {
            loadAllUserData(callback);
        } else if (type == ListMovieApiResponse.class) {
            loadAllMovieData(callback);
        } else if (type == MovieApiResponse.class) {
            int movieId = Integer.parseInt(DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.ID_MOVIE));
            loadMovieSelectedData(callback, movieId);
        } else if (type == CreateFavouriteMovieResponse.class) {
            createFavouriteMovie(callback);
        } else if (type == ListFavouriteMovieResponse.class) {
            loadAllFavoriteMovieData(callback, userId);
        } else if (type == WatchHistoryResponseRegister.class) {
            int movieId = Integer.parseInt(DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.ID_MOVIE));
            WatchHistoryRequest watchHistoryRequest = new WatchHistoryRequest(userId, movieId);
            createWatchHistory(callback, watchHistoryRequest);
        } else if (type == WatchHistoryApiResponse.class) {
            getAllWatchHistory(callback, userId);
        } else if (type == TransactionResponseRegister.class) {
            int amount = Integer.parseInt(DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.KEY_DEPOSIT));
            String content = DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.KEY_CONTENT);

            TransactionRequest transactionRequest = new TransactionRequest(userId, amount, content);
            createTransaction(callback, transactionRequest);
        }
        else if(type == CommentRegisterResponse.class){
            int movieId = Integer.parseInt(DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.ID_MOVIE));
            int rating = Integer.parseInt(DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.KEY_RATING));
            String content = DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.KEY_COMMENT);
            CommentRequest commentRequest = new CommentRequest(userId, movieId, rating, content);
            createComment(callback, commentRequest);
        }
        else if(type == UpdateRoleVipResponse.class){
            updateVip(callback, userId);
        }


    }

    private void createComment(DataSourceCallback<T> callback, CommentRequest commentRequest) {
        mExecutorService.execute(() -> {
            Call<CommentRegisterResponse> commentRegisterResponseCall
                    = service.createComment(commentRequest);
            commentRegisterResponseCall.enqueue(new Callback<CommentRegisterResponse>() {
                @Override
                public void onResponse(@NonNull Call<CommentRegisterResponse> call,
                                       @NonNull Response<CommentRegisterResponse> response) {
                    if (response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommentRegisterResponse> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(
                            new Exception(throwable.getMessage())));
                }
            });
        });
    }

    private void updateVip(DataSourceCallback<T> callback, int userId) {
        mExecutorService.execute(() -> {
            Call<UpdateRoleVipResponse> updateRoleVipResponseCall
                    = service.updateVip(userId);

            updateRoleVipResponseCall.enqueue(new Callback<UpdateRoleVipResponse>() {
                @Override
                public void onResponse(@NonNull Call<UpdateRoleVipResponse> call,
                                       @NonNull Response<UpdateRoleVipResponse> response) {
                    if (response.isSuccessful()) {
                        // Lấy dữ liệu từ phản hồi
                        UpdateRoleVipResponse body = response.body();

                        // Kiểm tra trường "error" trong phản hồi
                        if (body != null) {
                            Log.d("Update VIP", body.getMessage()); // Ghi log thông báo từ phản hồi
                            if (!body.getError()) {
                                callback.onCompleted(new ResponseResult.Success<>(body));
                            } else {
                                callback.onCompleted(new ResponseResult.Error<>(new Exception(body.getMessage())));
                            }
                        } else {
                            callback.onCompleted(new ResponseResult.Error<>(new Exception("Response body is null")));
                        }
                    } else {
                        callback.onCompleted(new ResponseResult.Error<>(new Exception("Response not successful: " + response.message())));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UpdateRoleVipResponse> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(
                            new Exception(throwable.getMessage())));
                }
            });
        });
    }

    private void createTransaction(DataSourceCallback<T> callback, TransactionRequest transactionRequest) {
        mExecutorService.execute(() -> {
            Call<TransactionResponseRegister> transaction
                    = service.createTransaction(transactionRequest);
            transaction.enqueue(new Callback<TransactionResponseRegister>() {
                @Override
                public void onResponse(@NonNull Call<TransactionResponseRegister> call,
                                       @NonNull Response<TransactionResponseRegister> response) {
                    if (response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TransactionResponseRegister> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(
                            new Exception(throwable.getMessage())));
                }
            });
        });
    }

    private void getAllWatchHistory(DataSourceCallback<T> callback, int userId) {
        mExecutorService.execute(() -> {
            Call<WatchHistoryApiResponse> watchHistoryApiResponseCall
                    = service.getWatchHistoryData(userId);
            watchHistoryApiResponseCall.enqueue(new Callback<WatchHistoryApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<WatchHistoryApiResponse> call,
                                       @NonNull Response<WatchHistoryApiResponse> response) {
                    if (response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WatchHistoryApiResponse> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(new Exception(throwable.getMessage())));
                }
            });
        });
    }

    private void createWatchHistory(DataSourceCallback<T> callback,
                                    WatchHistoryRequest watchHistoryRequest) {
        mExecutorService.execute(() -> {
            Call<WatchHistoryResponseRegister> watchHistoryResponseRegisterCall
                    = service.createWatchHistory(watchHistoryRequest);
            watchHistoryResponseRegisterCall.enqueue(new Callback<WatchHistoryResponseRegister>() {
                @Override
                public void onResponse(@NonNull Call<WatchHistoryResponseRegister> call,
                                       @NonNull Response<WatchHistoryResponseRegister> response) {
                    if (response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WatchHistoryResponseRegister> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(
                            new Exception(throwable.getMessage())));
                }
            });
        });
    }

    private void loadAllFavoriteMovieData(DataSourceCallback<T> callback, int userId) {
        mExecutorService.execute(() -> {
            Call<ListFavouriteMovieResponse> listFavouriteMovieResponseCall
                    = service.getAllFavouriteMovieData(userId);
            listFavouriteMovieResponseCall.enqueue(new Callback<ListFavouriteMovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<ListFavouriteMovieResponse> call,
                                       @NonNull Response<ListFavouriteMovieResponse> response) {
                    if (response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ListFavouriteMovieResponse> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(new Exception(throwable.getMessage())));
                }
            });
        });
    }

    private void createFavouriteMovie(DataSourceCallback<T> callback) {
        mExecutorService.execute(() -> {
            int idUser = Integer.parseInt(DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.ID_USER));
            int idMovie = Integer.parseInt(DataLocalManager.getInstance()
                    .mySharedPreferences.getValue(DataLocalManager.ID_MOVIE));
            FavouriteRequest favouriteRequest = new FavouriteRequest(idUser, idMovie);
            Call<CreateFavouriteMovieResponse>
                    createFavouriteMovieResponseCall = service.createFavouriteMovie(favouriteRequest);
            createFavouriteMovieResponseCall.enqueue(new Callback<CreateFavouriteMovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<CreateFavouriteMovieResponse> call,
                                       @NonNull Response<CreateFavouriteMovieResponse> response) {
                    if (response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CreateFavouriteMovieResponse> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(new Exception(throwable.getMessage())));
                }
            });
        });
    }

    private void loadMovieSelectedData(DataSourceCallback<T> callback, int id) {
        mExecutorService.execute(() -> {
            DetailMovieActivity.isVip = true;
            Call<MovieApiResponse> movieApiResponseCall = service.getMovieSelectedData(id);
            movieApiResponseCall.enqueue(new Callback<MovieApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieApiResponse> call,
                                       @NonNull Response<MovieApiResponse> response) {
                    if (response.body() != null) {
                        DetailMovieActivity.isVip = true;
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    } else {
                        try {
                            if (response.errorBody() != null) {
                                String errorResponse = response.errorBody().string();
                                DetailMovieActivity.isVip = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        callback.onCompleted(new ResponseResult.Error<>(new Exception(response.message())));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieApiResponse> call,
                                      @NonNull Throwable throwable) {
                    DetailMovieActivity.isVip = true;
                    Log.d("Movie Selected", "Error " + throwable.getMessage());
                    callback.onCompleted(new ResponseResult.Error<>(new Exception(throwable.getMessage())));
                }
            });
        });
    }

    @SuppressWarnings("unchecked")
    public void loadAllUserData(DataSourceCallback callback) {
        mExecutorService.execute(() -> {
            Call<UserApiResponse> userApiResponseCall = service.getAllUserData();
            userApiResponseCall.enqueue(new Callback<UserApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserApiResponse> call,
                                       @NonNull Response<UserApiResponse> response) {
                    if (response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserApiResponse> call,
                                      @NonNull Throwable throwable) {
                    callback.onCompleted(new ResponseResult.Error<>(new Exception(throwable.getMessage())));
                }
            });
        });
    }

    @SuppressWarnings("unchecked")
    public void loadAllMovieData(DataSourceCallback callback) {
        mExecutorService.execute(() -> {
            Call<ListMovieApiResponse> movieApiResponseCall =
                    service.getAllMovieData();
            movieApiResponseCall.enqueue(new Callback<ListMovieApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ListMovieApiResponse> call,
                                       @NonNull Response<ListMovieApiResponse> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        Log.d("API Response", "Movies: " + response.body().getMovies().size());
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    } else {
                        Log.e("API Response", "Failed to get movies: " + response.message());
                        callback.onCompleted(new ResponseResult.Error<>(new Exception(response.message())));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ListMovieApiResponse> call,
                                      @NonNull Throwable throwable) {
                    Log.e("API Response", "Error: " + throwable.getMessage());
                    callback.onCompleted(new ResponseResult.Error<>(new Exception(throwable.getMessage())));
                }
            });
        });
    }


    @SuppressWarnings("unchecked")
    public static void login(DataSourceCallback callback, String userName, String passWord) {
        mExecutorService.execute(() -> {
            LoginRequest loginRequest = new LoginRequest(userName, passWord);
            ApiService service = RetrofitClient.getApiService();
            Call<UserApiResponse> userApiResponseCall = service.loginPost(loginRequest);

            userApiResponseCall.enqueue(new Callback<UserApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserApiResponse> call,
                                       @NonNull Response<UserApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onCompleted(new ResponseResult.Success<>(response.body()));
                    } else {
                        Log.e("Login", "API call failed: NULL or not successful. Response code: " + response.code());
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("Login", "Error response: " + errorResponse);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onCompleted(new ResponseResult.Error<>(new Exception(response.message())));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserApiResponse> call,
                                      @NonNull Throwable throwable) {
//                    Log.e("Login", "API call failed: " + throwable.getMessage());
//                    callback.onCompleted(new ResponseResult.Error<>(
//                            new Exception(throwable.getMessage())));
                    Log.e("Login", "API call failed: " + throwable.getMessage());
                    callback.onCompleted(new ResponseResult.Error<>(new Exception("API call failed: " + throwable.getMessage())));
                }
            });
        });
    }

}
