package com.nessbit.vojonbari.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nessbit.vojonbari.ItemClickListener;
import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeCategory;
import com.nessbit.vojonbari.service.parser.FetchRecipeName;

import java.util.ArrayList;

public class RecipeCategoryAdapter extends RecyclerView.Adapter<RecipeCategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RecipeCategory> recipeCategories;

    public RecipeCategoryAdapter(Context context, ArrayList<RecipeCategory> recipeCategories) {
        this.context = context;
        this.recipeCategories = recipeCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recipe_category,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.foodMenuTv.setText(recipeCategories.get(i).getCategory());
        viewHolder.setItemClickListener((view, position) -> new FetchRecipeName(context).execute(recipeCategories.get(position).getCategory()));
    }

    @Override
    public int getItemCount() {
        return recipeCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView foodMenuTv;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodMenuTv = itemView.findViewById(R.id.foodMenuTv);
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
