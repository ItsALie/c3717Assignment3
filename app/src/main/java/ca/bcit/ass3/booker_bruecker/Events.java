package ca.bcit.ass3.booker_bruecker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class Events extends AppCompatActivity {
    myDbAdapter helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ListView listOfEvents = (ListView) findViewById(R.id.list_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        helper = new myDbAdapter(this);
        System.out.println(helper.getData());

        final List<String> itemList = Arrays.asList(helper.getData().split("\n"));
        System.out.println(itemList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listOfEvents.setAdapter(arrayAdapter);

        listOfEvents.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                String itemname = itemList.get(position);
                Toast.makeText(Events.this, "" + itemname.charAt(0), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Events.this, EventDetails.class);
                i.putExtra("EventID", "" + itemname.charAt(0));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event:
                Intent i = new Intent(Events.this, AddEvent.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
