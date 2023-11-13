import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap; // Holds each possible prefix for each term, the key is a prefix value = weightsorted list
    private int mySize;
    private Term[] myTerms;
	

    /**
	 * Given arrays of words and weights, initialize myTerms to a corresponding
	 * array of Terms sorted lexicographically.
	 * 
	 * This constructor is written for you, but you may make modifications to
	 * it.
	 * 
	 * @param terms
	 *            - A list of words to form terms from
	 * @param weights
	 *            - A corresponding list of weights, such that terms[i] has
	 *            weight[i].
	 * @return a BinarySearchAutocomplete whose myTerms object has myTerms[i] =
	 *         a Term with word terms[i] and weight weights[i].
	 * @throws NullPointerException if either argument passed in is null
	 */
    public HashListAutocomplete(String[] terms, double[] weights) {//done
        if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
		
		initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (k == 0) {//k has to be reall weighted number
			new ArrayList<>();
		}
        if (prefix.length() > MAX_PREFIX){
            prefix = prefix.substring(0,MAX_PREFIX);
        }
        if (myMap.containsKey(prefix)){
            List<Term> all = myMap.get(prefix);
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
        }
        else{
            return new ArrayList<>();
        }
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        if (terms.length != weights.length){
            throw new IllegalArgumentException("Must be the same amount of terms and weights");
        }
        myMap = new HashMap<String,List<Term>>();

        for (int i = 0; i < terms.length; i++) {
            Term newTerm = new Term(terms[i],weights[i]);
            int newTermlength = terms[i].length();
            String currentword = terms[i];
            mySize += BYTES_PER_CHAR * newTermlength  + BYTES_PER_DOUBLE;

            for(int k = 0; k <= MAX_PREFIX; k++){
                if(newTermlength < k){
                    break;
                }
                if(myMap.containsKey(currentword.substring(0,k))){
                    myMap.get(currentword.substring(0,k)).add(newTerm);
                }                else {
                    List<Term> newtermlist = new ArrayList<Term>();
                    newtermlist.add(newTerm);
                    myMap.put(terms[i].substring(0,k), newtermlist);
                    mySize += BYTES_PER_CHAR * currentword.substring(0,k).length();;
                }
                
            }

        }
        
        for(String key: myMap.keySet()){
            List<Term> unsorted = myMap.get(key);
            Collections.sort(unsorted, Comparator.comparing(Term::getWeight).reversed());
        }
        
    }



    @Override
    public int sizeInBytes() {
        return mySize;
    }  
}

