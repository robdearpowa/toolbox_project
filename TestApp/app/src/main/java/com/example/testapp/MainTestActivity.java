package com.example.testapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
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
import android.widget.LinearLayout;

public class MainTestActivity extends AppCompatActivity implements TimFragment.OnFragmentInteractionListener, CalcFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabItem tabCalc;
    private TabItem tabTim;
    private TabLayout tabContainer;
    private ViewPager vpMain;
    private LinearLayout headerContainer;

    private ValueAnimator animator1;
    private ValueAnimator animator2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        //Ottengo le referenze degli elementi che servono
        tabContainer = findViewById(R.id.tabContainer);
        tabCalc = findViewById(R.id.tabCalc);
        tabTim = findViewById(R.id.tabTim);
        vpMain = findViewById(R.id.vpMain);
        toolbar = findViewById(R.id.toolbar);
        headerContainer = findViewById(R.id.headerContainer);

        animator1 = new ValueAnimator();
        animator2 = new ValueAnimator();

        animator1.setIntValues(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary), ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
        animator2.setIntValues(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2), ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));

        animator1.setEvaluator(new ArgbEvaluator());
        animator2.setEvaluator(new ArgbEvaluator());

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                headerContainer.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor((Integer)valueAnimator.getAnimatedValue());
                }
            }
        });

        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                headerContainer.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor((Integer)valueAnimator.getAnimatedValue());
                }
            }
        });

        animator1.setDuration(300);
        animator2.setDuration(300);

        //Connetto il visualizzatore di pagine con il gestore delle tab
        tabContainer.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpMain.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        //toolbar.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        //tabContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        //headerContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        animator2.start();

                        break;
                    case 1:
                        //toolbar.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        //tabContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        //headerContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        animator1.start();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainTestActivity.this,
                                    R.color.colorPrimary2Dark));
                        }
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
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
