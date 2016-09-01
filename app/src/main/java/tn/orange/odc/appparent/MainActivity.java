package tn.orange.odc.appparent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sembozdemir.viewpagerarrowindicator.library.ViewPagerArrowIndicator;

import tn.orange.odc.appparent.Fragments.SimpleFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView ImgCentral;
    Button BtnCentral;
    TextView text1,text2,text3;
    ImageView Img1, Img2,Img3;
    ViewPager viewPager;
    ViewPagerArrowIndicator viewPagerArrowIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.main_activity_layout);


         text1 = (TextView) findViewById(R.id.textAppel);
         text2 = (TextView) findViewById(R.id.textRappel);
         text3 = (TextView) findViewById(R.id.textCom);

         Img1=(ImageView)findViewById(R.id.imageAppel);
         Img2=(ImageView)findViewById(R.id.imageRappel);
         Img3=(ImageView)findViewById(R.id.imageCom);

        Img1.setOnClickListener(this);
        Img2.setOnClickListener(this);
        Img3.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerArrowIndicator = (ViewPagerArrowIndicator) findViewById(R.id.viewPagerArrowIndicator);
        viewPagerArrowIndicator.setArrowIndicatorRes(R.drawable.fg,R.drawable.fd);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                Log.e("page n :",position+"");
                if(position == 0) {
                    // piloter
                    // menu item  0 :
                    text1.setText("Appeler");
                    Img1.setBackground(getResources().getDrawable(R.drawable.appeler));
                    Img1.setContentDescription("Appeler");
                    // menu item  2 :
                    text2.setText("Rappeler");
                    Img2.setBackground(getResources().getDrawable(R.drawable.rappelermenu));
                    Img2.setContentDescription("Rappeler");
                    //menu item 3:
                    text3.setText("Commander");
                    Img3.setBackground(getResources().getDrawable(R.drawable.commandermenu));
                    Img3.setContentDescription("Commander");
                }else
                if(position == 1){
                    // appel
                    // menu item  1 :
                    text1.setText("Rappeler");
                    Img1.setBackground(getResources().getDrawable(R.drawable.rappelermenu));
                    Img1.setContentDescription("Rappeler");
                    Img1.setContentDescription("Rappeler");
                    // menu item  2 :
                    text2.setText("Commander");
                    Img2.setBackground(getResources().getDrawable(R.drawable.commandermenu));
                    Img2.setContentDescription("Commander");
                    //menu item 3:
                    text3.setText("Piloter");
                    Img3.setBackground(getResources().getDrawable(R.drawable.pilotermenu));
                    Img3.setContentDescription("Piloter");


                }else if(position == 2){
                    // rappel
                    text1.setText("Commander");
                    Img1.setBackground(getResources().getDrawable(R.drawable.commandermenu));
                    Img1.setContentDescription("Commander");
                    text2.setText("Piloter");
                    Img2.setBackground(getResources().getDrawable(R.drawable.pilotermenu));
                    Img2.setContentDescription("Piloter");
                    text3.setText("Appeler");
                    Img3.setBackground(getResources().getDrawable(R.drawable.appeler));
                    Img3.setContentDescription("Appeler");

                }else if(position == 3){
                    text1.setText("Piloter");
                    Img1.setBackground(getResources().getDrawable(R.drawable.pilotermenu));
                    text1.setText("Piloter");

                    text2.setText("Appeler");
                    Img2.setBackground(getResources().getDrawable(R.drawable.appeler));
                    text2.setText("Appeler");

                    text3.setText("Rappeler");
                    Img3.setBackground(getResources().getDrawable(R.drawable.rappelermenu));
                    Img3.setContentDescription("Rappeler");

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerArrowIndicator.bind(viewPager);

    }

    @Override
    public void onClick(View view) {
        // ui menu current item
        // Piloter : 0 ; Appeler : 1; Rappeler :2 ; Commander: 3;
        if(view instanceof  ImageView){
            if(view.getContentDescription().toString().equalsIgnoreCase("Appeler")){
                viewPager.setCurrentItem(1);
            }else  if(view.getContentDescription().toString().equalsIgnoreCase("Rappeler")){
                viewPager.setCurrentItem(2);
            }else   if(view.getContentDescription().toString().equalsIgnoreCase("Commander")){
                viewPager.setCurrentItem(3);
            } else  if(view.getContentDescription().toString().equalsIgnoreCase("Piloter")){
                viewPager.setCurrentItem(0);
            }
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0:
                    return SimpleFragment.newInstance("Piloter");
                case 1:
                    return SimpleFragment.newInstance("Appeler");
                case 2:
                    return SimpleFragment.newInstance("Rappeler");
                case 3:
                    return SimpleFragment.newInstance("Commander");
                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}