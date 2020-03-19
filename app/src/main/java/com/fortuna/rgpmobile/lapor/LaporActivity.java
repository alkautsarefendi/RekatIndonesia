package com.fortuna.rgpmobile.lapor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fortuna.rgpmobile.login.LoginActivity;
import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.main.RPGValidator;
import com.fortuna.rgpmobile.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LaporActivity extends BaseActivity {

    Toolbar toolbar_lapor;
    Button btnLapor;
    EditText txtJudulLaporan, txtDescKejadian;
    RadioGroup radio_group;
    RadioButton radio_button;

    SharedPreferences lp;

    String jns_kej;

    public String getJns_kej() {
        return jns_kej;
    }

    public void setJns_kej(String jns_kej) {
        this.jns_kej = jns_kej;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_lapor);

        setupToolbar();
        initComponent();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_lapor = findViewById(R.id.toolbar_lapor);
        setSupportActionBar(toolbar_lapor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void initComponent() {

        btnLapor = findViewById(R.id.btnLapor);
        btnLapor.setOnClickListener(new ButtonLaporClickHandler());

        txtJudulLaporan = findViewById(R.id.txtJudulLaporan);
        txtDescKejadian = findViewById(R.id.txtDescKejadian);

        radio_group = findViewById(R.id.radio_group);
        radio_group.setOnCheckedChangeListener(new RadioGroupCheckCLick());

        lp = getSharedPreferences(LoginActivity.LoginPref, Context.MODE_PRIVATE);

    }


    private class RadioGroupCheckCLick implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.radioButton1) {

                int selectedId = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(selectedId);
                setJns_kej(radio_button.getText().toString());
                System.out.println("Value RB = " + getJns_kej());

            } else if (checkedId == R.id.radioButton2) {

                int selectedId = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(selectedId);
                setJns_kej(radio_button.getText().toString());
                System.out.println("Value RB = " + getJns_kej());

            } else if (checkedId == R.id.radioButton3) {

                int selectedId = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(selectedId);
                setJns_kej(radio_button.getText().toString());
                System.out.println("Value RB = " + getJns_kej());

            } else if (checkedId == R.id.radioButton4) {

                int selectedId = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(selectedId);
                setJns_kej(radio_button.getText().toString());
                System.out.println("Value RB = " + getJns_kej());

            } else if (checkedId == R.id.radioButton5) {

                int selectedId = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(selectedId);
                setJns_kej(radio_button.getText().toString());
                System.out.println("Value RB = " + getJns_kej());

            }

        }
    }

    private class ButtonLaporClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (txtJudulLaporan.getText().toString().equals("")) {
                Util.showmsg(LaporActivity.this, null, "Judul Laporan Kosong");
            } else if (getJns_kej() == null) {
                Util.showmsg(LaporActivity.this, null, "Silahkan Pilih Jenis Kejadian");
            } else if (txtDescKejadian.getText().toString().equals("")) {
                Util.showmsg(LaporActivity.this, null, "Deskripsi Kejadian Kosong");
            } else {
                KirimLaporan();
            }

        }
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @SuppressLint("SimpleDateFormat")
    private void KirimLaporan() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tk = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        String ts = tk.format(c);

        String ID_RELAWAN = lp.getString("idRelawanKey", "");
        String TGL_KEJADIAN = ts;
        String STS_LAPORAN = "0";
        String JUDUL = txtJudulLaporan.getText().toString();
        String DESKRIPSI = txtDescKejadian.getText().toString();
        String ID_RELAWAN_TO = lp.getString("idRelawanUplineKey", "");
        String STS_REPLY = "0";
        String DATE_CREATE = formattedDate;
        String USERID_CREATE = "";
        System.out.println("ID RELAWAN LAPOR = " + ID_RELAWAN + "\n" + "ID RELAWAN UPLINE = " + ID_RELAWAN_TO);

        String url = "http://31.220.58.1:8080/rgb/ws/lapor/post";
        RPGValidator rpgv = new RPGValidator();
        JSONObject object = rpgv.postLaporan(url, ID_RELAWAN, TGL_KEJADIAN, STS_LAPORAN, JUDUL,
                DESKRIPSI, ID_RELAWAN_TO, STS_REPLY, DATE_CREATE, USERID_CREATE);

        if (object != null) {

            try {

                if ((object.has("CODE") && object.getString("CODE").equals("00"))) {

                    clearAll();
                    Util.showmsg(LaporActivity.this, null, "Laporan Berhasil di Kirim");

                } else {

                    Util.showmsg(LaporActivity.this, null, "Gagal Mengirim Laporan");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            runOnUiThread(new Runnable() {
                public void run() {

                    Util.showmsg(LaporActivity.this, null, "Gagal Terkoneksi ke Server");
                    finish();

                }
            });

        }

    }

    private void clearAll() {

        txtJudulLaporan.setText("");
        txtDescKejadian.setText("");
        radio_group.clearCheck();

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
