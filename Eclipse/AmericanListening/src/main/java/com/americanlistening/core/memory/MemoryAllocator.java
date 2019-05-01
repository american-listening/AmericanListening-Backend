package com.americanlistening.core.memory;

import java.io.IOException;

public abstract class MemoryAllocator {

	public abstract String allocatorID();
	
	public abstract HandledMemoryObject load() throws IOException;
}
