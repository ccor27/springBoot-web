package com.springboot.app.util.paginator;

public class PageItem {

    private int number;
    private boolean isActual;

    public PageItem(int number, boolean isActual) {
        this.number = number;
        this.isActual = isActual;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }
}
