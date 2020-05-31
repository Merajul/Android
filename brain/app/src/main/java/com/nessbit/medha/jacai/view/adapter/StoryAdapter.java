package com.nessbit.medha.jacai.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Question;
import com.nessbit.medha.jacai.model.Story;
import com.nessbit.medha.jacai.service.UploadScoreService;
import com.nessbit.medha.jacai.utils.AppConstants;
import com.nessbit.medha.jacai.utils.GlideApp;
import com.nessbit.medha.jacai.utils.PrefUserInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Story story;
    private ArrayList<Question> questions;
    private PrefUserInfo prefUserInfo;
    private String[] radioSelectedText;
    private int score,flag;

    public StoryAdapter(Context context, Story story, ArrayList<Question> questions) {
        this.context = context;
        this.story = story;
        this.questions = questions;
        prefUserInfo = new PrefUserInfo(context);
        radioSelectedText = new String[questions.size()];
        Arrays.fill(radioSelectedText,null);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                return new ViewHolderHeader(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_story_header, viewGroup, false));
            case 1:
                return new ViewHolderQuestion(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_story, viewGroup, false));
            case 2:
                return new ViewHolderFooter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_story_footer, viewGroup, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder VH, int i) {
        switch (VH.getItemViewType()) {
            case 0:
                ViewHolderHeader viewHolder = (ViewHolderHeader) VH;
                viewHolder.storyNameTv.setText(story.getStoryName());
                viewHolder.storyDetailsTV.setText(story.getStoryDetails());
                if (!AppConstants.NO_IMAGE.equalsIgnoreCase(story.getStoryImage())) {
                    GlideApp.with(context).load(story.getStoryImage())
                            .thumbnail(GlideApp.with(context).load(R.drawable.double_ring))
                            .dontAnimate()
                            .dontTransform()
                            .into(viewHolder.storyImageIv);
                } else viewHolder.storyImageIv.setVisibility(View.GONE);
                break;
            case 1:
                ViewHolderQuestion viewHolderQ = (ViewHolderQuestion) VH;
                viewHolderQ.questionTv.setText(questions.get(i - 1).getQuestion());
                viewHolderQ.optionRb1.setText(questions.get(i - 1).getOption1());
                viewHolderQ.optionRb2.setText(questions.get(i - 1).getOption2());
                viewHolderQ.optionRb3.setText(questions.get(i - 1).getOption3());
                viewHolderQ.optionRb4.setText(questions.get(i - 1).getOption4());

                viewHolderQ.radioGroupRg.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
                    RadioButton radioButton = group.findViewById(checkedId);
                    radioSelectedText[i - 1] = radioButton.getText().toString();
                });
                break;
            case 2:
                ViewHolderFooter viewHolderB = (ViewHolderFooter) VH;
                viewHolderB.submitBtn.setOnClickListener(v -> {
                    score = 0;
                    flag = 1;
                    for (int looper = 0; looper < radioSelectedText.length; looper++) {
                        if (radioSelectedText[looper] == null) {
                            Toast.makeText(context, "All Answer Submit", Toast.LENGTH_SHORT).show();
                            flag = 0;
                            break;
                        } else {
                            if (radioSelectedText[looper].equalsIgnoreCase(questions.get(looper).getRightAnswer()))
                                score += 1;
                        }
                    }
                    if (flag == 1) new UploadScoreService(context).execute(prefUserInfo.getUserId(),score);
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        else if (position == questions.size() + 1) return 2;
        else return 1;
    }

    @Override
    public int getItemCount() {
        return questions.size() + 2;
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {

        private ImageView storyImageIv;
        private TextView storyNameTv;
        private TextView storyDetailsTV;

        public ViewHolderHeader(@NonNull View itemView) {
            super(itemView);
            storyImageIv = itemView.findViewById(R.id.storyImageIv);
            storyNameTv = itemView.findViewById(R.id.storyNameTv);
            storyDetailsTV = itemView.findViewById(R.id.storyDetailsTv);
        }
    }

    class ViewHolderQuestion extends RecyclerView.ViewHolder {

        private TextView questionTv;
        private RadioGroup radioGroupRg;
        private RadioButton optionRb1;
        private RadioButton optionRb2;
        private RadioButton optionRb3;
        private RadioButton optionRb4;

        public ViewHolderQuestion(@NonNull View itemView) {
            super(itemView);
            questionTv = itemView.findViewById(R.id.questionTv);
            radioGroupRg = itemView.findViewById(R.id.radioGroupRg);
            optionRb1 = itemView.findViewById(R.id.optionRb1);
            optionRb2 = itemView.findViewById(R.id.optionRb2);
            optionRb3 = itemView.findViewById(R.id.optionRb3);
            optionRb4 = itemView.findViewById(R.id.optionRb4);
        }
    }

    class ViewHolderFooter extends RecyclerView.ViewHolder {

        private Button submitBtn;

        public ViewHolderFooter(@NonNull View itemView) {
            super(itemView);
            submitBtn = itemView.findViewById(R.id.submitBtn);
        }
    }
}
