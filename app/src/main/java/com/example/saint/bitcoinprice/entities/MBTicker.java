package com.example.saint.bitcoinprice.entities;

public class MBTicker {

    public double high;
    public double low;
    public double volume;
    public double lastOffer;
    public double buy;
    public double sell;
    public int date;

    public MBTicker(double high, double low, double volume, double lastOffer, double buy, double sell, int date) {
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.lastOffer = lastOffer;
        this.buy = buy;
        this.sell = sell;
        this.date = date;
    }
}
