package com.cityu.ast.towngasproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    Button btnStartHome, btnEnd;
    static final int CAM_REQUEST = 1;
    private LocationManager locationManager;
    private LocationListener listener;
    private Double longitude, latitude;
    Geocoder geocoder;
    List<Address> addresses;
    View dialogView = null;
    AlertDialog b = null;
    AlertDialog.Builder dialogBuilder;
    TextView tvDate, tvTime, tvLocation, tvGpsSignal;
    Button btnRefresh, btnOk;
    Criteria criteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        dialogView = inflater.inflate(R.layout.custom_dialog_gps, null, false);

        getSupportActionBar().hide();

        btnStartHome = (Button) findViewById(R.id.btnStartHome);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        tvDate = (TextView) dialogView.findViewById(R.id.tvDate);
        tvTime = (TextView) dialogView.findViewById(R.id.tvTime);
        tvGpsSignal = (TextView) dialogView.findViewById(R.id.tvGpsSignal);
        tvLocation = (TextView) dialogView.findViewById(R.id.tvLocation);
        btnRefresh = (Button) dialogView.findViewById(R.id.btnRefresh);
        btnOk = (Button) dialogView.findViewById(R.id.btnOk);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();

                tvTime.setText(DateFormat.getTimeInstance().format(location.getTime()));
                tvDate.setText(DateFormat.getDateInstance().format(location.getTime()));
                tvGpsSignal.setText("好");

                configure_button();

                if (latitude != null || longitude != null) {
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        String address = addresses.get(0).getAddressLine(0);
                        String area = addresses.get(0).getAdminArea();
                        String city = addresses.get(0).getCountryName();

                        String fullAddress = address + ", " + area + ", " + city;
                        tvLocation.setText(fullAddress);
                    } catch (IOException e) {
                    }
                }


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };

        btnStartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
                configure_button();
            }

        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configure_button();
            }

        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvLocation.getText() == "") {
                    Toast.makeText(dialogView.getContext(), "等待信號...", Toast.LENGTH_LONG).show();
                } else {
                    b.dismiss();
                    Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }


    void configure_button() {
        // first check for permissions
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                                , 10);
                    }
                }

                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean statusOfNetwork = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!statusOfGPS && !statusOfNetwork ) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }

                if (statusOfGPS) {
                    locationManager.requestLocationUpdates("gps", 1000*2, 0, listener);
                } else if (statusOfNetwork) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        locationManager.requestSingleUpdate(criteria, listener, null);
                    }
                }

            }
        });

            return;
        }

    public void showChangeLangDialog() {

        if (b == null) {
            dialogBuilder.setView(dialogView);
            b = dialogBuilder.create();
        }

        // Clear all the text on the dialog
        b.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                tvLocation.setText("");
                tvTime.setText("");
                tvDate.setText("");
                tvGpsSignal.setText("");
            }
        });

        b.show();
    }




}
