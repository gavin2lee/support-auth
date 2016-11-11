package com.lachesis.support.auth.encryption.impl;

import com.lachesis.support.auth.encryption.Encrypter;
import com.lachesis.support.auth.encryption.EncryptionProvider;

public class DefaultEncryptionProvider implements EncryptionProvider {

	private Encrypter encrypter = new DesEncrypter();
	
	public void setEncrypter(Encrypter encrypter) {
		this.encrypter = encrypter;
	}
	
	@Override
	public Encrypter getEncrypter() {
		if(this.encrypter == null){
			throw new RuntimeException("the concrete encrypter should be specified");
		}
		return this.encrypter;
	}

}
