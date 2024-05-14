package utilities.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hash {
	private Hash() {}
	
	public static String sha256(String message) {
		String hash = "";
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
			hash = bytesToHex(encodedhash);
		}
		catch(Exception e) {
			System.out.println(e);
			hash = "-";
		}
		
		return hash;
		
	}
	
	public static String bytesToHex(byte[] hash) {
	    
		StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
}
