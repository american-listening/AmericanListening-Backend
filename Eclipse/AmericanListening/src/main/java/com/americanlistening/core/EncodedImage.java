package com.americanlistening.core;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * An encoded image is an stored in a byte array.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class EncodedImage {

	public static final int BUFFER_SIZE = 1024;

	/**
	 * Loads an encoded image from a file.
	 * 
	 * @param file
	 *            The file to load from.
	 * @return The new image.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public static EncodedImage load(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byte[] buf = new byte[BUFFER_SIZE];
		int bytesLoaded = 0;
		for (int readNum; (readNum = fis.read(buf)) != -1;) {
			bos.write(buf, 0, readNum);
			bytesLoaded += readNum;
		}
		fis.close();
		System.out.println("Loaded " + bytesLoaded + " bytes from file " + file.getAbsolutePath());

		byte[] bytes = bos.toByteArray();
		return new EncodedImage(bytes);
	}

	/**
	 * Converts an encoded image to a Java AWT Image object.
	 * 
	 * @param img
	 *            The encoded image.
	 * @return The AWT image.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public static Image toImage(EncodedImage img) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(img.data);
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");

		ImageReader reader = readers.next();
		Object source = bis;
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();

		return reader.read(0, param);
	}

	/**
	 * Image data.
	 */
	public final byte[] data;

	private EncodedImage(byte[] data) {
		this.data = data;
	}
	
	/**
	 * Returns the data as a string.
	 * 
	 * @return The data.
	 */
	public String stringData() {
		return Arrays.toString(data);
	}
}
