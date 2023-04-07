import { registerPlugin } from '@capacitor/core';
const vyouPlugin = registerPlugin('vyouPlugin', {
    web: () => import('./web').then(m => new m.vyouPluginWeb()),
});
export * from './definitions';
export { vyouPlugin };
//# sourceMappingURL=index.js.map