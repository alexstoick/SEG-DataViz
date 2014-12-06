package com.team1_k.project.seg.dataviz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class ComparisonCountrySelectionActivity extends Activity {

    private static final String LOG_TAG = "ui.comparison.country_selection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_country_selection);
        final ListView listView = (ListView) findViewById(R.id.countryListWithCheckbox);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                new String[]{"test", "test2", "test", "test2", "test", "test2", "test", "test2", "test", "test2", "test", "test2"});
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        Button goButton = (Button) findViewById(R.id.comparisonGoButton);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SparseBooleanArray array = listView.getCheckedItemPositions();
                for (int i = 0; i < array.size(); ++i) {
                    Log.d(LOG_TAG, String.valueOf(array.keyAt(i)));
                }
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
                Intent intentHome = new Intent(ComparisonCountrySelectionActivity.this, MainViewActivity.class);
                this.startActivity(intentHome);
                break;
            case R.id.action_News:
                Intent intentNews = new Intent(ComparisonCountrySelectionActivity.this, NewsActivity.class);
                this.startActivity(intentNews);
                break;
            case R.id.action_Markets:
                Intent intentMarkets = new Intent(ComparisonCountrySelectionActivity.this, ExchangeRatesActivity.class);
                this.startActivity(intentMarkets);
                break;
            case R.id.action_Countries:
                Intent intentCountries = new Intent(ComparisonCountrySelectionActivity.this, CountrySelectionActivity.class);
                this.startActivity(intentCountries);
                break;
            case R.id.action_More:
                Intent intentMore = new Intent(ComparisonCountrySelectionActivity.this, ComparisonActivity.class);
                this.startActivity(intentMore);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
