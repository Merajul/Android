package com.nessbit.medha.jacai.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Question;
import com.nessbit.medha.jacai.model.Story;
import com.nessbit.medha.jacai.view.adapter.StoryAdapter;

import java.util.ArrayList;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Story story = getIntent().getExtras().getParcelable("story");
        ArrayList<Question> questions = getIntent().getParcelableArrayListExtra("question");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        StoryAdapter adapter = new StoryAdapter(this, story, questions);
        recyclerView.setAdapter(adapter);
    }
}
