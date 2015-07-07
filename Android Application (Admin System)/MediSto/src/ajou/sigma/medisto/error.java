package ajou.sigma.medisto;



import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;

public class error extends Activity {
	/** Called when the activity is first created. */
	static ImageButton sec1temp, sec2temp ,sec3temp ,sec4temp;
	static ImageButton sec1humi,sec2humi,sec3humi,sec4humi;
	static ImageButton sec1illu, sec2illu, sec3illu, sec4illu;
	static ImageButton sec1shld, sec2shld, sec3shld, sec4shld;
	;

	static short[] errorflag = ajou.sigma.medisto.hwcontrol.errorflag;


	@Override
	public void onStart() {
		super.onStart();
		System.loadLibrary("MediSto");
		ajou.sigma.medisto.hwcontrol.PrinttextLCD("Error!!","Checking the Section");
		ajou.sigma.medisto.hwcontrol.errorclasscheck = false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




		setContentView(R.layout.error);

		sec1temp = (ImageButton)findViewById(R.id.TempErr1);
		sec2temp = (ImageButton)findViewById(R.id.TempErr2);
		sec3temp = (ImageButton)findViewById(R.id.TempErr3);
		sec4temp = (ImageButton)findViewById(R.id.TempErr4);

		sec1humi = (ImageButton)findViewById(R.id.HumiErr1);
		sec2humi  = (ImageButton)findViewById(R.id.HumiErr2);
		sec3humi  = (ImageButton)findViewById(R.id.HumiErr3);
		sec4humi  = (ImageButton)findViewById(R.id.HumiErr4);

		sec1illu = (ImageButton)findViewById(R.id.IlluErr1);
		sec2illu = (ImageButton)findViewById(R.id.IlluErr2);
		sec3illu = (ImageButton)findViewById(R.id.IlluErr3);
		sec4illu = (ImageButton)findViewById(R.id.IlluErr4);

		sec1shld = (ImageButton)findViewById(R.id.ShldErr1);
		sec2shld = (ImageButton)findViewById(R.id.ShldErr2);
		sec3shld = (ImageButton)findViewById(R.id.ShldErr3);
		sec4shld = (ImageButton)findViewById(R.id.ShldErr4);




		Button sec1ok = (Button)findViewById(R.id.Sec1OK);
		Button sec2ok = (Button)findViewById(R.id.Sec2OK);
		Button sec3ok = (Button)findViewById(R.id.Sec3OK);
		Button sec4ok = (Button)findViewById(R.id.Sec4OK);
		Button secallok = (Button)findViewById(R.id.ALLok);				



		Update();




		synchronized (ajou.sigma.medisto.hwcontrol.errorflag){
			sec1ok.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					for(int i =0 ; i<4; i++)
					{

						if(ajou.sigma.medisto.hwcontrol.errorflag[i]==1)
							ajou.sigma.medisto.hwcontrol.errorflag[i]=2;						

					}
			
					ajou.sigma.medisto.hwcontrol.nocheckerror = false;
					Toast.makeText(error.this, "section 1 is checked.", Toast.LENGTH_SHORT).show();
				}

			});
			sec2ok.setOnClickListener(new Button.OnClickListener() {


				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					for(int i =4 ; i<8; i++)
					{

						if(ajou.sigma.medisto.hwcontrol.errorflag[i]==1)
							ajou.sigma.medisto.hwcontrol.errorflag[i]=2;						

					}
			
				
					ajou.sigma.medisto.hwcontrol.nocheckerror = false;
					Toast.makeText(error.this, "section 2 is checked.", Toast.LENGTH_SHORT).show();
				}
			});

			sec3ok.setOnClickListener(new Button.OnClickListener() {


				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					for(int i =8 ; i<12; i++)
					{

						if(ajou.sigma.medisto.hwcontrol.errorflag[i]==1)
							ajou.sigma.medisto.hwcontrol.errorflag[i]=2;						

					}
					
					ajou.sigma.medisto.hwcontrol.nocheckerror = false;
					Toast.makeText(error.this, "section 3 is checked.", Toast.LENGTH_SHORT).show();
				}
			});

			sec4ok.setOnClickListener(new Button.OnClickListener() {


				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					for(int i =12 ; i<16; i++)
					{

						if(ajou.sigma.medisto.hwcontrol.errorflag[i]==1)
							ajou.sigma.medisto.hwcontrol.errorflag[i]=2;						

					}
				
					ajou.sigma.medisto.hwcontrol.nocheckerror = false;
					Toast.makeText(error.this, "section 4 is checked.", Toast.LENGTH_SHORT).show();
				}
			});
			secallok.setOnClickListener(new Button.OnClickListener() {


				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					for(int i =0 ; i<16; i++)
					{

						if(ajou.sigma.medisto.hwcontrol.errorflag[i]==1)
							ajou.sigma.medisto.hwcontrol.errorflag[i]=2;						

					}
					ajou.sigma.medisto.hwcontrol.nocheckerror = false;
					Toast.makeText(error.this, "All section is checked.", Toast.LENGTH_SHORT).show();

				}
			});
		}

		//Button launch = (Button)findViewById(R.id.btn_login);
		//launch.setOnClickListener(new Button.OnClickListener(){

		//public void onClick(View v) {
		//Intent intent = new Intent(intro.this, login.class);
		//startActivity(intent);
		//}

		//});

	}

	public static void Update()
	{
		if(ajou.sigma.medisto.hwcontrol.secon01)
		{
			if(errorflag[0]==1 || errorflag[0]==2)
				sec1temp.setBackgroundResource(R.drawable.temwarning);
			else
				sec1temp.setBackgroundResource(R.drawable.temnormal);
			if(errorflag[1]==1 || errorflag[1]==2)
				sec1humi.setBackgroundResource(R.drawable.humwarning);
			else
				sec1humi.setBackgroundResource(R.drawable.humnormal);
			if(errorflag[2]==1|| errorflag[2]==2)
				sec1illu.setBackgroundResource(R.drawable.illwarning);
			else
				sec1illu.setBackgroundResource(R.drawable.illnormal);
			if(errorflag[3]==1|| errorflag[3]==2)
				sec1shld.setBackgroundResource(R.drawable.shiwarning);			
			else
				sec1shld.setBackgroundResource(R.drawable.shinormal);		

		}
		else
		{
			sec1temp.setBackgroundResource(R.drawable.temoff);
			sec1humi.setBackgroundResource(R.drawable.humoff);
			sec1illu.setBackgroundResource(R.drawable.illoff);
			sec1shld.setBackgroundResource(R.drawable.shioff);

		}

		if(ajou.sigma.medisto.hwcontrol.secon02)
		{
			if(errorflag[4]==1|| errorflag[4]==2)
				sec2temp.setBackgroundResource(R.drawable.temwarning);
			else
				sec2temp.setBackgroundResource(R.drawable.temnormal);
			if(errorflag[5]==1|| errorflag[5]==2)
				sec2humi.setBackgroundResource(R.drawable.humwarning);
			else
				sec2humi.setBackgroundResource(R.drawable.humnormal);
			if(errorflag[6]==1|| errorflag[6]==2)
				sec2illu.setBackgroundResource(R.drawable.illwarning);
			else
				sec2illu.setBackgroundResource(R.drawable.illnormal);
			if(errorflag[7]==1|| errorflag[7]==2)
				sec2shld.setBackgroundResource(R.drawable.shiwarning);	
			else
				sec2shld.setBackgroundResource(R.drawable.shinormal);
		}
		else
		{
			sec2temp.setBackgroundResource(R.drawable.temoff);
			sec2humi.setBackgroundResource(R.drawable.humoff);
			sec2illu.setBackgroundResource(R.drawable.illoff);
			sec2shld.setBackgroundResource(R.drawable.shioff);

		}

		if(ajou.sigma.medisto.hwcontrol.secon03)
		{
			if(errorflag[8]==1|| errorflag[8]==2)
				sec3temp.setBackgroundResource(R.drawable.temwarning);
			else
				sec3temp.setBackgroundResource(R.drawable.temnormal);
			if(errorflag[9]==1|| errorflag[9]==2)
				sec3humi.setBackgroundResource(R.drawable.humwarning);
			else
				sec3humi.setBackgroundResource(R.drawable.humnormal);
			if(errorflag[10]==1|| errorflag[10]==2)
				sec3illu.setBackgroundResource(R.drawable.illwarning);
			else
				sec3illu.setBackgroundResource(R.drawable.illnormal);
			if(errorflag[11]==1|| errorflag[11]==2)
				sec3shld.setBackgroundResource(R.drawable.shiwarning);	
			else
				sec3shld.setBackgroundResource(R.drawable.shinormal);
		}
		else
		{
			sec3temp.setBackgroundResource(R.drawable.temoff);
			sec3humi.setBackgroundResource(R.drawable.humoff);
			sec3illu.setBackgroundResource(R.drawable.illoff);
			sec3shld.setBackgroundResource(R.drawable.shioff);

		}

		if(ajou.sigma.medisto.hwcontrol.secon04)
		{
			if(errorflag[12]==1|| errorflag[12]==2)
				sec4temp.setBackgroundResource(R.drawable.temwarning);
			else
				sec4temp.setBackgroundResource(R.drawable.temnormal);
			if(errorflag[13]==1|| errorflag[13]==2)
				sec4humi.setBackgroundResource(R.drawable.humwarning);
			else
				sec4humi.setBackgroundResource(R.drawable.humnormal);
			if(errorflag[14]==1|| errorflag[14]==2)
				sec4illu.setBackgroundResource(R.drawable.illwarning);
			else
				sec4illu.setBackgroundResource(R.drawable.illnormal);
			if(errorflag[15]==1|| errorflag[15]==2)
				sec4shld.setBackgroundResource(R.drawable.shiwarning);	
			else
				sec4shld.setBackgroundResource(R.drawable.shinormal);
		}
		else
		{
			sec4temp.setBackgroundResource(R.drawable.temoff);
			sec4humi.setBackgroundResource(R.drawable.humoff);
			sec4illu.setBackgroundResource(R.drawable.illoff);
			sec4shld.setBackgroundResource(R.drawable.shioff);

		}

	};


	static Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {    
			switch(msg.what){
			case 1:
				Update();
				break;	

			}
		}

	};



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ajou.sigma.medisto.hwcontrol.errorclasscheck = true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ajou.sigma.medisto.hwcontrol.errorclasscheck = true;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		ajou.sigma.medisto.hwcontrol.errorclasscheck = true;
	}
}