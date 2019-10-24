package com.example.esra.bitirmeprojesi.Class;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

public class ÜyeProfili implements Parcelable {

    private String üyeİsimSoyisim;
    private String üyeSehir;
    private String üyeEmail;
    private String üyeUid;
    private String üyeFoto;

    public void setÜyeİsimSoyisim(String üyeİsimSoyisim) {
        this.üyeİsimSoyisim = üyeİsimSoyisim;
    }

    public void setÜyeSehir(String üyeSehir) {
        this.üyeSehir = üyeSehir;
    }

    public void setÜyeEmail(String üyeEmail) {
        this.üyeEmail = üyeEmail;
    }

    public void setÜyeUid(String üyeUid) {
        this.üyeUid = üyeUid;
    }

    public void setÜyeFoto(String üyeFoto) {
        this.üyeFoto = üyeFoto;
    }

    public String getÜyeİsimSoyisim() {

        return üyeİsimSoyisim;
    }

    public String getÜyeSehir() {
        return üyeSehir;
    }

    public String getÜyeEmail() {
        return üyeEmail;
    }

    public String getÜyeUid() {
        return üyeUid;
    }

    public String getÜyeFoto() {
        return üyeFoto;
    }

    public ÜyeProfili(String üyeİsimSoyisim, String üyeSehir, String üyeEmail, String üyeUid, String üyeFoto) {

        this.üyeİsimSoyisim = üyeİsimSoyisim;
        this.üyeSehir = üyeSehir;
        this.üyeEmail = üyeEmail;
        this.üyeUid = üyeUid;
        this.üyeFoto = üyeFoto;
    }

    protected ÜyeProfili(Parcel in) {
        üyeİsimSoyisim=in.readString();
        üyeEmail=in.readString();
        üyeFoto=in.readString();
        üyeSehir=in.readString();
        üyeUid=in.readString();
    }

    public static final Creator<ÜyeProfili> CREATOR = new Creator<ÜyeProfili>() {
        @Override
        public ÜyeProfili createFromParcel(Parcel in) {
            return new ÜyeProfili(in);
        }

        @Override
        public ÜyeProfili[] newArray(int size) {
            return new ÜyeProfili[size];
        }
    };

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(üyeSehir);
        dest.writeString(üyeUid);
        dest.writeString(üyeİsimSoyisim);
        dest.writeString(üyeFoto);
        dest.writeString(üyeEmail);
    }
}
