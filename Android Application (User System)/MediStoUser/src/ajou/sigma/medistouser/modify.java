package ajou.sigma.medistouser;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import ajou.sigma.medistouser.*;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class modify extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.modify);

		System.loadLibrary("MediStoUser");

		Button buttonOK = (Button)findViewById(R.id.btn_usr_OK);
		Button buttonCancel = (Button)findViewById(R.id.btn_usr_cancel);

		final EditText old_password = (EditText)findViewById(R.id.usr_old_password);
		final EditText new_password = (EditText)findViewById(R.id.usr_new_password);
		final EditText match_password = (EditText)findViewById(R.id.usr_match_password);


//		SharedPreferences pref = getSharedPreferences("RootPassword", 0);
		buttonOK.setOnClickListener(new Button.OnClickListener() {

			//@Override
			public void onClick(View arg0) {

				if(old_password.getText().toString().equals(ajou.sigma.medistouser.login.password)) {
					if(new_password.getText().toString().equals(match_password.getText().toString())) {

						Toast.makeText(modify.this, "changing pass", Toast.LENGTH_SHORT).show();
						
						String user = ajou.sigma.medistouser.login.user;
						String password = new_password.getText().toString();
						String level = ajou.sigma.medistouser.login.level;

						HttpClient client=new DefaultHttpClient();
						byte[] postBodyByte;
						String postBody="";

						// make post body to transmit to server
						postBody="";
						// convert string type to byte[] type
						postBodyByte=postBody.getBytes();


						String SERVER_URI = "";

						//----------------------------insert-----------------------------
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

							}else{
								Log.i("MIR", "=======================RESPONSE_FAIL=======================");
								Log.i("MIR", "RESPONSE_FAIL");
								Log.i("MIR", "=======================RESPONSE_FAIL_END===================");
							}

						}catch(Exception e){
							Log.e("MIR","HTTP ERROR");
						}
						Log.i("MIR", "==END OF POST CLIENT AUTHENTICATION==");
						
						

						finish();
					} else {
						Toast.makeText(modify.this, "새로운 비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(modify.this, "이전 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(modify.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
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