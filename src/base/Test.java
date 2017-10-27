/*
 * A test file to pick and choose out tiny pieces of the Simulator for testing! 
 */

package base;

import com.google.gson.Gson;

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

public class Test {
	
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		Species spec = gson.fromJson(new FileReader("src/data/locust.temp"), Species.class);
		spec.processJSON();
		System.out.println(gson.toJson(spec));
	}
}

