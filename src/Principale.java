import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class Principale <E> {
    static final private String MSG_SOLL_PHRASE1 = "Veuillez ecrire la premiere phrase a comparer : ";
    static final private String MSG_SOLL_PHRASE2 = "Veuillez ecrire la deuxieme phrase a comparer : ";
    static final private String MSG_SOLL_DISTANCE =
            "Veuillez ecrire la distance maximale de comparaison (-1 pour distance infinie): ";
    static final private String MSG_ERR_INPUT = "Une erreur est survenue, on reccomence";
    static final private String MSG_ERR_DISTANCE = "La distance doit etre -1 ou plus grande ou egale a 0";

    private static double comparerPhrases(String phrase1, String phrase2, int distance ){
        String[] phrase1separee = separerMots(phrase1);
        String[] phrase2separee = separerMots(phrase2);
        ArrayList<Pair<String, String>> phrase1Bigramme = new ArrayList<>();
        ArrayList<Pair<String, String>> phrase2Bigramme = new ArrayList<>();

        if(distance == -1){
            distance = phrase1separee.length;
        }
        genererBigramme(phrase1separee,phrase1Bigramme, distance);

        if(distance == -1){
            distance = phrase2separee.length;
        }
        genererBigramme(phrase2separee,phrase2Bigramme, distance);

        return comparerDifferenceBigramme(phrase1Bigramme,phrase2Bigramme);
    }

    private static String[] separerMots (String phrase){
        String[] listeMots;
        phrase = phrase.toLowerCase();
        listeMots = Pattern.compile("[^a-z]").split(phrase);

        return listeMots;

    }

    private static double comparerDifferenceBigramme(ArrayList<Pair<String,String>> set1, ArrayList<Pair<String,String>> set2){
        double cpt = 0;
        double s1 = set1.size();
        double s2 = set2.size();
        int i = 0;
        //compter les bigrammes concordants
        while(i < set1.size()){

        }


        return (2*(cpt/s1)*(cpt/s2))/(cpt/s1+cpt/s2);
    }

    private static void genererBigramme(String[] phrase, ArrayList<Pair<String,String>> bigrammes, int delta){
        GenerateurBiGramme<String> generateur = new GenerateurBiGramme<>();
        for(int i = 0; i < phrase.length; i ++){
            generateur.add(phrase[i]);
        }

        Iterator<Pair<String, String>> it = generateur.iterator(delta);

        while(it.hasNext()){
            bigrammes.add(it.next());
        }

    }


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
                if(distanceMax < 0 && distanceMax != -1){
                    System.out.println(MSG_ERR_DISTANCE);
                }
            }catch(IOException e) {
                System.out.println(MSG_ERR_INPUT);
            }catch(NumberFormatException e){
                System.out.println(MSG_ERR_DISTANCE);
            }

        }while(distanceMax < 0 && distanceMax != -1);

        System.out.println(comparerPhrases(phrase1, phrase2, distanceMax));


    }
}
