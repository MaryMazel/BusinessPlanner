package com.example.businessplanner.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.businessplanner.R;
import com.example.businessplanner.presentation.calendar.CalendarFragment;
import com.example.businessplanner.presentation.customers.CustomersFragment;
import com.example.businessplanner.presentation.orders.OrdersFragment;
import com.example.businessplanner.presentation.plans.PlansFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarFragment calendarFragment = new CalendarFragment();
        replaceFragment(calendarFragment);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);

            if (menuItem.getItemId() == R.id.orders) {
                OrdersFragment ordersFragment = new OrdersFragment();
                replaceFragment(ordersFragment);
            } else if (menuItem.getItemId() == R.id.customers) {
                CustomersFragment customersFragment = new CustomersFragment();
                replaceFragment(customersFragment);
            } else if (menuItem.getItemId() == R.id.calendar) {
                CalendarFragment calendarFragment = new CalendarFragment();
                replaceFragment(calendarFragment);
            } else if (menuItem.getItemId() == R.id.plans) {
                PlansFragment plansFragment = new PlansFragment();
                replaceFragment(plansFragment);
            }
            drawerLayout.closeDrawers();

            return true;
        }
    };

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void showMenu() {
        drawerLayout.openDrawer(Gravity.START, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
