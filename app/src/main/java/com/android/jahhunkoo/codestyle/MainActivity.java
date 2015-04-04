package com.android.jahhunkoo.codestyle;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.android.jahhunkoo.codestyle.fragment.SampleListFragment;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            SampleListFragment fragment = SampleListFragment.getInstance(getSupportFragmentManager());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, SampleListFragment.TAG)
                    .commit();
        }

    }

}
