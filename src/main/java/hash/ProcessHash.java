package hash;

import algoritms.Algoritm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProcessHash {

  private HashType hashType;


  public String hash(String value, boolean withSalt) {
    if (!withSalt) {
      return hashType.hashWithoutSalt(value);
    }
    String salt = Algoritm.salt();
    return hashType.hashWithSalt(value, salt);
  }

  public String hash(String value, String salt) {
    return hashType.hashWithSalt(value, salt);
  }


}
