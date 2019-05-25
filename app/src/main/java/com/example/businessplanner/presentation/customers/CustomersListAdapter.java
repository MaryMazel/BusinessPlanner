package com.example.businessplanner.presentation.customers;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Customer;
import com.example.businessplanner.domain.DatabaseManager;

import java.util.List;

public class CustomersListAdapter extends RecyclerView.Adapter<CustomersListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Customer> customers;
    private DatabaseManager manager;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Customer customer);

        void onCustomerClick(Customer customer);
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
        final ImageView edit;
        final TextView customersName;
        final TextView customersPhone;
        final TextView customersEmail;

        void bind(final Customer customer, final OnItemClickListener listener) {
            if (customer.imageName.equals("no picture")) {
                Glide.with(context)
                        .load(R.drawable.round_shape_with_gradient)
                        .apply(RequestOptions.circleCropTransform())
                        .into(customersImage);
            } else {
                Glide.with(context)
                        .load(Integer.parseInt(customer.imageName))
                        .apply(RequestOptions.circleCropTransform())
                        .into(customersImage);
            }

            customersName.setText(customer.customer_name);
            customersPhone.setText(customer.phone);

            if (!customer.email.equals("0")) {
                customersEmail.setText(customer.email);
            } else {
                customersEmail.setText("------");
            }

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

            edit.setOnClickListener(v -> listener.onEditClick(customer));

            itemView.setOnClickListener(v -> listener.onCustomerClick(customer));
        }

        ViewHolder(View view) {
            super(view);
            customersImage = view.findViewById(R.id.customer_image);
            delete = view.findViewById(R.id.delete_customer);
            customersName = view.findViewById(R.id.customer_name);
            customersEmail = view.findViewById(R.id.email_card);
            customersPhone = view.findViewById(R.id.phone_number);
            edit = view.findViewById(R.id.edit_customer);
        }
    }
}

