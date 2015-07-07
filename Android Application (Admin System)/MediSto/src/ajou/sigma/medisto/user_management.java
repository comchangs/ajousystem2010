package ajou.sigma.medisto;

import java.net.*;
import java.util.*;

import org.xmlpull.v1.*;

import android.app.*;
import android.content.*;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class user_management extends ListActivity {
    /** Called when the activity is first created. */
	ArrayAdapter <String> mAdapter;
	static String[][] parsedata;
    static int i = 0;
    static int j = 0;
    @Override
	public void onStart() {
		super.onStart();
		System.loadLibrary("MediSto");
   	    ajou.sigma.medisto.hwcontrol.PrinttextLCD("User","Management...");
	}
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        
	        
	        setContentView(R.layout.user_management);	

        mAdapter = new ArrayAdapter<String>(
        		getApplicationContext(), 
        		android.R.layout.simple_list_item_1, 
        		new ArrayList<String>());
        
        setListAdapter(mAdapter);
        
        i = 0;
        dbXml();
        addStringData();
        
        Button bt = (Button)findViewById(R.id.btn_logalllist);
        bt.setOnClickListener(new OnClickListener(){


			public void onClick(View arg0) {
				Intent intent = new Intent(user_management.this, user_add.class);
			    startActivity(intent);
			}});
    }
    
	private void dbXml() {
		parsedata = new String[30][3];
		j = 0;
		try{
        	URL text = new URL( "http://dev.jwnc.net/sysprog/member_read.php" );

        	XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
        	XmlPullParser parser = parserCreator.newPullParser();

        	parser.setInput( text.openStream(), null );

        	Log.i("XML", "파싱 중..");
        	int parserEvent = parser.getEventType();
        	String tag;
        	
        	
        	boolean inUser = false;
        	boolean inPassword = false;
        	boolean inLevel = false;
        	while (parserEvent != XmlPullParser.END_DOCUMENT ){
        		switch(parserEvent){

        		case XmlPullParser.TEXT:
        			tag = parser.getName();
        			if (inUser) {
        				Log.i("XML Parse", "user = " + parser.getText() );
        				j++;
        				i++;
        				parsedata[j-1][0] = parser.getText();
        			}
        			if (inPassword) {
        				Log.i("XML Parse", "password = " + parser.getText() );
        				parsedata[j-1][1] = parser.getText();                  	
        			}
        			if (inLevel) {
        				Log.i("XML Parse", "level = " + parser.getText() ); 
        				parsedata[j-1][2] = parser.getText();
        			}
        			break;
        			
        			
        		case XmlPullParser.END_TAG:
        			tag = parser.getName();
        			if (tag.compareTo("user") == 0) {
        				inUser = false;
        			}
        			if (tag.compareTo("password") == 0) {
        				inPassword = false;
        			}
        			if (tag.compareTo("level") == 0) {
        				inLevel = false;
        			}
        			break;	
        			
        		case XmlPullParser.START_TAG:
        			tag = parser.getName();

        			if (tag.compareTo("user") == 0) {
        				inUser = true;
        			}
        			if (tag.compareTo("password") == 0) {
        				inPassword = true;
        			}
        			if (tag.compareTo("level") == 0) {
        				inLevel = true;
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
	
	
	
    private void addStringData(){
    	
    	
    	
    	//TODO
        //mAdapter.add("dfsdf");
    	for(int z= 0; z < j; z++) {
	    	mAdapter.add(parsedata[z][0].toString());
    	}
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	     
	
		//TODO
		user_modify.user = mAdapter.getItem(position);
		Intent intent = new Intent(user_management.this, user_modify.class);
	    startActivity(intent);
	}
}