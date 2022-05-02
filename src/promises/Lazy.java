package promises;

import java.util.function.Supplier;

/**
 * Abstract class for chained promises. I should make it impossible to extend outside of the package,
 * but this is just for demonstration anyway.
 *
 * @param <T> result type
 */
public abstract class Lazy<T> {
  public abstract T force();

  /**
   * Construct a Lazy value with a finished result
   * @param result result of the computation
   * @param <T> result type
   * @return lazy wrapper around the result
   */
  public static <T> Lazy<T> of(T result) {
    return new LazyResult<>(result);
  }

  /**
   * Construct a Lazy value from a computation which results in another lazy value
   * @param supplier
   * @param <T>
   * @return
   */
  public static <T> Lazy<T> of(Supplier<Lazy<T>> supplier) {
    return new LazyParent<T>(supplier);
  }
}
