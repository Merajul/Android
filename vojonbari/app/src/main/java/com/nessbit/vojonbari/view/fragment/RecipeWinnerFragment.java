package com.nessbit.vojonbari.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.Winner;
import com.nessbit.vojonbari.utils.PrefUserInfo;

import java.util.ArrayList;

public class RecipeWinnerFragment extends Fragment {

    private PrefUserInfo prefUserInfo;
    private TextView recipeNameTv,winnerNameTv,scoreTv;

    public RecipeWinnerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_winner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefUserInfo = new PrefUserInfo(getContext());

        recipeNameTv = view.findViewById(R.id.recipeNameTv);
        winnerNameTv = view.findViewById(R.id.winnerNameTv);
        scoreTv = view.findViewById(R.id.scoreTv);

        Bundle bundle = getArguments();
        if (bundle!=null) {
            ArrayList<Winner> winner = getArguments().getParcelableArrayList("winner");
            prefUserInfo.setWinnerMonth(Integer.parseInt(winner.get(0).getWinnerMonth()));
            prefUserInfo.setWinnerScore(winner.get(0).getWinnerScore());
            prefUserInfo.setWinnerName(winner.get(0).getWinnerName());
            prefUserInfo.setWinnerRecipe(winner.get(0).getWinnerRecipe());
        }

        recipeNameTv.setText("রেসিপির নাম: "+prefUserInfo.getWinnerRecipe());
        winnerNameTv.setText("বিজয়ীর নাম: "+prefUserInfo.getWinnerName());
        scoreTv.setText("স্কোর: "+prefUserInfo.getWinnerScore());
    }
}
