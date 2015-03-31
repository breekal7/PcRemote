package saifur.appbox.pcremote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Wmpplayer extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wmpplayer);
	}
	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub  //delete this return super.onCreateOptionsMenu(menu);
		
		MenuInflater blowUp1 =getMenuInflater();
		blowUp1.inflate(R.menu.optionmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
