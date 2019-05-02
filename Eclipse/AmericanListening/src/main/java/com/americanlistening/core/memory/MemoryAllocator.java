package com.americanlistening.core.memory;

import java.io.File;
import java.io.IOException;

/**
 * A memory allocator is an object which loads objects from files.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class MemoryAllocator {

	/**
	 * Returns the id of this allocator.
	 * 
	 * @return The id.
	 */
	public abstract String allocatorID();
	
	/**
	 * Loads an object from a file.
	 * 
	 * @param f The object to load from.
	 * @return The loaded object.
	 * @throws IOException When an I/O error occurs.
	 */
	public abstract HandledMemoryObject load(File f) throws IOException;
}
