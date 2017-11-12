package ca.bcit.ass3.booker_bruecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditEvent extends AppCompatActivity {
    myDbAdapter helper;
    String eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        helper = new myDbAdapter(this);
        Intent intent = getIntent();
        String eventID  = intent.getStringExtra("EventID");
        this.eventID = eventID;
        String eventName = helper.getName(eventID);
        String eventDate = helper.getDate(eventID);
        String eventTime = helper.getTime(eventID);

        TextView name = (TextView)findViewById(R.id.EditEventName);
        name.setText(eventName);
        TextView date = (TextView)findViewById(R.id.EditEventDate);
        date.setText(eventDate);
        TextView time = (TextView)findViewById(R.id.EditEventTime);
        time.setText(eventTime);
    }

    public void onUpdate(View v){
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
            long id = helper.updateEvent(eventID, name.getText().toString(),date.getText().toString(), time.getText().toString());
            if(id<=0)
            {                Toast.makeText(getApplicationContext(), "Insertion Unsuccessful", Toast.LENGTH_LONG).show();

            } else
            {
                name.setText("");
                date.setText("");
                time.setText("");
            }
        }

        Intent i = new Intent(this, Events.class);
        startActivity(i);
    }

    public void goBack(View v) {
        Intent i = new Intent(this, Events.class);
        startActivity(i);
    }
}
