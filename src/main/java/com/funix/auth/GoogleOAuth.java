package com.funix.auth;

import com.funix.config.MyKey;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

public class GoogleOAuth {
	private static final String GOOGLE_CLIENT_ID = MyKey.GOOGLE_OAUTH_CLIENT_ID;

    public static GoogleIdToken.Payload getPayload(String tokenString) 
    		throws Exception {
        GsonFactory gsonFactory = new GsonFactory();
        GoogleIdTokenVerifier googleIdTokenVerifier =
                            new GoogleIdTokenVerifier(new NetHttpTransport(), gsonFactory);
        GoogleIdToken token = GoogleIdToken.parse(gsonFactory, tokenString);

        if (googleIdTokenVerifier.verify(token)) {
            GoogleIdToken.Payload payload = token.getPayload();
            if (!GOOGLE_CLIENT_ID.equals(payload.getAudience())) {
                throw new IllegalArgumentException("Audience mismatch");
            } else if (!GOOGLE_CLIENT_ID.equals(payload.getAuthorizedParty())) {
                throw new IllegalArgumentException("Client ID mismatch");
            }
            return payload;
        } else {
            throw new IllegalArgumentException("id token cannot be verified");
        }
    }
}
