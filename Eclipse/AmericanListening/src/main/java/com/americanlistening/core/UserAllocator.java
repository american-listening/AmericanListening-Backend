package com.americanlistening.core;

import java.io.File;
import java.io.IOException;

import com.americanlistening.core.memory.HandledMemoryObject;
import com.americanlistening.core.memory.MemoryAllocator;

public class UserAllocator extends MemoryAllocator {

	@Override
	public String allocatorID() {
		return "user";
	}

	@Override
	public HandledMemoryObject load(File f) throws IOException {
		UserIO.readUser(f);
	}

}
