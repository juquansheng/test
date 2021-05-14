package com.malaxiaoyugan.test.Demotest;

public class Test2 {
    public static void main(String[] args) {
        System.out.println(solution(-999));
    }

    public static int solution(int N) {
        // write your code in Java SE 8
        int result = 0;
        if (N < 0){
            int abs = Math.abs(N);
            String s = String.valueOf(abs);
            for(int i = 0; i < s.length(); i++){
                char ch = s.charAt(i);
                int anInt = Integer.parseInt(String.valueOf(ch));
                if (anInt >= 5){
                    StringBuffer sb = new StringBuffer(s);
                    sb.insert(i,"5");
                    result = Integer.parseInt(sb.toString())*-1;
                    break;
                }
            }

        }else if (N == 0){
            return 50;
        }else {
            String s = String.valueOf(N);
            for(int i = 0; i < s.length(); i++){
                char ch = s.charAt(i);
                int anInt = Integer.parseInt(String.valueOf(ch));
                if (anInt <= 5){
                    StringBuffer sb = new StringBuffer(s);
                    sb.insert(i,"5");
                    result = Integer.parseInt(sb.toString());
                    break;
                }
            }
        }
        return result;
    }
}
