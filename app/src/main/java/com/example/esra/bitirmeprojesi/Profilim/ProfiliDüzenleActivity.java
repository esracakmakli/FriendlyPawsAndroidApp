package com.example.esra.bitirmeprojesi.Profilim;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Activity.Main2Activity;
import com.example.esra.bitirmeprojesi.Class.ÜyeProfili;
import com.example.esra.bitirmeprojesi.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ProfiliDüzenleActivity extends AppCompatActivity {

    Uri degistirilecekFoto;
    CircularImageView circularImageView;
    EditText kullanıcıİsimSoyisim,emailText;
    TextView sehirText;
    private Spinner spinnerIller;
    private static String[] iller = new String[] {"Yaşadığınız şehri değiştirin","İSTANBUL", "ANKARA",
            "İZMİR", "ADANA", "ADIYAMAN", "AFYONKARAHİSAR", "AĞRI", "AKSARAY",
            "AMASYA", "ANTALYA", "ARDAHAN", "ARTVİN", "AYDIN", "BALIKESİR",
            "BARTIN", "BATMAN", "BAYBURT", "BİLECİK", "BİNGÖL", "BİTLİS",
            "BOLU", "BURDUR", "BURSA", "ÇANAKKALE", "ÇANKIRI", "ÇORUM",
            "DENİZLİ", "DİYARBAKIR", "DÜZCE", "EDİRNE", "ELAZIĞ", "ERZİNCAN",
            "ERZURUM", "ESKİŞEHİR", "GAZİANTEP", "GİRESUN", "GÜMÜŞHANE",
            "HAKKARİ", "HATAY", "IĞDIR", "ISPARTA", "KAHRAMANMARAŞ", "KARABÜK",
            "KARAMAN", "KARS", "KASTAMONU", "KAYSERİ", "KIRIKKALE",
            "KIRKLARELİ", "KIRŞEHİR", "KİLİS", "KOCAELİ", "KONYA", "KÜTAHYA",
            "MALATYA", "MANİSA", "MARDİN", "MERSİN", "MUĞLA", "MUŞ",
            "NEVŞEHİR", "NİĞDE", "ORDU", "OSMANİYE", "RİZE", "SAKARYA",
            "SAMSUN", "SİİRT", "SİNOP", "SİVAS", "ŞIRNAK", "TEKİRDAĞ", "TOKAT",
            "TRABZON", "TUNCELİ", "ŞANLIURFA", "UŞAK", "VAN", "YALOVA",
            "YOZGAT", "ZONGULDAK"
    };
    private ArrayAdapter<String> veriAdaptoru;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private ProgressBar progressBar2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profili_duzenle_tasarim);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // kaydet=findViewById(R.id.profili_düzenle_kaydet_butonu);
        sehirText=findViewById(R.id.profili_düzenle_sehirText);
        circularImageView=findViewById(R.id.pDüzenle_profilFoto);
        kullanıcıİsimSoyisim=findViewById(R.id.pDüzenle_isimSoyisim);
        emailText=findViewById(R.id.pDüzenle_email);
        progressBar2=findViewById(R.id.profili_düzenle_prgrbr2);

        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        mStorageRef=FirebaseStorage.getInstance().getReference();
        spinnerIller = findViewById(R.id.spinnersehir);
        veriAdaptoru= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, iller);
        spinnerIller.setAdapter(veriAdaptoru);
        profilDegisikligiGetir();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void üyeProfiliGüncelle(View view){
        if (kullanıcıİsimSoyisim.getText().length()>0 && emailText.getText().length()>0){
            progressBar2.setVisibility(View.VISIBLE);
            if (degistirilecekFoto==null){
                final String kullanıcıid=mAuth.getCurrentUser().getUid();
                myRef = FirebaseDatabase.getInstance().getReference("Profiller");
                if (spinnerIller.getSelectedItem().toString()!="Yaşadığınız şehri değiştirin") {
                    myRef.child(kullanıcıid).child("üyeSehir").setValue(spinnerIller.getSelectedItem().toString());
                    myRef.child(kullanıcıid).child("üyeİsimSoyisim").setValue(kullanıcıİsimSoyisim.getText().toString());
                    myRef.child(kullanıcıid).child("üyeEmail").setValue(emailText.getText().toString());
                }else {
                    myRef.child(kullanıcıid).child("üyeİsimSoyisim").setValue(kullanıcıİsimSoyisim.getText().toString());
                    myRef.child(kullanıcıid).child("üyeEmail").setValue(emailText.getText().toString());
                }
                progressBar2.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfiliDüzenleActivity.this,"Değişiklikler Kaydedildi!",Toast.LENGTH_LONG).show();
            }

            if (degistirilecekFoto!=null){
                final UUID uuid=UUID.randomUUID();
                final String imageName="profilfoto/"+uuid+".jpg";
                StorageReference storageReference=mStorageRef.child(imageName);
                storageReference.putFile(degistirilecekFoto).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference newReference=FirebaseStorage.getInstance().getReference("profilfoto/"+uuid+".jpg");
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {

                                final String kullanıcıid=mAuth.getCurrentUser().getUid();
                                myRef = FirebaseDatabase.getInstance().getReference("Profiller");
                                myRef.child(kullanıcıid).child("üyeİsimSoyisim").setValue(kullanıcıİsimSoyisim.getText().toString());
                                myRef.child(kullanıcıid).child("üyeEmail").setValue(emailText.getText().toString());
                                myRef.child(kullanıcıid).child("üyeFoto").setValue(uri.toString());
                                if (spinnerIller.getSelectedItem().toString()!="Yaşadığınız şehri değiştirin"){
                                    myRef.child(kullanıcıid).child("üyeSehir").setValue(spinnerIller.getSelectedItem().toString());
                                }
                                progressBar2.setVisibility(View.INVISIBLE);
                                Toast.makeText(ProfiliDüzenleActivity.this,"Değişiklikler Kaydedildi!",Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfiliDüzenleActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }


        }else
            Toast.makeText(getApplicationContext(),"İsim soyisim ve email boş bırakılamaz!",Toast.LENGTH_LONG).show();

    }


    public void profilDegisikligiGetir(){
        FirebaseUser user=mAuth.getCurrentUser();
        String id=user.getUid().toString();
        DatabaseReference databaseReference=firebaseDatabase.getReference("Profiller");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();  //json dosyası olarak valueları veriyor
                    String email=String.valueOf(hashMap.get("üyeEmail"));
                    //email güncel kullanıcıya eşitse
                    if (email.matches(mAuth.getCurrentUser().getEmail())){
                        String adSoyad=String.valueOf(hashMap.get("üyeİsimSoyisim"));
                        String sehir=String.valueOf(hashMap.get("üyeSehir"));
                        String pp=String.valueOf(hashMap.get("üyeFoto"));
                        if (adSoyad !=null && sehir !=null){
                            kullanıcıİsimSoyisim.setText(adSoyad);
                            emailText.setText(email);
                            sehirText.setText(sehir);
                            Picasso.get().load(pp).into(circularImageView );
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfiliDüzenleActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }



    public void fotografiDegistir(View view){
        //izinleri kontrol et
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2 && resultCode==RESULT_OK && data!=null){
            degistirilecekFoto= data.getData();      //kayıtlı imagenin adresi
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),degistirilecekFoto);
                circularImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
