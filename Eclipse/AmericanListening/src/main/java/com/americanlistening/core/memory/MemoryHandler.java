package com.americanlistening.core.memory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A memory handle handles the allocation and freeing of memory;
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class MemoryHandler {

	private MemoryAllocator[] allocators;

	private Map<Long, MemoryObject> objs;

	private long addrn = 0x0;

	/**
	 * Creates a new memory handler with allocators <code>allocators</code>.
	 * 
	 * @param allocators
	 *            The allocators to use.
	 */
	public MemoryHandler(MemoryAllocator... allocators) {
		this.allocators = allocators;
		this.objs = new HashMap<>();
	}

	/**
	 * Adds an allocator to the handler.
	 * 
	 * @param alloc
	 *            The allocator to add.
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
	 * @param file
	 *            The file to load from.
	 * @param type
	 *            The allocator to use.
	 * @param tags
	 *            The tags that the object is tagged with.
	 * @return The memory object.
	 * @throws MemoryException
	 *             When the object fails to load.
	 */
	public MemoryObject load(File file, String type, String... tags) throws MemoryException {
		try {
			HandledMemoryObject hobj = null;
			boolean found = false;
			for (MemoryAllocator alloc : allocators) {
				if (alloc.allocatorID().equals(type)) {
					hobj = alloc.load(file);
					found = true;
					break;
				}
			}
			if (!found)
				throw new MemoryException("Failed to find an allocator of type \"" + type + "\".");
			if (hobj == null)
				throw new MemoryException("Allocator retured a null object.");
			MemoryObject obj = new MemoryObject(hobj, tags);
			obj.setAddress(addrn += 0x1);
			objs.put(obj.getAddress(), obj);
			return obj;
		} catch (IOException e) {
			throw new MemoryException("Failed to load object from file " + file.getAbsolutePath(), e);
		}
	}

	/**
	 * Returns a memory object by its address.
	 * 
	 * @param address
	 *            The address of the object.
	 * @return The respective object.
	 */
	public MemoryObject getObject(long address) {
		return objs.get(address);
	}

	/**
	 * Returns objects with tag <code>tag</code>.
	 * 
	 * @param tag
	 *            The tag to look for.
	 * @return The array of memory objects with the tag.
	 */
	public MemoryObject[] getObjectsWithTag(String tag) {
		List<MemoryObject> rob = new ArrayList<>();
		Collection<MemoryObject> cobjs = objs.values();
		for (MemoryObject obj : cobjs) {
			if (obj.hasTag(tag))
				rob.add(obj);
		}
		MemoryObject[] nobjs = new MemoryObject[rob.size()];
		nobjs = rob.toArray(nobjs);
		return nobjs;
	}
}
