import java.util.ArrayList;
import java.util.Iterator;

/**Cette classe permet de generer des bigrammes avec un parametre de distance.
 *
 * Pour une liste d'element qui lui est ajoute, permet un iterateur qui va retourner
 * toutes les Pair d'element a distance egale ou moindre que celle speficiee lors de
 * la creation de l'iterateur (la distance entre deux elements est le nombre d'element
 * les separant + 1)
 *
 * @param <T>   type des elements qui seront dans le generateur de bigrammes
 */
public class GenerateurBiGramme <T> extends ArrayList <T> {

    public GenerateurBiGramme(){
        super();
    }

    /**Cree un iterateur qui permettra de genere des bigrammes
     *
     * un iterateur qui va retourner
     * toutes les Pair d'element a distance egale ou moindre que celle speficiee lors de
     * la creation de l'iterateur (la distance entre deux elements est le nombre d'element
     * les separant + 1)
     *
     * @param delta     distance maximale permise pour un bigramme
     * @return      un iterateur
     * @throws  ExceptionBigrammeImpossible si le generateur a moins de deux elements
     */
    public Iterator<Pair<T,T>> iterator(int delta) throws ExceptionBigrammeImpossible{
        if(this.size() < 2){
            throw new ExceptionBigrammeImpossible();
        }

        return new BiGramme(delta);
    }

    /**classe de l'iterateur specialise de bigrammes
     *
     * @param <T1> type d'element a l'interieur des pairs qui seront retournee
     */
    public class BiGramme <T1> implements Iterator<Pair<T1, T1>>{
        private int debut;
        private int dernier;
        private int delta;

        /**initialise l'iterateur sur les deux premiers elements a retourne en pair
         * avec la distance maximale (distance max de -1 veut dire tous les bigrammes possibles)
         *
         * @param delta     -1 (pour tous les bigrammes) ou un entier plus grand que 0 indiquand la distance maximale
         *                      (distance entre deux elements est le nombre d'element les separant +1)
         */
        private BiGramme(int delta) {
            this.debut = 0;
            this.dernier = 1;
            this.delta = delta;
        }

        /**indique s'il reste au moins un bigramme possible a generer
         *
         * @return true si il reste au moions un bigramme possible, false sinon
         */
        @Override
        public boolean hasNext() {

            return this.debut < size()-1;
        }

        /**donne le prochain bigramme possible.
         *
         * les bigrammes seront retournee, pour un ensemble [a,b,c,d] dans l'ordre suivant:
         * [(a,b),(a,c),(a,d),(b,c),(b,d),(c,d)]. (omettant ceux dont la distance n'est pas permise)
         *
         * @return  un Pair contenant le prochain bigramme
         */
        @Override
        public Pair<T1, T1> next() {
            Pair<T1,T1> temp = new Pair(get(this.debut), get(this.dernier));

            if(this.dernier < size()-1 && ((this.dernier - this.debut) < delta || delta == -1)){
                this.dernier++;
            }else{
                this.debut++;
                this.dernier = this.debut+1;
            }

            return temp;
        }
    }
}
