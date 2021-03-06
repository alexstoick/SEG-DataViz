package com.team1_k.project.seg.dataviz.api.helper;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.team1_k.project.seg.dataviz.api.QueryBuilder;
import com.team1_k.project.seg.dataviz.data.DataVizContract.CountryEntry;
import com.team1_k.project.seg.dataviz.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexstoick on 11/23/14.
 */
public class CountryQuery {

    private static final String LOG_TAG = "api.helper.country";
    private Context mContext;

    public CountryQuery(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Builds a {@link android.content.ContentValues} object for the country passed to the function
     *
     * @param country the country which has to be inserted into the database
     * @return the formatted {@link android.content.ContentValues} object
     */
    private ContentValues createCountryContentValues(Country country) {
        ContentValues countryValues = new ContentValues();

        countryValues.put(CountryEntry.COLUMN_NAME, country.getName());
        countryValues.put(CountryEntry.COLUMN_API_ID, country.getApiId());
        countryValues.put(CountryEntry.COLUMN_CAPITAL_CITY, country.getCapitalCity());
        countryValues.put(CountryEntry.COLUMN_LONGITUDE, country.getLongitude());
        countryValues.put(CountryEntry.COLUMN_LATITUDE, country.getLatitude());
        Log.i(LOG_TAG, "country with" + country.getName());
        return countryValues;
    }

    private void parseCountries(JSONArray countries) throws JSONException {

        int length = countries.length();
        ContentValues[] bulkContentValues = new ContentValues[length];

        for (int i = 0; i < length; ++i) {
            JSONObject country = countries.getJSONObject(i);
            bulkContentValues[i] = createCountryContentValues(new Country(country));
        }
        mContext.getContentResolver()
                .bulkInsert(CountryEntry.CONTENT_URI, bulkContentValues);
    }

    /**
     * Creates an async HTTP request for the request API resource.
     *
     * @param page takes the page for the request
     */
    public void asyncCountryRequestWithPage(int page) {
        String url = QueryBuilder.API_BASE_URL + "country?region=WLD" + "&" +
                "page=" + String.valueOf(page) + "&" + QueryBuilder.JSON_FORMAT;

        AsyncHttpClient.getDefaultInstance().getString(url, new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(Exception e, AsyncHttpResponse response, String result) {
                if (e != null) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, e.toString());
                    return;
                }
                try {
                    JSONArray array = new JSONArray(result);
                    JSONObject page_info = array.getJSONObject(0);
                    JSONArray countries = array.getJSONArray(1);
                    parseCountries(countries);
                } catch (JSONException ex) {
                    Log.e(LOG_TAG, ex.toString());
                    return;
                }
            }
        });
    }
}
