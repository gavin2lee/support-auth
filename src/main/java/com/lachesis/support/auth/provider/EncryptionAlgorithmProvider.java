package com.lachesis.support.auth.provider;

public interface EncryptionAlgorithmProvider {
	String encrypt(String plainText);
	String decrypt(String cryptograph);
}
