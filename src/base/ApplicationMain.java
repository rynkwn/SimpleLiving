package base;

import item.ItemsReader;

import java.awt.Color;
import java.util.Scanner;

import javax.swing.JFrame;

import organization.Group;
import world.World;
import entities.Entity;
import entities.SpeciesReader;
import asciiPanel.AsciiPanel;

public class ApplicationMain extends JFrame {

	private AsciiPanel terminal;
	
	public ApplicationMain() {
		super();
		terminal = new AsciiPanel();
		terminal.write("rl tutorial", 1, 1);
		add(terminal);
		pack();
	}
	
	public void write(String stuff){
		terminal.write(stuff, 1, 1);
	}


	public static void main(String[] args) throws InterruptedException {
		ApplicationMain app = new ApplicationMain();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
		
		SpeciesReader.readSpeciesData("src/data/");
		ItemsReader.readItemData("src/data/");
		
		System.out.println(SpeciesReader.debugDump());
		
		System.out.println("\n\n\n");
		System.out.println(ItemsReader.debugDump());
		
		
		World world = new World(10, 10);
		
		Group testGroup = null;
		
		for(int i = 0; i <= 9; i += 2) {
			
			Group grp = new Group(world, i, i);
			
			if(i == 0) {
				testGroup = grp; 
			}
			
			Entity a = SpeciesReader.getSpecies("Locust").makeInstanceOf();
			Entity b = SpeciesReader.getSpecies("Locust").makeInstanceOf();
			Entity c = SpeciesReader.getSpecies("Locust").makeInstanceOf();
			Entity d = SpeciesReader.getSpecies("Locust_Minor").makeInstanceOf();
			
			grp.addMember(a);
			grp.addMember(b);
			grp.addMember(c);
			grp.addMember(d);
			
			grp.addItem(ItemsReader.makeComponent("Bud", 120));
			
			world.addGroup(grp.id, grp, i, i);
		}
		
		Scanner scan = new Scanner(System.in);
		
		//while(!scan.nextLine().equalsIgnoreCase("q")) {
		while(true){
			Thread.sleep(1000);
			//System.out.println(world.map[4][2].toString());
			app.write(world.display());
			
			System.out.println(testGroup.toString());
			
			world.turn();
		}
		
	}
	
}
