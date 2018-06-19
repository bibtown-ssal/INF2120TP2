import java.util.ArrayList;
import java.util.Iterator;

public class GenerateurBiGrammeCarcan <T> extends ArrayList <T> {

    public GenerateurBiGrammeCarcan(){
        super();
    }

    public Iterator<Pair<T,T>> iterator(int delta){

        return new BiGramme(delta);
    }

    public class BiGramme<U> implements Iterator<Pair<U, U>>{
        private int debut;
        private int dernier;
        private int delta;

        private BiGramme(int delta) {
            this.debut = 0;
            this.dernier = 1;
            this.delta = delta;
        }

        @Override
        public boolean hasNext() {

            return this.debut < size()-1;
        }

        @Override
        public Pair<U, U> next() {
            Pair<U,U> temp = new Pair(get(this.debut), get(this.dernier));


            if(this.dernier < size()-1 && (this.dernier - this.debut) < delta){
                this.dernier++;
            }else{
                this.debut++;
                this.dernier = this.debut+1;
            }

            return temp;
        }
    }
}
