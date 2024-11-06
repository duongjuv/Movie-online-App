package com.example.movieonlineapp.data.source.remote;

import com.example.movieonlineapp.data.request.CommentRequest;
import com.example.movieonlineapp.data.request.FavouriteRequest;
import com.example.movieonlineapp.data.request.TransactionRequest;
import com.example.movieonlineapp.data.request.WatchHistoryRequest;
import com.example.movieonlineapp.data.response.CategoryApiResponse;
import com.example.movieonlineapp.data.response.CommentRegisterResponse;
import com.example.movieonlineapp.data.response.CreateFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.ListFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.ListMovieApiResponse;
import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.response.TransactionResponseRegister;
import com.example.movieonlineapp.data.response.UpdateRoleVipResponse;
import com.example.movieonlineapp.data.response.UserApiResponse;
import com.example.movieonlineapp.data.request.LoginRequest;
import com.example.movieonlineapp.data.response.WatchHistoryApiResponse;
import com.example.movieonlineapp.data.response.WatchHistoryResponseRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService{

    // lấy danh sách user
    @GET("api/users/get-all")
    Call<UserApiResponse> getAllUserData();

    // lấy danh sách all movie
    @GET("api/movies/get-all")
    Call<ListMovieApiResponse> getAllMovieData();


    // lấy danh sách all category
    @GET("api/categories/get-all")
    Call<CategoryApiResponse> getAllCategoryData();

    // lấy danh sách phim yêu thích theo id user
    @GET("api/site/favorites/{id}")
    Call<ListFavouriteMovieResponse> getAllFavouriteMovieData(@Path("id") int userId);

    // tạo phim yêu thích
    @POST("api/site/favorites/create")
    Call<CreateFavouriteMovieResponse> createFavouriteMovie(@Body FavouriteRequest favouriteRequest);

    // lấy dữ liệu phim theo id (comments)
    @GET("api/movies/get-one/{id}")
    Call<MovieApiResponse> getMovieSelectedData(@Path("id") int movieId);

    // lấy lịch sử xem phim
    @GET("api/site/watch-history/{id}")
    Call<WatchHistoryApiResponse> getWatchHistoryData(@Path("id") int userId);

    // đăng ký lịch sử xem phim
    @POST("api/site/watch-history/create")
    Call<WatchHistoryResponseRegister> createWatchHistory(@Body WatchHistoryRequest watchHistoryRequest);

    // tạo comment cho phim
    @POST("api/site/comments/create")
    Call<CommentRegisterResponse> createComment(@Body CommentRequest commentRequest);

    // đăng ký nạp tiền
    @POST("api/transactions/create")
    Call<TransactionResponseRegister> createTransaction(@Body TransactionRequest transactionRequest);

    // update vip cho user thường
    @PUT("api/users/update-role-vip/{id}")
    Call<UpdateRoleVipResponse> updateVip(@Path("id") int userId);
    // Login
    @POST("api/users/login")
    Call<UserApiResponse> loginPost(@Body LoginRequest loginRequest);
}
