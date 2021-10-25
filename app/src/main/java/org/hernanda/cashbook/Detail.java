package org.hernanda.cashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.hernanda.cashbook.Helper.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Detail extends AppCompatActivity {
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        backBtn = findViewById(R.id.kembali);

        final SQLiteHelper myDb = new SQLiteHelper(this);
        final Cursor myData = myDb.SelectAllData();

        // listView1
        ListView lisView1 = (ListView)findViewById(R.id.listView1);

        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(Detail.this, R.layout.list_row, myData
                ,new String[] { myDb.Table_Column_Simbol, myDb.Table_Column_Tanggal,myDb.Table_Column_Nominal,myDb.Table_Column_Keterangan, myDb.Table_Column_Status}
                ,new int[] {R.id.simbol, R.id.tanggal, R.id.nominal, R.id.keterangan, R.id.status});

        lisView1.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail.this, Beranda.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
