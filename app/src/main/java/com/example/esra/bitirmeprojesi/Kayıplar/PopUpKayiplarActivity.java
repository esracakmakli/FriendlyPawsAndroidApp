package com.example.esra.bitirmeprojesi.Kayıplar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.esra.bitirmeprojesi.Activity.İlanSahibiActivity;
import com.example.esra.bitirmeprojesi.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.util.UUID;

public class PopUpKayiplarActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_kayiplar_activity); //activitenin layouta bağlanması
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //popupFragmentın popupactiviteye bağlanması
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.pop_up_activity_framelayout);

        //fragment boş ise
        if (fragment==null){
            fragment=PopUpKayiplarFragment.newInstance(); //popupfragmentı belirle
            fragmentManager.beginTransaction().add(R.id.pop_up_activity_framelayout,fragment).commit();
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
    public void ilanSahibineGit(View view){
        Intent yeni=new Intent(getApplicationContext(),İlanSahibiActivity.class);
        startActivity(yeni);

    }
    public void yorumSahibininProfilineGit(View view){
        Intent yeni=new Intent(getApplicationContext(),İlanSahibiActivity.class);
        startActivity(yeni);
    }

    //activityenin başka bir yerden çağırılabilmesi için ıntent metodu tanımlanır. Tıklanınca çağırılır.
    public static Intent newIntent(Context context,KayıpHayvanlar kayıpHayvanlar){
        Intent intent=new Intent(context,PopUpKayiplarActivity.class); //popup activity i intent yap
        intent.putExtra("Info",kayıpHayvanlar);
        return intent;
    }


}
