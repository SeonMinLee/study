package oop.separate.ex1.tobe;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SecurityService implements Encoder, Decoder {

    private static final String DEFAULT_TRANSFORM = "default";
    private SecretKeySpec keySpec;
    private IvParameterSpec ivSpec;

    @Override
    public String decode(String source) throws Exception{
        Cipher cipher = Cipher.getInstance(DEFAULT_TRANSFORM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(source)));
    }

    @Override
    public String encode(String source) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_TRANSFORM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return new String(Base64.getEncoder().encode(cipher.doFinal(source.getBytes())));
    }
}
