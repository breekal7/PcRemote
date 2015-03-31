package saifur.appbox.pcremote;

import java.io.IOException;
import java.io.OutputStream;

import saifur.appbox.pcremote.BluetoothTestActivity.ConnectThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Kmpplayer extends Activity {

	ConnectThread connect;
	OutputStream out = null;

	TextView t;

	Button forward;
	Button backward;
	Button play;
	Button pause;
	Button stop;
	Button next;
	Button previous;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kmpplayer);

		t = (TextView) findViewById(R.id.ktv);
		forward = (Button) findViewById(R.id.kfd);

		forward = (Button) findViewById(R.id.kfd);
		backward = (Button) findViewById(R.id.kbk);
		play = (Button) findViewById(R.id.kpl);
		pause = (Button) findViewById(R.id.kps);
		stop = (Button) findViewById(R.id.kst);
		next = (Button) findViewById(R.id.knx);
		previous = (Button) findViewById(R.id.kprv);

		connect = BluetoothTestActivity.getInstance().getCon();
		try {
			out = connect.getMmSocket().getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			t.setText("n o out");
			e.printStackTrace();
		}
		if (connect == null) {
			t.setText(" null ");
		}

		forward.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					out.write(2);
					t.setText("forward");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		backward.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					out = connect.getMmSocket().getOutputStream();
					out.write(3);
					t.setText("backward");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					out.write(12);
					t.setText("play");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		pause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					out.write(12);
					t.setText("pause");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					out.write(33);
					t.setText("stop");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					out.write(7);
					t.setText("next");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		previous.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					out.write(6);
					t.setText("previous");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub //delete this return
		// super.onCreateOptionsMenu(menu);

		MenuInflater blowUp1 = getMenuInflater();
		blowUp1.inflate(R.menu.optionmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
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
