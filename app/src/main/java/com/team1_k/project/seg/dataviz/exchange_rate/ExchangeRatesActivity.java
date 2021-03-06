package com.team1_k.project.seg.dataviz.exchange_rate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.team1_k.project.seg.dataviz.activities.base.MainViewActivity;
import com.team1_k.project.seg.dataviz.R;
import com.team1_k.project.seg.dataviz.activities.comparison.ComparisonActivity;
import com.team1_k.project.seg.dataviz.activities.country.CountrySelectionActivity;
import com.team1_k.project.seg.dataviz.news.NewsActivity;


public class ExchangeRatesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rates);

        LinearLayout ll_1 = (LinearLayout) findViewById(R.id.ll_1);
        LinearLayout ll_2 = (LinearLayout) findViewById(R.id.ll_2);
        ListView listView = (ListView) findViewById(R.id.exchange_listview);
        ListView listView2 = (ListView) findViewById(R.id.other_listview);
        TextView tvPortrait = (TextView) findViewById(R.id.tvPortrait);

        ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        ExchangeRate xChange = new ExchangeRate(this, listView, listView2, pb, ll_1, ll_2, tvPortrait);
        xChange.getRates();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view, menu);
        return true;
    }

    /**
     * Inside the menu the user can easily change the activities by selecting the menu items.
     * There are five cases inside the switch statement.
     * The user can go to the main page (home), to see the news, exchange rate, countries and comparing the countries.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_home:
                Intent intentHome = new Intent(ExchangeRatesActivity.this, MainViewActivity.class);
                this.startActivity(intentHome);
                break;
            case R.id.action_News:
                Intent intentNews = new Intent(ExchangeRatesActivity.this, NewsActivity.class);
                this.startActivity(intentNews);
                break;
            case R.id.action_Markets:
                Intent intentMarkets = new Intent(ExchangeRatesActivity.this, ExchangeRatesActivity.class);
                this.startActivity(intentMarkets);
                break;
            case R.id.action_Countries:
                Intent intentCountries = new Intent(ExchangeRatesActivity.this, CountrySelectionActivity.class);
                this.startActivity(intentCountries);
                break;
            case R.id.action_More:
                Intent intentMore = new Intent(ExchangeRatesActivity.this, ComparisonActivity.class);
                this.startActivity(intentMore);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
