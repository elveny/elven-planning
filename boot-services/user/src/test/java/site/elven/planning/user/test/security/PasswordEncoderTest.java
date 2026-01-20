package site.elven.planning.user.test.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PasswordEncoderTest {

    @Test
    public void testBCryptPasswordEncoder() {
        // Create an encoder with strength 16
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode("myPassword");
        System.out.println(result);
        assertTrue(encoder.matches("myPassword", result));
    }

    @Test
    public void testArgon2PasswordEncoder() {
        // Create an encoder with all the defaults
        Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        String result = encoder.encode("myPassword");
        System.out.println(result);
        assertTrue(encoder.matches("myPassword", result));
    }

    @Test
    public void testPbkdf2PasswordEncoder() {
        // Create an encoder with all the defaults
        Pbkdf2PasswordEncoder encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        String result = encoder.encode("myPassword");
        System.out.println(result);
        assertTrue(encoder.matches("myPassword", result));
    }

    @Test
    public void testSCryptPasswordEncoder() {
        // Create an encoder with all the defaults
        SCryptPasswordEncoder encoder = SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();
        String result = encoder.encode("myPassword");
        System.out.println(result);
        assertTrue(encoder.matches("myPassword", result));
    }
}
