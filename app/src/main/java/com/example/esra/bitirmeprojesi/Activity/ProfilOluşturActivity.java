package com.example.esra.bitirmeprojesi.Activity;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

public class ProfilOluşturActivity extends AppCompatActivity {

    CircularImageView foto;
    EditText isimText;
    EditText sehirText;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    private StorageReference mStorageRef;
    Uri eklenenFoto;
    FirebaseAuth auth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_olustur_activity);

        foto=findViewById(R.id.profil_olustur_foto);
        isimText=findViewById(R.id.profil_olustur_isimText);
        sehirText=findViewById(R.id.profil_olustur_sehirText);
        progressBar=findViewById(R.id.profil_olustur_progressbar);
        auth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();


    }

    public void profilOlustur(View view){

        if (isimText.getText().length()>0 && sehirText.getText().length()>0){
            final String isim=isimText.getText().toString();
            final String sehir=sehirText.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            final UUID uuid=UUID.randomUUID();
            final String fotoisim="images/"+uuid+".jpg";
            if (eklenenFoto==null){
                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Profiller");
                databaseReference.child(auth.getCurrentUser().getUid()).child("üyeSehir").setValue(sehir);
                databaseReference.child(auth.getCurrentUser().getUid()).child("üyeFoto").setValue("https://img2.pngdownload.id/20180714/hxu/kisspng-user-profile-computer-icons-login-clip-art-profile-picture-icon-5b49de2f52aa71.9002514115315676633386.jpg");
                databaseReference.child(auth.getCurrentUser().getUid()).child("üyeİsimSoyisim").setValue(isim);
                Toast.makeText(getApplicationContext(),"Profil Oluşturuldu!",Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(i);
            }
            if (eklenenFoto!=null){
                StorageReference storageReference=mStorageRef.child(fotoisim);
                storageReference.putFile(eklenenFoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference newReference=FirebaseStorage.getInstance().getReference(fotoisim);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadURL=uri.toString();
                                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Profiller");
                                databaseReference.child(auth.getCurrentUser().getUid()).child("üyeSehir").setValue(sehir);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("üyeİsimSoyisim").setValue(isim);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("üyeFoto").setValue(downloadURL);
                                Toast.makeText(getApplicationContext(),"Profil Oluşturuldu!",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),Main2Activity.class);
                                startActivity(i);

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }


        }else
            Toast.makeText(getApplicationContext(),"Bilgiler boş geçilemez!",Toast.LENGTH_LONG).show();
    }

    public void fotoEkle (View view){
        //izinleri kontrol et
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            eklenenFoto= data.getData();      //kayıtlı imagenin adresi
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),eklenenFoto);
                foto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void geriGit2(View view){
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
}
