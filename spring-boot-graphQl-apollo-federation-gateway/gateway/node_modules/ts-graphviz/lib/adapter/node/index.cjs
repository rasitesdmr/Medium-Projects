'use strict';

var node_stream = require('node:stream');
var node_child_process = require('node:child_process');
var index_js = require('../utils/index.cjs');
var node_util = require('node:util');
var node_fs = require('node:fs');

const pipeline = node_util.promisify(node_stream.pipeline);

async function toStream(dot, options) {
  const [command, args] = index_js.createCommandAndArgs(options ?? {});
  return new Promise(async function toStreamInternal(resolve, reject) {
    const p = node_child_process.spawn(command, args, { stdio: 'pipe' });
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
    const dist = p.stdout.pipe(new node_stream.PassThrough());
    p.on('close', async (code, signal) => {
      if (code === 0) {
        resolve(dist);
      } else {
        const message = Buffer.concat(stderrChunks).toString();
        reject(new Error(`Command "${command}" failed.\nCODE: ${code}\nSIGNAL: ${signal}\nMESSAGE: ${message}`));
      }
    });
    await pipeline(node_stream.Readable.from([dot]), p.stdin);
  });
}

async function toFile(dot, path, options) {
  const stream = await toStream(dot, options);
  await pipeline(stream, node_fs.createWriteStream(path));
}

exports.toFile = toFile;
exports.toStream = toStream;
