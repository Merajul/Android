package com.nessbit.vojonbari.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeDetails;
import com.nessbit.vojonbari.service.ReviewService;
import com.nessbit.vojonbari.utils.GlideApp;

import java.util.ArrayList;

import static com.nessbit.vojonbari.utils.AppConstants.NO_IMAGE;
import static com.nessbit.vojonbari.utils.DialogUtils.showDialogTrans;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ArrayList<RecipeDetails> recipeDetails = getIntent().getParcelableArrayListExtra("recipeDetails");

        TextView recipeNameTv = findViewById(R.id.recipeHeaderTv);
        TextView recipeDetailsTv = findViewById(R.id.recipeDetailsTv);
        ImageView recipeIv = findViewById(R.id.recipeIv);
        Button reviewBtn = findViewById(R.id.reviewBtn);

        recipeNameTv.setText(recipeDetails.get(0).getRecipeName());
        recipeDetailsTv.setText(recipeDetails.get(0).getRecipeDetails());
        if (!NO_IMAGE.equalsIgnoreCase(recipeDetails.get(0).getRecipeImage())) {
            GlideApp.with(this).load(recipeDetails.get(0).getRecipeImage())
                    .thumbnail(GlideApp.with(this).load(R.drawable.double_ring))
                    .dontAnimate()
                    .dontTransform()
                    .into(recipeIv);
        } else recipeIv.setVisibility(View.GONE);

        reviewBtn.setOnClickListener(v -> showReviewDialog(recipeDetails.get(0).getRecipeId()));
    }

    private void showReviewDialog(String recipeId) {
        Dialog dialog = showDialogTrans(this,R.layout.dialog_review,false,true);
        dialog.show();

        RatingBar ratingRb = dialog.findViewById(R.id.ratingRb);
        EditText reviewBoxEt = dialog.findViewById(R.id.reviewBoxEt);
        Button submitBtn = dialog.findViewById(R.id.submitBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);

        submitBtn.setOnClickListener(v -> new ReviewService(this).execute(recipeId,
                    String.valueOf(ratingRb.getRating()),reviewBoxEt.getText().toString().trim()));
        cancelBtn.setOnClickListener(v -> dialog.dismiss());
    }
}
