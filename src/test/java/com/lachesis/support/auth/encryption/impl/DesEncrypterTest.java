package com.lachesis.support.auth.encryption.impl;

//import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.lachesis.support.auth.encryption.Encrypter;

public class DesEncrypterTest {
	Encrypter encrypter;

	@Before
	public void setUp() throws Exception {
		encrypter = new DesEncrypter();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testEncrypt() {
		String plainText = "username|10.2.3.74|147666666";

		String cipherText = encrypter.encrypt(plainText);

		Assert.assertNotNull(cipherText);

		// "/NQ5ultMnf9deFRuHmyGkxnx8QrEfKp2c4Vq4yT9Nos="
	}

	@Ignore
	@Test
	public void testDecrypt() {
		String token = "/NQ5ultMnf9deFRuHmyGkxnx8QrEfKp2c4Vq4yT9Nos=";

		String plainText = encrypter.decrypt(token);

		Assert.assertNotNull(plainText);
		// "username|10.2.3.74|147666666"
	}

	@Test
	public void testEncryptAndDecrypt() {
		long startTime = System.currentTimeMillis();
		String plainText = "username|10.2.3.74|1478760076830";

		String cipherText = encrypter.encrypt(plainText);
		
		//"/NQ5ultMnf9deFRuHmyGk68L/nv9fTwa4g+4lpY0pTYOa/q1I5HTxw=="
		
		Assert.assertNotNull(cipherText);
		
		String decryptedText = encrypter.decrypt(cipherText);
		Assert.assertNotNull(decryptedText);
		
		Assert.assertEquals("compare plain text with cipher text", plainText, decryptedText);
		
		long endTime = System.currentTimeMillis();
		
		long timeElapse = (endTime - startTime);
		
		Assert.assertTrue("elapsed time shoud less than 200 milliseconds", timeElapse < 150);
	}

}
