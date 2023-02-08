package art.vyou.plugin;

import android.util.Log;

public class vyouPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
