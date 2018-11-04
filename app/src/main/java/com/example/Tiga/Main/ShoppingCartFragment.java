package com.example.Tiga.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private List<Book> books = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GetDataForShoppingCart();

        TextView tv_totalPrices = getActivity().findViewById(R.id.tv_totalPrices);
        tv_totalPrices.setText("  总价:" + String.valueOf(totalPrices()));
        getActivity().findViewById(R.id.btn_pay).setOnClickListener(this);

        RecyclerView rv_ShoppingCart = getActivity().findViewById(R.id.rv_ShoppingCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_ShoppingCart.setLayoutManager(linearLayoutManager);
        ShoppingCartAdapter adapter = new ShoppingCartAdapter(books);
        rv_ShoppingCart.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(100);
        defaultItemAnimator.setRemoveDuration(100);
        rv_ShoppingCart.setItemAnimator(defaultItemAnimator);
        rv_ShoppingCart.setAdapter(adapter);
    }

    //接受购物车数据
    private void GetDataForShoppingCart() {
        Bundle bundle = getArguments();
        books = bundle.getParcelableArrayList("BooksForShoppingCart");
    }

    //计算购物车总价
    private double totalPrices() {
        double totalPrices = 0.0;
        for (int i = 0; i < books.size(); i++) {
            totalPrices += books.get(i).getPrice();
        }
        return totalPrices;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                Toast.makeText(v.getContext(), "结算功能正在开发中!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
