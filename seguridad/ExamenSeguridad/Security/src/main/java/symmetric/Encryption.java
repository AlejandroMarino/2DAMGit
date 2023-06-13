package symmetric;

public interface Encryption {

    String encrypt(String textToEncrypt, String secret);

    String decrypt(String textToDecrypt, String secret);

}
