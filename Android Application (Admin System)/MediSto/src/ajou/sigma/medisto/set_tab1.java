package ajou.sigma.medisto;

import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class set_tab1 extends Activity {
    /** Called when the activity is first created. */
	
	   String luminstr="";
	   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_tab1);
        
        Button buttonOK = (Button)findViewById(R.id.offset_ok_01);
        Button buttonCancel = (Button)findViewById(R.id.offset_cancel_01);
        
        final RadioGroup lumin01 = (RadioGroup)findViewById(R.id.RadioGroup01);
        final RadioButton lumin01_min = (RadioButton)findViewById(R.id.lumin01_min);
        final RadioButton lumin01_max = (RadioButton)findViewById(R.id.lumin01_max);
        
        
        final EditText humidity01_min = (EditText)findViewById(R.id.humidity01_min);
        final EditText humidity01_max = (EditText)findViewById(R.id.humidity01_max);
        
        final EditText temp01_min = (EditText)findViewById(R.id.temp01_min);
        final EditText temp01_max = (EditText)findViewById(R.id.temp01_max);
        
        
        String  str;
		
		SharedPreferences pref = getSharedPreferences("Section01", 0);
		
		str= pref.getString("Lumin", "3");
		
		if(str=="0")
		{
			lumin01_min.setChecked(true);
			lumin01_max.setChecked(false);
			luminstr = "0";
		}
		else if(str=="1")
		{
			lumin01_min.setChecked(false);
			lumin01_max.setChecked(true);
			luminstr = "1";
		}
		else
		{
			lumin01_min.setChecked(false);
			lumin01_max.setChecked(false);			
		}
		
		str= pref.getString("Humidity_min", "0");
		humidity01_min.setText(str);
		str= pref.getString("Humidity_max", "0");
		humidity01_max.setText(str);
	
		str= pref.getString("Temp_min", "0");
		temp01_min.setText(str);
		str= pref.getString("Temp_max", "0");
		temp01_max.setText(str);	
		
		lumin01.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {


			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub

				if(arg1==R.id.lumin01_min)
				{
					luminstr = "0";
				}
				else
				{
					luminstr = "1";
				}

			}
		});

        
        
        buttonOK.setOnClickListener(new Button.OnClickListener() {
			
			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				 SharedPreferences pref = getSharedPreferences("Section01", 0);
				 Editor editor = pref.edit();
				 
				 editor.putString("Lumin", luminstr);
				 				 
				 editor.putString("Humidity_min", humidity01_min.getText().toString());
				 editor.putString("Humidity_max", humidity01_max.getText().toString());
				 
				 editor.putString("Temp_min", temp01_min.getText().toString());				 
				 editor.putString("Temp_max", temp01_max.getText().toString());			
				 
				 editor.commit();			
				
				 Toast.makeText(set_tab1.this, "설정값이 변경되었습니다.", Toast.LENGTH_SHORT).show();			 
				 
			}
		});
        
        buttonCancel.setOnClickListener(new Button.OnClickListener() {
			
			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String  str;
				
				SharedPreferences pref = getSharedPreferences("Section01", 0);
				
				
				str= pref.getString("Lumin", "3");
				
				if(str=="0")
				{
					lumin01_min.setChecked(true);
					lumin01_max.setChecked(false);
				}
				else if(str=="1")
				{
					lumin01_min.setChecked(false);
					lumin01_max.setChecked(true);
				}
				else
				{
					lumin01_min.setChecked(false);
					lumin01_max.setChecked(false);			
				}
				
				str= pref.getString("Humidity_min", "0");
				humidity01_min.setText(str);
				str= pref.getString("Humidity_max", "0");
				humidity01_max.setText(str);
			
				str= pref.getString("Temp_min", "0");
				temp01_min.setText(str);
				str= pref.getString("Temp_max", "0");
				temp01_max.setText(str);							
			}
		});     
        

       
    }
}