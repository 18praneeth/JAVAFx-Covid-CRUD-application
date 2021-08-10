package controller;


import java.util.*;

class HelloWorld {
    public static void main(String[] args) {


        List<String> arrayList = new ArrayList<String>();
        arrayList.add("a");
        arrayList.add("b");

        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            arrayList.add("c");
        }
    }
}