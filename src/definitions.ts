export interface vyouPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
