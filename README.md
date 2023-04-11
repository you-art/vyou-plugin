# vyou-plugin

Vyou Specific Capacitor features

## Install

```bash
npm install vyou-plugin
npx cap sync
```

## API

<docgen-index>

* [`ping(...)`](#ping)
* [`getConnectionStatus()`](#getconnectionstatus)
* [`checkPermissions()`](#checkpermissions)
* [`requestDetailedNetworkStatus()`](#requestdetailednetworkstatus)
* [`addListener('networkStatusChange', ...)`](#addlistenernetworkstatuschange)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### ping(...)

```typescript
ping(options: { ipAddress: string; timeOut?: number; retries?: number; }) => Promise<{ pings: number; pongs: number; avgRtt: number | undefined; }>
```

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code>{ ipAddress: string; timeOut?: number; retries?: number; }</code> |

**Returns:** <code>Promise&lt;{ pings: number; pongs: number; avgRtt: number; }&gt;</code>

--------------------


### getConnectionStatus()

```typescript
getConnectionStatus() => Promise<ConnectionStatus>
```

Query the current status of the network connection.

**Returns:** <code>Promise&lt;<a href="#connectionstatus">ConnectionStatus</a>&gt;</code>

**Since:** 1.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### requestDetailedNetworkStatus()

```typescript
requestDetailedNetworkStatus() => Promise<ConnectionStatus>
```

**Returns:** <code>Promise&lt;<a href="#connectionstatus">ConnectionStatus</a>&gt;</code>

--------------------


### addListener('networkStatusChange', ...)

```typescript
addListener(eventName: 'networkStatusChange', listenerFunc: ConnectionStatusChangeListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Listen for changes in the network connection.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'networkStatusChange'</code>                                                        |
| **`listenerFunc`** | <code><a href="#connectionstatuschangelistener">ConnectionStatusChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 1.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners (including the network status changes) for this plugin.

**Since:** 1.0.0

--------------------


### Interfaces


#### ConnectionStatus

Represents the state and type of the network connection.

| Prop                 | Type                                                      | Description                                                                                                                   | Since |
| -------------------- | --------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`connected`**      | <code>boolean</code>                                      | Whether there is an active connection or not.                                                                                 | 1.0.0 |
| **`connectionType`** | <code><a href="#connectiontype">ConnectionType</a></code> | The type of network connection currently in use. If there is no active network connection, `connectionType` will be `'none'`. | 1.0.0 |
| **`ssid`**           | <code>string</code>                                       |                                                                                                                               |       |
| **`bssid`**          | <code>string</code>                                       |                                                                                                                               |       |
| **`privateIp`**      | <code>number</code>                                       |                                                                                                                               |       |


#### PermissionStatus

| Prop                  | Type                                                        | Description                                                                                                                                                                                 | Since |
| --------------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`detailedNetwork`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for location alias. On Android it requests/checks both ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION permissions. On iOS and web it requests/checks location permission. | 1.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### ConnectionType

The type of network connection that a device might have.

<code>'wifi' | 'cellular' | 'ethernet' | 'none' | 'unknown'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### ConnectionStatusChangeListener

Callback to receive the status change notifications.

<code>(status: <a href="#connectionstatus">ConnectionStatus</a>): void</code>

</docgen-api>
