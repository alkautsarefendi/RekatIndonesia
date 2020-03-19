package com.fortuna.rgpmobile.pesan;

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

import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.adapter.InboxBroadcastAdapter;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.main.RPGValidator;
import com.fortuna.rgpmobile.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesanMasukBroadcastActivity extends BaseActivity {

    Toolbar toolbar_pesan_masuk_broadcast;
    RecyclerView rv_inbox_broadcast;
    List<InboxBroadcastData> mIBroadcastData = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_pesan_masuk_broadcast);

        setupToolbar();
        setupComponent();
        setupDataInbox();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_pesan_masuk_broadcast = findViewById(R.id.toolbar_pesan_masuk_broadcast);
        setSupportActionBar(toolbar_pesan_masuk_broadcast);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void setupComponent() {

        rv_inbox_broadcast = findViewById(R.id.rv_inbox_broadcast);

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

        mIBroadcastData.clear();

        String url = "http://31.220.58.1:8080/rgb/ws/broadcast/get";
        RPGValidator rpgv = new RPGValidator();
        JSONObject object = rpgv.cekInboxBroadcast(url);

        if (object != null) {

            try {

                if ((object.has("CODE") && object.getString("CODE").equals("00"))) {

                    String notes = object.getString("DATA");
                    JSONArray ibBroadcast = new JSONArray(notes);

                    for (int i = 0; i < ibBroadcast.length(); i++) {
                        JSONObject catObj = (JSONObject) ibBroadcast.get(i);
                        InboxBroadcastData mInbBroad = new InboxBroadcastData(catObj.getString("ID_RELAWAN"),
                                catObj.getString("TGL_BROADCAST"), catObj.getString("ID_BROADCAST"), catObj.getString("JNS_BROADCAST"),
                                catObj.getString("DESKRIPSI"), catObj.getString("TGL_EXPIRED"),
                                catObj.getString("DATE_CREATE"), catObj.getString("USERID_CREATE"));
                        mIBroadcastData.add(mInbBroad);
                    }

                    populateDataInboxBroadcast();
                    swipeRefreshLayout.setRefreshing(false);

                } else {

                    swipeRefreshLayout.setRefreshing(false);
                    Util.showmsg(PesanMasukBroadcastActivity.this, null, "Gagal Mendapatkan Data");
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
                    Util.showmsg(PesanMasukBroadcastActivity.this, null, "Gagal Terkoneksi ke Server");
                    finish();

                }
            });

        }

    }


    private void populateDataInboxBroadcast() {

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(PesanMasukBroadcastActivity.this,
                LinearLayoutManager.VERTICAL, false);
        /*rv_inbox_broadcast.addItemDecoration(new DividerItemDecoration(PesanMasukBroadcastActivity.this,
                DividerItemDecoration.VERTICAL));*/
        DividerItemDecoration divider = new DividerItemDecoration(PesanMasukBroadcastActivity.this,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider_rv));
        rv_inbox_broadcast.addItemDecoration(divider);

        rv_inbox_broadcast.setLayoutManager(mLinearLayoutManager);

        InboxBroadcastAdapter mBroadcastAdapter = new InboxBroadcastAdapter(PesanMasukBroadcastActivity.this, mIBroadcastData);
        rv_inbox_broadcast.setAdapter(mBroadcastAdapter);

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
