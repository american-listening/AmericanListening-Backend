package com.americanlistening.core.net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Handles all active client sessions.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Sessions {

	private Map<Long, ClientSession> sessions;
	private Random r;

	/**
	 * Creates a new sessions object.
	 * 
	 * @param seed
	 *            The seed to use for the ID generator.
	 */
	public Sessions(long seed) {
		sessions = new HashMap<>();
		r = new Random(seed);
	}

	/**
	 * Generates a client ID.
	 * 
	 * @return The ID.
	 */
	public long generateID() {
		long key = r.nextLong();
		while (sessions.containsKey(key)) {
			key = r.nextLong();
		}
		return key;
	}

	/**
	 * Adds a new session.
	 * 
	 * @param session
	 *            The session to add.
	 * @throws IllegalStateException
	 *             When the session id is already in use.
	 */
	public void addSession(ClientSession session) throws IllegalStateException {
		if (sessions.containsKey(session.sessionID()))
			throw new IllegalStateException("Session id " + session.sessionID() + " is already in use.");
		sessions.put(session.sessionID(), session);
	}

	/**
	 * Frees a session.
	 * 
	 * @param sessionID
	 *            The session ID.
	 */
	public void free(long sessionID) {
		sessions.remove(sessionID);
	}

	/**
	 * Returns sessions of id <code>sessionID</code>.
	 * 
	 * @param sessionID
	 *            The sessions id.
	 * @return The respective session, or <code>null</code> if no such session
	 *         exists.
	 */
	public ClientSession getSession(long sessionID) {
		return sessions.get(sessionID);
	}
}
