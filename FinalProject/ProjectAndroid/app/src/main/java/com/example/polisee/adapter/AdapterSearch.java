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

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {
    private List<Politician> politicianList = new ArrayList<>();
    private Context context;

    public AdapterSearch(Context context) {
        this.context = context;
    }

    public void setPoliticianList(List<Politician> politicianList) {
        this.politicianList = politicianList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Politician politician = politicianList.get(position);
        holder.nameTextView.setText(politician.getName());
        holder.positionTextView.setText(politician.getPosition());
        holder.countryTextView.setText(politician.getCountry());
        holder.biographyTextView.setText(politician.getBiography());
        Picasso.get().load(politician.getImage()).into(holder.profileImageView);

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
        return politicianList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView profileImageView;
        TextView nameTextView, positionTextView, countryTextView, biographyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profilehomesearch);
            nameTextView = itemView.findViewById(R.id.namehomesearch);
            positionTextView = itemView.findViewById(R.id.positionhomesearch);
            countryTextView = itemView.findViewById(R.id.countryhomesearch);
            biographyTextView = itemView.findViewById(R.id.biographyhomesearch);
        }
    }
}