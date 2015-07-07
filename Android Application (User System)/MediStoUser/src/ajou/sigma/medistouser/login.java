package ajou.sigma.medistouser;

import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {
    /** Called when the activity is first created. */
    static String user;		//getID
    static String password;	//값이 없으면 없는 유저
    static String level;
    boolean flag = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.login);
        
        final EditText editID = (EditText) findViewById(R.id.user_ID);
        final EditText editPassword = (EditText) findViewById(R.id.user_PW);

        System.loadLibrary("MediStoUser");

        Button buttonOK = (Button)findViewById(R.id.btn_user_OK);
        buttonOK.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
		        user = editID.getText().toString();	//getID
		        password = "";			//값이 없으면 없는 유저
		        level = "";
		        
		        try{
		        	URL text = new URL( "http://dev.jwnc.net/sysprog/member_search.php?user="+user );

		        	XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
		        	XmlPullParser parser = parserCreator.newPullParser();

		        	parser.setInput( text.openStream(), null );

					Log.i("XML", "");
					int parserEvent = parser.getEventType();
					String tag;
					int i = 0;
					boolean inPassword = false;
					boolean inLevel = false;

		        	while (parserEvent != XmlPullParser.END_DOCUMENT ){
		        		switch(parserEvent){

		        		case XmlPullParser.TEXT:
		        			tag = parser.getName();
		        			if (inPassword) {
		        				Log.i("XML Parse", "Password = " + parser.getText() );
		        				password = parser.getText();
		        				i++;                      	
		        			}
		        			if (inLevel) {
		        				Log.i("XML Parse", "Level = " + parser.getText() );
		        				level = parser.getText();
		        				//mAdapter.add(parser.getText());
		        				i++;                      	
		        			}
		        			break;
		        			
		        		case XmlPullParser.END_TAG:
		        			tag = parser.getName();
		        			if (tag.compareTo("password") == 0) {
		        				inPassword = false;
		        			}
		        			if (tag.compareTo("level") == 0) {
		        				inLevel = false;
		        			}
		        			break;	
		        			
		        		case XmlPullParser.START_TAG:
		        			tag = parser.getName();

		        			if (tag.compareTo("password") == 0) {
		        				inPassword = true;
		        			}
		        			if (tag.compareTo("level") == 0) {
		        				inLevel = true;
		        			}
		        			break;
		        		}
		        		parserEvent = parser.next();
		        	}
		        }catch( Exception e ){
		        	Log.e("dd", "Error in network call", e);
		        }
		        
		        if(password.equals("")) {
		        	Toast.makeText(login.this, "Not Found ID", Toast.LENGTH_SHORT).show();
		        }

		        else if(password.trim().equals(editPassword.getText().toString().trim())) {
				Log.i("Jeong","login OK");
				Toast.makeText(login.this, "문 잠금이 해제되었습니다.", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(login.this, menu.class);
			    startActivity(intent);
			}
			else{  
				 Toast.makeText(login.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
				 editPassword.setText("");
			}
		   }
         
        });
        
        
        Button buttonCancel = (Button)findViewById(R.id.btn_user_cancel);
        buttonCancel.setOnClickListener(new Button.OnClickListener(){

		   public void onClick(View v) {
		    hwcontrol.hwActivity.finish();
		    finish();
		   }
         
        });
        
        
        Button buttonNum0 = (Button)findViewById(R.id.btn_num0);
        buttonNum0.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"0");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}

		});

		Button buttonNum1 = (Button)findViewById(R.id.btn_num1);
		buttonNum1.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"1");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});

		Button buttonNum2 = (Button)findViewById(R.id.btn_num2);
		buttonNum2.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"2");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});


		Button buttonNum3 = (Button)findViewById(R.id.btn_num3);
		buttonNum3.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"3");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});


		Button buttonNum4 = (Button)findViewById(R.id.btn_num4);
		buttonNum4.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"4");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});


		Button buttonNum5 = (Button)findViewById(R.id.btn_num5);
		buttonNum5.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"5");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});


		Button buttonNum6 = (Button)findViewById(R.id.btn_num6);
		buttonNum6.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"6");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});

		Button buttonNum7 = (Button)findViewById(R.id.btn_num7);
		buttonNum7.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"7");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});

		Button buttonNum8 = (Button)findViewById(R.id.btn_num8);
		buttonNum8.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"8");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}
		});

		Button buttonNum9 = (Button)findViewById(R.id.btn_num9);
		buttonNum9.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				if (editpassword.length()<8 || editID.isFocused()) {
					editpassword.setText(editpassword.getText()+"9");
				} else {
					Toast.makeText(login.this, "비밀번호는 8자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
				}
				editpassword.setSelection(editpassword.length());
			}

		});

		Button buttonDelete = (Button)findViewById(R.id.btn_delete);
		buttonDelete.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				EditText editpassword = editID;
				if(editID.isFocused()) {
					editpassword = editID;
				}
				if(editPassword.isFocused()) {
					editpassword = editPassword;
				}

				String temp = null;
				if(editpassword.getText().toString().length() != 0 ) {
					temp = (String) editpassword.getText().toString().substring(0, editpassword.getText().toString().length()-1);
					editpassword.setText(temp);
					editpassword.setSelection(editpassword.length());
				}
			}

		});        
	}
}