#!/usr/bin/groovy

import no.evry.BitBucketOAuthUtil

def getAccessToken(String clientKey, String clientSecret) {
    return BitBucketOAuthUtil.getAccessToken(clientKey, clientSecret)
}