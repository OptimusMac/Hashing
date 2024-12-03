package hash;

import algoritms.BCRYPT;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;

public enum HashType {


  SHA256,
  BCRYPT;


  @SneakyThrows
  public String hashWithSalt(String value, String salt) {
    switch (ordinal()) {
      case 0 -> {
        return algoritms.SHA256.hash(value + salt);
      }
      case 1 -> {
        byte[] bytes =
            algoritms.BCRYPT.bcryptWithBlowfish(value.getBytes(), salt.getBytes());
        return algoritms.BCRYPT.encodeToBase64(bytes);
      }
    }
    return null;
  }

  public String hashWithoutSalt(String value){
    return hashWithSalt(value, "");
  }
}
