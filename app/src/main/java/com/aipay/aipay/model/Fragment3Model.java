package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2018/2/9.
 */

public class Fragment3Model implements Serializable {
    /**
     * mobile : 18306043086
     * head : /head/15.png
     * bank_card_account :
     * bank_card_proceeds_name :
     * bank_title :
     */

    private String mobile;
    private String head;
    private String bank_card_account;
    private String bank_card_proceeds_name;
    private String bank_title;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBank_card_account() {
        return bank_card_account;
    }

    public void setBank_card_account(String bank_card_account) {
        this.bank_card_account = bank_card_account;
    }

    public String getBank_card_proceeds_name() {
        return bank_card_proceeds_name;
    }

    public void setBank_card_proceeds_name(String bank_card_proceeds_name) {
        this.bank_card_proceeds_name = bank_card_proceeds_name;
    }

    public String getBank_title() {
        return bank_title;
    }

    public void setBank_title(String bank_title) {
        this.bank_title = bank_title;
    }
}
