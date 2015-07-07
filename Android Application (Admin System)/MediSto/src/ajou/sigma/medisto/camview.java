package ajou.sigma.medisto;

import android.app.Activity;
import android.content.*;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;

public class camview extends Activity {

	private ImageView	iv;
	private static Bitmap	bm;
	private int[] pixels;
	private int	width,height;
	public static int decode_bar;
	boolean flag = true;
	
	private  TextView tv;
	private Button bt  ;

	private static final int START_PREVIEW = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


		setContentView(R.layout.camview);

		
		
		
		
		/**/


		ajou.sigma.medisto.hwcontrol.CISCameraIOctl(0x0);
		iv = (ImageView)findViewById(R.id.image);
		bt = (Button)findViewById(R.id.btn_camera_OK);
		tv = (TextView)findViewById(R.id.camera);
		
		
		switch(ajou.sigma.medisto.state.sectionnum)
		{
	    case 1:
			tv.setText("SECTION01");
			break;
		case 2:
			tv.setText("SECTION02");
			break;
		case 3:
			tv.setText("SECTION03");
			break;
		case 4:
			tv.setText("SECTION04");
			break;		
		}
		
		
		bt.setOnClickListener(new Button.OnClickListener() {			

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mHandler.removeMessages(START_PREVIEW);
				finish();
				
			}
		});

		width = 320; 
		height = 240;  
		pixels = new int[width*height*2];  

		bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		if(flag)
			mHandler.sendEmptyMessage(START_PREVIEW);	

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag = false;
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		flag = false;
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {           
			switch(msg.what){
			case START_PREVIEW:
				ajou.sigma.medisto.hwcontrol.CISCameraControl(pixels,width,height);
				bm.setPixels(pixels, 0, 320, 0, 0, 320, 240);

				iv.setImageBitmap(bm);        

				sendMessageDelayed(obtainMessage(START_PREVIEW), 1);
				break;
			default:
				break;
			}
		}
	};

}