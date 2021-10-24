package org.hernanda.cashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.hernanda.cashbook.Helper.SQLiteHelper;

public class TambahPemasukan extends AppCompatActivity {

    private EditText tanggal, nominal, keterangan;
    private Button saveBtn, backBtn;
    private SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pemasukan);

        saveBtn = findViewById(R.id.simpan);
        backBtn = findViewById(R.id.kembali);
        tanggal = findViewById(R.id.tanggal);
        nominal = findViewById(R.id.nominal);
        keterangan = findViewById(R.id.keterangan);
        sqLiteHelper = new SQLiteHelper(this);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gettgl = tanggal.getText().toString();
                String getnominal = nominal.getText().toString();
                String getket = keterangan.getText().toString();

                if(TextUtils.isEmpty(gettgl) || TextUtils.isEmpty(getnominal) || TextUtils.isEmpty(getket)){
                    Toast.makeText(TambahPemasukan.this,"Isikan Data dengan Lengkap!", Toast.LENGTH_LONG).show();
                }else{




                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahPemasukan.this, Beranda.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void selectDate(View view) {
    }
}
