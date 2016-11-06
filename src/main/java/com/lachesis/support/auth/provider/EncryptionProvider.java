package com.lachesis.support.auth.provider;

import com.lachesis.support.auth.encrypter.Encrypter;

public interface EncryptionProvider {
	Encrypter getEncrypter();
}
