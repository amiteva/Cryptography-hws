import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;

public class BlockHash{
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
            String firstline = "8859787 6925873 186470169139517660561668745626791725958553393871";
            String secondline = "63170350 570276095991185825884860524538210572279389920839 247702896200342101121950758459142702711754687191";
            String thirdline = "0000000e39dc77c03929e57ea7967815b3bd74fc";
            String result;
            Integer start = 1234567891; //Our initial arbitrary text
            do{
                String block = firstline+"\n"+secondline+ "\n"+thirdline+"\n"+Integer.toString(start); //concatinate all lines together
                result = getHash(block); //get the hash from the previoud 4 lines in your block
                start+=10; //go to the next random text
            }while(!result.substring(0,7).equals("0000000")); //repeat until you dont find such an arbitrary message that the hash of it concatinated with the other 3 lines
                                                                //contains 7 zeroes on the beginning
            System.out.println(result);
        }
}