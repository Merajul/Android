package com.nessbit.vojonbari.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nessbit.vojonbari.ItemClickListener;
import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeName;
import com.nessbit.vojonbari.service.parser.FetchRecipeDetails;

import java.util.ArrayList;


public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RecipeName> recipeNames;

    public RecipeNameAdapter(Context context, ArrayList<RecipeName> recipeNames) {
        this.context = context;
        this.recipeNames = recipeNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recipe_name,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.foodMenuTv.setText(recipeNames.get(i).getRecipeName());
        viewHolder.reviewerTv.setText(recipeNames.get(i).getTotalReviewer());
        viewHolder.avgRb.setRating(Float.valueOf(recipeNames.get(i).getAvgRating()));
        viewHolder.setItemClickListener((view, position) -> new FetchRecipeDetails(context).execute(recipeNames.get(position).getRecipeId()));
    }

    @Override
    public int getItemCount() {
        return recipeNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView foodMenuTv,reviewerTv;
        private RatingBar avgRb;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodMenuTv = itemView.findViewById(R.id.foodMenuTv);
            reviewerTv = itemView.findViewById(R.id.reviewerTv);
            avgRb = itemView.findViewById(R.id.avgRb);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(itemView, getAdapterPosition());
        }
    }
}
