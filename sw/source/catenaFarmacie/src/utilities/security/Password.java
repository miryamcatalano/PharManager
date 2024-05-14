package utilities.security;

import java.security.SecureRandom;

public class Password {
	
	private String _password;
	private int _salt;
	
	public Password(String strPassword, int nSalt) {
		this._password = strPassword;
		this._salt = nSalt;
	}
	
    public static int CreateRandomSalt() {
    	
    	byte[] _saltBytes = new byte[4];
    	SecureRandom random = new SecureRandom();
        random.nextBytes(_saltBytes);

         return ((((int)_saltBytes[0]) << 24) + (((int)_saltBytes[1]) << 16) + 
           (((int)_saltBytes[2]) << 8) + ((int)_saltBytes[3]));
    }

    public String ComputeSaltedHash() {
    	// Creo l'array di byte della stringa password
    	try {
    		
	        String encoder = new String(_password);	      
	        
	        byte[] _secretBytes = encoder.getBytes();
	        
	        
	        // Creo nuovo salt
	        byte[] _saltBytes = new byte[4];
	        _saltBytes[0] = (byte)(_salt >> 24);
	        _saltBytes[1] = (byte)(_salt >> 16);
	        _saltBytes[2] = (byte)(_salt >> 8);
	        _saltBytes[3] = (byte)(_salt);
	
	        
	        // appende i due array
	        byte[] toHash = new byte[_secretBytes.length + _saltBytes.length];
	        System.arraycopy(_secretBytes, 0, toHash, 0, _secretBytes.length);
	        System.arraycopy(_saltBytes, 0, toHash, _secretBytes.length, _saltBytes.length);
	
	        return Hash.sha256(Hash.bytesToHex(toHash));
    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		return "-";
    	}
    }
}
