package promises;

/**
 * Lazy with no child promises. Computed result.
 *
 * @param <T> result type
 */
final class LazyResult<T> extends Lazy<T> {
  private final T result;

  public LazyResult(T result) {
    this.result = result;
  }


  @Override
  public T force() {
    return result;
  }
}
