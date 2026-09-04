package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private PantryAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rvPantry);
        tvEmpty = findViewById(R.id.tvEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<PantryItem> items = dbHelper.getAllItems();
        adapter = new PantryAdapter(items, item -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            intent.putExtra(AddEditIngredientActivity.EXTRA_ID, item.getId());
            intent.putExtra(AddEditIngredientActivity.EXTRA_NAME, item.getName());
            intent.putExtra(AddEditIngredientActivity.EXTRA_QUANTITY, item.getQuantity());
            intent.putExtra(AddEditIngredientActivity.EXTRA_UNIT, item.getUnit());
            intent.putExtra(AddEditIngredientActivity.EXTRA_EXPIRY, item.getExpiryDate());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        updateEmptyState(items);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list every time we come back to this screen
        List<PantryItem> items = dbHelper.getAllItems();
        adapter.updateData(items);
        updateEmptyState(items);
    }

    private void updateEmptyState(List<PantryItem> items) {
        if (items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }
}