 package com.ayush.farecalculator;
 
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context _context;
    int cityno,choice;
 
    public ViewPagerAdapter(Context context, FragmentManager fm) 
    {
        super(fm);
        _context=context;
 
    }
    public void setVal(int city,int ch)
    {
    	cityno=city;
    	choice=ch;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        Bundle args=new Bundle();
        args.putInt("city", cityno);
        args.putInt("ch",choice);
        switch(position){
        case 0:
            f=Calculate.newInstance(_context);
            f.setArguments(args);
            break;
        case 1:
            f=FareCard.newInstance(_context);
            f.setArguments(args);
            break;
        }
        return f;
    }
    @Override
    public int getCount() 
    {
        return 2;
    }
 
}