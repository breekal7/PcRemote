package saifur.appbox.pcremote;



import java.io.IOException;
import java.io.OutputStream;

import saifur.appbox.pcremote.BluetoothTestActivity.ConnectThread;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StartMenu extends ListActivity{

String classes[] = {"MainActivity","Kmpplayer","Wmpplayer"};
ConnectThread connect;
OutputStream out = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
				
		//full screen
			//requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
				connect= BluetoothTestActivity.getInstance().getCon();
	
		setListAdapter(new ArrayAdapter<String>(StartMenu.this, android.R.layout.simple_list_item_1, classes));
		
		
		}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub		
		super.onListItemClick(l, v, position, id);
		String menuItems = classes[position];
		try{
		
		Class ourClass = Class.forName("saifur.appbox.pcremote." + menuItems);
		Intent ourIntent = new Intent(StartMenu.this,ourClass);
		startActivity(ourIntent);
		
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}

	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub  //delete this return super.onCreateOptionsMenu(menu);
		
		MenuInflater blowUp =getMenuInflater();
		blowUp.inflate(R.menu.optionmenu, menu);
		return true;
	}

	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub   //return super.onOptionsItemSelected(item);
			
			switch(item.getItemId()){
			case R.id.aboutUs:
				
				Intent i = new Intent("saifur.appbox.pcremote.ABOUT");
				startActivity(i);
				
				break;
				
			case R.id.exit:
				finish();
				break;
			}
	
			return true;
	}


	
	
}
