import { PermissionState, PluginListenerHandle } from "@capacitor/core";
export declare type CallbackID = string;
export declare type ssdpCallback = (ssdpRecord: string) => void;
export interface vyouPluginPlugin {
    ping(options: {
        ipAddress: string;
        timeOut?: number;
        retries?: number;
    }): Promise<{
        pings: number;
        pongs: number;
        avgRtt: number | undefined;
    }>;
    /**
     * Query the current status of the network connection.
     *
     * @since 1.0.0
     */
    getConnectionStatus(): Promise<ConnectionStatus>;
    checkPermissions(): Promise<PermissionStatus>;
    requestDetailedNetworkStatus(): Promise<ConnectionStatus>;
    /**
     * Listen for changes in the network connection.
     *
     * @since 1.0.0
     */
    addListener(eventName: 'networkStatusChange', listenerFunc: ConnectionStatusChangeListener): Promise<PluginListenerHandle> & PluginListenerHandle;
    /**
     * Remove all listeners (including the network status changes) for this plugin.
     *
     * @since 1.0.0
     */
    removeAllListeners(): Promise<void>;
}
/**
 * Represents the state and type of the network connection.
 *
 * @since 1.0.0
 */
export interface ConnectionStatus {
    /**
     * Whether there is an active connection or not.
     *
     * @since 1.0.0
     */
    connected: boolean;
    /**
     * The type of network connection currently in use.
     *
     * If there is no active network connection, `connectionType` will be `'none'`.
     *
     * @since 1.0.0
     */
    connectionType: ConnectionType;
    ssid: string | undefined;
    bssid: string | undefined;
    privateIp: string | undefined;
}
/**
 * Callback to receive the status change notifications.
 *
 * @since 1.0.0
 */
export declare type ConnectionStatusChangeListener = (status: ConnectionStatus) => void;
/**
 * The type of network connection that a device might have.
 *
 * @since 1.0.0
 */
export declare type ConnectionType = 'wifi' | 'cellular' | 'ethernet' | 'none' | 'unknown';
/**
 * @deprecated Use `ConnectionStatus`.
 * @since 1.0.0
 */
export declare type NetworkStatus = ConnectionStatus;
/**
 * @deprecated Use `ConnectionStatusChangeListener`.
 * @since 1.0.0
 */
export declare type NetworkStatusChangeCallback = ConnectionStatusChangeListener;
export interface PermissionStatus {
    /**
     * Permission state for location alias.
     *
     * On Android it requests/checks both ACCESS_COARSE_LOCATION and
     * ACCESS_FINE_LOCATION permissions.
     *
     * On iOS and web it requests/checks location permission.
     *
     * @since 1.0.0
     */
    detailedNetwork: PermissionState;
}
export interface VyouPluginPermissions {
    permissions: PermissionStatus;
}
