package com.example.newsapp41;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp41.databinding.FragmentProfileBinding;
import com.example.newsapp41.databinding.ShotItemBinding;

import java.util.ArrayList;

public class ShotsAdapter extends RecyclerView.Adapter<ShotsAdapter.ShotsViewHolder>{

    private ArrayList<Integer> photos;

    public ShotsAdapter(ArrayList<Integer> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ShotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShotsViewHolder(ShotItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShotsViewHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ShotsViewHolder extends RecyclerView.ViewHolder {
        private ShotItemBinding binding;

        public ShotsViewHolder(@NonNull ShotItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Integer imageView) {
            binding.shot.setImageResource(imageView);
        }
    }
}
