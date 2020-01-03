package com.dragon.fruit.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
/*import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;*/
import org.apache.commons.codec.binary.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program fruit
 * @description: 加密工具类
 * @author: Gaofei
 * @create: 2018/11/06 18:04
 */

public class EncryptionUtils {



    /**
     * MD5 加密
     *
     * @param plainText
     * @return
     */
    public static String encryptionMD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * AES 加密
     *
     * @param content  要加密的内容
     * @param password 加密的key
     * @return
     */
    public static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者

            kgen.init(128, new SecureRandom(password.getBytes()));// 利用用户密码作为随机数初始化出
            // 128位的key生产者
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行

            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥

            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            // null。

            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥

            Cipher cipher = Cipher.getInstance("AES");// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return result;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param content  要解密的内容
     * @param password key
     * @return
     */
    public static byte[] decryptAES(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(content);
            return result; // 明文

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
        }
        return null;
    }


//----------------------------------------------------------------------------------------------------------
/*
 * 加密
 * 1.构造密钥生成器
 * 2.根据ecnodeRules规则初始化密钥生成器
 * 3.产生密钥
 * 4.创建和初始化密码器
 * 5.内容加密
 * 6.返回字符串
 */
public static String AESEncode(String encodeRules,String content){
    try {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen=KeyGenerator.getInstance("AES");
        //2.根据ecnodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        keygen.init(128, new SecureRandom(encodeRules.getBytes()));
        //3.产生原始对称密钥
        SecretKey original_key=keygen.generateKey();
        //4.获得原始对称密钥的字节数组
        byte [] raw=original_key.getEncoded();
        //5.根据字节数组生成AES密钥
        SecretKey key=new SecretKeySpec(raw, "AES");
        //6.根据指定算法AES自成密码器
        Cipher cipher=Cipher.getInstance("AES");
        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
        byte [] byte_encode=content.getBytes("utf-8");
        //9.根据密码器的初始化方式--加密：将数据加密
        byte [] byte_AES=cipher.doFinal(byte_encode);
        //10.将加密后的数据转换为字符串
        //这里用Base64Encoder中会找不到包
        //解决办法：
        //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
        String AES_encode=new String(Base64.encodeBase64(byte_AES));
        //11.将字符串返回
        return AES_encode;



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
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }

    //如果有错就返加nulll
    return null;
}
    /*
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String AESDncode(String encodeRules,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= Base64.decodeBase64(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }



    public static String NewAESEncode(String sKey,String content){
        try{

            //如果key为null或长度不为16，则为非法操作，返回null
            if (sKey == null || sKey.length() != 16) {
                return null;
            }

            //先用AES加密
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");//"算法"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));

            //再用base64加密
            String encrypt = new String(Base64.encodeBase64(encrypted));

            return encrypt;//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }



    public static String NewAESDncode(String sKey,String content){

        try{
            //如果key为null或长度不为16，则为非法操作，返回null
            if (sKey == null || sKey.length() != 16) {
                return null;
            }

            //先用base64解密
            byte[] encrypted = Base64.decodeBase64(content.getBytes());

            //再用AES解密
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(encrypted);

            //最后返回解密后的字符串
            String originalString = new String(original, "utf-8");
            return originalString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;


    }

    public static void main(String[] args) throws Exception {

      /*  String  encode = AESEncode("siweikeji8888888","{\"code\":\"200\",\"message\":\"success\",\"user\":{\"id\":13,\"phone\":\"18301403656\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"nickName\":\"你猜\",\"imageUrl\":\"string\",\"driveCode\":\"string\",\"isVip\":0,\"expiredDate\":1569916363000,\"status\":0,\"checkCod\":\"\",\"askCode\":\"POG6DF\",\"pid\":0,\"createTime\":1567240296000,\"tcID\":0,\"tcName\":\"\"},\"token\":\"ce91f624a69f17d7c4badf35222f96f2\"}");
        System.out.println(" 加密后的串为 : "+encode);

        String dcode = AESDncode("siweikeji8888888","m5/NjQqmFWDEfGFnePMl6OvSKUhzvJFstV0aYsSJOdQDz3iYyH501Mf52bOF1+6mvIhdDqEeEVY4UKzoKWaViUvXi8IOqzw5f3aPIndhZrn5OiDKscK6hgMAtSdQFMjfrsXL9Ufkse9axM6qwlO6/JREgUzJAMwo1MY7nY/L3iS2CRtPt1+E/EoH+AdvQjoOlR67i1g/8k4YWEOMxE5t8KZVGsB7nuDaGOIOil5HnbSlcGEHcbbQe6UW/2lzQXrq6FiiAek+OK3tn/jH6bauZitFkI/Xa3A4wisFWAvC2wMMn0MatuscCtoN0sRDpexO7yYSt0ZESiP0F9rBdctVXVuaXdDml9SDgFH9bbWObwBLhjpdkhQGxujxPoH7IxeklTTRSF0MAgJVeyrMV9dALg==");
        System.out.println(" 解密后的串为 ： "+dcode);*/

        /*String dcode1 = AESDncode("siweikeji8888888","TOz4cL2hX5SU3x2s/xi2fL+zqYLs9AncSVYJJ0gjTGzKfq4xab3WZv39yDlx9cedAnLBbbnlwHUXb8haRtT55oaZlvSkkNqy8Rh/f9TA1cc6DLfb7ImQnphvGnNPhYUBAWrkNQ+6Y7pPWLMwQo/Oa9wZ0DEWkLRVg0WQoeC9ntmKBzKlKQleTXKpqg13mqicJStKAqPWsqzWO+ZJPzOrEz8lr6DGfRHxsw+3/6Er9k6tBqcbcQb4L6ysMHaKJ4ddSNarqg8uKwUJ8DiAzSglMZ380xpZWrOhvaFiI1uklUFjD1lureyi+8QhMnAQKS7exEefeYpgTIUBb/5pZdtGzFu1+Kn9rARxVxex32MkmxYTIOgeS+J+CcGoLAcPDZI6NiGPOj9w6msej4pdPPt0hm88fCmqPVRu+P+IqR/HIMT3R5ori/9XnGjklA+O7/ch5Nz+lN6RMOja+qczR3AKgeiEwx94rm4I0NSjAD+V3tU=");
        System.out.println(" 解密后的串为1 ： "+dcode1);

        String newEncode = NewAESEncode("siweikeji8888888","{\"code\":\"200\",\"message\":\"success\",\"user\":{\"id\":13,\"phone\":\"18301403656\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"nickName\":\"你猜\",\"imageUrl\":\"string\",\"driveCode\":\"string\",\"isVip\":0,\"expiredDate\":1569916363000,\"status\":0,\"checkCod\":\"\",\"askCode\":\"POG6DF\",\"pid\":0,\"createTime\":1567240296000,\"tcID\":0,\"tcName\":\"\"},\"token\":\"ce91f624a69f17d7c4badf35222f96f2\"}");
        System.out.println("new 加密后的串为 1 ： " + newEncode);*/

        String newDncode = NewAESDncode("siweikeji8888888","QO6TXRFg95RLlnwBioyF9g==");
        System.out.println("new 加密后的串为 1 ： " + newDncode);

        //long time = 1568715620000L;

       String date = DateUtils.timeStamp2Date("1568715620000");

        Long time = 1571311783000L-new Date().getTime();
        System.out.println(date);
        System.out.println(time);



    }
}
