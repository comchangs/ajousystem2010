package ajou.sigma.medisto;

import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class set_tab3 extends Activity {
    /** Called when the activity is first created. */
	String luminstr="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_tab3); 
        
        
        Button buttonOK = (Button)findViewById(R.id.offset_ok_03);
        Button buttonCancel = (Button)findViewById(R.id.offset_cancel_03);
        
        final RadioGroup lumin03 = (RadioGroup)findViewById(R.id.RadioGroup03);
        final RadioButton lumin03_min = (RadioButton)findViewById(R.id.lumin03_min);
        final RadioButton lumin03_max = (RadioButton)findViewById(R.id.lumin03_max);
        
        final EditText humidity01_min = (EditText)findViewById(R.id.humidity03_min);
        final EditText humidity01_max = (EditText)findViewById(R.id.humidity03_max);
        
        final EditText temp01_min = (EditText)findViewById(R.id.temp03_min);
        final EditText temp01_max = (EditText)findViewById(R.id.temp03_max);
        
        
        String  str;
		
		SharedPreferences pref = getSharedPreferences("Section03", 0);
		
		
str= pref.getString("Lumin", "3");
		
		if(str=="0")
		{
			lumin03_min.setChecked(true);
			lumin03_max.setChecked(false);
			luminstr = "0";
		}
		else if(str=="1")
		{
			lumin03_min.setChecked(false);
			lumin03_max.setChecked(true);
			luminstr = "1";
		}
		else
		{
			lumin03_min.setChecked(false);
			lumin03_max.setChecked(false);			
		}
		
		str= pref.getString("Humidity_min", "0");
		humidity01_min.setText(str);
		str= pref.getString("Humidity_max", "0");
		humidity01_max.setText(str);
	
		str= pref.getString("Temp_min", "0");
		temp01_min.setText(str);
		str= pref.getString("Temp_max", "0");
		temp01_max.setText(str);	
		
		lumin03.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {


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
				
				 SharedPreferences pref = getSharedPreferences("Section03", 0);
				 Editor editor = pref.edit();
				 
				 editor.putString("Lumin", luminstr);
				 
				 editor.putString("Humidity_min", humidity01_min.getText().toString());
				 editor.putString("Humidity_max", humidity01_max.getText().toString());
				 
				 editor.putString("Temp_min", temp01_min.getText().toString());				 
				 editor.putString("Temp_max", temp01_max.getText().toString());			
				 
				 editor.commit();		
				 
				 Toast.makeText(set_tab3.this, "설정값이 변경되었습니다.", Toast.LENGTH_SHORT).show();
				
			}
		});
        
        buttonCancel.setOnClickListener(new Button.OnClickListener() {
			
			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String  str;
				
				SharedPreferences pref = getSharedPreferences("Section03", 0);
				
				

				str= pref.getString("Lumin", "3");
				
				if(str=="0")
				{
					lumin03_min.setChecked(true);
					lumin03_max.setChecked(false);
				}
				else if(str=="1")
				{
					lumin03_min.setChecked(false);
					lumin03_max.setChecked(true);
				}
				else
				{
					lumin03_min.setChecked(false);
					lumin03_max.setChecked(false);			
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