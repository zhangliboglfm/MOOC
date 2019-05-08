package com.myself.computerThinking.customException;

import javax.sound.midi.Soundbank;

public class myException extends RuntimeException {


   public  myException(){
       super();
   }
   public  myException(String message){
       super(message);
   }

    public static void main(String[] args) {
       try {
           getDivision(1,2);
           System.out.println(123);     //不执行
       }catch (Exception e){
           System.out.println(e.getMessage());  // “抛出异常”
       }
    }

    private static   int getDivision(int a,int b){
        throw new myException("抛出异常");
    }
}

