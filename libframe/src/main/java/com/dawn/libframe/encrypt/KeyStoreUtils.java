package com.dawn.libframe.encrypt;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.security.KeyStore;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class KeyStoreUtils {
    private static KeyStoreUtils sKeyUtils;
    private static String AES_CBC = "AES/CBC/PKCS7Padding";
    KeyStore keyStore;
    KeyGenerator keyGenerator;
    Cipher cipher;
    public static String alias = "12345678";
    public static int iv_length = 32;

    private KeyStoreUtils(){
        createKey(alias);

    }

    public static KeyStoreUtils getInstance(){
        if(sKeyUtils == null){
            synchronized (KeyStoreUtils.class){
                if(sKeyUtils == null){
                    sKeyUtils = new KeyStoreUtils();
                }
            }
        }
        return sKeyUtils;
    }

    public void createKey(String keyAlias) { //I call this method only once in the onCreate() method of another activity, with keyAlias "A"
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            keyStore.load(null);
            keyGenerator.init(
                    new KeyGenParameterSpec.Builder(keyAlias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setUserAuthenticationRequired(false)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                            .setRandomizedEncryptionRequired(false)
                            .build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String plainText){ //I call this method many times with the same keyAlias "A" and same plaintext in the same activity
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(alias, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //return byteToHex(cipher.doFinal(plainText.getBytes()));
            String ivStr = byteToHex(cipher.getIV());
            Log.d("11111111","11111111 : "+cipher.getIV()+" 1: "+cipher.getIV().length+" 2: "+ivStr.length());
            return ivStr + byteToHex(cipher.doFinal(plainText.getBytes()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "BUG";
    }


    public String decrypt(String strTemp){
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(AES_CBC);
            SecretKey key = (SecretKey) keyStore.getKey(alias, null);
            String ivStr = strTemp.substring(0,iv_length);
            String cryptStr = strTemp.substring(iv_length);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(hexStringToByteArray(ivStr));
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] original = cipher.doFinal(hexStringToByteArray(cryptStr));

            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }

    private String byteToHex(byte[] byteArray){
        StringBuilder buf = new StringBuilder();
        for (byte b : byteArray)
            buf.append(String.format("%02X", b));
        String hexStr = buf.toString();
        return hexStr;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }
}
