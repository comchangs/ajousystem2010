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

public class res_tab1 extends ListActivity  {
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
		parsedata = new String[30][12];
		j = 0;
		try{
        	URL text = new URL( "http://dev.jwnc.net/sysprog/resource_read1.php?first="+i );

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
        	boolean inSection1= false;
        	boolean inSection2 = false;
        	boolean inSection3 = false;
        	boolean inSection4 = false;
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
        			if (inName) {
        				Log.i("XML Parse", "name = " + parser.getText() );
        				parsedata[j-1][1] = parser.getText();                  	
        			}
        			if (inLevel) {
        				Log.i("XML Parse", "level = " + parser.getText() ); 
        				parsedata[j-1][2] = parser.getText();
        			}
        			if (inTemp_max) {
        				Log.i("XML Parse", "temp_max = " + parser.getText() );
        				parsedata[j-1][3] = parser.getText();
        			}
        			if (inTemp_min) {
        				Log.i("XML Parse", "temp_min = " + parser.getText() );  
        				parsedata[j-1][4] = parser.getText();
        			}
        			if (inHumi_max) {
        				Log.i("XML Parse", "humi_max = " + parser.getText() );  
        				parsedata[j-1][5] = parser.getText();
        			}
        			if (inHumi_min) {
        				Log.i("XML Parse", "humi_min = " + parser.getText() );
        				parsedata[j-1][6] = parser.getText();
        			}
        			if (inIllu) {
        				Log.i("XML Parse", "illu = " + parser.getText() );  
        				parsedata[j-1][7] = parser.getText();
        			}
        			if (inSection1) {
        				Log.i("XML Parse", "section1 = " + parser.getText() );
        				parsedata[j-1][8] = parser.getText();
        			}
        			if (inSection2) {
        				Log.i("XML Parse", "section2 = " + parser.getText() );
        				parsedata[j-1][9] = parser.getText();
        			}
        			if (inSection3) {
        				Log.i("XML Parse", "section3 = " + parser.getText() );
        				parsedata[j-1][10] = parser.getText();
        			}
        			if (inSection4) {
        				Log.i("XML Parse", "section4 = " + parser.getText() );
        				parsedata[j-1][11] = parser.getText();
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
	
	
	
    private void addStringData(){
    	
    	
    	
    	//TODO
        //mAdapter.add("dfsdf");
    	for(int z= 0; z < j; z++) {
	    	mAdapter.add(parsedata[z][0].toString()+" - "+parsedata[z][1].toString()+"\n "+
	    				 "Level: "+parsedata[z][2].toString()+"\n "+
	    				 "Temp: "+parsedata[z][4].toString()+" ~ "+parsedata[z][3].toString()+"\n "+
	    				 "Humi: "+parsedata[z][6].toString()+" ~ "+parsedata[z][5].toString()+"\n "+
	    				 "Illu: "+(Integer.parseInt(parsedata[z][7].toString())>=1?"Light":"Dark")+"\n "+
	    			 	 "Sec1 QTY: "+parsedata[z][8].toString()+"\n "+
	    			 	 "Sec2 QTY: "+parsedata[z][9].toString()+"\n "+
	    			 	 "Sec3 QTY: "+parsedata[z][10].toString()+"\n "+
	    			 	 "Sec4 QTY: "+parsedata[z][11].toString()+"\n ");
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