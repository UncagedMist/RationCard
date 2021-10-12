package tbc.uncagedmist.rationcard.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tbc.uncagedmist.rationcard.Adapter.DetailAdapter;
import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Database.MyDatabase;
import tbc.uncagedmist.rationcard.Model.Product;
import tbc.uncagedmist.rationcard.R;

public class DetailFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Product> productArrayList = new ArrayList<>();
    FloatingActionButton resultBack;

    View myFragment;

    Context context;

    @Override
    public void onAttach(Activity activity) {
        context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myFragment = inflater.inflate(R.layout.fragment_detail, container, false);

        recyclerView = myFragment.findViewById(R.id.recycler_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultBack = myFragment.findViewById(R.id.resultBack);

        Cursor cursor = new MyDatabase(
                getContext())
                .getAllProductsByStateId(Common.CurrentStateId);

        while (cursor.moveToNext()) {
            Product product = new Product(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            productArrayList.add(product);
        }

        DetailAdapter adapter = new DetailAdapter(context,productArrayList);

        recyclerView.setAdapter(adapter);

        resultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction =
                        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,homeFragment).commit();
            }
        });

        return myFragment;
    }
}