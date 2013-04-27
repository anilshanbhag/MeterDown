package com.ayush.farecalculator;
 
import java.text.DecimalFormat;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
 
public class Calculate extends Fragment
{
	private Button fareCal;
	private TextView fareView;
	private EditText distanceInp;
	private EditText unitInp;
	private RadioGroup timeRadioGroup;
	private RadioButton dayButton;
	private RadioButton nightButton;
	
	int choice,fare,cityno;
    double factor=1;
	
    public static Fragment newInstance(Context context) {
        Calculate f = new Calculate();
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.calculate_layout, null);
        
        
        this.findAllViewById(root);
		
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
								
								
						}
				}
			}
		});
		return root;
    }
    private void findAllViewById(ViewGroup root)
	{
		fareCal=(Button) root.findViewById(R.id.button1);
		distanceInp=(EditText)root.findViewById(R.id.edit_text_dist);
		unitInp=(EditText)root.findViewById(R.id.edit_text_unit);
		fareView=(TextView)root.findViewById(R.id.textView1);
		timeRadioGroup=(RadioGroup)root.findViewById(R.id.search_radio_group);
		dayButton=(RadioButton)root.findViewById(R.id.day_button);
		nightButton=(RadioButton)root.findViewById(R.id.night_button);
		dayButton.setOnClickListener(radioButtonListener);
		nightButton.setOnClickListener(radioButtonListener);
		
	}
    public void displayString(CharSequence message)
	{
		fareView.setText("The Fare is Rs."+message);
	}
	
	private void calculateMumbaiAutoFare(double distance)
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
	private void calculateMumbaiTaxiFare(double distance)
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
	private OnClickListener radioButtonListener = new OnClickListener() {
		
		public void onClick(View view) {
			// Is the button now checked?
			boolean checked = ((RadioButton) view).isChecked();
			// 	Check which radio button was clicked
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
 
}