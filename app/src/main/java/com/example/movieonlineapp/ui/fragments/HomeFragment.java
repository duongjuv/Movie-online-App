package com.example.movieonlineapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.ListMovieApiResponse;
import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.ui.activity.PersonalActivity;
import com.example.movieonlineapp.ui.detail.activity.DetailMovieActivity;
import com.example.movieonlineapp.ui.detail.viewmodel.MovieItemViewModel;
import com.example.movieonlineapp.ui.detail.viewmodel.MovieSelectedViewModel;
import com.example.movieonlineapp.ui.home.adapter.CategoryAdapter;
import com.example.movieonlineapp.ui.home.decoration.ItemOffsetDecoration;
import com.example.movieonlineapp.ui.home.adapter.MovieAdapter;
import com.example.movieonlineapp.ui.home.viewmodel.MovieViewModel;
import com.example.movieonlineapp.ui.home.viewmodel.MovieViewModelFactory;
import com.example.movieonlineapp.ui.main.MainActivity;
import com.example.movieonlineapp.ui.main.slider.SliderAdapters;
import com.example.movieonlineapp.ui.main.slider.SliderItems;
import com.example.movieonlineapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;


public class HomeFragment extends Fragment {

    private View mView;
    private ViewPager2 viewPager2;
    private ImageView userDetail;
    private RecyclerView recyclerViewAllMovies;
    private RecyclerView recyclerViewBestMovie, recyclerCategories;
    private ProgressBar progressBarAllMovie, progressBarRecommendMovie;
    private NestedScrollView scrollView;
    private EditText edtSearchBar;
    private MovieAdapter mAdapterAllMovies;
    private MovieAdapter mAdapterBestMovies;
    private MovieViewModel mViewModel;
    private String[] mCategories;
    private final Handler slideHandler = new Handler(Looper.getMainLooper());
    private Handler uiHandler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo handler gắn với MainLooper
        uiHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(mView);
        banners();
        setupViewModel();
    }

    private void banners() {
        List<SliderItems> sliderItemsList = new ArrayList<>();
        sliderItemsList.add(new SliderItems(R.drawable.avengers));
        sliderItemsList.add(new SliderItems(R.drawable.anh2));
        sliderItemsList.add(new SliderItems(R.drawable.anh3));
        sliderItemsList.add(new SliderItems(R.drawable.anh4));
        sliderItemsList.add(new SliderItems(R.drawable.anh5));
        sliderItemsList.add(new SliderItems(R.drawable.anh7));
        sliderItemsList.add(new SliderItems(R.drawable.anh8));
        sliderItemsList.add(new SliderItems(R.drawable.anh9));

        viewPager2.setAdapter(new SliderAdapters(sliderItemsList, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(8);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    private void initViews(View view) {
        setupViews(view);
        userDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), PersonalActivity.class);
                startActivity(intent);
            }
        });
        edtSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    switchSearchFragment();
                }
            }
        });
        mCategories = getResources().getStringArray(R.array.array_categories_fake);
        CategoryAdapter categoryAdapter = new CategoryAdapter(mCategories);

        LinearLayoutManager layoutManagerCategory =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL, false);
        // Thêm ItemDecoration để tạo khoảng cách
        int spaceInDp = 20;  // 20dp
        int spaceInPx = (int) (spaceInDp * getResources().getDisplayMetrics().density);
        recyclerCategories.addItemDecoration(new ItemOffsetDecoration(spaceInPx));
        recyclerCategories.setLayoutManager(layoutManagerCategory);
        recyclerCategories.setAdapter(categoryAdapter);
        recyclerCategories.setHasFixedSize(true);

        mAdapterAllMovies =
                new MovieAdapter(requireContext(), new ArrayList<>(), this::createDetailMovie);
        mAdapterBestMovies =
                new MovieAdapter(requireContext(), new ArrayList<>(), this::createDetailMovie);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAllMovies.setLayoutManager(layoutManager);
        recyclerViewAllMovies.setAdapter(mAdapterAllMovies);
        recyclerViewAllMovies.setHasFixedSize(true);

        LinearLayoutManager layoutManagerBestMovies =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBestMovie.setLayoutManager(layoutManagerBestMovies);
        recyclerViewBestMovie.setAdapter(mAdapterBestMovies);
        recyclerViewBestMovie.setHasFixedSize(true);

    }

    private void setupViews(View view) {
        edtSearchBar = view.findViewById(R.id.edtSearchMovie);
        progressBarAllMovie = view.findViewById(R.id.progressBarAllMovie);
        viewPager2 = view.findViewById(R.id.viewpagerSlider);
        recyclerViewAllMovies = view.findViewById(R.id.recyclerView_bestMovie);
        recyclerViewBestMovie = view.findViewById(R.id.recyclerBestMovie);
        progressBarRecommendMovie = view.findViewById(R.id.progressBarRecMovie);
        recyclerCategories = view.findViewById(R.id.recyclerViewCategory);
        userDetail = view.findViewById(R.id.userIdMain);
    }

    private void switchSearchFragment() {
        if(MainActivity.currentFragment != MainActivity.FRAGMENT_SEARCH){
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragmentContainerView, new SearchFragment());
            transaction.addToBackStack(null);
            transaction.commit();
            MainActivity.currentFragment = MainActivity.FRAGMENT_SEARCH;
        }
    }

    private void createDetailMovie(com.example.movieonlineapp.domain.model.Movie movie) {
        Intent intent = new Intent(requireContext(), DetailMovieActivity.class);
        DataLocalManager.getInstance().
                mySharedPreferences.putValue(DataLocalManager.ID_MOVIE, String.valueOf(movie.getId()));

        SelectedDataSource selectedDataSource = Utils.checkInternetState(requireContext());
        ExecutorService executorService =
                ((MyApplication) requireActivity().getApplication()).homeFragmentExecutorService;
        RemoteDataSource<MovieApiResponse> remoteDataSource =
                new RemoteDataSource<>(MovieApiResponse.class, executorService);
        LocalDataSource localDataSource = new LocalDataSource();
        Repository repository = new Repository.Builder()
                .setSelectedDataSource(selectedDataSource)
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        MovieItemViewModel model = MovieItemViewModel.getInstance(repository);
        MovieSelectedViewModel movieSelectedViewModel = MovieSelectedViewModel.getInstance();
        movieSelectedViewModel.setMovieData(movie);
        model.loadData();
        startActivity(intent);
    }

    private void setupViewModel() {
        progressBarAllMovie.setVisibility(View.VISIBLE);
        progressBarRecommendMovie.setVisibility(View.VISIBLE);

        SelectedDataSource selectedDataSource = Utils.checkInternetState(requireContext());
        ExecutorService executorService =
                ((MyApplication) requireActivity().getApplication()).homeFragmentExecutorService;
        RemoteDataSource<ListMovieApiResponse> remoteDataSource =
                new RemoteDataSource<>(ListMovieApiResponse.class, executorService);
        LocalDataSource localDataSource = new LocalDataSource();
        Repository repository = new Repository.Builder()
                .setSelectedDataSource(selectedDataSource)
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        mViewModel = new ViewModelProvider(this,
                new MovieViewModelFactory(repository)).get(MovieViewModel.class);
        // Quan sát dữ liệu tất cả các phim
        mViewModel.getAllMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                progressBarAllMovie.setVisibility(View.GONE);
                uiHandler.post(() -> mAdapterAllMovies.updateData(movies));
            } else {
                Toast.makeText(requireContext(), "No movies found", Toast.LENGTH_SHORT).show();
            }
        });
        // Quan sát dữ liệu phim VIP
        mViewModel.getVipMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                progressBarRecommendMovie.setVisibility(View.GONE);
                  // Cập nhật danh sách phim VIP
                uiHandler.post(() -> mAdapterBestMovies.updateData(movies));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        slideHandler.removeCallbacks(sliderRunnable);
    }

}