package com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Kayıpİlanların;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class İlanHarGörKayiplarPopUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilan_har_goruntule_kayiplar_pop_up_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.ilan_har_gor_pop_up_kayiplar_frame_layout);

        if (fragment==null){
            fragment=İlanHarGörKayiplarPopUpFragment.newConstr();
            fragmentManager.beginTransaction().add(R.id.ilan_har_gor_pop_up_kayiplar_frame_layout,fragment).commit();
        }
    }

    public static Intent profilimKayiplaryeniIntent(Context context, KayıpHayvanlar kayıpHayvanlar){
        Intent intent=new Intent(context,İlanHarGörKayiplarPopUpActivity.class);
        intent.putExtra("word",kayıpHayvanlar);
        return intent;

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
