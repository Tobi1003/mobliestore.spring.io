package com.fpt.MobileStore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPass = "1";
		String encodedPassword = encoder.encode(rawPass);
		System.out.println(encodedPassword);
	}

}
