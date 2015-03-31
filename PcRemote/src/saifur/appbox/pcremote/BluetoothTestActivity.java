package saifur.appbox.pcremote;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothTestActivity extends Activity {
	private static final int REQUEST_ENABLE_BT = 243;

	@Override
	protected void onResume() {
		//connector = null;
		//check = false;
		super.onResume();
	}

	@Override
	protected void onStop() {
	//	connector.cancel();
		super.onStop();
	}

	// ////////////////////////////
	private boolean check = false;
	private ConnectThread connector = null;

	private static BluetoothTestActivity singleton;

	// Returns the application instance
	public static BluetoothTestActivity getInstance() {
		if (singleton == null) {
			singleton = new BluetoothTestActivity();
		}
		return singleton;
	}

	public ConnectThread getCon() {
		return this.connector;
	}
	
	
	
	

	private Set<BluetoothDevice> pairedDevices = null;
	private BluetoothAdapter mbluetoothAdapter = null;

	private Button bPaired = null;
	private Button bDiscover = null;
	private Button bStopDiscovery = null;
	private Button bRemote = null;

	private ArrayAdapter<Item> mpArrayAdpater = null;
	private ArrayAdapter<Item> mdArrayAdpater = null;
	private ListView pairedList = null;
	private ListView discoveredList = null;



	private List<Item> pSet = null;
	private List<Item> dSet = null;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Item Item = new Item(device.getName(), device);
				mdArrayAdpater.add(Item);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_test);

		singleton = this;
		bPaired = (Button) findViewById(R.id.bPaired);
		bDiscover = (Button) findViewById(R.id.bDiscover);
		bStopDiscovery = (Button) findViewById(R.id.bStop);

		// these three to control slide motion
		bRemote = (Button) findViewById(R.id.bRemote);


		pairedList = (ListView) findViewById(R.id.listPaired);
		discoveredList = (ListView) findViewById(R.id.listDiscovered);


		pSet = new ArrayList<Item>();
		dSet = new ArrayList<Item>();

		mpArrayAdpater = new ArrayAdapter<Item>(getApplicationContext(),
				android.R.layout.simple_list_item_1, pSet);
		mdArrayAdpater = new ArrayAdapter<Item>(getApplicationContext(),
				android.R.layout.simple_list_item_1, dSet);

		pairedList.setAdapter(mpArrayAdpater);
		discoveredList.setAdapter(mdArrayAdpater);

		// Get Bluetooth device and enable it if not enabled
		mbluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


		if (mbluetoothAdapter == null) {
			Toast.makeText(getApplicationContext(),
					"device donot support bluetooth ", Toast.LENGTH_LONG)
					.show();
		}

		if (!mbluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}

		// Button's onclickListener
		bPaired.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pairedDevices = mbluetoothAdapter.getBondedDevices();
				if (pairedDevices.size() > 0) {
					for (BluetoothDevice bdevice : pairedDevices) {
						Item Item = new Item(bdevice.getName(), bdevice);
						mpArrayAdpater.add(Item);
					}
				}

			}
		});

		bDiscover.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentFilter filter = new IntentFilter(
						BluetoothDevice.ACTION_FOUND);
				registerReceiver(mReceiver, filter);
				mdArrayAdpater.clear();
				mbluetoothAdapter.startDiscovery();
			}
		});

		bRemote.setText("Start");
		bRemote.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (check == true) {
					bRemote.setText("Stop");
					check = false;

					/*
				Intent ourIntent = new Intent(BluetoothTestActivity.this,Kmpplayer.class);
					startActivity(ourIntent);
	*/			
					Intent ourIntent = new Intent(BluetoothTestActivity.this,StartMenu.class);
					startActivity(ourIntent);
	 


				} else {
					bRemote.setText("Start");
					if (connector != null)
						connector.cancel();
					check = true;
				}
			}
		});
/*
	///comment it out to run correctly
		bStopDiscovery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mbluetoothAdapter.cancelDiscovery();
				
			}
		});

		/*
		 * to make your device discoverable Intent discoverableIntent = new
		 * Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		 * discoverableIntent
		 * .putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		 * startActivity(discoverableIntent);
		 */

		// listView onItemClickListeners
		pairedList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView parent, View view,
							int position, long id) {
						Item Item = (Item) parent.getAdapter()
								.getItem(position);
						String texte = Item.getDevName() + " "
								+ Item.getBdevice().getAddress();
						Toast.makeText(getApplicationContext(), texte,
								Toast.LENGTH_SHORT).show();
					}
				});

		discoveredList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView parent, View view,
							int position, long id) {
						Item Item = (Item) parent.getAdapter()
								.getItem(position);
						String texte = Item.getDevName() + " "
								+ Item.getBdevice().getAddress();
						Toast.makeText(getApplicationContext(), texte,
								Toast.LENGTH_SHORT).show();

						// If without disconnecting one tries to connect again
						if (connector != null)
							connector.cancel();
						check = false;
						bRemote.setText("Start");
						// ----------------------------------------------------

						mbluetoothAdapter.cancelDiscovery();
						connector = new ConnectThread(Item.getBdevice());
						connector.start();
						try {
							Thread.sleep(2000, 0);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Toast.makeText(getApplicationContext(), "now start",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT) {
			Toast.makeText(getApplicationContext(),
					"Bluetooth enabled perfectly", Toast.LENGTH_SHORT).show();
		}
	}

	// Needed for Adpater to able store more info and to have a proper view of
	// B-devices
	class Item {
		String devName;
		BluetoothDevice bdevice;

		public Item(String devName, BluetoothDevice bdevice) {
			super();
			this.devName = devName;
			this.bdevice = bdevice;
		}

		public String getDevName() {
			return devName;
		}

		public void setDevName(String devName) {
			this.devName = devName;
		}

		public  BluetoothDevice getBdevice() {
			return bdevice;
		}

		public void setBdevice(BluetoothDevice bdevice) {
			this.bdevice = bdevice;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.getDevName();
		}
	}

	class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			// Use a temporary object that is later assigned to mmSocket,
			// because mmSocket is final
			BluetoothSocket tmp = null;
			mmDevice = device;

			// Get a BluetoothSocket to connect with the given BluetoothDevice
			try {
				// MY_UUID is the app's UUID string, also used by the server
				// code
				UUID uuid = UUID
						.fromString("0f2b61c1-8be2-40e6-ab90-e735818da0a7");
				tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);

			} catch (IOException e) {
			}
			mmSocket = tmp;
		}

		public void run() {
			// Cancel discovery because it will slow down the connection
			try {
				// Connect the device through the socket. This will block
				// until it succeeds or throws an exception
				mmSocket.connect();
			} catch (IOException connectException) {
				// Unable to connect; close the socket and get out
				try {
					mmSocket.close();
				} catch (IOException closeException) {
				}
				return;
			}

			// Do work to manage the connection (in a separate thread)
			manageConnectedSocket(mmSocket);
		}

		private void manageConnectedSocket(BluetoothSocket mmSocket2) {
			// to get things start working set to true
			check = true;
		}

		public BluetoothSocket getMmSocket() {
			return mmSocket;
		}

		// Will cancel an in-progress connection, and close the socket
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}
}
