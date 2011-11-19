package com.ElikCandy.IPCalc;

//import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class IPCalcActivity extends TabActivity {
	/** Called when the activity is first created. */
	// @Override
	private TextView edtext;
	private EditText edtext1;
	private TabHost mTabHost;
	private Spinner maskSpin;

	Integer Index = 30;
	Integer[] TempOctet = { 0, 0, 0, 0 };
	Integer[] TempMask = { 0, 0, 0, 0 };
	Long TempHex;
	String[] StrOctet = { "", "", "", "" };
	String[] StrMax = { "", "", "", "" };
	String iNet,iMask, iMin,iMax,iBroad,iInvert,iAddr,iNum;
	Integer[][] MyMask = { 
			{ 0, 0, 0, 0, 0 }, 
			{ 128, 0, 0, 0, 2147483646 },
			{ 192, 0, 0, 0, 1073741822 }, 
			{ 224, 0, 0, 0, 536870910 },
			{ 240, 0, 0, 0, 268427774 }, 
			{ 248, 0, 0, 0, 134217726 },
			{ 252, 0, 0, 0, 67108862 }, 
			{ 254, 0, 0, 0, 33554430 },
			{ 255, 0, 0, 0, 16777214 }, 
			{ 255, 128, 0, 0, 8388606 },
			{ 255, 192, 0, 0, 4194302 }, 
			{ 255, 224, 0, 0, 2097150 },
			{ 255, 240, 0, 0, 1048574 }, 
			{ 255, 248, 0, 0, 524286 },
			{ 255, 252, 0, 0, 262142 }, 
			{ 255, 254, 0, 0, 131070 },
			{ 255, 255, 0, 0, 65534 }, 
			{ 255, 255, 128, 0, 32766 },
			{ 255, 255, 192, 0, 16382 }, 
			{ 255, 255, 224, 0, 8190 },
			{ 255, 255, 240, 0, 4094 }, 
			{ 255, 255, 248, 0, 2046 },
			{ 255, 255, 252, 0, 1022 }, 
			{ 255, 255, 254, 0, 510 },
			{ 255, 255, 255, 0, 254 }, 
			{ 255, 255, 255, 128, 126 },
			{ 255, 255, 255, 192, 62 }, 
			{ 255, 255, 255, 224, 30 },
			{ 255, 255, 255, 240, 14 }, 
			{ 255, 255, 255, 248, 6 },
			{ 255, 255, 255, 252, 2 }, 
			{ 255, 255, 255, 254, 0 },
			{ 255, 255, 255, 255, 0 } };

	// TextView tview;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTabHost = getTabHost();
		// tview = new TextView(this);
		mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Расчет").setContent(R.id.countLayout));
		mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Настройки").setContent(R.id.setupLayout));
		mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("История").setContent(R.id.historyLayout));

		mTabHost.setCurrentTab(0);
		
		maskSpin = (Spinner) findViewById(R.id.editMask);
//		maskSpin.I
		

	}

	public void clearHistoryClick(View v) {
//		v.setBackgroundColor(Color.GREEN);
		edtext1 = (EditText) findViewById(R.id.historyText);
		edtext1.setText("");
	}

	public void MessageBox(String Str) {
		Context context = getApplicationContext(); 
//		CharSequence text = "Что за IP адрес?"; 
		int duration = Toast.LENGTH_SHORT; 
		Toast toast = Toast.makeText(context, Str, duration);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();			
	}
	
	public void buttonCount(View v) {
		String opa;
		maskSpin = (Spinner) findViewById(R.id.editMask);
		edtext1 = (EditText) findViewById(R.id.editIP);
		opa = (String) edtext1.getText().toString();
		iAddr = opa;
		StrOctet = opa.split("\\.");
		Integer Index = maskSpin.getSelectedItemPosition();
		iMask = maskSpin.getSelectedItem().toString();
		if ((StrOctet[0] != "" && Integer.parseInt(StrOctet[0]) < 256)
				&& (StrOctet[1] != "" && Integer.parseInt(StrOctet[1]) < 256)
				&& (StrOctet[2] != "" && Integer.parseInt(StrOctet[2]) < 256)
				&& (StrOctet[3] != "" && Integer.parseInt(StrOctet[3]) < 256)) {

			TempOctet[0] = Integer.parseInt(StrOctet[0]) & MyMask[Index][0];
			TempOctet[1] = Integer.parseInt(StrOctet[1]) & MyMask[Index][1];
			TempOctet[2] = Integer.parseInt(StrOctet[2]) & MyMask[Index][2];
			TempOctet[3] = Integer.parseInt(StrOctet[3]) & MyMask[Index][3];

			TempHex = Long.parseLong(String.format("%02X",TempOctet[0]) + String.format("%02X",TempOctet[1]) + String.format("%02X",TempOctet[2]) + String.format("%02X",TempOctet[3]),16);
//			MessageBox(TempHex.toString());
			  
			TempHex = TempHex + MyMask[Index][4]; 
			StrMax[3] = ((Long)(TempHex &  0x000000ff)).toString(); 
			TempHex = TempHex >> 8; 
			StrMax[2] = ((Long)(TempHex & 0x000000ff)).toString(); 
			TempHex = TempHex >> 8;
			StrMax[1] = ((Long)(TempHex & 0x000000ff)).toString(); 
			TempHex = TempHex >> 8; 
			StrMax[0] = ((Long)(TempHex & 0x000000ff)).toString();

			edtext = (TextView) findViewById(R.id.textNetwork);
			iNet = TempOctet[0].toString() + "." +  TempOctet[1].toString() + "." + TempOctet[2].toString() + "." + TempOctet[3].toString(); 
			edtext.setText(iNet);

			edtext = (TextView) findViewById(R.id.textMinhost);
			iMin = TempOctet[0].toString() + "." + TempOctet[1].toString() + "." + TempOctet[2].toString() + "." + ((Integer)(TempOctet[3] + 1)).toString(); 
			edtext.setText(iMin);

			edtext = (TextView) findViewById(R.id.textMaxhost);
			iMax = StrMax[0] + "." + StrMax[1] + "." + StrMax[2] + "." + StrMax[3];
			edtext.setText(iMax);

			edtext = (TextView) findViewById(R.id.textBroadcast);
			iBroad = StrMax[0] + "." + StrMax[1] + "." + StrMax[2] + "." + ((Integer)(Integer.parseInt(StrMax[3])+1)).toString();
			edtext.setText(iBroad);

			edtext = (TextView) findViewById(R.id.textNumofhosts);
			iNum = MyMask[Index][4].toString();
			edtext.setText(iNum);

			edtext = (TextView) findViewById(R.id.textInverted);
			iInvert = 	((Integer)(MyMask[Index][0] ^ 255)).toString() + "." + ((Integer)(MyMask[Index][1] ^ 255)).toString() + "." +
					((Integer)(MyMask[Index][2] ^ 255)).toString() + "." + ((Integer)(MyMask[Index][3] ^ 255)).toString();
			edtext.setText(iInvert);

			edtext1 = (EditText) findViewById(R.id.historyText);
			edtext1.append("Address\t\t\t: "+iAddr+"\n");
			edtext1.append("Bits / Mask\t\t: "+iMask+"\n");
			edtext1.append("Network\t\t\t: "+iNet+"\n");
			edtext1.append("Broadcast\t\t: "+iBroad+"\n");
			edtext1.append("Min address\t\t: "+iMin+"\n");
			edtext1.append("Max address\t\t: "+iMax+"\n");
			edtext1.append("Num of hosts\t: "+iNum+"\n");
			edtext1.append("Inverted mask\t: "+iInvert+"\n");
			edtext1.append("----------------------------\n");
		}
		else {
			MessageBox("Что за IP адрес?");
		}
	}
	public void onQuit (View v) {
		finish();

	}
}