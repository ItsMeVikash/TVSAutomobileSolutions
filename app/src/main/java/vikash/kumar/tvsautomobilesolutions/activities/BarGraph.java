package vikash.kumar.tvsautomobilesolutions.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import vikash.kumar.tvsautomobilesolutions.R;
import vikash.kumar.tvsautomobilesolutions.model_pojo.DataModel;

public class BarGraph extends AppCompatActivity {

    private BarChart chart;
    private PieChart pieChart;
    private ArrayList<DataModel> arrayList;
    private Button pieButton,barButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        Toast.makeText(this, "Rotate your screen for better understandings ", Toast.LENGTH_LONG).show();
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList=new ArrayList<>();
        if(MainActivity.staffNameArrayList!=null)
            arrayList=MainActivity.staffNameArrayList;

        chart = (BarChart) findViewById(R.id.barchart);

        ArrayList salary = new ArrayList();
        ArrayList name = new ArrayList();
        //Pie chart
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();


        for (int i=0;i<10;i++){
            String str1=arrayList.get(i).getSalary().replace("$","");
            String str2=str1.replaceAll(",","");
            salary.add(new BarEntry(Integer.parseInt(str2), i));
            yvalues.add(new Entry(Integer.parseInt(str2), i));
            name.add(arrayList.get(i).getName());
            xVals.add(arrayList.get(i).getName());
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);
        xAxis.setSpaceBetweenLabels(0);
        BarDataSet bardataset = new BarDataSet(salary, "Salary Of Employee");
        xAxis.setLabelsToSkip(0);
        xAxis.setTextSize(2);
        chart.animateY(000);
        BarData data = new BarData(name, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(false);
        PieDataSet dataSet = new PieDataSet(yvalues, "Salary Of Employee");
        PieData datas = new PieData(xVals, dataSet);
        data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(datas);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieButton=findViewById(R.id.pieButton);
        barButton=findViewById(R.id.barButton);
        pieButton.setVisibility(View.GONE);

    }
    //for bar chart
    public void changeChart(View view){
        pieChart.setVisibility(View.GONE);
        chart.setVisibility(View.VISIBLE);
        pieButton.setVisibility(View.VISIBLE);
        barButton.setVisibility(View.GONE);
    }
    //for pie chart
    public void changeCharts(View view){
        chart.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);
        pieButton.setVisibility(View.GONE);
        barButton.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
