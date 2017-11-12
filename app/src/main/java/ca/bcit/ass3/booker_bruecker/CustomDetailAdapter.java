package ca.bcit.ass3.booker_bruecker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haley on 11/11/2017.
 */

public class CustomDetailAdapter extends BaseAdapter implements ListAdapter {
    private List<String> list = new ArrayList<String>();
    private Context context;
    myDbAdapter helper;
    String eventID;


    public CustomDetailAdapter(List<String> list, Context context, String eventID) {
        this.list = list;
        this.context = context;
        helper = new myDbAdapter(context);
        this.eventID = eventID;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.two_button_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button editBtn = (Button)view.findViewById(R.id.add_btn);
        final TextView event = (TextView)view.findViewById(R.id.list_item_string);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            //do something
            View parent = (View) v.getParent();
            TextView tv = (TextView) parent.findViewById(R.id.list_item_string);
            String id = "" + tv.getText().charAt(0);
            helper.deleteDetails(id);
            notifyDataSetChanged();
            Intent i = new Intent(context, EventDetails.class);
            i.putExtra("EventID",eventID);
            context.startActivity(i);
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            View parent = (View) v.getParent();
            TextView tv = (TextView) parent.findViewById(R.id.list_item_string);
            String id = "" + tv.getText().charAt(0);
            Intent i = new Intent(context, EditEventDetails.class);
            i.putExtra("EventID",eventID);
            i.putExtra("EventDetailsID",id);
            context.startActivity(i);
            }
        });
        return view;
    }
}
