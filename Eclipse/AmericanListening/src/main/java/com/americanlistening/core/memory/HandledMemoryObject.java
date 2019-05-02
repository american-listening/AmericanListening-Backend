package com.americanlistening.core.memory;

public interface HandledMemoryObject {

	public void memAlloc(MemoryAllocator allocator) throws MemoryException;
	
	public void memFree(MemoryAllocator allocator) throws MemoryException;
}
