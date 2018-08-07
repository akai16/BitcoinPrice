package com.example.saint.bitcoinprice.constants;

public class MercadoBitcoinAPI {

    public static String MERCADO_BITCOIN_API_PATH = "https://www.mercadobitcoin.net/api/";

    public static class COIN {
        public static String BITCOIN  = "btc/";
        public static String LITECOIN = "ltc/";
        public static String BCASH    = "bch/";
    }


    public static class METHOD {
        public static String TICKER = "ticker/";
        public static String orderbook = "orderbook/";
        public static String trades = "trades/";
    }
}
