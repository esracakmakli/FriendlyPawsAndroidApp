package com.example.esra.bitirmeprojesi.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Class.ÜyeProfili;
import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class İlanSahibiActivity extends AppCompatActivity {

    CircularImageView ımageView;
    TextView ilanSahibiİsimSoyisim, ilanSahibiSehir;
    FirebaseDatabase firebaseDatabase;
    private ProgressBar progressBar;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1905;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilan_sahibi_tasarim);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.ilan_sahibi_progressBar);
        ımageView = findViewById(R.id.ilan_sahibi_profilFoto);
        ilanSahibiİsimSoyisim = findViewById(R.id.ilan_sahibi_kullanıcıİsimSoyisim);
        ilanSahibiSehir = findViewById(R.id.ilan_sahibi_kullanıcıSehir);
        firebaseDatabase = FirebaseDatabase.getInstance();


        ilanSahibiProfilGetirKayiplar();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ilanSahibiProfilGetirKayiplar() {


        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Kayıp Hayvan İlanları");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String id = String.valueOf(hashMap.get("İlan İD"));
                    Intent intent = getIntent();
                    final String ilanid = intent.getStringExtra("ilan id kayiplar");
                    if (ilanid.matches(id)) {
                        final String ilanSahibiid = String.valueOf(hashMap.get("İlan Sahibi İD"));
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profiller");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                    String ilanSahibi = String.valueOf(hashMap.get("üyeUid"));
                                    if (ilanSahibiid.matches(ilanSahibi)) {
                                        String adSoyad = String.valueOf(hashMap.get("üyeİsimSoyisim"));
                                        String sehir = String.valueOf(hashMap.get("üyeSehir"));
                                        String pp = String.valueOf(hashMap.get("üyeFoto"));

                                        ilanSahibiİsimSoyisim.setText(adSoyad);
                                        ilanSahibiSehir.setText(sehir);
                                        Picasso.get().load(pp).into(ımageView, new Callback() {
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

    public void ilanSahibiİleİletisimeGec(View view) {
        Intent ıntent = getIntent();
        final String telno = ıntent.getStringExtra("tel no kayıp ilanı");

        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel: " + telno));
        startActivity(i);
    }

}