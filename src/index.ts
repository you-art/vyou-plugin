import { registerPlugin } from '@capacitor/core';

import type { vyouPluginPlugin } from './definitions';

const vyouPlugin = registerPlugin<vyouPluginPlugin>('vyouPlugin', {
  web: () => import('./web').then(m => new m.vyouPluginWeb()),
});

export * from './definitions';
export { vyouPlugin };
