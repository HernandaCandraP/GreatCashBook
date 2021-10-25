package org.hernanda.cashbook;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.hernanda.cashbook.Helper.SQLiteHelper;

public class Beranda extends AppCompatActivity {
    private SQLiteHelper sqLiteHelper;
    private TextView pengeluaran, pemasukan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        sqLiteHelper = new SQLiteHelper(this);

        pengeluaran = findViewById(R.id.pengeluaran);
        pemasukan = findViewById(R.id.pemasukan);



        Integer countPengeluaran = sqLiteHelper.countDataPengeluaran();
        Integer countPemasukan = sqLiteHelper.countDataPemasukan();

        pemasukan.setText("Pemasukan: " + formatRupiah(Double.parseDouble(countPemasukan.toString())));
        pengeluaran.setText("Pengeluaran: " + formatRupiah(Double.parseDouble(countPengeluaran.toString())));


        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.yAxis(0).title("nominal");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();

        for (int i = 0; i < 20; i++)
        {
            seriesData.add(new CustomDataEntry(String.valueOf(11+i), (Math.random() * 1000000 + 50000), (Math.random() * 1000000 + 50000)));
        }


        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");


        Line series2 = cartesian.line(series2Mapping);
        series2.name("Pengeluaran");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Pendapatan");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value2, Number value3) {
            super(x, value2);
            setValue("value3", value3);
        }

    }


    public void tambahPemasukan(View view){
        startActivity(new Intent(Beranda.this, TambahPemasukan.class));
    }

    public void tambahPengeluaran(View view){
        startActivity(new Intent(Beranda.this, TambahPengeluaran.class));
    }

    public void detail(View view){
        startActivity(new Intent(Beranda.this, Detail.class));
    }

    public void pengaturan(View view){
        startActivity(new Intent(Beranda.this, Pengaturan.class));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Great Cash Book")
                .setMessage("apakah ingin keluar aplikasi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }



}
