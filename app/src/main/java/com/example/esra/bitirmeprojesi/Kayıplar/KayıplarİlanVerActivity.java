package com.example.esra.bitirmeprojesi.Kayıplar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Activity.Main2Activity;
import com.example.esra.bitirmeprojesi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.callback.Callback;

public class KayıplarİlanVerActivity extends AppCompatActivity implements KayıpHayvanlarAdapter.MyListener{

    private Spinner spinnerhayvanTürü;
    private Spinner spinnerYaş;
    private Spinner spinnerIller;
    private Spinner spinnerIlceler;
    private Spinner spinnerCinsiyet;
    private Spinner spinnerirk;
    private static String[] cinsiyet=new String[] {"Cinsiyeti","Dişi","Erkek"};
    private static String[] hayvanTürü=new String[] {"Türü","Kedi","Köpek"};
    private static String[] sayılar2=new String[] {"Yaşı","0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    private static String[] iller = new String[] {"Kaybolduğu İl","İSTANBUL", "ANKARA",
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


    private KayıpHayvanlarAdapter kayıpHayvanlarAdapter;
    private ArrayList<KayıpHayvanlar> kayıplarArrayList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    Uri selectedImage;


    EditText ismi;
    EditText ilanAciklama;
    EditText kayıpTarihi;
    EditText iletişim;
    ImageView ilanFoto;
    TextView ilanSahibi;
   private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ver_kayiplar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        kayıpHayvanlarAdapter=new KayıpHayvanlarAdapter(KayıplarİlanVerActivity.this,this);
        kayıplarArrayList=kayıpHayvanlarAdapter.getKayıplarArrayList();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mStorageRef=FirebaseStorage.getInstance().getReference();


        spinnerhayvanTürü=findViewById(R.id.spinnertür);
        veriAdaptoru=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hayvanTürü);
        veriAdaptoru.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhayvanTürü.setAdapter(veriAdaptoru);
        spinnerirk=findViewById(R.id.spinnerirk);
        spinnerhayvanTürü.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals(hayvanTürü[0]))
                    veriAdaptoruırk = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,boşş);
                if(parent.getSelectedItem().toString().equals(hayvanTürü[1]))
                    veriAdaptoruırk = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kediIrkı);
                if(parent.getSelectedItem().toString().equals(hayvanTürü[2]))
                    veriAdaptoruırk = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kopekIrkı);
                veriAdaptoruırk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerirk.setAdapter(veriAdaptoruırk);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYaş=findViewById(R.id.spinneryas);
        veriAdaptoru2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,sayılar2);
        spinnerYaş.setAdapter(veriAdaptoru2);

        spinnerCinsiyet=findViewById(R.id.spinnercinsiyet);
        veriAdaptoru4=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cinsiyet);
        spinnerCinsiyet.setAdapter(veriAdaptoru4);

        spinnerIller = findViewById(R.id.spinneryer);
        veriAdaptoru3= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, iller);
        veriAdaptoru3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIller.setAdapter(veriAdaptoru3);

        spinnerIlceler=findViewById(R.id.spinnerilçe);
        veriAdaptoru5=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,adanaİlçeler);
        veriAdaptoru5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIlceler.setAdapter(veriAdaptoru5);

        spinnerIller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals(iller[0]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,bos);
                if(parent.getSelectedItem().toString().equals(iller[1]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,istanbulİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[2]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,ankaraİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[3]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,izmirİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[4]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,adanaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[5]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,adıyamanİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[6]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,afyonkarahisarİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[7]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,ağrıİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[8]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,aksarayİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[9]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,amasyaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[10]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,antalyaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[11]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,ardahanİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[12]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,artvinİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[13]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,aydınİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[14]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,balıkesirİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[15]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,bartınİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[16]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,batmanİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[17]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,bayburtİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[18]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,bilecikİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[19]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,bingolİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[20]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,bitlisİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[21]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,boluİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[22]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,burdurİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[23]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,bursaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[24]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,canakkaleİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[25]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,cankırıİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[26]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,corumİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[27]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,denizliİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[28]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,diyarbakirİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[29]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,düzceİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[30]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,edirneİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[31]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,elazıgİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[32]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,erzincanİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[33]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,erzurumİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[34]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,eskisehirİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[35]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,gaziantepİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[36]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,giresunİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[37]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,gümüshaneİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[38]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,hakkariİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[39]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,hatayİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[40]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,ıgdırİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[41]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,ıspartaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[42]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kahramanmarasİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[43]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,karabukİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[44]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,karamanİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[45]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,karsİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[46]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kastamonuİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[47]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kayseriİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[48]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kırıkkaleİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[49]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kırklareliİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[50]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kırsehirİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[51]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kilisİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[52]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kocaeliİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[53]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,konyaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[54]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,kütahyaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[55]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,malatyaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[56]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,manisaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[57]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,mardinİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[58]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,mersinİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[59]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,muğlaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[60]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,muşİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[61]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,nevsehirİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[62]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,niğdeİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[63]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,orduİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[64]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,osmaniyeİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[65]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,rizeİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[66]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,sakaryaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[67]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,samsunİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[68]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,siirtİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[69]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,sinopİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[70]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,sivasİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[71]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,sırnakİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[72]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,tekirdağİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[73]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,tokatİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[74]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,trabzonİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[75]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,tunceliİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[76]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,sanlıurfaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[77]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,usakİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[78]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,vanİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[79]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,yalovaİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[80]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,yozgatİlçeler);
                if(parent.getSelectedItem().toString().equals(iller[81]))
                    veriAdaptoru5 = new ArrayAdapter<String>(KayıplarİlanVerActivity.this, android.R.layout.simple_list_item_1,zonguldakİlçeler);
                veriAdaptoru5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIlceler.setAdapter(veriAdaptoru5);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"İl seçimi yapmadınız!",Toast.LENGTH_LONG).show();
            }
        });


        ismi=findViewById(R.id.ilan_activity_isim_text);
        ilanAciklama=findViewById(R.id.ilan_activity_açıklamatext);
        kayıpTarihi=findViewById(R.id.ilan_activity_tarihtext);
        iletişim=findViewById(R.id.iletişim);
        ilanFoto=findViewById(R.id.ilan_activity_imageview);
        ilanSahibi=findViewById(R.id.profilim_kullanıcıİsimSoyisim);
        progressBar=findViewById(R.id.kayıp_ilan_ver_progressBar);



        kayıpTarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                int yil=c.get(Calendar.YEAR);
                int ay=c.get(Calendar.MONTH);
                int gün=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker;
                datePicker=new DatePickerDialog(KayıplarİlanVerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        kayıpTarihi.setText(dayOfMonth+"."+month+"."+year);
                    }
                },yil,ay,gün);

                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(TimePickerDialog.BUTTON_POSITIVE,"Ayarla",datePicker);
                datePicker.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",datePicker);
                datePicker.show();
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


    public void ilanEkle(View view){


        if (ismi.getText().length()>0 && kayıpTarihi.getText().length()>0 && iletişim.getText().length()>0 && spinnerIller.getSelectedItem().toString()!= "Kaybolduğu İl"
                && spinnerYaş.getSelectedItem().toString() !="Yaşı" && spinnerhayvanTürü.getSelectedItem().toString()!="Türü"
                && spinnerCinsiyet.getSelectedItem().toString()!="Cinsiyeti" ){

            final UUID uuid=UUID.randomUUID();
            final String imageName="images/"+uuid+".jpg";

            if (selectedImage!=null){
                progressBar.setVisibility(View.VISIBLE);
                StorageReference storageReference=mStorageRef.child(imageName);
                storageReference.putFile(selectedImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        StorageReference newReference=FirebaseStorage.getInstance().getReference(imageName);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadURL=uri.toString();
                                FirebaseUser user=mAuth.getCurrentUser();
                                String ilanSahip=user.getEmail().toString();
                                String hayvanIsmi=ismi.getText().toString();
                                String hayvanTürü=spinnerhayvanTürü.getSelectedItem().toString();
                                String hayvanYasi=spinnerYaş.getSelectedItem().toString();
                                String acıklama=ilanAciklama.getText().toString();
                                String kayıpYeri=spinnerIller.getSelectedItem().toString();
                                String cinsiyet=spinnerCinsiyet.getSelectedItem().toString();
                                String tarih=kayıpTarihi.getText().toString();
                                String iletisim=iletişim.getText().toString();
                                String ilçe=spinnerIlceler.getSelectedItem().toString();
                                String irk=spinnerirk.getSelectedItem().toString();

                                UUID uuid1=UUID.randomUUID();
                                final String uuidString=uuid1.toString();

                                final String ilanSahibiİd=mAuth.getCurrentUser().getUid();


                                DatabaseReference Reference=firebaseDatabase.getReference("Profiller");
                                Reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                                            HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                                            String email=String.valueOf(hashMap.get("üyeEmail"));
                                            if (email.matches(mAuth.getCurrentUser().getEmail())) {
                                                String kullanıcı = String.valueOf(hashMap.get("üyeİsimSoyisim"));
                                                if (kullanıcı!= null ) {

                                                    myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("İlan Sahibi İsim Soyisim").setValue(kullanıcı);
                                                }
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("İlan İD").setValue(uuidString);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Hayvan İsmi").setValue(hayvanIsmi);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Hayvan Türü").setValue(hayvanTürü);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Hayvan Irkı").setValue(irk);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Hayvan Yaşı").setValue(hayvanYasi);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("İlan Açıklaması").setValue(acıklama);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Kaybolduğu İl").setValue(kayıpYeri);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Kaybolduğu Tarih").setValue(tarih);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Cinsiyet").setValue(cinsiyet);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("İlan Sahibi").setValue(ilanSahip);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("İlan Sahibi İD").setValue(ilanSahibiİd);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("İletişim Bilgileri").setValue(iletisim);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Fotoğraf").setValue(downloadURL);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("Kaybolduğu İlçe").setValue(ilçe);
                                myRef.child("Kayıp Hayvan İlanları").child(uuidString).child("İlan Zamanı").setValue(ServerValue.TIMESTAMP);

                                final DatabaseReference databaseReference=firebaseDatabase.getReference("Profiller");

                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("İlan İD").setValue(uuidString);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Hayvan İsmi").setValue(hayvanIsmi);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Hayvan Türü").setValue(hayvanTürü);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Hayvan Irkı").setValue(irk);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Hayvan Yaşı").setValue(hayvanYasi);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("İlan Açıklaması").setValue(acıklama);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Cinsiyet").setValue(cinsiyet);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Kaybolduğu İl").setValue(kayıpYeri);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Kaybolduğu Tarih").setValue(tarih);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("İletişim Bilgileri").setValue(iletisim);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Fotoğraf").setValue(downloadURL);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("Kaybolduğu İlçe").setValue(ilçe);
                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(uuidString).child("İlan Zamanı").setValue(ServerValue.TIMESTAMP);

                                kayıplarArrayList.clear();
                                Toast.makeText(getApplicationContext(),"İlan Eklendi!",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                                startActivity(intent);

                            }
                        });

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }else

                Toast.makeText(getApplicationContext(),"Fotoğraf seçmediniz!",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"Bilgiler boş geçilemez!",Toast.LENGTH_LONG).show();
        }

    }



    public void selectImage(View view){
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
           selectedImage= data.getData();      //kayıtlı imagenin adresi
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                ilanFoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void MyListener(KayıpHayvanlar kayıpHayvanlar) {
        Intent intent=PopUpKayiplarActivity.newIntent(KayıplarİlanVerActivity.this,kayıpHayvanlar);
        startActivity(intent);
    }

    private ArrayAdapter<String> veriAdaptoru;
    private ArrayAdapter<String> veriAdaptoru2;
    private ArrayAdapter<String> veriAdaptoru3;
    private ArrayAdapter<String> veriAdaptoru4;
    private ArrayAdapter<String> veriAdaptoru5;
    private ArrayAdapter<String> veriAdaptoruırk;

    private static String[] bos=new String[] {"İlçe"};
    private static String[] adanaİlçeler=new String[] {" Aladağ " , " Ceyhan " , " Çukurova " , " Feke " , " İmamoğlu " , " Karaisalı " , " Karataş " , " Kozan " , " Pozantı " , " Saimbeyli " , " Sarıçam " , "Seyhan " , "Tufanbeyli " , " Yumurtalık " , " Yüreğir "} ;
    private static String[] adıyamanİlçeler=new String[] { " Adıyaman " , " Besni " , " Çelikhan " , " Gerger " , " Gölbaşı " , " Kâhta " , " Samsat " , " Sincik " , " Tut " };
    private static String[] afyonkarahisarİlçeler=new String[] {" Afyonkarahisar " , " Başmakçı " , " Bayat " , " Bolvadin " , " Çay " , " Çobanlar " , " Dazkırı " , " Dinar " , " Emirdağ " , " Evciler " , " Hocalar " , "İhsaniye " , "İscehisar " , " Kızılören " , " Sandıklı " , " Sinanpaşa " , " Sultandağı " , " Şuhut "};
    private static String[] ağrıİlçeler=new String[] {" Ağrı " , " Diyadin " , " Doğubayazıt " , " Eleşkirt " , " Hamur " , " Patnos " , " Taşlıçay " , " Tutak " };
    private static String[] aksarayİlçeler=new String[] { " Ağaçören " , " Aksaray " , " Eskil " , " Gülağaç " , " Güzelyurt " , " Ortaköy " , " Sarıyahşi " };
    private static String[] amasyaİlçeler=new String[] {" Amasya " , " Göynücek " , " Gümüşhacıköy " , " Hamamözü " , " Merzifon " , " Suluova " , " Taşova " };
    private static String[] ankaraİlçeler=new String[] { " Akyurt " , " Altındağ " , " Ayaş " , " Balâ " , " Beypazarı " , " Çamlıdere " , " Çankaya " , " Çubuk " , " Elmadağ " , " Etimesgut " , " Evren " , "Gölbaşı " , "Güdül " , " Haymana " , " Kalecik " , " Kahramankazan " , " Keçiören " , " Kızılcahamam " , " Mamak " , " Nallıhan " , " Polatlı " , " Pursaklar " , " Sincan " , " Şereflikoçhisar " , "Yenimahalle " };
    private static String[] antalyaİlçeler=new String[] { " Akseki " , " Aksu " , " Alanya " , " Döşemealtı " , " Elmalı " , " Finike " , " Gazipaşa " , " Gündoğmuş " , " İbradı " , " Demre " , " Kaş " , "Kemer " , "Kepez " , " Konyaaltı " , " Korkuteli " , " Kumluca " , " Manavgat " , " Muratpaşa " , " Serik " };
    private static String[] ardahanİlçeler=new String[] {" Ardahan " , " Çıldır " , " Damal " , " Göle " , " Hanak " , " Posof "};
    private static String[] artvinİlçeler=new String[] {" Ardanuç " , " Arhavi " , " Artvin " , " Borçka " , " Hopa " , " Murgul " , " Şavşat " , " Yusufeli " };
    private static String[] aydınİlçeler=new String[] {" Bozdoğan " , " Buharkent " , " Çine " , " Didim " , " Efeler " , " Germencik " , " İncirliova " , " Karacasu " , " Karpuzlu " , " Koçarlı " , " Köşk " , "Kuşadası " , "Kuyucak " , " Nazilli " , " Söke " , " Sultanhisar " , " Yenipazar " };
    private static String[] balıkesirİlçeler=new String[] {" Altıeylül " , " Ayvalık " , " Balya " , " Bandırma " , " Bigadiç " , " Burhaniye " , " Dursunbey " , " Edremit " , " Erdek " , " Gömeç " , " Gönen " , "Havran " , "İvrindi " , " Karesi " , " Kepsut " , " Manyas " , " Marmara " , " Savaştepe " , " Sındırgı " , " Susurluk " };
    private static String[] bartınİlçeler=new String[] {" Amasra " , " Bartın " , " Kurucaşile " , " Ulus " };
    private static String[] batmanİlçeler=new String[] { " Batman " , " Beşiri " , " Gercüş " , " Hasankeyf " , " Kozluk " , " Sason " };
    private static String[] bayburtİlçeler=new String[] {" Aydıntepe " , " Bayburt (İl merkezi) " , " Demirözü "};
    private static String[] bilecikİlçeler=new String[] {" Bilecik " , " Bozüyük " , " Gölpazarı " , " İnhisar " , " Osmaneli " , " Pazaryeri " , " Söğüt " , " Yenipazar " };
    private static String[] bingolİlçeler=new String[] { " Adaklı " , " Bingöl " , " Genç " , " Karlıova " , " Kiğı " , " Solhan " , " Yayladere " , " Yedisu " };
    private static String[] bitlisİlçeler=new String[] { " Adilcevaz " , " Ahlat " , " Bitlis " , " Güroymak " , " Hizan " , " Mutki " , " Tatvan " };
    private static String[] boluİlçeler=new String[] {" Bolu " , " Dörtdivan " , " Gerede " , " Göynük " , " Kıbrıscık " , " Mengen " , " Mudurnu " , " Seben " , " Yeniçağa " };
    private static String[] burdurİlçeler=new String[] {" Ağlasun " , " Altınyayla " , " Bucak " , " Burdur " , " Çavdır " , " Çeltikçi " , " Gölhisar " , " Karamanlı " , " Kemer " , " Tefenni " , " Yeşilova " };
    private static String[] bursaİlçeler=new String[] {" Büyükorhan " , " Gemlik " , " Gürsu " , " Harmancık " , " İnegöl " , " İznik " , " Karacabey " , " Keles " , " Kestel " , " Mudanya " , " Mustafakemalpaşa " ," Nilüfer " ," Orhaneli " , " Orhangazi " , " Osmangazi " , " Yenişehir " , " Yıldırım " };
    private static String[] canakkaleİlçeler=new String[] {" Ayvacık " , " Bayramiç " , " Biga " , " Bozcaada " , " Çan " , " Çanakkale " , " Eceabat " , " Ezine " , " Gelibolu " , " Gökçeada " , " Lapseki " , "Yenice " };
    private static String[] cankırıİlçeler=new String[] {" Atkaracalar " , " Bayramören " , " Çankırı " , " Çerkeş " , " Eldivan " , " Ilgaz " , " Kızılırmak " , " Korgun " , " Kurşunlu " , " Orta " , " Şabanözü " ," Yapraklı " };
    private static String[] corumİlçeler=new String[] { " Alaca " , " Bayat " , " Boğazkale " , " Çorum " , " Dodurga " , " İskilip " , " Kargı " , " Laçin " , " Mecitözü " , " Oğuzlar " , " Ortaköy " , "Osmancık " , "Sungurlu " , " Uğurludağ " };
    private static String[] denizliİlçeler=new String[] {" Acıpayam " , " Babadağ " , " Baklan " , " Bekilli " , " Beyağaç " , " Bozkurt " , " Buldan " , " Çal " , " Çameli " , " Çardak " , " Çivril " , "Güney " , "Honaz " , " Kale " , " Merkezefendi " , " Pamukkale " , " Sarayköy " , " Serinhisar " , " Tavas "};
    private static String[] diyarbakirİlçeler=new String[] {" Bağlar " , " Bismil " , " Çermik " , " Çınar " , " Çüngüş " , " Dicle " , " Eğil " , " Ergani " , " Hani " , " Hazro " , " Kayapınar " , "Kocaköy " , " Kulp" , " Lice " , " Silvan " , " Sur " , " Yenişehir " };
    private static String[] düzceİlçeler=new String[] {" Akçakoca " , " Cumayeri " , " Çilimli " , " Düzce " , " Gölyaka " , " Gümüşova " , " Kaynaşlı " , " Yığılca " };
    private static String[] edirneİlçeler=new String[] {" Enez " , " Havsa " , " İpsala " , " Keşan " , " Lalapaşa " , " Meriç " , " Merkez " , " Süloğlu " , " Uzunköprü " };
    private static String[] elazıgİlçeler=new String[] {" Ağın " , " Alacakaya " , " Arıcak " , " Baskil " , " Elâzığ " , " Karakoçan " , " Keban " , " Kovancılar " , " Maden " , " Palu " , " Sivrice " };
    private static String[] erzincanİlçeler=new String[] {" Çayırlı " , " Erzincan " , " İliç " , " Kemah " , " Kemaliye " , " Otlukbeli " , " Refahiye " , " Tercan " , " Üzümlü " };
    private static String[] erzurumİlçeler=new String[] {" Aşkale " , " Aziziye " , " Çat " , " Hınıs " , " Horasan " , " İspir " , " Karaçoban " , " Karayazı " , " Köprüköy " , " Narman " , " Oltu " , "Olur " , "Palandöken " , " Pasinler " , " Pazaryolu " , " Şenkaya " , " Tekman " , " Tortum " , " Uzundere " , " Yakutiye " };
    private static String[] eskisehirİlçeler=new String[] {" Alpu " , " Beylikova " , " Çifteler " , " Günyüzü " , " Han " , " İnönü " , " Mahmudiye " , " Mihalgazi " , " Mihalıççık " , " Odunpazarı " , " Sarıcakaya " ," Seyitgazi " ," Sivrihisar " , " Tepebaşı " };
    private static String[] gaziantepİlçeler=new String[] { " Araban " , " İslahiye " , " Karkamış " , " Nizip " , " Nurdağı " , " Oğuzeli " , " Şahinbey " , " Şehitkâmil " , " Yavuzeli " };
    private static String[] giresunİlçeler=new String[] {" Alucra " , " Bulancak " , " Çamoluk " , " Çanakçı " , " Dereli " , " Doğankent " , " Espiye " , " Eynesil " , " Giresun " , " Görele " , " Güce " , "Keşap " , "Piraziz " , " Şebinkarahisar " , " Tirebolu " , " Yağlıdere " };
    private static String[] gümüshaneİlçeler=new String[] {" Gümüşhane " , " Kelkit " , " Köse " , " Kürtün " , " Şiran " , " Torul " };
    private static String[] hakkariİlçeler=new String[] {" Çukurca " , " Hakkâri " , " Şemdinli " , " Yüksekova "};
    private static String[] hatayİlçeler=new String[] { " Altınözü " , " Antakya " , " Arsuz " , " Belen " , " Defne " , " Dörtyol " , " Erzin " , " Hassa " , " İskenderun " , " Kırıkhan " , " Kumlu " , "Payas " , "Reyhanlı " , " Samandağ " , " Yayladağı " };
    private static String[] ıgdırİlçeler=new String[] {" Aralık " , " Iğdır " , " Karakoyunlu " , " Tuzluca " };
    private static String[] ıspartaİlçeler=new String[] { " Aksu " , " Atabey " , " Eğirdir " , " Gelendost " , " Gönen " , " Isparta " , " Keçiborlu " , " Senirkent " , " Sütçüler " , " Şarkikaraağaç " , " Uluborlu " ," Yalvaç " ," Yenişarbademli " };
    private static String[] istanbulİlçeler=new String[] { " Adalar " , " Arnavutköy " , " Ataşehir " , " Avcılar " , " Bağcılar " , " Bahçelievler " , " Bakırköy " , " Başakşehir " , " Bayrampaşa " , " Beşiktaş " , " Beykoz " ," Beylikdüzü " ," Beyoğlu " , " Büyükçekmece " , " Çatalca " , " Çekmeköy " , " Esenler " , " Esenyurt " , " Eyüp " , " Fatih " , " Gaziosmanpaşa " , " Güngören " , " Kadıköy " , " Kağıthane " , "Kartal " , " Küçükçekmece" , " Maltepe " , " Pendik " , " Sancaktepe " , " Sarıyer " , " Silivri " , " Sultanbeyli " , " Sultangazi " , " Şile " , " Şişli " , " Tuzla " , " Ümraniye " , " Üsküdar " , "Zeytinburnu " };
    private static String[] izmirİlçeler=new String[] { " Aliağa " , " Balçova " , " Bayındır " , " Bayraklı " , " Bergama " , " Beydağ " , " Bornova " , " Buca " , " Çeşme " , " Çiğli " , " Dikili " , "Foça " , "Gaziemir " , " Güzelbahçe " , " Karabağlar " , " Karaburun " , " Karşıyaka " , " Kemalpaşa " , " Kınık " , " Kiraz " , " Konak " , " Menderes " , " Menemen " , " Narlıdere " , " Ödemiş ", " Seferihisar ", " Selçuk " , " Lastik " , " Torbalı " , " Urla " };
    private static String[] kahramanmarasİlçeler=new String[] {" Afşin " , " Andırın " , " Çağlayancerit " , " Dulkadiroğlu " , " Ekinözü " , " Elbistan " , " Göksun " , " Nurhak " , " Onikişubat " , " Pazarcık " , " Türkoğlu " };
    private static String[] karabukİlçeler=new String[] {" Eflani " , " Eskipazar " , " Karabük " , " Ovacık " , " Safranbolu " , " Yenice " };
    private static String[] karamanİlçeler=new String[] { " Ayrancı " , " Başyayla " , " Ermenek " , " Karaman " , " Kazımkarabekir " , " Sarıveliler " };
    private static String[] karsİlçeler=new String[] {" Akyaka " , " Arpaçay " , " Digor " , " Kağızman " , " Kars " , " Sarıkamış " , " Selim " , " Susuz " };
    private static String[] kastamonuİlçeler=new String[] {" Abana " , " Ağlı " , " Araç " , " Azdavay " , " Bozkurt " , " Cide " , " Çatalzeytin " , " Daday " , " Devrekani " , " Doğanyurt " , " Hanönü " , "İhsangazi " , "İnebolu " , " Kastamonu " , " Küre " , " Pınarbaşı " , " Seydiler " , " Şenpazar " , " Taşköprü " , " Tosya " };
    private static String[] kayseriİlçeler=new String[] { " Akkışla " , " Bünyan " , " Develi " , " Felahiye " , " Hacılar " , " İncesu " , " Kocasinan " , " Melikgazi " , " Özvatan " , " Pınarbaşı " , " Sarıoğlan " ," Sarız " , "Talas " , " Tomarza " , " Yahyalı " , " Yeşilhisar " };
    private static String[] kırıkkaleİlçeler=new String[] {" Bahşılı " , " Balışeyh " , " Çelebi " , " Delice " , " Karakeçili " , " Keskin " , " Kırıkkale " , " Sulakyurt " , " Yahşihan " };
    private static String[] kırklareliİlçeler=new String[] {" Babaeski " , " Demirköy " , " Kırklareli " , " Kofçaz " , " Lüleburgaz " , " Pehlivanköy " , " Pınarhisar " , " Vize " };
    private static String[] kırsehirİlçeler=new String[] {" Akçakent " , " Akpınar " , " Boztepe " , " Çiçekdağı " , " Kaman " , " Kırşehir " , " Mucur " };
    private static String[] kilisİlçeler=new String[] { " Elbeyli " , " Kilis " , " Musabeyli " , " Polateli "};
    private static String[] kocaeliİlçeler=new String[] {" Başiskele " , " Çayırova " , " Darıca " , " Derince " , " Dilovası " , " Gebze " , " Gölcük " , " İzmit " , " Kandıra " , " Karamürsel " , " Kartepe " , "Körfez " };
    private static String[] konyaİlçeler=new String[] {" Ahırlı " , " Akören " , " Akşehir " , " Altınekin " , " Beyşehir " , " Bozkır " , " Cihanbeyli " , " Çeltik " , " Çumra " , " Derbent " , " Derebucak " , "Doğanhisar " , "Emirgazi " , " Ereğli " , " Güneyini " , " Hadım " , " Halkapınar " , " Hüyük " , " Ilgın " , " Kadınhanı " , " Karapınar " , " Karatay " , " Kulu " , " Meram " , " Sarayönü " ," Selçuklu " ," Seydişehir " , " Taşkent " , " Tuzlukçu " , " Yalıhüyük " , " Yunak " };
    private static String[] kütahyaİlçeler=new String[] { " Altıntaş " , " Aslanapa " , " Çavdarhisar " , " Domaniç " , " Dumlupınar " , " Emet " , " Gediz " , " Hisarcık " , " Kütahya " , " Pazarlar " , " Şaphane " ," Simav " , "Tavşanlı " };
    private static String[] malatyaİlçeler=new String[] {" Akçadağ " , " Arapgir " , " Arguvan " , " Battalgazi " , " Darende " , " Doğanşehir " , " Doğanyol " , " Hekimhan " , " Kale " , " Kuluncak " , " Pütürge " ," Yazıhan " ," Yeşilyurt " };
    private static String[] manisaİlçeler=new String[] {" Ahmetli " , " Akhisar " , " Alaşehir " , " Demirci " , " Gölmarmara " , " Gördes " , " Kırkağaç " , " Köprübaşı " , " Kula " , " Salihli " , " Sarıgöl " , "Saruhanlı " , "Selendi " , " Soma " , " Şehzadeler " , " Turgutlu " , " Yunusemre " };
    private static String[] mardinİlçeler=new String[] { " Artuklu " , " Dargeçit " , " Derik " , " Kızıltepe " , " Mazıdağı " , " Midyat " , " Nusaybin " , " Ömerli " , " Savur " , " Yeşilli " };
    private static String[] mersinİlçeler=new String[] { " Akdeniz " , " Anamur " , " Aydıncık " , " Bozyazı " , " Çamlıyayla " , " Erdemli " , " Gülnar " , " Mezitli " , " Mut " , " Silifke " , " Tarsus " , "Toroslar " , "Yenişehir " };
    private static String[] muğlaİlçeler=new String[] {" Bodrum " , " Dalaman " , " Datça " , " Fethiye " , " Kavaklıdere " , " Köyceğiz " , " Marmaris " , " Menteşe " , " Milas " , " Ortaca " , " Seydikemer " , "Ula " , "Yatağan " };
    private static String[] muşİlçeler=new String[] {" Bulanık " , " Hasköy " , " Korkut " , " Malazgirt " , " Muş " , " Varto " };
    private static String[] nevsehirİlçeler=new String[] {" Acıgöl " , " Avanos " , " Derinkuyu " , " Gülşehir " , " Hacıbektaş " , " Kozaklı " , " Nevşehir " , " Ürgüp " };
    private static String[] niğdeİlçeler=new String[] {" Altunhisar " , " Bor " , " Çamardı " , " Çiftlik " , " Niğde " , " Ulukışla " };
    private static String[] orduİlçeler=new String[] {" Akkuş " , " Altınordu " , " Aybastı " , " Çamaş " , " Çatalpınar " , " Çaybaşı " , " Fatsa " , " Gölköy " , " Gülyalı " , " Gürgentepe " , " İkizce " , "Kabadüz " , "Kabataş " , " Korgan " , " Kumru " , " Mesudiye " , " Perşembe " , " Ulubey " , " Ünye " };
    private static String[] osmaniyeİlçeler=new String[] {" Bahçe " , " Düziçi " , " Hasanbeyli " , " Kadirli " , " Osmaniye " , " Sumbas " , " Toprakkale " };
    private static String[] rizeİlçeler=new String[] {" Ardeşen " , " Çamlıhemşin " , " Çayeli " , " Derepazarı " , " Fındıklı " , " Güneysu " , " Hemşin " , " İkizdere " , " İyidere " , " Kalkandere " , " Pazar " ," Rize " };
    private static String[] sakaryaİlçeler=new String[] { " Adapazarı " , " Akyazı " , " Arifiye " , " Erenler " , " Ferizli " , " Geyve " , " Hendek " , " Karapürçek " , " Karasu " , " Kaynarca " , " Kocaali " , "Pamukova " , "Sapanca " , " Serdivan " , " Söğütlü " , " Taraklı " };
    private static String[] samsunİlçeler=new String[] {" Alaçam " , " Asarcık " , " Atakum " , " Ayvacık " , " Bafra " , " Canik " , " Çarşamba " , " Havza " , " İlkadım " , " Kavak " , " Ladik " , "Ondokuzmayıs " , "Salıpazarı " , " Tekkeköy " , " Terme " , " Vezirköprü " , " Yakakent " };
    private static String[] siirtİlçeler=new String[] { " Siirt " , " Tillo " , " Baykan " , " Eruh " , " Kurtalan " , " Pervari " , " Şirvan " };
    private static String[] sinopİlçeler=new String[] { " Ayancık " , " Boyabat " , " Dikmen " , " Durağan " , " Erfelek " , " Gerze " , " Saraydüzü " , " Sinop " , " Türkeli " };
    private static String[] sivasİlçeler=new String[] { " Akıncılar " , " Altınyayla " , " Divriği " , " Doğanşar " , " Gemerek " , " Gölova " , " Hafik " , " İmranlı " , " Kangal " , " Koyulhisar " , " Sivas " , "Suşehri " , "Şarkışla " , " Ulaş " , " Yıldızeli " , " Zara " , " Gürün " };
    private static String[] sanlıurfaİlçeler=new String[] {" Akçakale " , " Birecik " , " Bozova " , " Ceylanpınar " , " Eyyübiye " , " Halfeti " , " Haliliye " , " Harran " , " Hilvan " , " Karaköprü " , " Siverek " ," Suruç " , "Viranşehir " };
    private static String[] sırnakİlçeler=new String[] {" Beytüşşebap " , " Cizre " , " Güçlükonak " , " İdil " , " Silopi " , " Şırnak " , " Uludere " };
    private static String[] tekirdağİlçeler=new String[] {" Çerkezköy " , " Çorlu " , " Ergene " , " Hayrabolu " , " Kapaklı " , " Malkara " , " Marmara Ereğlisi " , " Muratlı " , " Saray " , " Süleymanpaşa " , " Şarköy " };
    private static String[] tokatİlçeler=new String[] {" Almus " , " Artova " , " Başçiftlik " , " Erbaa " , " Niksar " , " Pazar " , " Reşadiye " , " Sulusaray " , " Tokat " , " Turhal " , " Yeşilyurt " , "Zile " };
    private static String[] trabzonİlçeler=new String[] {" Akçaabat " , " Araklı " , " Arsin " , " Beşikdüzü " , " Çarşıbaşı " , " Çaykara " , " Dernekpazarı " , " Düzköy " , " Hayrat " , " Köprübaşı " , " Maçka " ," Of " , "Ortahisar " , " Sürmene " , " Şalpazarı " , " Tonya " , " Vakfıkebir " , " Yomra " };
    private static String[] tunceliİlçeler=new String[] {" Çemişgezek " , " Hozat " , " Mazgirt " , " Nazımiye " , " Ovacık " , " Pertek " , " Pülümür " , " Tunceli " };
    private static String[] usakİlçeler=new String[] {" Banaz " , " Eşme " , " Karahallı " , " Sivaslı " , " Ulubey " , " Uşak " };
    private static String[] vanİlçeler=new String[] {" Bahçesaray " , " Başkale " , " Çaldıran " , " Çatak " , " Edremit " , " Erciş " , " Gevaş " , " Gürpınar " , " İpekyolu " , " Muradiye " , " Özalp " , "Saray " , "Tuşba " };
    private static String[] yalovaİlçeler=new String[] { " Altınova " , " Armutlu " , " Çınarcık " , " Çiftlikköy " , " Termal " , " Yalova " };
    private static String[] yozgatİlçeler=new String[] { " Akdağmadeni " , " Aydıncık " , " Boğazlıyan " , " Çandır " , " Çayıralan " , " Çekerek " , " Kadışehri " , " Saraykent " , " Sarıkaya " , " Sorgun " , " Şefaatli " ," Yenifakılı " ," Yerköy " , " Yozgat " };
    private static String[] zonguldakİlçeler=new String[] { " Alaplı " , " Çaycuma " , " Devrek " , " Gökçebey " , " Kilimli " , " Kozlu " , " Karadeniz Ereğli " , " Zonguldak " };

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

