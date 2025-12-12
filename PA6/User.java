/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * This file contains the implementation of the User class.
 */

import java.util.ArrayList;

/**
 * Represents a user on the Reddit-like platform, tracking
 * posts created, votes made, and total karma.
 */
public class User {

    private int karma;
    private String username;
    private ArrayList<Post> posts;
    private ArrayList<Post> upvoted;
    private ArrayList<Post> downvoted;

    private static final String USER_STRING_FORMAT =
        "u/%s Karma: %d";

    /**
     * Constructs a new User with the given username.
     *
     * @param username the user's name
     */
    public User(String username) {
        this.username = username;
        this.karma = 0;
        this.posts = new ArrayList<>();
        this.upvoted = new ArrayList<>();
        this.downvoted = new ArrayList<>();
    }

    /**
     * Adds a post authored by this user.
     *
     * @param post the post to add
     */
    public void addPost(Post post) {
        if (post == null) {
            return;
        }
        this.posts.add(post);
        updateKarma();
    }

    /**
     * Recomputes karma based on all posts.
     */
    public void updateKarma() {
        this.karma = 0;
        for (Post p : this.posts) {
            this.karma += p.getUpvoteCount()
                       - p.getDownvoteCount();
        }
    }

    /**
     * Returns the user's karma.
     *
     * @return karma score
     */
    public int getKarma() {
        return this.karma;
    }

    /**
     * Upvotes a post.
     *
     * @param post the post to upvote
     */
    public void upvote(Post post) {
        if (post == null || this.upvoted.contains(post)
            || post.getAuthor() == this) {
            return;
        }

        if (this.downvoted.contains(post)) {
            this.downvoted.remove(post);
            post.updateDownvoteCount(false);
        }

        this.upvoted.add(post);
        post.updateUpvoteCount(true);
        post.getAuthor().updateKarma();
    }

    /**
     * Downvotes a post.
     *
     * @param post the post to downvote
     */
    public void downvote(Post post) {
        if (post == null || this.downvoted.contains(post)
            || post.getAuthor() == this) {
            return;
        }

        if (this.upvoted.contains(post)) {
            this.upvoted.remove(post);
            post.updateUpvoteCount(false);
        }

        this.downvoted.add(post);
        post.updateDownvoteCount(true);
        post.getAuthor().updateKarma();
    }

    /**
     * Returns the top-scoring original post.
     *
     * @return the best post or null
     */
    public Post getTopPost() {
        Post top = null;
        int max = Integer.MIN_VALUE;

        for (Post p : this.posts) {
            if (p.getTitle() != null) {
                int score = p.getUpvoteCount()
                           - p.getDownvoteCount();
                if (score > max) {
                    max = score;
                    top = p;
                }
            }
        }
        return top;
    }

    /**
     * Returns the top-scoring comment.
     *
     * @return the best comment or null
     */
    public Post getTopComment() {
        Post top = null;
        int max = Integer.MIN_VALUE;

        for (Post p : this.posts) {
            if (p.getTitle() == null) {
                int score = p.getUpvoteCount()
                           - p.getDownvoteCount();
                if (score > max) {
                    max = score;
                    top = p;
                }
            }
        }
        return top;
    }

    /**
     * Returns the list of posts authored by this user.
     *
     * @return list of posts
     */
    public ArrayList<Post> getPosts() {
        return this.posts;
    }

    /**
     * Returns a formatted string of this user.
     *
     * @return string representation
     */
    public String toString() {
        return String.format(
            USER_STRING_FORMAT,
            this.username,
            this.karma);
    }
}
