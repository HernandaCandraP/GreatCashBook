package org.hernanda.cashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.hernanda.cashbook.Helper.SQLiteHelper;

public class Login extends AppCompatActivity {

    Button LogInButton;
    EditText Email, Password ;
    String UsernameHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogInButton = findViewById(R.id.login);
        Email = findViewById(R.id.username);
        Password = findViewById(R.id.password);

        sqLiteHelper = new SQLiteHelper(this);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextStatus();      // cek apakah text kosong
                LoginFunction();        // fungsi login
            }
        });

    }

    public void LoginFunction(){
        if(EditTextEmptyHolder) {

            // permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // cek username di sqlite.
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_LOGIN, null, " " + SQLiteHelper.Table_Column_Username + "=?", new String[]{UsernameHolder}, null, null, null);

            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();

                    // cek password apakah sesuai dengan email
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_Password));
                    cursor.close();
                }
            }
            CheckFinalResult();
        }
        else {
            // jika salah satu input text kosong.
            Toast.makeText(Login.this,"Silakan Masukkan Username atau Password.", Toast.LENGTH_LONG).show();
        }
    }

    // cek apakah ada isi pada input text
    public void CheckEditTextStatus(){

        UsernameHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // cek EditText kosong / tidak
        if( TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        } else {
            EditTextEmptyHolder = true ;
        }
    }

    // cek apakah username dan password sesuai
    public void CheckFinalResult(){
        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            Toast.makeText(Login.this,"Login Berhasil",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Login.this, Beranda.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(Login.this,"Username dan Password Salah, Silakan Coba Lagi.",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;
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
}
