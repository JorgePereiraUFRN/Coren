package Coren;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		Random r = new Random(0);
		
		for(int i = 0; i < 10;i++) {
			System.out.println(r.nextBoolean());
			System.out.println(r.nextInt(2));
			System.out.println(r.nextInt(200));
		}
		

	}

}
