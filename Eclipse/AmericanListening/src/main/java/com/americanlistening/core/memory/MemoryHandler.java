package com.americanlistening.core.memory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class MemoryHandler {

	private MemoryAllocator[] allocators;

	private Map<Long, MemoryObject> objs;
	
	private long addrn = 0L;

	/**
	 * Creates a new memory handler with allocators <code>allocators</code>.
	 * 
	 * @param allocators The allocators to use.
	 */
	public MemoryHandler(MemoryAllocator... allocators) {
		this.allocators = allocators;
	}

	/**
	 * Adds an allocator to the handler.
	 * 
	 * @param alloc The allocator to add.
	 */
	public void addAllocator(MemoryAllocator alloc) {
		MemoryAllocator[] allocs = (MemoryAllocator[]) Arrays.copyOf(allocators, allocators.length + 1);
		allocs[allocs.length - 1] = alloc;
		this.allocators = allocs;
	}

	/**
	 * Returns the allocators in this handler. The returned list does not affect
	 * this handler.
	 * 
	 * @return The allocators used by this handler.
	 */
	public MemoryAllocator[] getAllocators() {
		return (MemoryAllocator[]) Arrays.copyOf(allocators, allocators.length);
	}

	/**
	 * Loads a handled memory object from a file.
	 * 
	 * @param file The file to load from.
	 * @param type The allocator to use.
	 * @return The memory object.
	 * @throws MemoryException When the object fails to load.
	 */
	public MemoryObject load(File file, String type) throws MemoryException {
		try {
			HandledMemoryObject hobj = null;
			boolean found = false;
			for (MemoryAllocator alloc : allocators) {
				if (alloc.allocatorID().equals(type)) {
					hobj = alloc.load();
					found = true;
					break;
				}
			}
			if (!found)
				throw new MemoryException("Failed to find an allocator of type \"" + type + "\".");
			if (hobj == null)
				throw new MemoryException("Allocator retured a null object.");
			MemoryObject obj = new MemoryObject(hobj);
			obj.setAddress(addrn++);
			return obj;
		} catch (IOException e) {
			throw new MemoryException("Failed to load object from file " + file.getAbsolutePath(), e);
		}
	}
	
	public MemoryObject getObject(long address) {
		return objs.get(address);
	}
}
