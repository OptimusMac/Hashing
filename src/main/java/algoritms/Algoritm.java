package algoritms;

public abstract class Algoritm {

  public static String salt() {
    long seed = Long.MAX_VALUE;
    seed ^= (seed << 21);
    seed ^= (seed >>> 35);
    seed ^= (seed << 4);
    long random = (seed * 2685821657736338717L) >>> 32;

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 10; i++) {
      char c = (char) ((int) (random >> i + builder.length()) % 24);
      builder.append(c);
    }

    return builder.toString();
  }

}
