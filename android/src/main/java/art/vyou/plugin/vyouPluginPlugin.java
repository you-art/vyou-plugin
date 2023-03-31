package art.vyou.plugin;

import android.util.Log;
import java.io.BufferedReader;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@CapacitorPlugin(name = "vyouPlugin")
public class vyouPluginPlugin extends Plugin {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
  
    @PluginMethod
    public void ping(PluginCall call) {
        JSObject ret = new JSObject();
        try {
            Log.i("Pinging", call.getData().toString());
            String ipAddress = call.getString("ipAddress");
            Integer timeOut = call.getInt("timeOut", 1000);
            Integer retries = call.getInt("retries", 1);
            ret.put("pings", retries);
            JSONObject pingResults = doPing(ipAddress, timeOut, retries, "v4");

            ret.put("pongs", pingResults.getString("pctReceived"));
            ret.put("avgRtt", pingResults.getString("avgRtt"));
        } catch (Exception e) {
            Log.w("Ping error", e.toString());
            ret.put("pongs", 0);
            ret.put("avgRtt", 0);
        }
        call.resolve(ret);
    }

    private JSONObject doPing(String ip, Integer timeout, Integer retry, String version) {
        Log.i("Ping", "pinging " + ip);

        String inputLine = "";
        String stringLine = "";
        String transmitted = "";
        double avgRtt = 0;
        double minRtt = 0;
        double maxRtt = 0;
        JSONObject r = new JSONObject();
        Runtime runtime = Runtime.getRuntime();
        try {
            System.out.println(version);

            String command = "/system/bin/ping -n ";
            if (version.toLowerCase().equals("v6")) {
                command = "/system/bin/ping6 -n ";
            }
            if (timeout > 0) {
                command = command + " -W " + timeout;
            }
            if (retry > 0) {
                command = command + " -c " + retry + " ";
            }
            System.out.println(">>" + command + ip);
            Process mIpAddrProcess = runtime.exec(command + ip);
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println("mExitValue" + mExitValue);
            if (mExitValue == 0) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(mIpAddrProcess.getInputStream()));
                inputLine = bufferedReader.readLine();
                while ((inputLine != null)) {
                    System.out.println("Input Line:    " + inputLine);
                    if (inputLine.length() > 0 && inputLine.contains("transmitted")) {
                        transmitted = inputLine;
                    }
                    if (inputLine.length() > 0 && inputLine.contains("avg")) {
                        stringLine = inputLine;
                    }
                    inputLine = bufferedReader.readLine();
                }
                if (stringLine != null) {
                    String afterEqual = stringLine.substring(stringLine.indexOf("=") + 1, stringLine.length()).trim();
                    String[] items = afterEqual.split("/");
                    avgRtt = Double.valueOf(items[1]);
                    minRtt = Double.valueOf(items[0]);
                    maxRtt = Double.valueOf(items[2]);
                    r.put("avgRtt", avgRtt);
                    r.put("minRtt", minRtt);
                    r.put("maxRtt", maxRtt);
                    String s[] = transmitted.trim().split(",");
                    r.put("pctTransmitted", s[0].trim().split(" ")[0]);
                    r.put("pctReceived", s[1].trim().split(" ")[0]);
                    r.put("pctLoss", s[2].trim().split(" ")[0]);
                } else {
                    r.put("avgRtt", 0);
                }
            } else {
                avgRtt = 0;
                r.put("avgRtt", 0);
                r.put("pctTransmitted", retry);
                r.put("pctReceived", 0);
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            System.out.println(" Exception:" + ignore);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return r;
    }

    @Override
    protected void handleOnStart() {
  //      startSelectorThread();
    //    LocalBroadcastManager.getInstance(getContext()).registerReceiver(dataForwardReceiver, new IntentFilter("capacitor-udp-forward"));
    }

    @Override
    protected void handleOnStop() {
        Log.i("lifecycle", "stop");
       // stopSelectorThread();
    }

    @Override
    protected void handleOnRestart() {
        Log.i("lifecycle", "restart");
       //   startSelectorThread();
    }
}
