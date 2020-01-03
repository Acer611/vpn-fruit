package com.dragon.fruit.common.utils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 邀请码生成工具类
 */
public class ShareCode {

    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r = new char[]{'Q', 'W', 'E', '8', 'A', 'S', '2', 'D', 'Z', 'X', '9', 'C', '7', 'P', '5', 'I', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'l', 'T', 'N', '6', 'B', 'G', 'H'};

    /** (不能与自定义进制有重复) */
    private static final char b = 'O';

    /** 进制长度 */
    private static final int binLen = r.length;

    /** 序列最小长度 */
    private static final int s = 4;

    /**
     * 根据ID生成六位随机码
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf=new char[32];
        int charPos=32;

        while((id / binLen) > 0) {
            int ind=(int)(id % binLen);
            buf[--charPos]=r[ind];
            id /= binLen;
        }
        buf[--charPos]=r[(int)(id % binLen)];
        String str=new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < s) {
            StringBuilder sb=new StringBuilder();
            sb.append(b);
            Random rnd=new Random();
            for(int i=1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str+=sb.toString();
        }
        return str;
    }

    public static long codeToId(String code) {
        char chs[]=code.toCharArray();
        long res=0L;
        for(int i=0; i < chs.length; i++) {
            int ind=0;
            for(int j=0; j < binLen; j++) {
                if(chs[i] == r[j]) {
                    ind=j;
                    break;
                }
            }
            if(chs[i] == b) {
                break;
            }
            if(i > 0) {
                res=res * binLen + ind;
            } else {
                res=ind;
            }
        }
        return res;
    }


    /**
     * 生成四位的验证码
     * @return
     */
    public static  String generateCodeFour(){
        String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
                "8", "9", "0","1"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(3, 9);

        return result;
    }

    public static void main(String[] args) {
        String s = toSerialCode(18301403656L);
        System.out.println("....."+s);
        System.out.println(codeToId(s));
        LocalDateTime dateTime =  LocalDateTime.now();
        Date date = new Date();
        System.out.println("当前时间 java 8：" + dateTime);
        System.out.println("当前时间 java ：" + date);

        String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z" };
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);

        System.out.println("四位验证码：" + result);

    }
}