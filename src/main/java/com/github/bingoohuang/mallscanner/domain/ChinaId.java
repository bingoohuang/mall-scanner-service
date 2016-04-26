package com.github.bingoohuang.mallscanner.domain;

public class ChinaId {
    private int state;
    private String message;

    private String name; // 姓名
    private String num; // 公民身份号码
    private String address; // 住址

    private String startDate; // 有效期限, 例如20151103
    private String endDate; // 有效期限, 例如20351103

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static ChinaId newChinaId(int state, String message) {
        ChinaId chinaId = new ChinaId();
        chinaId.setState(state);
        chinaId.setMessage(message);

        return chinaId;
    }

    public static ChinaId newChinaIdBack(String startDate, String endDate) {
        ChinaId chinaId = new ChinaId();
        chinaId.setState(0);
        chinaId.setMessage("OK");
        chinaId.setStartDate(startDate);
        chinaId.setEndDate(endDate);

        return chinaId;
    }

    public static ChinaId newChinaIdFace(String name, String num, String address) {
        ChinaId chinaId = new ChinaId();
        chinaId.setState(0);
        chinaId.setMessage("OK");
        chinaId.setName(name);
        chinaId.setNum(num);
        chinaId.setAddress(address);

        return chinaId;
    }
}
