package com.fortuna.rgpmobile.hitung;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.dashboard.DashboardActivity;
import com.fortuna.rgpmobile.lapor.LaporActivity;
import com.fortuna.rgpmobile.util.Util;

import java.math.BigDecimal;

public class HitungSuaraActivity extends BaseActivity {

    Toolbar toolbar_hitung_suara;
    TextView number_prasan, number_tidak_sah, suaraLawan;
    EditText total_jumlah_suara;
    Button decrease_prasan, increase_prasan, decrease_tidak_sah, increase_tidak_sah, btnGenerate, btnKirim;

    int intPrasan = 0;
    int intTidakSah = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_hitung_suara);

        setupToolbar();
        initComponent();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_hitung_suara = findViewById(R.id.toolbar_hitung_suara);
        setSupportActionBar(toolbar_hitung_suara);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void initComponent() {

        number_prasan = findViewById(R.id.number_prasan);
        number_tidak_sah = findViewById(R.id.number_tidak_sah);
        suaraLawan = findViewById(R.id.suaraLawan);

        total_jumlah_suara = findViewById(R.id.total_jumlah_suara);
        total_jumlah_suara.setSelection(total_jumlah_suara.getText().length());

        decrease_prasan = findViewById(R.id.decrease_prasan);
        decrease_prasan.setOnClickListener(new DecreasePrasanClickHandler());
        increase_prasan = findViewById(R.id.increase_prasan);
        increase_prasan.setOnClickListener(new IncreasePrasanClickHandler());
        decrease_tidak_sah = findViewById(R.id.decrease_tidak_sah);
        decrease_tidak_sah.setOnClickListener(new DecreaseTidakSahClickHandler());
        increase_tidak_sah = findViewById(R.id.increase_tidak_sah);
        increase_tidak_sah.setOnClickListener(new IncreaseTidakSahClickHandler());
        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(new GenerateClickHandler());
        btnKirim = findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(new KirimClickHandler());

    }


    private class DecreasePrasanClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (intPrasan > 0) {
                intPrasan = intPrasan - 1;
                displayPrasan(intPrasan);
            } else {
                Util.showmsg(HitungSuaraActivity.this, null, "Jumlah Suara Tidak Bisa Negative");
            }

        }
    }

    private class IncreasePrasanClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            int jmlPem = Integer.valueOf(total_jumlah_suara.getText().toString());

            if (jmlPem < 1) {

                Util.showmsg(HitungSuaraActivity.this, null, "Total Pemilih Belum di Input");

            } else {

                intPrasan = intPrasan + 1;
                displayPrasan(intPrasan);

            }

        }
    }

    @SuppressLint("SetTextI18n")
    private void displayPrasan(int number) {
        number_prasan.setText("" + number);
    }


    private class DecreaseTidakSahClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (intTidakSah > 0) {
                intTidakSah = intTidakSah - 1;
                displayTidakSah(intTidakSah);
            } else {
                Util.showmsg(HitungSuaraActivity.this, null, "Jumlah Suara Tidak Bisa Negative");
            }

        }
    }

    private class IncreaseTidakSahClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            int jmlPem = Integer.valueOf(total_jumlah_suara.getText().toString());

            if (jmlPem < 1) {

                Util.showmsg(HitungSuaraActivity.this, null, "Total Pemilih Belum di Input");

            } else {

                intTidakSah = intTidakSah + 1;
                displayTidakSah(intTidakSah);

            }

        }
    }

    @SuppressLint("SetTextI18n")
    private void displayTidakSah(int number) {
        number_tidak_sah.setText("" + number);
    }


    private class GenerateClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            int jmlPem = Integer.valueOf(total_jumlah_suara.getText().toString());

            if (jmlPem < 1) {

                Util.showmsg(HitungSuaraActivity.this, null, "Total Pemilih Belum di Input");

            } else {

                BigDecimal totalPemilih = new BigDecimal(total_jumlah_suara.getText().toString());
                BigDecimal suaraPrasan = new BigDecimal(number_prasan.getText().toString());
                BigDecimal suaraTidakSah = new BigDecimal(number_tidak_sah.getText().toString());

                BigDecimal suaraJM = totalPemilih.subtract(suaraPrasan).subtract(suaraTidakSah);

                suaraLawan.setText(String.valueOf(suaraJM));

            }

        }
    }

    private class KirimClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Util.showmsg(HitungSuaraActivity.this, null, "Saat ini Belum Bisa Mengirim Hasil");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_global, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
