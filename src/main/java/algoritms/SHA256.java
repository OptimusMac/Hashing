package algoritms;

public class SHA256 extends Algoritm {

  private static final int[] K = {
      0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
      0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
      0xe49b69c1, 0x40c72493, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8,
      0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc,
      0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70,
      0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x275c1e7a,
      0x34e90c6c, 0x6f67a0bc, 0x6d6e25b2, 0x8b1c61c7, 0x984f4a39, 0xa3c8bc8b, 0x5b7fdb6e, 0x74119e38,
      0x770a1b11, 0x7b535fbd, 0x7e8f07a6, 0x83b06b3d, 0xa7a63173, 0xd1233f10, 0x030d8c76, 0x2d3c4429
  };


  public static String hash(String message) {
    byte[] messageBytes = message.getBytes();

    byte[] paddedMessage = padMessage(messageBytes);

    int[] H = {
        0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
        0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
    };

    for (int i = 0; i < paddedMessage.length / 64; i++) {
      int[] W = new int[64];
      for (int t = 0; t < 16; t++) {
        W[t] = ((paddedMessage[i * 64 + t * 4] & 0xFF) << 24)
            | ((paddedMessage[i * 64 + t * 4 + 1] & 0xFF) << 16)
            | ((paddedMessage[i * 64 + t * 4 + 2] & 0xFF) << 8)
            | (paddedMessage[i * 64 + t * 4 + 3] & 0xFF);
      }

      for (int t = 16; t < 64; t++) {
        W[t] = sigma1(W[t - 2]) + W[t - 7] + sigma0(W[t - 15]) + W[t - 16];
      }

      int a = H[0], b = H[1], c = H[2], d = H[3], e = H[4], f = H[5], g = H[6], h = H[7];
      for (int t = 0; t < 64; t++) {
        int T1 = h + Sigma1(e) + Ch(e, f, g) + K[t] + W[t];
        int T2 = Sigma0(a) + Maj(a, b, c);
        h = g;
        g = f;
        f = e;
        e = d + T1;
        d = c;
        c = b;
        b = a;
        a = T1 + T2;
      }

      H[0] += a;
      H[1] += b;
      H[2] += c;
      H[3] += d;
      H[4] += e;
      H[5] += f;
      H[6] += g;
      H[7] += h;
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      result.append(String.format("%08x", H[i]));
    }
    return result.toString();
  }

  private static int Ch(int x, int y, int z) {
    return (x & y) ^ (~x & z);
  }

  private static int Maj(int x, int y, int z) {
    return (x & y) ^ (x & z) ^ (y & z);
  }

  private static int Sigma0(int x) {
    return Integer.rotateRight(x, 7) ^ Integer.rotateRight(x, 18) ^ (x >>> 3);
  }

  private static int Sigma1(int x) {
    return Integer.rotateRight(x, 17) ^ Integer.rotateRight(x, 19) ^ (x >>> 10);
  }

  private static int sigma0(int x) {
    return Integer.rotateRight(x, 7) ^ Integer.rotateRight(x, 18) ^ (x >>> 3);
  }

  private static int sigma1(int x) {
    return Integer.rotateRight(x, 17) ^ Integer.rotateRight(x, 19) ^ (x >>> 10);
  }

  private static byte[] padMessage(byte[] message) {
    int originalLength = message.length * 8;
    int paddingLength = 448 - (originalLength % 512);
    if (paddingLength <= 0) {
      paddingLength += 512;
    }

    byte[] padding = new byte[(paddingLength + 64) / 8];
    padding[0] = (byte) 0x80;
    long length = originalLength;
    for (int i = 0; i < 8; i++) {
      padding[padding.length - 8 + i] = (byte) (length >>> (56 - i * 8));
    }

    byte[] paddedMessage = new byte[message.length + padding.length];
    System.arraycopy(message, 0, paddedMessage, 0, message.length);
    System.arraycopy(padding, 0, paddedMessage, message.length, padding.length);
    return paddedMessage;
  }

}
