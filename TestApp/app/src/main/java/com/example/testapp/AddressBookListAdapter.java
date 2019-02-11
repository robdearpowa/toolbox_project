package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AddressBookListAdapter extends BaseAdapter {

    private List<Contact> contacts;
    private Context context;

    public AddressBookListAdapter(List<Contact> contacs, Context contex){
        this.contacts = contacs;
        this.context = contex;
    }

    @Override
    public int getCount() {
        return contacts != null ? contacts.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = li.inflate(R.layout.contact_layout, null);

        TextView txtContactName = convertView.findViewById(R.id.txtContactName);
        TextView txtContactInfo = convertView.findViewById(R.id.txtContactInfo);

        Contact contact = contacts.get(position);

        txtContactName.setText(contact.getName() + " " + contact.getLastname());
        txtContactInfo.setText(contact.getCellphone() + " " + contact.getEmail());

        return convertView;
    }
}
