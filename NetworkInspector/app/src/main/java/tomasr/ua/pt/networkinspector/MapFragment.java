package tomasr.ua.pt.networkinspector;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import tomasr.ua.pt.networkinspector.modules.LocationCoord;
import tomasr.ua.pt.networkinspector.modules.MarkerInfo;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;


/**
 * Tomás Rodrigues (tomasrodrigues@ua.pt)
 * @date Setember 2016
 */

@SuppressLint("ValidFragment")
public class MapFragment extends Fragment implements OnMapReadyCallback {

    //Loading
    ProgressBar progress_bar;
    TextView progress_text;

    //On Map View
    private GoogleMap mMap;
    private List<Marker> mMarkers = new ArrayList<>();
    private LocationCoord gps;

    //Gets
    List<MarkerInfo> marker_info = new ArrayList<MarkerInfo>();
    List<Integer> ids = new ArrayList();

    public MapFragment() {
    }
    public MapFragment(LocationCoord gps) {
        this.gps = gps;
    }
    public MapFragment(LocationCoord gps, List<MarkerInfo> marker_info) {
        this.gps = gps;
        this.marker_info = marker_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        progress_bar = (ProgressBar) v.findViewById(R.id.progress_bar);
        progress_text = (TextView) v.findViewById(R.id.progress_text);
        progress_bar.setVisibility(View.VISIBLE);
        progress_text.setVisibility(View.VISIBLE);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        if(marker_info==null || marker_info.size()==0){
            progress_bar = (ProgressBar) getView().findViewById(R.id.progress_bar);
            progress_text = (TextView) getView().findViewById(R.id.progress_text);
            progress_bar.setVisibility(View.VISIBLE);
            progress_text.setVisibility(View.VISIBLE);
            progress_text.setText("Updating Map...");
            SystemClock.sleep(1500);
            progress_bar.setVisibility(View.GONE);
            progress_text.setVisibility(View.GONE);
        }

        Log.i("marker_info",""+marker_info.get(0).getInfo());

        for(int i=0; i< marker_info.size(); i++) {
            addMarker(marker_info.get(i).getLat(), marker_info.get(i).getLon(), marker_info.get(i).getInfo());
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 8));
    }


    public void addMarker(Double lat, Double lon, String info) {
        float cor = HUE_YELLOW;
        switch (info){
            case "no_coverage":
                cor = HUE_ROSE;
                break;
            case "bad_quality_audio":
                cor = HUE_VIOLET;
                break;
            case "no_data":
                cor = HUE_ORANGE;
                break;
            case "dropped_call":
                cor = HUE_BLUE;
                break;
            case "slow_data":
                cor = HUE_GREEN;
                break;
            case "cannot_call":
                cor = HUE_MAGENTA;
                break;
        }

        mMarkers.add(mMap.addMarker(new MarkerOptions().position(
                new LatLng(lat, lon)).
                title(""+info).icon(BitmapDescriptorFactory.
                defaultMarker(cor))));
    }

    public double distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (double) (earthRadius * c);

        return dist;
    }

    private class GETMarkers extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

//            StringBuilder result = new StringBuilder();
//            try {
//                URL url = new URL(urls[0]);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    result.append(line);
//                }
//                rd.close();
//
//                String resultado = result.toString();
//                JSONArray jArray = new JSONArray(resultado);
//
//                for (int i=0; i < jArray.length(); i++) {
//
//                    JSONObject oneObject = jArray.getJSONObject(i);
//
//                    // Pulling items from the Objects
//                    int d_id = oneObject.getInt("id");
//                    double d_latitude = oneObject.getDouble("latitude");
//                    double d_longitude = oneObject.getDouble("longitude");
//                    String d_info = oneObject.getString("info");
//
//                    //Add to the list
//                    marker_info.add(i, d_id, d_latitude, d_latitude, d_info);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
            return "";
        }

        protected void onPostExecute(Boolean result) {

        }

    }


}
