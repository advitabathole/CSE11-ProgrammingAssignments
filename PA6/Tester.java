/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * This file contains tests for the User and Post classes.
 */

/**
 * Tester class that demonstrates functionality of User
 * and Post, including posting, commenting, voting,
 * threading, and karma updates.
 */
public class Tester {

    /**
     * Executes a series of tests demonstrating program behavior.
     *
     * @param args unused command line arguments
     */
    public static void main(String[] args) {

        User u1 = new User("advita");
        User u2 = new User("ajay");

        System.out.println(u1);
        System.out.println(u2);

        Post p1 = new Post("Hello", "This is my post", u1);
        u1.addPost(p1);

        Post c1 = new Post("Nice post!", p1, u2);
        u2.addPost(c1);

        System.out.println(p1);
        System.out.println(c1);

        System.out.println("Thread for comment:");
        for (Post p : c1.getThread()) {
            System.out.println(p);
        }

        u2.upvote(p1);
        u1.upvote(c1);

        u1.downvote(p1);
        u2.downvote(c1);

        System.out.println("Advita karma: " + u1.getKarma());
        System.out.println("Ajay karma: " + u2.getKarma());

        Post topPost = u1.getTopPost();
        System.out.println("Top post for Advita:");
        System.out.println(topPost);

        Post topComment = u2.getTopComment();
        System.out.println("Top comment for Ajay:");
        System.out.println(topComment);

        Post p2 = new Post("Another post",
                           "More content", u1);
        u1.addPost(p2);
        u2.upvote(p2);
        u1.downvote(p2);

        System.out.println("Advita posts:");
        for (Post p : u1.getPosts()) {
            System.out.println(p);
        }

        System.out.println(
            "Final Advita karma: " + u1.getKarma());
    }
}
