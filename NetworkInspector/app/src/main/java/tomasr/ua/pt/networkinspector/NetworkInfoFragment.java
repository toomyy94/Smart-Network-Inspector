package tomasr.ua.pt.networkinspector;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import tomasr.ua.pt.networkinspector.modules.LocationCoord;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author Tomás Rodrigues (tomasrodrigues@ua.pt)
 *  Setember 2016
 */

@SuppressLint("ValidFragment")
public class NetworkInfoFragment extends Fragment {

    private LocationCoord gps;

    //RF Parameters
    public ArrayList<String> wifi_info = new ArrayList<>();
    public ArrayList<String> lte_info = new ArrayList<>();
    public ArrayList<String> phone_info = new ArrayList<>();
    public String tac = "-1";
    public String rsrp = "-1";
    public String rsrq = "-1";
    public String mPci = "-1";

    public NetworkInfoFragment( ) {
    }

    public NetworkInfoFragment(LocationCoord gps) {
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
        View view = inflater.inflate(R.layout.fragment_network_info,
                container, false);

        //WIFI
        wifi_info = getCurrentSsid(getActivity().getBaseContext());

        ArrayAdapter<String> itemsWifi =
                new ArrayAdapter<String>(getActivity(), R.layout.wifi_items, wifi_info);
        ListView listWifi = (ListView) view.findViewById(R.id.list_wifi_info);
        listWifi.setAdapter(itemsWifi);

        //LTE
        lte_info = getLteInfo();

        ArrayAdapter<String> itemsLTE =
                new ArrayAdapter<String>(getActivity(), R.layout.wifi_items, lte_info);
        ListView listLTE = (ListView) view.findViewById(R.id.list_lte_info);
        listLTE.setAdapter(itemsLTE);

        //Phone
        phone_info = getPhoneInfo();

        ArrayAdapter<String> itemsPhone =
                new ArrayAdapter<String>(getActivity(), R.layout.wifi_items, phone_info);
        ListView listPhone = (ListView) view.findViewById(R.id.list_phone_info);
        listPhone.setAdapter(itemsPhone);



        return view;
    }

    public ArrayList<String> getLteInfo(){
        ArrayList<String> lte_info = new ArrayList<String>();

        TelephonyManager tm = (TelephonyManager)getActivity().getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        Log.i("all_lte_info",""+tm.getAllCellInfo().toString());
        if(tm.getAllCellInfo().toString().contains("CellIdentityLte")) {
            String[] s_tac = tm.getAllCellInfo().toString().split("mTac=");
            Log.i("s_tac", "" + s_tac);
            tac = s_tac[1].substring(0, 5);

            String[] s_rsrp = tm.getAllCellInfo().toString().split("rsrp=");
            rsrp = s_rsrp[1].substring(0, 4);

            String[] s_rsrq = tm.getAllCellInfo().toString().split("rsrq=");
            rsrq = s_rsrq[1].substring(0, 3);

            String[] s_cellID = tm.getAllCellInfo().toString().split("mPci=");
            mPci = s_cellID[1].substring(0, 1);

            lte_info.add("Cell ID: " + mPci);
            lte_info.add("Rsrp: " + rsrp + "dBm");
            lte_info.add("RSRQ: " + rsrq + "dBm");
            lte_info.add("LAC / TAC: " + tac);
        }
        else lte_info.add("No LTE Connectivity");

        return lte_info;
    }

    public ArrayList<String> getPhoneInfo(){
        ArrayList<String> phone_info = new ArrayList<String>();

        TelephonyManager tm = (TelephonyManager)getActivity().getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        //Operator e GPS
        phone_info.add("Country Iso: "+tm.getNetworkCountryIso());
        phone_info.add("MCC: "+tm.getNetworkOperator().substring(0,3));
        phone_info.add("MNC: "+tm.getNetworkOperator().substring(4));
        phone_info.add("Operator Name: "+tm.getNetworkOperatorName());
        phone_info.add("Lat: "+gps.getLatitude());
        phone_info.add("Lon: "+gps.getLongitude());

        //Memória
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        phone_info.add("Total memory: "+mi.totalMem/100000000+"Gb");
        phone_info.add("Available memory: "+mi.availMem/100000000+"Gb");

        //Bateria
//        BatteryManager batManager = (BatteryManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//        boolean isCharging = batManager.getIntProperty();
//        phone_info.add("");

        return phone_info;
    }

    public static ArrayList<String> getCurrentSsid(Context context) {
        ArrayList<String> wifi_info = new ArrayList<String>();

        String ssid = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }

        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
//                wifi_info.add(connectionInfo.getSSID());
//                wifi_info.add(connectionInfo.getBSSID());
//                wifi_info.add(String.valueOf(connectionInfo.getHiddenSSID()));
//                wifi_info.add(String.valueOf(connectionInfo.getMacAddress()));
//                wifi_info.add(String.valueOf(connectionInfo.getLinkSpeed()));
//                wifi_info.add(String.valueOf(connectionInfo.getNetworkId()));
//                wifi_info.add(String.valueOf(connectionInfo.getRssi()));
//                wifi_info.add(String.valueOf(connectionInfo.toString()));
                String[] parts = connectionInfo.toString().split(",");
                wifi_info.add(" "+parts[0]); //SSID
                wifi_info.add(parts[1]); //BSSID
                wifi_info.add(parts[2]); //MAC
                wifi_info.add(" Handshake State: "+parts[3].substring(18)); //HandShake Completed
                wifi_info.add(parts[4]+"dBm"); //Signal Strenght dbm
                wifi_info.add(parts[5]); //Speed Mbps
                wifi_info.add(parts[6]); //Frequency
            }
        }

        return wifi_info;
    }

}
