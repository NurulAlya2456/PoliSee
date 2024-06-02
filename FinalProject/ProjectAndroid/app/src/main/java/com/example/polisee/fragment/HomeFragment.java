package com.example.polisee.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polisee.Politician;
import com.example.polisee.R;
import com.example.polisee.adapter.AdapterHome;
import com.example.polisee.api.ApiConfig;
import com.example.polisee.api.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView rvHome;
    private AdapterHome adapterHome;
    private ExecutorService executorService;
    private Handler handler;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progressbarhome);
        rvHome = view.findViewById(R.id.recyclerhome);
        rvHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapterHome = new AdapterHome(getContext());
        rvHome.setAdapter(adapterHome);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        ImageView navSearch = view.findViewById(R.id.nav_search);
        navSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.maincontainer, searchFragment)
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

        loadPolitician();
        return view;
    }

    private void loadPolitician() {
        handler.post(() -> progressBar.setVisibility(View.VISIBLE));

        executorService.execute(() -> {
            ApiService apiService = ApiConfig.getApiService();
            Call<List<Politician>> call = apiService.getData();

            call.enqueue(new Callback<List<Politician>>() {
                @Override
                public void onResponse(Call<List<Politician>> call, Response<List<Politician>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Politician> politicians = response.body();

                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            adapterHome.setPoliticians(politicians);
                        });
                    } else {
                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<Politician>> call, Throwable t) {
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }
}