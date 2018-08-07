package com.example.saint.bitcoinprice.networkUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.saint.bitcoinprice.constants.MercadoBitcoinAPI;
import com.example.saint.bitcoinprice.entities.MBTicker;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {



    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }


    private HttpURLConnection conectar(String coin, String operation) throws IOException {
        int SEGUNDOS = 1000;
        HttpURLConnection connection;

        URL url = new URL(MercadoBitcoinAPI.MERCADO_BITCOIN_API_PATH + coin + operation);
        connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(10*SEGUNDOS);
        connection.setConnectTimeout(15*SEGUNDOS);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.connect();

        return connection;
    }


    public MBTicker getMBTickerJSON(String coin) {
        try {
            HttpURLConnection conexao = conectar(coin, MercadoBitcoinAPI.METHOD.TICKER);

            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = conexao.getInputStream();

                JSONObject json = new JSONObject(bytesParaString(inputStream));

                JSONObject ticker = json.getJSONObject("ticker");


                MBTicker tickerObject = new MBTicker(ticker.getDouble("high"),
                                                     ticker.getDouble("low" ),
                                                     ticker.getDouble("vol" ),
                                                     ticker.getDouble("last"),
                                                     ticker.getDouble("buy" ),
                                                     ticker.getDouble("sell"),
                                                     ticker.getInt   ("date"));

                return tickerObject;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }





    private String bytesParaString(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();

        int bytesLidos;
        while ((bytesLidos = inputStream.read(buffer))!=- 1){

            bufferzao.write(buffer,0,bytesLidos);
        }

        return  new String(bufferzao.toByteArray(),"UTF-8");
    }

}
