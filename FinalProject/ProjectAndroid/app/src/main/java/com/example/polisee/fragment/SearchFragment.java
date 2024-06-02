package com.example.polisee.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.polisee.Politician;
import com.example.polisee.R;
import com.example.polisee.adapter.AdapterSearch;
import com.example.polisee.api.ApiConfig;
import com.example.polisee.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterSearch adapterSearch;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclersearch);
        progressBar = view.findViewById(R.id.progressbarsearch);
        searchView = view.findViewById(R.id.search);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSearch = new AdapterSearch(getContext());
        recyclerView.setAdapter(adapterSearch);

        ImageView navHome = view.findViewById(R.id.nav_home);
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.maincontainer, homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ImageView navFavorite = view.findViewById(R.id.nav_favorite);
        navFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.maincontainer, favoriteFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ImageView navAbout = view.findViewById(R.id.nav_about);
        navAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment aboutFragment = new AboutFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.maincontainer, aboutFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        setupSearchView();
        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPoliticians(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchPoliticians(String query) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiConfig.getApiService();
        Call<List<Politician>> call = apiService.searchPoliticians(query);
        call.enqueue(new Callback<List<Politician>>() {
            @Override
            public void onResponse(Call<List<Politician>> call, Response<List<Politician>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    adapterSearch.setPoliticianList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Politician>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}