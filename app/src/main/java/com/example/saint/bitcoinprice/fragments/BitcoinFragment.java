package com.example.saint.bitcoinprice.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saint.bitcoinprice.R;
import com.example.saint.bitcoinprice.constants.MercadoBitcoinAPI;
import com.example.saint.bitcoinprice.constants.SecurityPreferencesConstants;
import com.example.saint.bitcoinprice.entities.MBTicker;
import com.example.saint.bitcoinprice.networkUtils.NetworkUtils;
import com.example.saint.bitcoinprice.utils.SecurityPreferences;

public class BitcoinFragment extends Fragment {

    ViewHolder mViewHolder = new ViewHolder();
    BitcoinTask mBitcoinTask;
    Double totalBitcoin = 0;
    Double valorInvestido = 0;
    SecurityPreferences preferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferences = new SecurityPreferences(getContext());
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bitcoin_price, container, false);

        this.mViewHolder.mTextLastOffer = (TextView) layout.findViewById(R.id.text_last_offer_price);
        this.mViewHolder.mTextLowestPrice = (TextView) layout.findViewById(R.id.text_lowest_price);
        this.mViewHolder.mTextHighestPrice = (TextView) layout.findViewById(R.id.text_highest_price);
        this.mViewHolder.mTextInvestido = (TextView) layout.findViewById(R.id.text_total_investido);
        this.mViewHolder.mTextTotalBitcoin = (TextView) layout.findViewById(R.id.text_total_bitcoin);
        this.mViewHolder.mTextTotalReais = (TextView) layout.findViewById(R.id.text_total_reais);
        this.mViewHolder.mTextLucro= (TextView) layout.findViewById(R.id.text_lucro);

        this.setDefaultValues();

        return layout;
    }

    private void setDefaultValues() {
        Float totalInvestidoPreferencese = this.preferences.getFloatValue(SecurityPreferencesConstants.TOTAL_INVESTIDO);
        if( totalInvestidoPreferencese !=  -1) {
            this.valorInvestido = Double.valueOf(String.valueOf(totalInvestidoPreferencese));
        }

        Float totalBitcoinPreferences = this.preferences.getFloatValue(SecurityPreferencesConstants.TOTAL_BITCOIN);
        if (totalBitcoinPreferences != -1) {
            this.totalBitcoin = Double.valueOf(String.valueOf(totalBitcoinPreferences));
        }

        this.mViewHolder.mTextTotalBitcoin.setText(String.valueOf(this.totalBitcoin));
        this.mViewHolder.mTextInvestido.setText(String.valueOf(this.valorInvestido));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (NetworkUtils.isConnected(getActivity())) {
            if (mBitcoinTask == null || mBitcoinTask.getStatus() == AsyncTask.Status.FINISHED) {
                mBitcoinTask = new BitcoinTask();
                mBitcoinTask.execute();
            } else if (mBitcoinTask.getStatus() == AsyncTask.Status.RUNNING) {
                this.mViewHolder.mTextLastOffer.setText(R.string.waiting_for_data);
            }
        } else {
            this.mViewHolder.mTextLastOffer.setText(R.string.error_no_connection);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        refresh();
    }


    class BitcoinTask extends AsyncTask<Void, Void, MBTicker> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mViewHolder.mTextLastOffer.setText(R.string.searching_data);
            mViewHolder.mTextTotalReais.setText(R.string.searching_data);
            mViewHolder.mTextLucro.setText(R.string.searching_data);

        }

        @Override
        protected MBTicker doInBackground(Void... voids) {
            return new NetworkUtils().getMBTickerJSON(MercadoBitcoinAPI.COIN.BITCOIN);
        }

        @Override
        protected void onPostExecute(MBTicker ticker) {
            super.onPostExecute(ticker);
            if (ticker != null) {
                mViewHolder.mTextLastOffer.setText(String.valueOf(ticker.lastOffer));
                mViewHolder.mTextLowestPrice.setText(String.valueOf(ticker.low));
                mViewHolder.mTextHighestPrice.setText(String.valueOf(ticker.high));

                Double totalReais = ticker.lastOffer * totalBitcoin;
                mViewHolder.mTextTotalReais.setText(String.format("%.2f",totalReais));

                Double lucro = totalReais - valorInvestido;
                mViewHolder.mTextLucro.setText(String.format("%.2f",lucro));
                if(lucro > 0) {
                    mViewHolder.mTextLucro.setTextColor(getResources().getColor(R.color.colorGreen));
                }
                else {
                    mViewHolder.mTextLucro.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
            else {
                mViewHolder.mTextLastOffer.setText(R.string.error_no_connection);
            }
        }
    }


    public void refresh() {

        if (mBitcoinTask != null) {
            if (mBitcoinTask.getStatus() == AsyncTask.Status.FINISHED) {
                mBitcoinTask = new BitcoinTask();
                mBitcoinTask.execute();
            }
        }
    }


    private class ViewHolder {
        TextView mTextLastOffer;
        TextView mTextLowestPrice;
        TextView mTextHighestPrice;
        TextView mTextInvestido;
        TextView mTextTotalBitcoin;
        TextView mTextTotalReais;
        TextView mTextLucro;
    }

}
