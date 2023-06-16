package com.nikolasgilangwisnuadi_202102362.mid_202102362;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nim, nama, jeniskelamin, alamat, email;
    Button simpan, tampil, hapus, edit;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nim = findViewById(R.id.edtnim);
        nama = findViewById(R.id.edtnama);
        jeniskelamin = findViewById(R.id.edtjk);
        alamat = findViewById(R.id.edtalamat);
        email = findViewById(R.id.edtemail);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        hapus = findViewById(R.id.btnhapus);
        edit = findViewById(R.id.btnedit);
        db = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ni = nim.getText().toString();
                String nma = nama.getText().toString();
                String jk = jeniskelamin.getText().toString();
                String almt = alamat.getText().toString();
                String em = email.getText().toString();

                if (TextUtils.isEmpty(ni) || TextUtils.isEmpty(nma) || TextUtils.isEmpty(jk)
                        || TextUtils.isEmpty(almt) || TextUtils.isEmpty(em)) {
                    Toast.makeText(MainActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    boolean checkkode = db.checkNim(ni);
                    if (!checkkode) {
                        boolean insert = db.insertData(ni, nma, jk, almt, em);
                        if (insert) {
                            Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity to prevent going back to it when pressing back button
                        } else {
                            Toast.makeText(MainActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = nim.getText().toString();
                Boolean cekHapusData = db.hapusData(kb);
                if (cekHapusData == true)
                    Toast.makeText(MainActivity.this, "Data Berhasil Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilData();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("NIM Mahasiswa: " + res.getString(0) + "\n");
                    buffer.append("Nama Mahasiswa: " + res.getString(1) + "\n");
                    buffer.append("Jenis Kelamin: " + res.getString(2) + "\n");
                    buffer.append("Alamat: " + res.getString(3) + "\n");
                    buffer.append("Email: " + res.getString(4) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ni = nim.getText().toString();
                String nma = nama.getText().toString();
                String jk = jeniskelamin.getText().toString();
                String almt = alamat.getText().toString();
                String em = email.getText().toString();

                if (TextUtils.isEmpty(ni) || TextUtils.isEmpty(nma) || TextUtils.isEmpty(jk)
                        || TextUtils.isEmpty(almt) || TextUtils.isEmpty(em)) {
                    Toast.makeText(MainActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    boolean edit = db.editData(ni, nma, jk, almt, em);
                    if (edit) {
                        Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish(); // Close the current activity to prevent going back to it when pressing back button
                    } else {
                        Toast.makeText(MainActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
