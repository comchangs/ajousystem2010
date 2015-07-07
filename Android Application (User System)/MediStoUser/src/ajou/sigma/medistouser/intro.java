package ajou.sigma.medistouser;

import ajou.sigma.medistouser.*;
import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class intro extends Activity {
    /** Called when the activity is first created. */
	public static int usr_section_num=3;
	
	//public final static String root_password = "1234";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
 
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro);
        
        Button launch = (Button)findViewById(R.id.btn_login);
        launch.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
		    Intent intent = new Intent(intro.this, hwcontrol.class);
		    startActivity(intent);
		   }
         
        });
    }
}