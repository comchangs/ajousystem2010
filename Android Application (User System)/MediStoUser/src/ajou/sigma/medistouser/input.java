package ajou.sigma.medistouser;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class input extends TabActivity {
    /** Called when the activity is first created. */
	static String num;
	static String[] parsedata = new String [12];

	public static Activity inputActivity;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
             
        System.loadLibrary("MediStoUser");

   	    TabHost tabhost = getTabHost();

        tabhost.addTab(tabhost.newTabSpec("input_code").setIndicator("Barcode").setContent(new Intent(this, input_code.class)));
       
        tabhost.addTab(tabhost.newTabSpec("input_num").setIndicator("Passive").setContent(new Intent(this, input_num.class)));

        for(int tab = 0 ; tab < tabhost.getTabWidget().getChildCount(); ++tab)
        {
        	tabhost.getTabWidget().getChildAt(tab).getLayoutParams().height=30;
        }
    }
}