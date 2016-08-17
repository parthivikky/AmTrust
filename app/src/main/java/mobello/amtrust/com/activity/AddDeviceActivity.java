package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.Choose;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_DEVICE_CATEGORY = 100;
    private static final int REQUEST_DEVICE_BRAND = 101;
    private static final int REQUEST_DEVICE_PRODUCT = 102;

    private RelativeLayout categoryContainer, brandContainer, productContainer, imeiContainer, priceContainer, purchaseDateContainer,
            invoiceContainer, startDateContainer;
    private TextView txtCategory, txtBrand, txtProduct;
    private EditText etIMEI, etPrice, etPurchaseDate, etInvoice, etStartDate;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, AddDeviceActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ((TextView) findViewById(R.id.title)).setText(getString(R.string.add_new_device));
        initViews();
    }

    private void initViews() {
        categoryContainer = _findViewById(R.id.category_container);
        brandContainer = _findViewById(R.id.brand_container);
        productContainer = _findViewById(R.id.product_container);
        imeiContainer = _findViewById(R.id.imei_container);
        priceContainer = _findViewById(R.id.price_container);
        purchaseDateContainer = _findViewById(R.id.purchase_date_container);
        invoiceContainer = _findViewById(R.id.invoice_container);
        startDateContainer = _findViewById(R.id.start_date_container);
        txtCategory = _findViewById(R.id.category);
        txtBrand = _findViewById(R.id.brand);
        txtProduct = _findViewById(R.id.product);
        etIMEI = _findViewById(R.id.imei);
        etPrice = _findViewById(R.id.price);
        etInvoice = _findViewById(R.id.invoice);
        etPurchaseDate = _findViewById(R.id.purchase_date);
        etStartDate = _findViewById(R.id.start_date);
        categoryContainer.setOnClickListener(this);
        brandContainer.setOnClickListener(this);
        productContainer.setOnClickListener(this);
        imeiContainer.setOnClickListener(this);
        priceContainer.setOnClickListener(this);
        purchaseDateContainer.setOnClickListener(this);
        invoiceContainer.setOnClickListener(this);
        startDateContainer.setOnClickListener(this);
    }

    private <T extends View> T _findViewById(int resId) {
        return (T) findViewById(resId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category_container:
                ChooserActivity.startForResult(AddDeviceActivity.this, getCategories(), REQUEST_DEVICE_CATEGORY);
                break;
            case R.id.brand_container:
                ChooserActivity.startForResult(AddDeviceActivity.this, getBrands(), REQUEST_DEVICE_BRAND);
                break;
            case R.id.product_container:
                ChooserActivity.startForResult(AddDeviceActivity.this, getProducts(), REQUEST_DEVICE_PRODUCT);
                break;
            case R.id.imei_container:
                focus(etIMEI);
                break;
            case R.id.price_container:
                focus(etPrice);
                break;
            case R.id.purchase_date_container:
                focus(etPurchaseDate);
                break;
            case R.id.invoice_container:
                focus(etInvoice);
                break;
            case R.id.start_date_container:
                focus(etStartDate);
                break;
        }
    }

    private void focus(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DEVICE_CATEGORY:
                    txtCategory.setText(data.getStringExtra("name"));
                    break;
                case REQUEST_DEVICE_BRAND:
                    txtBrand.setText(data.getStringExtra("name"));
                    break;
                case REQUEST_DEVICE_PRODUCT:
                    txtProduct.setText(data.getStringExtra("name"));
                    break;
            }
        }
    }

    private String getCategories() {
        LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Mobile", false);
        linkedHashMap.put("Television", false);
        linkedHashMap.put("Refrigerator", false);
        linkedHashMap.put("Washing Machine", false);
        linkedHashMap.put("Laptop", false);

        if (!TextUtils.isEmpty(txtCategory.getText().toString()))
            linkedHashMap.put(txtCategory.getText().toString(), true);
        return new Gson().toJson(linkedHashMap);
    }

    private String getBrands() {
        LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Apple", false);
        linkedHashMap.put("Samsung", false);
        linkedHashMap.put("HTC", false);
        linkedHashMap.put("Blackberry", false);
        linkedHashMap.put("Motorola", false);

        if (!TextUtils.isEmpty(txtBrand.getText().toString()))
            linkedHashMap.put(txtBrand.getText().toString(), true);
        return new Gson().toJson(linkedHashMap);
    }

    private String getProducts() {
        LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Moto E", false);
        linkedHashMap.put("Moto G3", false);
        linkedHashMap.put("Moto G4", false);
        linkedHashMap.put("Moto G4 Plus", false);
        linkedHashMap.put("Moto X", false);

        if (!TextUtils.isEmpty(txtProduct.getText().toString()))
            linkedHashMap.put(txtProduct.getText().toString(), true);
        return new Gson().toJson(linkedHashMap);
    }
}
