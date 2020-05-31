package com.nessbit.vojonbari.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeName;
import com.nessbit.vojonbari.view.adapter.RecipeNameAdapter;

import java.util.ArrayList;


public class RecipeNameFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView recipeHeaderTv = view.findViewById(R.id.recipeHeaderTv);
        recipeHeaderTv.setText("খাবারের নাম");

        ArrayList<RecipeName> recipeNames = getArguments().getParcelableArrayList("recipeName");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecipeNameAdapter adapter = new RecipeNameAdapter(getContext(),recipeNames);
        recyclerView.setAdapter(adapter);
    }
}
