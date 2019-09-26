package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019/3/23.
 */
public class CreateRechargeModel implements Serializable {

    /**
     * id : c3710fd6ce3c728e3293ba427ea0d1c4
     * sn : TU1565064578505510
     * behalf_mobile : 18306043086
     * member_id : 5abc8b9d07fd6dcbebb0d1ccbf0334a2
     * pay_type : 1
     * money : 100
     * pay_detail_type : 2
     * member_type : 1
     * member_head : /head/15.png
     * member_nickname :
     * digital_money : 14.285714285714
     * updated_at : 2019-08-06 12:09:38
     * created_at : 2019-08-06 12:09:38
     */

    private String id;

    private int pay_detail_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPay_detail_type() {
        return pay_detail_type;
    }

    public void setPay_detail_type(int pay_detail_type) {
        this.pay_detail_type = pay_detail_type;
    }
}
