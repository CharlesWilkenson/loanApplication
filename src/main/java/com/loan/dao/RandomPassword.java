package com.loan.dao;

public class RandomPassword {
	
	private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

	public static String randomAlphaNumeric(int count) {

	StringBuilder builder = new StringBuilder();

	while (count-- != 0) {

	int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

	builder.append(ALPHA_NUMERIC_STRING.charAt(character));

	}

	
	System.out.println(builder.toString());
	return builder.toString();

	}
}
