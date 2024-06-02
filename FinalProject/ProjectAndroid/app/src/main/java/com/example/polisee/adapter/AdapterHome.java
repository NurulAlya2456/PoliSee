package com.example.polisee.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polisee.Politician;
import com.example.polisee.R;
import com.example.polisee.activity.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {
    private List<Politician> politicians = new ArrayList<>();
    private Context context;

    public AdapterHome(Context context) {
        this.context = context;
    }

    public void setPoliticians(List<Politician> politicians) {
        this.politicians = politicians;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.ViewHolder holder, int position) {
        Politician politician = politicians.get(position);

        holder.name.setText(politician.getName());
        holder.position.setText(politician.getPosition());
        holder.country.setText(politician.getCountry());
        holder.biography.setText(politician.getBiography());
        Picasso.get().load(politician.getImage()).into(holder.profile);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", String.valueOf(politician.getId()));
            intent.putExtra("name", politician.getName());
            intent.putExtra("position", politician.getPosition());
            intent.putExtra("dob", politician.getDob());
            intent.putExtra("country", politician.getCountry());
            intent.putExtra("party", politician.getParty());
            intent.putExtra("years_in_office", politician.getYears_in_office());
            intent.putExtra("biography", politician.getBiography());
            intent.putExtra("image", politician.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return politicians.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name, position, country, biography;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namehome);
            position = itemView.findViewById(R.id.positionhome);
            country = itemView.findViewById(R.id.countryhome);
            biography = itemView.findViewById(R.id.biographyhome);
            profile = itemView.findViewById(R.id.profilehome);
        }
    }
}