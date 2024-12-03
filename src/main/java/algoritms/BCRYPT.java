package algoritms;

import java.util.Arrays;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

public class BCRYPT extends Algoritm{

  private static final int ROUNDS = 10;

  public static byte[] bcryptWithBlowfish(byte[] password, byte[] salt) throws CryptoException {
    byte[] combined = new byte[password.length + salt.length];
    System.arraycopy(password, 0, combined, 0, password.length);
    System.arraycopy(salt, 0, combined, password.length, salt.length);

    BlowfishEngine blowfish = new BlowfishEngine();
    CBCBlockCipher cipher = new CBCBlockCipher(blowfish);
    PaddedBufferedBlockCipher paddedCipher = new PaddedBufferedBlockCipher(cipher);

    byte[] key = Arrays.copyOf(combined, 16);
    KeyParameter keyParam = new KeyParameter(key);

    paddedCipher.init(true, keyParam);

    byte[] result = new byte[combined.length];
    int processedLength = paddedCipher.processBytes(combined, 0, combined.length, result, 0);

    for (int i = 0; i < ROUNDS; i++) {
      byte[] tempResult = new byte[processedLength];
      paddedCipher.processBytes(result, 0, processedLength, tempResult, 0);
      result = tempResult;
    }

    return result;
  }

  public static String encodeToBase64(byte[] data) {
    return Base64.toBase64String(data);
  }



}
