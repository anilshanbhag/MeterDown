package com.ayush.farecalculator;
 
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class FareCard extends Fragment {
 
    public static Fragment newInstance(Context context) {
        FareCard f = new FareCard();
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fare_card, null);
        return root;
    }
 
}