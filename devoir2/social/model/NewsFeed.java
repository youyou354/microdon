package social.model;

public class NewsFeed {
        //complété
        private User mainUser;
        private User lastUser;
        private int postNb;
        private int nextIndex;
        private int previousIndex;
        private int lastIndex;

        /**
         * Initialise un nouveau NewsFeed pour l'utilisateur spécifié. Le NewsFeed est
         * initialisé de manière à pouvoir commencer une itération de la même manière
         * que si la méthode startIteration avait été appelée.
         *
         * @param aUser l'utilisateur dont on souhaite explorer le NewsFeed
         *
         * @requires aUser != null;
         * @ensures getUser().equals(aUser);
         * @ensures !hasPrevious();
         * @ensures previousIndex() == -1;
         * @ensures hasNext() <==> size() > 0;
         * @ensures nextIndex() == 0;
         * @ensures lastIndex() == -1;
         * @ensures lastUser() == null;
         */
        public NewsFeed(User aUser) {
            //complété
            this.mainUser = aUser;
            this.startIteration();
        }

        /**
         * Renvoie l'utilisateur dont ce NewsFeed est le fil d'actualité.
         *
         * @return l'utilisateur dont ce NewsFeed est le fil d'actualité
         *
         * @pure
         */
        public User getUser() {
            //complété
            return this.mainUser;
        }

        /**
         * Démarre une nouvelle itération de ce NewsFeed.
         *
         * @ensures !hasPrevious();
         * @ensures previousIndex() == -1;
         * @ensures hasNext() <==> size() > 0;
         * @ensures nextIndex() == 0;
         * @ensures lastIndex() == -1;
         * @ensures lastUser() == null;
         * @ensures !getUser().hasPrevious();
         * @ensures (\forall User u; getUser().equals(u) ||
         *          getUser().getSubscriptions().contains(u); !u.hasPrevious());
         * @ensures (\forall User u; getUser().equals(u) ||
         *          getUser().getSubscriptions().contains(u); u.lastIndex() == -1);
         */
        public void startIteration() {
            //complété
            this.previousIndex = -1;
            this.nextIndex = 0;
            this.lastIndex = -1;
            this.lastUser = null;
            this.postNb = this.mainUser.getPostNb();
            this.mainUser.startIteration();
            for(User u : this.mainUser.getSubscriptions()){
                this.postNb += u.getPostNb();
                u.startIteration();
            }
        }

        /**
         * Renvoie true si et seulement si cette itération possède un Post précédent
         * (i.e. plus récent).
         *
         * @return true si cette itération possède un Post précédent; false sinon
         *
         * @ensures \result <==> (\exists User u; getUser().equals(u) ||
         *          getUser().getSubscriptions().contains(u); u.hasPrevious());
         * @pure
         */
        public boolean hasPrevious() {
            //complété
            if(this.mainUser.hasPrevious()){
                return true;
            }
            else {
                for (User u : this.mainUser.getSubscriptions()){
                    if(u.hasPrevious()){
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Renvoie, s'il en existe un, l'index du Post précédent dans cette itération.
         * Renvoie -1 si cette itération ne possède pas de Post précédent.
         *
         * @return l'index du Post précédent dans cette itération s'il un tel élement
         *         existe; -1 sinon
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
         * Renvoie le Post précédent (plus récent) de cette itération et recule d'un
         * élement. Un appel postérieur à next() renverra le même élement.
         *
         * @return le Post précédent de cette itération
         *
         * @requires hasPrevious();
         * @ensures \result != null;
         * @ensures previousIndex() == \old(previousIndex()) - 1;
         * @ensures nextIndex() == \old(nextIndex()) - 1;
         * @ensures lastIndex() == \old(previousIndex());
         * @ensures lastUser() != null;
         * @ensures \result.equals(lastUser().getPost(lastUser().lastIndex()));
         */
        public Post previous() {
            //complété
            this.lastIndex = this.previousIndex;
            this.previousIndex--;
            this.nextIndex--;
            Post p_recent = null;
            if(mainUser.hasPrevious()) {
                this.lastUser = this.mainUser;
                p_recent = this.lastUser.getPost(lastUser.previousIndex());
            }
            for(User u : this.mainUser.getSubscriptions()){
                if(u.hasPrevious()) {
                    if (p_recent == null || p_recent.isBefore(u.getPost(u.previousIndex()))) {
                        p_recent = u.getPost(u.previousIndex());
                        this.lastUser = u;
                    }
                }
            }
            return this.lastUser.previous();
        }

        /**
         * Renvoie true si et seulement si cette itération possède un Post postérieur
         * (i.e. plus ancien).
         *
         * @return true si cette itération possède un Post postérieur; false sinon
         *
         * @ensures \result <==> (\exists User u; getUser().equals(u) ||
         *          getUser().getSubscriptions().contains(u); u.hasNext());
         * @pure
         */
        public boolean hasNext() {
            //complété
            if(this.mainUser.hasNext()){
                return true;
            }
            for (User u : this.mainUser.getSubscriptions()){
                if(u.hasNext()){
                    return true;
                }
            }
            return false;
        }

        /**
         * Renvoie le Post suivant (plus ancien) de cette itération et avance d'un
         * élement dans l'itération. Un appel ultérieur à prévious() renverra le même
         * élement.
         *
         * @return le Post suivant de cette itération
         *
         * @requires hasNext();
         * @ensures \result != null;
         * @ensures previousIndex() == \old(previousIndex()) + 1;
         * @ensures nextIndex() == \old(nextIndex()) + 1;
         * @ensures lastIndex() == \old(nextIndex());
         * @ensures lastUser() != null;
         * @ensures \result.equals(lastUser().getPost(lastUser().lastIndex()));
         */
        public Post next() {
            //complété
            this.lastIndex = this.nextIndex;
            this.nextIndex++;
            this.previousIndex++;
            Post p_ancien = null;
            if(mainUser.hasNext()) {
                this.lastUser = this.mainUser;
                p_ancien = this.lastUser.getPost(lastUser.nextIndex());
            }
            for(User u : this.mainUser.getSubscriptions()){
                if(u.hasNext()) {
                    if (p_ancien == null || p_ancien.isAfter(u.getPost(u.nextIndex()))) {
                        p_ancien = u.getPost(u.nextIndex());
                        this.lastUser = u;
                    }
                }
            }
            return this.lastUser.next();
        }

        /**
         * Renvoie, s'il en existe un, l'index du Post suivant dans cette itération.
         * Renvoie -1 si cette itération ne possède pas de Post suivant.
         *
         * @return l'index du Post suivant dans cette itération s'il un tel élement
         *         existe; -1 sinon
         *
         * @pure
         */
        public int nextIndex() {
            //complété
            if(this.hasNext()){
                return this.nextIndex;
            }
            return -1;
        }

        /**
         * Renvoie l'index dans ce NewsFeed de l'élement renvoyé par le dernier appel à
         * next() ou previous(). Renvoie -1 si aucun appel à next() ou prévious() n'a
         * été effectué depuis le démarrage de cette itération (i.e. depuis la création
         * de l'instance ou le dernier appel à startIteration()).
         *
         * @return l'index de l'élement renvoyé par le dernier appel à next() ou
         *         previous()
         *
         * @pure
         */
        public int lastIndex() {
            //complété
            return this.lastIndex;
        }


        /**
         * Renvoie l'auteur du Post renvoyé par le dernier appel à next() ou previous().
         * Renvoie null si aucun appel à next() ou prévious() n'a été effectué depuis le
         * démarrage de cette itération (i.e. depuis la création de l'instance ou le
         * dernier appel à startIteration()).
         *
         * @return l'auteur du Post renvoyé par le dernier appel à next() ou previous()
         *
         * @ensures \result != null ==> getUser().getSubscriptions().contains(\result);
         *
         * @pure
         */
        public User lastUser() {
            //complété
            return this.lastUser;
        }

        /**
         * Renvoie leed.
         * e nombre total de Post de ce NewsF
         * @return le nombre total de Post de ce NewsFeed
         *
         * @pure
         */
        public int size() {
            //complété
            return this.postNb;
        }
    }

