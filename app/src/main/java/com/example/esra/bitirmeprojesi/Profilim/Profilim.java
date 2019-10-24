package com.example.esra.bitirmeprojesi.Profilim;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Profilim extends Fragment {


    TextView pDüzenle,ilanHarGörüntüle,kullanıcıİsimSoyisimText,sehirText;
    CircularImageView circularImageView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    public static Profilim newInstance(){
        return new Profilim();                   //her çağrıldığında profili döndür
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_profilim,container,false);       //container=viewgroup
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        progressBar=rootView.findViewById(R.id.profilim_progressbar);
        circularImageView=rootView.findViewById(R.id.profilim_profilFoto);
        pDüzenle=rootView.findViewById(R.id.profilim_pDüzenle);
        ilanHarGörüntüle=rootView.findViewById(R.id.profilim_ilanHarGörüntüle);
        kullanıcıİsimSoyisimText=rootView.findViewById(R.id.profilim_kullanıcıİsimSoyisim);
        sehirText=rootView.findViewById(R.id.profilim_kullanıcıSehir);
        profilDegisikligiGetir();
        return rootView;
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
                            kullanıcıİsimSoyisimText.setText(adSoyad);
                            sehirText.setText(sehir);
                            Picasso.get().load(pp).into(circularImageView, new Callback() {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

}
