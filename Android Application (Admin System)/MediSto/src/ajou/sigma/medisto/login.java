package ajou.sigma.medisto;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class login extends Activity {
	public static Activity loginActivity;
	
	public void onStart() {
		super.onStart();
        System.loadLibrary("MediSto");
   	    ajou.sigma.medisto.hwcontrol.PrinttextLCD("Please input","your password");
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
		loginActivity = this;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.login);
        
        final EditText editPassword = (EditText) findViewById(R.id.edit_password);


        Button buttonOK = (Button)findViewById(R.id.btn_OK);
        buttonOK.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			SharedPreferences pref = getSharedPreferences("RootPassword", 0);
			
			String root_password = pref.getString("password", "1234");/*ajou.sigma.medisto.intro.root_password;*/
			if(root_password.trim().equals(editPassword.getText().toString().trim())) {
				Log.i("Jeong","login OK");
				Intent intent = new Intent(login.this, setting_main.class);
			    startActivity(intent);
			}
			else{  
				 Toast.makeText(login.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
				 editPassword.setText("");
			}
		   }
         
        });
        
        
        Button buttonCancle = (Button)findViewById(R.id.btn_cancle);
        buttonCancle.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
		    finish();
		    //hwcontrol.hwActivity.finish();
		   }
         
        });
        
        
        Button buttonNum0 = (Button)findViewById(R.id.btn_num0);
        buttonNum0.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			    if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"0");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum1 = (Button)findViewById(R.id.btn_num1);
        buttonNum1.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"1");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum2 = (Button)findViewById(R.id.btn_num2);
        buttonNum2.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"2");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum3 = (Button)findViewById(R.id.btn_num3);
        buttonNum3.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"3");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum4 = (Button)findViewById(R.id.btn_num4);
        buttonNum4.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"4");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum5 = (Button)findViewById(R.id.btn_num5);
        buttonNum5.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"5");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum6 = (Button)findViewById(R.id.btn_num6);
        buttonNum6.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"6");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum7 = (Button)findViewById(R.id.btn_num7);
        buttonNum7.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"7");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum8 = (Button)findViewById(R.id.btn_num8);
        buttonNum8.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"8");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum9 = (Button)findViewById(R.id.btn_num9);
        buttonNum9.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"9");
			    } else {
			    	Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonDelete = (Button)findViewById(R.id.btn_delete);
        buttonDelete.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			String temp = null;
			if(editPassword.getText().toString().length() != 0 ) {
				temp = (String) editPassword.getText().toString().substring(0, editPassword.getText().toString().length()-1);
				editPassword.setText(temp);
				editPassword.setSelection(editPassword.length());
			}
		   }
         
        });
        
    }
}
