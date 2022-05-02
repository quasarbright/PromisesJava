package promises;

import java.util.function.Supplier;

/**
 * Composed promise. Contains chain of child promises.
 * Force uses constant space and collapses all child promises as it goes.
 *
 * @param <T> result type
 */
final class LazyParent<T> extends Lazy<T> {
  private Promise<Lazy<T>> child;

  public LazyParent(Promise<Lazy<T>> child) {
    this.child = child;
  }

  public LazyParent(Supplier<Lazy<T>> child) {
    this(new Promise<>(child));
  }

  @Override
  public T force() {
    Lazy<T> childResult = child.forcePromise();
    if (childResult instanceof LazyParent) {
      LazyParent<T> inner = (LazyParent<T>) childResult;
      Lazy<T> innerResult = inner.child.forcePromise();
      this.child = new Promise<>(innerResult);
      if (innerResult instanceof LazyParent) {
        // swap the pointers
        inner.child = new Promise<>(this);
        // was p1 -> p2 -> p3
        // now it's p2 -> p1 -> p3
        // repeat to collapse p1 -> p3
        // will lead to all sub-promises pointing to p1
        // except for the last one (which produces a final value)
        return this.force();
      } else {
        // grandchild was a result
        return innerResult.force();
      }
    } else {
      // child was a result
      return childResult.force();
    }
  }
}
