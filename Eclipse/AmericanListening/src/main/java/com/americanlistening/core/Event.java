package com.americanlistening.core;

import com.americanlistening.core.net.ExportableObject;

/**
 * Represents an event that has happened.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Event implements ExportableObject {

	/**
	 * Returns an event type by its id.
	 * 
	 * @param id
	 *            The id.
	 * @return The respective event type, or <code>null</code> if it does not exist.
	 */
	public static EventType fromID(int id) {
		for (EventType t : EventType.values()) {
			if (t.id == id)
				return t;
		}
		return null;
	}

	/**
	 * Specifies the type of event that has happened.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.0
	 */
	public static enum EventType {
		NULL(0), FOLLOW(1), UNFOLLOW(2), LIKE(3);

		private int id;

		private EventType(int id) {
			this.id = id;
		}

		/**
		 * Returns the id of this event.
		 * 
		 * @return The id.
		 */
		public int getID() {
			return id;
		}
	}

	/**
	 * The type of event.
	 */
	public final EventType type;

	/**
	 * The time at which the event occurred.
	 */
	public final long time;

	/**
	 * Creates a new event.
	 * 
	 * @param type
	 *            The type of event.
	 */
	public Event(EventType type) {
		this.type = type;
		this.time = System.currentTimeMillis();
	}
	
	@Override
	public String toString() {
		return "Event[event=" + type.id + ",time=" + time + "]";
	}

	@Override
	public String export() {
		return ExportFormat.defaultFormat.format(new String[] { "type", "time" }, new Object[] { type.id, time });
	}
}
