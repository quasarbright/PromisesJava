import promises.Lazy;

public class Main {
  public static void main(String[] args) {
    Lazy<String> lazy = Lazy.of(() -> Lazy.of(() -> Lazy.of(() -> {
      System.out.println("hello");
      return Lazy.of("world");
    })));
    Lazy<String> lazier = Lazy.of(() -> Lazy.of(() -> lazy));
    System.out.println(lazier.force());
    System.out.println(lazy.force());// the computation only runs once

    Lazy<Integer> lazyInt = Lazy.of(() -> {
      Lazy<Integer> dontCare = Lazy.of(() -> {
        System.out.println("if a tree falls in the woods and nobody hears it...");
        return Lazy.of(1 + 1);
      });
      Lazy<Integer> y = Lazy.of(() -> Lazy.of(5 + 5));
      return Lazy.of(() -> Lazy.of(100 + y.force()));
    });
    System.out.println(lazyInt.force());
  }
}
