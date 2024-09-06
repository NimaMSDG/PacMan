package com.example.javafxtest;

public class Map {
    static double BlockSize = 25;
    String mapString = "11111111111111111111111111111\n" +
            "14000000000000000000000000041\n" +
            "10111111011111111111011111101\n" +
            "10000041000000000000014000001\n" +
            "10111101011111011111010111101\n" +
            "10000101000000000000010100001\n" +
            "10110101011111011111010101101\n" +
            "10000100000000000000000100001\n" +
            "10110101010111211101010101101\n" +
            "100000010101g333g101010101101\n" +
            "101101010101g333g101010000001\n" +
            "10110101010111111101010101101\n" +
            "10000100000000000000000100001\n" +
            "10110101011111011111010101101\n" +
            "10000101000000000000010100001\n" +
            "10111101011111011111010111101\n" +
            "10000041000000000000014000001\n" +
            "10111111011111111111011111101\n" +
            "14000000000000p00000000000041\n" +
            "11111111111111111111111111111\n";//20;

    int[][] array(){return getMapByString(mapString);}


    int[][] getMapByString(String map){
        String[] mapSplit = map.split("\n");
        char[][] chars = new char[20][29];
        for (int j = 0; j < 20; j++) {
            chars[j]=mapSplit[j].toCharArray();
        }
        int[][] mapInt = new int[20][29];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 29; j++) {
                if (chars[i][j]=='1') mapInt[i][j]=1;
                else mapInt[i][j]=0;
            }
        }

        return mapInt;
    }
}
