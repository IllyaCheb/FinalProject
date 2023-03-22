package com.example.animefinalappproject;

// Illya Chebotaryov

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<AnimeModel> animeList;
    private Context context;

    public ItemAdapter(ArrayList<AnimeModel> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }
    // ItemViewHolder
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_item, parent, false);
        return new ItemViewHolder(view);
    }
    //
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(animeList.get(position));
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivPoster;
        private AnimeModel animeModel;

        ItemViewHolder(View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        void bind(AnimeModel animeModel) {
            this.animeModel = animeModel;

            // Load the poster using Glide
            Glide.with(itemView)
                    .load(animeModel.getImageUrl())
                    .placeholder(R.drawable.placeholder) // Replace with your placeholder drawable
                    .error(R.drawable.error) // Replace with your error drawable
                    .into(ivPoster);
        }

        @Override
        public void onClick(View view) {
            // Replace MainActivity with the actual name of your activity if different
            ((MainActivity) view.getContext()).launchSecondActivity(animeModel);
        }
    }
}
