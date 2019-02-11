package com.example.testapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.admin.SystemUpdatePolicy;
import android.graphics.drawable.ColorDrawable;
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

public class MainTestActivity extends AppCompatActivity implements TimFragment.OnFragmentInteractionListener, CalcFragment.OnFragmentInteractionListener, AddressBookFragment.OnFragmentInteractionListener, MemoFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabItem tabCalc;
    private TabItem tabTim;
    private TabLayout tabContainer;
    private ViewPager vpMain;
    private LinearLayout headerContainer;

    private ValueAnimator animator;

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

        vpMain.setOffscreenPageLimit(2);

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
                        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        //toolbar.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        //tabContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        //headerContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary));
                        animator.start();

                        break;
                    case 1:
                        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        //toolbar.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        //tabContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        //headerContainer.setBackgroundColor(ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary2));
                        animator.start();
                        break;
                    case 2:
                        animator.setIntValues(((ColorDrawable)headerContainer.getBackground()).getColor(), ContextCompat.getColor(MainTestActivity.this, R.color.colorPrimary3));
                        animator.start();
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
