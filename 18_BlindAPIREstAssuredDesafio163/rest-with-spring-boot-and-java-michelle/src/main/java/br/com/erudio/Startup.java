package br.com.erudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
        
        
        /*
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder());
        
        String result = passwordEncoder.encode("admin234");
        System.out.println("My hash " + result);
       */
        
        
       
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        
//        Pbkdf2PasswordEncoder pbkdf2Encoder =
//        		new Pbkdf2PasswordEncoder(
//    				"", 8, 185000,
//    				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
//        
//        encoders.put("pbkdf2", pbkdf2Encoder);
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
//        
//        String result1 = passwordEncoder.encode("admin123");
//        String result2 = passwordEncoder.encode("admin234");
//        System.out.println("My hash result1 " + result1);
//        System.out.println("My hash result2 " + result2);
//        
//        
//    //My hash result1 {pbkdf2}7dd26d0b02713e8cff46ac3d023a69b2ebd1b7f3ed43a662f1363228ca9da70ade93bbb2b84aa0d0
//	  //My hash result2 {pbkdf2}ef9f5f40a9915ad9776dd78fc3ca0bbf6a0ad782b1a729a266714c0d3da90d21091b3eff34d38052
//         
        
    }
}
