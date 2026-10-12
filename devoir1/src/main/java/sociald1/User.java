/**
 *
 * @author Youssef EL OTMANI N°12208301
 * Je déclare qu'il s'agit de mon propre travail.
 *
 */

package sociald1;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Un utilisateur du réseau social Microdon.
 *
 * <p>Un User est caractérisé par:
 * <ul>
 * <li>son nom et son mot de passe</li>
 * <li>sa date d'inscription sur Microdon (= date de création de l'instance de
 * l'User)</li>
 * <li>l'ensemble des utilisateurs auxquels il est abonné et dont les Post sont
 * intégrés à son fil d'actualité</li>
 * <li>la liste de ses Post ordonnés du plus récent au plus ancien</li>
 * </ul></p>
 *
 * Un ensemble complet de méthodes permet d'effectuer des itérations
 * bidirectionnelles sur la liste des Post de l'User. Ce sont les méthodes:
 * startIteration(), hasNext(), nextIndex(), next(), hasPrevious(),
 * previousIndex(), previous() et lastIndex().
 *
 * <pre>{@code
 * aUser.startIteration(); // Initialisation pour une nouvelle itération
 * // Affichage des Post du plus récent au plus ancien
 * while (aUser.hasNext()) {
 * 	System.out.println("Post suivant (plus ancien):" + aUser.next());
 * }
 * // Affichage des Post du plus ancien au plus récent
 * while (aUser.hasPrevious()) {
 * 	System.out.println("Post précédent (plus récent):" + aUser.previous());
 * }
 * }</pre>
 *
 * @invariant getName() != null && !getName().isBlank();
 * @invariant getPassword() != null && !getPassword().isBlank();
 * @invariant getRegistrationDate() != null;
 * @invariant getSubscriptions() != null && !getSubscriptions().contains(null);
 * @invariant getSubscriptionNb() == getSubscriptions().size();
 * @invariant getPosts() != null && !getPosts().contains(null);
 * @invariant (\forall int i, j; i >= 0 && i < j && j < getPostNb();
 *            getPost(i).isAfter(getPost(j)));
 * @invariant getPostNb() == getPosts().size();
 * @invariant previousIndex() >= -1 && previousIndex() < getPosts().size();
 * @invariant nextIndex() >= 0 && nextIndex() <= getPosts().size();
 * @invariant nextIndex() == previousIndex() + 1;
 * @invariant lastIndex() >= -1 && lastIndex() < getPosts().size();
 * @invariant lastIndex() == previousIndex() || lastIndex() == nextIndex();
 *
 * @author Marc Champesme
 * @since 17/09/2023
 * @version 3/10/2023
 *
 */
public class User {
	//complété
	private String name;
	private String password;
	private Instant registrationDate;
	private Set<User> subscriptions;
	private List<Post> userPosts;
	private int previousIndex;
	private int nextIndex;
	private int lastIndex;
	private Post lastPost;

	/**
	 * Initialise une nouvelle instance ayant les nom et mot de passe spécifiés. La
	 * date d'inscription du nouvel utilisateur est la date au moment de l'exécution
	 * de ce constructeur.
	 *
	 * @param userName nom de la nouvelle instance de User
	 * @param password mot de passe de la nouvelle instance de User
	 *
	 * @requires userName != null && !userName.isBlank();
	 * @requires password != null && !password.isBlank();
	 * @ensures getName().equals(userName);
	 * @ensures getPassword().equals(password);
	 * @ensures getRegistrationDate() != null;
	 * @ensures \old(Instant.now()).isBefore(getRegistrationDate());
	 * @ensures getRegistrationDate().isBefore(Instant.now());
	 * @ensures getSubscriptions() != null;;
	 * @ensures getSubscriptions().isEmpty();
	 * @ensures getPosts() != null;
	 * @ensures getPosts().isEmpty();
	 * @ensures !hasPrevious();
	 * @ensures previousIndex() == -1;
	 * @ensures nextIndex() == 0;
	 * @ensures lastIndex() == -1;
	 */
	public User (String userName, String password) {
		//complété
		this.name = userName;
		this.password = password;
		this.registrationDate = Instant.now();
		this.subscriptions = new HashSet<User>();
		this.userPosts = new ArrayList<Post>();
		this.lastPost = null;
		this.previousIndex = -1;
		this.nextIndex = 0;
		this.lastIndex = -1;
		//ou sinon this.startIteration(); à la place des lignes 110, 111, 112, 113.
	}
	/**
	 * Renvoie le nom de cet utilisateur.
	 *
	 * @return le nom de cet utilisateur
	 *
	 * @pure
	 */
	public String getName() {
		//complété
		return this.name;
	}

	/**
	 * Renvoie le mot de passe de cet utilisateur.
	 *
	 * @return le mot de passe de cet utilisateur
	 *
	 * @pure
	 */
	public String getPassword() {
		//complété
		return this.password;
	}

	/**
	 * Renvoie l'Instant représentant la date d'inscription de cet utilisateur.
	 *
	 * @return la date d'inscription de cet utilisateur
	 *
	 * @pure
	 */
	public Instant getRegistrationDate() {
		//complété
		return this.registrationDate;
	}

	/**
	 * Renvoie une nouvelle instance de HashSet contenant l'ensemble des utilisateurs
	 * auxquels cet utilisateur s'est abonné.
	 *
	 * @return l'ensemble des utilisateurs auxquels cet utilisateur s'est abonné
	 *
	 * @ensures result != null;
	 * @ensures (\forall User u; \result.contains(u); hasSubscritionTo(u));
	 * @ensures (\forall User u; hasSubscriptionTo(u); \result.contains(u));
	 * @ensures \result.size() == getSubscriptionNb();
	 *
	 * @pure
	 */
	public HashSet<User> getSubscriptions() {
		//complété
		HashSet<User> hash_abonnes = new HashSet<User>(this.subscriptions);
		return hash_abonnes;
        // ou sinon return new HashSet<User>(this.subscriptions);
	}

	/**
	 * Ajoute l'utilisateur spécifié à l'ensemble des souscriptions de cet
	 * utilisateur. Un utilisateur ne peut souscrire à lui-même. Renvoie true si
	 * l'utilisateur spécifié ne faisait pas déjà partie des souscriptions de cet
	 * utilisateur.
	 *
	 * @param u l'utilisateur auquel cet utilisateur veut s'abonner
	 *
	 * @return true si l'utilisateur spécifié ne faisait pas déjà partie des
	 *         abonnements de cet utilisateur; false sinon
	 *
	 * @requires u != null;
	 * @requires !this.equals(u);
	 * @ensures hasSubscriptionTo(u);
	 * @ensures \result <==> !\old(hasSubscriptionTo(u));
	 * @ensures \result <==> (getSubscriptionNb() == \old(getSubscriptionNb() + 1));
	 * @ensures !\result <==> (getSubscriptionNb() == \old(getSubscriptionNb()));
	 * @ensures !\result <==> (getSubscriptions().equals(\old(getSubscriptions()));
	 * @ensures getSubscriptions().containsAll(oldSubs);
	 *
	 */
	public boolean addSubscription(User u) {
		//complété
		if(!hasSubscriptionTo(u)){
			this.subscriptions.add(u);
			return true;
		}
		return false;
	}

	/**
	 * Retire l'utilisateur spécifié de l'ensemble des abonnements de cet
	 * utilisateur. Renvoie true si l'utilisateur spécifié était précédemment un
	 * abonnement de cet utilisateur.
	 *
	 * @param u l'utilisateur à retirer de l'ensemble des abonnements de cet
	 *          utilisateur.
	 *
	 * @return true si l'utilisateur spécifié était précédemment un abonnement de
	 *         cet utilisateur; false sinon
	 *
	 * @ensures !hasSubscriptionTo(u);
	 * @ensures \result <==> \old(hasSubscriptionTo(u));
	 * @ensures \result <==> (getSubscriptionNb() == \old(getSubscriptionNb() - 1));
	 * @ensures !\result <==> (getSubscriptionNb() == \old(getSubscriptionNb()));
	 * @ensures !\result <==> (getSubscriptions().equals(\old(getSubscriptions()));
	 */
	public boolean removeSubscription(User u) {
		//complété
		if(hasSubscriptionTo(u)){
			this.subscriptions.remove(u);
			return true;
		}
		return false;
	}


	/**
	 * Renvoie true si l'utilisateur spécifié fait partie des abonnements de cet
	 * utilisateur.
	 *
	 * @param u l'utilisateur dont on cherche à savoir s'il fait partie des
	 *          abonnements de cet utilisateur
	 *
	 * @return true si l'utilisateur spécifié fait partie des abonnements de cet
	 *         utilisateur; false sinon
	 *
	 * @ensures \result <==> getSubscriptions().contains(u);
	 *
	 * @pure
	 */
	public boolean hasSubscriptionTo(User u) {
		//complété
		return this.subscriptions.contains(u);
	}

	/**
	 * Renvoie le nombre d'utilisateurs auxquels cet utilisateur est abonné.
	 *
	 * @return le nombre d'utilisateurs auxquels cet utilisateur est abonné
	 *
	 * @ensures \result == getSubscriptions().size();
	 *
	 * @pure
	 */
	public int getSubscriptionNb() {
		//complété
		return this.subscriptions.size();
	}

	/**
	 * Renvoie une nouvelle instance de ArrayList contenant la liste des posts de cet utilisateur. La
	 * liste renvoyée est triée selon leurs dates, les messages les plus récents
	 * étant en tête de liste.
	 *
	 * @return une liste des posts de cet utilisateur
	 *
	 * @ensures (\forall int i, j; i >= 0 && i < j && j < \result.size();
	 *          \result.get(i).isAfter(\result.get(j)));
	 *
	 * @pure
	 */
	public ArrayList<Post> getPosts() {
		//complété
		ArrayList<Post> List_posts = new ArrayList<Post>(this.userPosts);
		return List_posts;
        // ou sinon return new ArrayList<Post>(this.userPosts);
	}

	/**
	 * Crée et renvoie une nouvelle instance de Post dont le texte est la chaîne de
	 * caractère spécifiée et l'ajoute à la liste des posts de cet utilisateur. La
	 * date de ce Post est la date d'exécution de cette méthode.
	 *
	 * @param msg texte du nouveau post de cet utilisateur
	 *
	 * @return le nouveau Post de cet utilisateur
	 *
	 * @old oldDate = Instant.now();
	 * @requires msg != null;
	 * @ensures getPost(0).equals(\result);
	 * @ensures \result.getText().equals(msg);
	 * @ensures getPostNb() == \old(getPostNb()) + 1;
	 * @ensures oldDate.isBefore(\result.getDate());
	 * @ensures \result.getDate().isBefore(Instant.now());
	 * @ensures \old(lastIndex() >= 0) ==> nextIndex() == \old(nextIndex() + 1);
	 * @ensures \old(lastIndex() >= 0) ==> previousIndex() == \old(previousIndex() + 1);
	 * @ensures \old(lastIndex() >= 0) ==> lastIndex() == \old(lastIndex() + 1);
	 * @ensures \old(lastIndex() == -1) ==> nextIndex() == \old(nextIndex());
	 * @ensures \old(lastIndex() == -1) ==> previousIndex() == \old(previousIndex());
	 * @ensures \old(lastIndex() == -1) ==> lastIndex() == \old(lastIndex());
	 *
	 */
	public Post addPost(String msg) {
		//complété
		Post nouveau_post = new Post(msg); //création d'une nouvelle instance de Post
		// datecreation est initialisé au moment de l'execution de la méthode constructeur,
		// car ce dernier contient la méthode Istant.now
		this.userPosts.add(0,nouveau_post); // On rajoute le nouveau post à l'index 0
		if (lastIndex >= 0) {
			this.lastIndex ++;
			this.nextIndex ++;
			this.previousIndex ++;
			this.lastPost = this.userPosts.get(this.lastIndex);
			//on décale de 1
		}
		return nouveau_post;
	}

	/**
	 * Renvoie le nombre de Post de cet utilisateur.
	 *
	 * @return le nombre de Post de cet utilisateur
	 *
	 * @ensures \result == getPosts().size();
	 *
	 * @pure
	 */
	public int getPostNb() {
		//complété
		return this.userPosts.size();
	}

	/**
	 * Renvoie le ième plus récent Post de ce User.
	 *
	 * @param i index du Post cherché
	 *
	 * @return le ième plus récent Post de ce User
	 *
	 * @requires i >= 0 && i < getPostNb();
	 * @ensures \result.equals(getPosts().get(i));
	 *
	 * @pure
	 */
	public Post getPost(int i) {
		//complété
		return this.userPosts.get(i);
	}

	/**
	 * Initialise ce User pour le démarrage d'une nouvelle itération sur les Post de
	 * ce User. Cette itération s'effectue à partir du Post le plus récent, de sorte
	 * que chaque appel à next() renvoie un Post plus ancien.
	 *
	 * @ensures !hasPrevious();
	 * @ensures previousIndex() == -1;
	 * @ensures nextIndex() == 0;
	 * @ensures lastIndex() == -1;
	 */
	public void startIteration() {
		//complété
		this.lastPost = null;
		this.previousIndex = -1;
		this.nextIndex = 0;
		this.lastIndex = -1;
	}

	/**
	 * Renvoie true si ce User possède un Post plus ancien pour l'itération en
	 * cours.
	 *
	 * @return true si ce User possède un Post plus ancien pour l'itération en cours
	 *
	 * @ensures \result <==> nextIndex() < getPostNb();
	 *
	 * @pure
	 */
	public boolean hasNext() {
		//complété
		return this.nextIndex < this.userPosts.size();
	}

	/**
	 * Renvoie le Post suivant (plus ancien) dans l'itération en cours et avance
	 * d'un élément dans l'itération.
	 *
	 * @return le Post suivant (plus ancien) dans l'itération en cours
	 *
	 * @requires hasNext();
	 * @ensures \result.equals(\old(getPost(nextIndex())));
	 * @ensures \result.equals(getPost(lastIndex()));
	 * @ensures nextIndex() == \old(nextIndex()) + 1;
	 * @ensures previousIndex() == \old(previousIndex()) + 1;
	 * @ensures previousIndex() == \old(nextIndex());
	 * @ensures lastIndex() == \old(nextIndex());
	 * @ensures lastIndex() == previousIndex();
	 */

	public Post next() {
		//complété
		this.lastIndex = this.nextIndex;
		this.previousIndex +=1;
		this.lastPost= getPost(lastIndex);
		return getPost(this.nextIndex++);
		// ou sinon  à la place de la ligne au dessus
		// this.nextIndex++;
		// return userPosts.get(lastIndex);
	}

	/**
	 * Renvoie l'index du Post qui sera renvoyé par le prochain appel à next(). Si
	 * l'itération est arrivée à la fin la valeur renvoyée est getPostNb().
	 *
	 * @return l'index du Post qui sera renvoyé par le prochain appel à next(); ou
	 *         getPostNb()
	 *
	 * @ensures \result == getPostNb() <==> !hasNext();
	 * @ensures hasNext() <==> \result >= 0 && \result < getPostNb();
	 *
	 * @pure
	 */

	public int nextIndex() {
		//complété
		if(hasNext()){
			return this.nextIndex;
		}
		return this.userPosts.size();
	}

	/**
	 * Renvoie true si ce User possède un Post plus récent pour l'itération en
	 * cours.
	 *
	 * @return true si ce User possède un Post plus récent pour l'itération en cours
	 *
	 * @ensures \result <==> previousIndex() >= 0;
	 * @ensures !\result <==> previousIndex() == -1;
	 *
	 * @pure
	 */
	public boolean hasPrevious() {
		//complété
		return this.previousIndex >= 0 ;
	}

	/**
	 * Renvoie le Post précedent (plus récent) dans l'itération en cours et recule
	 * d'un élément dans l'itération.
	 *
	 * @return le Post précedent (plus récent) dans l'itération en cours
	 *
	 * @requires hasPrevious();
	 * @ensures \result.equals(\old(getPost(previousIndex())));
	 * @ensures \result.equals(getPost(lastIndex()));
	 * @ensures nextIndex() == \old(nextIndex()) - 1;
	 * @ensures previousIndex() == \old(previousIndex()) - 1;
	 * @ensures nextIndex() == \old(previousIndex());
	 * @ensures lastIndex() == \old(previousIndex());
	 * @ensures lastIndex() == nextIndex();
	 */
	public Post previous() {
		//complété
		this.lastIndex = this.previousIndex;
		this.nextIndex --;
		this.lastPost= getPost(lastIndex);
		return getPost(this.previousIndex--);
		// ou sinon  à la place de la ligne au dessus
		// this.previousIndex--;
		// return userPosts.get(lastIndex);
	}

	/**
	 * Renvoie l'index du Post qui sera renvoyé par le prochain appel à previous().
	 * Si l'itération est arrivée au début la valeur renvoyée est -1.
	 *
	 * @return l'index du Post qui sera renvoyé par le prochain appel à previous();
	 *         ou -1
	 *
	 * @ensures \result == -1 <==> !hasPrevious();
	 * @ensures hasPrevious() <==> \result >= 0 && \result < getPostNb();
	 *
	 * @pure
	 */
	public int previousIndex() {
		//complété
		if(this.hasPrevious()){
			return this.previousIndex;
		}
		return -1;
	}

	/**
	 * Renvoie l'index du Post renvoyé par le dernier appel à previous() ou next().
	 * Si previous() et next() n'ont pas été appelée depuis le dernier appel à
	 * StartIteration() (ou l'appel du constructeur), renvoie -1.
	 *
	 * @return l'index du Post renvoyé par le dernier appel à previous() ou next()
	 *
	 * @ensures \result == nextIndex() || \result == previousIndex();
	 *
	 * @pure
	 */

	public int lastIndex() {
		//complété
		return this.lastIndex;
	}

}
