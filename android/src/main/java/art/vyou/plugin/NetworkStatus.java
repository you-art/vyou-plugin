package art.vyou.plugin;

public class NetworkStatus {

    public enum ConnectionType {
        WIFI("wifi"),
        CELLULAR("cellular"),
        NONE("none"),
        UNKNOWN("unknown");

        private String connectionType;

        ConnectionType(String connectionType) {
            this.connectionType = connectionType;
        }

        public String getConnectionType() {
            return this.connectionType;
        }
    }

    public boolean connected = false;
    public ConnectionType connectionType = ConnectionType.NONE;
    public String ssid;
    public String bssid;
    public Integer networkId;
}
