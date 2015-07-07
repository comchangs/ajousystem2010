package ajou.sigma.medistouser;

import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class input_code extends Activity {
    /** Called when the activity is first created. */
	private ImageView iv;
	private static Bitmap bm;
	private Button bt;
	private int[] pixels;
	private int width,height;
	public static int decode_bar;
	boolean flag=true;

	private static final int START_PREVIEW = 1;
	private static final int DECODE = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.input_code);

		System.loadLibrary("MediStoUser");

		ajou.sigma.medistouser.hwcontrol.CISCameraIOctl(0x0);
		iv = (ImageView)findViewById(R.id.inp_image);
		bt = (Button)findViewById(R.id.inp_Button01);
		width = 320; 
		height = 240;  
		pixels = new int[width*height*2];  
		bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		if(flag)
		mHandler.sendEmptyMessage(START_PREVIEW);

		bt.setOnClickListener(new Button.OnClickListener() {


			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				hwcontrol.BuzzerControl(1);
//				hwcontrol.BuzzerControl(0);
				
				Log.d("JH","Onclick");
				if(flag)
				mHandler.sendEmptyMessage(DECODE);
				Log.d("JH","DoneDecode");
				Log.d("JH",Integer.toString(decode_bar));
				//				Toast.makeText(input_code.this, decode_bar, Toast.LENGTH_SHORT).show();
				//				

				//TODO increse in db (barcode saved in variable decode_bar) or create content

			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("test::", "stop1");
		flag = false;
		Log.d("test::", "stop2");

		Log.d("test::", "stop3");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		flag = true;
	}

	//decoding barcode
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {           
			switch(msg.what){
			case START_PREVIEW:
				ajou.sigma.medistouser.hwcontrol.CISCameraControl(pixels,width,height);
				bm.setPixels(pixels, 0, 320, 0, 0, 320, 120);

				for(int x=0;x<45;x++)
				{
					bm.setPixel(x, 58, 0xAAFFFFFF);
					bm.setPixel(x, 59, 0xAAFFFFFF);
					bm.setPixel(x, 60, 0xAAFFFFFF);
					bm.setPixel(x, 61, 0xAAFFFFFF);
				}
				
				for(int x=275;x<320;x++)
				{
					bm.setPixel(x, 58, 0xAAFFFFFF);
					bm.setPixel(x, 59, 0xAAFFFFFF);
					bm.setPixel(x, 60, 0xAAFFFFFF);
					bm.setPixel(x, 61, 0xFFFFFFFF);
				}
				for(int x=0; x<290; x++)
				{
					int bValue = bm.getPixel(x,61) & 0x000000FF;
					int gValue = (bm.getPixel(x,61) & 0x0000FF00) >> 8;
				int rValue = (bm.getPixel(x,61) & 0x00FF0000) >> 16;

				if(rValue> gValue  && rValue> bValue )
				{
					bm.setPixel(x, 61, 0xFFFF0000);
				}
				if(rValue < gValue  && bValue < gValue )
				{
					bm.setPixel(x, 61, 0xFF00FF00);
				}
				if(rValue < bValue && gValue <bValue )
				{
					bm.setPixel(x, 61, 0xFF0000FF);
				}

				if(rValue > 160 && gValue > 160 && bValue > 160)
				{        
					bm.setPixel(x, 61, 0xFFFFFFFF);
				}    

				if(rValue < 95 && gValue < 95 && bValue < 95){
					bm.setPixel(x, 61, 0xFF000000);
				}


				}
				iv.setImageBitmap(bm);        

				sendMessageDelayed(obtainMessage(START_PREVIEW), 1);
				break;

			case DECODE:
				System.loadLibrary("MediStoUser");
				
				hwcontrol.BuzzerControl(1);
				for (int a = 0; a < 400000; a++) {}
				hwcontrol.BuzzerControl(0);
				Log.i("JH","789");
				mHandler.removeMessages(START_PREVIEW);
				Log.i("JH","012");
				int result[] = new int[20];
				//int value[] = new int [5];   
				int i=0;

				int r,g,b;

				int rsum=0,bsum=0,gsum=0, colorcounter = 0, whitecounter = 0;

				iv.setImageBitmap(bm);    
				for(int x=45; x<290; x++)
				{
					b = bm.getPixel(x,61) & 0x000000FF;
					g = ((bm.getPixel(x,61) & 0x0000FF00)) >> 8;
				r = ((bm.getPixel(x,61) & 0x00FF0000)) >> 16;

				if(r==255 && b==255 && g ==255)
				{
					whitecounter++;          
				}
				else
				{
					colorcounter ++;
					if(colorcounter > 10)
						whitecounter = 0;

					rsum += r;
					gsum += g;
					bsum += b;           
				}
				Log.d("InDecode","decoding");
				if( whitecounter == 5 &&colorcounter !=0 )
				{


					rsum = rsum / colorcounter;
					gsum = gsum / colorcounter;
					bsum = bsum / colorcounter;

					if(rsum> gsum  && rsum> bsum )//red
					{
						result[i] = 1;
					}
					if(rsum < gsum  && bsum < gsum )//green
					{
						result[i] = 2;
					}
					if(rsum < bsum && gsum <bsum )//blue
					{
						result[i] = 3;
					}

					if(rsum < 90 && gsum < 90 && bsum < 90)//black
					{
						result[i] = 4;
					}

					i++;
					rsum = 0; gsum = 0 ; bsum=0; colorcounter=0;
				}
				}
			
				Log.d("INDECODE","HI");

				if(result[4]==0||result[5]!=0)
				{
					Toast.makeText(input_code.this, "barcode decoding error", Toast.LENGTH_SHORT).show();
					mHandler.sendEmptyMessage(START_PREVIEW);
				}
				else{
					decode_bar = (result[0]-1)*256+(result[1]-1)*64+(result[2]-1)*16+(result[3]-1)*4+result[4]-1;
					Log.e("INDECODE",Integer.toString(decode_bar));


					for(int i1=0; i1<12; i1++) {
						ajou.sigma.medistouser.input.parsedata[i1]="";
					}

					Toast.makeText(input_code.this, "barcode : " + Integer.toString(decode_bar), Toast.LENGTH_SHORT).show();
					ajou.sigma.medistouser.input.num = Integer.toString(decode_bar);
					parsing_barcode();

					if(ajou.sigma.medistouser.input.parsedata[2].equals("")) {
						mHandler.sendEmptyMessage(START_PREVIEW);
						Toast.makeText(input_code.this, Integer.toString(decode_bar) + " : 해당 품목이 없습니다.", Toast.LENGTH_SHORT).show();
					} else {
					if(Integer.parseInt(login.level)<=Integer.parseInt(ajou.sigma.medistouser.input.parsedata[2])) {
						Intent intent = new Intent (input_code.this, item_increase.class);
						startActivity(intent);
					} else {
						Toast.makeText(input_code.this, "허가 거부되었습니다.", Toast.LENGTH_SHORT).show();
					}
					}
				}
				break;
			default:
				break;
			}
		}
	};

	public void parsing_barcode() {
		try{
			URL text = new URL( "http://dev.jwnc.net/sysprog/resource_search.php?num="+ajou.sigma.medistouser.input.num );

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
						ajou.sigma.medistouser.input.parsedata[0] = parser.getText();
					}
					if (inName) {
						Log.i("XML Parse", "name = " + parser.getText() );
						ajou.sigma.medistouser.input.parsedata[1] = parser.getText();                  	
					}
					if (inLevel) {
						Log.i("XML Parse", "level = " + parser.getText() ); 
						ajou.sigma.medistouser.input.parsedata[2] = parser.getText();
					}
					if (inTemp_max) {
						Log.i("XML Parse", "temp_max = " + parser.getText() );
						ajou.sigma.medistouser.input.parsedata[3] = parser.getText();
					}
					if (inTemp_min) {
						Log.i("XML Parse", "temp_min = " + parser.getText() );  
						ajou.sigma.medistouser.input.parsedata[4] = parser.getText();
					}
					if (inHumi_max) {
						Log.i("XML Parse", "humi_max = " + parser.getText() );  
						ajou.sigma.medistouser.input.parsedata[5] = parser.getText();
					}
					if (inHumi_min) {
						Log.i("XML Parse", "humi_min = " + parser.getText() );
						ajou.sigma.medistouser.input.parsedata[6] = parser.getText();
					}
					if (inIllu) {
						Log.i("XML Parse", "illu = " + parser.getText() );  
						ajou.sigma.medistouser.input.parsedata[7] = parser.getText();
					}
					if (inSection1) {
						Log.i("XML Parse", "section1 = " + parser.getText() );
						ajou.sigma.medistouser.input.parsedata[8] = parser.getText();
					}
					if (inSection2) {
						Log.i("XML Parse", "section2 = " + parser.getText() );
						ajou.sigma.medistouser.input.parsedata[9] = parser.getText();
					}
					if (inSection3) {
						Log.i("XML Parse", "section3 = " + parser.getText() );
						ajou.sigma.medistouser.input.parsedata[10] = parser.getText();
					}
					if (inSection4) {
						Log.i("XML Parse", "section4 = " + parser.getText() );
						ajou.sigma.medistouser.input.parsedata[11] = parser.getText();
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

	}
}