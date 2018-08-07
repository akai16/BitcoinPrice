package com.example.saint.bitcoinprice.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.saint.bitcoinprice.R;
import com.example.saint.bitcoinprice.fragments.BitcoinFragment;
import com.example.saint.bitcoinprice.fragments.NewValueDialogFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewHolder mViewHolder = new ViewHolder();

    FragmentManager fm;
    BitcoinFragment fragmentBitcoinPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        this.mViewHolder.mButtonRefresh = (Button) this.findViewById(R.id.button_atualizar);
        this.mViewHolder.mButtonRefresh.setOnClickListener(this);

        fm = getSupportFragmentManager();
        fragmentBitcoinPrice = (BitcoinFragment) fm.findFragmentById(R.id.frame_bitcoin_price);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NewValueDialogFragment newValueDialogFragment;
        switch (item.getItemId()) {
            case R.id.menu_option_investido:
                newValueDialogFragment = NewValueDialogFragment.newInstance(NewValueDialogFragment.TOTAL_INVESTIDO_EXTRA);
                newValueDialogFragment.abrir(getSupportFragmentManager());
                break;
            case R.id.menu_optioo_total_bitcoin:
                newValueDialogFragment = NewValueDialogFragment.newInstance(NewValueDialogFragment.TOTAL_BITCOIN_EXTRA);
                newValueDialogFragment.abrir(getSupportFragmentManager());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.button_atualizar){
            fragmentBitcoinPrice.refresh();
        }
    }

    private class ViewHolder {
        Button mButtonRefresh;
    }

}


