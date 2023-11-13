import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    public int compare(Term v, Term w) {
        if (myPrefixSize == 0){
            return 0;
        }
        String word1 = v.getWord();//word1
        String word2 = w.getWord();//word2

        int compare = myPrefixSize; 
        boolean same = true;

        if (word1.length() < myPrefixSize || word2.length() < myPrefixSize){ // if either word is less than the prefixsize only check for up to the lenght of that word
            if (word1.length() < word2.length()){
                compare = word1.length();
            }
            else if (word1.length() > word2.length()){
                compare = word2.length();
            }
        }

    
        for (int i = 0; i < compare && i < word1.length() && i < word2.length(); i++) {
            if (word1.charAt(i) < word2.charAt(i)) {//if a letter is diff ret
                same = false;
                return -1;
            } else if (word1.charAt(i) > word2.charAt(i)) { //
                same = false;
                return 1;
            }
            if (word1.charAt(i) == word2.charAt(i) && i+1 == myPrefixSize && same == true){//if last letters are same, stil the same
                return 0;//if 
            }
        }

        if (word1.length() < word2.length() && same == true){
            return -1;
        }
        else if (word1.length() > word2.length() && same == true){
            return 1;
        }
        else{
            return 0;
        }
        
        
    }
}
