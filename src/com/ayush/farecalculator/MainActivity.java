package com.ayush.farecalculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {
	
	
	private Button fareCal;
	private TextView fareView;
	private EditText distanceInp;
	private RadioGroup timeRadioGroup;
	private RadioButton dayButton;
	private RadioButton nightButton;
	
	private ViewPager _mViewPager;
	private ViewPagerAdapter _adapter;
	 
    
    int choice,fare,cityno;
    double factor=1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setUpView();
		setTab();
		
		this.findAllViewById();
		
		distanceInp.setRawInputType(Configuration.KEYBOARD_12KEY);
		
		fareCal.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v)
			{
				int distance=0;
				
				try {
				    distance = Integer.parseInt(distanceInp.getText().toString());
				} catch(NumberFormatException nfe) {
				   System.out.println("Could not parse " + nfe);
				} 
				switch (choice)
				{
					case 0:
						switch (cityno)
						{
							case 0:
								calculateMumbaiAutoFare(distance);
								break;
							case 1:
								
								
						}
				}
			}
		});
		
		
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
	
	private void findAllViewById()
	{
		fareCal=(Button) findViewById(R.id.button1);
		distanceInp=(EditText)findViewById(R.id.edit_text);
		fareView=(TextView)findViewById(R.id.textView1);
		timeRadioGroup=(RadioGroup)findViewById(R.id.search_radio_group);
		dayButton=(RadioButton)findViewById(R.id.day_button);
		nightButton=(RadioButton)findViewById(R.id.night_button);
		
	}
	
	public void displayString(CharSequence message)
	{
		fareView.setText("The Fare is Rs."+message);
	}
	
	private void calculateMumbaiAutoFare(int distance)
	{
		double dist=(distance/200)*0.2;
		double fareTemp=(9.87*dist)*factor;
		if(fareTemp<15)
		{
			fareTemp=15;
		}
		fare=round(fareTemp);
		String s=Integer.toString(fare);
		displayString(s);
	}
	private void calculateMumbaiTaxiFare(int distance)
	{
		double units=(((double)distance)/1000*0.6)+0.04;
		int temp=(int)(units*10);
		units=((double)temp)/10;
		double fareTemp=19.296875*units*factor;
		fare=round(fareTemp);
		String s=Integer.toString(fare);
		displayString(s);
	}
	int round(double d)
	{
	    DecimalFormat twoDForm = new DecimalFormat("#");
	    return Integer.valueOf(twoDForm.format(d));
	}
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.day_button:
	            if (checked)
	                factor=1;
	            break;
	        case R.id.night_button:
	            if (checked)
	                factor=1.25;
	            break;
	    }
	}
	private void setUpView()
	{
		_mViewPager=(ViewPager)findViewById(R.id.viewPager);
		_adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
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

