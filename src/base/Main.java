package base;

import Item.*;
import entities.*;

public class Main {

	public static void main(String [] args){
		Person tester = new Person();
		Clothing shirt = new Clothing("shirt");
		System.out.println("Shirt's toString: " + shirt.toString());
		tester.pickUp(shirt);
		System.out.println(tester.inventory());
		tester.wear(0, 0);
		System.out.println(tester.appendages());
		System.out.println(tester.inventory());
	}
}
