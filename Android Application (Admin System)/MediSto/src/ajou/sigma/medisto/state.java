package ajou.sigma.medisto;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class state extends Activity {
	/** Called when the activity is first created. */
	static short[] sensor_value = new short [16];
	static boolean[] illflag = new boolean[4];

	static TextView temp01, temp02, temp03, temp04;
	static TextView humi01, humi02, humi03, humi04;

	static int sectionnum=0;

	ImageButton cam01,cam02,cam03,cam04;
	static ImageView ill01, ill02, ill03, ill04,
	           shl01, shl02, shl03, shl04;

	

	static ImageView sw01, sw02, sw03, sw04;

	/*@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ajou.sigma.medisto.hwcontrol.stateopen = true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ajou.sigma.medisto.hwcontrol.stateopen = false;
		
	}*/
	@Override
	public void onCreate(Bundle savedInstanceState) {



		super.onCreate(savedInstanceState);


		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		System.loadLibrary("MediSto");
		ajou.sigma.medisto.hwcontrol.PrinttextLCD("Section...","State checking");



		setContentView(R.layout.state);


		temp01 = (TextView) findViewById(R.id.tv_Temperature01);
		temp02 = (TextView) findViewById(R.id.tv_Temperature02);
		temp03 = (TextView) findViewById(R.id.tv_Temperature03);
		temp04 = (TextView) findViewById(R.id.tv_Temperature04);
		humi01 = (TextView) findViewById(R.id.tv_Humidity01);
		humi02 = (TextView) findViewById(R.id.tv_Humidity02);
		humi03 = (TextView) findViewById(R.id.tv_Humidity03);
		humi04 = (TextView) findViewById(R.id.tv_Humidity04);
		cam01 = (ImageButton)findViewById(R.id.Cmera01);
		cam02 = (ImageButton)findViewById(R.id.Cmera02);
		cam03 = (ImageButton)findViewById(R.id.Cmera03);
		cam04 = (ImageButton)findViewById(R.id.Cmera04);
		
		sw01 = (ImageView)findViewById(R.id.Dipsw1);
		sw02 = (ImageView)findViewById(R.id.Dipsw2);
		sw03 = (ImageView)findViewById(R.id.Dipsw3);
		sw04 = (ImageView)findViewById(R.id.Dipsw4);
		
		ill01 = (ImageView)findViewById(R.id.Illu1);
		ill02 = (ImageView)findViewById(R.id.Illu2);
		ill03 = (ImageView)findViewById(R.id.Illu3);
		ill04 = (ImageView)findViewById(R.id.Illu4);
		
		shl01 = (ImageView)findViewById(R.id.Shld1);
		shl02 = (ImageView)findViewById(R.id.Shld2);
		shl03 = (ImageView)findViewById(R.id.Shld3);
		shl04 = (ImageView)findViewById(R.id.Shld4);
		 


		//temp, humi, ilumi, redsight

		cam01.setOnClickListener(new Button.OnClickListener() {


			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sectionnum = 1;
				Intent intent = new Intent(state.this, camview.class);
				startActivity(intent);			
			}
		});
		cam02.setOnClickListener(new Button.OnClickListener() {


			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sectionnum = 2;
			    Intent intent = new Intent(state.this, camview.class);
				startActivity(intent);	
			
			}
		});
		cam03.setOnClickListener(new Button.OnClickListener() {


			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sectionnum = 3;
				Intent intent = new Intent(state.this, camview.class);
				startActivity(intent);			
			}
		});
		cam04.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sectionnum = 4;
				Intent intent = new Intent(state.this, camview.class);
				startActivity(intent);			
			}
		});





	}

	public static void ValueUpdate(){

		synchronized (ajou.sigma.medisto.hwcontrol.sensor_parse) {

			sensor_value  = ajou.sigma.medisto.hwcontrol.sensor_parse;

		}
		
		synchronized (ajou.sigma.medisto.hwcontrol.luminflag) {
			illflag = ajou.sigma.medisto.hwcontrol.luminflag;
		}
		
		
		if(ajou.sigma.medisto.hwcontrol.secon01){
			temp01.setText(Integer.toString( sensor_value[0] ) + " ℃" );
			humi01.setText(Integer.toString( sensor_value[1] ) + " ％" );	
	    	sw01.setBackgroundResource(R.drawable.swon);	    	
	    	if(sensor_value[3] < 550 && 0 < sensor_value[3])//error
	    	{
	    		shl01.setBackgroundResource(R.drawable.shiwarning);
	    	}
	    	else if(sensor_value[3]>=550)
	    	{
	    		shl01.setBackgroundResource(R.drawable.shinormal);
	    	}
	    	if(sensor_value[2] > 1000)
	    	{
	    		ill01.setBackgroundResource(R.drawable.illnormal);
	    	}
	    	else
	    	{
	    		ill01.setBackgroundResource(R.drawable.illoff);
	    	}
	    	
	    /*	if(ajou.sigma.medisto.hwcontrol.senoff[0] == 1)
			{
				temp01.setText("sensor is off by unknown"  );
				humi01.setText("sensor is off by unknown"  );	
				sw01.setBackgroundResource(R.drawable.swoff);
				ill01.setBackgroundResource(R.drawable.illoff);
				shl01.setBackgroundResource(R.drawable.shioff);
			
			}*/
	    	

				
		}
	 
		else
		{
			temp01.setText("sensor is off by dipswitch"  );
			humi01.setText("sensor is off by dipswitch"  );	
			sw01.setBackgroundResource(R.drawable.swoff);
			ill01.setBackgroundResource(R.drawable.illoff);
			shl01.setBackgroundResource(R.drawable.shioff);
			
		}
		
		if(ajou.sigma.medisto.hwcontrol.secon02){
			temp02.setText(Integer.toString( sensor_value[4] ) + " ℃" );
			humi02.setText(Integer.toString( sensor_value[5] ) + " ％" );
			sw02.setBackgroundResource(R.drawable.swon);
			if(sensor_value[7] < 550 && 0 < sensor_value[7])//error
	    	{
	    		shl02.setBackgroundResource(R.drawable.shiwarning);
	    	}
	    	else if(sensor_value[7]>=550)
	    	{
	    		shl02.setBackgroundResource(R.drawable.shinormal);
	    	}
			if(sensor_value[6] > 1000)
	    	{
	    		ill02.setBackgroundResource(R.drawable.illnormal);
	    	}
	    	else
	    	{
	    		ill02.setBackgroundResource(R.drawable.illoff);
	    	}
		/*	if(ajou.sigma.medisto.hwcontrol.senoff[1] == 1)
			{
				temp02.setText("sensor is off by unknown"  );
				humi02.setText("sensor is off by unknown"  );	
				sw02.setBackgroundResource(R.drawable.swoff);
				ill02.setBackgroundResource(R.drawable.illoff);
				shl02.setBackgroundResource(R.drawable.shioff);
			
			}*/
	    	
		}

		else
		{
			temp02.setText("sensor is off by dipswitch"  );
			humi02.setText("sensor is off by dipswitch"  );		
			sw02.setBackgroundResource(R.drawable.swoff);
			ill02.setBackgroundResource(R.drawable.illoff);
			shl02.setBackgroundResource(R.drawable.shioff);
		}
		if(ajou.sigma.medisto.hwcontrol.secon03){
			temp03.setText(Integer.toString( sensor_value[8] ) + " ℃" );
			humi03.setText(Integer.toString( sensor_value[9] ) + " ％" );
			sw03.setBackgroundResource(R.drawable.swon);
			if(sensor_value[11] < 550 && 0 < sensor_value[11])//error
	    	{
	    		shl03.setBackgroundResource(R.drawable.shiwarning);
	    	}
	    	else if(sensor_value[11]>=550)
	    	{
	    		shl03.setBackgroundResource(R.drawable.shinormal);
	    	}
			if(sensor_value[10] > 1000)
	    	{
	    		ill03.setBackgroundResource(R.drawable.illnormal);
	    	}
	    	else
	    	{
	    		ill03.setBackgroundResource(R.drawable.illoff);
	    	}
			
			/*if(ajou.sigma.medisto.hwcontrol.senoff[2] == 1)
			{
				temp03.setText("sensor is off by unknown"  );
				humi03.setText("sensor is off by unknown"  );	
				sw03.setBackgroundResource(R.drawable.swoff);
				ill03.setBackgroundResource(R.drawable.illoff);
				shl03.setBackgroundResource(R.drawable.shioff);
			
			}
	    	*/
		}
		 
		else 
		{
			temp03.setText("sensor is off by dipswitch"  );
			humi03.setText("sensor is off by dipswitch"  );
			sw03.setBackgroundResource(R.drawable.swoff);
			ill03.setBackgroundResource(R.drawable.illoff);
			shl03.setBackgroundResource(R.drawable.shioff);
		}
		if(ajou.sigma.medisto.hwcontrol.secon04){
			temp04.setText(Integer.toString( sensor_value[12]) + " ℃" );
			humi04.setText(Integer.toString( sensor_value[13]) + " ％" );
			sw04.setBackgroundResource(R.drawable.swon);
			if(sensor_value[15] < 550 && 0 < sensor_value[15])//error
	    	{
	    		shl04.setBackgroundResource(R.drawable.shiwarning);
	    	}
	    	else if(sensor_value[15]>=550)
	    	{
	    		shl04.setBackgroundResource(R.drawable.shinormal);
	    	}
			if(sensor_value[14] > 1000)
	    	{
	    		ill04.setBackgroundResource(R.drawable.illnormal);
	    	}
	    	else
	    	{
	    		ill04.setBackgroundResource(R.drawable.illoff);
	    	}
			
		/*	if(ajou.sigma.medisto.hwcontrol.senoff[3] == 1)
			{
				temp04.setText("sensor is off by unknown"  );
				humi04.setText("sensor is off by unknown"  );	
				sw04.setBackgroundResource(R.drawable.swoff);
				ill04.setBackgroundResource(R.drawable.illoff);
				shl04.setBackgroundResource(R.drawable.shioff);
			
			}*/
	    	
		}
		
		else 
		{
			temp04.setText("sensor is off by dipswitch"  );
			humi04.setText("sensor is off by dipswitch"  );	
			sw04.setBackgroundResource(R.drawable.swoff);
			ill04.setBackgroundResource(R.drawable.illoff);
			shl04.setBackgroundResource(R.drawable.shioff);
		}
		
		Log.i("state", "state");

	}



	static Handler state_handle = new Handler(){
		public void handleMessage (Message msg)
		{
			//DotMatrixControl(mg.what);

			try {
				if(msg.what==1){
					Log.i("state", Integer.toString(msg.what));
					ValueUpdate();					
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

			Log.i("test", Integer.toString(msg.what));

		}
	};

}