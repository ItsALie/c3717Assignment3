package ca.bcit.ass3.booker_bruecker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEvent extends AppCompatActivity {
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        helper = new myDbAdapter(this);
    }

    public void onAdd(View v){
        EditText name = (EditText)findViewById(R.id.EditEventName);
        EditText date = (EditText)findViewById(R.id.EditEventDate);
        EditText time = (EditText)findViewById(R.id.EditEventTime);
        helper = new myDbAdapter(getApplicationContext());
        if(name.getText() == null || date.getText() == null || time.getText() == null)
        {
            Toast.makeText(getApplicationContext(), "Enter Both Name and Date and Time", Toast.LENGTH_LONG).show();
        }
        else
        {
            long id = helper.insertEvent(name.getText().toString(),date.getText().toString(), time.getText().toString());
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(), "Insertion Unsuccessful", Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(getApplicationContext(), "Insertion Successful", Toast.LENGTH_LONG).show();
                name.setText("");
                date.setText("");
                time.setText("");
            }
        }

        Intent i = new Intent(this, Events.class);
        startActivity(i);
    }
}
