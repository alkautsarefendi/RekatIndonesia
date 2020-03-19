package com.fortuna.rgpmobile.dashboard;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.berita.BeritaActivity;

public class DashboardActivity extends BaseActivity {

    Toolbar toolbar_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_dashboard);

        setupToolbar();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_dashboard = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
