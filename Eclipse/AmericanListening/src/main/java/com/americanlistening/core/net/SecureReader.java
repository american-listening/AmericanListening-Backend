package com.americanlistening.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class SecureReader extends Reader {

	private BufferedReader in;
	private Encryption en;
	
	public SecureReader(InputStream in, Encryption en) {
		this.in = new BufferedReader(new InputStreamReader(in));
		this.en = en;
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int read(char[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
