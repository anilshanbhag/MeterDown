package com.ayush.farecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends FragmentActivity {
	
	
	
	
	private ViewPager _mViewPager;
	private ViewPagerAdapter _adapter;
	 
    int cityno;
    int choice;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setUpView();
		setTab();
		
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem citySpinner = menu.findItem( R.id.menu_city_spinner);
	    setupCitySpinner( citySpinner );

	    MenuItem typeSpinner = menu.findItem( R.id.menu_type_spinner );
	    setupTypeSpinner( typeSpinner );

	    return super.onCreateOptionsMenu(menu);
		
	}
	private void setupCitySpinner(MenuItem item) 
	{
	    View view = item.getActionView();
	    if (view instanceof Spinner)
	    {
	        Spinner spinner = (Spinner) view;
	        spinner.setAdapter(ArrayAdapter.createFromResource(this,R.array.city_options,android.R.layout.simple_spinner_dropdown_item));
	        spinner.setOnItemSelectedListener(new CitySpinnerActivity());
	    }
	}
	private void setupTypeSpinner(MenuItem item) 
	{
	    View view = item.getActionView();
	    if (view instanceof Spinner)
	    {
	        Spinner spinner = (Spinner) view;
	        spinner.setAdapter(ArrayAdapter.createFromResource(this,R.array.type_options,android.R.layout.simple_spinner_dropdown_item));
	        spinner.setOnItemSelectedListener(new TypeSpinnerActivity());
	    }
	}
	public class CitySpinnerActivity extends Activity implements OnItemSelectedListener
	{
		public void onItemSelected(AdapterView<?> parent,View view,int pos,long id)
		{
			cityno=pos;
		}
		public void onNothingSelected(AdapterView<?> parent){}
	}
	public class TypeSpinnerActivity extends Activity implements OnItemSelectedListener
	{
		public void onItemSelected(AdapterView<?> parent,View view,int pos,long id)
		{
			choice=pos;
		}
		public void onNothingSelected(AdapterView<?> parent){}
	}
	
	private void setUpView()
	{
		_mViewPager=(ViewPager)findViewById(R.id.viewPager);
		_adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
		_adapter.setVal(cityno,choice);
		_mViewPager.setAdapter(_adapter);
		_mViewPager.setCurrentItem(0);
	}
	private void setTab()
	{
		_mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
			
			@Override
			public void onPageScrollStateChanged(int position){}
			@Override
			public void onPageScrolled(int arg0,float arg1,int arg2){}
			@Override
			public void onPageSelected(int position)
			{//todo autogenerated method stub
				switch(position)
				{
				case 0:
					findViewById(R.id.first_tab).setVisibility(View.VISIBLE);
					findViewById(R.id.second_tab).setVisibility(View.INVISIBLE);
					break;
				case 1:
					findViewById(R.id.first_tab).setVisibility(View.INVISIBLE);
					findViewById(R.id.second_tab).setVisibility(View.VISIBLE);
					break;
				}
			}
		});//ask why
	}


};

