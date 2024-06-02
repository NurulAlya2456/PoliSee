package com.example.polisee.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polisee.Politician;
import com.example.polisee.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {
    private List<Politician> politicianList;
    private OnItemClickListener onItemClickListener;
    private OnStarClickListener onStarClickListener;

    public AdapterFavorite(List<Politician> politicianList) {
        this.politicianList = politicianList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnStarClickListener(OnStarClickListener listener) {
        this.onStarClickListener = listener;
    }

    @NonNull
    @Override
    public AdapterFavorite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavorite.ViewHolder holder, int position) {
        Politician politician = politicianList.get(position);
        holder.bind(politician, onItemClickListener, onStarClickListener);

    }

    @Override
    public int getItemCount() {
        return politicianList.size();
    }

    public void setPoliticians(List<Politician> politicians) {
        this.politicianList = politicians;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, position, lastAdded;
        public ImageView profileImage, starFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namefavorite);
            position = itemView.findViewById(R.id.positionfavorite);
            lastAdded = itemView.findViewById(R.id.lastadded);
            profileImage = itemView.findViewById(R.id.profilefavorite);
            starFavorite = itemView.findViewById(R.id.savestar2favorite);
        }

        public void bind(final Politician politician, final OnItemClickListener itemClickListener, final OnStarClickListener starClickListener) {
            name.setText(politician.getName());
            position.setText(politician.getPosition());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getDefault());
            String formattedDate = sdf.format(politician.getLast_added_timestamp());
            lastAdded.setText("Last added to favorites at " + formattedDate);

            // Memuat gambar profil menggunakan Picasso (jika ada)
            if (politician.getImage() != null && !politician.getImage().isEmpty()) {
                Picasso.get().load(politician.getImage()).into(profileImage);
            } else {
                profileImage.setImageResource(R.mipmap.ic_launcher); // Gambar default jika tidak ada gambar
            }

            itemView.setOnClickListener(v -> itemClickListener.onItemClick(politician));
            starFavorite.setOnClickListener(v -> {
                if (starClickListener != null) {
                    starClickListener.onStarClick(politician);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Politician politician);
    }

    public interface OnStarClickListener {
        void onStarClick(Politician politician);
    }
}