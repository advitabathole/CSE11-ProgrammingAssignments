/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * This file contains the implementation of the Post class.
 */

import java.util.ArrayList;

/**
 * Represents a Reddit-style post or comment with voting
 * and thread metadata.
 */
public class Post {

    private String title;
    private String content;
    private Post replyTo;
    private User author;
    private int upvoteCount;
    private int downvoteCount;

    private static final String TO_STRING_POST_FORMAT =
        "[%d|%d]\t%s\n\t%s";
    private static final String TO_STRING_COMMENT_FORMAT =
        "[%d|%d]\t%s";

    /**
     * Constructs an original post with a title and content.
     *
     * @param title the title of the post
     * @param content the post's content
     * @param author the author of the post
     */
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.replyTo = null;
        this.author = author;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
    }

    /**
     * Constructs a comment replying to another post.
     *
     * @param content the comment text
     * @param replyTo the post being replied to
     * @param author the author of the comment
     */
    public Post(String content, Post replyTo, User author) {
        this.title = null;
        this.content = content;
        this.replyTo = replyTo;
        this.author = author;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
    }

    /**
     * Returns the title of this post, or null for a comment.
     *
     * @return the title or null
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the post this one replies to, or null.
     *
     * @return the parent post
     */
    public Post getReplyTo() {
        return this.replyTo;
    }

    /**
     * Returns the author of this post.
     *
     * @return the user who created the post
     */
    public User getAuthor() {
        return this.author;
    }

    /**
     * Returns the upvote count.
     *
     * @return number of upvotes
     */
    public int getUpvoteCount() {
        return this.upvoteCount;
    }

    /**
     * Returns the downvote count.
     *
     * @return number of downvotes
     */
    public int getDownvoteCount() {
        return this.downvoteCount;
    }

    /**
     * Updates the upvote count.
     *
     * @param isIncrement true to increment, false to decrement
     */
    public void updateUpvoteCount(boolean isIncrement) {
        if (isIncrement) {
            this.upvoteCount += 1;
        } else {
            this.upvoteCount -= 1;
        }
    }

    /**
     * Updates the downvote count.
     *
     * @param isIncrement true to increment, false to decrement
     */
    public void updateDownvoteCount(boolean isIncrement) {
        if (isIncrement) {
            this.downvoteCount += 1;
        } else {
            this.downvoteCount -= 1;
        }
    }

    /**
     * Returns the thread chain from the original post
     * to this comment.
     *
     * @return list of posts from root to this post
     */
    public ArrayList<Post> getThread() {
        ArrayList<Post> thread = new ArrayList<>();
        Post current = this;
        while (current != null) {
            thread.add(0, current);
            current = current.getReplyTo();
        }
        return thread;
    }

    /**
     * Returns string representation of this post or comment.
     *
     * @return formatted post/comment string
     */
    public String toString() {
        if (this.title != null) {
            return String.format(
                TO_STRING_POST_FORMAT,
                this.upvoteCount,
                this.downvoteCount,
                this.title,
                this.content);
        }

        return String.format(
            TO_STRING_COMMENT_FORMAT,
            this.upvoteCount,
            this.downvoteCount,
            this.content);
    }
}
