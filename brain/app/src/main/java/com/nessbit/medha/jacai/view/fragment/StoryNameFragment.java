package com.nessbit.medha.jacai.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.StoryName;
import com.nessbit.medha.jacai.view.adapter.StoryNameAdapter;

import java.util.ArrayList;

public class StoryNameFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_story_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<StoryName> storyNames = getArguments().getParcelableArrayList("storyName");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        StoryNameAdapter adapter = new StoryNameAdapter(getContext(),storyNames);
        recyclerView.setAdapter(adapter);
    }
}
