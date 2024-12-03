package algoritms;

public abstract class Algoritm {

  public static String salt() {
    long seed = System.nanoTime();
    seed ^= (seed << 21);
    seed ^= (seed >>> 35);
    seed ^= (seed << 4);
    long random = (seed * 2685821657736338717L) >>> 32;

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 10; i++) {
      char c = (char) ('a' + (random & 0x1F) % 26);
      builder.append(c);

      random = (random * 2685821657736338717L) >>> 32;
    }

    return builder.toString();
  }


}
