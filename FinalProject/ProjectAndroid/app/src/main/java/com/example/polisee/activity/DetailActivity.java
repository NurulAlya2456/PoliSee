package com.example.polisee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polisee.DatabaseHelper;
import com.example.polisee.Politician;
import com.example.polisee.R;
import com.example.polisee.api.ApiConfig;
import com.example.polisee.api.ApiService;
import com.example.polisee.response.DataResponse;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ImageView profile;
    private TextView name, dob, country, party, position, years, biography;
    private ImageView saveStar;
    private int politicianId;
    private boolean isFavorite = false;
    private ExecutorService executorService;
    private Handler handler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        profile = findViewById(R.id.imagedetail);
        name = findViewById(R.id.namedetail);
        dob = findViewById(R.id.dobdetail);
        country = findViewById(R.id.countrydetail);
        party = findViewById(R.id.partydetail);
        position = findViewById(R.id.positiondetail);
        years = findViewById(R.id.yearsdetail);
        biography = findViewById(R.id.biographydetail);
        saveStar = findViewById(R.id.savestar1detail);
        progressBar = findViewById(R.id.progressbardetail);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String nameText = intent.getStringExtra("name");
        String positionText = intent.getStringExtra("position");
        String dobText = intent.getStringExtra("dob");
        String countryText = intent.getStringExtra("country");
        String partyText = intent.getStringExtra("party");
        String yearsText = intent.getStringExtra("years_in_office");
        String biographyText = intent.getStringExtra("biography");
        String imageText = intent.getStringExtra("image");


        if (nameText != null) name.setText(nameText);
        if (positionText != null) position.setText(positionText);
        if (dobText != null) dob.setText(dobText);
        if (countryText != null) country.setText(countryText);
        if (partyText != null) party.setText(partyText);
        if (yearsText != null) years.setText(yearsText);
        if (biographyText != null) biography.setText(biographyText);
        if (imageText != null) Picasso.get().load(imageText).into(profile);

        if (id != null) {
            politicianId = Integer.parseInt(id);
            loadPoliticianDetails(id);
            checkIsFavorite(politicianId);
        }

        saveStar.setOnClickListener(v -> {
            if (!isFavorite) {
                addToFavorites(politicianId, nameText, positionText, dobText, countryText, partyText, yearsText, biographyText, imageText);
            } else {
                removeFromFavorites(politicianId);
            }
        });
    }

    private void loadPoliticianDetails(String id) {
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            ApiService apiService = ApiConfig.getApiService();
            Call<DataResponse> call = apiService.getDetail(id);

            call.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    handler.post(() -> progressBar.setVisibility(View.GONE));
                    if (response.isSuccessful()) {
                        DataResponse dataResponse = response.body();
                        if (dataResponse != null) {
                            Politician politician = dataResponse.getData();
                            if (politician != null) {
                                handler.post(() -> {
                                    name.setText(politician.getName());
                                    position.setText(politician.getPosition());
                                    dob.setText(politician.getDob());
                                    country.setText(politician.getCountry());
                                    party.setText(politician.getParty());
                                    years.setText(politician.getYears_in_office());
                                    biography.setText(politician.getBiography());
                                    Picasso.get().load(politician.getImage()).into(profile);
                                });
                            }
                        } else {
                            Log.e("DetailActivity", "DataResponse object is null");
                        }
                    } else {
                        Log.e("DetailActivity", "Failed to load politician details: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    handler.post(() -> progressBar.setVisibility(View.GONE));
                    Log.e("DetailActivity", "onFailure: " + t.getMessage());
                }
            });
        });
    }

    private void checkIsFavorite(int id) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        isFavorite = dbHelper.isFavorite(id);
        updateStarImage();
    }

    private void addToFavorites(int id, String name, String position, String dob, String country, String party, String years_in_office, String biography, String image) {
        DatabaseHelper dbHelper = new DatabaseHelper(DetailActivity.this);
        boolean isAdded = dbHelper.addFavorite(new Politician(id, name, position, dob, country, party, years_in_office, biography, image, System.currentTimeMillis()));
        if (isAdded) {
            isFavorite = true;
            updateStarImage();
            Toast.makeText(DetailActivity.this, "Ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailActivity.this, "Gagal menambahkan ke favorit", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromFavorites(int id) {
        DatabaseHelper dbHelper = new DatabaseHelper(DetailActivity.this);
        boolean isRemoved = dbHelper.removeFavorite(id);
        if (isRemoved) {
            isFavorite = false;
            updateStarImage();
            Toast.makeText(DetailActivity.this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailActivity.this, "Gagal menghapus dari favorit", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStarImage() {
        if (isFavorite) {
            saveStar.setImageResource(R.drawable.starblack);
        } else {
            saveStar.setImageResource(R.drawable.starwhite);
        }
    }
}