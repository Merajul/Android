package com.nessbit.medha.jacai.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Category;
import com.nessbit.medha.jacai.service.parser.FetchStoryName;
import com.nessbit.medha.jacai.view.ItemClickListener;

import java.util.ArrayList;

import static com.nessbit.medha.jacai.utils.AppConstants.CAT_CURRENT_AFFAIR;
import static com.nessbit.medha.jacai.utils.AppConstants.CAT_FICTION;
import static com.nessbit.medha.jacai.utils.AppConstants.CAT_HISTORY;
import static com.nessbit.medha.jacai.utils.AppConstants.CAT_INFO_TECH;
import static com.nessbit.medha.jacai.utils.AppConstants.CAT_SCIFI;
import static com.nessbit.medha.jacai.utils.AppConstants.CAT_SHORT_STORY;
import static com.nessbit.medha.jacai.utils.AppConstants.CAT_SPORTS;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_category,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.categoryTv.setText(categories.get(i).getCategory());
        if (CAT_SHORT_STORY.equalsIgnoreCase(categories.get(i).getCategory()))
            viewHolder.categoryIv.setImageResource(R.drawable.short_story);
        else if (CAT_INFO_TECH.equalsIgnoreCase(categories.get(i).getCategory()))
            viewHolder.categoryIv.setImageResource(R.drawable.info_tech);
        else if (CAT_CURRENT_AFFAIR.equalsIgnoreCase(categories.get(i).getCategory()))
            viewHolder.categoryIv.setImageResource(R.drawable.current_affairs);
        else if (CAT_HISTORY.equalsIgnoreCase(categories.get(i).getCategory()))
            viewHolder.categoryIv.setImageResource(R.drawable.history);
        else if (CAT_SCIFI.equalsIgnoreCase(categories.get(i).getCategory()))
            viewHolder.categoryIv.setImageResource(R.drawable.scifi);
        else if (CAT_FICTION.equalsIgnoreCase(categories.get(i).getCategory()))
            viewHolder.categoryIv.setImageResource(R.drawable.fiction);
        else if (CAT_SPORTS.equalsIgnoreCase(categories.get(i).getCategory()))
            viewHolder.categoryIv.setImageResource(R.drawable.sports);
        else viewHolder.categoryIv.setImageResource(R.drawable.not_found);

        viewHolder.setItemClickListener((view, position) -> new FetchStoryName(context).execute(categories.get(position).getCategory()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView categoryIv;
        private TextView categoryTv;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIv = itemView.findViewById(R.id.categoryIv);
            categoryTv = itemView.findViewById(R.id.categoryTv);
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
