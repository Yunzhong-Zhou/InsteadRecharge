package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019/8/22.
 */
public class OneClickLoginModel implements Serializable {

    /**
     * appId : n1LrwNtz
     * accessToken : STsid0000001566465444836H00ZBhYbK7NbSqw0vXWV04qb1mX6QZxA
     * telecom : CMCC
     * timestamp : 1566465445000
     * randoms : 4d73dea8cc1e49f09629b8fee8a97670
     * device : ZGV2aWNlPXNhbXN1bmd8aXA9MTkyLjE2OC4xMDEuMTJ8RElEPTQ0NjQwMWM0 OWI0M2ViYjUwYjM2NmE2NGM5ZmQwMWIwfHV1aWQ9ZTRmMjg4NWQzMzY2NDcz OThjM2JjOWM1ODhhYWI2NGI=
     * version : 2.2.1
     * sign : rVnMe3UOcnQMnLndqFPRCniirqg=
     */

    private String appId;
    private String accessToken;
    private String telecom;
    private String timestamp;
    private String randoms;
    private String device;
    private String version;
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTelecom() {
        return telecom;
    }

    public void setTelecom(String telecom) {
        this.telecom = telecom;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRandoms() {
        return randoms;
    }

    public void setRandoms(String randoms) {
        this.randoms = randoms;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "OneClickLoginModel{" +
                "appId='" + appId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", telecom='" + telecom + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", randoms='" + randoms + '\'' +
                ", device='" + device + '\'' +
                ", version='" + version + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
