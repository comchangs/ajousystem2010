package ajou.sigma.medisto;

import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class set_tab2 extends Activity {
    /** Called when the activity is first created. */
	
	String luminstr="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_tab2);  		 
        
        
        Button buttonOK = (Button)findViewById(R.id.offset_ok_02);
        Button buttonCancel = (Button)findViewById(R.id.offset_cancel_02);
        
        final RadioGroup lumin02 = (RadioGroup)findViewById(R.id.RadioGroup02);
        final RadioButton lumin02_min = (RadioButton)findViewById(R.id.lumin02_min);
        final RadioButton lumin02_max = (RadioButton)findViewById(R.id.lumin02_max);
        
        final EditText humidity02_min = (EditText)findViewById(R.id.humidity02_min);
        final EditText humidity02_max = (EditText)findViewById(R.id.humidity02_max);
        
        final EditText temp02_min = (EditText)findViewById(R.id.temp02_min);
        final EditText temp02_max = (EditText)findViewById(R.id.temp02_max);
        
        
        String  str;
		
		SharedPreferences pref = getSharedPreferences("Section02", 0);
		
		
str= pref.getString("Lumin", "3");
		
		if(str=="0")
		{
			lumin02_min.setChecked(true);
			lumin02_max.setChecked(false);
			luminstr = "0";
		}
		else if(str=="1")
		{
			lumin02_min.setChecked(false);
			lumin02_max.setChecked(true);
			luminstr = "1";
		}
		else
		{
			lumin02_min.setChecked(false);
			lumin02_max.setChecked(false);			
		}
		
		str= pref.getString("Humidity_min", "0");
		humidity02_min.setText(str);
		str= pref.getString("Humidity_max", "0");
		humidity02_max.setText(str);
	
		str= pref.getString("Temp_min", "0");
		temp02_min.setText(str);
		str= pref.getString("Temp_max", "0");
		temp02_max.setText(str);	
		
		lumin02.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {


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
				
				 SharedPreferences pref = getSharedPreferences("Section02", 0);
				 Editor editor = pref.edit();
				 
				 editor.putString("Lumin", luminstr);
				 
				 editor.putString("Humidity_min", humidity02_min.getText().toString());
				 editor.putString("Humidity_max", humidity02_max.getText().toString());
				 
				 editor.putString("Temp_min", temp02_min.getText().toString());				 
				 editor.putString("Temp_max", temp02_max.getText().toString());			
				 
				 editor.commit();			
				 
				 Toast.makeText(set_tab2.this, "설정값이 변경되었습니다.", Toast.LENGTH_SHORT).show();
				
			}
		});
        
        buttonCancel.setOnClickListener(new Button.OnClickListener() {
			
			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String  str;
				
				SharedPreferences pref = getSharedPreferences("Section02", 0);
				
				

				str= pref.getString("Lumin", "3");
				
				if(str=="0")
				{
					lumin02_min.setChecked(true);
					lumin02_max.setChecked(false);
				}
				else if(str=="1")
				{
					lumin02_min.setChecked(false);
					lumin02_max.setChecked(true);
				}
				else
				{
					lumin02_min.setChecked(false);
					lumin02_max.setChecked(false);			
				}
				
				str= pref.getString("Humidity_min", "0");
				humidity02_min.setText(str);
				str= pref.getString("Humidity_max", "0");
				humidity02_max.setText(str);
			
				str= pref.getString("Temp_min", "0");
				temp02_min.setText(str);
				str= pref.getString("Temp_max", "0");
				temp02_max.setText(str);							
			}
		});     
        

    }
}