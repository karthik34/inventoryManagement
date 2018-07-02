package com.inventory.management;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class InventoryManagement {
	NumberFormat numFormat=NumberFormat.getInstance();
	
	

	public static void main(String[] args) {
		InventoryManagement management=new InventoryManagement();
		Scanner scan=new Scanner(System.in);

		System.out.println("Please Enter Your Command :");
		boolean flag=true;
		TreeMap<String, ProductDetails> products=new TreeMap<String, ProductDetails>();
		HashMap<String, ProductDetails> deletedItems=new HashMap<String, ProductDetails>();
		ProductDetails details=null;
		while(flag){	
			String val=scan.nextLine();
			String sect[]=val.split(" ");
			if(sect[0].equals("report")){
				management.displayInventoryReport(products,deletedItems);
			}else{
				if(sect[0].equals("create")){
					details=new ProductDetails();
					details.setProductName(sect[1]);
					details.setCostPrice(Double.parseDouble(sect[2]));
					details.setSellingPrice(Double.parseDouble(sect[3]));
				}else if(sect[0].equals("updateBuy")){
					details=products.get(sect[1]);
					details.setAvailableQty(Double.parseDouble(sect[2]));
					details.setValue(details.getAvailableQty()*details.getCostPrice());
				}else if(sect[0].equals("updateSell")){
					details=products.get(sect[1]);
					details.setSoldQty(Double.parseDouble(sect[2]));
					details.setAvailableQty(details.getAvailableQty()-details.getSoldQty());
					details.setValue(details.getAvailableQty()*details.getCostPrice());
				}else if(sect[0].equals("delete")){
					details=products.get(sect[1]);
					deletedItems.put(sect[1], details);
					products.remove(sect[1]);
				}
				if(!sect[0].equals("delete"))
					products.put(sect[1], details);
			}
			flag=Boolean.parseBoolean(scan.nextLine());	

		}
	}

	public void displayInventoryReport(TreeMap<String, ProductDetails> products,HashMap<String, ProductDetails> deletedRecords){
		numFormat.setMaximumFractionDigits(2);
		double totalPrice=0;
		double totalProfit=0;
		double costPriceOfDeletedItems=0;
		System.out.println();
		System.out.println("Inventroy Report");
		System.out.printf("%s\t%s\t%s\t%s\t%s","Item Name","Brought At","Sold At","Available Qty","Value");
		System.out.println();
		System.out.printf("%s\t%s\t%s\t%s\t%s","---------","----------","-------","-------------","-----");
		System.out.println();
		for(Entry<String, ProductDetails> details: products.entrySet()){
			System.out.printf("%s\t\t%s\t\t%s\t%s\t\t%s",details.getValue().getProductName()
					,numFormat.format(details.getValue().getCostPrice()),numFormat.format(details.getValue().getSellingPrice()),
					numFormat.format(details.getValue().getAvailableQty()),numFormat.format(details.getValue().getValue()));
			System.out.println();
			totalPrice+=details.getValue().getValue();
			totalProfit+=((details.getValue().getSellingPrice()-details.getValue().getCostPrice())*details.getValue().getSoldQty());
		}
		for(Entry<String, ProductDetails> details: deletedRecords.entrySet()){
		if(deletedRecords.containsKey(details.getKey())){
			costPriceOfDeletedItems=deletedRecords.get(details.getKey()).getCostPrice();
			totalProfit-=(costPriceOfDeletedItems*deletedRecords.get(details.getKey()).getAvailableQty());
		}
		}
		System.out.println("----------------------------------------------------------------------------------");
		System.out.printf("%s\t\t\t\t\t%s","Total value ",numFormat.format(totalPrice));
		System.out.println();
		System.out.printf("%s\t\t\t\t\t%s","Profit since previous report ",numFormat.format(totalProfit));
		System.out.println();
	}

}
