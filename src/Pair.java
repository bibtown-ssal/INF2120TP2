
public class Pair<T, U> {
    public T premiere;
    public U deuxieme;

    public Pair( T premiere, U deuxieme ) {
        //a instanceof U. illegal.
        //deuxieme instanceof Fraction. legal.
        this.premiere = premiere;
        this.deuxieme = deuxieme;
    }
}