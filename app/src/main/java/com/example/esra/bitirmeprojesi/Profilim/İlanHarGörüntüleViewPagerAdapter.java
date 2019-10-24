package com.example.esra.bitirmeprojesi.Profilim;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Kayıpİlanların.İlanHareketleriniGörüntüleKayiplarFragment;
import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Sahiplendirme.İlanHareketleriniGörüntüleSahiplenFragment;

public class İlanHarGörüntüleViewPagerAdapter extends FragmentStatePagerAdapter {
    public İlanHarGörüntüleViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment secilen;
        switch (i){
            case 0:
                secilen=İlanHareketleriniGörüntüleKayiplarFragment.newInstance();
                break;
            case 1:
                secilen=İlanHareketleriniGörüntüleSahiplenFragment.newInstance();
                break;
                default:
                    return null;
        }
        return secilen;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence secilenFragment;
        switch (position){
            case 0:
                secilenFragment="Kayıp İlanların";
                break;
            case 1:
                secilenFragment="Sahiplendirme";
                break;
                default:
                    return null;
        }
        return secilenFragment;
    }
}
