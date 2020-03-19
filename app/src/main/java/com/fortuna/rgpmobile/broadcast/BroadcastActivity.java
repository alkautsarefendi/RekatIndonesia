package com.fortuna.rgpmobile.broadcast;

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

public class BroadcastActivity extends BaseActivity {

    Toolbar toolbar_broadcast;
    Button btnKirim;
    EditText txtJudulPesan, txtIsiPesan;
    RadioGroup radio_group_bc;
    RadioButton radio_button_bc;

    SharedPreferences lp;

    String jns_broad;

    public String getJns_broad() {
        return jns_broad;
    }

    public void setJns_broad(String jns_broad) {
        this.jns_broad = jns_broad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_broadcast);

        setupToolbar();
        initComponent();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_broadcast = findViewById(R.id.toolbar_broadcast);
        setSupportActionBar(toolbar_broadcast);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void initComponent() {

        btnKirim = findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(new ButtonKirimClickHandler());

        txtJudulPesan = findViewById(R.id.txtJudulPesan);
        txtIsiPesan = findViewById(R.id.txtIsiPesan);

        radio_group_bc = findViewById(R.id.radio_group_bc);
        radio_group_bc.setOnCheckedChangeListener(new RadioGroupBCCheckCLick());

        lp = getSharedPreferences(LoginActivity.LoginPref, Context.MODE_PRIVATE);

    }


    private class RadioGroupBCCheckCLick implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId == R.id.radioButton1) {

                int selectedId = radio_group_bc.getCheckedRadioButtonId();
                radio_button_bc = findViewById(selectedId);
                setJns_broad(radio_button_bc.getText().toString());
                System.out.println("Value RB = " + getJns_broad());

            } else if(checkedId == R.id.radioButton2) {

                int selectedId = radio_group_bc.getCheckedRadioButtonId();
                radio_button_bc = findViewById(selectedId);
                setJns_broad(radio_button_bc.getText().toString());
                System.out.println("Value RB = " + getJns_broad());

            } else if(checkedId == R.id.radioButton3){

                int selectedId = radio_group_bc.getCheckedRadioButtonId();
                radio_button_bc = findViewById(selectedId);
                setJns_broad(radio_button_bc.getText().toString());
                System.out.println("Value RB = " + getJns_broad());

            }

        }
    }


    private class ButtonKirimClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (txtJudulPesan.getText().toString().equals("")) {
                Util.showmsg(BroadcastActivity.this, null, "Judul Pesan Kosong");
            } else if (getJns_broad() == null) {
                Util.showmsg(BroadcastActivity.this, null, "Silahkan Pilih Jenis Broadcast");
            } else if (txtIsiPesan.getText().toString().equals("")) {
                Util.showmsg(BroadcastActivity.this, null, "Isi Pesan Kosong");
            } else {
                KirimBroadcast();
            }

        }
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @SuppressLint("SimpleDateFormat")
    private void KirimBroadcast() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dc = new SimpleDateFormat("HHmmss");
        SimpleDateFormat tk = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        String ts = tk.format(c);
        String ib = dc.format(c);

        Calendar cal= Calendar.getInstance();
        cal.set(Calendar.DATE, Calendar.DATE + 7);
        Date newDa = cal.getTime();
        String de = df.format(newDa);
        System.out.println("Current time => " + c + "Next 7 Days => " + newDa);

        String ID_RELAWAN = lp.getString("idRelawanKey", "");
        String TGL_BROADCAST = ts;
        String ID_BROADCAST = ib;
        String JNS_BROADCAST = getJns_broad();
        String DESKRIPSI = txtIsiPesan.getText().toString();
        String TGL_EXPIRED = de;
        String DATE_CREATE = formattedDate;
        String USERID_CREATE = "";
        System.out.println("ID RELAWAN BROADCAST = " + ID_RELAWAN);

        String url = "http://31.220.58.1:8080/rgb/ws/broadcast/post";
        RPGValidator rpgv = new RPGValidator();
        JSONObject object = rpgv.postBroadcast(url, ID_RELAWAN, TGL_BROADCAST, ID_BROADCAST, JNS_BROADCAST,
                DESKRIPSI, TGL_EXPIRED, DATE_CREATE, USERID_CREATE);

        if (object != null) {

            try {

                if ((object.has("CODE") && object.getString("CODE").equals("00"))) {

                    clearAll();
                    Util.showmsg(BroadcastActivity.this, null, "Laporan Berhasil di Kirim");

                } else {

                    Util.showmsg(BroadcastActivity.this, null, "Gagal Mengirim Laporan");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            runOnUiThread(new Runnable() {
                public void run() {

                    Util.showmsg(BroadcastActivity.this, null, "Gagal Terkoneksi ke Server");
                    finish();

                }
            });

        }

    }

    private void clearAll() {

        txtJudulPesan.setText("");
        txtIsiPesan.setText("");
        radio_group_bc.clearCheck();

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
