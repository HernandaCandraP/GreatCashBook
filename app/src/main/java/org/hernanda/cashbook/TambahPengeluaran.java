package org.hernanda.cashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.hernanda.cashbook.Helper.MoneyTextWatcher;
import org.hernanda.cashbook.Helper.SQLiteHelper;

import java.util.Calendar;

import static org.hernanda.cashbook.TambahPemasukan.DATE_PICKER_ID;

public class TambahPengeluaran extends AppCompatActivity {

    private EditText tanggal, nominal, keterangan;
    private Button saveBtn, backBtn;
    private SQLiteHelper sqLiteHelper;
    private ImageButton changeDate;
    Intent intent;

    private int year;
    private int month;
    private int day;

    static final int DATE_PICKER_ID = 1111;

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

        changeDate = findViewById(R.id.button_date);
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        //        set nominal rupiah
        nominal.addTextChangedListener(new MoneyTextWatcher(nominal));

        tanggal.setEnabled(false);
        tanggal.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));

        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gettgl = tanggal.getText().toString();
                String getnominalstr = MoneyTextWatcher.parseCurrencyValue(nominal.getText().toString()).toString();
                String getket = keterangan.getText().toString();
                String status = "==>>";
                String simbol = "[ - ]";

                if(TextUtils.isEmpty(gettgl) || TextUtils.isEmpty(getnominalstr) || TextUtils.isEmpty(getket)){
                    Toast.makeText(TambahPengeluaran.this,"Isikan Data dengan Lengkap!", Toast.LENGTH_LONG).show();
                }else{
                    sqLiteHelper.insertKeuangan(simbol,gettgl, Integer.valueOf(getnominalstr), getket, status);
                    intent = new Intent(TambahPengeluaran.this,Beranda.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            tanggal.setText(new StringBuilder().append(day)
                    .append("-").append(month + 1).append("-").append(year)
                    .append(" "));

        }
    };
}
