package com.airbnb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class test {
    public static void main(String[] args){
        List<Object> l=new ArrayList<Object>();
        l.add(1);
        l.add(2);
        l.add(3);
        List<Object> l1=new ArrayList<Object>();
        l1.add(3);
        l1.add(4);
        l1.add(5);
        List<Object> l2=new ArrayList<Object>();
        l2.add(5);
        l2.add(6);
        l2.add(7);

        l.add(l1);
        l.add(l2);

        System.out.println(l);


    }
}
