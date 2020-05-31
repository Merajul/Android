package com.nessbit.vojonbari.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeCategory;
import com.nessbit.vojonbari.service.network.NetworkCallback;
import com.nessbit.vojonbari.service.parser.FetchRecipeCategory;
import com.nessbit.vojonbari.service.parser.FetchWinner;
import com.nessbit.vojonbari.utils.PrefUserInfo;
import com.nessbit.vojonbari.view.adapter.RecipeCategoryAdapter;
import com.nessbit.vojonbari.view.fragment.RecipeWinnerFragment;

import java.util.ArrayList;
import java.util.Calendar;

import static com.nessbit.vojonbari.utils.DialogUtils.showExitDialog;
import static com.nessbit.vojonbari.utils.NetworkUtil.haveNetworkConnection;

public class RecipeCategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        NetworkCallback {

    private DrawerLayout drawerLayout;
    private PrefUserInfo prefUserInfo;
    private AlertDialog dialog;
    private final String NETWORK_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_category);

        prefUserInfo = new PrefUserInfo(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(2).setActionView(R.layout.drawer_winner_image);

        findViewById(R.id.drawerIcon).setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));

        if (haveNetworkConnection(this)) new FetchRecipeCategory(this).execute();
        else showRefreshNetDialog();
    }

    public void showRefreshNetDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.network_dialog, null);
        mBuilder.setView(mView).setCancelable(false)
                .setPositiveButton("REFRESH", (DialogInterface dialog, int which) -> registerReceiver(networkReceiver, new IntentFilter(NETWORK_ACTION)));
        dialog = mBuilder.create();
        dialog.show();
    }

    public Runnable sendUpdatesToUI = () -> showRefreshNetDialog();

    public BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches(NETWORK_ACTION)) {
                if (haveNetworkConnection(getApplicationContext())) {
                    dialog.dismiss();
                    unregisterReceiver(networkReceiver);
                    new FetchRecipeCategory(RecipeCategoryActivity.this).execute();
                } else {
                    unregisterReceiver(networkReceiver);
                    new Handler().postDelayed(sendUpdatesToUI, 500);
                }
            }
        }
    };

    protected void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit();
    }

    protected void removeFragmentBackStack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) getFragmentManager().popBackStack();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onTaskFinish(boolean success) {

    }

    @Override
    public void onTaskFinish(ArrayList<?> data) {
        ArrayList<RecipeCategory> foodCategories = (ArrayList<RecipeCategory>) data;
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecipeCategoryAdapter adapter = new RecipeCategoryAdapter(this, foodCategories);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) startActivity(new Intent(this, RecipeCategoryActivity.class));
        else if (id == R.id.nav_recipe_upload)
            startActivity(new Intent(this, UploadRecipeActivity.class));
        else if (id == R.id.nav_recipe_winner) {
            removeFragmentBackStack();

            Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int winnerMonth = prefUserInfo.getWinnerMonth();
            if (winnerMonth != currentMonth) new FetchWinner(this).execute(winnerMonth,currentMonth);
            else openFragment(new RecipeWinnerFragment());

        } else if (id == R.id.nav_about) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.nav_dialog_about, null);
            TextView sparentech = mView.findViewById(R.id.famedBD);
            sparentech.setMovementMethod(LinkMovementMethod.getInstance());
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
        }
        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1)
                getSupportFragmentManager().popBackStack();
            else showExitDialog(this);
        }
    }
}
