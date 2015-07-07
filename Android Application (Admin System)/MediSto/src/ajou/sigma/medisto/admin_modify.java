package ajou.sigma.medisto;

import java.net.*;

import org.xmlpull.v1.*;

import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

public class admin_modify extends Activity {
    /** Called when the activity is first created. */
	@Override
	public void onStart() {
		super.onStart();
        System.loadLibrary("MediSto");
   	    ajou.sigma.medisto.hwcontrol.PrinttextLCD("Adimin Password","Changing...");
	}

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        
        setContentView(R.layout.admin_modify);
        

        Button buttonOK = (Button)findViewById(R.id.btn_admin_OK);
        Button buttonCancel = (Button)findViewById(R.id.btn_admin_cancle);
        
        final EditText old_password = (EditText)findViewById(R.id.old_password);
        final EditText new_password = (EditText)findViewById(R.id.new_password);
        final EditText match_password = (EditText)findViewById(R.id.match_password);
        
        
        SharedPreferences pref = getSharedPreferences("RootPassword", 0);
        
		buttonOK.setOnClickListener(new Button.OnClickListener() {
			
			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				 SharedPreferences pref = getSharedPreferences("RootPassword", 0);
				 Editor editor = pref.edit();
				 
				 if(old_password.getText().toString().equals(pref.getString("password", "1234"))) {
					 if(new_password.getText().toString().equals(match_password.getText().toString())) {
						 editor.putString("password", new_password.getText().toString());
						 editor.commit();	
						 Toast.makeText(admin_modify.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
						 finish();
					 } else {
						 Toast.makeText(admin_modify.this, "새 비밀번호와 확인 값이 서로 다릅니다.", Toast.LENGTH_SHORT).show();
					 }
				 } else {
					 Toast.makeText(admin_modify.this, "기존 비밀번호를 틀렸습니다.", Toast.LENGTH_SHORT).show();
				 }
			}
		});
		
		buttonCancel.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
		    finish();
		   }
         
        });
       
        
        
        
        
        Button buttonNum0 = (Button)findViewById(R.id.btn_num0);
        buttonNum0.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			    if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"0");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum1 = (Button)findViewById(R.id.btn_num1);
        buttonNum1.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"1");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum2 = (Button)findViewById(R.id.btn_num2);
        buttonNum2.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"2");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum3 = (Button)findViewById(R.id.btn_num3);
        buttonNum3.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"3");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum4 = (Button)findViewById(R.id.btn_num4);
        buttonNum4.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"4");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum5 = (Button)findViewById(R.id.btn_num5);
        buttonNum5.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"5");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum6 = (Button)findViewById(R.id.btn_num6);
        buttonNum6.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"6");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum7 = (Button)findViewById(R.id.btn_num7);
        buttonNum7.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"7");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum8 = (Button)findViewById(R.id.btn_num8);
        buttonNum8.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"8");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum9 = (Button)findViewById(R.id.btn_num9);
        buttonNum9.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
			   if (editPassword.length()<8) {
			    	editPassword.setText(editPassword.getText()+"9");
			    } else {
			    	Toast.makeText(admin_modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonDelete = (Button)findViewById(R.id.btn_delete);
        buttonDelete.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = old_password;
			   if(old_password.isFocused()) {
		        	editPassword = old_password;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        
		        
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