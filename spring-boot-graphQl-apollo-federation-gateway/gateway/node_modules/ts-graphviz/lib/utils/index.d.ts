/**
 * @hidden
 */
type $keywords<T extends string> = {
  [key in T]: key;
};
/**
 * @hidden
 */
interface $keywordsValidation
  extends $keywords<`${string} ${string}` | `${string}\n${string}` | `${string}\t${string}`> {}

type F<A extends any[], O> = (...args: A) => O;
type IO<I, O> = F<[I], O>;
declare function pipe<I extends any[], O>(f0: F<I, O>): F<I, O>;
declare function pipe<I extends any[], T1, O>(f0: F<I, T1>, f1: IO<T1, O>): F<I, O>;
declare function pipe<I extends any[], T1, T2, O>(f0: F<I, T1>, f1: IO<T1, T2>, f2: IO<T2, O>): F<I, O>;
declare function pipe<I extends any[], T1, T2, T3, O>(
  f0: F<I, T1>,
  f1: IO<T1, T2>,
  f2: IO<T2, T3>,
  f3: IO<T3, O>,
): F<I, O>;
declare function pipe<I extends any[], T1, T2, T3, T4, O>(
  f0: F<I, T1>,
  f1: IO<T1, T2>,
  f2: IO<T2, T3>,
  f3: IO<T3, T4>,
  f4: IO<T4, O>,
): F<I, O>;
declare const map: <T, O>(selector: (item: T) => O) => (src: Iterable<T>) => O[];
declare const filter: <T>(pred: (item: T) => boolean) => (src: Iterable<T>) => T[];

export { $keywords, $keywordsValidation, F, IO, filter, map, pipe };
