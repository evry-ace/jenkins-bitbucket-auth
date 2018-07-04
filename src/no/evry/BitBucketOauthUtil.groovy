#!/usr/bin/groovy

package no.evry

import groovy.json.*

@Singleton
class BitBucketOAuthUtil {
    private Map<String, Map> tokens = [];
    private String tokenUri = "https://bitbucket.org/site/oauth2/access_token";

    def fetchToken(String clientKey, String clientSecret) {
        def key = URLEncoder.encode(clientKey, "UTF-8");
        def secret = URLEncoder.encode(clientSecret, "UTF-8");

        def url = new URL(tokenUri);
        def connection = url.openConnection();

        connection.setMethod("POST");
        connection.doOutput = true;

        def auth = "Basic ${"${key}:${secret}".toString().bytes.encodeBase64().toString()}";
        def data = "{\"grant_type\": \"client_credentials\"}"
        connection.setRequestProperty("Authorization", auth);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", "" + data.getBytes().length);
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect()

        def text = connection.content.text

        def obj = new JsonSlurper().parseText(text)
        tokens[key] = obj
    }

    def getAccessToken(String clientKey, String clientSecret) {
        if (!tokens.contains(clientKey) || (tokens.contains(clientey) && tokens.expires_in < 600)) {
            fetchToken(clientKey, clientSecret)
        }

        return tokens[clientKey].access_token
    }
}
