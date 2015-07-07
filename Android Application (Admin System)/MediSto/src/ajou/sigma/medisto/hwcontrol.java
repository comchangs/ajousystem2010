package ajou.sigma.medisto;

import java.net.URL;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class hwcontrol extends Activity {
	public static Activity hwActivity;

	int temp=0;
	int NewValue;
	long time=0;
	long [] sentime = new long [8];
	static boolean nocheckerror = false;
	static boolean errorclasscheck = true;
	static boolean secon01 = false , secon02 = false ,secon03 = false, secon04 =false ,dot=false;
	static boolean[] luminflag = new boolean[4];
	static short [] senoff = new short[4];
	boolean sendcheck = false;

	//TODO EventHandler	hardwareHandler;


	public static void PrinttextLCD(String str1, String str2)
	{
		int ret;
		IOCtlClear();                
		// TEXTLCD_DISPLAY
		IOCtlDisplay(true);  // toggle
		ret = TextLCDOut(str1, str2);
	}


	int [] stateflag = new int[16]; //
	byte[] sensordata;
	static short[] sensor_parse = new short[16];
	static short[] errorflag = new short[16];
	short[] sendflag = new short [16];

	Handler state_handle = ajou.sigma.medisto.state.state_handle;

	//thread
	SensorThread  s_thread; //sj
	ErrorThread e_thread; //sj


	@Override
	public void onCreate(Bundle savedInstanceState) {
		hwActivity = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


		//thread

		s_thread = new SensorThread(); //sj
		e_thread = new ErrorThread();  //sj
		s_thread.start();  //sj
		e_thread.start();//sj
		
		//TODO hardwareHandler = new EventHandler();


		System.loadLibrary("MediSto");

		setContentView(R.layout.hwcontrol);


		Log.i("dwtest",Integer.toString(GetValue()));

		Open();

		Intent intent = new Intent(hwcontrol.this, login.class);
		startActivity(intent);


	}
	public native int SegmentControl(int value);
	public native byte[] USNdataJNI();
	public native int BuzzerControl(int value);
	public native static int CISCameraIOctl(int cmd);
	public native static int CISCameraControl(int[] image, int width, int height);
	public native int Open();
	public native int Close();
	public native int GetValue();
	public native int DotMatrixControl(int data);
	public native int LEDControl(int value);
	public native static int TextLCDOut(String str0, String str1);
	public native static int IOCtlWirteByte(String str);
	public native static int IOCtlPos(int pos);
	public native static int IOCtlClear();
	public native static int IOCtlReturnHome();
	public native static int IOCtlDisplay(boolean bOn);
	public native static int IOCtlCursor(boolean bOn);
	public native static int IOCtlBlink(boolean bOn);

	public static final short byteToShort(byte[] buffer, int offset){  
		return (short) ( (buffer[offset+1]&0xff)<<8 | (buffer[offset]&0xff) );
	}
	//
	public void onDestroy() {
		super.onDestroy();
		s_thread.stop();
		e_thread.stop();
		Close();
		System.exit(0);

	}







	//
	class SensorThread extends Thread { //sensor phasing
		public void run() {
			try
			{

				int count=0;

				while(true)
				{

					Thread.sleep(20);
					sensordata = USNdataJNI();
					synchronized (sensor_parse) {


						if(byteToShort(sensordata,2)==24189 && byteToShort(sensordata, 20) < 130 ){
							if(byteToShort(sensordata, 8) == 1 ) {
								sensor_parse[0] = byteToShort(sensordata, 20);//temp
								sensor_parse[1] = byteToShort(sensordata, 22);//humi
								sensor_parse[2] = byteToShort(sensordata, 24);//lumi
								sensor_parse[3] = byteToShort(sensordata, 26);//shil
								sentime[0] = SystemClock.elapsedRealtime();
								sentime[4] = 1;
								//SegmentControl(100000+(byteToShort(sensordata, 20)*100)
								//		+ byteToShort(sensordata, 22));
								//count = 1;
							} else if (byteToShort(sensordata, 8) == 2 ) {
								sensor_parse[4] = byteToShort(sensordata, 20);
								sensor_parse[5] = byteToShort(sensordata, 22);
								sensor_parse[6] = byteToShort(sensordata, 24);
								sensor_parse[7] = byteToShort(sensordata, 26);
								sentime[1] = SystemClock.elapsedRealtime();
								sentime[5] = 1;
								//SegmentControl(200000+(byteToShort(sensordata, 20)*100)
								//		+ byteToShort(sensordata, 22));
								//count = 2;
							} else if (byteToShort(sensordata, 8) == 3) {
								sensor_parse[8] = byteToShort(sensordata, 20);
								sensor_parse[9] = byteToShort(sensordata, 22);
								sensor_parse[10] = byteToShort(sensordata, 24);
								sensor_parse[11] = byteToShort(sensordata, 26);
								sentime[2] = SystemClock.elapsedRealtime();
								sentime[6] = 1;
								//SegmentControl(300000+(byteToShort(sensordata, 20)*100)
								//		+ byteToShort(sensordata, 22));
								//count = 3;
							} else if (byteToShort(sensordata, 8) == 4 ) {
								sensor_parse[12] = byteToShort(sensordata, 20);
								sensor_parse[13] = byteToShort(sensordata, 22);
								sensor_parse[14] = byteToShort(sensordata, 24);
								sensor_parse[15] = byteToShort(sensordata, 26);
								
								sentime[3] = SystemClock.elapsedRealtime();
								sentime[7] = 1;

								//SegmentControl(400000+(byteToShort(sensordata, 20)*100)
								//		+ byteToShort(sensordata, 22));
								//count = 0;
							}

							Log.i("time", Long.toString(SystemClock.elapsedRealtime()-time));
							if((SystemClock.elapsedRealtime() - time) > 5000)
							{
								SendState(sensor_parse);
								time = SystemClock.elapsedRealtime();
							}



							//TODO 
						}


						//if(stateopen)
						{
						state_handle.sendMessage(Message.obtain(state_handle,1));
						}
						// int temp=UpdateValue();

						UpdateValue();
						Thread.sleep(50);
						

						if(temp==1)
						{
							/*Message msg1 = hardwareHandler.obtainMessage();
							msg1.what= 1;
							hardwareHandler.removeMessages(1);
							hardwareHandler.sendMessageDelayed(msg1, 1000);*/
							
							if(secon01 == true)
							{
							LED_dev(sensor_parse[2]);
							SegmentControl(100000+ sensor_parse[0]*100 
									+ sensor_parse[1]);
							LED_dev(0);
							}

							if(secon02 == true)
							{
							LED_dev(sensor_parse[6]);
							SegmentControl(200000+ sensor_parse[4]*100 
									+ sensor_parse[5]);
							LED_dev(0);
							}
							if(secon03 == true)
							{
							LED_dev(sensor_parse[10]);
							SegmentControl(300000+ sensor_parse[8]*100 
									+ sensor_parse[9]);
							LED_dev(0);
							}
							if(secon04 == true)
							{
							LED_dev(sensor_parse[14]);							  
							SegmentControl(400000+ sensor_parse[12]*100 
									+ sensor_parse[13]);
							LED_dev(0);
							}

							Thread.sleep(500); 
						}

					}
					
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}// end of run   

	} // end of myThread	




	//sensor thread
	class ErrorThread extends Thread{//error check thread

		String user = "0";
		public void run(){

			while(true)
			{
				try
				{

					synchronized (sensor_parse) {	
						synchronized (errorflag){

							int errorcount=0;

							for(int i=0; i<4 ; i++)
							{
								int lumin,humidity_min,humidity_max,temp_min,temp_max;

								
								
								
								String str = "Section0";

								SharedPreferences pref = getSharedPreferences(str + Integer.toString(i+1), 0);	

								lumin = Integer.parseInt(pref.getString("Lumin", "1"));							

								humidity_min = Integer.parseInt(pref.getString("Humidity_min", "0"));
								humidity_max = Integer.parseInt(pref.getString("Humidity_max", "100"));

								temp_min = Integer.parseInt(pref.getString("Temp_min", "0"));
								temp_max = Integer.parseInt(pref.getString("Temp_max", "100"));
								
								if(i==0&&!secon01)
								{
									continue;
								}
								if(i==1&&!secon02)
								{
									continue;
								}
								if(i==2&&!secon03)
								{
									continue;
								}
								if(i==3&&!secon04)
								{
						    		continue;
								}

								
								/*if(((SystemClock.elapsedRealtime() - sentime[i]) > 30000)&& sentime[i*4] ==1)//sensor die
								{
									BuzzerControl(1);
									Thread.sleep(500);
									BuzzerControl(0);
									BuzzerControl(1);
									Thread.sleep(500);
									BuzzerControl(0);
									senoff[i] = 1;
								}
								else if(((SystemClock.elapsedRealtime() - sentime[i]) < 30000)&& sentime[i*4] ==1)
								{
									senoff[i] = 0;
								}*/

								BuzzerControl(0);
								synchronized(luminflag){
									if(lumin == 0)
									{
										if(sensor_parse[(i*4)+ 2] > 1000)
										{		
											luminflag[i] = false;
											stateflag[errorcount] = 30 + i+1;			
											errorcount++;
											if(errorflag[(i*4)+ 2]==0)
												errorflag[(i*4)+ 2] = 1;
											if(sendflag[(i*4)+ 2]==0)
												sendflag[(i*4)+ 2] = 1;

										}
										else
										{
											luminflag[i] = true;
											if(errorflag[(i*4)+ 2]==2)
												errorflag[(i*4)+ 2] = 0;	
											if(sendflag[(i*4)+ 2]==2)
												sendflag[(i*4)+ 2] = 0;	
										}
									}
											
								}
								if(sensor_parse[(i*4)+1] < humidity_min || sensor_parse[(i*4)+1] > humidity_max)
								{
									stateflag[errorcount] = 40 + i+1;
									errorcount++;
									if(errorflag[(i*4)+ 1]==0)
										errorflag[(i*4)+ 1] = 1;
									if(sendflag[(i*4)+ 1]==0)
										sendflag[(i*4)+ 1] = 1;
								}	
								else
								{
									if(errorflag[(i*4)+ 1]==2)
										errorflag[(i*4)+ 1] = 0;	
									if(sendflag[(i*4)+ 1]==2)
										sendflag[(i*4)+ 1] = 0;
								}
								if(sensor_parse[(i*4)] < temp_min || sensor_parse[(i*4)] > temp_max)
								{
									stateflag[errorcount] = 50 + i+1;
									errorcount++;
									if(errorflag[(i*4)]==0)
										errorflag[(i*4)] = 1;
									if(sendflag[(i*4)]==0)
										sendflag[(i*4)] = 1;
								}	
								else
								{
									if(errorflag[(i*4)]==2)
										errorflag[(i*4)] = 0;	
									if(sendflag[(i*4)]==2)
										sendflag[(i*4)] = 0;
								}
								
								user = userpasing(Integer.toString(i+1));
								
								if(sensor_parse[(i*4)+3] < 550 && 0 < sensor_parse[(i*4)+3] && user == "0")
								{
									stateflag[errorcount] = 20 + i+1;
									errorcount++;
									if(errorflag[(i*4) + 3]==0)
										errorflag[(i*4) + 3] = 1;
									if(sendflag[(i*4) + 3]==0)
										sendflag[(i*4) + 3] = 1;
								}
								else if(sensor_parse[(i*4)+3] > 550 )
								{
									if(errorflag[(i*4)+3 ]==2)
										errorflag[(i*4)+3] = 0;		
									if(sendflag[(i*4)+3 ]==2)
										sendflag[(i*4)+3] = 0;	
								}

							}			

							for(int i = 0 ; i<16 ; i++)
							{
								if(errorflag[i] == 1)
								{
									nocheckerror = true;
									break;
								}							
							}
							for(int i = 0 ; i<16 ; i++)
							{
								if(sendflag[i] == 1)
								{
									sendcheck = true;
									break;
								}							
							}

							if(errorcount!=0)
							{
								BuzzerControl(1);
								Thread.sleep(200);
								BuzzerControl(0);
								for(int i=0; i<errorcount; i++)
								{
									if(dot){
									m_handle.sendMessageDelayed(Message.obtain(m_handle, stateflag[i]), 10);

									Thread.sleep(100);
									}
								}


								if(sendcheck)//sendlog
								{
									for(int i=0; i<errorcount; i++)
									{
										int temp = stateflag[i]/10;
										String msg = "", ill = "0" , secure = "0";

										String user = "0";
										if(temp == 2)//3
											msg = "Secure%20Error";
										else if(temp == 3)//2
											msg = "Illuminance%20Error";
										else if(temp ==4)//1
											msg = "Huminity%20Error";
										else if(temp == 5)//0
											msg = "Temperature%20Error";


										if(msg == "Secure%20Error")
											secure = "1";

										if(sensor_parse[((stateflag[i]%10)-1)*4 + 2]>1000)
											ill = "1";										

										user = userpasing(Integer.toString(stateflag[i]%10));									
										
										SendLog(Integer.toString(sensor_parse[((stateflag[i]%10)-1)*4]), 
												Integer.toString(sensor_parse[((stateflag[i]%10)-1)*4+1]),
												ill,
												msg, 
												Integer.toString(stateflag[i]%10), secure,
												user);
										
										
										sendflag[((stateflag[i]%10)-1)*4 + (5-(stateflag[i]/10))] = 2;
									}
									sendcheck = false;
								}



								if(nocheckerror && errorclasscheck)//error class open
								{
								Intent intent = new Intent(hwcontrol.this, error.class);
								startActivity(intent);
								
								}
								
								if(nocheckerror && !errorclasscheck)
								{
									ajou.sigma.medisto.error.mHandler.sendEmptyMessageDelayed(1, 100);
									Thread.sleep(10);
								}

							}	
						}		
					}
					Thread.sleep(500);		
				}

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}


	}
	//hardware control handler
	/*
	public class EventHandler extends Handler {
		EventHandler() {}    	  
		public void handleMessage(Message msg) 
		{
			try
			{
				if(msg.what==1)
				{
					if(secon01 == true)
					{
					LED_dev(sensor_parse[2]);
					SegmentControl(100000+ sensor_parse[0]*100 
							+ sensor_parse[1]);
					LED_dev(0);
					}

					if(secon02 == true)
					{
					LED_dev(sensor_parse[6]);
					SegmentControl(200000+ sensor_parse[4]*100 
							+ sensor_parse[5]);
					LED_dev(0);
					}
					if(secon03 == true)
					{
					LED_dev(sensor_parse[10]);
					SegmentControl(300000+ sensor_parse[8]*100 
							+ sensor_parse[9]);
					LED_dev(0);
					}
					if(secon04 == true)
					{
					LED_dev(sensor_parse[14]);							  
					SegmentControl(400000+ sensor_parse[12]*100 
							+ sensor_parse[13]);
					LED_dev(0);
					}
				}
				
				sendMessageDelayed(obtainMessage(1), 5000);
			}
			catch(Exception e) {}
			
		



			return;
		}
		public void sleep( long delayMillis )
		{
			this.removeMessages(1);
			sendMessageDelayed( obtainMessage(1), delayMillis );
		}
	}
	*/
	

	Handler m_handle = new Handler(){
		public void handleMessage (Message msg)
		{
			DotMatrixControl(msg.what);

			Log.i("test", Integer.toString(msg.what));

		}
	};


	public void LED_dev(int data){   
		short LED_level;   

		if(data<1800)
			LED_level = 0;
		else if(data<2200)
			LED_level = 1;
		else if(data<2600)
			LED_level = 2;
		else if(data<3000)
			LED_level = 3;
		else if(data<3400)
			LED_level = 4;
		else if(data<3800)
			LED_level = 5;
		else if(data<4200)
			LED_level = 6;
		else if(data<4600)
			LED_level = 7;
		else 
			LED_level = 8;


		switch(LED_level){
		case 0:
			LEDControl(0x00);
			break;
		case 1:
			LEDControl(0x01);
			break;
		case 2:
			LEDControl(0x03);
			break;
		case 3:
			LEDControl(0x07);
			break;
		case 4:
			LEDControl(0x0f);
			break;
		case 5:
			LEDControl(0x1f);
			break;
		case 6:
			LEDControl(0x3f);
			break;
		case 7:
			LEDControl(0x7f);
			break;
		case 8:
			LEDControl(0xff);
			break;
		}
	}


	public void UpdateValue() {
		NewValue = GetValue();

		Log.i("dipvalue", Integer.toString(GetValue()));

		if((NewValue & 0x80000000) != 0x80000000) {
			if((NewValue & 0x1) == 0x1) {
				secon01 = true;
			} else {
				secon01 = false;
			}

			if((NewValue & 0x2) == 0x2) {
    			secon02 = true;
    		} else {
    			secon02 = false;
    		}
    		if((NewValue & 0x4) == 0x4) {
    			secon03 = true;
    		} else {
    			secon03 = false;
    		}
    		if((NewValue & 0x8) == 0x8) {
    			secon04 = true;
    		} else {
    			secon04 = false;
    		}/*
    		if((NewValue & 0x100) == 0x100) {
    			dipsw15.setChecked(true);
    		} else {
    			dipsw15.setChecked(false);
    		}
    		if((NewValue & 0x200) == 0x200) {
    			dipsw16.setChecked(true);
    		} else {
    			dipsw16.setChecked(false);
    		}
    		if((NewValue & 0x400) == 0x400) {
    			dipsw17.setChecked(true);
    		} else {
    			dipsw17.setChecked(false);
    		}
    		if((NewValue & 0x800) == 0x800) {
    			dipsw18.setChecked(true);
    		} else {
    			dipsw18.setChecked(false);
    		}*/

    		if((NewValue & 0x10000) == 0x10000) {
    			dot = true;
    		} else {
    			dot = false;
    		}/*
    		if((NewValue & 0x20000) == 0x20000) {
    			dipsw22.setChecked(true);
    		} else {
    			dipsw22.setChecked(false);
    		}
    		if((NewValue & 0x40000) == 0x40000) {
    			dipsw23.setChecked(true);
    		} else {
    			dipsw23.setChecked(false);
    		}
    		if((NewValue & 0x80000) == 0x80000) {
    			dipsw24.setChecked(true);
    		} else {
    			dipsw24.setChecked(false);
    		}
    		if((NewValue & 0x1000000) == 0x1000000) {
    			dipsw25.setChecked(true);
    		} else {
    			dipsw25.setChecked(false);
    		}
    		if((NewValue & 0x2000000) == 0x2000000) {
    			dipsw26.setChecked(true);
    		} else {
    			dipsw26.setChecked(false);
    		}
    		if((NewValue & 0x4000000) == 0x4000000) {
    			dipsw27.setChecked(true);
    		} else {
    			dipsw27.setChecked(false);
    		}*/
			if((NewValue & 0x8000000) == 0x8000000) {
				temp = 1;
				Log.i("dip", "true");
			} else {
				temp = 0;
				Log.i("dip", "false");
			} 		
		}
		else {
			Log.i("update", "nono");
		}

		//return 0;
	}

	public void SendState(short [] sensor_parse)
	{

		String ill01 = "0" , secure01 = "0", ill02 = "0" , secure02 = "0"
			, ill03 = "0" , secure03 = "0", ill04 = "0" , secure04 = "0"
				, sec01 = "0" , sec02 = "0" , sec03 = "0" , sec04 = "0";	


		if(secon01==true)
			sec01 = "1";
		if(secon02==true)
			sec02 = "1";
		if(secon03==true)
			sec03 = "1";
		if(secon04==true)
			sec04 = "1";
	
		if(sensor_parse[3]<550)
			secure01 = "1";	
		if(sensor_parse[7]<550)
			secure02 = "1";	
		if(sensor_parse[11]<550)
			secure03 = "1";	
		if(sensor_parse[15]<550)
			secure04 = "1";	
		

		if(sensor_parse[2]>1000)
			ill01 = "1";	
		if(sensor_parse[6]>1000)
			ill02 = "1";	
		if(sensor_parse[10]>1000)
			ill03 = "1";	
		if(sensor_parse[14]>1000)
			ill04 = "1";	

		//TODO

		SendLog1(Integer.toString(sensor_parse[0]), 
				Integer.toString(sensor_parse[1]),	
				ill01 , 
				secure01,
				sec01,
				Integer.toString(sensor_parse[4]),
				Integer.toString(sensor_parse[5]),
				ill02 ,
				secure02,
				sec02,
				Integer.toString(sensor_parse[8]),
				Integer.toString(sensor_parse[9]),
				ill03,
				secure03,
				sec03,
				Integer.toString(sensor_parse[12]),
				Integer.toString(sensor_parse[13]),
				ill04,
				secure04,
				sec04);
	}


	public void SendLog(String temp, String humi, String illu, String event, String section, String secure, String user)
	{
		//----------------------------insert-----------------------------
		// log insert
		
		/* temp = "34"; 
		 humi = "43"; 
		 illu = "1"; 
		 event = "Qook"; 
		 section = "1";
	     secure = "0";
		 */

		/*/ member insert
		String user = "200621756";
		String password = "sysprog";
		String level = "1";
		//*/

		/*/ resource insert
		String num = "1234";
		String name = "";
		String level = "3"; 
		String temp_max = "40";
		String temp_min = "35";
		String humi_max = "30";
		String humi_min = "20";
		String illu = "1";
		String section1 = "";
		String section2 = "";
		String section3 = "";
		String section4 = "";
		//*/

		/*/ login insert
		String user = "1234";
		String level = "1";
		String section = "3";
		//*/
		//TODO
		/*/ status insert
		String sec1_temp = "23";
		String sec1_humi = "44";
		String sec1_illu = "1";
		String sec2_temp = "23";
		String sec2_humi = "23";
		String sec2_illu = "1";
		String sec3_temp = "44";
		String sec3_humi = "43";
		String sec3_illu = "0";
		String sec4_temp = "44";
		String sec4_humi = "43";
		String sec4_illu = "0";
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
		// log insert
		SERVER_URI="http://dev.jwnc.net/sysprog/log_write.php?user="+user+"&temp="+temp+"&humi="+humi+"&illu="+illu+"&event="+event+"&section="+section+"&secure="+secure;    
		// member insert
		//SERVER_URI="http://dev.jwnc.net/sysprog/member_write.php?user="+user+"&password="+password+"&level="+level;    
		// resource insert
		//SERVER_URI="http://dev.jwnc.net/sysprog/resorce_write.php?num="+num+"&name="+name+"&level="+level+"&temp_max="+temp_max+"&temp_min="+temp_min+"&humi_max="+humi_max+"&humi_min="+humi_min+"&illu="+illu+"&section1="+section1+"&section2="+section2+"&section3="+section3+"&section4="+section4;    
		// login
		//SERVER_URI="http://dev.jwnc.net/sysprog/login.php?num="+num+"&name="+name+"&level="+level+"&temp_max="+temp_max+"&temp_min="+temp_min+"&humi_max="+humi_max+"&humi_min="+humi_min+"&illu="+illu+"&section1="+section1+"&section2="+section2+"&section3="+section3+"&section4="+section4;    
		// status
		//SERVER_URI="http://dev.jwnc.net/sysprog/resorce_write.php?sec1_temp="+sec1_temp+"&sec1_humi="+sec1_humi+"&sec1_illu="+sec1_illu+"&sec2_temp="+sec2_temp+"&sec2_humi="+sec2_humi+"&sec2_illu="+sec2_illu+"&sec3_temp="+sec3_temp+"&sec3_humi="+sec3_humi+"&sec3_illu="+sec3_illu+"&sec4_temp="+sec4_temp+"&sec4_humi="+sec4_humi+"&sec4_illu="+sec4_illu;    

		try{
			HttpEntity httpBody=new ByteArrayEntity(postBodyByte);
			HttpResponse response;
			HttpParams parms=new BasicHttpParams();
			HttpPost method=new HttpPost(SERVER_URI);
			method.setEntity(httpBody);
			method.setParams(parms);

			response=client.execute(method);

			Log.e("MIR", "GET STATUS LINE: "+response.getStatusLine());

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
		Log.e("MIR", "==END OF POST CLIENT AUTHENTICATION==");
	}

	public void SendLog1(String sec1_temp,
			String sec1_humi,
			String sec1_illu ,
			String sec1_secure,
			String sec1_on,
			String sec2_temp ,
			String sec2_humi ,
			String sec2_illu,
			String sec2_secure,
			String sec2_on,
			String sec3_temp ,
			String sec3_humi ,
			String sec3_illu,
			String sec3_secure,
			String sec3_on,
			String sec4_temp ,
			String sec4_humi,
			String sec4_illu,
			String sec4_secure,
			String sec4_on)
	{



		HttpClient client=new DefaultHttpClient();
		byte[] postBodyByte;
		String postBody="";

		// make post body to transmit to server
		postBody="";
		// convert string type to byte[] type
		postBodyByte=postBody.getBytes();


		String SERVER_URI = "";

		//----------------------------insert-----------------------------
		// log insert
		//SERVER_URI="http://dev.jwnc.net/sysprog/log_write.php?user="+user+"&temp="+temp+"&humi="+humi+"&illu="+illu+"&event="+event+"&section="+section+"&secure="+secure;    
		// member insert
		//SERVER_URI="http://dev.jwnc.net/sysprog/member_write.php?user="+user+"&password="+password+"&level="+level;    
		// resource insert
		//SERVER_URI="http://dev.jwnc.net/sysprog/resorce_write.php?num="+num+"&name="+name+"&level="+level+"&temp_max="+temp_max+"&temp_min="+temp_min+"&humi_max="+humi_max+"&humi_min="+humi_min+"&illu="+illu+"&section1="+section1+"&section2="+section2+"&section3="+section3+"&section4="+section4;    
		// login
		//SERVER_URI="http://dev.jwnc.net/sysprog/login.php?num="+num+"&name="+name+"&level="+level+"&temp_max="+temp_max+"&temp_min="+temp_min+"&humi_max="+humi_max+"&humi_min="+humi_min+"&illu="+illu+"&section1="+section1+"&section2="+section2+"&section3="+section3+"&section4="+section4;    
		// status
		SERVER_URI="http://dev.jwnc.net/sysprog/status_write.php?sec1_temp="+sec1_temp+"&sec1_humi="+sec1_humi+"&sec1_illu="+sec1_illu+"&sec1_secure="+sec1_secure+"&sec1_on="+sec1_on+"&sec2_temp="+sec2_temp+"&sec2_humi="+sec2_humi+"&sec2_illu="+sec2_illu+"&sec2_secure="+sec2_secure+"&sec2_on="+sec2_on+"&sec3_temp="+sec3_temp+"&sec3_humi="+sec3_humi+"&sec3_illu="+sec3_illu+"&sec3_secure="+sec3_secure+"&sec3_on="+sec3_on+"&sec4_temp="+sec4_temp+"&sec4_humi="+sec4_humi+"&sec4_illu="+sec4_illu+"&sec4_secure="+sec4_secure+"&sec4_on="+sec4_on;    

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
	}

	
	public String userpasing(String section)
	{
		String user = "0";
		
	    try{
        	URL text = new URL( "http://dev.jwnc.net/sysprog/login_read.php?section="+section );

        	XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
        	XmlPullParser parser = parserCreator.newPullParser();

        	parser.setInput( text.openStream(), null );

        	Log.i("XML", "start");
        	int parserEvent = parser.getEventType();
        	String tag;
        	int i = 0;
        	boolean inUser = false;
        

        	while (parserEvent != XmlPullParser.END_DOCUMENT ){
        		switch(parserEvent){

        		case XmlPullParser.TEXT:
        			tag = parser.getName();
        			if (inUser) {
        				Log.i("XML Parse", "User = " + parser.getText() );
        				user = parser.getText();
        				i++;                      	
        			}
        			
        			break;
        			
        		case XmlPullParser.END_TAG:
        			tag = parser.getName();
        			if (tag.compareTo("user") == 0) {
        				inUser = false;
        			}
        			
        			break;	
        			
        		case XmlPullParser.START_TAG:
        			tag = parser.getName();

        			if (tag.compareTo("user") == 0) {
        				inUser = true;
        			}
        			
        			break;
        		}
        		parserEvent = parser.next();
        	}
        	Log.i("XML", "end");
        }catch( Exception e ){
        	Log.e("dd", "Error in network call", e);
        	Log.e("??", user, e);
        }
        return user;
	}

	


}

