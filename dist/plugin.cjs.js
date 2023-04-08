'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const vyouPlugin = core.registerPlugin('vyouPlugin', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.vyouPluginWeb()),
});

function translatedConnection() {
    const connection = window.navigator.connection ||
        window.navigator.mozConnection ||
        window.navigator.webkitConnection;
    let result = 'unknown';
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
        }
    }
    return result;
}
class vyouPluginWeb extends core.WebPlugin {
    constructor() {
        super();
        this.ping = async (options) => {
            var _a, _b;
            options.timeOut = (_a = options.timeOut) !== null && _a !== void 0 ? _a : 1000;
            options.retries = (_b = options.retries) !== null && _b !== void 0 ? _b : 1;
            return {
                pings: options.retries,
                pongs: Math.floor(Math.random() * options.retries),
                avgRtt: Math.random() * 100
            };
        };
        this.getConnectionStatus = async () => {
            if (!window.navigator) {
                throw this.unavailable('Browser does not support the Network Information API');
            }
            const connected = window.navigator.onLine;
            const connectionType = translatedConnection();
            const status = {
                connected,
                connectionType: connected ? connectionType : 'none',
                ssid: undefined,
                bssid: undefined
            };
            return status;
        };
        this.handleOnline = () => {
            const connectionType = translatedConnection();
            const status = {
                connected: true,
                connectionType: connectionType,
                ssid: undefined,
                bssid: undefined
            };
            this.notifyListeners('networkStatusChange', status);
        };
        this.handleOffline = () => {
            const status = {
                connected: false,
                connectionType: 'none',
                ssid: undefined,
                bssid: undefined
            };
            this.notifyListeners('networkStatusChange', status);
        };
        if (typeof window !== 'undefined') {
            window.addEventListener('online', this.handleOnline);
            window.addEventListener('offline', this.handleOffline);
        }
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    vyouPluginWeb: vyouPluginWeb
});

exports.vyouPlugin = vyouPlugin;
//# sourceMappingURL=plugin.cjs.js.map
