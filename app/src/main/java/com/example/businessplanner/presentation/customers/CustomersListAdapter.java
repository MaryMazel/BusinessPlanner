package com.example.businessplanner.presentation.customers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.data.entities.Plan;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CustomersListAdapter extends RecyclerView.Adapter<CustomersListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Customer> customers;
    private DatabaseManager manager;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Customer customer);
    }

    CustomersListAdapter(Context context, List<Customer> customers, OnItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.manager = new DatabaseManager(context);
        this.listener = listener;
        this.customers = customers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.customers_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(customers.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return customers == null ? 0 : customers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView customersImage;
        final ImageView delete;
        final TextView customersName;
        final TextView customersPhone;
        final TextView customersEmail;
        final TextView customersAddress;
        final TextView profit;
        final TextView dealDate;
        final TextView state;

        void bind(final Customer customer, final OnItemClickListener listener) {
            customersName.setText(customer.customer_name);
            customersPhone.setText(customer.phone);
            customersEmail.setText(customer.email);
            customersAddress.setText(customer.address);
            profit.setText(String.valueOf(customer.profit));
            dealDate.setText(formatDate(customer.deal_date));
            state.setText(customer.state.toString());

            delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation")
                        .setMessage("Do you really want to delete a customer: '" + customer.customer_name + "'?")
                        .setNegativeButton("No",
                                (dialog, id) -> dialog.cancel())
                        .setPositiveButton("Yes", (dialog, which) -> {
                            manager.deleteCustomer(customer.id);
                            customers.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        });
                AlertDialog alert = builder.create();
                alert.show();
            });
            itemView.setOnClickListener(v -> listener.onItemClick(customer));
        }

        public String formatDate(long date) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            return format.format(date);
        }

        ViewHolder(View view) {
            super(view);
            customersImage = view.findViewById(R.id.customer_image);
            delete = view.findViewById(R.id.delete_customer);
            customersName = view.findViewById(R.id.customer_name);
            customersEmail = view.findViewById(R.id.email_card);
            customersAddress = view.findViewById(R.id.customers_address);
            customersPhone = view.findViewById(R.id.phone_number);
            profit = view.findViewById(R.id.profit);
            dealDate = view.findViewById(R.id.deal_date);
            state = view.findViewById(R.id.state);
        }
    }
}

