package com.nessbit.vojonbari.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeCategory;
import com.nessbit.vojonbari.service.UploadRecipeService;
import com.nessbit.vojonbari.service.network.NetworkCallback;
import com.nessbit.vojonbari.service.parser.FetchRecipeCategory;
import java.util.ArrayList;

import static com.nessbit.vojonbari.utils.AppConstants.FILL_FORM;
import static com.nessbit.vojonbari.utils.AppConstants.NO_IMAGE;
import static com.nessbit.vojonbari.utils.SpinnerUtil.showSpinner;

public class UploadRecipeActivity extends AppCompatActivity implements NetworkCallback {

    private TextView recipeUploadTv;
    private Spinner recipeCategorySn;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);

        new FetchRecipeCategory(this).execute();
    }

    private void viewImplementation(ArrayList<String> categories) {
        recipeCategorySn = findViewById(R.id.recipeCategorySn);
        showSpinner(this,recipeCategorySn,categories);

        EditText recipeNameEt = findViewById(R.id.recipeNameEt);
        EditText recipeDetailsEt = findViewById(R.id.recipeDetailsEt);
        recipeUploadTv = findViewById(R.id.recipeUploadTv);
        recipeUploadTv.setOnClickListener(v -> showFileChooser());

        findViewById(R.id.uploadBtn).setOnClickListener(v -> {
            String category =  recipeCategorySn.getSelectedItem().toString().trim();
            String name = recipeNameEt.getText().toString().trim();
            String details = recipeDetailsEt.getText().toString().trim();

            if (!(category.isEmpty() || name.isEmpty() || details.isEmpty())) {
                if (recipeUploadTv.getText().toString().trim().isEmpty())
                    new UploadRecipeService(this).executeAlternative(category,name,details, NO_IMAGE);
                    //Toast.makeText(this, "Please Select a recipe Image", Toast.LENGTH_SHORT).show();
                else new UploadRecipeService(this).execute(category,name,details,getPath(filePath));
            }
            else Toast.makeText(this, FILL_FORM, Toast.LENGTH_SHORT).show();
        });
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                filePath = data.getData();
                String path = getPath(filePath);
                String[] arr = path.split("/");
                recipeUploadTv.setText(arr[arr.length - 1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
    //method to get the file path from uri
    private String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    public void onTaskFinish(boolean success) {}

    @Override
    public void onTaskFinish(ArrayList<?> data) {
        ArrayList<RecipeCategory> foodCategories = (ArrayList<RecipeCategory>) data;
        ArrayList<String> categories = new ArrayList<>();
        for (RecipeCategory recipeCategory : foodCategories) {
            categories.add(recipeCategory.getCategory());
        }
        viewImplementation(categories);
    }
}
