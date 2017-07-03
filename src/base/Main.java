package base;

import java.io.*;
import java.util.*;

import item.*;
import entities.*;
import data.*;
import organization.*;
import world.*;
import util.*;
import behavior.*;

public class Main {

	public static void main(String [] args) throws Exception{
		
		SpeciesReader.readSpeciesData("src/data/");
		ItemsReader.readItemData("src/data/");
		GroupBehaviorReader.readBehaviorData("src/data");
		
		
		System.out.println(SpeciesReader.debugDump());
		
		System.out.println("\n\n\n");
		System.out.println(ItemsReader.debugDump());
		
		System.out.println("\n\n\n");
		System.out.println(GroupBehaviorReader.debugDump());
		
		int length = 10;
		int width = 10;
		double[][] noise = ValueNoise.generateValueNoise(length, width, 10, 3);
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				System.out.format("%04f,", noise[i][j]);
			}
			System.out.println();
		}
		
		
		/*
		World world = new World(10, 10, 10);
		
		Group testGroup = null;
		
		for(int i = 0; i <= 9; i += 2) {
			
			Group grp = new Group(world, i, i);
			grp.setBehavior(GroupBehaviorReader.makeInstanceOf("generic_pastoral.behavior", grp));
			
			if(i == 0) {
				testGroup = grp; 
			}
			
			ArrayList<Entity> testEntities = new ArrayList<Entity>();
			
			testEntities.add(SpeciesReader.getSpecies("Locust").makeInstanceOf(1000));
			testEntities.add(SpeciesReader.getSpecies("Locust").makeInstanceOf(1000));
			testEntities.add(SpeciesReader.getSpecies("Locust").makeInstanceOf(0));
			testEntities.add(SpeciesReader.getSpecies("Locust_Minor").makeInstanceOf(1000));

			for(Entity e : testEntities) {
				grp.addMember(e);
			}
			
			grp.addItem(ItemsReader.makeComponent("Bud", 120));
			
			world.addGroup(grp.id, grp, i, i);
		}
		
		Scanner scan = new Scanner(System.in);
		
		//while(!scan.nextLine().equalsIgnoreCase("q")) {
		while(true){
			Thread.sleep(1000);
			System.out.println("\n\n\n");
			//System.out.println(world.map[4][2].toString());
			System.out.println(world.display());
			
			System.out.println(testGroup.toString());
			
			world.turn();
		}
		*/
		//scan.close();
		
		
	}
}
