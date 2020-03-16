package oop.separate.ex1.asis;

import com.sun.org.apache.xml.internal.security.algorithms.Algorithm;
import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import oop.separate.ex1.Req;
import oop.separate.ex1.Res;
import oop.separate.ex1.ResponseEntitny;
import oop.separate.ex1.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.dsig.Transform;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class CashClient {
    String api = "api";
    private static final String DEFAULT_TRANSFORM = "default";

    private SecretKeySpec keySpec;
    private IvParameterSpec ivSpec;
    private RestTemplate restTemplate;

    private Res post(Req req) throws Exception {
        String reqBody = toJson(req);

        Cipher cipher = Cipher.getInstance(DEFAULT_TRANSFORM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        String encReqBody = new String(Base64.getEncoder().encode(cipher.doFinal(reqBody.getBytes())));

        ResponseEntitny<String> responseEntitny = restTemplate.postForEntity(api, encReqBody, String.class);

        String encRespBody = responseEntitny.getBody();

        Cipher cipher2 = Cipher.getInstance(DEFAULT_TRANSFORM);
        cipher2.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        String respBody = new String(cipher.doFinal(Base64.getDecoder().decode(encRespBody)));

        return jsonToObj(respBody);
    }

    private Res jsonToObj(String respBody) {
        return new Res();
    }

    private String toJson(Req req) {
        return "req to json";
    }
}
