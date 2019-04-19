package com.americanlistening.core;

import com.americanlistening.core.net.ExportableObject;

/**
 * Class storing an image for a profile.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class ProfileImage implements ExportableObject {

	private EncodedImage image;

	/**
	 * Creates a new profile image.
	 * 
	 * @param image
	 *            The image.
	 */
	public ProfileImage(EncodedImage image) {
		this.image = image;
	}

	/**
	 * Returns the image.
	 * 
	 * @return the image.
	 */
	public EncodedImage getImage() {
		return image;
	}

	@Override
	public String export() {
		return ExportFormat.defaultFormat.format(new String[] { "data" }, new String[] { image.stringData() });
	}
}
