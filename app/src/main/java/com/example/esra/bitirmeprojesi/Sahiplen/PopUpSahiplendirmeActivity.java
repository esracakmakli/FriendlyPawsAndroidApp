package com.example.esra.bitirmeprojesi.Sahiplen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.esra.bitirmeprojesi.Activity.İlanSahibiActivity;
import com.example.esra.bitirmeprojesi.R;


public class PopUpSahiplendirmeActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_sahiplendirme_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.sahiplendirme_framelayout);

        if (fragment==null){
            fragment=PopUpSahiplendirmeFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.sahiplendirme_framelayout,fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static Intent newIntent(Context context, Sahiplendirme sahiplendirme){
        Intent intent=new Intent(context,PopUpSahiplendirmeActivity.class);
        intent.putExtra("INFO",sahiplendirme);
        return intent;
    }
    public void ilanSahibineGit(View view){
        Intent yeni=new Intent(getApplicationContext(),İlanSahibiActivity.class);
        startActivity(yeni);

    }


}
