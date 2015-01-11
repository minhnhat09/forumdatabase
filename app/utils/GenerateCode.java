package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public  class GenerateCode {
	private static SecureRandom random = new SecureRandom();
	
	public static String nextSessionId(){
		return new BigInteger(130, random).toString(32);
	}
	public static void main(String[] args) {
		System.out.println(GenerateCode.nextSessionId());
	}
}
