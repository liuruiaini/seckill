package com.javasm.util;

import java.util.ArrayList;
import java.util.List;

public  class StringUtil {
    public static List<Integer> StringToIntergerList(String str){
        String substring = str.substring(1, str.length() - 1);
        System.out.println(substring);
        String[] split = substring.split(",");
        List<Integer> list=new ArrayList<>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }
    public static List<String> StringToList(String str){
        String substring = str.substring(1, str.length() - 1);
        System.out.println(substring);
        String[] split = substring.split(",");
        List<String> list=new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }

        return list;
    }
}
