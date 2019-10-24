package com.example.esra.bitirmeprojesi.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıplarİlanVerActivity;
import com.example.esra.bitirmeprojesi.Profilim.ProfiliDüzenleActivity;
import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüleActivity;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Sahiplen.SahiplendirmeİlanVerActivity;
import com.example.esra.bitirmeprojesi.SoruCevap.SoruCevap;
import com.example.esra.bitirmeprojesi.Kayıplar.Kayiplar;
import com.example.esra.bitirmeprojesi.Profilim.Profilim;
import com.example.esra.bitirmeprojesi.Sahiplen.Sahiplen;
import com.example.esra.bitirmeprojesi.SoruCevap.SoruSorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.util.UUID;

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        android.support.v7.widget.Toolbar toolbar=findViewById(R.id.toolbarözel);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        databaseReference=FirebaseDatabase.getInstance().getReference();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom_nav,new Kayiplar()).commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;

            switch (menuItem.getItemId()){
                case  R.id.nav_kayıplar:
                    selectedFragment=new Kayiplar();
                    break;
                case  R.id.nav_sahiplen:
                    selectedFragment=new Sahiplen();
                    break;
                case  R.id.nav_yardım:
                    selectedFragment= new SoruCevap();
                    break;
                case  R.id.nav_profilim:
                    selectedFragment=new Profilim();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom_nav,selectedFragment).commit();

            return true;
        }
    };
    public void signOut(View view){

        mAuth.getInstance().signOut();
        Intent intent=new Intent(Main2Activity.this,BaşlangıcActivity.class);
        startActivity(intent);
        
    }

    public void ilanVer(View view){
        Intent intent=new Intent(Main2Activity.this,KayıplarİlanVerActivity.class);
        startActivity(intent);

    }
    public void sahiplendirmeİlanVer(View view){
        Intent intent=new Intent(Main2Activity.this,SahiplendirmeİlanVerActivity.class);
        startActivity(intent);
    }
    public  void  profiliniDüzenle(View view){
        Intent intent=new Intent(Main2Activity.this,ProfiliDüzenleActivity.class);
        startActivity(intent);
    }
    public void ilanHareketleriniGörüntüle(View view){
        Intent intent=new Intent(Main2Activity.this,İlanHareketleriniGörüntüleActivity.class);
        startActivity(intent);

    }
    public void soruSor(View view){
        Intent intent=new Intent(Main2Activity.this,SoruSorActivity.class);
        startActivity(intent);
    }



}