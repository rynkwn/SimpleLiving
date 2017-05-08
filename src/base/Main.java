package base;

import java.io.*;
import java.util.*;

import item.*;
import entities.*;
import data.*;
import organization.*;
import world.*;

public class Main {

	public static void main(String [] args){
		SpeciesReader.readSpeciesData("src/data/");
		ItemsReader.readItemData("src/data/");
		
		System.out.println(SpeciesReader.debugDump());
		
		System.out.println("\n\n\n");
		System.out.println(ItemsReader.debugDump());
		
		World world = new World(10, 10);
		
		for(int i = 0; i <= 9; i++) {
			Group grp = new Group(world, i, i);
			world.addGroup(grp.id, grp, i, i);
		}
		
		Scanner scan = new Scanner(System.in);
		
		while(!scan.nextLine().equalsIgnoreCase("q")) {
			System.out.println("\n\n\n");
			System.out.println(world.display());
			
			world.turn();
		}
		
		scan.close();
		
		
		/*
		Person tester = new Person();
		
		Clothing shirt = new Clothing("shirt");
		System.out.println("Shirt's toString: " + shirt.toString());
		tester.pickUp(shirt);
		System.out.println(tester.inventory());
		tester.wear(0, 0);
		System.out.println(tester.appendages());
		System.out.println(tester.inventory());
		*/
		
		//Village vl = new Village();
		//vl.iterate();
		
		
		/*
		Tile tile = new Tile();
		Building fact = new Building();
		tile.addFactory(0, 0, fact);
		tile.turn();
		
		tile.localItems.printDetailedContents();
		*/
	}
}
