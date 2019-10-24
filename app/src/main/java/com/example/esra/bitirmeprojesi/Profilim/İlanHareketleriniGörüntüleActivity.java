package com.example.esra.bitirmeprojesi.Profilim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.esra.bitirmeprojesi.R;

public class İlanHareketleriniGörüntüleActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilan_hareketlerini_goruntule_tasarim);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager=findViewById(R.id.ilan_har_goruntule_view_pager);
        tabLayout=findViewById(R.id.ilan_har_goruntule_tab_layout);
        //uygulamanın suppport managerı cagırılır
        İlanHarGörüntüleViewPagerAdapter adapter=new İlanHarGörüntüleViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);  //adater ile viewpager baglandı
        tabLayout.setupWithViewPager(viewPager);//tablayout ile viewpager baglandı
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
