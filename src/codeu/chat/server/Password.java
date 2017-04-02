package codeu.chat.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Password {

    // Returns a hash of our password as a string.
    // Returns null if unsuccessful.
    public static String hash(String plaintext, byte[] salt){
        String output = null;
        try{
            // Make a new MessageDigest object, update it with our salt
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);

            // Convert our plaintext into an array of bytes that have been hashed with our salt
            byte[] bytes = md.digest(plaintext.getBytes());

            // Make a string out of our hashed password
            StringBuilder sb = new StringBuilder();
            for(int i=0 ; i<bytes.length ; i++){
                sb.append(Integer.toString((bytes[i]&0xff)+0x100,16).substring(1));
            }
            output = sb.toString();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return output;
    }

    // Gets a secure salt to use with our password hashing (NEEDS TO BE STORED ON FILE)
    // returns a byte array that is our salt
    // returns null if unsuccessful
    public static byte[] getSalt(){
        byte[] salt = null;
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[16];
            random.nextBytes(salt);
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return salt;
    }

    // Takes an input in plaintext, the hashed password, and the salt, and returns whether or not
    // the plaintext is the correct password.
    // Returns false if it failed for any reason (including a method failure)
    public static boolean checkPassword(String input, String hashedPassword, byte[] salt){
        String validateMe;
        validateMe = hash(input,salt);
        return hashedPassword.equals(validateMe);
    }
}