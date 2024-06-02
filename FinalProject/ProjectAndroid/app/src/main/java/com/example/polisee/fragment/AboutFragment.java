package com.example.polisee.fragment;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.polisee.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        Button btnSelengkapnya = view.findViewById(R.id.btn_selengkapnya);
        btnSelengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ganti URL_WEBSITE dengan URL yang diinginkan
                String url = "https://id.wikipedia.org/wiki/Jabatan_politik";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Navigation to Home Fragment
        ImageView navHome = view.findViewById(R.id.nav_home);
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homeFragment = new HomeFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.maincontainer, homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Navigation to Search Fragment
        ImageView navSearch = view.findViewById(R.id.nav_search);
        navSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment searchFragment = new SearchFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.maincontainer, searchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Navigation to Favorite Fragment
        ImageView navFavorite = view.findViewById(R.id.nav_favorite);
        navFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment favoriteFragment = new FavoriteFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.maincontainer, favoriteFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}