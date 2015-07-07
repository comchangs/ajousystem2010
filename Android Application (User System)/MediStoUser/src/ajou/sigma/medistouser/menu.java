package ajou.sigma.medistouser;

import java.net.URL;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import ajou.sigma.medistouser.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

public class menu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu);
		

		//파싱
        String temp = null;
        String humi = null;
        String section = Integer.toString(intro.usr_section_num);
        try{
        	URL text = new URL( "http://dev.jwnc.net/sysprog/status_read.php?section="+section );

        	XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
        	XmlPullParser parser = parserCreator.newPullParser();

        	parser.setInput( text.openStream(), null );

        	Log.i("XML", "파싱 중..");
        	int parserEvent = parser.getEventType();
        	String tag;
        	int i = 0;
        	boolean inTemp = false;
        	boolean inHumi = false;

        	while (parserEvent != XmlPullParser.END_DOCUMENT ){
        		switch(parserEvent){

        		case XmlPullParser.TEXT:
        			tag = parser.getName();
        			if (inTemp) {
        				Log.i("XML Parse", "temp = " + parser.getText() );
        				temp = parser.getText();
        				i++;                      	
        			}
        			if (inHumi) {
        				Log.i("XML Parse", "humi = " + parser.getText() );
        				humi = parser.getText();
        				//mAdapter.add(parser.getText());
        				i++;                      	
        			}
        			break;
        			
        		case XmlPullParser.END_TAG:
        			tag = parser.getName();
        			if (tag.compareTo("temp") == 0) {
        				inTemp = false;
        			}
        			if (tag.compareTo("humi") == 0) {
        				inHumi = false;
        			}
        			break;	
        			
        		case XmlPullParser.START_TAG:
        			tag = parser.getName();

        			if (tag.compareTo("temp") == 0) {
        				inTemp = true;
        			}
        			if (tag.compareTo("humi") == 0) {
        				inHumi = true;
        			}
        			break;




        		}
        		parserEvent = parser.next();
        	}
        	Log.i("XML", "파싱 끝");
        }catch( Exception e ){
        	Log.e("dd", "Error in network call", e);
        }

		
		TextView tv_humi = (TextView)findViewById(R.id.tv_Humidity01);
		tv_humi.setText(humi+"%");
		TextView tv_temp = (TextView)findViewById(R.id.tv_Temperature01);
		tv_temp.setText(temp+"%");
		
		AbsoluteLayout layout = (AbsoluteLayout)findViewById(R.id.AbsoluteLayout01);
		layout.setBackgroundResource(R.drawable.sec1bg);
		
		TextView user_id = (TextView)findViewById(R.id.user_id);
		user_id.setText(login.user);
		Button logout = (Button)findViewById(R.id.btn_logout);
		logout.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Toast.makeText(menu.this, "문 잠금이 설정되었습니다.", Toast.LENGTH_SHORT).show();
				String login_user = login.user;
				String login_level = login.level;
				String login_section = Integer.toString(intro.usr_section_num);
				
				HttpClient client=new DefaultHttpClient();
				byte[] postBodyByte;
				String postBody="";

				// make post body to transmit to server
				postBody="";
				// convert string type to byte[] type
				postBodyByte=postBody.getBytes();


				String SERVER_URI = "";
				
				SERVER_URI="http://dev.jwnc.net/sysprog/logout.php?user="+login_user+"&level="+login_level+"&section="+login_section;    
				
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
				Toast.makeText(menu.this, "문 잠금이 설정되었습니다.", Toast.LENGTH_SHORT).show();
				hwcontrol.hwActivity.finish();
				finish();
			}
		});
		Button usrmodify = (Button)findViewById(R.id.btn_usr_modify);
		usrmodify.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent (menu.this, modify.class);
				startActivity(intent);
			}
		});
		Button input = (Button)findViewById(R.id.inven_in);
		input.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent (menu.this, input.class);
				startActivity(intent);
			}
		});
		Button output = (Button)findViewById(R.id.inven_out);
		output.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent (menu.this, output.class);
				startActivity(intent);
			}
		});
		Button invenview = (Button)findViewById(R.id.inven_view);
		invenview.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent (menu.this, resource.class);
				startActivity(intent);
			}
		});

    }
}