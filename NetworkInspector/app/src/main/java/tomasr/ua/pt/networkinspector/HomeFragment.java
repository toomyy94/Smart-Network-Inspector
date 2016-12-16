package tomasr.ua.pt.networkinspector;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

import tomasr.ua.pt.networkinspector.modules.LocationCoord;

/**
 * @author Tomás Rodrigues (tomasrodrigues@ua.pt)
 *  Setember 2016
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private LocationCoord gps;

    public HomeFragment(LocationCoord gps) {
        this.gps=gps;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);

        //Get's
        ImageView no_coverage = (ImageView) view.findViewById(R.id.no_coverage);
        ImageView no_data = (ImageView) view.findViewById(R.id.no_data);
        ImageView slow_data = (ImageView) view.findViewById(R.id.slow_data);
        ImageView cannot_call = (ImageView) view.findViewById(R.id.cannot_call);
        ImageView dropped_call = (ImageView) view.findViewById(R.id.dropped_call);
        ImageView bad_quality_audio = (ImageView) view.findViewById(R.id.bad_quality_audio);

        //POST ONCLICK
        final String URL = "http://192.168.8.217:5011/location/point";

        no_coverage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Post params to be sent to the server
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("latitude", gps.getLatitude());
                params.put("longitude", gps.getLongitude());
                params.put("info", "no_coverage");

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Não foi possível armazenar os dados no nosso sistema!", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
                //Ver se entra aqui!
                Toast.makeText(getActivity(),"Obrigado pelo seu feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        no_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Post params to be sent to the server
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("latitude", gps.getLatitude());
                params.put("longitude", gps.getLongitude());
                params.put("info", "no_data");

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Não foi possível armazenar os dados no nosso sistema!", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
                //Ver se entra aqui!
                Toast.makeText(getActivity(),"Obrigado pelo seu feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        slow_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Post params to be sent to the server
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("latitude", gps.getLatitude());
                params.put("longitude", gps.getLongitude());
                params.put("info", "slow_data");

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Não foi possível armazenar os dados no nosso sistema!", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
                //Ver se entra aqui!
                Toast.makeText(getActivity(),"Obrigado pelo seu feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        cannot_call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Post params to be sent to the server
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("latitude", gps.getLatitude());
                params.put("longitude", gps.getLongitude());
                params.put("info", "cannot_call");

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Não foi possível armazenar os dados no nosso sistema!", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
                //Ver se entra aqui!
                Toast.makeText(getActivity(),"Obrigado pelo seu feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        dropped_call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Post params to be sent to the server
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("latitude", gps.getLatitude());
                params.put("longitude", gps.getLongitude());
                params.put("info", "dropped_call");

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Não foi possível armazenar os dados no nosso sistema!", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
                //Ver se entra aqui!
                Toast.makeText(getActivity(),"Obrigado pelo seu feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        bad_quality_audio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Post params to be sent to the server
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("latitude", gps.getLatitude());
                params.put("longitude", gps.getLongitude());
                params.put("info", "bad_quality_audio");

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Não foi possível armazenar os dados no nosso sistema!", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
                //Ver se entra aqui!
                Toast.makeText(getActivity(),"Obrigado pelo seu feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
