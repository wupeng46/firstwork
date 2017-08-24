package com.sf.o2o.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class AES256CBCEncode {
    /**
     * ����
     * 
     * @param content
     *            ��Ҫ���ܵ�����
     * @param password
     *            ��������
     * @return
     */
    public static String encrypt(String content, String key) {
        try {
            byte ivBytes[] = new byte[16];
            
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// ����������
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, newKey,ivSpec);// ��ʼ��
            String result = Base64.encodeBase64String(cipher.doFinal(byteContent));
            return result; // ����
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ����
     * 
     * @param content
     *            ����������
     * @param password
     *            ������Կ
     * @return
     */
    public static String decrypt(String content, String key) {
        try {
            byte ivBytes[] = new byte[16];
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// ����������
            cipher.init(Cipher.DECRYPT_MODE, newKey,ivSpec);// ��ʼ��
            String result = new String(cipher.doFinal(Base64.decodeBase64(content)),"UTF-8");
            return result; // ����
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String content = "test";
        String password = "abcdefghijklmnopqrstuvwxyz123456";
        // ����
        System.out.println("����ǰ��" + content);
        String encryptResult = encrypt(content, password);
        System.out.println(encryptResult);

        // ����
        String decryptResult = decrypt(encryptResult, password);
        System.out.println("���ܺ�" + decryptResult);
    }
}
