package com.ceyizlistesi.ceyizlistesi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ceyizlistesi.ceyizlistesi.Fragments.ChestFragment;
import com.ceyizlistesi.ceyizlistesi.Fragments.DiscoverFragment;
import com.ceyizlistesi.ceyizlistesi.Fragments.NotificationsFragment;
import com.ceyizlistesi.ceyizlistesi.Fragments.UserFragment;
import com.ceyizlistesi.ceyizlistesi.Helper.ConnectivityChangeReceiver;
import com.ceyizlistesi.ceyizlistesi.Helper.InternetCheckListener;
import com.ceyizlistesi.ceyizlistesi.Helper.IsInternetConnected;
import com.ceyizlistesi.ceyizlistesi.Helper.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    public ImageView plusIcon;
    private static Context context;
    private static Activity activity;
    SectionsPagerAdapter adapter;
    private TabLayout tabLayout;
    DiscoverFragment discoverFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        MainActivity.activity = this;



        mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        plusIcon = findViewById(R.id.share_plus_button);
        plusIcon.setVisibility(View.GONE);

        mViewPager =  findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F0FFFFFF"));
        tabLayout.setTabRippleColor(null);



        tabLayout.getTabAt(0).setIcon(R.drawable.selector_discover);
        tabLayout.getTabAt(1).setIcon(R.drawable.selector_chest);
        tabLayout.getTabAt(3).setIcon(R.drawable.selector_user);
        tabLayout.getTabAt(2).setIcon(R.drawable.selector_notifications);

        discoverFragment = (DiscoverFragment) adapter.getItem(0);





        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {






              if(tab.getPosition()==0){
                  plusIcon.setVisibility(View.VISIBLE);

              }

              else{
                   plusIcon.setVisibility(View.GONE);
               }

          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {


          }

          @Override
          public void onTabReselected(TabLayout.Tab tab) {

              int position = tab.getPosition();

              if(position==0){
                 plusIcon.setVisibility(View.VISIBLE);

              }
              else
                  plusIcon.setVisibility(View.GONE);




          }
      });

    }



    public void setClickableFalse(View view) {
        if (view != null) {
            view.setClickable(false);
            if (view instanceof ViewGroup) {
                ViewGroup vg = ((ViewGroup) view);
                for (int i = 0; i < vg.getChildCount(); i++) {
                    setClickableFalse(vg.getChildAt(i));
                }
            }
        }
    }

    public void setClickableTrue(View view) {
        if (view != null) {
            view.setClickable(true);
            if (view instanceof ViewGroup) {
                ViewGroup vg = ((ViewGroup) view);
                for (int i = 0; i < vg.getChildCount(); i++) {
                    setClickableTrue(vg.getChildAt(i));
                }
            }
        }
    }

    public void setTabLayoutClickable(Boolean flag){
        if(flag)
            setClickableTrue(tabLayout);
        else
            setClickableFalse(tabLayout);
    }


    public synchronized static Context getAppContext() {
        return MainActivity.context;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        activity = currentActivity;
    }

    public static Activity currentActivity() {
        return activity;
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DiscoverFragment());
        adapter.addFragment(new ChestFragment());
        adapter.addFragment(new NotificationsFragment());
        adapter.addFragment(new UserFragment());
        viewPager.setAdapter(adapter);
    }





}
