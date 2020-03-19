package com.fortuna.rgpmobile.pesan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fortuna.rgpmobile.login.LoginActivity;
import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.adapter.InboxLaporanAdapter;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.main.RPGValidator;
import com.fortuna.rgpmobile.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesanMasukLaporanActivity extends BaseActivity {

    Toolbar toolbar_pesan_masuk_laporan;
    RecyclerView rv_inbox_laporan;
    List<InboxLaporanData> mILaporanData = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    SharedPreferences lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_pesan_masuk_laporan);

        setupToolbar();
        setupComponent();
        setupDataInbox();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_pesan_masuk_laporan = findViewById(R.id.toolbar_pesan_masuk_laporan);
        setSupportActionBar(toolbar_pesan_masuk_laporan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupComponent() {

        lp = getSharedPreferences(LoginActivity.LoginPref, Context.MODE_PRIVATE);

        rv_inbox_laporan = findViewById(R.id.rv_inbox_laporan);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutClickHandler());

    }


    private class SwipeRefreshLayoutClickHandler implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {

            swipeRefreshLayout.post(
                    new Runnable() {
                        @Override
                        public void run() {

                            swipeRefreshLayout.setRefreshing(true);

                            setupDataInbox();

                        }
                    }
            );

        }
    }


    private void setupDataInbox() {

        swipeRefreshLayout.setRefreshing(true);

        mILaporanData.clear();

        String ID_RELAWAN = lp.getString("idRelawanKey", "");

        String url = "http://31.220.58.1:8080/rgb/ws/lapor/get";
        RPGValidator rpgv = new RPGValidator();
        JSONObject object = rpgv.cekInboxLaporan(url, ID_RELAWAN);

        if (object != null) {

            try {

                if ((object.has("CODE") && object.getString("CODE").equals("00")) ) {

                    String notes = object.getString("DATA");
                    JSONArray ibLaporan = new JSONArray(notes);

                    for (int i = 0; i < ibLaporan.length(); i++) {
                        JSONObject catObj = (JSONObject) ibLaporan.get(i);
                        InboxLaporanData mInbLap = new InboxLaporanData(catObj.getString("ID_RELAWAN"),
                                catObj.getString("TGL_KEJADIAN"), catObj.getString("STS_LAPORAN"), catObj.getString("JUDUL"),
                                catObj.getString("DESKRIPSI"), catObj.getString("ID_RELAWAN_TO"), catObj.getString("STS_REPLY"),
                                catObj.getString("DATE_CREATE"), catObj.getString("USERID_CREATE"));
                        mILaporanData.add(mInbLap);
                    }

                    populateDataInboxLaporan();
                    swipeRefreshLayout.setRefreshing(false);

                } else {

                    swipeRefreshLayout.setRefreshing(false);
                    Util.showmsg(PesanMasukLaporanActivity.this, null, "Gagal Mendapatkan Data");
                    finish();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
                finish();
            }

        } else {

            runOnUiThread(new Runnable() {
                public void run() {

                    swipeRefreshLayout.setRefreshing(false);
                    Util.showmsg(PesanMasukLaporanActivity.this, null, "Gagal Terkoneksi ke Server");
                    finish();

                }
            });

        }

    }


    private void populateDataInboxLaporan() {

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(PesanMasukLaporanActivity.this,
                LinearLayoutManager.VERTICAL, false);
        /*rv_inbox_laporan.addItemDecoration(new DividerItemDecoration(PesanMasukLaporanActivity.this,
                DividerItemDecoration.VERTICAL));*/
        DividerItemDecoration divider = new DividerItemDecoration(PesanMasukLaporanActivity.this,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider_rv));
        rv_inbox_laporan.addItemDecoration(divider);

        rv_inbox_laporan.setLayoutManager(mLinearLayoutManager);

        InboxLaporanAdapter mLaporanAdapter = new InboxLaporanAdapter(PesanMasukLaporanActivity.this, mILaporanData);
        rv_inbox_laporan.setAdapter(mLaporanAdapter);

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
