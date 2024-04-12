package com.example.kodrok_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WifiState extends AppCompatActivity {
    private static final String LOG_TAG = "AndroidExample";

    private static final int MY_REQUEST_CODE = 123;

    private WifiManager wifiManager;

    private Button buttonState;
    private Button buttonScan;
    ImageButton profile;

    private EditText editTextPassword;
    private LinearLayout linearLayoutScanResults;
    private TextView textViewScanResults;

    private WifiBroadcastReceiver wifiReceiver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_state);

        this.wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Instantiate broadcast receiver
        this.wifiReceiver = new WifiBroadcastReceiver();
        profile = findViewById(R.id.imageButton3);

        // Register the receiver
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        //
        this.buttonState = this.findViewById(R.id.button_state);
        this.buttonScan = (Button) this.findViewById(R.id.button_scan);

        this.editTextPassword = (EditText) this.findViewById(R.id.editText_password);
        this.textViewScanResults = (TextView) this.findViewById(R.id.textView_scanResults);
        this.linearLayoutScanResults = (LinearLayout) this.findViewById(R.id.linearLayout_scanResults);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WifiState.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        this.buttonState.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showWifiState();
            }
        });

        this.buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askAndStartScanWifi();
            }
        });
    }


    private void askAndStartScanWifi()  {

        // With Android Level >= 23, you have to ask the user
        // for permission to Call.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23
            int permission1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

            // Check for permissions
            if (permission1 != PackageManager.PERMISSION_GRANTED) {

                Log.d(LOG_TAG, "Requesting Permissions");

                // Request permissions
                ActivityCompat.requestPermissions(this,
                        new String[] {
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.ACCESS_NETWORK_STATE
                        }, MY_REQUEST_CODE);
                return;
            }
            Log.d(LOG_TAG, "Permissions Already Granted");
        }
        this.doStartScanWifi();
    }

    private void doStartScanWifi()  {
        this.wifiManager.startScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(LOG_TAG, "onRequestPermissionsResult");

        if (requestCode == MY_REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                Log.d(LOG_TAG, "Permission Granted: " + permissions[0]);

                // Start Scan Wifi.
                this.doStartScanWifi();
            } else {
                // Permission denied, boo! Disable the
                // functionality that depends on this permission.
                Log.d(LOG_TAG, "Permission Denied: " + permissions[0]);
            }
            // Other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void showWifiState()  {
        int state = this.wifiManager.getWifiState();
        String statusInfo = "Unknown";

        switch (state)  {
            case WifiManager.WIFI_STATE_DISABLING:
                statusInfo = "Disabling";
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                statusInfo = "Disabled";
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                statusInfo = "Enabling";
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                statusInfo = "Enabled";
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                statusInfo = "Unknown";
                break;
            default:
                statusInfo = "Unknown";
                break;
        }
        Toast.makeText(this, "Wifi Status: " + statusInfo, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop()  {
        this.unregisterReceiver(this.wifiReceiver);
        super.onStop();
    }


    // Define class to listen to broadcasts
    class WifiBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent)   {
            Log.d(LOG_TAG, "onReceive()");

            Toast.makeText(WifiState.this, "Scan Complete!", Toast.LENGTH_SHORT).show();

            boolean ok = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);

            if (ok)  {
                Log.d(LOG_TAG, "Scan OK");

                @SuppressLint("MissingPermission") List<ScanResult> list = wifiManager.getScanResults();

                WifiState.this.showNetworks(list);
                WifiState.this.showNetworksDetails(list);
            }  else {
                Log.d(LOG_TAG, "Scan not OK");
            }

        }
    }

    private void showNetworks(List<ScanResult> results) {
        this.linearLayoutScanResults.removeAllViews();

        for( final ScanResult result: results)  {
            final String networkCapabilities = result.capabilities;
            final String networkSSID = result.SSID; // Network Name.
            //
            Button button = new Button(this);

            button.setText(networkSSID + " ("+networkCapabilities+")");
            this.linearLayoutScanResults.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String networkCapabilities = result.capabilities;
                    connectToNetwork(networkCapabilities, networkSSID);
                }
            });
        }
    }

    private void showNetworksDetails(List<ScanResult> results)  {

        this.textViewScanResults.setText("");
        StringBuilder sb = new StringBuilder();
        sb.append("Result Count: " + results.size());

        for(int i = 0; i < results.size(); i++ )  {
            ScanResult result = results.get(i);
            sb.append("\n\n  --------- Network " + i + "/" + results.size() + " ---------");

            sb.append("\n result.capabilities: " + result.capabilities);
            sb.append("\n result.SSID: " + result.SSID); // Network Name.

            sb.append("\n result.BSSID: " + result.BSSID);
            sb.append("\n result.frequency: " + result.frequency);
            sb.append("\n result.level: " + result.level);

            sb.append("\n result.describeContents(): " + result.describeContents());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { // Level 17, Android 4.2
                sb.append("\n result.timestamp: " + result.timestamp);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Level 23, Android 6.0
                sb.append("\n result.centerFreq0: " + result.centerFreq0);
                sb.append("\n result.centerFreq1: " + result.centerFreq1);
                sb.append("\n result.venueName: " + result.venueName);
                sb.append("\n result.operatorFriendlyName: " + result.operatorFriendlyName);
                sb.append("\n result.channelWidth: " + result.channelWidth);
                sb.append("\n result.is80211mcResponder(): " + result.is80211mcResponder());
                sb.append("\n result.isPasspointNetwork(): " + result.isPasspointNetwork() );
            }
        }
        this.textViewScanResults.setText(sb.toString());
    }

    private void connectToNetwork(String networkCapabilities, String networkSSID)  {
        Toast.makeText(this, "Connecting to network: "+ networkSSID, Toast.LENGTH_SHORT).show();

        String networkPass = this.editTextPassword.getText().toString();
        //
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID =  "\"" + networkSSID + "\"";

        if(networkCapabilities.toUpperCase().contains("WEP")) { // WEP Network.
            Toast.makeText(this, "WEP Network", Toast.LENGTH_SHORT).show();

            wifiConfig.wepKeys[0] = "\"" + networkPass + "\"";
            wifiConfig.wepTxKeyIndex = 0;
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        } else if(networkCapabilities.toUpperCase().contains("WPA")) { // WPA Network
            Toast.makeText(this, "WPA Network", Toast.LENGTH_SHORT).show();
            wifiConfig.preSharedKey = "\""+ networkPass +"\"";
        } else  { // OPEN Network.
            Toast.makeText(this, "OPEN Network", Toast.LENGTH_SHORT).show();
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        this.wifiManager.addNetwork(wifiConfig);

        @SuppressLint("MissingPermission") List<WifiConfiguration> list = this.wifiManager.getConfiguredNetworks();
        for( WifiConfiguration config : list ) {
            if(config.SSID != null && config.SSID.equals("\"" + networkSSID + "\"")) {
                this.wifiManager.disconnect();
                this. wifiManager.enableNetwork(config.networkId, true);
                this.wifiManager.reconnect();
                break;
            }
        }
    }
}