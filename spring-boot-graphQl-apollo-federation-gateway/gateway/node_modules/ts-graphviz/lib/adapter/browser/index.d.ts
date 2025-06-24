/**
 * @module ts-graphviz/adapter
 * @beta
 */
type Options = any;
/**
 * Execute the Graphviz dot command and make a Stream of the results.
 */
declare function toStream(dot: string, options?: Options): never;
/**
 * Execute the Graphviz dot command and output the results to a file.
 */
declare function toFile(dot: string, path: string, options?: Options): never;

export { Options, toFile, toStream };
