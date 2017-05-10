package base;

import java.io.*;
import java.util.*;

import item.*;
import entities.*;
import data.*;
import organization.*;
import world.*;

public class Main {

	public static void main(String [] args) throws Exception{
		SpeciesReader.readSpeciesData("src/data/");
		ItemsReader.readItemData("src/data/");
		
		System.out.println(SpeciesReader.debugDump());
		
		System.out.println("\n\n\n");
		System.out.println(ItemsReader.debugDump());
		
		World world = new World(10, 10);
		
		for(int i = 0; i <= 9; i += 2) {
			Group grp = new Group(world, i, i);
			
			Entity a = SpeciesReader.getSpecies("Locust").makeInstanceOf();
			Entity b = SpeciesReader.getSpecies("Locust").makeInstanceOf();
			Entity c = SpeciesReader.getSpecies("Locust").makeInstanceOf();
			Entity d = SpeciesReader.getSpecies("Locust_Minor").makeInstanceOf();
			
			grp.addMember(a);
			grp.addMember(b);
			grp.addMember(c);
			grp.addMember(d);
			
			grp.inventory.add(ItemsReader.makeComponent("Bud", 120));
			
			world.addGroup(grp.id, grp, i, i);
		}
		
		Scanner scan = new Scanner(System.in);
		
		//while(!scan.nextLine().equalsIgnoreCase("q")) {
		while(true){
			Thread.sleep(1000);
			System.out.println("\n\n\n");
			System.out.println(world.display());
			
			world.turn();
		}
		
		//scan.close();
		
		
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
