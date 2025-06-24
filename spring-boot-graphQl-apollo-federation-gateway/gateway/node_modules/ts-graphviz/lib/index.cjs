'use strict';

var index_js = require('./common/index.cjs');
var index_js$1 = require('./core/index.cjs');

Object.keys(index_js).forEach(function (k) {
  if (k !== 'default' && !exports.hasOwnProperty(k))
    Object.defineProperty(exports, k, {
      enumerable: true,
      get: function () {
        return index_js[k];
      },
    });
});
Object.keys(index_js$1).forEach(function (k) {
  if (k !== 'default' && !exports.hasOwnProperty(k))
    Object.defineProperty(exports, k, {
      enumerable: true,
      get: function () {
        return index_js$1[k];
      },
    });
});
