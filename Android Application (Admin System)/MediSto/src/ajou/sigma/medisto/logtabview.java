package ajou.sigma.medisto;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class logtabview extends TabActivity {
    /** Called when the activity is first created. */
	public void onStart() {
		super.onStart();
        System.loadLibrary("MediSto");
   	    ajou.sigma.medisto.hwcontrol.PrinttextLCD("Section..","  Log View");
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
             


   	    TabHost tabhost = getTabHost();

        tabhost.addTab(tabhost.newTabSpec("log_all").setIndicator("All_sec.").setContent(new Intent(this, log_all.class)));
       
        tabhost.addTab(tabhost.newTabSpec("log_tab1").setIndicator("Sec.1").setContent(new Intent(this, log_tab1.class)));
        
        tabhost.addTab(tabhost.newTabSpec("log_tab2").setIndicator("Sec.2").setContent(new Intent(this, log_tab2.class)));
        
        tabhost.addTab(tabhost.newTabSpec("log_tab3").setIndicator("Sec.3").setContent(new Intent(this, log_tab3.class)));
        
        tabhost.addTab(tabhost.newTabSpec("log_tab4").setIndicator("Sec.4").setContent(new Intent(this, log_tab4.class)));

        
        for(int tab = 0 ; tab < tabhost.getTabWidget().getChildCount(); ++tab)
        {
        	tabhost.getTabWidget().getChildAt(tab).getLayoutParams().height=30;
        	
        }
        
    }
}