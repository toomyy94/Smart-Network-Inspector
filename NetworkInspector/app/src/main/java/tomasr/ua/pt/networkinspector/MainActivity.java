package tomasr.ua.pt.networkinspector;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tomasr.ua.pt.networkinspector.modules.LocationCoord;
import tomasr.ua.pt.networkinspector.modules.MarkerInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Navigation Menu
    private NavigationView navigationView = null;
    private Toolbar toolbar = null;
    private LocationCoord gps = null;
    private static final int PERMISSION_REQUEST_CODE = 1;

    //Gets
    List<MarkerInfo> marker_info = new ArrayList<MarkerInfo>();

    //Graph
    List<String> hora = new ArrayList<String>();
    List<String> info = new ArrayList<String>();
    List<String> hora_slow_data = new ArrayList<String>();
    List<String> info_slow_data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Wifi Manage
        WifiManager wifi;
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);//Turn on Wifi

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //GPS Manage
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        gps = new LocationCoord(this);

        //GET in TIMER
        callAsynchronousTask();

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Allow ImHere to access this device's location?");
            dialog.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Deny", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void callAsynchronousTask(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            PerformBackgroundTask performBackgroundTask = new PerformBackgroundTask();
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            performBackgroundTask.execute();

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 50000 ms
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        //fragmetn initially
        HomeFragment fragment = new HomeFragment(gps);
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        // permission android 6.0
        if (!checkPermission()) {
            requestPermission();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Log.i("home","home");

            HomeFragment fragment = new HomeFragment(gps);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_network_info) {
            Log.i("nav_network_info","nav_network_info");

            NetworkInfoFragment fragment = new NetworkInfoFragment(gps);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_map) {
            Log.i("map","map");

            MapFragment fragment = new MapFragment(gps, marker_info);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_monitoring) {

            MonitoringFragment fragment = new MonitoringFragment(hora, info, hora_slow_data, info_slow_data);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_tests) {

            Intent i = new Intent(getBaseContext(), TestsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "My location sends via Network Inspector\nhttp://maps.google.com/maps?q=" + gps.getLatitude() + "," + gps.getLongitude());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_about) {

            AboutFragment fragment = new AboutFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class PerformBackgroundTask extends AsyncTask<String, Void, List<MarkerInfo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<MarkerInfo> doInBackground(String... urls) {

            //      HTTP GET MARKERS
            String URL_Get_Markers = "https://rm-backend.herokuapp.com/api/backend/info/all/";
            marker_info.clear();

            StringRequest stringRequest = new StringRequest(URL_Get_Markers,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try{
                                JSONObject object = new JSONObject(response);
                                JSONArray jArray = object.getJSONArray("results");


                                for (int i=0; i < jArray.length(); i++) {

                                    JSONObject oneObject = jArray.getJSONObject(i);

                                    // Pulling items from the Objects
                                    Integer id = oneObject.getInt("id");
                                    Double latitude = Double.parseDouble(oneObject.getString("lat"));
                                    Double longitude = Double.parseDouble(oneObject.getString("lon"));
                                    String info = oneObject.getString("info");
                                    String msg_time = oneObject.getString("msg_time");

                                    MarkerInfo markerInfo = new MarkerInfo(id,latitude,longitude,info,msg_time);
                                    marker_info.add(markerInfo);
                                }

                                for (int i=0; i < jArray.length(); i++) {
                                    //array de horas dos slow_datas
                                    if(marker_info.get(i).getInfo().equals("slow_data")){
                                        info_slow_data.add(marker_info.get(i).getInfo());
                                        hora_slow_data.add(marker_info.get(i).getMsg_time());
                                    }
                                    //array de infos e horas completos
                                    info.add(marker_info.get(i).getInfo());
                                    hora.add(marker_info.get(i).getMsg_time());
                                }

                            }catch (JSONException e){
                                e.printStackTrace();
                                Log.e("erro","erro no try do JSON");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("erro","erro no get");
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
            requestQueue.add(stringRequest);

            return marker_info;
        }

        protected void onPostExecute(Boolean result) {

        }

    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) return true;
        else return false;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }
}
