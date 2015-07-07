package ajou.sigma.medistouser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class item_decrease extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.item_decrease);

		TextView tv_num = (TextView) findViewById(R.id.mediid_in);
		TextView tv_name = (TextView) findViewById(R.id.mediname);
		TextView tv_temp = (TextView) findViewById(R.id.temp_in);
		TextView tv_humi = (TextView) findViewById(R.id.humi_in);
		TextView tv_illu = (TextView) findViewById(R.id.illu_in);
		TextView tv_quantity = (TextView) findViewById(R.id.quantity_in);

		tv_num.setText(ajou.sigma.medistouser.output.parsedata[0]);
		tv_name.setText(ajou.sigma.medistouser.output.parsedata[1]);
		tv_temp.setText(ajou.sigma.medistouser.output.parsedata[4] + " ~ " + ajou.sigma.medistouser.output.parsedata[3]);
		tv_humi.setText(ajou.sigma.medistouser.output.parsedata[6] + " ~ " + ajou.sigma.medistouser.output.parsedata[5]);
		if(ajou.sigma.medistouser.output.parsedata[7].equals("1")) {
			tv_illu.setText("non-needed");
		} else {
			tv_illu.setText("needed");
		}
		tv_quantity.setText(ajou.sigma.medistouser.output.parsedata[7+ajou.sigma.medistouser.intro.usr_section_num]);

		final EditText Dec_number = (EditText) findViewById(R.id.dec_number);

		Button buttonOK = (Button)findViewById(R.id.btn_OK);
		buttonOK.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				int temp=-1;

				if(Dec_number.getText().toString().equals("")) {
					Toast.makeText(item_decrease.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
				} else {
					temp = Integer.parseInt(ajou.sigma.medistouser.output.parsedata[7+ajou.sigma.medistouser.intro.usr_section_num]);
					if(temp > Integer.parseInt(Dec_number.getText().toString())) {
						ajou.sigma.medistouser.output.parsedata[7+ajou.sigma.medistouser.intro.usr_section_num] = Integer.toString(temp - Integer.parseInt(Dec_number.getText().toString()));
					} else {
						Toast.makeText(item_decrease.this, "재고가 부족합니다.", Toast.LENGTH_SHORT).show();
						temp=-1;
					}
					if(temp!=-1) {
						// resource insert
						String num = ajou.sigma.medistouser.output.parsedata[0];
						String name = ajou.sigma.medistouser.output.parsedata[1];
						String level = ajou.sigma.medistouser.output.parsedata[2];
						String temp_max = ajou.sigma.medistouser.output.parsedata[3];
						String temp_min = ajou.sigma.medistouser.output.parsedata[4];
						String humi_max = ajou.sigma.medistouser.output.parsedata[5];
						String humi_min = ajou.sigma.medistouser.output.parsedata[6];
						String illu = ajou.sigma.medistouser.output.parsedata[7];
						String section1 = ajou.sigma.medistouser.output.parsedata[8];
						String section2 = ajou.sigma.medistouser.output.parsedata[9];
						String section3 = ajou.sigma.medistouser.output.parsedata[10];
						String section4 = ajou.sigma.medistouser.output.parsedata[11];
						//*/


						HttpClient client=new DefaultHttpClient();
						byte[] postBodyByte;
						String postBody="";

						// make post body to transmit to server
						postBody="";
						// convert string type to byte[] type
						postBodyByte=postBody.getBytes();


						String SERVER_URI = "";

						//----------------------------insert-----------------------------
						// resource insert
						SERVER_URI="http://dev.jwnc.net/sysprog/resource_write.php?num="+num+"&name="+name+"&level="+level+"&temp_max="+temp_max+"&temp_min="+temp_min+"&humi_max="+humi_max+"&humi_min="+humi_min+"&illu="+illu+"&section1="+section1+"&section2="+section2+"&section3="+section3+"&section4="+section4;   

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


						} catch( Exception e ){
							Log.e("dd", "Error in network call", e);
						} 
						Toast.makeText(item_decrease.this, "decreased", Toast.LENGTH_SHORT).show();
						output.outputActivity.finish();
						finish();
					}
				}	
			}
		});


		Button buttonCancel = (Button)findViewById(R.id.btn_cancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				finish();
			}

		});


		Button buttonNum0 = (Button)findViewById(R.id.btn_num0);
		buttonNum0.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"0");
				Dec_number.setSelection(Dec_number.length());
			}

		});

		Button buttonNum1 = (Button)findViewById(R.id.btn_num1);
		buttonNum1.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"1");
				Dec_number.setSelection(Dec_number.length());
			}

		});

		Button buttonNum2 = (Button)findViewById(R.id.btn_num2);
		buttonNum2.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"2");
				Dec_number.setSelection(Dec_number.length());
			}

		});


		Button buttonNum3 = (Button)findViewById(R.id.btn_num3);
		buttonNum3.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"3");
				Dec_number.setSelection(Dec_number.length());
			}

		});


		Button buttonNum4 = (Button)findViewById(R.id.btn_num4);
		buttonNum4.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"4");
				Dec_number.setSelection(Dec_number.length());
			}

		});


		Button buttonNum5 = (Button)findViewById(R.id.btn_num5);
		buttonNum5.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"5");
				Dec_number.setSelection(Dec_number.length());
			}

		});


		Button buttonNum6 = (Button)findViewById(R.id.btn_num6);
		buttonNum6.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"6");
				Dec_number.setSelection(Dec_number.length());
			}

		});

		Button buttonNum7 = (Button)findViewById(R.id.btn_num7);
		buttonNum7.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"7");
				Dec_number.setSelection(Dec_number.length());
			}

		});

		Button buttonNum8 = (Button)findViewById(R.id.btn_num8);
		buttonNum8.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"8");
				Dec_number.setSelection(Dec_number.length());
			}

		});

		Button buttonNum9 = (Button)findViewById(R.id.btn_num9);
		buttonNum9.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Dec_number.setText(Dec_number.getText()+"9");
				Dec_number.setSelection(Dec_number.length());
			}

		});

		Button buttonDelete = (Button)findViewById(R.id.btn_delete);
		buttonDelete.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				String temp = null;
				if(Dec_number.getText().toString().length() != 0 ) {
					temp = (String) Dec_number.getText().toString().substring(0, Dec_number.getText().toString().length()-1);
					Dec_number.setText(temp);
					Dec_number.setSelection(Dec_number.length());
				}
			}
		});
	}
}