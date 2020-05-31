package com.nessbit.medha.jacai.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Winner;

public class WinnerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_winner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Winner winner = getArguments().getParcelable("data");

        TextView winnerNameTv = view.findViewById(R.id.winnerNameTv);
        TextView winnerMobileTv = view.findViewById(R.id.winnerMobileTv);
        TextView winnerScoreTv = view.findViewById(R.id.scoreTv);

        winnerNameTv.setText(winner.getWinnerName());
        winnerMobileTv.setText(winner.getWinnerMobile().substring(2));
        winnerScoreTv.setText(""+winner.getWinnerScore());
    }
}
