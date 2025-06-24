function _pipe(...args) {
  const [o1, ...operations] = args;
  return (...t) => operations.reduce((acc, f) => f(acc), o1(...t));
}
function pipe(o1, ...operations) {
  return _pipe(o1, ...operations);
}
const defer =
  (fn) =>
  (...args) =>
  (src) =>
    fn(src, ...args);
const toIterable = (f) => ({
  [Symbol.iterator]: f,
});
const map = defer((src, selector) =>
  Array.from(
    toIterable(function* () {
      for (const v of src) {
        yield selector(v);
      }
    }),
  ),
);
const filter = defer((src, pred) =>
  Array.from(
    toIterable(function* () {
      for (const x of src) {
        if (pred(x)) {
          yield x;
        }
      }
    }),
  ),
);

export { filter, map, pipe };
