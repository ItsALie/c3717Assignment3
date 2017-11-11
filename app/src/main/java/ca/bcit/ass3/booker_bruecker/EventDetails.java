package ca.bcit.ass3.booker_bruecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class EventDetails extends AppCompatActivity {
    myDbAdapter helper;
    Intent prevIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        ListView listOfEvents = (ListView) findViewById(R.id.list_event_details);
        prevIntent = getIntent();
        String eventID = prevIntent.getStringExtra("EventID");
        Toast.makeText(getApplicationContext(), "" + eventID, Toast.LENGTH_LONG).show();

        helper = new myDbAdapter(this);

        List<String> itemList = Arrays.asList(helper.getDetailData(eventID).split("\n"));
        System.out.println(itemList);

        if(helper.getDetailData(eventID).length() != 0) {
            CustomDetailAdapter adapter = new CustomDetailAdapter(itemList, this, eventID);
            listOfEvents.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_details, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event_details:
                Intent i = new Intent(EventDetails.this, AddEventDetails.class);
                i.putExtra("EventID", prevIntent.getStringExtra("EventID"));
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
