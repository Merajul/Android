package com.nessbit.medha.jacai.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Score;
import com.nessbit.medha.jacai.service.UpdateUser;
import com.nessbit.medha.jacai.utils.PrefUserInfo;


public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PrefUserInfo prefUserInfo = new PrefUserInfo(getContext());
        Score score = getArguments().getParcelable("data");

        EditText nameEt = view.findViewById(R.id.nameEt);
        EditText ageEt = view.findViewById(R.id.ageEt);
        TextView mobileTv = view.findViewById(R.id.mobileTv);
        TextView scoreTv = view.findViewById(R.id.scoreTv);
        ImageView nameEditIv = view.findViewById(R.id.nameEditIv);
        ImageView ageEditIv = view.findViewById(R.id.ageEditIv);
        ImageView profileImageIv = view.findViewById(R.id.profileImageIv);
        Button saveBtn = view.findViewById(R.id.saveBtn);

        if ("Male".equalsIgnoreCase(prefUserInfo.getUserGender()))
            profileImageIv.setImageResource(R.drawable.male_user);
        else if ("Female".equalsIgnoreCase(prefUserInfo.getUserGender()))
            profileImageIv.setImageResource(R.drawable.female_user);
        else profileImageIv.setImageResource(R.drawable.not_found);

        nameEt.setEnabled(false);
        ageEt.setEnabled(false);
        nameEt.setText(prefUserInfo.getUserName());
        ageEt.setText(""+prefUserInfo.getUserAge());
        mobileTv.setText(prefUserInfo.getUserMobile().substring(2));
        if (score.getMonthlyScore() == -1) scoreTv.setText("Haven't participated yet!");
        else scoreTv.setText(""+score.getMonthlyScore());

        nameEditIv.setOnClickListener(v -> nameEt.setEnabled(true));
        ageEditIv.setOnClickListener(v -> ageEt.setEnabled(true));

        saveBtn.setOnClickListener(v -> {
            String userName = nameEt.getText().toString().trim();
            int age = Integer.parseInt(ageEt.getText().toString().trim());
            prefUserInfo.setUserName(userName);
            prefUserInfo.setUserAge(age);
            new UpdateUser(getContext()).execute(prefUserInfo.getUserId(), userName, age);
        });
    }
}
