package com.example.testapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.admin.SystemUpdatePolicy;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.AbstractMutableList;

public class MainTestActivity extends AppCompatActivity implements TimFragment.OnFragmentInteractionListener, CalcFragment.OnFragmentInteractionListener, AddressBookFragment.OnFragmentInteractionListener, MemoFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabItem tabCalc;
    private TabItem tabTim;
    private TabLayout tabContainer;
    private ViewPager vpMain;
    private LinearLayout headerContainer;

    private ValueAnimator animator;

    public static MainTestActivity instance;
    public static SharedPreferences settingsSaver;
    public FancyBoxSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        instance = this;

        //Ottengo le referenze degli elementi che servono
        tabContainer = findViewById(R.id.tabContainer);
        tabCalc = findViewById(R.id.tabCalc);
        tabTim = findViewById(R.id.tabTim);
        vpMain = findViewById(R.id.vpMain);
        toolbar = findViewById(R.id.toolbar);
        headerContainer = findViewById(R.id.headerContainer);

        settingsSaver = getPreferences(MODE_PRIVATE);



        settings = new FancyBoxSettings();
        settings.loadSettings();





        headerContainer.setBackgroundColor(settings.getTabColors() != null ? settings.getTabColors().get(settings.getInitialTab()) : FancyBoxSettings.Companion.defaulTabColors().get(FancyBoxSettings.Companion.getDefaultInitialTab()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(settings.getTabColors() != null ? settings.getTabColors().get(settings.getInitialTab()) : FancyBoxSettings.Companion.defaulTabColors().get(FancyBoxSettings.Companion.getDefaultInitialTab()));
        }


        vpMain.setOffscreenPageLimit(3);

        animator = new ValueAnimator();


        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));

        animator.setEvaluator(new ArgbEvaluator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                headerContainer.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor((Integer)valueAnimator.getAnimatedValue());
                }
            }
        });



        animator.setDuration(350);

        //Connetto il visualizzatore di pagine con il gestore delle tab
        tabContainer.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpMain.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), settings.getTabColors() != null ? settings.getTabColors().get(0) : FancyBoxSettings.Companion.defaulTabColors().get(0));
                        //toolbar.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        //tabContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        //headerContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        animator.start();

                        break;
                    case 1:
                        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), settings.getTabColors() != null ? settings.getTabColors().get(1) : FancyBoxSettings.Companion.defaulTabColors().get(1));
                        //toolbar.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        //tabContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        //headerContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        animator.start();
                        break;
                    case 2:
                        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), settings.getTabColors() != null ? settings.getTabColors().get(2) : FancyBoxSettings.Companion.defaulTabColors().get(2));
                        animator.start();
                        break;
                    case 3:
                        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), settings.getTabColors() != null ? settings.getTabColors().get(3) : FancyBoxSettings.Companion.defaulTabColors().get(3));
                        animator.start();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Creo un gestore delle pagine passandogli un fragmentManager e il numero delle tab
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabContainer.getTabCount());

        //Aggiungo il gestore delle pagine al mio visualizzatore delle pagine
        vpMain.setAdapter(pageAdapter);

        //Connetto il gestore delle tab al visualizzatore di pagine
        vpMain.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabContainer));

        vpMain.setCurrentItem(settings.getInitialTab() != null ? settings.getInitialTab() : FancyBoxSettings.Companion.getDefaultInitialTab());
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
