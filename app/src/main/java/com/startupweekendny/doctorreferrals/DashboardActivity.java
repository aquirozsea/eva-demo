package com.startupweekendny.doctorreferrals;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MapboxTileLayer;
import com.mapbox.mapboxsdk.views.MapView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends Activity implements DocListFragment.OnFragmentInteractionListener {

//    MapFragment map = null;
    Fragment map = null;
    DocListFragment docList = null;

    MapView mapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

//        docList = new DocListFragment();

        final Context context = this;

        ActionBar.Tab tab = actionBar.newTab()
                .setText("Map view")
                .setTabListener(new ActionBar.TabListener() {

                    @Override
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                        if (map == null) {
//                            map = MapFragment.newInstance();
//                            map.getMapAsync(new OnMapReadyCallback() {
//                                @Override
//                                public void onMapReady(GoogleMap googleMap) {
//                                    googleMap.setMyLocationEnabled(true);
//                                }
//                            });
                            map = new Fragment() {
                                @Nullable
                                @Override
                                public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                                    mapView = new MapView(context);
                                    mapView.setAccessToken("pk.eyJ1IjoiZHJyZWZlcnJhbHMiLCJhIjoiZGQyNTA0ODI4N2M0M2EzNjdlYzY0Yzk4YWViZDFiNmEifQ.W6lxbfLoBrQtWhMSOms7sg");
                                    mapView.setTileSource(new MapboxTileLayer("drreferrals.mepg04kl"));
                                    mapView.setCenter(new LatLng(40.78, -73.96));
                                    mapView.setZoom(13f);
//                                    mapView.addMarkers();
                                    return mapView;
                                }
                            };
                            ft.add(R.id.tab_frame, map);
                        }
                        else {
                            ft.attach(map);
                        }
                    }

                    @Override
                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                        if (map != null) {
                            ft.detach(map);
                        }
                    }

                    @Override
                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                });
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText("Doctors view")
                .setTabListener(new ActionBar.TabListener() {

                    @Override
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                        if (docList == null) {
                            docList = new DocListFragment();
                            ft.add(R.id.tab_frame, docList);
//                            ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorReferrals");
//                            query.findInBackground(new FindCallback<ParseObject>() {
//                                @Override
//                                public void done(List<ParseObject> list, ParseException e) {
//                                    if (e == null) {
//                                        docList.setData(list);
//                                    }
//                                }
//                            });
                        }
                        else {
                            ft.attach(docList);
                        }
                    }

                    @Override
                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                        if (docList != null) {
                            ft.detach(docList);
                        }
                    }

                    @Override
                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                });
        actionBar.addTab(tab);

        addViewMarkers();

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorReferrals");
//        query.getInBackground("htadBWZL7u", new GetCallback<ParseObject>() {
//            public void done(ParseObject object, ParseException e) {
//                if (e == null) {
//                    Toast.makeText(context, object.getString("FirstName"), Toast.LENGTH_LONG).show();
//                } else {
//                    // something went wrong
//                }
//            }
//        });
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    Toast.makeText(context, Integer.toString(list.size()), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchSelected(View view) {
        Intent startSearchIntent = new Intent(this, SearchActivity.class);
        startActivity(startSearchIntent);
    }

    public void statsSelected(View view) {
        Intent startStatsIntent = new Intent(this, StatsActivity.class);
        startActivity(startStatsIntent);
    }

    public void profileSelected(View view) {
        Intent startProfileIntent = new Intent(this, ProfileActivity.class);
        startActivity(startProfileIntent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void addViewMarkers() {
        new AsyncTask<Void, Void, JSONArray>() {

            @Override
            protected JSONArray doInBackground(Void... params) {
                InputStream inputStream = null;
                String result = "";
                try {

                    // create HttpClient
                    HttpClient httpclient = new DefaultHttpClient();

                    // make GET request to the given URL
                    HttpResponse httpResponse = httpclient.execute(new HttpGet("http://a.tiles.mapbox.com/v4/drreferrals.mepg04kl/features.json?access_token=pk.eyJ1IjoiZHJyZWZlcnJhbHMiLCJhIjoiZGQyNTA0ODI4N2M0M2EzNjdlYzY0Yzk4YWViZDFiNmEifQ.W6lxbfLoBrQtWhMSOms7sg"));

                    // receive response as inputStream
                    inputStream = httpResponse.getEntity().getContent();

                    // convert inputstream to string
                    if(inputStream != null)
                        result = convertInputStreamToString(inputStream);
                    else
                        result = "Did not work!";

                } catch (Exception e) {
                }

                JSONArray ret = null;
                try {
                    JSONObject featureCollection = new JSONObject(result);
                    ret = featureCollection.getJSONArray("features");
                }
                catch (Exception e) {
                }

                return ret;
            }

            @Override
            protected void onPostExecute(JSONArray features) {
                try {
                    List<Marker> markers = new ArrayList<Marker>();
                    for (int i = 0; i < features.length(); i++) {
                        JSONObject feature = (JSONObject) features.get(i);
                        JSONArray coordinates = feature.getJSONObject("geometry").getJSONArray("coordinates");
                        JSONObject properties = feature.getJSONObject("properties");
                        String name = properties.getString("FirstName") + " " + properties.getString("LastName");
                        String specialty = properties.getString("Specialty");
                        markers.add(new Marker(name, specialty, new LatLng(coordinates.getDouble(1), coordinates.getDouble(0))));
                    }
                    mapView.addMarkers(markers);
                }
                catch (Exception e) {

                }
            }
        }.execute();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
