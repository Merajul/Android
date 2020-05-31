package com.nessbit.medha.jacai.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Category;
import com.nessbit.medha.jacai.service.parser.FetchCategory;
import com.nessbit.medha.jacai.service.network.NetworkCallback;
import com.nessbit.medha.jacai.service.parser.FetchUserScore;
import com.nessbit.medha.jacai.service.parser.FetchWinner;
import com.nessbit.medha.jacai.utils.PrefUserInfo;
import com.nessbit.medha.jacai.view.adapter.CategoryAdapter;
import com.nessbit.medha.jacai.view.fragment.UserFragment;
import com.nessbit.medha.jacai.view.fragment.WinnerFragment;

import java.util.ArrayList;

import static com.nessbit.medha.jacai.utils.DialogUtils.showExitDialog;
import static com.nessbit.medha.jacai.utils.NetworkUtil.haveNetworkConnection;

public class CategoryActivity extends AppCompatActivity implements NetworkCallback {

    private AlertDialog dialog;
    private final String NETWORK_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private PrefUserInfo prefUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        prefUserInfo=new PrefUserInfo(this);

        if (haveNetworkConnection(this)) new FetchCategory(this).execute();
        else showRefreshNetDialog();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = (@NonNull MenuItem menuItem) -> {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(CategoryActivity.this, CategoryActivity.class));
                break;
            case R.id.nav_trophy:
                new FetchWinner(CategoryActivity.this).execute();
                /*removeFragmentBackStack();
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.content, new WinnerFragment()).addToBackStack(null).commit();*/
                break;
            case R.id.nav_user:
                new FetchUserScore(CategoryActivity.this).execute(prefUserInfo.getUserId());
                //removeFragmentBackStack();
                /*getSupportFragmentManager().beginTransaction().
                        replace(R.id.content, new UserFragment()).addToBackStack(null).commit();*/
                break;
        }
        return true;
    };

    protected void removeFragmentBackStack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
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
                    new FetchCategory(CategoryActivity.this).execute();
                } else {
                    unregisterReceiver(networkReceiver);
                    new Handler().postDelayed(sendUpdatesToUI, 500);
                }
            }
        }
    };

    @Override
    public void onTaskFinish(Object data,String TAG) {
        Bundle args = new Bundle();
        args.putParcelable("data", (Parcelable) data);
        Fragment fragment = null;

        if (TAG.equalsIgnoreCase("FetchUserScore")) fragment = new UserFragment();
        else if (TAG.equalsIgnoreCase("FetchWinner")) fragment = new WinnerFragment();

        fragment.setArguments(args);
        removeFragmentBackStack();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onTaskFinish(ArrayList<?> data) {
        ArrayList<Category> categories = (ArrayList<Category>) data;
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1)
            getSupportFragmentManager().popBackStack();
        else showExitDialog(this);
    }
}
