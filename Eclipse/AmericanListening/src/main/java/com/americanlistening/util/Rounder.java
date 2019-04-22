package com.americanlistening.util;

/**
 * Helps with rounding numbers.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public final class Rounder {

	private Rounder() {	}

	/**
	 * Rounds number <code>number</code> to the place <code>place</code>.
	 * 
	 * @param number
	 *            The number to round.
	 * @param place
	 *            The place to round to.
	 * @return The rounded number.
	 */
	public static double round(double number, int place) {
		double mul = Math.pow(10, place);
		double tnum = number * mul;
		double nnum = Math.round(tnum);
		return nnum / mul;
	}
}
