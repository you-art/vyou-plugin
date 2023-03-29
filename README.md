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

</docgen-api>
