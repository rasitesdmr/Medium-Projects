/// <reference types="node" />
import { Layout, Options } from '../types/index.js';
export { Format, Layout, Options } from '../types/index.js';

/**
 * Execute the Graphviz dot command and make a Stream of the results.
 */
declare function toStream<T extends Layout>(dot: string, options?: Options<T>): Promise<NodeJS.ReadableStream>;

/**
 * Execute the Graphviz dot command and output the results to a file.
 */
declare function toFile<T extends Layout>(dot: string, path: string, options?: Options<T>): Promise<void>;

export { toFile, toStream };
