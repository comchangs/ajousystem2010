package ajou.sigma.medisto;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.*;
import org.apache.http.util.*;

import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

public class user_add extends Activity {
    /** Called when the activity is first created. */
	
    @Override
	public void onStart() {
		super.onStart();
        System.loadLibrary("MediSto");
        ajou.sigma.medisto.hwcontrol.PrinttextLCD("User Account","New Create...");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        setContentView(R.layout.user_add);
        
        Button buttonOK = (Button)findViewById(R.id.btn_usr_OK);
        Button buttonCancel = (Button)findViewById(R.id.btn_usr_cancel);
        
        final EditText new_user_id = (EditText)findViewById(R.id.new_usr_add_id);
        final EditText new_password = (EditText)findViewById(R.id.usr_add_new_password);
        final EditText match_password = (EditText)findViewById(R.id.usr_add_match_password);
        final EditText user_level = (EditText)findViewById(R.id.usr_add_level);
        
		buttonOK.setOnClickListener(new Button.OnClickListener() {
			
			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(new_password.getText().toString().equals(match_password.getText().toString()) && !user_level.getText().toString().equals("")) {
				//----------------------------insert-----------------------------

					// member insert
					String user = new_user_id.getText().toString();
					String password = new_password.getText().toString();
					String level = user_level.getText().toString();
					
					

					HttpClient client=new DefaultHttpClient();
					byte[] postBodyByte;
					String postBody="";

					// make post body to transmit to server
					postBody="";
					// convert string type to byte[] type
					postBodyByte=postBody.getBytes();


					String SERVER_URI = "";

					// member insert
					SERVER_URI="http://dev.jwnc.net/sysprog/member_write.php?user="+user+"&password="+password+"&level="+level;    
					
					try{
						HttpEntity httpBody=new ByteArrayEntity(postBodyByte);
						HttpResponse response;
						HttpParams parms=new BasicHttpParams();
						HttpPost method=new HttpPost(SERVER_URI);
						method.setEntity(httpBody);
						method.setParams(parms);

						response=client.execute(method);

						Log.i("MIR", "GET STATUS LINE: "+response.getStatusLine());

						// store STATUS for RETURN CODE
						String HEADER_STATUS = "";
						HEADER_STATUS=response.getStatusLine().toString();

						// get Entity from response
						HttpEntity responseResultEntity=response.getEntity();

						if(responseResultEntity != null){
							// get result from response
							String RESPONSE_RESULT_FOR_AUTH = "";
							RESPONSE_RESULT_FOR_AUTH=EntityUtils.toString(responseResultEntity);
							//----------------------------select-----------------------------
							Log.i("Jeong", RESPONSE_RESULT_FOR_AUTH);
							Toast.makeText(user_add.this, new_user_id.getText().toString()+"번 사용자가  추가 되었습니다.", Toast.LENGTH_SHORT).show();

						} else {
							Log.i("MIR", "=======================RESPONSE_FAIL=======================");
							Log.i("MIR", "RESPONSE_FAIL");
							Log.i("MIR", "=======================RESPONSE_FAIL_END===================");
						}

					}catch(Exception e){
						Log.e("MIR","HTTP ERROR");
					}
					Log.i("MIR", "==END OF POST CLIENT AUTHENTICATION==");
				} else {
					 Toast.makeText(user_add.this, "새 비밀번호와 확인 값이 서로 다르거나 User ID, Level값이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
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
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"0");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum1 = (Button)findViewById(R.id.btn_num1);
        buttonNum1.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"1");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum2 = (Button)findViewById(R.id.btn_num2);
        buttonNum2.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"2");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum3 = (Button)findViewById(R.id.btn_num3);
        buttonNum3.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"3");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum4 = (Button)findViewById(R.id.btn_num4);
        buttonNum4.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"4");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum5 = (Button)findViewById(R.id.btn_num5);
        buttonNum5.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"5");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        
        Button buttonNum6 = (Button)findViewById(R.id.btn_num6);
        buttonNum6.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"6");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum7 = (Button)findViewById(R.id.btn_num7);
        buttonNum7.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"7");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum8 = (Button)findViewById(R.id.btn_num8);
        buttonNum8.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"8");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonNum9 = (Button)findViewById(R.id.btn_num9);
        buttonNum9.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
		        }
		        
			    if (editPassword.length()<8 || editPassword == new_user_id) {
			    	editPassword.setText(editPassword.getText()+"9");
			    } else if (editPassword.length()>1 && editPassword == user_level) {
			    	Toast.makeText(user_add.this, "레벨은 1자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    } else {
			    	Toast.makeText(user_add.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
			    }
		    editPassword.setSelection(editPassword.length());
		   }
         
        });
        
        Button buttonDelete = (Button)findViewById(R.id.btn_delete);
        buttonDelete.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
			   EditText editPassword = new_user_id;
			   if(new_user_id.isFocused()) {
		        	editPassword = new_user_id;
		        }
		        if(new_password.isFocused()) {
		        	editPassword = new_password;
		        }
		        if(match_password.isFocused()) {
		        	editPassword = match_password;
		        }
		        if(user_level.isFocused()) {
		        	editPassword = user_level;
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