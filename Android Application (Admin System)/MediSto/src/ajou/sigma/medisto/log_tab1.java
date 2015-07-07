package ajou.sigma.medisto;

import java.net.*;
import java.util.ArrayList;

import org.xmlpull.v1.*;


import android.app.Activity;
import android.app.ListActivity;
import android.content.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class log_tab1 extends ListActivity  {
	/** Called when the activity is first created. */
	ArrayAdapter <String> mAdapter;
	static String[][] parsedata;
    static int i = 0;
    static int j = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_all);  	

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
				dbXml();
				addStringData();
			}});
    }
    
	private void dbXml() {
		parsedata = new String[30][9];
		j = 0;
		try{
        	URL text = new URL( "http://dev.jwnc.net/sysprog/log_read1.php?first="+i );

        	XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
        	XmlPullParser parser = parserCreator.newPullParser();

        	parser.setInput( text.openStream(), null );

        	Log.i("XML", "파싱 중..");
        	int parserEvent = parser.getEventType();
        	String tag;
        	
        	
        	boolean inNum = false;
        	boolean inUser = false;
        	boolean inTemp = false;
        	boolean inHumi = false;
        	boolean inIllu = false;
        	boolean inEvent = false;
        	boolean inSection = false;
        	boolean inSecure = false;
        	boolean inDate= false;
        	boolean inPhoto = false;
        	while (parserEvent != XmlPullParser.END_DOCUMENT ){
        		switch(parserEvent){

        		case XmlPullParser.TEXT:
        			tag = parser.getName();
        			if (inNum) {
        				Log.i("XML Parse", "num = " + parser.getText() );
        				j++;
        				i++;
        				parsedata[j-1][0] = parser.getText();
        			}
        			if (inUser) {
        				Log.i("XML Parse", "user = " + parser.getText() );
        				parsedata[j-1][1] = parser.getText();                  	
        			}
        			if (inTemp) {
        				Log.i("XML Parse", "temp = " + parser.getText() ); 
        				parsedata[j-1][2] = parser.getText();
        			}
        			if (inHumi) {
        				Log.i("XML Parse", "humi = " + parser.getText() );
        				parsedata[j-1][3] = parser.getText();
        			}
        			if (inIllu) {
        				Log.i("XML Parse", "illu = " + parser.getText() );  
        				parsedata[j-1][4] = parser.getText();
        			}
        			if (inEvent) {
        				Log.i("XML Parse", "event = " + parser.getText() );  
        				parsedata[j-1][5] = parser.getText();
        			}
        			if (inSecure) {
        				Log.i("XML Parse", "secure = " + parser.getText() );
        				parsedata[j-1][6] = parser.getText();
        			}
        			if (inSection) {
        				Log.i("XML Parse", "section = " + parser.getText() );  
        				parsedata[j-1][7] = parser.getText();
        			}
        			if (inDate) {
        				Log.i("XML Parse", "date = " + parser.getText() );
        				parsedata[j-1][8] = parser.getText();
        			}
        			if (inPhoto) {
        				Log.i("XML Parse", "photo = " + parser.getText() );                     	
        			}
        			break;
        			
        		case XmlPullParser.END_TAG:
        			tag = parser.getName();
        			if (tag.compareTo("num") == 0) {
        				inNum = false;
        			}
        			if (tag.compareTo("user") == 0) {
        				inUser = false;
        			}
        			if (tag.compareTo("temp") == 0) {
        				inTemp = false;
        			}
        			if (tag.compareTo("humi") == 0) {
        				inHumi = false;
        			}
        			if (tag.compareTo("illu") == 0) {
        				inIllu = false;
        			}
        			if (tag.compareTo("event") == 0) {
        				inEvent = false;
        			}
        			if (tag.compareTo("section") == 0) {
        				inSection = false;
        			}
        			if (tag.compareTo("secure") == 0) {
        				inSecure = false;
        			}
        			if (tag.compareTo("date") == 0) {
        				inDate = false;
        			}
        			if (tag.compareTo("photo") == 0) {
        				inPhoto = false;
        			}
        			break;	
        			
        		case XmlPullParser.START_TAG:
        			tag = parser.getName();

        			if (tag.compareTo("num") == 0) {
        				inNum = true;
        			}
        			if (tag.compareTo("user") == 0) {
        				inUser = true;
        			}
        			if (tag.compareTo("temp") == 0) {
        				inTemp = true;
        			}
        			if (tag.compareTo("humi") == 0) {
        				inHumi = true;
        			}
        			if (tag.compareTo("illu") == 0) {
        				inIllu = true;
        			}
        			if (tag.compareTo("event") == 0) {
        				inEvent = true;
        			}
        			if (tag.compareTo("section") == 0) {
        				inSection = true;
        			}
        			if (tag.compareTo("secure") == 0) {
        				inSecure = true;
        			}
        			if (tag.compareTo("date") == 0) {
        				inDate = true;
        			}
        			if (tag.compareTo("photo") == 0) {
        				inPhoto = true;
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
	    	mAdapter.add(parsedata[z][0].toString()+") "+parsedata[z][5].toString()+"\n "+
	    				 parsedata[z][8].toString()+"\n "+
	    				 "User: "+parsedata[z][1].toString()+"\n "+
	    				 "Temp: "+parsedata[z][2].toString()+" ℃\n "+
	    				 "Humi: "+parsedata[z][3].toString()+" ％\n "+
	    				 "Illu: "+(Integer.parseInt(parsedata[z][4].toString())>=1?"Light":"Dark")+"\n "+
	    			 	 "Secure: "+parsedata[z][6].toString()+"\n ");
    	}
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	     
	
		//TODO
		Toast.makeText(this, mAdapter.getItem(position)+"", 600000).show();
	}
}