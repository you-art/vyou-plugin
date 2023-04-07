import { WebPlugin } from '@capacitor/core';
import type { vyouPluginPlugin, ConnectionStatus } from './definitions';
declare global {
    interface Navigator {
        connection: any;
        mozConnection: any;
        webkitConnection: any;
    }
}
export declare class vyouPluginWeb extends WebPlugin implements vyouPluginPlugin {
    constructor();
    ping(options: {
        ipAddress: string;
        timeOut: number | undefined;
        retries: number | undefined;
    }): Promise<{
        pings: number;
        pongs: number;
        avgRtt: number | undefined;
    }>;
    getConnectionStatus(): Promise<ConnectionStatus>;
    private handleOnline;
    private handleOffline;
}
