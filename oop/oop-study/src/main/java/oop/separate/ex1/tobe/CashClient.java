package oop.separate.ex1.tobe;

import oop.separate.ex1.Req;
import oop.separate.ex1.Res;

public class CashClient {

    private final HttpService httpService;

    public CashClient(HttpService httpService) {
        this.httpService = httpService;
    }

    private Res post(Req req) throws Exception {
        return jsonToObj(httpService.execute(toJson(req)));
    }

    private Res jsonToObj(String respBody) {
        return new Res();
    }

    private String toJson(Req req) {
        return "req to json";
    }
}
