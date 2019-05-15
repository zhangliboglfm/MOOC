package com.myself.JvmMomeory;

import java.util.ArrayList;
import java.util.List;

public class TestGc {
    public static TestGc HOOK;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        TestGc.HOOK = this;
        System.out.println("finalize");
    }

    /*
          输出结果
    finalize
    HOOK is alvie
    HOOK is dead
     */
    public static void main(String[] args) throws Exception {
            List<String> list = new ArrayList<String>();
            int i=0;
            while(true){
                list.add(String.valueOf(i++).intern());
            }

        }

}
