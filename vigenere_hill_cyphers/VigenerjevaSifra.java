import java.util.*;

public class VigenerjevaSifra{
    //public final double e=12.702; //the letter 'e': is the mostly used letter in the English language

    public static int[] CharToInt(String b){
        int[] word=new int[b.length()];
        for(int i=0; i<b.length(); i++)
            word[i]=(int)b.charAt(i)-'a';
        return word;
    }
    public static String IntToChar(int[] b){
        char[] word=new char[b.length];
        for(int i=0; i<word.length; i++)
            word[i]=(char)(b[i]+'a');
        String besedilo=new String(word);
        return besedilo;
    }
    public static void printString(String b){
        System.out.print("String: ");
        for(int i=0; i<b.length(); i++)
            System.out.print(b.charAt(i));
        System.out.println();
    }
    public static void printArray(int[] b){
        System.out.print("Array: ");
        for(int i=0; i<b.length; i++)
            System.out.print(b[i]+" ");
        System.out.println();
    }
    public static String Encrypt(String b, String k){
        int[] word1=CharToInt(b);
        int[] word2=CharToInt(k);
        IntToChar(word1);
        int i=0;
        for(int l=0; l<b.length()/k.length(); l++)
            for(int j=0; j<k.length(); j++){
                word1[i]=(word1[i]+word2[j])%26;
                i++;
                if(i>=b.length())
                    break;
            }
        return IntToChar(word1);
    }

    public static String Decrypt(String b, String k){
        int[] word1=CharToInt(b);
        int[] word2=CharToInt(k);
        IntToChar(word1);
        int i=0;
        for(int l=0; l<b.length()/k.length(); l++)
            for(int j=0; j<k.length(); j++){
                word1[i] = ((word1[i] - word2[j]) + 26) % 26;
                i++;
                if (i >= b.length())
                    break;
            }
        return IntToChar(word1);
    }

    public static int[] frequencies(String message){
        int[] f=new int[26];
        for(int i=0; i<message.length(); i++){
            f[(int)(message.charAt(i)-'a')]++;
        }
        return f;
    }
    public static double indexSovpadanja(int[] f, int[] m){ //f-frequency table of the message, m-integer values of each letter of the message
        double sum=0;
        for(int i=0; i<26; i++)
            sum+=Math.pow(f[i],2);
        return sum/Math.pow(m.length,2);
    }
    public static int divisors(int n){ //returns the number of divisors of a number n -needed for length of an array
        int counter=0;
        for(int i=1; i<=n; i++)
            if(n%i==0)
                counter++;
        return counter;
    }
    public static int minimal(double[] razliki){ //it returns the index of the minimal difference
        int i=0;
        double min=razliki[0];
        for(int j=0; j<razliki.length; j++)
            if(min>razliki[j]){
                i=j;
                min=razliki[j];
            }
        return i;
    }
    public static int findLengthOfKey(String message, int[] frequencies) {
        int[] text = CharToInt(message);
        int l = text.length;
        double[] razliki = new double[divisors(l)]; //contains the differences between each sum and 0.065 (index sovpadanja of the English alphabet), we want the minimal sum
        int counter = 0;
        //double[] errors=[l/9]
        for (int i = 1; i < l/10; i++) {
            int[] y = new int[l / i];
            int k = 0;
            for (int j = 0; j < l; j+=i) {
                y[k] = text[j];
                k++;
                if (k >= l / i) //just for null pointer exception
                    break;
            }
            int[] f = frequencies(new String(IntToChar(y)));
            double indexC = indexSovpadanja(f, y);
            System.out.println("Ic: " + indexC);
            razliki[counter] = Math.abs(indexC - 0.065);
            counter++;
        }
        return minimal(razliki);
    }
    //returns the lenght of the key


    public static int maxFreq(int[] frequencies){ //a function that returns the index of the maximal frequency in an array
        int i=0;
        int max=frequencies[0];
        for(int j=0; j<26; j++)
            if(max<frequencies[j]){
                max=frequencies[j];
                i=j;
            }
        return i;
    }
    public static char breakingOneCezarjevaSifra(int[] cipher){
        int[] f=frequencies(IntToChar(cipher));
        int x=maxFreq(f); //x is a number between 0 and 25 which is a cipher for the letter 'e' in the english alphabet
        int ki=(x+22)%26;
        return (char)(ki+'a');
    }
    public static String breakingCiphertext(String cipher){
        int n=findLengthOfKey(cipher, frequencies(cipher)); //length of the key
        int l=cipher.length();
        int[] ciphertext=CharToInt(cipher);
        int[][] y=new int[n][l/n]; //here each row will be the integer values of the letters of a substring that are encrypted with the same Caesar cipher
        for(int i=0; i<y.length; i++){
            int k=0; //index of a column for row i
            for(int j=i; j<l; j+=n){ //go through the whole ciphertext
                y[i][k]=ciphertext[j]; //for yi take each n-th letter
                k++;
                if(k>=l/n) //just for avoiding null pointer exception
                    break;
            }
        }
        //We divided the cypher into n-many subcyphers that are encrypted with the same caesar cipher. Now we need to break n-many caesar ciphers, so for each subcipher separately
        char[] key=new char[n]; //each element of the char array key is one letter of the key that we are searching for
        for(int i=0; i<n; i++) //we go through all rows of y
            key[i]=breakingOneCezarjevaSifra(y[i]);

        System.out.println("Key with breaking the cipher: "+new String(key));
        return Decrypt(cipher, new String(key));
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String plaintext=sc.next();
        String key=sc.next();
        String cryptogram=Encrypt(plaintext,key);
        String message=Decrypt(cryptogram,key);
        System.out.println("Plaintext: "+plaintext);
        System.out.println("Key: "+key);
        System.out.println("Cryptogram of the message: "+cryptogram);
        System.out.println("Message with decryption function: "+message);
        System.out.println();
        /*System.out.println("Breaking Vigenerjeva Sifra: ");
        System.out.println("Cipher: " + cryptogram);
        System.out.println("Decrypted message: "+breakingCiphertext(cryptogram));*/
    }
}