package com.mason.player;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	
	public static boolean itemAdded = false;
	
	public static List<String> inventory = new ArrayList<String>();
	
	public static void itemOne(){
	if (itemAdded == false){
		Inventory.inventory.add("ItemOne");
		itemAdded = true;
		}
		if(itemAdded == true){
			System.out.println("Item has been added");
		}
	
}
	

}
