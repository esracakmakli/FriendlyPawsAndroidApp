package com.example.esra.bitirmeprojesi.SoruCevap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Activity.Main2Activity;
import com.example.esra.bitirmeprojesi.Kayıplar.KayıplarİlanVerActivity;
import com.example.esra.bitirmeprojesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SoruSorActivity extends AppCompatActivity {

    EditText soruBaşlığı,soru;
    private Spinner spinnerhayvanTürü,spinnerirk,spinnerKategori;
    private static String[] türü=new String[] {"Türü","Kedi","Köpek"};
    private static String[] kategori=new String[] {"Kategori Seçiniz","Genel","Hayvan Sağlığı","Bakım","Beslenme","Eğitim&Oyun","Çiftleştirme","Hayvan Hakları"};
    private ArrayAdapter<String> türAdapter;
    private ArrayAdapter<String> cinsAdapter;
    private ArrayAdapter<String> kategoriAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soru_cevap_soru_sor_tasarim);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        soruBaşlığı=findViewById(R.id.soru_cevap_soru_başlığıText);
        soru=findViewById(R.id.soru_cevap_sorunuzText);
        progressBar=findViewById(R.id.soru_sor_progressbar);

        spinnerKategori=findViewById(R.id.soru_cevap_soru_kategorisi_spinner);
        kategoriAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategori);
        kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(kategoriAdapter);

        spinnerhayvanTürü=findViewById(R.id.soru_cevap_soru_hayvan_türü_spinner);
        türAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, türü);
        türAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhayvanTürü.setAdapter(türAdapter);
        spinnerirk=findViewById(R.id.soru_cevap_soru_hayvan_cinsi_spinner);
        spinnerhayvanTürü.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals(türü[0]))
                    cinsAdapter = new ArrayAdapter<String>(SoruSorActivity.this, android.R.layout.simple_list_item_1,boşş);
                if(parent.getSelectedItem().toString().equals(türü[1]))
                    cinsAdapter = new ArrayAdapter<String>(SoruSorActivity.this, android.R.layout.simple_list_item_1,kediIrkı);
                if(parent.getSelectedItem().toString().equals(türü[2]))
                    cinsAdapter = new ArrayAdapter<String>(SoruSorActivity.this, android.R.layout.simple_list_item_1,kopekIrkı);
                cinsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerirk.setAdapter(cinsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    public void soruEkle(View view){

        if (soruBaşlığı.getText().length()>0 && spinnerKategori.getSelectedItem().toString()!="Kategori Seçiniz" && spinnerhayvanTürü.getSelectedItem().toString()!="Türü"
                && spinnerirk.getSelectedItem().toString()!="Cinsi" && soru.getText().length()>0){
            progressBar.setVisibility(View.VISIBLE);
            UUID uuid1=UUID.randomUUID();
            final String uuidString=uuid1.toString();
            final String soruBaslıgı=soruBaşlığı.getText().toString();
            final String soruu=soru.getText().toString();
            final String tür=spinnerhayvanTürü.getSelectedItem().toString();
            final String kategori=spinnerKategori.getSelectedItem().toString();
            final String cins=spinnerirk.getSelectedItem().toString();
            SimpleDateFormat bicim=new SimpleDateFormat("dd.M.yyyy");
            Date tarih=new Date();
            final String sTarih=bicim.format(tarih);
            DatabaseReference Reference=firebaseDatabase.getReference("Profiller");
            Reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                        String email=String.valueOf(hashMap.get("üyeEmail"));
                        if (email.matches(mAuth.getCurrentUser().getEmail())) {
                            String kullanıcı = String.valueOf(hashMap.get("üyeİsimSoyisim"));
                            String foto=String.valueOf(hashMap.get("üyeFoto"));
                            if (kullanıcı!= null ) {

                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Kullanıcı İD").setValue(mAuth.getCurrentUser().getUid());
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Soru Başlığı").setValue(soruBaslıgı);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Soru Kategorisi").setValue(kategori);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Soru İD").setValue(uuidString);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Hayvan Türü").setValue(tür);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Hayvan Cinsi").setValue(cins);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Kullanıcı").setValue(kullanıcı);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Kullanıcı Profil Fotoğrafı").setValue(foto);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Soru Tarihi").setValue(sTarih);
                                myRef.child("Soru&Cevap").child(kategori).child(uuidString).child("Soru").setValue(soruu);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Kullanıcı İD").setValue(mAuth.getCurrentUser().getUid());
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Soru Başlığı").setValue(soruBaslıgı);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Soru Kategorisi").setValue(kategori);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Soru İD").setValue(uuidString);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Hayvan Türü").setValue(tür);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Hayvan Cinsi").setValue(cins);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Kullanıcı").setValue(kullanıcı);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Kullanıcı Profil Fotoğrafı").setValue(foto);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Soru Tarihi").setValue(sTarih);
                                myRef.child("Soru&Cevap").child("Tüm Kategoriler").child(uuidString).child("Soru").setValue(soruu);


                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Hata oluştu.",Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Soru eklendi.",Toast.LENGTH_SHORT).show();



        }else
            Toast.makeText(getApplicationContext(),"Bilgiler boş geçilemez!",Toast.LENGTH_SHORT).show();

    }


    private static String[] boşş=new String[]{"Cinsi"};
    private static String[] kediIrkı=new String[]{"Bilmiyorum","Amerikan Bobtail","Amerikan Curl","Amerikan Keuda","Amerikan Wirehair","Ankara Kedisi","Australian Mist","Balinese"
            ,"Bengal","Birman","Bombay/Siyah kediler","British Shorthair","Burmese","Burmilla","Chartreux","Chinchilla","Chinese Li Hua","Colorpoint Shorthair","Cornish Rex"
            ,"Devon Rex","Egyptian Mau","European Burmese","Exotic Shorthair","Habeş (Abyssinian) Kedisi","Havana Brown","Himalayan","Honey Bear","İran kedisi","Japon Bobtail"
            ,"Javanese","Kashmir","Korat","LaPerm","Maine Coon","Manx","Mavi Rus","Norwegian Forest","Ocicat","Oriental","Pixie Bob","Ragamuffin","Ragdoll","Savannah"
            ,"Scottish Fold","Selkirk Rex","Sibirya Kedisi","Singapura","Siyam kedisi","Snowshoe (Karayak)","Somali","Sphynx","Tekir","Tiffanie","Tonkinese"
            ,"Tuxedo (Smokin) Kedi","Van Kedisi","York Chocolate"};
    private static String[] kopekIrkı=new String[]{"Bilmiyorum","australian shepherd","airedale terrier","akita ınu","alabay","alaskan malamute","alman çoban köpeği"
            ,"american eskimo dog","american staffordshire","amerikan bulldog","amerikan cocker spaniel","Australian Cattle Dog","Basenji","basset hound","beagle"
            ,"bearded collie","belgian malinois","belgian shepherd","bernese mountain dog","bichon frise","biewer yorkshire terrier","bloodhound","bolognese","border collie"
            ,"border terrier","boston terrier","boxer","briard","bull terrier","bullmastiff","cane corso","cardigan welsh corgi","cavalier king charles","cavapoo","chihuahua"
            ,"chow chow","collie","coton de tulear","dachshund","dalmaçyalı (dalmatian)","danua","doberman","dogue de bordeaux","english cocker spaniel","english setter"
            ,"fox terrier","fransız (french) bulldog"," golden cocker retriever","fransız (french) bulldog","golden cocker retriever","Golden Retriever","goldendoodle"
            ,"Gordon setter","greyhound","havanese","havaton" ,"ırish setter","ingiliz (english) bulldog","Jack Russell Terrier","keeshond"," Komondor","Labradoodle"
            ,"labrador retriever"," Leonberger"," Lhasa Apso","Maltese Terrier","Maltipoo","Mastiff"," Miniature Pinscher","Morkie","Napoliten Mastiff"
            ,"Newfoundland","Norfolk Terrier","Old English Sheepdog","Papillon","Pekingese","Pembroke Welsh Corgi","Pointer","Pomeranian","poodle","Portekiz su köpeği"
            ,"pug (mops)","puggle","Puli","rottweiler","saint bernard","Samoyed","Schnauzer","Scottish Terrier","shar pei","shiba ınu","shih poo","şitsu shihtzu"
            ,"Siberian husky","Tibet mastifi","Tibetan Terrier","Toy Fox Terrier","Vizsla","Weimaraner","Welsh Terrier","West highland terrier","Whippet","Yorkshire terrier"};

}
