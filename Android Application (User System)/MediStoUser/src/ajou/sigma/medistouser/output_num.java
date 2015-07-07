package ajou.sigma.medistouser;

import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import ajou.sigma.medistouser.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class output_num extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.output_num);

		final EditText itemID = (EditText) findViewById(R.id.edit_itemid);

		Button buttonOK = (Button)findViewById(R.id.btn_OK);
		buttonOK.setOnClickListener(new Button.OnClickListener(){
			//출고
			public void onClick(View v) {

				ajou.sigma.medistouser.output.num = itemID.getText().toString();
				//초기화
				for(int i=0; i<12; i++) {
					ajou.sigma.medistouser.output.parsedata[i]="";
				}

				if(itemID.getText().toString().equals("")) {
					Toast.makeText(output_num.this, "관리번호를 입력하세요.", Toast.LENGTH_SHORT).show();
				} else {
				ajou.sigma.medistouser.output.num = itemID.getText().toString();

				try{
					URL text = new URL( "http://dev.jwnc.net/sysprog/resource_search.php?num="+ajou.sigma.medistouser.output.num );

					XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
					XmlPullParser parser = parserCreator.newPullParser();

					parser.setInput( text.openStream(), null );

					Log.i("XML", "파싱 중..");
					int parserEvent = parser.getEventType();
					String tag;


					boolean inNum = false;
					boolean inName = false;
					boolean inLevel = false;
					boolean inTemp_max = false;
					boolean inTemp_min = false;
					boolean inHumi_max = false;
					boolean inHumi_min = false;
					boolean inIllu = false;
					boolean inSection1 = false;
					boolean inSection2 = false;
					boolean inSection3 = false;
					boolean inSection4 = false;
					while (parserEvent != XmlPullParser.END_DOCUMENT ){
						switch(parserEvent){

						case XmlPullParser.TEXT:
							tag = parser.getName();
							if (inNum) {
								Log.i("XML Parse", "num = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[0] = parser.getText();
							}
							if (inName) {
								Log.i("XML Parse", "name = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[1] = parser.getText();                  	
							}
							if (inLevel) {
								Log.i("XML Parse", "level = " + parser.getText() ); 
								ajou.sigma.medistouser.output.parsedata[2] = parser.getText();
							}
							if (inTemp_max) {
								Log.i("XML Parse", "temp_max = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[3] = parser.getText();
							}
							if (inTemp_min) {
								Log.i("XML Parse", "temp_min = " + parser.getText() );  
								ajou.sigma.medistouser.output.parsedata[4] = parser.getText();
							}
							if (inHumi_max) {
								Log.i("XML Parse", "humi_max = " + parser.getText() );  
								ajou.sigma.medistouser.output.parsedata[5] = parser.getText();
							}
							if (inHumi_min) {
								Log.i("XML Parse", "humi_min = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[6] = parser.getText();
							}
							if (inIllu) {
								Log.i("XML Parse", "illu = " + parser.getText() );  
								ajou.sigma.medistouser.output.parsedata[7] = parser.getText();
							}
							if (inSection1) {
								Log.i("XML Parse", "section1 = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[8] = parser.getText();
							}
							if (inSection2) {
								Log.i("XML Parse", "section2 = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[9] = parser.getText();
							}
							if (inSection3) {
								Log.i("XML Parse", "section3 = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[10] = parser.getText();
							}
							if (inSection4) {
								Log.i("XML Parse", "section4 = " + parser.getText() );
								ajou.sigma.medistouser.output.parsedata[11] = parser.getText();
							}
							break;

						case XmlPullParser.END_TAG:
							tag = parser.getName();
							if (tag.compareTo("num") == 0) {
								inNum = false;
							}
							if (tag.compareTo("name") == 0) {
								inName = false;
							}
							if (tag.compareTo("level") == 0) {
								inLevel = false;
							}
							if (tag.compareTo("temp_max") == 0) {
								inTemp_max = false;
							}
							if (tag.compareTo("temp_min") == 0) {
								inTemp_min = false;
							}
							if (tag.compareTo("humi_max") == 0) {
								inHumi_max = false;
							}
							if (tag.compareTo("humi_min") == 0) {
								inHumi_min = false;
							}
							if (tag.compareTo("illu") == 0) {
								inIllu = false;
							}
							if (tag.compareTo("section1") == 0) {
								inSection1 = false;
							}
							if (tag.compareTo("section2") == 0) {
								inSection2 = false;
							}
							if (tag.compareTo("section3") == 0) {
								inSection3 = false;
							}
							if (tag.compareTo("section4") == 0) {
								inSection4 = false;
							}
							break;	

						case XmlPullParser.START_TAG:
							tag = parser.getName();

							if (tag.compareTo("num") == 0) {
								inNum = true;
							}
							if (tag.compareTo("name") == 0) {
								inName = true;
							}
							if (tag.compareTo("level") == 0) {
								inLevel = true;
							}
							if (tag.compareTo("temp_max") == 0) {
								inTemp_max = true;
							}
							if (tag.compareTo("temp_min") == 0) {
								inTemp_min = true;
							}
							if (tag.compareTo("humi_max") == 0) {
								inHumi_max = true;
							}
							if (tag.compareTo("humi_min") == 0) {
								inHumi_min = true;
							}
							if (tag.compareTo("illu") == 0) {
								inIllu = true;
							}
							if (tag.compareTo("section1") == 0) {
								inSection1 = true;
							}
							if (tag.compareTo("section2") == 0) {
								inSection2 = true;
							}
							if (tag.compareTo("section3") == 0) {
								inSection3 = true;
							}
							if (tag.compareTo("section4") == 0) {
								inSection4 = true;
							}
							break;
						}
						parserEvent = parser.next();
					}
					Log.i("XML", "파싱 끝");
				}catch( Exception e ){
					Log.e("dd", "Error in network call", e);
				}
		        if(ajou.sigma.medistouser.output.parsedata[1]=="") {
		        	Toast.makeText(output_num.this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
		        } else {
				if(Integer.parseInt(login.level)<=Integer.parseInt(ajou.sigma.medistouser.output.parsedata[2])) {
		        	Intent intent = new Intent (output_num.this, item_decrease.class);
		        	startActivity(intent);
		        } else {
		        	Toast.makeText(output_num.this, "허가 거부되었습니다.", Toast.LENGTH_SHORT).show();
		        }
		        }
				}
			}
		});


		Button buttonCancle = (Button)findViewById(R.id.btn_cancel);
		buttonCancle.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				finish();
			}
		});


		Button buttonNum0 = (Button)findViewById(R.id.btn_num0);
		buttonNum0.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"0");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});

		Button buttonNum1 = (Button)findViewById(R.id.btn_num1);
		buttonNum1.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"1");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});

		Button buttonNum2 = (Button)findViewById(R.id.btn_num2);
		buttonNum2.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"2");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});


		Button buttonNum3 = (Button)findViewById(R.id.btn_num3);
		buttonNum3.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"3");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});


		Button buttonNum4 = (Button)findViewById(R.id.btn_num4);
		buttonNum4.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"4");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});


		Button buttonNum5 = (Button)findViewById(R.id.btn_num5);
		buttonNum5.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"5");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});


		Button buttonNum6 = (Button)findViewById(R.id.btn_num6);
		buttonNum6.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"6");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});

		Button buttonNum7 = (Button)findViewById(R.id.btn_num7);
		buttonNum7.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"7");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});

		Button buttonNum8 = (Button)findViewById(R.id.btn_num8);
		buttonNum8.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"8");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});

		Button buttonNum9 = (Button)findViewById(R.id.btn_num9);
		buttonNum9.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				if (itemID.length()<4) {
					itemID.setText(itemID.getText()+"9");
				} else {
					Toast.makeText(output_num.this, "관리번호는 최대 4글자 입니다.", Toast.LENGTH_SHORT).show();
				}
				itemID.setSelection(itemID.length());
			}

		});

		Button buttonDelete = (Button)findViewById(R.id.btn_delete);
		buttonDelete.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				String temp = null;
				if(itemID.getText().toString().length() != 0 ) {
					temp = (String) itemID.getText().toString().substring(0, itemID.getText().toString().length()-1);
					itemID.setText(temp);
					itemID.setSelection(itemID.length());
				}
			}

		});
	}
}
