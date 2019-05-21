package com.example.businessplanner.presentation.orders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        ViewHolder(View view) {
            super(view);
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
        }

        void bind(final Order order, final OnItemClickListener listener, int i) {
            orderNumber.setText(i + 1);
            customerName.setText(manager.getCustomerName(order.customerID));
            address.setText(order.address);
            description.setText(order.description);
            orderType.setText(order.orderType.toString());
            networkOwner.setText(order.networkOwner.toString());
            dealDate.setText(formatDate(order.deal_date));
            capacity.setText(String.valueOf(order.capacity));
            voltage.setText(String.valueOf(order.voltage));
            reliability.setText(String.valueOf(order.reliability));
            attachmentSpot.setText(order.attachmentSpot);
            meterType.setText(order.meterType);
            transformers.setText(order.transformers);
            state.setText(order.state.toString());
            itemView.setOnClickListener(v -> listener.onItemClick(order));
            /*if (!customer.address.equals("0")) {
                customersAddress.setText(customer.address);
            } else {
                customersAddress.setText("no address");
            }

            if (customer.profit != 0) {
                profit.setText(String.valueOf(customer.profit));
            } else {
                profit.setText("no profit");
            }

            if (customer.deal_date == 0) {
                dealDate.setText("no date");
            } else {
                dealDate.setText(formatDate(customer.deal_date));
            }

            state.setText(customer.state.toString());
*/
        }

        String formatDate(long date) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            return format.format(date);
        }
    }
}

