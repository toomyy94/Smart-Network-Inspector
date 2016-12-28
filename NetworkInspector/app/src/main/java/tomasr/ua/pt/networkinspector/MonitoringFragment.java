package tomasr.ua.pt.networkinspector;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.communication.IOnPointFocusedListener;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tomasr.ua.pt.networkinspector.modules.ChartFragment;


/**
 * @author Rui Oliveira (ruipedrooliveira@ua.pt) & Tomás Rodrigues (tomasrodrigues@ua.pt)
 *  Junho 2016
 */

@SuppressLint("ValidFragment")
public class MonitoringFragment extends ChartFragment {

    private PieChart chartPie;
    private ValueLineChart slowPie;
    List<String> hora, info, hora_slow_data, info_slow_data;
    ArrayList<String> info_mes = new ArrayList<String>();

    int no_coverage = 0;
    int no_data = 0;
    int slow_data = 0;
    int bad_quality = 0;
    int dropped_call = 0;
    int cannot_call = 0;

    public MonitoringFragment(List<String> hora, List<String> info) {
        this.hora=hora;
        this.info=info;
    }

    public MonitoringFragment(List<String> hora, List<String> info, List<String> hora_slow_data, List<String> info_slow_data) {
        this.hora=hora;
        this.info=info;
        this.hora_slow_data=hora_slow_data;
        this.info_slow_data=info_slow_data;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //chartTemp.startAnimation();
        slowPie.startAnimation();
    }

    @Override
    public void restartAnimation() {
        //chartTemp.startAnimation();
        slowPie.startAnimation();
    }

    @Override
    public void onReset() {
        //chartTemp.resetZoom(true);
        slowPie.resetZoom(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monitoring, container, false);

        TextView tx = (TextView) view.findViewById(R.id.distr);
        tx.clearComposingText();

        //Data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        String mes = currentDateandTime.substring(4,6);
        String dia = currentDateandTime.substring(6,8);

        //Pie Chart - Gráfico circular
        PieChart chartPie = (PieChart) view.findViewById(R.id.temp);

        for(int i=0; i<info.size();i++){
            if(mes.equals(hora.get(i).substring(5,7))) {
                info_mes.add(info.get(i));
            }
        }

        for(int i=0; i<info_mes.size();i++){
            switch (info_mes.get(i)){
                case "bad_quality":
                    bad_quality+=1;
                    break;
                case "dropped_call":
                    dropped_call+=1;
                    break;
                case "cannot_call":
                    cannot_call+=1;
                    break;
                case "no_data":
                    no_data+=1;
                    break;
                case "no_coverage":
                    no_coverage+=1;
                    break;
                case "slow_data":
                    slow_data+=1;
                    break;

            }
        }
        chartPie.addPieSlice(new PieModel("No Data", no_data, Color.parseColor("#FE6DA8")));
        chartPie.addPieSlice(new PieModel("Slow Data", slow_data, Color.parseColor("#56B7F1")));
        chartPie.addPieSlice(new PieModel("No Coverage", no_coverage, Color.parseColor("#CDA67F")));
        chartPie.addPieSlice(new PieModel("Cannot Call", cannot_call, Color.parseColor("#FED70E")));
        chartPie.addPieSlice(new PieModel("Dropped Call", dropped_call, Color.parseColor("#422701")));
        chartPie.addPieSlice(new PieModel("Bad Quality Audio", bad_quality, Color.parseColor("#839502")));

        chartPie.startAnimation();

        //Grafico de linhas - Hora/Slow_data
        slowPie = (ValueLineChart) view.findViewById(R.id.slow_data);

        ValueLineSeries seriesSlow = new ValueLineSeries();
        seriesSlow.setColor(Color.RED);

        ArrayList<String> deHoras = new ArrayList<>();
        for (int j=0; j<24;j++){
            deHoras.add(j+":00");
        }

        //Slow Datas divididos por horas para o dia de hoje
        Integer deSlowData[] = new Integer[24];

        for (int i = 0; i < deSlowData.length; i++) {
            deSlowData[i] = 0;
            for (int j = 0; j < hora_slow_data.size(); j++) {
                if(i>9) {
                    if (hora_slow_data.get(j).substring(11, 13).equals(String.valueOf(i)) && dia.equals(hora_slow_data.get(j).substring(8,10)))
                        deSlowData[i]++;
                }
                else{
                    if (hora_slow_data.get(j).substring(11, 13).equals(String.valueOf("0"+i))&& dia.equals(hora_slow_data.get(j).substring(8,10)))
                        deSlowData[i]++;
                }

            }
        }


        for (int i = 0; i < deHoras.size(); i++) {
            Log.i("deSlowData",""+deSlowData[i]);
            seriesSlow.addPoint(new ValueLinePoint(deHoras.get(i), deSlowData[i]));
        }


        slowPie.addSeries(seriesSlow);
        slowPie.addStandardValue(0);
        slowPie.addStandardValue(2);
        slowPie.addStandardValue(4);
        slowPie.setOnPointFocusedListener(new IOnPointFocusedListener() {
            @Override
            public void onPointFocused(int _PointPos) {
                Log.d("Test", "Pos: " + _PointPos);
            }
        });


        return view;
    }


}
