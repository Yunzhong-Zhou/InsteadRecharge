package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2018/2/8.
 */

public class RechargeDetailModel implements Serializable {
    /**
     * wechat :
     * alipay :
     * unionpay :
     * ewm :
     * ewm_qrcode :
     * bank_title : 中国建设银行
     * bank_card_account : 1234 5678 0123 4567
     * bank_card_proceeds_name : 币哥哥
     * bank_address : 美国纽约XXX
     * top_up : {"id":"d27474f7766b487f302e1df8d81c38ce","sn":"TU1565066851504852","behalf_mobile":"18306043086","pay_type":2,"money":"100.33","digital_money":"14.33","status":1,"pay_detail_type":3,"created_at":"2019-08-06 12:47:31","status_title":"待审核"}
     */

    private String wechat;
    private String alipay;
    private String unionpay;
    private String ewm;
    private String ewm_qrcode;
    private String bank_title;
    private String bank_card_account;
    private String bank_card_proceeds_name;
    private String bank_address;
    private TopUpBean top_up;

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(String unionpay) {
        this.unionpay = unionpay;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public String getEwm_qrcode() {
        return ewm_qrcode;
    }

    public void setEwm_qrcode(String ewm_qrcode) {
        this.ewm_qrcode = ewm_qrcode;
    }

    public String getBank_title() {
        return bank_title;
    }

    public void setBank_title(String bank_title) {
        this.bank_title = bank_title;
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

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }

    public TopUpBean getTop_up() {
        return top_up;
    }

    public void setTop_up(TopUpBean top_up) {
        this.top_up = top_up;
    }

    public static class TopUpBean {
        /**
         * id : d27474f7766b487f302e1df8d81c38ce
         * sn : TU1565066851504852
         * behalf_mobile : 18306043086
         * pay_type : 2
         * money : 100.33
         * digital_money : 14.33
         * status : 1
         * pay_detail_type : 3
         * created_at : 2019-08-06 12:47:31
         * status_title : 待审核
         */

        private String id;
        private String sn;
        private String behalf_mobile;
        private int pay_type;
        private String money;
        private String input_money;
        private int status;
        private int pay_detail_type;
        private String created_at;
        private String status_title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getBehalf_mobile() {
            return behalf_mobile;
        }

        public void setBehalf_mobile(String behalf_mobile) {
            this.behalf_mobile = behalf_mobile;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getInput_money() {
            return input_money;
        }

        public void setInput_money(String input_money) {
            this.input_money = input_money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPay_detail_type() {
            return pay_detail_type;
        }

        public void setPay_detail_type(int pay_detail_type) {
            this.pay_detail_type = pay_detail_type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getStatus_title() {
            return status_title;
        }

        public void setStatus_title(String status_title) {
            this.status_title = status_title;
        }
    }
}
