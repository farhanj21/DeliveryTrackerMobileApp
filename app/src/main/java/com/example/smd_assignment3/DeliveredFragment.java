package com.example.smd_assignment3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DeliveredFragment extends Fragment {

    Context context;
    ArrayList<Product> deliveredProducts = new ArrayList<Product>();
    private DeliveredAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DeliveredFragment() {
        // Required empty public constructor
    }

    public static DeliveredFragment newInstance(String param1, String param2) {
        DeliveredFragment fragment = new DeliveredFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delivered, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lvDeliveredOrdersList = view.findViewById(R.id.lvDeliveredOrdersList);

        ProductDB productDB = new ProductDB(context);
        productDB.open();
        deliveredProducts = productDB.fetchProducts("delivered");
        for (Product product : deliveredProducts) {
            Log.d("ProductInfo", "ID: " + product.getId() + ", Title: " + product.getTitle() +
                    ", Date: " + product.getDate() + ", Price: " + product.getPrice() +
                    ", Status: " + product.getStatus());
        }
        productDB.close();

            adapter = new DeliveredAdapter(context, R.layout.confirmed_item_design, deliveredProducts);
        lvDeliveredOrdersList.setAdapter(adapter);

    }

    public void addProductToDeliver(Product p){
        adapter.addDeliverProduct(p);
        Toast.makeText(context, "Item Moved to Delivery", Toast.LENGTH_SHORT).show();
    }
}