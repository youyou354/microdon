/**
 * @author Youssef EL OTMANI 12208301
 * Je déclare qu'il s'agit de mon propre travail
 */

package social.model;


import java.time.Instant;
import java.util.ListIterator;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class  RePost  extends SimplePost {
    private User subPostAuthor;
    private Post subPost;

    public RePost(String text, User subPostAuthor, Post subPost){

        super(text);
        if (subPostAuthor == null ) throw new NullPointerException();
        if (subPost == null) throw new NullPointerException();
        if (!subPostAuthor.getPosts().contains(subPost)) throw new IllegalArgumentException();
        this.subPostAuthor = subPostAuthor;
        this.subPost = subPost;
    }

    @Override
    public String getText(){
        return this.text + this.subPostAuthor + subPost.getText();
    }
}
