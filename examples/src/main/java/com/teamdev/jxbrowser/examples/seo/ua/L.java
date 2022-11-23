package com.teamdev.jxbrowser.examples.seo.ua;


import java.util.Arrays;
import java.util.Random;

public class L
{
   public static int randomNumber(int START,int END){
       if(END<=START)
           return START;
       Random random = new Random();
       return random.nextInt(END - START + 1) + START;
   }

    public static int randomNumber(Integer... e){
        return Arrays.asList(e).get(L.randomNumber(0,e.length-1));
    }



    public static long rm(){
       //6291457+>128+128
        //2097408+128+128
        //+256
       int rs=L.randomNumber(2,1000);
       long temp=3L;
       long lastN=56623104L;
       long c=2097152;
        for(int i=1;i<rs;i++){
            temp=temp*3;
            lastN=temp;
            System.out.println(Math.abs(temp*c));
        }
       return Math.abs(lastN*c)+L.randomNumber(0,1);
   }




}
