package ajou.sigma.medisto;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class settabview extends TabActivity {
    /** Called when the activity is first created. */
	@Override
	public void onStart() {
		super.onStart();
        System.loadLibrary("MediSto");
   	    ajou.sigma.medisto.hwcontrol.PrinttextLCD("Section..","  Setting View");
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
             


   	    TabHost tabhost = getTabHost();

        tabhost.addTab(tabhost.newTabSpec("set_tab1").setIndicator("Section1").setContent(new Intent(this, set_tab1.class)));
        
        tabhost.addTab(tabhost.newTabSpec("set_tab2").setIndicator("Section2").setContent(new Intent(this, set_tab2.class)));
        
        tabhost.addTab(tabhost.newTabSpec("set_tab3").setIndicator("Section3").setContent(new Intent(this, set_tab3.class)));
        
        tabhost.addTab(tabhost.newTabSpec("set_tab4").setIndicator("Section4").setContent(new Intent(this, set_tab4.class)));

        
        for(int tab = 0 ; tab < tabhost.getTabWidget().getChildCount(); ++tab)
        {
        	tabhost.getTabWidget().getChildAt(tab).getLayoutParams().height=30;
        	
        }
        
    }
}