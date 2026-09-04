package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_QUANTITY = "extra_quantity";
    public static final String EXTRA_UNIT = "extra_unit";
    public static final String EXTRA_EXPIRY = "extra_expiry";

    private DatabaseHelper dbHelper;
    private EditText etName, etQuantity, etUnit, etExpiry;
    private int existingId = -1; // -1 means "adding new", not editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        // Check if we were opened to EDIT an existing item
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            existingId = intent.getIntExtra(EXTRA_ID, -1);
            etName.setText(intent.getStringExtra(EXTRA_NAME));
            etQuantity.setText(String.valueOf(intent.getDoubleExtra(EXTRA_QUANTITY, 0)));
            etUnit.setText(intent.getStringExtra(EXTRA_UNIT));
            etExpiry.setText(intent.getStringExtra(EXTRA_EXPIRY));
            btnDelete.setVisibility(android.view.View.VISIBLE);
        }

        btnSave.setOnClickListener(v -> saveItem());
        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteItem(existingId);
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        // --- Validation ---
        if (name.isEmpty()) {
            etName.setError("Ingredient name is required");
            return;
        }
        if (quantityStr.isEmpty()) {
            etQuantity.setError("Quantity is required");
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            etQuantity.setError("Enter a valid number");
            return;
        }
        if (quantity <= 0) {
            etQuantity.setError("Quantity must be greater than 0");
            return;
        }
        if (unit.isEmpty()) {
            etUnit.setError("Unit is required (e.g. g, ml, pcs)");
            return;
        }
        // --- End validation ---

        if (existingId == -1) {
            dbHelper.insertItem(name, quantity, unit, expiry);
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updateItem(existingId, name, quantity, unit, expiry);
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}