/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ead
 */
public class Class {
public static void main(String[] args) {


    int[] vetor = new int[5];
		
		try {
			for(int i = 0; i < 10; i++);
			vetor[i] = i;
			System.out.println(i);
		}catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Posição fora do Index:" + i);
}
}
