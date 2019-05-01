package com.americanlistening.core.memory;

public interface HandledMemoryObject {

	public void memAlloc() throws MemoryException;
	
	public void memFree() throws MemoryException;
}
