package com.americanlistening.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.americanlistening.core.memory.MemoryObject;

/**
 * Class handling the dynamic loading and unloading of data.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class DynamicMemoryHandler {
	
	public static final int DEFAULT_TIMEOUT = 5000;
	
	public static class Request {
		private long requestID;
		public RequestType requestType;
		public String requestSubType;
	}

	public static enum RequestType {
		LOAD, SAVE
	}
	
	private Logger logger;
	private DynamicMemoryHandlerRun run;
	private boolean allowRequests;
	
	DynamicMemoryHandler(Instance inst) {
		this.logger = inst.logger;
	}
	
	private class DynamicMemoryHandlerRun implements Runnable {

		private long nid;
		private List<Request> requests;
		private Map<Long, MemoryObject> results;
		
		private Object lock;
		
		private DynamicMemoryHandlerRun() {
			nid = 0L;
			requests = new ArrayList<>();
			results = new HashMap<>();
			lock = new Object();
		}
		
		@Override
		public void run() {
			while (true) {
				if (requests.size() > 0 && allowRequests) {
					synchronized (lock) {
						Request req = requests.get(0);
						if (req.requestType == RequestType.LOAD) {
							
						} else if (req.requestType == RequestType.SAVE) {
						} else {
							throw new RuntimeException("Request " + req.requestID + " has invalid request type.");
						}
						results.put(req.requestID, null);
					}
				} else
					Thread.yield();
			}
		}
		
		private long generateRequest(Request req) {
			synchronized (lock) {
				req.requestID = nid++;
				requests.add(req);
			}
			return req.requestID;
		}
		
		private MemoryObject getResult(long rid) {
			return results.get(rid);
		}
	}
	
	public long requestAndContinue(Request req) {
		return run.generateRequest(req);
	}
	
	public MemoryObject requestAndWait(Request req, int timeout) throws IllegalStateException {
		long id = requestAndContinue(req);
		long start = System.currentTimeMillis();
		MemoryObject ret = run.getResult(id);
		while (ret == null) {
			if ((System.currentTimeMillis() - start) > timeout)
				throw new IllegalStateException("Request has timed out after " + timeout + "ms.");
			ret = run.getResult(id);
			Thread.yield();
		}
		return ret;
	}
	
	public MemoryObject requestAndWait(Request req) throws IllegalStateException {
		return requestAndWait(req, DEFAULT_TIMEOUT);
	}
	
	public void dispatch() {
		Thread t = new Thread(run);
		t.setName("Dynamic-Memory-Thread");
		t.start();
	}
	
	public void setAllowRequests(boolean allowRequests) {
		this.allowRequests = allowRequests;
	}
	
	public boolean allowsRequests() {
		return allowRequests;
	}
}
