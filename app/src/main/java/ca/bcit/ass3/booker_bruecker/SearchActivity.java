package ca.bcit.ass3.booker_bruecker;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    myDbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView listOfEvents = (ListView) findViewById(R.id.list_events);
        helper = new myDbAdapter(this);

        String searchQuery = getIntent().getStringExtra(SearchManager.QUERY);

        final List<String> itemList = Arrays.asList(helper.getNameSearch(searchQuery).split("\n"));

        if(helper.getNameSearch(searchQuery).length() != 0) {
            myCustomAdapter adapter = new myCustomAdapter(itemList, this);
            listOfEvents.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_LONG).show();
        }


    }
}
