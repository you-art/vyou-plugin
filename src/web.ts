import { WebPlugin } from '@capacitor/core';

import type { vyouPluginPlugin } from './definitions';

export class vyouPluginWeb extends WebPlugin implements vyouPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
