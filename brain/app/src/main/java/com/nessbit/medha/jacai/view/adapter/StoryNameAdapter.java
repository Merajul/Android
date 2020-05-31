package com.nessbit.medha.jacai.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.StoryName;
import com.nessbit.medha.jacai.service.parser.FetchStory;
import com.nessbit.medha.jacai.view.ItemClickListener;
import java.util.ArrayList;

public class StoryNameAdapter extends RecyclerView.Adapter<StoryNameAdapter.ViewHolder> {

    private Context context;
    ArrayList<StoryName> storyNames;

    public StoryNameAdapter(Context context, ArrayList<StoryName> storyNames) {
        this.context = context;
        this.storyNames = storyNames;
    }

    @NonNull
    @Override
    public StoryNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_story_name,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryNameAdapter.ViewHolder viewHolder, int i) {
        viewHolder.storyNameTv.setText(storyNames.get(i).getStoryName());
        viewHolder.setItemClickListener((view, position) -> new FetchStory(context).execute(storyNames.get(position).getStoryName()));
    }

    @Override
    public int getItemCount() {
        return storyNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView storyNameTv;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storyNameTv = itemView.findViewById(R.id.storyNameTv);
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
