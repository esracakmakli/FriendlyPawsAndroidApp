package com.example.esra.bitirmeprojesi.SoruCevap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.Kayıplar.PopUpKayiplarActivity;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Yorumlar.KayıpYorumlarAdapter;
import com.example.esra.bitirmeprojesi.Yorumlar.SoruCevapCevaplarAdapter;
import com.example.esra.bitirmeprojesi.Yorumlar.Yorumlar;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SoruActivity extends AppCompatActivity {
    private TextView soru,isim,tarih,baslık;
    private CircularImageView foto;

    //cevaplar için
    private TextView notText;
    private RecyclerView recyclerView;
    SoruCevapCevaplarAdapter soruCevapCevaplarAdapter;
    EditText cevapText;
    private ArrayList<Yorumlar> gelenCevaplar;
    TextView cevapSahibiText,cevap,cevapTarihiText;
    CircularImageView circularImageView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    private SoruCevapClass soruCevapClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soru_activity);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        soru=findViewById(R.id.soru_activity_soru);
        isim=findViewById(R.id.soru_activity_isim_soyisim);
        tarih=findViewById(R.id.soru_activity_tarih);
        baslık=findViewById(R.id.soru_activity_konu);
        foto=findViewById(R.id.soru_activity_pp);
        notText=findViewById(R.id.soru_cevap_text);
        recyclerView=findViewById(R.id.soru_activity_rv);

        Intent intent = getIntent();
        String isimgetir=intent.getStringExtra("isimgonder");
        String sorugetir=intent.getStringExtra("sorugonder");
        String tarihgetir =intent.getStringExtra("sorutarih");
        String baslıkgetir=intent.getStringExtra("sorubaslık");
        String pp=intent.getStringExtra("foto");
        soru.setText(sorugetir);isim.setText(isimgetir);tarih.setText(tarihgetir);baslık.setText(baslıkgetir);
        Picasso.get().load(pp).fit().centerCrop().into(foto);


        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        databaseReference=database.getReference("Soru&Cevap");
        recyclerView=findViewById(R.id.soru_activity_rv);
        cevapText=findViewById(R.id.soru_cevap_cevapText);
       // cevapGonder=findViewById(R.id.soru_cevap_yorum_gonder_button);
        soruCevapCevaplarAdapter =new SoruCevapCevaplarAdapter(SoruActivity.this,databaseReference);
        gelenCevaplar= soruCevapCevaplarAdapter.getCevaplarArrayList();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SoruActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(soruCevapCevaplarAdapter);

        cevapSahibiText=findViewById(R.id.kayıplar_yorum_yorumsahibi);
        cevap=findViewById(R.id.kayıplar_yorum_yapılanyorum);
        cevapTarihiText=findViewById(R.id.kayıplar_yorum_yorumtarihi);
        circularImageView=findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
        getCevaplarArrayLİst(gelenCevaplar);

    }
    public void soruCevapYorumGonder(View view){

        final String gönderilecekCevap=cevapText.getText().toString();
        if (cevapText.getText().length()>0) {
            UUID uuid1 = UUID.randomUUID();
            final String uuidString = uuid1.toString();
            final SimpleDateFormat bicim = new SimpleDateFormat("dd.MM.yyyy hh:mm aa"); //mevcut tarih ve saati am/pm moduna göre çek
            final Date tarihSaat = new Date();
            Intent intent = getIntent();
            final String idgetir=intent.getStringExtra("idgonder");
            DatabaseReference myRef=database.getReference("Profiller");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds:dataSnapshot.getChildren()) {
                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                        String id = String.valueOf(hashMap.get("üyeUid"));
                        if (id.matches(auth.getCurrentUser().getUid())){
                           final String pp=String.valueOf(hashMap.get("üyeFoto"));
                            final String kullanıcı=String.valueOf(hashMap.get("üyeİsimSoyisim"));
                            final DatabaseReference reference=database.getReference("Soru&Cevap").child("Tüm Kategoriler");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                        String soru_id = String.valueOf(hashMap.get("Soru İD"));
                                        if (soru_id.matches(idgetir)){
                                           final String sorukategori=String.valueOf(hashMap.get("Soru Kategorisi"));
                                           DatabaseReference ref=database.getReference("Soru&Cevap");
                                            ref.child(sorukategori).child(idgetir).child("Cevaplar").child(uuidString).child("Cevap").setValue(gönderilecekCevap);
                                            ref.child(sorukategori).child(idgetir).child("Cevaplar").child(uuidString).child("Cevap Sahibi").setValue(kullanıcı);
                                            ref.child(sorukategori).child(idgetir).child("Cevaplar").child(uuidString).child("Cevap Tarihi").setValue(bicim.format(tarihSaat));
                                            ref.child(sorukategori).child(idgetir).child("Cevaplar").child(uuidString).child("Fotoğraf").setValue(pp);
                                            ref.child(sorukategori).child(idgetir).child("Cevaplar").child(uuidString).child("Cevap İD").setValue(uuidString);
                                            ref.child(sorukategori).child(idgetir).child("Cevaplar").child(uuidString).child("Cevap Sahibi İD").setValue(auth.getCurrentUser().getUid());
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference.child(idgetir).child("Cevaplar").child(uuidString).child("Cevap").setValue(gönderilecekCevap);
                            reference.child(idgetir).child("Cevaplar").child(uuidString).child("Cevap Sahibi").setValue(kullanıcı);
                            reference.child(idgetir).child("Cevaplar").child(uuidString).child("Cevap Tarihi").setValue(bicim.format(tarihSaat));
                            reference.child(idgetir).child("Cevaplar").child(uuidString).child("Fotoğraf").setValue(pp);
                            reference.child(idgetir).child("Cevaplar").child(uuidString).child("Cevap İD").setValue(uuidString);
                            reference.child(idgetir).child("Cevaplar").child(uuidString).child("Cevap Sahibi İD").setValue(auth.getCurrentUser().getUid());

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            cevapText.setText("");
        }else
            Toast.makeText(getApplicationContext(),"Hata!",Toast.LENGTH_SHORT).show();

    }

    private ArrayList<Yorumlar> getCevaplarArrayLİst(final ArrayList<Yorumlar> gelenCevaplar) {
        if (gelenCevaplar.isEmpty()){  //cevaplar boş değil ise
            notText.setVisibility(View.GONE);
            Intent intent = getIntent();
            final String idgetir=intent.getStringExtra("idgonder");
            final DatabaseReference myRef=database.getReference("Soru&Cevap").child("Tüm Kategoriler").child(idgetir);
            myRef.child("Cevaplar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gelenCevaplar.clear();
                    for (DataSnapshot ds:dataSnapshot.getChildren()) {
                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                        String cevaplayan=String.valueOf(hashMap.get("Cevap Sahibi"));
                        String cevap=String.valueOf(hashMap.get("Cevap"));
                        String foto=String.valueOf(hashMap.get("Fotoğraf"));
                        String tarih=String.valueOf(hashMap.get("Cevap Tarihi"));
                        String id=String.valueOf(hashMap.get("Cevap İD"));
                        gelenCevaplar.add(new Yorumlar(cevaplayan,cevap,tarih,foto,id));
                        soruCevapCevaplarAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            notText.setText("Henüz cevaplanmamış, ilk cevabı veren sen ol!");
        }


        return gelenCevaplar;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static Intent yeni(Context context, SoruCevapClass soruCevapClass){
        Intent intent=new Intent(context, SoruActivity.class); //popup activity i intent yap
        intent.putExtra("sorucevap",soruCevapClass);
        return intent;
    }

}
