package com.americanlistening.core.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Writer for writing to an output securely.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class SecureWriter extends OutputStream {

	private PrintWriter out;
	private Encryption en;
	
	/**
	 * Creates a new secure writer.
	 * 
	 * @param os The output stream.
	 * @param en The encryption object.
	 */
	public SecureWriter(OutputStream os, Encryption en) {
		this.out = new PrintWriter(os);
		this.en = en;
	}
	
	/**
	 * Returns the encryption object for the writer.
	 * 
	 * @return The encryption object.
	 */
	public Encryption getEncryption() {
		return en;
	}
	
	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
