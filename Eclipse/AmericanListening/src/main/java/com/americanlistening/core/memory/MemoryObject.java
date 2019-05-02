package com.americanlistening.core.memory;

/**
 * A handled memory object is an object that is managed by a memory handler.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 * @see MemoryHandler
 */
public final class MemoryObject {
	
	/**
	 * null object.
	 */
	public static final MemoryObject NULL_OBJECT;

	/**
	 * null constant.
	 */
	public static final long NULL = 0x0;

	static {
		NULL_OBJECT = new MemoryObject(null);
		NULL_OBJECT.address = NULL;
	}
	
	private HandledMemoryObject wrapped;
	private long address;

	private String[] tags;

	/**
	 * Creates a new memory object.
	 * 
	 * @param wrapped
	 *            The wrapped handle.
	 */
	public MemoryObject(HandledMemoryObject wrapped, String... tags) {
		this.wrapped = wrapped;
		this.tags = tags;
	}

	void setAddress(long address) {
		this.address = address;
	}

	/**
	 * Returns the address of this object.
	 * 
	 * @return The address.
	 */
	public final long getAddress() {
		return address;
	}

	/**
	 * Returns whether the object is null.
	 * 
	 * @return <code>true</code> if it is null and <code>false</code> otherwise.
	 */
	public final boolean isNull() {
		return address != NULL || wrapped == null;
	}

	/**
	 * Returns whether the object has tag <code>tag</code>.
	 * 
	 * @param tag
	 *            The tag.
	 * @return <code>true</code> if the object has it and <code>false</code>
	 *         otherwise.
	 */
	public final boolean hasTag(String tag) {
		for (String t : tags) {
			if (t.equals(tag))
				return true;
		}
		return false;
	}

	@Override
	public void finalize() {
		try {
			if (!isNull())
				memFree();
		} catch (Throwable t) {
			throw new RuntimeException("Failed to free memory object " + toString(), t);
		}
	}

	@Override
	public String toString() {
		return "HandledMemoryObject[address=" + address + "]";
	}

	/**
	 * Allocates memory on the handler.
	 * 
	 * @throws MemoryException
	 *             When allocation fails.
	 */
	public void memAlloc() throws MemoryException {
		if (wrapped == null)
			throw new MemoryException("Wrapped object is null.");
		wrapped.memAlloc();
	}

	/**
	 * Frees memory on the handler.
	 * 
	 * @throws MemoryException
	 *             When deallocation fails.
	 */
	public void memFree() throws MemoryException {
		if (isNull())
			throw new MemoryException("Cannot free a null object.");
		address = NULL;
		wrapped.memFree();
	}
}
