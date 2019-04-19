package com.americanlistening.core;

import java.util.List;

/**
 * Class containing information about a user.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class User {

	/**
	 * Unique user id.
	 */
	public final long id;

	// Public profile attributes
	private String username;
	private List<User> following;
	private List<User> followers;
	private ProfileImage profileImage;

	// Possible public profile attributes
	private Location location;
	private int age;

	// Private profile attributes - used to login
	private String email;

	/**
	 * Creates a new user.
	 * 
	 * @param id
	 *            The id to use.
	 */
	public User(long id) {
		this.id = id;
	}

	/**
	 * Returns the username of this user.
	 * 
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of this user.
	 * 
	 * @param username
	 *            The username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the following
	 */
	public List<User> getFollowing() {
		return following;
	}

	/**
	 * Add a user that this user is following.
	 * 
	 * @param user
	 *            The user to add.
	 */
	public void addFollowing(User user) {
		following.add(user);
	}

	/**
	 * Remove a user that this user is following.
	 * 
	 * @param user
	 *            The user to remove.
	 */
	public void removeFollowing(User user) {
		following.remove(user);
	}

	/**
	 * @return the followers
	 */
	public List<User> getFollowers() {
		return followers;
	}

	/**
	 * Adds a follower to this user.
	 * 
	 * @param user
	 *            The user that has followed.
	 */
	public void addFollower(User user) {
		followers.add(user);
	}

	/**
	 * Removes a follower from this user.
	 * 
	 * @param user
	 *            The user that should be removed.
	 */
	public void removeFollower(User user) {
		followers.remove(user);
	}

	/**
	 * @return the profileImage
	 */
	public ProfileImage getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage
	 *            the profileImage to set
	 */
	public void setProfileImage(ProfileImage profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Returns the registered email of this user.
	 * 
	 * @return The email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of this user.
	 * 
	 * @param email
	 *            The email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Follows user <code>user</code>.
	 * 
	 * @param user
	 *            The user to follow.
	 */
	public void follow(User user) {
		user.addFollower(this);
		addFollowing(user);
	}

	/**
	 * Unfollows user <code>user</code>.
	 * 
	 * @param user
	 *            The user to unfollow.
	 */
	public void unfollow(User user) {
		user.removeFollower(this);
		removeFollowing(user);
	}

	/**
	 * Returns the IDs of who this user is following.
	 * 
	 * @return The IDs of the users.
	 */
	public long[] getFollowingIDs() {
		return null;
	}

	/**
	 * Returns the IDs of who this user is followed by.
	 * 
	 * @return The IDs of the users.
	 */
	public long[] getFollowerIDs() {
		return null;
	}

	@Override
	public String toString() {
		return "User[id=" + id + ",name=" + username + "]";
	}
}
