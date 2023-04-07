import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(vyouPluginPlugin)
public class vyouPluginPlugin: CAPPlugin {
    private let implementation = vyouPlugin()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    private var networkImpl: Network?

    override public func load() {
        CAPLog.print("Loading network plugin")
        do {
            networkImpl = try Network()
            networkImpl?.statusObserver = { [weak self] status in
                CAPLog.print(status.logMessage)
                self?.notifyListeners("networkStatusChange", data: [
                    "connected": status.isConnected,
                    "connectionType": status.jsStringValue
                ])
            }
        } catch let error {
            CAPLog.print("Unable to start network monitor: \(error)")
        }
    }

    @objc func getConnectionStatus(_ call: CAPPluginCall) {
        let status = networkImpl?.currentStatus() ?? Network.Connection.unavailable
        call.resolve(["connected": status.isConnected, "connectionType": status.jsStringValue])
    }
}

extension Network.Connection {
    internal var jsStringValue: String {
        switch self {
        case .cellular:
            return "cellular"
        case .wifi:
            return "wifi"
        case .unavailable:
            return "none"
        }
    }
    internal var isConnected: Bool {
        switch self {
        case .cellular, .wifi:
            return true
        case .unavailable:
            return false
        }
    }
    internal var logMessage: String {
        switch self {
        case .cellular:
            return "Reachable via Cellular"
        case .wifi:
            return "Reachable via WiFi"
        case .unavailable:
            return "Not reachable"
        }
    }
}
