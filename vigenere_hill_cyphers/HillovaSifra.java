import java.util.*;

public class HillovaSifra{
    public static void printInt(int[][] tab) {
        System.out.println("Integer table:");
        for (int i = 0; i < tab.length; i++){
            for (int j = 0; j < tab[i].length; j++)
                System.out.print(tab[i][j] + " ");
            System.out.println();
        }
    }
    public static int[][] CharToInt(String message){
        int[][] text=new int[2][message.length()/2];
        int index=0;
        for(int j=0; j<message.length()/2; j++)
            for(int i=0; i<2; i++){
                text[i][j]=(int)(message.charAt(index)-'a');
                index++;
                if(index>=message.length())
                    break;
            }
        return text;
    }
    public static int[] CharToInteger(String b){
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
    public static int[][] matrixMultiplication(int[][] m1, int[][] m2){
        int[][] rez=new int[m1.length][m2[0].length];
        int sum=0;
        for(int i=0;i<m1.length; i++)
            for(int j=0; j<m2[0].length; j++){
                for(int k=0; k<m2.length; k++)
                    sum=(sum+(m1[i][k]*m2[k][j])%26)%26;
                rez[i][j]=sum;
                sum=0;
            }
        return rez;
    }
    public static int inverseNumber(int n){
        for(int i=1; i<26; i++)
            if((n*i)%26==1)
                return i;
         return -1;
    }
    public static int[][] inverseMatrix(int[][] m){
        int det=((m[0][0]*m[1][1]%26-m[0][1]*m[1][0]%26)+26)%26; //it must hold that gcd(det,26)=1
        det=inverseNumber(det);

        int[][] inverse=new int[2][2];
        inverse[0][0]=m[1][1];
        inverse[0][1]=(-m[0][1]+26)%26;
        inverse[1][0]=(-m[1][0]+26)%26;
        inverse[1][1]=m[0][0];

        for(int i=0;i<2; i++)
            for(int j=0; j<2; j++)
                inverse[i][j]=(inverse[i][j]*det)%26;
        //printInt(inverse);
        return inverse;
    }
    public static String Encrypt(int[][] plaintext, int[][] key){
        int[][] rez=matrixMultiplication(key,plaintext);
        char[] kriptogram=new char[rez.length*rez[0].length];
        int k=0;
        for(int i=0; i<rez[0].length; i++)
            for(int j=0; j<2; j++){
                kriptogram[k]=(char)(rez[j][i]+'a');
                k++;
                if(k>=kriptogram.length)
                    break;
            }
        return new String(kriptogram);
    }
    public static String Decrypt(int[][] cryp, int[][]key){
        key=inverseMatrix(key);
        int[][] rez=matrixMultiplication(key,cryp);
        char[] kriptogram=new char[rez.length*rez[0].length];
        int k=0;
        for(int i=0; i<rez[0].length; i++)
            for(int j=0; j<2; j++){
                kriptogram[k]=(char)(rez[j][i]+'a');
                k++;
                if(k>=kriptogram.length)
                    break;
            }
        return new String(kriptogram);
    }
    public static double[] frequencies(String message){
        double[] f=new double[26];
        for(int i=0; i<message.length(); i++){
            f[(int)(message.charAt(i)-'a')]++;
        }
        for(int i=0; i<26; i++)
            f[i]=(f[i]/message.length())*100;
        return f;
    }
    public static double calculate(double[] f1, double[] f2){ //returns the sum of the differences between the frequencies of each letter in the ciphertext and in the alphabet
        int sum=0;
        for(int i=0; i<26; i++)
            sum+=Math.pow(f2[i]-f1[i],2);
        return sum;
    }
    public static String breakHillCipher(int[][] cipher){
        int[][] key1=new int[1][2]; //d,e - first row of inverse matrix of key
        int[][] key2=new int[1][2]; //f,g - second row of index matrix of key
        double[] alphabet={8.167,1.492,2.202,4.253,12.702,2.228,2.015,6.094,6.966,0.153,1.292,4.025,2.406,6.749,7.507,1.929,0.095,5.987,6.327,9.356,2.758,0.978,2.560,0.150,1.994,0.077};
        //-------------------------------- d,e:
        double[][] sum1=new double[26][26];
        int[][] rez=new int[cipher.length][cipher[0].length];
        for(int i=0; i<26; i++){
            for(int j=0; j<26; j++){
                key1[0][0]=i;
                key1[0][1]=j;
                rez=matrixMultiplication(key1,cipher);
                double[] freq=frequencies(IntToChar(rez[0]));
                sum1[i][j]=calculate(freq,alphabet);
                if(i==0&&j==0 || i==3&&j==8)
                    System.out.println(sum1[i][j]);
            }
        }
        int d=0; //indexes in the 2d array sum1, which represent the values of the first row of the inverse matrix of the key
        int e=0;
        double minsum=sum1[0][0];
        for(int i=0; i<26; i++)
            for(int j=0; j<26; j++)
                if(minsum>sum1[i][j]){
                    minsum=sum1[i][j];
                    d=i;
                    e=j;
                }
        //-------------------------------- f,g:
        double[][] sum2=new double[26][26];
        int[][] rez2=new int[cipher.length][cipher[0].length];
        for(int i=0; i<26; i++){
            for(int j=0; j<26; j++){
                key2[0][0]=i;
                key2[0][1]=j;
                rez2=matrixMultiplication(key2,cipher);
                double[] freq2=frequencies(IntToChar(rez2[0]));
                sum2[i][j]=calculate(freq2,alphabet);
            }
        }
        int f=0; //indexes in the 2d array sum2, which represent the values of the second row of the inverse matrix of the key
        int g=0;
        double minsum2=sum2[0][0];
        for(int i=0; i<26; i++)
            for(int j=0; j<26; j++)
                if(minsum2>sum2[i][j]){
                    minsum2=sum2[i][j];
                    f=i;
                    g=j;
                }
        int[][] key=new int[2][2];
        System.out.println();
        System.out.println("d: "+d+", e: "+e+", f: "+f+", g: "+g);
        key[0][0] = d;
        key[0][1] = e;
        key[1][0] = f;
        key[1][1] = g;

        key=inverseMatrix(key);
        return Decrypt(cipher,key);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String message = sc.next(); //reading the plaintext as a string
        String k = sc.next(); //reading the key as a string
        int[][] key = new int[2][2];
        key[0][0] = (int) (k.charAt(0) - 'a');
        key[0][1] = (int) (k.charAt(1) - 'a');
        key[1][0] = (int) (k.charAt(2) - 'a');
        key[1][1] = (int) (k.charAt(3) - 'a');

        if (message.length() % 2 != 0)
            message += 'X'; //if the last letter is big X then it means that it is an extra
                            //letter that was added to make the length divisible by 2
        int[][] plaintext = new int[2][message.length() / 2];
        int index = 0;
        for (int j = 0; j < message.length() / 2; j++)
            for (int i = 0; i < 2; i++) {
                plaintext[i][j] = (int) (message.charAt(index) - 'a');
                index++;
                if (index >= message.length())
                    break;
            }
        System.out.println("Message: " + message);
        printInt(plaintext);
        System.out.println("Key: " + k);
        printInt(key);
        System.out.println("Inverse of the key: ");
        //inverseMatrix(key);
        printInt(inverseMatrix(key));
        String kriptogram = Encrypt(plaintext, key);
        System.out.println("Encrypted message: " + kriptogram);
        System.out.println();
        int[][] krip = CharToInt(kriptogram);
        /*System.out.println("Message: " + kriptogram);
        printInt(krip);
        System.out.println("Key: " + k);
        printInt(key);*/
        String text = Decrypt(CharToInt(kriptogram), key);
        System.out.println("Decrypted message: " + text);
        //System.out.println("Breaking the cipher: " + kriptogram);
        //System.out.println("Decrypted cipher: " + breakHillCipher(krip));*/

    }
}