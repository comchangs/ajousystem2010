package ajou.sigma.medisto;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class intro extends Activity {
    /** Called when the activity is first created. */
	
	//public final static String root_password = "1234";
	
    @Override
	public void onStart() {
		super.onStart();
        System.loadLibrary("MediSto");
        ajou.sigma.medisto.hwcontrol.PrinttextLCD("Welcome to ", "MediSto System");
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
 
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
        setContentView(R.layout.intro);
        
        Button launch = (Button)findViewById(R.id.btn_login);
        launch.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
		    //Intent intent = new Intent(intro.this, hwcontrol.class);
		    Intent intent = new Intent(intro.this, hwcontrol.class);
		    startActivity(intent);
		   }
         
        });
    }
        
        
}