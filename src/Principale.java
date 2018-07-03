import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Classe comparant deux String et affichant un indice de simimlitude.
 *
 * Apres avoir recue deux String, cette classe les decomposent en mot (tout charactere non-alphabetique est une separation
 * de mot) compare ces suites de mots a l'aide d'une distance maximale et retourne un indice entre 0 et 1.
 *
 * un indice de 0 implique que les deux phrases sont differente et un indice de 1 implique qu'elles sont identiques
 * (en fonction de la distance)
 *
 */
public class Principale {

    static final private String MSG_SOLL_PHRASE1 = "Veuillez ecrire la premiere phrase a comparer : ";
    static final private String MSG_SOLL_PHRASE2 = "Veuillez ecrire la deuxieme phrase a comparer : ";
    static final private String MSG_SOLL_DISTANCE =
            "Veuillez ecrire la distance maximale de comparaison (-1 pour distance infinie): ";
    static final private String MSG_ERR_INPUT = "Une erreur est survenue, on reccomence";
    static final private String MSG_ERR_DISTANCE = "La distance doit etre -1 ou plus grande ou egale a 0";
    static final private String MSG_ERR_PHRASE_COURTE = "Les phrases doivent avoir minimalement 2 mots";

    /**Methode comparant deux String et affichant un indice de simimlitude.
     *
     * cette classe les decomposent en mot (tout charactere non-alphabetique est une separation
     * de mot) compare ces suites de mots a l'aide d'une distance maximale et retourne un indice entre 0 et 1.
     *
     * un indice de 0 implique que les deux phrases sont differente et un indice de 1 implique qu'elles sont identiques
     * (en fonction de la distance)
     *
     * @param phrase1   String represantant une phrase a comparer
     * @param phrase2   String represantant l'autre phrase a comparer
     * @param distance  Distance maximale permise entre deux mots (-1 = distance maximale) (distance = nombre de mot
     *                      separant les deux mots+1)
     * @return  un indice de similitude
     * @throws ExceptionBigrammeImpossible  si une phrase est de moins de 2 mots
     */
    private static double comparerPhrases(String phrase1, String phrase2, int distance )
            throws ExceptionBigrammeImpossible{

        String[] phrase1separee = separerMots(phrase1);
        String[] phrase2separee = separerMots(phrase2);
        ArrayList<Pair<String, String>> phrase1Bigramme = new ArrayList<>();
        ArrayList<Pair<String, String>> phrase2Bigramme = new ArrayList<>();

        if(phrase1separee.length < 2 || phrase2separee.length < 2){
            throw new ExceptionBigrammeImpossible();
        }

        genererBigramme(phrase1separee,phrase1Bigramme, distance);
        genererBigramme(phrase2separee,phrase2Bigramme, distance);

        return comparerDifferenceBigramme(phrase1Bigramme,phrase2Bigramme);
    }

    /** Cette methode separe une phrase (String) en un tableau de mot
     *
     * chaque charactere non-alphabetique est considere comme un separateur de mot
     *
     * @param phrase    un String represantant une phrase
     * @return      un tableau de mot (en String)
     */
    private static String[] separerMots (String phrase){
        String[] listeMots;
        phrase = phrase.toLowerCase();
        listeMots = Pattern.compile("[^a-z]").split(phrase);

        return listeMots;

    }

    /**
     * Cette methode compare deux set de bigrammes et calcul un indice de similitude entre ces deux sets
     *
     * indice = 2uv/(u+v) ou u = |i|/|s1|  et v = |i|/|s2|  ou s1 et s2 est le nombre de bigramme dans chaque set et
     * i est le nombre de bigramme commun (deux bigrammes identiques dans un set compte comme pour deux)
     *
     * @param set1  un set de bigramme (dans un Arraylist de Pair)
     * @param set2  un autre set de bigramme (dans un ArrayList de Pair)
     * @return      un indice de similarite
     */
    private static double comparerDifferenceBigramme(ArrayList<Pair<String,String>> set1, ArrayList<Pair<String,String>> set2){
        double cpt = 0;
        double s2 = set2.size();
        int i = 0;
        int j;
        double u;
        double v;
        boolean trouve;
        double resultat;

        while(i < set1.size()){
            j = 0;
            trouve = false;
            while(!trouve && j < set2.size()){
                if(bigrammesEgaux(set1.get(i), set2.get(j))){
                    set2.remove(j);
                    trouve = true;
                    cpt++;
                }else{
                    j++;
                }
            }
            i++;
        }
        u = cpt/set1.size();
        v = cpt/s2;
        if(u == 0.0 && v == 0.0){
            resultat =0;
        }else{
            resultat = ( 2 * u * v / ( u + v ));
        }

        return resultat;
    }

    /**Cette methode genere un set de bigramme
     *
     * @param phrase    un tableau de String de mot composant une phrase a utiliser pour generer un set de bigrammes
     * @param bigrammes l'Arraylist de Pair de string qui va contenir les bigrammes
     * @param delta     la distance permise pour generer les bigrammes (-1 = distance maximale) (distance = nombre de mot
     *                       separant les deux mots+1)
     *
     * @throws ExceptionBigrammeImpossible si le tableau de mots contient moins de 2 mots
     */
    private static void genererBigramme(String[] phrase, ArrayList<Pair<String,String>> bigrammes, int delta)
            throws ExceptionBigrammeImpossible {
        GenerateurBiGramme<String> generateur = new GenerateurBiGramme<>();
        for(int i = 0; i < phrase.length; i ++){
            generateur.add(phrase[i]);
        }

        Iterator<Pair<String, String>> it = generateur.iterator(delta);

        while(it.hasNext()){
            bigrammes.add(it.next());
        }

    }

    /**Compare deux bigrammes de String (sans tenir compte de la case)
     *
     * compare le premier element du premier bigramme au premier element du deuxieme bigramme,
     * compare le deuxieme element du premier bigramme au deuxieme element du deuxieme bigramme,
     * et retourne vrai si les elements sont les memes, sans tenir compte de la case.
     *
     * @param pair1 un premier bigramme
     * @param pair2 le bigramme avec qui comparer
     * @return  true s'ils contiennent les memes String dans le meme ordre (ignorant la case)
     *          false sinon
     */
    private static boolean bigrammesEgaux(Pair<String, String> pair1, Pair<String,String> pair2){

        return (pair1.premiere.equalsIgnoreCase(pair2.premiere))
                && (pair1.deuxieme.equalsIgnoreCase(pair2.deuxieme));
    }

    /** Demande a l'utilisateur deux phrases a comparer, une distance de comparaison, et affiche un indice de
     *  similitude
     *
     *  Demande a l'utilisateur deux phrases (valide si celles-ci sont non-vide), demande a l'utilisateur une distance
     *  (valide que c'est un entier > 0 ou -1). Affiche l'indice de similitude ou un message d'erreur (si au moins une
     *      des phrases a moins de 2 mots)
     *
     * @param args
     */
    public static void main (String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String phrase1 = null;
        String phrase2 = null;
        int distanceMax = -5;

        do {
            System.out.print(MSG_SOLL_PHRASE1);
            try {
                phrase1 = br.readLine();
                System.out.print(MSG_SOLL_PHRASE2);
                phrase2 = br.readLine();
            } catch (IOException e) {
                System.err.println(MSG_ERR_INPUT);
            }
        }while(phrase2 == null || phrase1 == null);

        do{
            System.out.print(MSG_SOLL_DISTANCE);
            try{
                distanceMax = Integer.parseInt(br.readLine());
                if(distanceMax < 1 && distanceMax != -1){
                    System.out.println(MSG_ERR_DISTANCE);
                }
            }catch(IOException e) {
                System.out.println(MSG_ERR_INPUT);
            }catch(NumberFormatException e){
                System.out.println(MSG_ERR_DISTANCE);
            }
        }while(distanceMax < 1 && distanceMax != -1);

        try {
            System.out.println(comparerPhrases(phrase1, phrase2, distanceMax));
        }catch(ExceptionBigrammeImpossible e){
            System.err.println(MSG_ERR_PHRASE_COURTE);
        }

    }
}
