package com.example.esra.bitirmeprojesi.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Class.ÜyeProfili;
import com.example.esra.bitirmeprojesi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {


    EditText emailText;
    EditText passwordText;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        emailText=(EditText) findViewById(R.id.main_activity_emailtext);
        passwordText=(EditText) findViewById(R.id.main_activity_şifretext);
        progressBar=findViewById(R.id.main_activity_progressbar);

        FirebaseUser user=mAuth.getCurrentUser();

        if (user!=null){

          Intent intent=new Intent(getApplicationContext(),Main2Activity.class);   //kullanıcı giriş yaptıysa direk git
          startActivity(intent);

        }

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }


    public void signIn(final View view){
        if (emailText.getText().length()>0 && passwordText.getText().length()>0){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){//giriş başarılı ise

                                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                                startActivity(intent);
                            }

                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {//giriş başarısız ise
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(view,"Kullanıcı bulunamadı!",Snackbar.LENGTH_LONG).show();
                }
            });
        }else
            Snackbar.make(view,"Email veya şifre alanları boş bırakılamaz!",Snackbar.LENGTH_LONG).show();

    }

    public void signUp(final View view){
        if ( emailText.getText().length()>0 && passwordText.getText().length()>0){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {   //kayıt başarılı olursa
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(MainActivity.this,"Kullanıcı Oluşturuldu.",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getApplicationContext(), ProfilOluşturActivity.class);
                                startActivity(intent);

                                FirebaseUser user=mAuth.getCurrentUser();
                                final String id = user.getUid().toString();

                                ÜyeProfili üyeProfili = new ÜyeProfili("İsim Soyisim","Yaşadığınız Şehir",emailText.getText().toString(),id,"https://img2.pngdownload.id/20180714/hxu/kisspng-user-profile-computer-icons-login-clip-art-profile-picture-icon-5b49de2f52aa71.9002514115315676633386.jpg");
                                databaseReference.child("Profiller").child(id).setValue(üyeProfili);

                                OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                                    @Override
                                    public void idsAvailable(String userId, String registrationId) {
                                        databaseReference.child("Profiller").child(id).child("Oyuncu ID").setValue(userId);
                                    }
                                });


                            }

                        }
                    }).addOnFailureListener(this, new OnFailureListener() {              //kayıt başarısız olursa
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(view,"Hata!",Snackbar.LENGTH_LONG).show();

                }
            });

        }else
            //Toast.makeText(getApplicationContext(),"İsim, email veya şifre alanları boş bırakılamaz!",Toast.LENGTH_LONG).show();
            Snackbar.make(view,"Email veya şifre alanları boş bırakılamaz!",Snackbar.LENGTH_LONG).show();

    }
    public void  geriGit(View view){
        Intent intent=new Intent(getApplicationContext(),BaşlangıcActivity.class);
        startActivity(intent);
    }
}
