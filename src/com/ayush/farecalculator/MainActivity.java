package com.ayush.farecalculator;

import java.text.DecimalFormat;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	
	private Button fareCal;
	private TextView fareView;
	private EditText distanceInp;
	private RadioGroup timeRadioGroup;
	private RadioButton dayButton;
	private RadioButton nightButton;
	
	 /** An array of strings to populate dropdown list */
    String[] actions = new String[]
    {
        "Auto",
        "Taxi",
    };
    int choice,fare;
    double factor=1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.findAllViewById();
		
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
				if (choice==0)
				{
					calculateAutoFare(distance);
				}
				else
				{
					calculateTaxiFare(distance);
				}
			}
		});
		
		  /** Create an array adapter to populate dropdownlist */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, actions);
 
        /** Enabling dropdown list navigation for the action bar */
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
 
        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() 
        {
 
        	@Override
        	public boolean onNavigationItemSelected(int itemPosition, long itemId) 
        	{
    			choice=itemPosition;
    			return false;
        	}
        };
 
        /** Setting dropdown items and item navigation listener for the actionbar */
        getActionBar().setListNavigationCallbacks(adapter, navigationListener);
    
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
	private void calculateAutoFare(int distance)
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
	private void calculateTaxiFare(int distance)
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

};
