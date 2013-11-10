package com.exzalt.meterdown;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {
	
	public static final String PREFS_NAME="MeterDownPrefFile";
	
	private Button fareCal;
	private TextView fareView;
	private EditText distanceInp;
	private EditText unitInp;
	private RadioButton dayButton;
	private RadioButton nightButton;
	private RadioButton taxiButton;
	private RadioButton autoButton;
	private RadioButton unitButton;
	private RadioButton distButton;
	private Context context;
	
	int choice,fare,cityno,nightMode,pref,inpmode;
	double distance,unit;
	
    
    String city[]=new String[]{
    	"Mumbai",
    	"Pune",
    	"Bengaluru",
    	"Kolkata",
    	"Hyderabad",
    	"Delhi"
    };
    public MainActivity()
    {
    	choice=0;
    	cityno=0;
    	inpmode=0;
    	nightMode=0;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		context=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.findAllViewById();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		unitInp.setEnabled(false);
		unitInp.setInputType(InputType.TYPE_NULL);
		distanceInp.setEnabled(true);
		distanceInp.setRawInputType(Configuration.KEYBOARD_12KEY);
		distanceInp.requestFocus();
		
		fareCal.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v)
			{
				if(inpmode==0){
					try {
						distance = Double.parseDouble(distanceInp.getText().toString());
					} catch(NumberFormatException nfe) {
						System.out.println("Could not parse " + nfe);
					} 
				}
				else{
					try {
						unit = Double.parseDouble(unitInp.getText().toString());
					} catch(NumberFormatException nfe) {
						System.out.println("Could not parse " + nfe);
					}
				}
				if(inpmode==0)
				{
					switch (choice)
					{	
						case 0:
							switch (cityno)
							{
								case 0:
									calculateMumbaiAutoUnit();	
									calculateMumbaiAutoFare();
									break;
								case 1:
									calculatePuneAutoUnit();
									calculatePuneAutoFare();
									break;
								case 2:
									calculateBengaluruAutoUnit();
									calculateHyderabadAutoFare();
									break;	
								case 4:
									calculateHyderabadAutoUnit();
									calculateHyderabadAutoFare();
									break;
								case 5:
									calculateDelhiAutoUnit();
									calculateDelhiAutoFare();
									break;
							}
							break;
						case 1:
							switch(cityno)
							{
								case 0:
									calculateMumbaiTaxiUnit();
									calculateMumbaiTaxiFare();
									break;
								case 3:
									calculateKolkataTaxiUnit();
									calculateKolkataTaxiFare();
									break;
								case 5:
									calculateDelhiTaxiUnit();
									calculateDelhiTaxiFare();
									break;
							}
							break;
					}
					unit=roundTwoDec(unit);
					unitInp.setText(Double.toString(unit));
				}
			
				else if(inpmode==1)
				{
					switch (choice)
					{	
						case 0:
							switch (cityno)
							{
								case 0:
									calculateMumbaiAutoDist();	
									calculateMumbaiAutoFare();
									break;
								case 1:
									calculatePuneAutoDist();
									calculatePuneAutoFare();
									break;
								case 2:
									calculateBengaluruAutoDist();
									calculateBengaluruAutoFare();
									break;	
								case 4:
									calculateHyderabadAutoDist();
									calculateHyderabadAutoFare();
									break;	
								case 5:
									calculateDelhiAutoDist();
									calculateDelhiTaxiFare();
									break;
							}
							break;
						case 1:
							switch(cityno)
							{
								case 0:
									calculateMumbaiTaxiDist();
									calculateMumbaiTaxiFare();
									break;
								case 3:
									calculateKolkataTaxiDist();
									calculateKolkataTaxiFare();
									break;
								case 5:
									calculateDelhiTaxiDist();
									calculateDelhiTaxiFare();
									break;
							}
							break;
							
					}
					distance=roundTwoDec(distance);
					distanceInp.setText(Double.toString(distance));
				}
				displayString(Integer.toString(fare));
				
			}
		});	
			/** Create an array adapter to populate dropdownlist */
	    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, city);
	    	
		/**Enabling dropdown list navigation for the action bar */
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		        
		        
		        
		        
	  	/**Defining Navigation listener*/ 
        OnNavigationListener navigationListener = new OnNavigationListener() {
        	@Override
         	public boolean onNavigationItemSelected(int itemPosition, long itemId) 
         	{
     			cityno=itemPosition;
     			switch(cityno)
     			{
     				case 1:
     				case 2:
     				case 4:
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
		
		getSupportActionBar().setListNavigationCallbacks(adapter, navigationListener);
		
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int pref = settings.getInt("cityno", 0);
        getSupportActionBar().setSelectedNavigationItem(pref);
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
		getSupportMenuInflater().inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		if (item.getItemId() == R.id.action_farecard) {
			FareCard();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
		
	}
	private void FareCard()
	{
		Intent intent = new Intent(context, WebViewActivity.class);
		intent.putExtra("cityno", cityno);
		intent.putExtra("choice", choice);
	    startActivity(intent);
	}
	private void findAllViewById()
	{
		fareCal=(Button) findViewById(R.id.button1);
		distanceInp=(EditText)findViewById(R.id.edit_text_dist);
		unitInp=(EditText)findViewById(R.id.edit_text_unit);
		fareView=(TextView)findViewById(R.id.textViewFareValue);
		dayButton=(RadioButton)findViewById(R.id.day_button);
		nightButton=(RadioButton)findViewById(R.id.night_button);
		autoButton=(RadioButton)findViewById(R.id.auto_button);
		taxiButton=(RadioButton)findViewById(R.id.taxi_button);
		unitButton=(RadioButton)findViewById(R.id.unit_button);
		distButton=(RadioButton)findViewById(R.id.dist_button);
		
	}
    public void displayString(CharSequence message)
	{
		fareView.setText("Rs."+message);
	}
    private void calculateMumbaiAutoUnit()
    {
    	unit=distance*0.5+0.2;
    	int temp=(int)(unit*10);
    	unit=((double)temp)/10;
    	if(unit<1)
    	{
    		unit=1;
    	}
    }
    private void calculateMumbaiAutoDist()
    {
    	distance=(unit-0.2)*2;
    }
	private void calculateMumbaiAutoFare()
	{
		
		double factor=1;
		if(nightMode==1)
			factor=1.25;
		
		double fareTemp=15+19.74*(unit-1);
		fare=round(fareTemp*factor);
	}
	private void calculatePuneAutoUnit()
	{
		int distance1=(int)(distance*10);
		unit=((double)(distance1))/10;
		if(unit<1.5)
			unit=1.5;
	}
	private void calculatePuneAutoDist()
	{
		distance=unit;
	}
	private void calculatePuneAutoFare()
	{
		double factor=1;
		if(nightMode==1)
			factor=1.25;
		double fareTemp=17+(unit-1.5)*11.65;
		fare=round(fareTemp*factor);		
	}
	private void calculateKolkataTaxiUnit()
	{
		unit=(int)(distance*5);
		if(unit<10)
		{
			unit=10;
		}
	}
	private void calculateKolkataTaxiDist()
	{
		distance=unit/5;
	}
	private void calculateKolkataTaxiFare()
	{
		double factor=1;
		if(nightMode==1)
			factor=1.15;
		
		double fareTemp=unit*2.4+1;
		fare=round(fareTemp*factor);
	}
	private void calculateBengaluruAutoUnit()
	{
		int temp=(int)(distance*10);
		unit=((double) temp)/10;
		if(unit<1.8)
			unit=1.8;
	}
	private void calculateBengaluruAutoDist()
	{
		distance=unit;
	}
	private void calculateBengaluruAutoFare()
	{
		double factor=1;
		if(nightMode==1)
			factor=1.50;
		double fareTemp=20+(unit-1.8)*11;
		fare=round(fareTemp*factor);
	}
	private void calculateHyderabadAutoUnit()
	{
		int temp=(int)(distance*10);
		unit=((double)temp)/10;
		if(unit<1.6)
			unit=1.6;
	}
	private void calculateHyderabadAutoDist()
	{
		distance=unit;
	}
	private void calculateHyderabadAutoFare()
	{
		double factor=1;
		if(nightMode==1)
			factor=1.50;
		double fareTemp=16+(unit-1.6)*9;
		fare=round(fareTemp*factor);
	}
	private void calculateMumbaiTaxiUnit()
	{
		int distance1=(int)(distance*1000);
		unit=(((double)distance1)/1000*0.6)+0.04;
		int temp=(int)(unit*10);
		unit=((double)temp)/10;
		if(unit<1)
		{
			unit=1;
		}
	}
	private void calculateMumbaiTaxiDist()
	{
		distance=(unit-0.04)/.6;
	}
	private void calculateMumbaiTaxiFare()
	{
		double factor=1;
		if(nightMode==1)
			factor=1.25;
		double fareTemp=19.296875*unit;
		fare=round(fareTemp*factor);
	}
	private void calculateDelhiAutoUnit()
	{
		int temp=(int)(distance*10);
		unit=((double)temp)/10;
		if(unit<2)
		{
			unit=2;
		}
	}
	private void calculateDelhiAutoDist()
	{
		distance=unit;
	}
	private void calculateDelhiAutoFare()
	{
		double factor=1;
		if(nightMode==1)
			factor=1.25;
		double fareTemp=25+(unit-2)*8;
		fare=round(fareTemp*factor);
	}
	private void calculateDelhiTaxiUnit()
	{
		int temp=(int)(distance*10);
		unit=((double)temp)/10;
		if(unit<2)
			unit=2;
		
	}
	private void calculateDelhiTaxiDist()
	{
		distance=unit;
	}
	private void calculateDelhiTaxiFare()
	{
		double factor=1;
		if(nightMode==1)
			factor=1.25;
		double fareTemp=25+(unit-1)*14;
		fare=round(fareTemp*factor);
	}
	
	int round(double d)
	{
	    DecimalFormat twoDForm = new DecimalFormat("#");
	    return Integer.valueOf(twoDForm.format(d));
	}
	double roundTwoDec(double d)
	{
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	 
		
	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();
		if (view.getId() == R.id.day_button) {
			if (checked)
				nightMode=0;
		} else if (view.getId() == R.id.night_button) {
			if (checked){
				nightMode=1;
			}
		} else if (view.getId() == R.id.auto_button) {
			if(checked){
				choice=0;
			}
		} else if (view.getId() == R.id.taxi_button) {
			if(checked){
				choice=1;
			}
		}else if (view.getId() == R.id.unit_button) {
			if(checked){
				inpmode=1;
				distanceInp.setEnabled(false);
				distanceInp.setInputType(InputType.TYPE_NULL);
				unitInp.setEnabled(true);
				unitInp.setRawInputType(Configuration.KEYBOARD_12KEY);
				unitInp.requestFocus();
				
			}
		}else if (view.getId() == R.id.dist_button) {
			if(checked){
				inpmode=0;
				unitInp.setEnabled(false);
				unitInp.setInputType(InputType.TYPE_NULL);
				distanceInp.setEnabled(true);
				distanceInp.setRawInputType(Configuration.KEYBOARD_12KEY);
				distanceInp.requestFocus();
			}
		}
    }
};
	 




