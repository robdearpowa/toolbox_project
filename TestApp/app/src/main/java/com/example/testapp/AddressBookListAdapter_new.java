package com.example.testapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

public class AddressBookListAdapter_new extends RecyclerView.Adapter<AddressBookListAdapter_new.ViewHolder> {

    private List<Contact> contacts;
    private Context context;

    public AddressBookListAdapter_new(List<Contact> contacts, Context context){
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = li.inflate(R.layout.contact_layout, null);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Contact contact = contacts.get(i);

        viewHolder.txtContactName.setText(contact.getName() + " " + contact.getLastname());
        viewHolder.txtContactInfo.setText(contact.getCellphone() + " " + contact.getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtContactName;
        private TextView txtContactInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtContactName = itemView.findViewById(R.id.txtContactName);
            txtContactInfo = itemView.findViewById(R.id.txtContactInfo);

        }
    }
}
