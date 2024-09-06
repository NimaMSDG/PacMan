package com.example.javafxtest;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HunterGhostController {

    public static class radepa {
        public int i,j;
        radepa(){
            i=0;
            j=0;
        }
    }


    static boolean check(ArrayList<radepa> rad, int x, int y){
        for (int i=0;i<rad.size();i++){
            if (rad.get(i).i == x && rad.get(i).j == y){
                return false;
            }
        }
        return true;
    }
    static void minwaytojavaher(int n,int m,int map[][],int minway[],int min[],ArrayList<radepa> rad,radepa[] radepa){

        int size = rad.size();
        int i = rad.get(size-1).i,j = rad.get(size-1).j;
        int ip = rad.get(size-2).i,jp = rad.get(size-2).j;

        //System.out.println(i+"---"+j);
        if (radepa[0].i!=0 && radepa[0].j!=0) {
            return;}


        if (map[i][j]==9){
            radepa[0]=rad.get(2);
            return;
        }


        if (i>0 && (map[i - 1][j] != 1) && i-1!=ip && check(rad,i-1,j)) {
            radepa u = new radepa();
            u.i=i-1;
            u.j=j;
            rad.add(u);
            minway[0]++;minwaytojavaher(n,m,map,minway,min,rad,radepa);}
        if (j>0 && (map[i][j - 1] != 1) && j-1!=jp && check(rad,i,j-1)) {
            radepa u = new radepa();
            u.i=i;
            u.j=j-1;
            rad.add(u);
            minway[0]++;minwaytojavaher(n,m,map,minway,min,rad,radepa);}
        if (j<m-1 && (map[i][j + 1] != 1) && j+1!=jp && check(rad,i,j+1)) {
            radepa u = new radepa();
            u.i=i;
            u.j=j+1;
            rad.add(u);
            minway[0]++;minwaytojavaher(n,m,map,minway,min,rad,radepa);}
        if (i<n-1 &&(map[i + 1][j] != 1)&& i+1!=ip && check(rad,i+1,j)) {
            radepa u = new radepa();
            u.i=i+1;
            u.j=j;
            rad.add(u);
            minway[0]++;minwaytojavaher(n,m,map,minway,min,rad,radepa);}
        //if (i==0 && j ==3){
        //  System.out.println("bubhbj");
        //}
        minway[0]--;


        //System.out.print("iiiiii");
        rad.remove(size-1);

    }

    public static Direction huntingMove(Ghost ghost,int[][] map,int ip,int jp){

        int n;
        int m;

        int[][] Radar = radar(map,ghost.i(),ghost.j(),ip,jp);


        n=Radar.length;
        m=Radar[0].length;



        ArrayList<radepa> rads = new ArrayList<radepa>();
        radepa x = new radepa();
        x.i = -1;
        x.j = -1;
        rads.add(x);
        radepa y = new radepa();
        y.i = 8;
        y.j = 8;
        rads.add(y);
        //System.out.println(ghost.j()+"------"+ghost.i());

        radepa[] Radepa = new radepa[]{new radepa()};

        int[] min,minway;
        min = new int[]{n * m};
        minway = new int[]{0};
        minwaytojavaher(n,m,Radar,minway,min,rads,Radepa);
        System.out.println(Radepa[0].i+"---"+Radepa[0].j);

        System.out.println(Radar[Radepa[0].i][Radepa[0].j]);

        if (Radepa[0].j==8 && Radepa[0].i== 8-1){
            return Direction.Up;
        }
        if (Radepa[0].j==8 && Radepa[0].i== 8+1){
            return Direction.Down;
        }
        if (Radepa[0].i==8 && Radepa[0].j== 8-1){
            return Direction.Left;
        }
        if (Radepa[0].i==8 && Radepa[0].j==8+1){
            return Direction.Right;
        }
        return Direction.None;

    }

    public static int[][] radar(int[][] map,int x,int y,int ip,int jp){
        int h=0;
        int s=0;
        int w =0;
        boolean pacmanIn = false;
        for (int j=y-8;j<=y+8 && j<21;j++){
            if (j<0) continue;
            h++;
            for (int i=x-8; i <= x+8&& i<19; i++) {
                if (i<0) continue;
                s++;
                if (i==ip&&j==jp){
                    pacmanIn=true;
                }
            }
        }
        w=s/h;
        //if (!pacmanIn) return null;
        int di = x-8;
        int dj = y-8;
        if (di<0) di =0 ;
        if (dj<0) dj=0;

        int[][] radar = new int[17][17];
        for (int j=y-8;j<=y+8 && j<21;j++){
            if (j<0) continue;
            for (int i=x-8; i <= x+8&& i<19; i++) {
                if (i<0) continue;
                //System.out.println(j+"---"+i);
                radar[j-dj][i-di]=map[j][i];
                if (i==ip&&j==jp){
                    radar[j-dj][i-di]=9;
                }
            }
        }
        System.out.println(Arrays.deepToString(radar));
        return radar;
    }
}
