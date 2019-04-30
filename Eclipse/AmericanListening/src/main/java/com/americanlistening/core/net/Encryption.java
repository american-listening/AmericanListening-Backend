package com.americanlistening.core.net;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Handles encryption
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Encryption {

	private String encryptionType;
	private KeyPair keys;
	
	Encryption(String encryptionType) {
		this.encryptionType = encryptionType;
	}
	
	public void init() throws NoSuchAlgorithmException {
		keys = KeyPairGenerator.getInstance(encryptionType).generateKeyPair();
	}
	
	public String encrypt(String s) {
		if (keys == null)
			throw new IllegalStateException("Class not initialized!");
		return null;
	}
	
	public String decrypt(String s) {
		if (keys == null)
			throw new IllegalStateException("Class not initialized!");
		return null;
	}
	
	public String getEncryptionType() {
		return encryptionType;
	}
	
	@Override
	public String toString() {
		return "Encryption[encryptionType=" + encryptionType + "]";
	}
}
