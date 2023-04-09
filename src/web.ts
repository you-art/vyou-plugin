import {WebPlugin} from '@capacitor/core';

import type {
  vyouPluginPlugin,
  ConnectionStatus,
  ConnectionType,
  PermissionStatus
} from './definitions';

declare global {
  interface Navigator {
    connection: any;
    mozConnection: any;
    webkitConnection: any;
  }
}

function translatedConnection(): ConnectionType {
  const connection =
    window.navigator.connection ||
    window.navigator.mozConnection ||
    window.navigator.webkitConnection;
  let result: ConnectionType = 'unknown';
  const type = connection ? connection.type || connection.effectiveType : null;
  if (type && typeof type === 'string') {
    switch (type) {
      // possible type values
      case 'bluetooth':
      case 'cellular':
        result = 'cellular';
        break;
      case 'none':
        result = 'none';
        break;
      case 'ethernet':
      case 'wifi':
      case 'wimax':
        result = 'wifi';
        break;
      case 'other':
      case 'unknown':
        result = 'unknown';
        break;
      // possible effectiveType values
      case 'slow-2g':
      case '2g':
      case '3g':
        result = 'cellular';
        break;
      case '4g':
        result = 'wifi';
        break;
      default:
        break;
    }
  }
  return result;
}

export class vyouPluginWeb extends WebPlugin implements vyouPluginPlugin {

  constructor() {
    super();
    if (typeof window !== 'undefined') {
      window.addEventListener('online', this.handleOnline);
      window.addEventListener('offline', this.handleOffline);
    }
  }

  async checkPermissions(): Promise<PermissionStatus> {
    return {detailedNetwork: 'denied'};
  }
  requestDetailedNetworkStatus(): Promise<ConnectionStatus> {
    throw this.unavailable('Permissions API not available in this browser');
  }


  ping = async (options: {ipAddress: string, timeOut: number | undefined, retries: number | undefined}): Promise<{pings: number, pongs: number, avgRtt: number | undefined}> => {
    options.timeOut = options.timeOut ?? 1000
    options.retries = options.retries ?? 1
    return {
      pings: options.retries,
      pongs: Math.floor(Math.random() * options.retries),
      avgRtt: Math.random() * 100
    }
  }

  getConnectionStatus = async (): Promise<ConnectionStatus> => {
    if (!window.navigator) {
      throw this.unavailable(
        'Browser does not support the Network Information API',
      );
    }

    const connected = window.navigator.onLine;
    const connectionType = translatedConnection();

    const status: ConnectionStatus = {
      connected,
      connectionType: connected ? connectionType : 'none',
      ssid: undefined,
      bssid: undefined,
      networkId: undefined
    };

    return status;
  }

  private handleOnline = () => {
    const connectionType = translatedConnection();

    const status: ConnectionStatus = {
      connected: true,
      connectionType: connectionType,
      ssid: undefined,
      bssid: undefined,
      networkId: undefined
    };

    this.notifyListeners('networkStatusChange', status);
  };

  private handleOffline = () => {
    const status: ConnectionStatus = {
      connected: false,
      connectionType: 'none',
      ssid: undefined,
      bssid: undefined,
      networkId: undefined
    };

    this.notifyListeners('networkStatusChange', status);
  };
}

