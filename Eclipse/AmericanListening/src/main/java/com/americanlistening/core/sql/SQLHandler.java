package com.americanlistening.core.sql;

import java.sql.ResultSet;

/**
 * Interface used to execute code after an SQL request has been completed.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
@FunctionalInterface
public interface SQLHandler {

	/**
	 * Executes when a query has been processed.
	 * 
	 * @param result
	 *            The result from the query.
	 * @param query
	 *            The original query that was requested.
	 */
	public void execute(ResultSet result, String query);
}
