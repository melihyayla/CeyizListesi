package com.ceyizlistesi.ceyizlistesi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class IntroSlider extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_intro_slider);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);


        if(!prefs.getBoolean("first_time", false)){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_time", true);
            editor.commit();

            String title = getString(R.string.slider_title), description = getString(R.string.slider_description);

            addSlide(AppIntro2Fragment.newInstance(title, "fonts/gordita_bold.otf",
                    description, "fonts/gordita_regular.otf",
                    R.drawable.ic_witch,
                    ContextCompat.getColor(getApplicationContext(),
                            R.color.white),
                    Color.rgb( 71,65,103),
                    Color.rgb( 71,65,103)));

            addSlide(AppIntro2Fragment.newInstance(title, "fonts/gordita_bold.otf",
                    description, "fonts/gordita_regular.otf",
                    R.drawable.ic_witch,
                    ContextCompat.getColor(getApplicationContext(),
                            R.color.white),
                    Color.rgb( 71,65,103),
                    Color.rgb( 71,65,103)));

            addSlide(AppIntro2Fragment.newInstance(title, "fonts/gordita_bold.otf",
                    description, "fonts/gordita_regular.otf",
                    R.drawable.ic_witch,
                    ContextCompat.getColor(getApplicationContext(),
                            R.color.white),
                    Color.rgb( 71,65,103),
                    Color.rgb( 71,65,103)));




            setSkipText("ATLA");

            setSkipTextTypeface("fonts/gordita_medium.otf");

            //setBarColor(Color.rgb( 255,255,255));

            setIndicatorColor(Color.rgb( 71,65,103),Color.parseColor("#CCCCFF"));
            setNextArrowColor(Color.parseColor("#CCCCFF"));
            setColorDoneText(Color.rgb(144,97 ,255));
            setColorSkipButton(Color.rgb(144 ,97 ,255));

            setDoneText("BASLA");

            setDoneTextTypeface("fonts/gordita_medium.otf");

        }
        else
       {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }


    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
