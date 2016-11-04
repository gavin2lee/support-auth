package com.lachesis.support.auth.encrypter;

public interface Encrypter {
	String encrypt(String plainText);
	String decrypt(String cryptograph);
}
