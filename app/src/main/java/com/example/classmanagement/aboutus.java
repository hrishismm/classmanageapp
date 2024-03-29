package com.example.classmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class aboutus extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0");
        return new AboutPage(getContext())
                .isRTL(false)
                .setDescription(getString(R.string.app_description))
                .addGroup(getString(R.string.contact_group))
                .setImage(R.drawable.bhatt)
                .addEmail("bhatticse@gmail.com", "Email")
                .addFacebook(getString(R.string.fb),"Facebook")
                .addTwitter(getString(R.string.fb),"Twitter")
                .addInstagram(getString(R.string.insta),"Instagram")
                .create();


    }
}
