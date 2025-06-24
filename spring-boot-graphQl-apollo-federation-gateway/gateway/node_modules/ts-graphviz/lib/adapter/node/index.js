import { pipeline as pipeline$1, PassThrough, Readable } from 'node:stream';
import { spawn } from 'node:child_process';
import { createCommandAndArgs } from '../utils/index.js';
import { promisify } from 'node:util';
import { createWriteStream } from 'node:fs';

const pipeline = promisify(pipeline$1);

async function toStream(dot, options) {
  const [command, args] = createCommandAndArgs(options ?? {});
  return new Promise(async function toStreamInternal(resolve, reject) {
    const p = spawn(command, args, { stdio: 'pipe' });
    p.on('error', (e) => {
      reject(
        new Error(`Command "${command}" failed.\nMESSAGE:${e.message}`, {
          cause: e,
        }),
      );
    });
    const stderrChunks = [];
    p.stdout.on('pause', () => p.stdout.resume());
    p.stderr.on('data', (chunk) => stderrChunks.push(chunk));
    p.stderr.on('pause', () => p.stderr.resume());
    const dist = p.stdout.pipe(new PassThrough());
    p.on('close', async (code, signal) => {
      if (code === 0) {
        resolve(dist);
      } else {
        const message = Buffer.concat(stderrChunks).toString();
        reject(new Error(`Command "${command}" failed.\nCODE: ${code}\nSIGNAL: ${signal}\nMESSAGE: ${message}`));
      }
    });
    await pipeline(Readable.from([dot]), p.stdin);
  });
}

async function toFile(dot, path, options) {
  const stream = await toStream(dot, options);
  await pipeline(stream, createWriteStream(path));
}

export { toFile, toStream };
