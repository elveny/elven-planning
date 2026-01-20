package site.elven.planning.user.test;

import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import java.util.Date;

public class Test1 {
    public static void main(String[] args) {
//        test0();
//        test1();
        test2();
    }

    private static void test2(){
        System.out.println(new Date(1732586656673L));
        System.out.println(new Date(1734918494550L));
        System.out.println(new Date(1735008160168L));
    }


    private static void test1(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("user")
                .build();
        System.out.println(user.getPassword());
    }

    private static void test0() {
        System.out.println(new StandardSessionIdGenerator().generateSessionId(null));
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = delegatingPasswordEncoder.encode("123456");
        System.out.println(encode);
        System.out.println(delegatingPasswordEncoder.matches("123456", encode));
        System.out.println(delegatingPasswordEncoder.matches("123456", "{bcrypt}$2a$10$D5WmfMmGEKgLd.e0eBw6Ie3aOjr/em.Hpyp6S8w3u2cCg64.ABwci"));
    }


}
