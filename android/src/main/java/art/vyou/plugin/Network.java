package art.vyou.plugin;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkCapabilities;


import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Network {

    /**
     * Interface for callbacks when network status changes.
     */
    interface NetworkStatusChangeListener {
        void onNetworkStatusChanged(boolean wasLostEvent);
    }

    class ConnectivityCallback extends NetworkCallback {

        @Override
        public void onLost(@NonNull android.net.Network network) {
            super.onLost(network);
            statusChangeListener.onNetworkStatusChanged(true);
        }

        @Override
        public void onCapabilitiesChanged(@NonNull android.net.Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            statusChangeListener.onNetworkStatusChanged(false);
        }
    }

    @Nullable
    private NetworkStatusChangeListener statusChangeListener;

    private ConnectivityCallback connectivityCallback;
    private Context context;
    private ConnectivityManager connectivityManager;
    private BroadcastReceiver receiver;
    private WifiManager wifiManager;
      private static final int LOCATION_REQUEST_CODE = 2; // Permissions request code
  private static final int WIFI_SERVICE_INFO_CODE = 3;
  private static final String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    /**
     * Create network monitoring object.
     * @param context
     */
    @SuppressWarnings("deprecation")
    public Network(@NonNull Context context) {
        this.context = context;
        this.connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            receiver =
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        statusChangeListener.onNetworkStatusChanged(false);
                    }
                };
        } else {
            this.connectivityCallback = new ConnectivityCallback();
        }
        //this.requestLocationPermission(LOCATION_REQUEST_CODE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * Set the object to receive callbacks.
     * @param listener
     */
    public void setStatusChangeListener(@Nullable NetworkStatusChangeListener listener) {
        this.statusChangeListener = listener;
    }

    /**
     * Return the object that is receiving the callbacks.
     * @return
     */
    @Nullable
    public NetworkStatusChangeListener getStatusChangeListener() {
        return statusChangeListener;
    }

    /**
     * Get the current network information.
     * @return NetworkStatus
     */
    public NetworkStatus getNetworkStatus() {
        NetworkStatus networkStatus = new NetworkStatus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (this.connectivityManager != null) {
                android.net.Network activeNetwork = this.connectivityManager.getActiveNetwork();
                NetworkCapabilities capabilities =
                    this.connectivityManager.getNetworkCapabilities(this.connectivityManager.getActiveNetwork());
                if (activeNetwork != null && capabilities != null) {
                    networkStatus.connected =
                        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        networkStatus.connectionType = NetworkStatus.ConnectionType.WIFI;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        networkStatus.connectionType = NetworkStatus.ConnectionType.CELLULAR;
                    } else {
                        networkStatus.connectionType = NetworkStatus.ConnectionType.UNKNOWN;
                    }
                }
            }
        } else {
            networkStatus = getAndParseNetworkInfo();
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        Log.i("WifiInfo: ", wifiInfo.toString());
        networkStatus.ssid = wifiInfo.getSSID();
        networkStatus.bssid = wifiInfo.getBSSID();
        final int ipAddress = wifiInfo.getIpAddress();
        final String formattedIpAddress = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        networkStatus.privateIp = formattedIpAddress;
        return networkStatus;
    }

    @SuppressWarnings("deprecation")
    private NetworkStatus getAndParseNetworkInfo() {
        NetworkStatus networkStatus = new NetworkStatus();
        android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            networkStatus.connected = networkInfo.isConnected();
            String typeName = networkInfo.getTypeName();
            if (typeName.equals("WIFI")) {
                networkStatus.connectionType = NetworkStatus.ConnectionType.WIFI;
            } else if (typeName.equals("MOBILE")) {
                networkStatus.connectionType = NetworkStatus.ConnectionType.CELLULAR;
            }

        }
        return networkStatus;
    }

    /**
     * Register a network callback.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startMonitoring() {
        connectivityManager.registerDefaultNetworkCallback(connectivityCallback);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void startMonitoring(AppCompatActivity activity) {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        activity.registerReceiver(receiver, filter);
    }

    /**
     * Unregister the network callback.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(connectivityCallback);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void stopMonitoring(@NonNull AppCompatActivity activity) {
        activity.unregisterReceiver(receiver);
    }

    // wifi stuff

}
