package com.team1_k.project.seg.dataviz.activities.comparison;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.team1_k.project.seg.dataviz.activities.country.CountrySelectionActivity;
import com.team1_k.project.seg.dataviz.exchange_rate.ExchangeRatesActivity;
import com.team1_k.project.seg.dataviz.activities.base.MainViewActivity;
import com.team1_k.project.seg.dataviz.news.NewsActivity;
import com.team1_k.project.seg.dataviz.R;
import com.team1_k.project.seg.dataviz.adapters.MetricArrayAdapter;
import com.team1_k.project.seg.dataviz.model.Client;
import com.team1_k.project.seg.dataviz.model.Metric;


public class ComparisonActivity extends Activity {

    private Metric[] mMetrics;
    private Client mClient;
    private static final String LOG_TAG = "ui.comparison";

    private void fetchClient() {
        mClient = new Client(Client.Type.INVESTOR);
    }

    private void fetchMetrics() {
        String[] metric_api_ids = mClient.getType().getInterests();
        int length = metric_api_ids.length;
        mMetrics = new Metric[length];
        try {
            for (int i = 0; i < length; ++i) {
                mMetrics[i] = Metric.getMetricWithApiId(getApplicationContext(), metric_api_ids[i]);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);
        fetchClient();
        fetchMetrics();
        ListView listView = (ListView) findViewById(R.id.comparisonList);
        listView.setAdapter(new MetricArrayAdapter(
                        getApplicationContext(),
                        R.layout.list_row_comparison,
                        mMetrics
                )
        );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        CountryComparisonSelectionActivity.class)
                        .putExtra(
                                CountryComparisonSelectionActivity.TAG_METRIC_DATABASE_ID,
                                mMetrics[i].getDatabaseId())
                        .putExtra(
                                CountryComparisonSelectionActivity.TAG_METRIC_API_ID,
                                mMetrics[i].getApiId()
                        );
                startActivity(intent);
            }
        });
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_home:
                Intent intentHome = new Intent(ComparisonActivity.this, MainViewActivity.class);
                this.startActivity(intentHome);
                break;
            case R.id.action_News:
                Intent intentNews = new Intent(ComparisonActivity.this, NewsActivity.class);
                this.startActivity(intentNews);
                break;
            case R.id.action_Markets:
                Intent intentMarkets = new Intent(ComparisonActivity.this, ExchangeRatesActivity.class);
                this.startActivity(intentMarkets);
                break;
            case R.id.action_Countries:
                Intent intentCountries = new Intent(ComparisonActivity.this, CountrySelectionActivity.class);
                this.startActivity(intentCountries);
                break;
            case R.id.action_More:
                Intent intentMore = new Intent(ComparisonActivity.this, ComparisonActivity.class);
                this.startActivity(intentMore);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
