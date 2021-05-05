package com.revature;

public class application {
	
	public String printNumberInWord(int number) {
		// TODO Write an implementation for this method declaration
		
		switch (number) {
		case 0:
			System.out.println("ZERO");
			break;
		case 1:
			System.out.println("ONE");
			break;
		case 2:
			System.out.println("TWO");
			break;
		case 3:
			System.out.println("THREE");
			break;
		case 4:
			System.out.println("FOUR");
			break;
		case 5:
			System.out.println("FIVE");
			break;
		case 6:
			System.out.println("SIX");
			break;
		case 7:
			System.out.println("SEVEN");
			break;
		case 8:
			System.out.println("EIGHT");
			break;
		case 9:
			System.out.println("NINE");
			break;
		default:
			System.out.println("OTHER");
			break;
		}	
		
		return null;
	}
	
	public String reverse(String string) {
		// TODO Write an implementation for this method declaration
		char [] input = string.toCharArray();
		int end = input.length - 1;
		char tmp;
		for (int i = 0; i < end; i++) {
			tmp = input[i];
			input[i] = input[end];
			input[end]=tmp;
			end--;
		}
		
		for (int n = 0; n < input.length; n++) {
		System.out.println(input[n]);
		}
		
		return null;
	}
}
