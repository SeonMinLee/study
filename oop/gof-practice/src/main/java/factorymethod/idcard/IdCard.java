package factorymethod.idcard;

import factorymethod.framework.Product;

public class IdCard extends Product {
    private String owner;
    private String authorizationCode;

    IdCard(String owner, String authorizationCode) {
        System.out.println(owner + "(" + authorizationCode + ")" + "의 카드를 만듭니다.");
        this.owner = owner;
        this.authorizationCode = authorizationCode;
    }

    @Override
    public void use() {
        System.out.println(owner + "(" + authorizationCode + ")" + "의 카드를 사용합니다.");
    }

    public String getOwner() {
        return owner;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }
}
