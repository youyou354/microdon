/**
 * @author Youssef EL OTMANI 12208301
 * Je déclare qu'il s'agit de mon propre travail
 */

package social.model;


import java.time.Instant;
import java.util.*;


/**
 * Un post d'un utilisateur d'un réseau social, caractérisé par son texte, sa
 * date de création et l'ensemble des utilisateurs l'ayant "liké".
 *
 * La seule caractéristque modifiable de cette classe est l'ensemble des
 * utilisteurs ayant "liké" ce Post.
 *
 * @invariant getText() != null;
 * @invariant getDate() != null;
 * @invariant getLikers() != null && !getLikers().contains(null);
 * @invariant iterator() != null;
 *
 * @author Marc Champesme
 * @since 2/08/2023
 * @version 9/12/2023
 */


public class SimplePost implements Post {

    private Instant dateCreation;
    protected String text;
    private Set<User> likers;

    public SimplePost(String text){
        if (text == null){ throw new NullPointerException();}
        this.text = text;
        this.likers = new HashSet<User>();
        this.dateCreation = Instant.now();
    }


    /**
     * Renvoie une nouvelle instance de Date représentant la date de création de ce
     * Post.
     *
     * @return la date de création de ce Post
     *
     * @pure
     */
    public Instant getDate(){
        return this.dateCreation;
    }

    /**
     * Renvoie le texte de ce Post.
     *
     * @return le texte de ce Post
     *
     * @pure
     */
    public String getText(){
        return this.text;
    }

    /**
     * Renvoie le nombre de like, c'est à dire le nombre d'utilisateurs ayant "liké"
     * ce Post.
     *
     * @return le nombre de like de ce Post
     *
     * @ensures \result == getLikers().size();
     *
     * @pure
     */
    public int getLikeNumber(){
        return this.likers.size();
    }

    /**
     * Renvoie true si l'utilisateur spécifié fait partie des "likers" de ce Post.
     *
     * @param u utilisateur dont on souhaite savoir s'il fait partie des likers de
     *          ce message
     * @return true si l'utilisateur spécifié fait partie des "likers" de ce Post;
     *         false sinon
     *
     * @ensures \result <==> getLikers().contains(u);
     *
     * @pure
     */
    public boolean hasLikeFrom(User u){
        if (u != null) {
            return this.likers.contains(u);
        }
        return false;
    }

    /**
     * Ajoute un utilisateur à l'ensemble des utilisateurs ayant "liké" ce message.
     * L'auteur d'un Post a la possibilité de "liker" les messages dont il est
     * l'auteur.
     *
     * @param u utilisateur ayant "liké" ce message
     * @return true si l'utilisateur ne faisait pas déjà partie des "likers"; false
     *         sinon
     *
     * @throws NullPointerException si l'argument spécifié est null
     *
     * @requires u != null;
     * @ensures hasLikeFrom(u);
     * @ensures \result <==> !\old(hasLikeFrom(u));
     * @ensures \result ==> (getLikeNumber() == \old(getLikeNumber() + 1));
     * @ensures !\result ==> (getLikeNumber() == \old(getLikeNumber()));
     *
     */
    public boolean addLikeFrom(User u){
        if(u==null) throw new NullPointerException();
        if(!hasLikeFrom(u)){
            this.likers.add(u);
            return true;
        }
        return false;
    }

    /**
     * Renvoie une vue non modifiable de l'ensemble des "likers" de ce Post.
     *
     * @return une vue non modifiable de l'ensemble des "likers" de ce Post
     *
     * @ensures \result != null;
     * @ensures (\forall User u; hasLikeFrom(u); \result.contains(u));
     * @ensures (\forall User u; \result.contains(u); hasLikeFrom(u));
     * @ensures \result.size() == getLikeNumber();
     *
     * @pure
     */
    public Set<User> getLikers(){

        return Collections.unmodifiableSet(this.likers);
    }

    /**
     * Renvoie un iterateur sur l'ensemble des "likers" de ce Post. L'iterateur
     * renvoyé interdit toute modification de l'ensemble.
     *
     * @return un iterateur sur l'ensemble des "likers" de ce Post
     *
     * @ensures \resmodel = new ListIterObserverAdapter<User>(\result);
     * @ensures \resmodel.toSet().equals(getLikers());
     *
     * @pure
     */
    public ListIterator<User> iterator(){
        List<User> list = new ArrayList<User>(this.likers);
        List<User> coll = Collections.unmodifiableList(list);
        return coll.listIterator();
    }

    /**
     * Compare ce Post avec le Post spécifié selon l'ordre de leurs dates de
     * création.
     *
     * @param p le Post à comparer avec ce Post
     *
     * @return O si les dates de création des deux Post sont equals; une valeur
     *         inférieure à 0 si ce Post a une date antérieure à la date du Post
     *         spécifié; une valeur supérieure à 0 si ce Post a une date postérieure
     *         à la date du Post spécifié
     *
     * @throws NullPointerException si l'argument spécifié est null
     *
     * @requires p != null;
     * @ensures \result == this.getDate().compareTo(p.getDate());
     * @ensures (\result == 0) <==> (!this.isBefore(p) && !this.isAfter(p));
     * @ensures (\result < 0) <==> this.isBefore(p);
     * @ensures (\result > 0) <==> this.isAfter(p);
     *
     * @pure
     */
    public int compareTo(Post p){
        if(p == null)
        {
            throw new NullPointerException();
        }
        return this.getDate().compareTo(p.getDate());
    }

    /**
     * Teste si ce Post a été publié avant le Post spécifié.
     *
     * @param p le Post à comparer avec ce Post
     *
     * @return true si et seulement si la date de ce Post est strictement antérieur
     *         à celle du Post spécifié
     *
     * @throws NullPointerException si l'argument spécifié est null
     *
     * @requires p != null;
     * @ensures \result <==> this.getDate().isBefore(p.getDate());
     * @ensures \result <==> (this.compareTo(p) < 0);
     * @ensures !(\result && this.isAfter(p));
     *
     * @pure
     */
    public boolean isBefore(Post p){
        if (p == null){
            throw new NullPointerException();
        }
        return this.dateCreation.isBefore(p.getDate());
    }

    /**
     * Teste si ce Post a été publié après le Post spécifié.
     *
     * @param p le Post à comparer avec ce Post
     *
     * @return true si et seulement si la date de ce Post est strictement postérieur
     *         à celle du Post spécifié
     *
     * @throws NullPointerException si l'argument spécifié est null
     *
     * @requires p != null;
     * @ensures \result <==> this.getDate().isAfter(p.getDate());
     * @ensures \result <==> (this.compareTo(p) > 0);
     * @ensures !(\result && this.isBefore(p));
     *
     * @pure
     */
    public boolean isAfter(Post p){
        if (p == null){
            throw new NullPointerException();
        }
        return this.dateCreation.isAfter(p.getDate());
    }

}