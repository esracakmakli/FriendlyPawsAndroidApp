package com.example.esra.bitirmeprojesi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esra.bitirmeprojesi.R;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

public class BaşlangıcActivity extends AppCompatActivity {

    RoundedImageView imageview2;
    TextView text1,text2,text3;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baslangic);
        imageview2=findViewById(R.id.imageView28);
        text1=findViewById(R.id.textView21);
        text2=findViewById(R.id.textView22);
        text3=findViewById(R.id.textView24);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        if (user!=null){

            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);   //kullanıcı giriş yaptıysa direk git
            startActivity(intent);

        }


    }


    public void basla(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
