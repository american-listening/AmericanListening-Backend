package com.americanlistening.core;

import com.americanlistening.core.net.ExportableObject;

/**
 * Class wrapping location information.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Location implements ExportableObject {

	/**
	 * Specifies a country.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.0
	 */
	public static enum Country {
		UNKNOWN(-1, null), UNSPECIFIED(0, "Unspecified"), UNITED_STATES(1, "United States"), CANADA(2,
				"Canada"), MEXICO(3, "Mexico");

		private int id;
		private String name;

		private Country(int id, String name) {
			this.id = id;
			this.name = name;
		}

		/**
		 * Returns the id of the country.
		 * 
		 * @return The id.
		 */
		public int id() {
			return id;
		}

		/**
		 * Returns the name of the country.
		 * 
		 * @return The name.
		 */
		public String friendlyName() {
			return name;
		}

		/**
		 * Creates an empty location with this country.
		 * 
		 * @return The new location.
		 */
		public Location createEmpty() {
			Location loc = new Location();
			loc.country = this;
			return loc;
		}
	}

	public Country country;
	public String state, city;

	@Override
	public String export() {
		return ExportFormat.defaultFormat.format(new String[] {
			"country", "state", "city"
		},
		new Object[] {
			country.id(), state, city	
		});
	}
}
