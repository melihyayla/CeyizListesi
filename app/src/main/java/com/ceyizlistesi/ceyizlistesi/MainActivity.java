package com.ceyizlistesi.ceyizlistesi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ceyizlistesi.ceyizlistesi.Fragments.ChestFragment;
import com.ceyizlistesi.ceyizlistesi.Fragments.DiscoverFragment;
import com.ceyizlistesi.ceyizlistesi.Fragments.NotificationsFragment;
import com.ceyizlistesi.ceyizlistesi.Fragments.UserFragment;
import com.ceyizlistesi.ceyizlistesi.Helper.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager =  findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F0FFFFFF"));
        tabLayout.setTabRippleColor(null);

        tabLayout.getTabAt(0).setIcon(R.drawable.selector_discover);
        tabLayout.getTabAt(1).setIcon(R.drawable.selector_chest);
        tabLayout.getTabAt(2).setIcon(R.drawable.selector_notifications);
        tabLayout.getTabAt(3).setIcon(R.drawable.selector_user);


        if (isNetworkAvailable()) {
            Toast.makeText(MainActivity.this, "Internet connection established", Toast.LENGTH_SHORT)
                    .show();
        } else {
            createDialogBox();

        }

        
    }


    public void createDialogBox(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.no_internet_connection_dialog_box,null);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();

        dialog.show();

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }



    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DiscoverFragment());
        adapter.addFragment(new ChestFragment());
        adapter.addFragment(new NotificationsFragment());
        adapter.addFragment(new UserFragment());
        viewPager.setAdapter(adapter);
    }
}
