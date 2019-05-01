package com.americanlistening.core.memory;

/**
 * A memory exception is thrown when there are errors in memory allocations or
 * deallocations.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class MemoryException extends RuntimeException {

	private static final long serialVersionUID = 2167327108795166064L;

	public MemoryException() {
		super();
	}

	public MemoryException(String message) {
		super(message);
	}

	public MemoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemoryException(Throwable cause) {
		super(cause);
	}

	protected MemoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
