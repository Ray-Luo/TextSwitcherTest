package com.raystone.ray.snappytransition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.snappy_container);
        if(fragment == null)
        {fragment = SnappyFragment.newInstance();}
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.snappy_container,fragment,"SNAPPYFRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
