package ajou.sigma.medistouser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class hwcontrol extends Activity {
	public static Activity hwActivity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hwActivity = this;
        setContentView(R.layout.hwcontrol);
        
        System.loadLibrary("MediStoUser");
        
		Intent intent = new Intent(hwcontrol.this, login.class);
		startActivity(intent);
    }
    
	public native static int CISCameraIOctl(int cmd);
	public native static int CISCameraControl(int[] image, int width, int height);
	public native static int BuzzerControl(int value);
}