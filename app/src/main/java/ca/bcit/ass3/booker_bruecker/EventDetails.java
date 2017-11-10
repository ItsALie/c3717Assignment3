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

        helper = new myDbAdapter(this);

        /*List<String> itemList = Arrays.asList(helper.getDetailData().split("\n"));
        System.out.println(itemList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listOfEvents.setAdapter(arrayAdapter);

        listOfEvents.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Toast.makeText(EventDetails.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });*/
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
