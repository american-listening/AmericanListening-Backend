package com.americanlistening.core.net;

class ClientSessionImpl implements ClientSession {

	private Sessions sessions;
	private Connection connection;
	private long sessionID;
	
	ClientSessionImpl(Sessions sessions, Connection connection) {
		if (sessions == null)
			throw new NullPointerException("null Sessions object.");
		this.sessions = sessions;
		this.connection = connection;
		this.sessionID = sessions.generateID();
	}

	@Override
	public long sessionID() {
		return sessionID;
	}
	
	@Override
	public void freeSession() {
		sessions.free(sessionID);
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void handleInput(String inp) {
		// TODO Auto-generated method stub
		
	}
}
