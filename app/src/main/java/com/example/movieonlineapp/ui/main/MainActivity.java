package com.example.movieonlineapp.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.movieonlineapp.R;
import com.example.movieonlineapp.ui.fragments.FavouriteFragment;
import com.example.movieonlineapp.ui.fragments.HomeFragment;
import com.example.movieonlineapp.ui.fragments.SearchFragment;
import com.example.movieonlineapp.ui.fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    public static final int FRAGMENT_HOME = 1;
    public static final int FRAGMENT_SEARCH = 2;
    public static final int FRAGMENT_FAVOURITE = 3;
    public static final int FRAGMENT_PRIVATE = 4;

    public static int currentFragment = FRAGMENT_HOME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        replaceFragment(new HomeFragment());
        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                if (FRAGMENT_HOME != currentFragment) {
                    mBottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                    replaceFragment(new HomeFragment());
                    currentFragment = FRAGMENT_HOME;
                }

            } else if (id == R.id.nav_favourite) {
                if (FRAGMENT_FAVOURITE != currentFragment) {
                    mBottomNavigationView.getMenu().findItem(R.id.nav_favourite).setChecked(true);
                    replaceFragment(new FavouriteFragment());
                    currentFragment = FRAGMENT_FAVOURITE;
                }

            } else if (id == R.id.nav_search) {
                if (FRAGMENT_SEARCH != currentFragment) {
                    mBottomNavigationView.getMenu().findItem(R.id.nav_search).setChecked(true);
                    replaceFragment(new SearchFragment());
                    currentFragment = FRAGMENT_SEARCH;
                }

            } else {
                if(FRAGMENT_PRIVATE != currentFragment){
                    mBottomNavigationView.getMenu().findItem(R.id.nav_private).setChecked(true);
                    replaceFragment(new UserFragment());
                    currentFragment = FRAGMENT_PRIVATE;
                }

            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        @SuppressLint("CommitTransaction")
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }
}