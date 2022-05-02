package promises;

import java.util.function.Supplier;

/**
 * A delayed computation that only runs once. A memoized thunk.
 *
 * @param <T> result type
 */
public class Promise<T> {
  // null when not forced
  private T result;
  private boolean forced;
  // the delayed computation
  private final Supplier<T> supplier;

  public Promise(Supplier<T> supplier) {
    this.supplier = supplier;
    result = null;
    forced = false;
  }

  public Promise(T value) {
    this(() -> value);
  }

  /**
   * Return the result if known, use the supplier and remember the result otherwise.
   *
   * @return the result
   */
  public T forcePromise() {
    if(!forced) {
      result = supplier.get();
      forced = true;
    }
    return result;
  }
}
