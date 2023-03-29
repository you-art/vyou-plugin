export interface vyouPluginPlugin {
  ping(options: {ipAddress: string, timeOut?: number, retries?: number}): Promise<{pings : number,pongs : number, avgRtt: number | undefined}>;
}
