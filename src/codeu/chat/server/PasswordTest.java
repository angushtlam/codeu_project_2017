package codeu.chat.server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

/*
 * A password hashing demo written using only Java's built-in classes
 * a (perhaps better) solution might be to use 3rd-party things
 *
 * Based largely on:
 * http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 *
 *
 * Liam Hartery March 2017
 */

public class PasswordTest {
    public static void main(String[]args){

        Scanner kb = new Scanner(System.in);
        String pass = "guessThisPassword";
        try {

            // We'd need to store the username, salt, and hashed password to file
            // I think (?) storing the salt is fine, as it's just a way to prevent the compromising multiple users
            // who have the same password, since they'd normally produce the same hash.

            byte[] salt = getSalt();
            String hashedPassword = hashPassword(pass, salt);

            // Password prompt
            System.out.print("Password: ");
            String password = kb.nextLine();
            if(checkPassword(password,hashedPassword,salt)){
                System.out.println("Password Verified.");
            }else{
                System.out.println("Incorrect Password");
            }

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public static String hashPassword(String plaintext, byte[] salt) throws NoSuchAlgorithmException{
        String output = null;
        try{
            // Using SHA-512 makes things hard to brute force
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // using a random salt prevents two people who share a password having the same hashed password
            md.update(salt);
            byte[] bytes = md.digest(plaintext.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i = 0 ; i<bytes.length ; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            }
            output = sb.toString();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return output;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException{
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static boolean checkPassword(String input, String hashedPassword, byte[] salt)
            throws NoSuchAlgorithmException{

        String validateMe = ""; // lul validateMe is me_irl
        try{
            validateMe = hashPassword(input,salt);
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return validateMe.equals(hashedPassword);
    }
}
