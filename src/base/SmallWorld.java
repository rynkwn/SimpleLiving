package base;

import java.io.*;
import java.util.*;

import item.*;
import ecology.*;
import entities.*;
import data.*;
import organization.*;
import world.*;
import util.*;
import behavior.*;

public class SmallWorld {

	public static void main(String [] args) throws Exception{
		
		SpeciesReader.readSpeciesData("src/data/species");
		ItemsReader.readItemData("src/data/items");
		//GroupBehaviorReader.readBehaviorData("src/data");
		EcologyReader.readEcologyData("src/data/wildlife");
		
		
		System.out.println(SpeciesReader.debugDump());
		
		System.out.println("\n\n\n");
		System.out.println(ItemsReader.debugDump());
		
		//System.out.println("\n\n\n");
		//System.out.println(GroupBehaviorReader.debugDump());

		System.out.println("\n\n\n");
		System.out.println(EcologyReader.debugDump());
		
		int length = 2;
		int width = 2;
		
		World world = new World(length, width, 10);
		
		Group testGroup = new Group(world, 0, 0);
		testGroup.setBehavior(GroupBehaviorType.VOLCH_PEACEFUL);
		
		ArrayList<Entity> testEntities = new ArrayList<Entity>();
		
		testEntities.add(SpeciesReader.getSpecies("Volch").makeInstanceOf(1000));
		testEntities.add(SpeciesReader.getSpecies("Volch").makeInstanceOf(1000));
		testEntities.add(SpeciesReader.getSpecies("Volch").makeInstanceOf(0));
		testEntities.add(SpeciesReader.getSpecies("Volch Minor").makeInstanceOf(1000));

		for(Entity e : testEntities) {
			testGroup.addMember(e);
		}
		
		testGroup.addItem(ItemsReader.makeComponent("Bud", 120));
		
		world.addGroup(testGroup.id, testGroup, 0, 0);
		
		
		Scanner scan = new Scanner(System.in);
		
		//while(!scan.nextLine().equalsIgnoreCase("q")) {
		
		while(true){
			Thread.sleep(1000);
			System.out.println("\n\n\n");
			//System.out.println(world.map[4][2].toString());
			System.out.println(world.display());
			//System.out.println(world.map[0][0].toString());
			//System.out.println(world.ecology[0][0].toString());
			
			System.out.println(testGroup.toString());
			
			world.turn();
		}
		//scan.close();
	}
}
