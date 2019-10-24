package com.example.esra.bitirmeprojesi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class İlanSahibiSahiplendirmeActivity extends AppCompatActivity {
    CircularImageView ımageView;
    TextView ilanSahibiİsimSoyisim,ilanSahibiSehir;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilan_sahibi_tasarim);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressBar=findViewById(R.id.ilan_sahibi_progressBar);
        ımageView=findViewById(R.id.ilan_sahibi_profilFoto);
        ilanSahibiİsimSoyisim=findViewById(R.id.ilan_sahibi_kullanıcıİsimSoyisim);
        ilanSahibiSehir=findViewById(R.id.ilan_sahibi_kullanıcıSehir);
        firebaseDatabase=FirebaseDatabase.getInstance();

        ilanSahibiProfilGetirSahiplendirme();

    }

    public void ilanSahibiProfilGetirSahiplendirme(){

        DatabaseReference newR=FirebaseDatabase.getInstance().getReference("Sahiplendirme İlanları");
        newR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String ids=String.valueOf(hashMap.get("İlan İD"));
                    Intent intent = getIntent();
                    final String ilanidS=intent.getStringExtra("ilan id sahiplendirme");
                    if (ilanidS.matches(ids)){
                        final String ilanSahibiidS=String.valueOf(hashMap.get("İlan Sahibi İD"));
                        DatabaseReference newreference=FirebaseDatabase.getInstance().getReference("Profiller");
                        newreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                    String ilanSahibiS=String.valueOf(hashMap.get("üyeUid"));
                                    if (ilanSahibiidS.matches(ilanSahibiS)){
                                        String adSoyadS=String.valueOf(hashMap.get("üyeİsimSoyisim"));
                                        String sehirS=String.valueOf(hashMap.get("üyeSehir"));
                                        String ppS=String.valueOf(hashMap.get("üyeFoto"));

                                        ilanSahibiİsimSoyisim.setText(adSoyadS);
                                        ilanSahibiSehir.setText(sehirS);
                                        Picasso.get().load(ppS).into(ımageView, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
