package com.furkan.client.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    private static MessageDigest messageDigest;

    public static String encrypt(String plainText) throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();

        byte[] passBytes = plainText.getBytes();
        byte[] digested = messageDigest.digest(passBytes);

        StringBuffer stringBuffer = new StringBuffer();

        for(int ctr = 0; ctr < digested.length;ctr++) {
            stringBuffer.append(Integer.toHexString(0xff & digested[ctr]));
        }
        return stringBuffer.toString();
    }}
