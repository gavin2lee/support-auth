package com.lachesis.support.auth.verifier;

import com.lachesis.support.auth.vo.AuthToken;

public interface TokenVerifier {
	AuthToken verify(String token, String terminalIpAddress);
}
