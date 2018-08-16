package com.neri.claiton.flashandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

        private ToggleButton toggleButton;

        private CameraManager mCameraManager;
        private String mCameraId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                // Verificar se o Flash está disponível
                boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

                if(!isFlashAvailable) {
                        showNoFlashError();
                }

                // Pegando o CameraManager e o CameraId
                mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                        mCameraId = mCameraManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                        e.printStackTrace();
                }

                toggleButton = findViewById(R.id.toggleButton);

                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                switchFlashLight(isChecked);
                        }
                });
        }

        private void switchFlashLight(boolean status) {
                try {
                        mCameraManager.setTorchMode(mCameraId, status);
                } catch (CameraAccessException e) {
                        e.printStackTrace();
                }
        }

        private void showNoFlashError() {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("ATENÇÃO!");
                alertDialog.setMessage("Flash não disponível neste dispositivo.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                finish();
                        }
                });
                alertDialog.show();
        }
}