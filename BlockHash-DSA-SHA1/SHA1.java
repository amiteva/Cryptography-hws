import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;

public class SHA1{
    // Takes text and return its hash (a hexadecimal number as a string)
    public static String getHash(String text) {
            try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(text.getBytes("ascii"));
            return String.format("%040x", new BigInteger(1, md.digest()));
            } catch (Exception e){
            e.printStackTrace();
            }
            return null;
    }

    // Takes hexadecimal number as a string and returns a number in decimal as a string
    public static String toDecimal(String hex) {
            return new BigInteger(hex, 16).toString();
            }

    public static void main(String[] args) {
        Integer start = (int) (Math.random() * ((6000000 - 5061351) + 1)) + 1578700; //When computing Im gonna start searching from this number start,
                                                                                    // so everytime I ran my program, I will not get the same result
                                                                                    // I used the first suitable pair of numbers that I found, that was different from the pairs of the other students
        HashMap<String, Integer> hm = new HashMap<String, Integer>(); //Each pair of numbers that I will generate I will put in a hash map so that i keep track of them: a pair of 11 charachters from the hash and the number whose hash that is
                                                                    // The program stops when it generates a number whose first 11 charachters are already contained in the hash map ---> we found a matching pair!
        Integer rez = null;
        int brojac=0;
        while (rez == null) {
            if(brojac%100==0)
                System.out.println("We are on loop number"+brojac+" "+hm.size());
            String s = Integer.toString(start);
            String hash = getHash(s); //we get the hash of our starting number
            String bits = hash.substring(0, 11);//we take the first 11 charachetr (first 44 bits of the hash)
            rez = hm.get(bits);
            if (rez == null) { //check if the map already contains this hash
                hm.put(bits, start);
                start++;
            } else {
                System.out.println(getHash(Integer.toString(start)));
                System.out.println(getHash(Integer.toString(rez)));

                String result = Integer.toString(start)+" "+Integer.toString(rez); //concatinate the two strings
                System.out.println(start + " " + rez + " " + toDecimal(getHash(result)));
                //rez and start are the pair with the same hashes(the first 44 bits)
            }
            brojac++;
            //System.out.println(s+" "+hash+" "+ start+" "+bits);
        }
    }
}