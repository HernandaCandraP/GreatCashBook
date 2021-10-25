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

public class TambahPengeluaran extends AppCompatActivity {

    private EditText tanggal, nominal, keterangan;
    private Button saveBtn, backBtn;
    private SQLiteHelper sqLiteHelper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengeluaran);
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
                String getnominalstr = nominal.getText().toString();
                String getket = keterangan.getText().toString();
                String status = "pengeluaran";
                String simbol = "[-]";

                if(TextUtils.isEmpty(gettgl) || TextUtils.isEmpty(getnominalstr) || TextUtils.isEmpty(getket)){
                    Toast.makeText(TambahPengeluaran.this,"Isikan Data dengan Lengkap!", Toast.LENGTH_LONG).show();
                }else{
                    sqLiteHelper.insertKeuangan(simbol,gettgl, Integer.valueOf(getnominalstr), getket, status);
                    intent = new Intent(TambahPengeluaran.this,Beranda.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Berhasil Menambah Pengeluaran",Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahPengeluaran.this, Beranda.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void selectDate(View view) {
    }
}
