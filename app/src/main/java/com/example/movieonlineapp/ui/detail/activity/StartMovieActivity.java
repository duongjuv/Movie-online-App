package com.example.movieonlineapp.ui.detail.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;

import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;

import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.CreateFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.WatchHistoryResponseRegister;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.ui.detail.activity.supportMedia.TrackSelectionDialog;
import com.example.movieonlineapp.ui.detail.viewmodel.MovieSelectedViewModel;
import com.example.movieonlineapp.utils.Utils;

import java.util.Objects;
import java.util.concurrent.ExecutorService;


public class StartMovieActivity extends AppCompatActivity {

    private ImageView btnPlayPause;
    private ImageView btnFullScreen;
    private TextView txtExoTitle;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private boolean isFullScreen = false;
    //private final String[] speed = {"0.5x", "0.75x", "Normal", "1.25x", "1.5x"};
    int RESIZE_MODE = 0;
    private boolean isShowingTrackSelectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MovieSelectedViewModel model = MovieSelectedViewModel.getInstance();
        String video_url = Objects.requireNonNull(model.getMovieData().getValue()).getUrlVideo();

        createHistoryWatch(new CreateWatchHistoryCallBack() {
            @Override
            public void onCreateSuccess(String msg) {
                //Log.d("Register History: ", msg );
            }

            @Override
            public void onCreateFailure(String msg) {
                //Log.d("Register History: ", msg );
            }
        });
        playerView = findViewById(R.id.PlayerView);
        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(video_url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);

        ImageView btnBackButton = playerView.findViewById(R.id.exo_back);
        ImageView btnForward = playerView.findViewById(R.id.exo_forward);
        ImageView btnBackward = playerView.findViewById(R.id.exo_backward);
        btnPlayPause = playerView.findViewById(R.id.exo_play_pause);
        btnFullScreen = playerView.findViewById(R.id.exo_full);
        txtExoTitle = playerView.findViewById(R.id.exo_title);
        ImageView btnSpeed = playerView.findViewById(R.id.exo_speed);
        ImageView btnResize = playerView.findViewById(R.id.exo_resize);
        ImageView btnSetting = playerView.findViewById(R.id.exo_quality_setting);
        txtExoTitle.setText(model.getMovieData().getValue().getTitle());

        btnPlayPause.setOnClickListener(v -> {
            exoPlayer.setPlayWhenReady(!exoPlayer.getPlayWhenReady());
            btnPlayPause.setImageResource
                    (Boolean.TRUE.equals(exoPlayer.getPlayWhenReady())
                            ? R.drawable.pause : R.drawable.backplay);
        });


        btnResize.setOnClickListener(new View.OnClickListener() {
            @OptIn(markerClass = UnstableApi.class)
            @Override
            public void onClick(View v) {
                if (RESIZE_MODE == 0) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    RESIZE_MODE = 1;
                } else if (RESIZE_MODE == 1) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
                    RESIZE_MODE = 2;
                } else if (RESIZE_MODE == 2) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    RESIZE_MODE = 3;
                } else if (RESIZE_MODE == 3) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    RESIZE_MODE = 0;
                }
            }
        });

        btnForward.setOnClickListener(
                v -> exoPlayer.seekTo(exoPlayer.getCurrentPosition() + 10000));
        btnBackward.setOnClickListener(v -> {
            long num = exoPlayer.getCurrentPosition() - 10000;
            if (num < 0) {
                exoPlayer.seekTo(0);
            } else {
                exoPlayer.seekTo(exoPlayer.getCurrentPosition() - 10000);
            }
        });

        btnBackButton.setOnClickListener(v -> finish());

        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View v) {
                if (isFullScreen) {
                    txtExoTitle.setVisibility(View.INVISIBLE);
                    btnFullScreen.setImageDrawable(ContextCompat.
                            getDrawable(getApplicationContext(), R.drawable.backfullclose));
                    if (getSupportActionBar() != null) {
                        Objects.requireNonNull(getActionBar()).show();
                    }
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ConstraintLayout.LayoutParams params =
                            (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height =
                            (int) (200 * getApplicationContext().getResources()
                                    .getDisplayMetrics().density);
                    playerView.setLayoutParams(params);
                    isFullScreen = false;
                } else {
                    txtExoTitle.setVisibility(View.VISIBLE);
                    btnFullScreen.setImageDrawable(ContextCompat.
                            getDrawable(getApplicationContext(), R.drawable.backfullopen));
                    if (getSupportActionBar() != null) {
                        Objects.requireNonNull(getActionBar()).hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    ConstraintLayout.LayoutParams params =
                            (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    playerView.setLayoutParams(params);
                    isFullScreen = true;
                }
            }
        });
        playerView.setControllerVisibilityListener(
                (PlayerView.ControllerVisibilityListener) visibility -> {
            if (isFullScreen) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                isFullScreen = true;
            }
        });

        btnSpeed.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Thiết lập tốc độ video");

            // Tham chiếu đúng mảng chuỗi
            String[] speedArray = getResources().getStringArray(R.array.video_speeds);

            builder.setItems(speedArray, (dialog, which) -> {
                if (which == 0) {
                    PlaybackParameters parameters = new PlaybackParameters(0.5f);
                    exoPlayer.setPlaybackParameters(parameters);
                }
                if (which == 1) {
                    PlaybackParameters parameters = new PlaybackParameters(0.75f);
                    exoPlayer.setPlaybackParameters(parameters);
                }
                if (which == 2) {
                    PlaybackParameters parameters = new PlaybackParameters(1f);
                    exoPlayer.setPlaybackParameters(parameters);
                }
                if (which == 3) {
                    PlaybackParameters parameters = new PlaybackParameters(1.25f);
                    exoPlayer.setPlaybackParameters(parameters);
                }
                if (which == 4) {
                    PlaybackParameters parameters = new PlaybackParameters(1.5f);
                    exoPlayer.setPlaybackParameters(parameters);
                }
            });
            builder.show();
        });
        btnSetting.setOnClickListener(v -> {
            if (!isShowingTrackSelectionDialog
                    && TrackSelectionDialog.willHaveContent(exoPlayer)) {
                isShowingTrackSelectionDialog = true;
                TrackSelectionDialog trackSelectionDialog =
                        TrackSelectionDialog.createForPlayer(
                                exoPlayer,
                                /* onDismissListener= */
                                dismissedDialog -> isShowingTrackSelectionDialog = false);
                trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void createHistoryWatch(CreateWatchHistoryCallBack callback) {
        ExecutorService executorService =
                ((MyApplication) getApplicationContext()).detailMovieExecutorService;
        LocalDataSource localDataSource = new LocalDataSource();
        RemoteDataSource<WatchHistoryResponseRegister> remoteDataSource =
                new RemoteDataSource<>(WatchHistoryResponseRegister.class, executorService);
        SelectedDataSource selectedDataSource = Utils.checkInternetState(this);
        Repository repository = new Repository.Builder()
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        DataSource.DataSourceCallback resultCallback = result -> {
            if (result instanceof ResponseResult.Success) {

                WatchHistoryResponseRegister watchHistoryResponseRegister =
                        ((ResponseResult.Success<WatchHistoryResponseRegister>) result).data;

                if (watchHistoryResponseRegister.getMessage()
                        .equals(getString(R.string.txt_create_watch_history_successfully))
                && watchHistoryResponseRegister.getMessage()
                        .equals(getString(R.string.txt_update_watch_history_successfully))) {
                    String msg = getString(R.string.txt_khoi_tao_lich_su_thanh_cong);
                    callback.onCreateSuccess(msg);
                } else {
                    callback.onCreateFailure(getString(R.string.txt_failCreateHistory));
                }
            } else {

                callback.onCreateFailure(getString(R.string.txt_cannotCreateHistory));
            }
        };

        repository.loadData(resultCallback);
    }
    public interface CreateWatchHistoryCallBack {
        void onCreateSuccess(String msg);
        void onCreateFailure(String msg);
    }

    @Override
    protected void onResume() {
        exoPlayer.play();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }
}