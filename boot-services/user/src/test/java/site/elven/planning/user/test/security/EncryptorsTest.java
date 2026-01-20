package site.elven.planning.user.test.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import java.nio.charset.StandardCharsets;

public class EncryptorsTest {
    @Test
    public void testBytesEncryptor(){
        String salt = KeyGenerators.string().generateKey();
        BytesEncryptor bytesEncryptor = Encryptors.stronger("password", salt);
        byte[] encrypt = bytesEncryptor.encrypt("123456".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(encrypt, StandardCharsets.UTF_8));
        byte[] decrypt = bytesEncryptor.decrypt(encrypt);
        System.out.println(new String(decrypt, StandardCharsets.UTF_8));
    }

    @Test
    public void testTextEncryptor(){
        String salt = KeyGenerators.string().generateKey();
        TextEncryptor textEncryptor = Encryptors.text("password", salt);
        String encrypt = textEncryptor.encrypt("123456");
        System.out.println(encrypt);
        String decrypt = textEncryptor.decrypt(encrypt);
        System.out.println(decrypt);

    }

    @Test
    public void testBytesKeyGenerator(){
        BytesKeyGenerator generator = KeyGenerators.secureRandom();
        byte[] key = generator.generateKey();
        System.out.println(new String(key, StandardCharsets.UTF_8));

        BytesKeyGenerator bytesKeyGenerator = KeyGenerators.secureRandom(16);
        BytesKeyGenerator shared = KeyGenerators.shared(16);

        StringKeyGenerator stringKeyGenerator = KeyGenerators.string();
        String key1 = stringKeyGenerator.generateKey();
        System.out.println(key1);


    }
}
