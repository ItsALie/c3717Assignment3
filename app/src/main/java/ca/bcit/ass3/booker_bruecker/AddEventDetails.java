package ca.bcit.ass3.booker_bruecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEventDetails extends AppCompatActivity {
    myDbAdapter helper;
    String eventID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_details);
        helper = new myDbAdapter(this);
        Intent intent = getIntent();
        String eventID  = intent.getStringExtra("EventID");
        this.eventID = eventID;
    }

    public void onAdd(View v){
        EditText name = (EditText)findViewById(R.id.EditEventName);
        EditText unit = (EditText)findViewById(R.id.EditEventUnits);
        EditText quantity = (EditText)findViewById(R.id.EditEventQuantity);
        helper = new myDbAdapter(getApplicationContext());
        if(name.getText() == null || unit.getText() == null || quantity.getText() == null || eventID == null)
        {
            Toast.makeText(getApplicationContext(), "Enter Both Name and Date and Time", Toast.LENGTH_LONG).show();
        }
        else
        {
            long id = helper.insertEventDetails(name.getText().toString(),unit.getText().toString(), quantity.getText().toString(), eventID);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(), "Insertion Unsuccessful", Toast.LENGTH_LONG).show();
            } else
            {
                name.setText("");
                unit.setText("");
                quantity.setText("");
            }
        }
        Intent i = new Intent(this, EventDetails.class);
        i.putExtra("EventID", eventID);
        startActivity(i);
    }
    public void goBack(View v) {
        Intent i = new Intent(this, EventDetails.class);
        i.putExtra("EventID", eventID);
        startActivity(i);
    }
}
