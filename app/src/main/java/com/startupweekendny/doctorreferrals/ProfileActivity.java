package com.startupweekendny.doctorreferrals;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends Activity {

    ListAdapter adapter;
    ListView insuranceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_profile);

        String[] insuranceProviders = new String[] {"Aetna", "Cigna", "Blue Cross Blue Shield"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, insuranceProviders);

        insuranceList = (ListView) findViewById(R.id.profile_insurance_list);
        insuranceList.setAdapter(adapter);
        insuranceList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
}
