import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;

public class DSA{
    public static BigInteger p,q,alfa,beta,a,h,gama,delta;

    public static void KeyGenerator(){ //generate p and q
        q=new BigInteger(160,100,new Random()); //we get a random prime number of 160 bits, and we search for p based on this number
       // System.out.println(q+" "+q.bitLength());
        BigInteger result=new BigInteger(863,new Random()).setBit(863); //this will choose a random number between 0 and 2^863, and it sets the bit 863 (the left most bit) to 1 so we get 864 bits
        result=result.multiply(q).add(BigInteger.ONE);
        while(!(result.bitLength()==1024 && result.isProbablePrime(100))){ //We check if the number that we currently have has a length of 1024 bits and if it is a prime number with a probability of 1 - (1/2)^100
            result=new BigInteger(863,new Random()).setBit(863);
            result=result.multiply(q).add(BigInteger.ONE);
        }
        p=result;

        do{ //calculations for alfa and beta and our private key a
        h=new BigInteger(1024, new Random());
        h=h.mod(p);
        BigInteger potenca=p.subtract(BigInteger.ONE);
        potenca=potenca.divide(q);
        alfa=h.modPow(potenca,p);
        }while(h.compareTo(BigInteger.ZERO)==0 || alfa.compareTo(BigInteger.ONE)==0);

        a=new BigInteger(160, new Random());
        a=a.mod(q);

       beta=alfa.modPow(a,p);
    }

    public static BigInteger[] signature(BigInteger message){ //we will return 2 big integers, gama and delta, which will represent our signature of the message
        do {
            BigInteger k = new BigInteger(160, new Random());
            k = k.mod(q);
            gama = alfa.modPow(k, p);
            gama = gama.mod(q);
            BigInteger inverseOfk = k.modInverse(q);
            String hash = getHash(message.toString());
            BigInteger h1 = new BigInteger(hash, 16);
            delta = h1.add(a.multiply(gama));
            delta = inverseOfk.multiply(delta);
            delta = delta.mod(q);
        }while(gama.compareTo(BigInteger.ZERO)==0 || delta.compareTo(BigInteger.ONE)==0);

        BigInteger[] result=new BigInteger[2];
        result[0]=gama;
        result[1]=delta;
        return result;

    }
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

    public static void main(String[] args){
        BigInteger message = new BigInteger("186470169139517660561668745626791725958553393871"); //the two numbers from SHA-1 separated by space
        KeyGenerator();
        BigInteger[] result=signature(message);

        System.out.println(a);
        System.out.println(p);
        System.out.println(q);
        System.out.println(alfa);
        System.out.println(beta);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }
}