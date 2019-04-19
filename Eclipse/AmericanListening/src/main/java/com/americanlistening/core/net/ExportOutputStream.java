package com.americanlistening.core.net;

import java.io.IOException;
import java.io.OutputStream;

import com.americanlistening.core.Location;

public class ExportOutputStream extends OutputStream {

	private OutputStream parent;
	
	public ExportOutputStream(OutputStream parent) {
		this.parent = parent;
	}
	
	@Override
	public void write(int b) throws IOException {
		parent.write(b);
	}

	public void write(ExportableObject obj) throws IOException {
		write(obj.export().getBytes());
	}
}
