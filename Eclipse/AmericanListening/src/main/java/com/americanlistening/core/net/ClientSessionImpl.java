package com.americanlistening.core.net;

import java.util.ArrayList;
import java.util.List;

class ClientSessionImpl implements ClientSession {

	private Sessions sessions;
	private Connection connection;
	private long sessionID;
	
	private List<InputCallback> iCalls;
	private List<ErrorCallback> eCalls;
	
	ClientSessionImpl(Sessions sessions, Connection connection) {
		if (sessions == null)
			throw new NullPointerException("null Sessions object.");
		this.sessions = sessions;
		this.connection = connection;
		this.sessionID = sessions.generateID();
		iCalls = new ArrayList<InputCallback>();
		eCalls = new ArrayList<ErrorCallback>();
	}
	
	void pushInput(String input) {
		for (InputCallback i : iCalls) {
			i.onInput(input, this);
		}
	}

	@Override
	public long sessionID() {
		return sessionID;
	}
	
	@Override
	public void freeSession() {
		sessions.free(sessionID);
		try {
			connection.close();
		} catch (Throwable t) {
			for (ErrorCallback e : eCalls) {
				e.onError(t, Thread.currentThread(), this);
			}
		}
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void addInputCallback(InputCallback callback) {
		iCalls.add(callback);
	}
}
