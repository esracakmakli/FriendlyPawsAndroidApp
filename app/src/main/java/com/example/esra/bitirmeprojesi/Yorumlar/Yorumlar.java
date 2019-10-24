package com.example.esra.bitirmeprojesi.Yorumlar;

import android.os.Parcel;
import android.os.Parcelable;

public class Yorumlar implements Parcelable {

    private String yorumSahibi;
    private String yorum;
    private String yorumid;
    private String yorumTarihi;
    private String yorumSahibiFoto;

    public Yorumlar(Parcel in) {
        yorum=in.readString();
        yorumSahibi=in.readString();
        yorumSahibiFoto=in.readString();
        yorumTarihi=in.readString();
        yorumid=in.readString();
    }

    public Yorumlar() {
    }

    public Yorumlar(String yorumSahibi, String yorum, String yorumTarihi, String yorumSahibiFoto, String yorumid) {
        this.yorumSahibi = yorumSahibi;
        this.yorum = yorum;
        this.yorumTarihi = yorumTarihi;
        this.yorumSahibiFoto = yorumSahibiFoto;
        this.yorumid=yorumid;
    }

    public String getYorumid() {
        return yorumid;
    }

    public void setYorumid(String yorumid) {
        this.yorumid = yorumid;
    }

    public String getYorumSahibi() {
        return yorumSahibi;
    }

    public void setYorumSahibi(String yorumSahibi) {
        this.yorumSahibi = yorumSahibi;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public String getYorumTarihi() {
        return yorumTarihi;
    }

    public void setYorumTarihi(String yorumTarihi) {
        this.yorumTarihi = yorumTarihi;
    }

    public String getYorumSahibiFoto() {
        return yorumSahibiFoto;
    }

    public void setYorumSahibiFoto(String yorumSahibiFoto) {
        this.yorumSahibiFoto = yorumSahibiFoto;
    }

    public static final Creator<Yorumlar> CREATOR = new Creator<Yorumlar>() {
        @Override
        public Yorumlar createFromParcel(Parcel in) {
            return new Yorumlar(in);
        }

        @Override
        public Yorumlar[] newArray(int size) {
            return new Yorumlar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(yorum);
        dest.writeString(yorumSahibi);
        dest.writeString(yorumSahibiFoto);
        dest.writeString(yorumTarihi);
        dest.writeString(yorumid);
    }

}
