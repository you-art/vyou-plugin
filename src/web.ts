import {WebPlugin} from '@capacitor/core';

import type {vyouPluginPlugin} from './definitions';

export class vyouPluginWeb extends WebPlugin implements vyouPluginPlugin {

  async ping(options: {ipAddress: string, timeOut: number | undefined, retries: number | undefined}): Promise<{pings: number, pongs: number, avgRtt: number | undefined}> {
    options.timeOut = options.timeOut ?? 1000
    options.retries = options.retries ?? 1
    return {
      pings: options.retries,
      pongs: Math.floor(Math.random() * options.retries),
      avgRtt: Math.random() * 100
    }
  }
}
