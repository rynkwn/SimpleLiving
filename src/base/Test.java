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

import com.google.gson.Gson;

public class Test {
	public static void main(String[] args) throws Exception {
		
		Gson gson = new Gson();
		Species species = gson.fromJson(new FileReader(new File("src/data/species/cheryh.species")), Species.class);
		
		species.processJSON();
		
		System.out.println(species.toString());

		/*
		Gson gson = new Gson();

		String name = "testInfra";
		double weight = 10.0;
		int quantity = 1;
		String projectName = "test";
		LaborPool lp = new LaborPool(0);
		HashMap<String, Integer> rawMats = new HashMap<String, Integer>();
		rawMats.put("T1", 1);

		Macronutrient nutr = new Macronutrient(0);
		HashMap<String, Integer> products = new HashMap<String, Integer>();
		products.put("P1", 1);

		Infrastructure infra = new Infrastructure(name, weight, quantity, projectName, lp, rawMats, nutr, products);

		System.out.println(gson.toJson(infra));
		*/
		// Creating an Infrastructure Object.
		
		
		
	}
}