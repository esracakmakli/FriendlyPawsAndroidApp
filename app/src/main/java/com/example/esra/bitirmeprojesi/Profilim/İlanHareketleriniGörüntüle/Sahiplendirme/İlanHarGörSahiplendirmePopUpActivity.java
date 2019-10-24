package com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Sahiplendirme;

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

import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Sahiplen.Sahiplendirme;

public class İlanHarGörSahiplendirmePopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilan_har_goruntule_sahiplendirme_pop_up_activity);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.ilan_har_gör_sahiplendirme_pop_up_frame_layout);

        if (fragment==null){
            fragment=İlanHarGörSahiplendirmePopUpFragment.newConstr();
            fragmentManager.beginTransaction().add(R.id.ilan_har_gör_sahiplendirme_pop_up_frame_layout,fragment).commit();
        }
    }

    public static Intent ilanHarGorSahiplendirmeyeniIntent(Context context, Sahiplendirme sahiplendirme){
        Intent intent=new Intent(context,İlanHarGörSahiplendirmePopUpActivity.class);
        intent.putExtra("WORD",sahiplendirme);
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
