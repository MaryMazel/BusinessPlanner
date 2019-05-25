package com.example.businessplanner.presentation.orders;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businessplanner.R;
import com.example.businessplanner.data.entities.Order;
import com.example.businessplanner.domain.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Order> orders;
    private DatabaseManager manager;
    private Context context;
    private OnItemClickListener listener;

    OrdersListAdapter(Context context, List<Order> orders, OnItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.manager = new DatabaseManager(context);
        this.listener = listener;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.orders_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(orders.get(i), listener, i);
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumber;
        TextView customerName;
        TextView address;
        TextView description;
        TextView orderType;
        TextView networkOwner;
        TextView dealDate;
        TextView capacity;
        TextView voltage;
        TextView reliability;
        TextView attachmentSpot;
        TextView meterType;
        TextView transformers;
        TextView state;

        ImageView delete;

        ViewHolder(View view) {
            super(view);
            orderNumber = view.findViewById(R.id.order_number);
            customerName = view.findViewById(R.id.order_customer);
            address = view.findViewById(R.id.order_address);
            description = view.findViewById(R.id.order_description);
            orderType = view.findViewById(R.id.order_type);
            networkOwner = view.findViewById(R.id.order_network_owner);
            dealDate = view.findViewById(R.id.order_deal_date);
            capacity = view.findViewById(R.id.order_capacity);
            voltage = view.findViewById(R.id.order_voltage);
            reliability = view.findViewById(R.id.order_reliability);
            attachmentSpot = view.findViewById(R.id.order_attachment_spot);
            meterType = view.findViewById(R.id.order_meter_type);
            transformers = view.findViewById(R.id.order_transformers);
            state = view.findViewById(R.id.order_state);
            delete = view.findViewById(R.id.delete_order_in_list);
        }

        void bind(final Order order, final OnItemClickListener listener, int i) {
            orderNumber.setText(String.valueOf(i + 1));
            customerName.setText(manager.getCustomerName(order.customerID));
            address.setText(order.address);
            if (!order.description.equals("0")) {
                description.setText(order.description);
            } else {
                description.setText("------");
            }

            orderType.setText(order.orderType.toString());
            networkOwner.setText(order.networkOwner.toString());
            if (!(order.deal_date == 0)) {
                dealDate.setText(formatDate(order.deal_date));
            } else {
                dealDate.setText("------");
            }

            if (!(order.capacity == 0)) {
                capacity.setText(String.valueOf(order.capacity));
            } else {
                capacity.setText("------");
            }

            if (!(order.voltage == 0)) {
                voltage.setText(String.valueOf(order.voltage));
            } else {
                voltage.setText("------");
            }

            switch (order.reliability) {
                case 0:
                    reliability.setText("------");
                    break;
                case 1:
                    reliability.setText("I");
                    break;
                case 2:
                    reliability.setText("II");
                    break;
                case 3:
                    reliability.setText("III");
                    break;
            }

            if (!order.attachmentSpot.equals("0")) {
                attachmentSpot.setText(order.attachmentSpot);
            } else {
                attachmentSpot.setText("------");
            }

            if (!order.meterType.equals("0")) {
                meterType.setText(order.meterType);
            } else {
                meterType.setText("------");
            }

            if (!(order.transformers == 0)) {
                transformers.setText(String.valueOf(order.transformers));
            } else {
                transformers.setText("------");
            }

            state.setText(order.state.toString());

            itemView.setOnClickListener(v -> listener.onItemClick(order));

            delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation")
                        .setMessage("Do you really want to delete an order?")
                        .setNegativeButton("No",
                                (dialog, id) -> dialog.cancel())
                        .setPositiveButton("Yes", (dialog, which) -> {
                            manager.deleteOrder(order.id);
                            orders.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        });
                AlertDialog alert = builder.create();
                alert.show();
            });
        }

        String formatDate(long date) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            return format.format(date);
        }
    }
}

