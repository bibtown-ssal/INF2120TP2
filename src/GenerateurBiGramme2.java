import java.util.ArrayList;
import java.util.Iterator;

public class GenerateurBiGramme2 extends ArrayList {

    public GenerateurBiGramme2(){
        super();
    }

    public Iterator<Pair<String,String>> iterator(int delta){

        return new BiGramme<>(delta);
    }

    public class BiGramme <String> implements Iterator<Pair<String, String>>{
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
        public Pair<String, String> next() {
            Pair<String,String> temp = new Pair<String,String>(get(this.debut), get(this.dernier));

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
