package com.nessbit.vojonbari.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import com.nessbit.vojonbari.R;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.nessbit.vojonbari.utils.NetworkUtil.haveNetworkConnection;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private Snackbar snackbar;
    private final int REQUEST_PERMISSION_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    public void onLogin(View view) {
        if (!haveNetworkConnection(this)) {
            FrameLayout content = findViewById(R.id.content);
            snackbar = Snackbar.make(content, "No internet connection!", Snackbar.LENGTH_SHORT)
                    .setAction("Close",(View v) -> snackbar.dismiss());
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.violet));
            snackbar.show();
        } else {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            } else startActivity(new Intent(this,SubscribeActivity.class)); //loggedIn();
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_CODE)
    protected void checkPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECEIVE_SMS};
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else EasyPermissions.requestPermissions(this, "Need permission for access storage while selecting recipe photo",
                    REQUEST_PERMISSION_CODE, perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            new AppSettingsDialog.Builder(this).build().show();
    }
}
