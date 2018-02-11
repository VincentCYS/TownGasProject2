package com.cityu.ast.towngasproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
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
    ProgressDialog pDialog;
    String gpsStatus = "";

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


                // o < x < 25 good
                // 25 <= x <= 50 ok
                // x > 50 bad
                int gpsAccuracy = (int) location.getAccuracy();

                if(gpsAccuracy > 0 ) {
                    if (gpsAccuracy < 25) {
                        gpsStatus = "好";
                    } else if (gpsAccuracy <= 50) {
                        gpsStatus = "普通";
                    } else {
                        gpsStatus = "差";
                    }
                }

                tvGpsSignal.setText(gpsStatus + " ");
                pDialog.dismiss();

               // configure_button();

                if (latitude != null || longitude != null) {
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        String address = addresses.get(0).getAddressLine(0);
                        String area = addresses.get(0).getAdminArea();
                        String city = addresses.get(0).getCountryName();

                        String fullAddress = address + ", " + area + ", " + city;
                        tvLocation.setText(fullAddress);
                        tvTime.setText(DateFormat.getTimeInstance().format(location.getTime()));
                        tvDate.setText(DateFormat.getDateInstance().format(location.getTime()));
                        pDialog.dismiss();
                    } catch (Exception e) {
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
                tvLocation.setText("");
                tvTime.setText("");
                tvDate.setText("");
                tvGpsSignal.setText("");

                configure_button();
            }

        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvLocation.getText() != "") {
                   // Toast.makeText(dialogView.getContext(), "等待信號...", Toast.LENGTH_LONG).show();
                    b.dismiss();
                    tvLocation.setText("");
                    tvTime.setText("");
                    tvDate.setText("");
                    tvGpsSignal.setText("");
                    Intent intent = new Intent(HomeActivity.this, StartWorkActivity.class);
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
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE}
                                , 10);
                    }
                }

                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean statusOfNetwork = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!statusOfGPS && !statusOfNetwork ) {
                    new AlertDialog.Builder(HomeActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                            .setTitle("請開啟定位服務(GPS)\n")
                            .setMessage("請於設定=>位置=>開啟定位服務(GPS)")
                            .setCancelable(false)
                            // Positive button event
                            .setPositiveButton(
                                    "確定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivity(i);
                                            dialog.cancel();
                                        }

                                        // Negative button event
                                    }).setNegativeButton(
                            "取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
//                                    pDialog.cancel();
                                }
                            }).create().show();
                } else {
                    pDialog = new ProgressDialog(HomeActivity.this);
                    pDialog.setMessage("載入中...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);  // set to false
                    pDialog.show();

                    new CountDownTimer(20000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            pDialog.dismiss();
                            tvGpsSignal.setText("沒有信號");
                        }
                    }.start();

                    if (statusOfGPS) {
                        locationManager.requestLocationUpdates("gps", 1000*20, 0, listener);
                        // locationManager.requestSingleUpdate("gps",listener, null);

                    } else if (statusOfNetwork) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                            locationManager.requestSingleUpdate(criteria, listener, null);
                        }
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
