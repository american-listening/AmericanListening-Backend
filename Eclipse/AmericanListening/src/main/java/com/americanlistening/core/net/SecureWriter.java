package com.americanlistening.core.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * A secure writer handles secure writing to an output stream.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class SecureWriter extends Writer {

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
	public void write(char[] arg0, int arg1, int arg2) throws IOException {
		out.write(arg0, arg1, arg2);
	}

	@Override
	public void write(int arg0) throws IOException {
		out.write(arg0);
	}

	/**
	 * Prints an object to the stream.
	 * 
	 * @param obj The object to print.
	 * @throws IOException When an I/O error occurs.
	 */
	public void print(Object obj) throws IOException {
		final String rout = obj == null ? "null" : obj.toString();
		final String eout = en.encrypt(rout);
		write(eout, 0, eout.length());
	}

	/**
	 * Prints an object to the stream, followed by a return character. This is
	 * equivalent to calling <code>print()</code> and then <code>println()</code>.
	 * 
	 * @param obj The object to print.
	 * @throws IOException When an I/O error occurs.
	 */
	public void println(Object obj) throws IOException {
		print(obj);
		println();
	}

	/**
	 * Prints a return character to the stream.
	 * 
	 * @throws IOException When an I/O error occurs.
	 */
	public void println() throws IOException {
		print("\n");
	}

	@Override
	public void flush() {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}
}
