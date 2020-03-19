package com.fortuna.rgpmobile.pesan;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.recycler.RecyclerItemClickListener;
import com.fortuna.rgpmobile.view.AndroidDataAdapter;
import com.fortuna.rgpmobile.view.AndroidVersion;

import java.util.ArrayList;

public class PesanMasukActivity extends BaseActivity {

    Toolbar toolbar_pesan_masuk;

    private RecyclerView recycler_view_inbox;
    private RecyclerView.LayoutManager inboxLayoutManager;

    private final String recyclerViewTitleText[] = {"Pesan Broadcast", "Pesan Laporan"};

    private final int recyclerViewImages[] = {
            R.drawable.icon_pesan_masuk, R.drawable.icon_pesan_masuk
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_pesan_masuk);

        setupToolbar();
        setupRecycle();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_pesan_masuk = findViewById(R.id.toolbar_pesan_masuk);
        setSupportActionBar(toolbar_pesan_masuk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void setupRecycle() {

        recycler_view_inbox = findViewById(R.id.recycler_view_inbox);
        recycler_view_inbox.setNestedScrollingEnabled(false);
        recycler_view_inbox.setHasFixedSize(true);
        inboxLayoutManager = new GridLayoutManager(PesanMasukActivity.this, 1);
        recycler_view_inbox.setLayoutManager(inboxLayoutManager);

        ArrayList<AndroidVersion> av = prepareData();
        AndroidDataAdapter mAdapter = new AndroidDataAdapter(PesanMasukActivity.this, av);
        /*Typeface tf_regular = Typeface.createFromAsset(getAssets(), "arial.ttf");
        mRecyclerView.setTypeface(tf_regular);*/
        recycler_view_inbox.setAdapter(mAdapter);

        recycler_view_inbox.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {

                        switch (i) {
                            case 0:
                                Intent lapor = new Intent(PesanMasukActivity.this, PesanMasukBroadcastActivity.class);
                                startActivity(lapor);
                                break;
                            case 1:
                                Intent bc = new Intent(PesanMasukActivity.this, PesanMasukLaporanActivity.class);
                                startActivity(bc);
                                break;
                        }

                    }
                })
        );

    }


    private ArrayList<AndroidVersion> prepareData() {

        ArrayList<AndroidVersion> av = new ArrayList<>();
        for (int i = 0; i < recyclerViewTitleText.length; i++) {
            AndroidVersion mAndroidVersion = new AndroidVersion();
            mAndroidVersion.setAndroidVersionName(recyclerViewTitleText[i]);
            mAndroidVersion.setrecyclerViewImage(recyclerViewImages[i]);
            av.add(mAndroidVersion);
        }
        return av;
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
