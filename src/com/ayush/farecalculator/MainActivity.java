package com.ayush.farecalculator;

import java.text.DecimalFormat;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	public static final String PREFS_NAME="MeterDownPrefFile";
	
	private Button fareCal;
	private TextView fareView;
	private EditText distanceInp;
	private EditText unitInp;
	private RadioGroup timeRadioGroup;
	private RadioButton dayButton;
	private RadioButton nightButton;
	private RadioButton taxiButton;
	private RadioButton autoButton;
	private Context context;
	
	int choice=0,fare,cityno,nightMode=0,pref;
    
    String city[]=new String[]{
    	"Mumbai",
    	"Pune",
    	"Bengaluru",
    	"Kolkata",
    	"Hyderabad",
    	"Surat"
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		context=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.findAllViewById();
		
		distanceInp.setRawInputType(Configuration.KEYBOARD_12KEY);
		
		fareCal.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v)
			{
				double distance=0;
				double unit=0;
				
				try {
					distance = Double.parseDouble(distanceInp.getText().toString());
				} catch(NumberFormatException nfe) {
					System.out.println("Could not parse " + nfe);
				} 
				try {
					unit = Double.parseDouble(unitInp.getText().toString());
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
								calculatePuneAutoFare(distance);
								break;
							case 2:
								calculateBengaluruAutoFare(distance);
								break;	
							case 4:
								calculateHyderabadAutoFare(distance);
								break;
							
								
						}
						break;
					case 1:
						switch(cityno)
						{
							case 0:
								calculateMumbaiTaxiFare(distance);
								break;
							case 3:
								calculateKolkataTaxiFare(distance);
								break;
						}
						break;
				}
			}
		});	
		/** Create an array adapter to populate dropdownlist */
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, city);
	    
		/**Enabling dropdown list navigation for the action bar */
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ActionBar actionbar=getActionBar();
		        
		        
		        
		        
	  	/**Defining Navigation listener*/ 
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
        	@Override
         	public boolean onNavigationItemSelected(int itemPosition, long itemId) 
         	{
     			cityno=itemPosition;
     			switch(cityno)
     			{
     				case 1:
     				case 2:
     				case 4:
     				case 5:
     					autoButton.setEnabled(true);
     					autoButton.setChecked(true);
     					taxiButton.setEnabled(false);
     					choice=0;
     					break;
     				case 3:
     					autoButton.setEnabled(false);
     					taxiButton.setEnabled(true);
     					taxiButton.setChecked(true);
     					choice=1;
     					break;
 					default:
 						autoButton.setEnabled(true);
 						taxiButton.setEnabled(true);
 						if(autoButton.isChecked())
 						{
 							choice=0;
 						}
 						else
 						{
 							choice=1;
 						}
 						break;
     			}
     			return false;
         	}
		
        };
		
		getActionBar().setListNavigationCallbacks(adapter, navigationListener);
		
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int pref = settings.getInt("cityno", 0);
        getActionBar().setSelectedNavigationItem(pref);
    }
	 @Override
    protected void onStop(){
		 super.onStop();

		 // We need an Editor object to make preference changes.
		 // All objects are from android.context.Context
		 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putInt("cityno", cityno);

	      // Commit the edits!
	      editor.commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId())
		{
			case R.id.action_farecard:
				FareCard();
				return true;
				
			default:
	            return super.onOptionsItemSelected(item);
				
		}
		
	}
	private void FareCard()
	{
		Intent intent = new Intent(context, WebViewActivity.class);
	    startActivity(intent);
	}
	private void findAllViewById()
	{
		fareCal=(Button) findViewById(R.id.button1);
		distanceInp=(EditText)findViewById(R.id.edit_text_dist);
		unitInp=(EditText)findViewById(R.id.edit_text_unit);
		fareView=(TextView)findViewById(R.id.textView1);
		timeRadioGroup=(RadioGroup)findViewById(R.id.search_radio_group);
		dayButton=(RadioButton)findViewById(R.id.day_button);
		nightButton=(RadioButton)findViewById(R.id.night_button);
		autoButton=(RadioButton)findViewById(R.id.auto_button);
		taxiButton=(RadioButton)findViewById(R.id.taxi_button);
		
		
	}
    public void displayString(CharSequence message)
	{
		fareView.setText("The Fare is Rs."+message);
	}
	private void calculateMumbaiAutoFare(double distance)
	{
		int distance1=(int)(distance*1000);
		double factor;
		if(nightMode==0)
			factor=1;
		else 
			factor=1.25;
		double dist=(distance1/200)*0.2;
		double fareTemp=(9.87*dist);
		if(fareTemp<15)
		{
			fareTemp=15;
		}
		fare=round(fareTemp*factor);
		String s=Integer.toString(fare);
		displayString(s);
	}
	private void calculatePuneAutoFare(double distance)
	{
		double factor;
		if(nightMode==0)
			factor=1;
		else 
			factor=1.5;
		int distance1=(int)(distance*1000);
		int temp=distance1/100;
		double units=((double)(temp))/10;
		if(units<1)
			units=1;
		double fareTemp=11+(units-1)*10;
		fare=round(fareTemp*factor);
		String s=Integer.toString(fare);
		displayString(s);		
	}
	private void calculateKolkataTaxiFare(double distance)
	{
		double factor;
		if(nightMode==0)
			factor=1;
		else 
			factor=1.15;
		int units=(int)(distance*5);
		if(units<10)
		{
			units=10;
		}
		double fareTemp=units*2.4+1;
		fare=round(fareTemp*factor);
		String s=Integer.toString(fare);
		displayString(s);
	}
	private void calculateBengaluruAutoFare(double distance)
	{
		double factor;
		if(nightMode==0)
			factor=1;
		else 
			factor=1.50;
		
		int temp=(int)(distance*10);
		double units=((double) temp)/10;
		if(units<1.8)
			units=1.8;
		double fareTemp=20+(units-1.8)*11;
		fare=round(fareTemp*factor);
		String s=Integer.toString(fare);
		displayString(s);
	}
	private void calculateHyderabadAutoFare(double distance)
	{
		double factor;
		if(nightMode==0)
			factor=1;
		else 
			factor=1.50;
		int temp=(int)(distance*10);
		double units=((double)temp)/10;
		if(units<1.6)
			units=1.6;
		double fareTemp=16+(units-1.6)*9;
		fare=round(fareTemp*factor);
		String s=Integer.toString(fare);
		displayString(s);
	}
	private void calculateMumbaiTaxiFare(double distance)
	{
		int distance1=(int)(distance*1000);
		double factor;
		if(nightMode==0)
			factor=1;
		else 
			factor=1.25;
		double units=(((double)distance1)/1000*0.6)+0.04;
		int temp=(int)(units*10);
		units=((double)temp)/10;
		double fareTemp=19.296875*units;
		fare=round(fareTemp*factor);
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
		// 	Check which radio button was clicked
		switch(view.getId()) {
			case R.id.day_button:
				if (checked)
					nightMode=0;
				break;
			case R.id.night_button:
				if (checked)
					nightMode=1;
				break;
			case R.id.auto_button:
				if(checked)
					choice=0;
				break;
			case R.id.taxi_button:
				if(checked)
					choice=1;
				break;
		}
    }
};
	 




