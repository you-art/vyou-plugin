export type CallbackID = string

export type ssdpCallback = (ssdpRecord : string) => void

export interface vyouPluginPlugin {
  ping(options: {ipAddress: string, timeOut?: number, retries?: number}): Promise<{pings : number,pongs : number, avgRtt: number | undefined}>;
}
