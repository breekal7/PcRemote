package saifur.appbox.pcremote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import saifur.appbox.pcremote.BluetoothTestActivity.ConnectThread;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.text.Editable;
import android.text.GetChars;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ConnectThread connect;
	OutputStream out = null;
	InputStream in = null;

	private GestureDetector gestureDetector;

	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	Button b6;
	EditText e1;
	float disX = 0;
	float disY = 0;
	String message = "working!!\n";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.st);

		b2 = (Button) findViewById(R.id.stl);
		b4 = (Button) findViewById(R.id.str);
		b5 = (Button) findViewById(R.id.stsu);
		b6 = (Button) findViewById(R.id.stsd);

		connect = BluetoothTestActivity.getInstance().getCon();
		try {
			out = connect.getMmSocket().getOutputStream();
			out.write(1);
			in = connect.getMmSocket().getInputStream();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					out = connect.getMmSocket().getOutputStream();
					message = "left\n";
					byte[] msgBuffer = message.getBytes();
					out.flush();
					out.write(msgBuffer);
					out.write(msgBuffer);

				} catch (Exception e) {

				}
			}
		});

		b4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					message = "right\n";
					byte[] msgBuffer = message.getBytes();
					out.flush();
					out.write(msgBuffer);
					out.write(msgBuffer);

				} catch (Exception e) {

				}
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					// PathMeasure pm =new PathMeasure(path, false);
					// float a = pm.getLength();

					out = connect.getMmSocket().getOutputStream();
					message = "scrollup\n";
					byte[] msgBuffer = message.getBytes();
					out.flush();
					out.write(msgBuffer);
					out.write(msgBuffer);

				} catch (Exception e) {

				}
			}
		});
		b6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					out = connect.getMmSocket().getOutputStream();
					message = "scrolldown\n";
					byte[] msgBuffer = message.getBytes();
					out.flush();
					out.write(msgBuffer);
					out.write(msgBuffer);

				} catch (Exception e) {

				}
			}
		});

		View v = findViewById(R.id.lnl);
		v.setOnTouchListener(new View.OnTouchListener() {
			int x = 0;
			int x1 = 0;
			int y = 0;
			int y1 = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					x = (int) event.getX();
					y = (int) event.getY();
					return true;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_OUTSIDE:
					return true;
				case MotionEvent.ACTION_UP:
					x1 = (int) event.getX();
					y1 = (int) event.getY();
					return true;
				}
				disX = x1 - x;
				disY = y1 - y;
				if ((disX > 20) && (disY > 20)) {
					message = "++\n";
				} else if ((disX > 20) && (disY < 20)) {
					message = "+-\n";
				} else if ((disX < 20) && (disY > 20)) {
					message = "-+\n";
				} else if ((disX < 20) && (disY < 20)) {
					message = "--\n";
				} else if ((disX < (disY / 2)) && (disY > 40)) {
					message = "+0\n";
				} else if ((disY < 20) && (disX > 40)) {
					message = "0+\n";
				} else if ((disX < (disY / 2)) && (disY < 40)) {
					message = "-0\n";
				} else if ((disY < 20) && (disX < 40)) {
					message = "0-\n";
				}

				try {
					out = connect.getMmSocket().getOutputStream();
					byte[] msgBuffer = message.getBytes();
					out.flush();
					out.write(msgBuffer);
					out.write(msgBuffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return false;
			}
		});

		// ////////////////////////////////////

		// ////////////////////////////////////
	}

	// /////////////////////////////////////////////////////////////

	// /////////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			out.write(99);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ////////////////

}
