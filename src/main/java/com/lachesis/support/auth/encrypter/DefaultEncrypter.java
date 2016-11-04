package com.lachesis.support.auth.encrypter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.provider.EncryptionAlgorithmProvider;

public class DefaultEncrypter implements Encrypter{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultEncrypter.class);
	
	private EncryptionAlgorithmProvider provider;
	
	public String encrypt(String plainText){
		if(LOG.isDebugEnabled()){
			LOG.debug("using EncryptionAlgorithmProvider [" + provider.getClass() + "]");
		}
		
		try{
			String cryptograph = provider.encrypt(plainText);
			return cryptograph;
		}catch(Exception e){
			LOG.error("encrypting failed.", e);
			throw new RuntimeException("Cannot encrypt such text.");
		}
	}
	
	public String decrypt(String cryptograph){
		if(LOG.isDebugEnabled()){
			LOG.debug("using EncryptionAlgorithmProvider [" + provider.getClass() + "]");
		}
		try{
			String plainText = provider.encrypt(cryptograph);
			return plainText;
		}catch(Exception e){
			LOG.error("decrypting failed.", e);
			return cryptograph;
		}
	}
}
