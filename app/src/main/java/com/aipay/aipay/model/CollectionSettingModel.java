package com.aipay.aipay.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2018/2/10.
 */

public class CollectionSettingModel implements Serializable {
    /**
     * member : {"mobile_state_code":"86","mobile":"18306043086","bank_card_account":"45525555225554555","bank_card_proceeds_name":"阿斯顿马丁","bank_title":"阿斯顿银行","alipay_account":"ffsxcffdf","alipay_proceeds_name":"阿斯顿","alipay_proceeds_qrcode":"/upload/alipay-proceeds-qrcode/2018-11-13/c066cc8fb1c98edc17d5a530e4652a2b.png","wechat_proceeds_qrcode":"/upload/wechat-proceeds-qrcode/2018-11-13/e610e7d71c5071893ca376d3d4980e52.png"}
     * bank_list : [{"title":"中国工商银行"},{"title":"招商银行"},{"title":"中国农业银行"},{"title":"中国建设银行"},{"title":"中国银行"},{"title":"中国民生银行"},{"title":"中国光大银行"},{"title":"中信银行"},{"title":"交通银行"},{"title":"兴业银行"},{"title":"上海浦东发展银行"},{"title":"中国人民银行"},{"title":"华夏银行"},{"title":"深圳发展银行"},{"title":"广东发展银行"},{"title":"国家开发银行"},{"title":"中国邮政储蓄银行"},{"title":"中国进出口银行"},{"title":"中国农业发展银行"},{"title":"中国银行香港分行"},{"title":"北京银行"},{"title":"北京农村商业银行 "},{"title":"天津银行"},{"title":"上海银行"},{"title":"上海农村商业银行"},{"title":"南京银行"},{"title":"宁波银行"},{"title":"杭州市商业银行 "},{"title":"深圳平安银行"},{"title":"深圳农村商业银行"},{"title":"温州银行"},{"title":"厦门国际银行"},{"title":"济南市商业银行"},{"title":"重庆银行"},{"title":"哈尔滨银行"},{"title":"成都市商业银行"},{"title":"包头市商业银行 "},{"title":"南昌市商业银行"},{"title":"贵阳商业银行"},{"title":"兰州市商业银行"},{"title":"常熟农村商业银行"},{"title":"青岛市商业银行 "},{"title":"徽商银行"},{"title":"花旗中国银行"},{"title":"汇丰中国银行 "},{"title":"渣打中国银行 "},{"title":"香港汇丰银行"},{"title":"渣打(香港)银行 "},{"title":"中国建设银行(亚洲)"},{"title":"东亚银行"},{"title":"恒生银行"},{"title":"花旗(台湾)银行"},{"title":"荷兰银行"},{"title":"欧力士银行 "},{"title":"巴黎银行"},{"title":"美国运通银行"},{"title":"蒙特利尔银行 "},{"title":"满地可银行"},{"title":"瑞士银行"},{"title":"德意志银行"}]
     */

    private MemberBean member;
    private List<BankListBean> bank_list;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<BankListBean> getBank_list() {
        return bank_list;
    }

    public void setBank_list(List<BankListBean> bank_list) {
        this.bank_list = bank_list;
    }

    public static class MemberBean {
        /**
         * mobile_state_code : 86
         * mobile : 18306043086
         * bank_card_account : 45525555225554555
         * bank_card_proceeds_name : 阿斯顿马丁
         * bank_title : 阿斯顿银行
         * alipay_account : ffsxcffdf
         * alipay_proceeds_name : 阿斯顿
         * alipay_proceeds_qrcode : /upload/alipay-proceeds-qrcode/2018-11-13/c066cc8fb1c98edc17d5a530e4652a2b.png
         * wechat_proceeds_qrcode : /upload/wechat-proceeds-qrcode/2018-11-13/e610e7d71c5071893ca376d3d4980e52.png
         */

        private String mobile_state_code;
        private String mobile;
        private String bank_card_account;
        private String bank_card_proceeds_name;
        private String bank_title;
        private String bank_address;
        private String alipay_account;
        private String alipay_proceeds_name;
        private String alipay_proceeds_qrcode;
        private String wechat_proceeds_qrcode;

        private int proceeds_display;

        public int getProceeds_display() {
            return proceeds_display;
        }

        public void setProceeds_display(int proceeds_display) {
            this.proceeds_display = proceeds_display;
        }

        public String getMobile_state_code() {
            return mobile_state_code;
        }

        public void setMobile_state_code(String mobile_state_code) {
            this.mobile_state_code = mobile_state_code;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getBank_address() {
            return bank_address;
        }

        public void setBank_address(String bank_address) {
            this.bank_address = bank_address;
        }

        public String getAlipay_account() {
            return alipay_account;
        }

        public void setAlipay_account(String alipay_account) {
            this.alipay_account = alipay_account;
        }

        public String getAlipay_proceeds_name() {
            return alipay_proceeds_name;
        }

        public void setAlipay_proceeds_name(String alipay_proceeds_name) {
            this.alipay_proceeds_name = alipay_proceeds_name;
        }

        public String getAlipay_proceeds_qrcode() {
            return alipay_proceeds_qrcode;
        }

        public void setAlipay_proceeds_qrcode(String alipay_proceeds_qrcode) {
            this.alipay_proceeds_qrcode = alipay_proceeds_qrcode;
        }

        public String getWechat_proceeds_qrcode() {
            return wechat_proceeds_qrcode;
        }

        public void setWechat_proceeds_qrcode(String wechat_proceeds_qrcode) {
            this.wechat_proceeds_qrcode = wechat_proceeds_qrcode;
        }
    }

    public static class BankListBean {
        /**
         * title : 中国工商银行
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
