package com.github.bingoohuang.mallscanner.domain;

public class Barcode {
    private int state;
    private String message;

    private String codeText;

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

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }


    public static Barcode newBarCode(int state, String message, String codeText) {
        Barcode barcode = new Barcode();
        barcode.setState(state);
        barcode.setMessage(message);
        barcode.setCodeText(codeText);

        return barcode;
    }
}
