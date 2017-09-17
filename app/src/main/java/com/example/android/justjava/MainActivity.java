package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        displayQuantity(++quantity);
        displayPrice();
    }

    public void decrement(View view) {
        if(quantity == 0) {
            displayQuantity(0);
        } else {
            displayQuantity(--quantity);
        }
        displayPrice();
    }

    public boolean hasWhippedCream() {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return whippedCream.isChecked();
    }

    public boolean hasChocolate() {
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return chocolate.isChecked();
    }

    private int calculatePrice() {
        int coffeePrice = 5;
        return quantity * coffeePrice;
    }

    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantity));

    }

    private void displayPrice() {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(calculatePrice()));
    }

    public void submitOrder(View view) {
        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        displayOrderCompleteMessage(name);
    }

    private void displayOrderCompleteMessage(String name) {
        StringBuilder orderText = new StringBuilder("Name: " + name + ".\nCoffees ordered: " + quantity + "\n");
        double whippedCreamPrice = 0.5;
        int chocolate = 1;
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        double totalPrice = calculatePrice();
        if(hasWhippedCream()) {
            totalPrice += whippedCreamPrice;
            orderText.append("Please add whipped cream.\n");
        }
        if(hasChocolate()) {
            totalPrice += chocolate;
            orderText.append("Please add chocolate.\n");
        }
        orderText.append("Total price: " + NumberFormat.getCurrencyInstance().format(totalPrice));
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "t_nakova_1991@gmail.bg");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderText.toString());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        priceTextView.setText("Thank you " + name + "!\nYou ordered " + quantity + " coffees.\nTotal amount: "
                + NumberFormat.getCurrencyInstance().format(totalPrice));
    }

}
