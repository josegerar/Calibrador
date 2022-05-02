package com.ingreatsol.calibrador.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.ingreatsol.calibrador.MainActivity;
import com.ingreatsol.calibrador.R;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestPermissionsViewModel extends ViewModel {

    public static final int LOCATION = 101;

    public void showAlertActiveIntent(Activity context, String title, String message, String intentString) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // On pressing Settings button
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(intentString);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void showAlertPermission(FragmentActivity context,
                                    String title, String message,
                                    String messageDenied, String[] permissions) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // On pressing Settings button
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (permissions.length == 1) {
                    ActivityResultLauncher<String> requestPermissionLauncher = context.registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                            isGranted -> {
                                if (!isGranted) {
                                    showAlertDialog(context, title, messageDenied);
                                }
                            });
                    requestPermissionLauncher.launch(permissions[0]);
                } else if (permissions.length > 1) {
                    ActivityResultLauncher<String[]> multiplePermissionLauncher = context.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                            isGranted -> {
                                if (isGranted.containsValue(false)) {
                                    showAlertDialog(context, title, messageDenied);
                                }
                            });
                    multiplePermissionLauncher.launch(permissions);
                }
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void showAlertDialog(FragmentActivity context,
                                String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void requestPermissionsIfNecessary(FragmentActivity context, String[] permissions, String title, String message, String messageDenied) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (permission.equals(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) continue;
            } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.DONUT) continue;
            }
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            showAlertPermission(context, title, message, messageDenied, permissionsToRequest.toArray(new String[0]));
        }
    }

    /**
     * @param context                          Application context
     * @param permissions                      permissions request results
     * @param grantResults                     results in permmisions
     * @param REQUEST_PERMISSIONS_REQUEST_CODE code request permissions
     */
    public void onRequestPermissionsResult(Activity context, @NonNull String[] permissions,
                                           @NonNull int[] grantResults, int REQUEST_PERMISSIONS_REQUEST_CODE) {
        ArrayList<String> permissionsToRequest = new ArrayList<>(Arrays.asList(permissions).subList(0, grantResults.length));
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    context,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}
