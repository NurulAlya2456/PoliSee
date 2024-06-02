package com.example.polisee.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.polisee.DatabaseHelper;
import com.example.polisee.Politician;
import com.example.polisee.R;
import com.example.polisee.activity.DetailActivity;
import com.example.polisee.adapter.AdapterFavorite;

import java.util.List;

public class FavoriteFragment extends Fragment {
    private RecyclerView rvFavorite;
    private AdapterFavorite adapterFavorite;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorite = view.findViewById(R.id.rvfavorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());
        List<Politician> politicians = databaseHelper.getFavorite();

        adapterFavorite = new AdapterFavorite(politicians);
        adapterFavorite.setOnItemClickListener(politician -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("id", String.valueOf(politician.getId()));
            intent.putExtra("name", politician.getName());
            intent.putExtra("position", politician.getPosition());
            intent.putExtra("dob", politician.getDob());
            intent.putExtra("country", politician.getCountry());
            intent.putExtra("party", politician.getParty());
            intent.putExtra("years_in_office", politician.getYears_in_office());
            intent.putExtra("biography", politician.getBiography());
            intent.putExtra("image", politician.getImage());
            startActivity(intent);
        });

        adapterFavorite.setOnStarClickListener(politician -> {
            try {
                databaseHelper.removeFavorite(politician.getId());
                List<Politician> updatedPoliticians = databaseHelper.getFavorite();
                adapterFavorite.setPoliticians(updatedPoliticians);
                Log.d("FavoriteFragment", "Politician " + politician.getName() + " removed from favorites");
            } catch (Exception e) {
                Log.e("FavoriteFragment", "Error removing favorite: " + e.getMessage());
            }
        });

        rvFavorite.setAdapter(adapterFavorite);

        setupNavigation(view);
        return view;
    }

    private void setupNavigation(View view) {
        ImageView navHome = view.findViewById(R.id.nav_home);
        navHome.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.maincontainer, homeFragment)
                    .addToBackStack(null)
                    .commit();
        });

        ImageView navSearch = view.findViewById(R.id.nav_search);
        navSearch.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.maincontainer, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });

        ImageView navAbout = view.findViewById(R.id.nav_about);
        navAbout.setOnClickListener(v -> {
            AboutFragment aboutFragment = new AboutFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.maincontainer, aboutFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Saat Fragment Favorite dilakukan resume, perbarui daftar politisi favorit
        List<Politician> politicians = databaseHelper.getFavorite();
        adapterFavorite.setPoliticians(politicians);
    }
}