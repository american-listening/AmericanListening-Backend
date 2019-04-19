package com.americanlistening.core;

/**
 * An export format represents the string format in which a set of objects will
 * be formatted into.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class ExportFormat {

	/**
	 * The default format.
	 */
	public static final ExportFormat defaultFormat = new DefaultFormat();

	private static class DefaultFormat extends ExportFormat {

		@Override
		public String format(String[] varNames, Object[] varValues) {
			if (varNames.length != varValues.length)
				throw new IllegalArgumentException("Invalid array lengths.");
			String callerClass = new Exception().getStackTrace()[0].getClassName();
			StringBuilder builder = new StringBuilder();
			builder.append(callerClass).append('[');
			if (varValues.length == 0 || varNames.length == 0) {
				builder.append(']');
				return builder.toString();
			}
			builder.append(varNames[0]).append('=').append(varValues[0]);
			for (int i = 0; i < varNames.length; i++) {
				if (varValues[0] != null)
					builder.append(',').append(varNames[0]).append('=').append(varValues[0]);
				else
					builder.append(',').append(varNames[0]).append("=null");
			}
			builder.append(']');
			return builder.toString();
		}
	}

	/**
	 * Formats a set of objects.
	 * 
	 * @param varNames
	 *            The names of the variables.
	 * @param varValues
	 *            The values of the respective variables.
	 * @return The formatted string.
	 */
	public abstract String format(String[] varNames, Object[] varValues);
}
