package oop.separate.ex1.tobe;

import oop.separate.ex1.ResponseEntitny;
import oop.separate.ex1.RestTemplate;

public class HttpService {

    private static final String API = "api";
    private final RestTemplate restTemplate;
    private final Encoder encoder;
    private final Decoder decoder;

    public HttpService(RestTemplate restTemplate, Encoder encoder, Decoder decoder) {
        this.restTemplate = restTemplate;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String execute(String reqBody) throws Exception {
        ResponseEntitny<String> responseEntity = restTemplate.postForEntity(API, encoder.encode(reqBody), String.class);
        return decoder.decode(responseEntity.getBody());
    }


}
