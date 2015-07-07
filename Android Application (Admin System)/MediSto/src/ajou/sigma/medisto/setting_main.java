package ajou.sigma.medisto;

import android.app.*;
import android.content.*;
import android.content.DialogInterface.OnClickListener;
import android.os.*;
import android.view.*;
import android.widget.*;

public class setting_main extends Activity{

	
	@Override
	public void onStart() {
		super.onStart();
        System.loadLibrary("MediSto");
        ajou.sigma.medisto.hwcontrol.PrinttextLCD("Section...", "  Setting Menu");
	}
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		  setContentView(R.layout.setting_main);

		  Button btn_state = (Button)findViewById(R.id.btn_state);
		  Button setviewbt = (Button)findViewById(R.id.btn_setting);
		  Button logviewbt = (Button)findViewById(R.id.btn_log);
		  Button resviewbt = (Button)findViewById(R.id.btn_res);
		  Button btn_usr = (Button)findViewById(R.id.btn_usr);
		  Button btn_admin_modify = (Button)findViewById(R.id.btn_admin_modify);
		  Button btn_logout = (Button)findViewById(R.id.btn_logout);
		  


		  // state
		  btn_state.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(setting_main.this, state.class);
					startActivity(intent);
				}
		  });
		  
		  // Setting View
		  setviewbt.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(setting_main.this, settabview.class);
			    startActivity(intent);	
			}
		  });
		  
		  // Log View
		  logviewbt.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(setting_main.this, logtabview.class);
				startActivity(intent);
			}
		  });
		  
		  // Resource View
		  resviewbt.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(setting_main.this, restabview.class);
				startActivity(intent);
			}
		  });
		  
		// User Management
		  btn_usr.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(setting_main.this, user_management.class);
					startActivity(intent);
				}
		  });
		  
		  // admin modify
		  btn_admin_modify.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(setting_main.this, admin_modify.class);
					startActivity(intent);
				}
		  });
		  
		// Logout
		  btn_logout.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					//Intent intent = new Intent(setting_main.this, intro.class);
					setting_main.this.finish();
					//login.loginActivity.finish();
					hwcontrol.hwActivity.finish();
					//startActivity(intent);
				}
		  });
  
	}	
}
