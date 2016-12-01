package com.android.trail.homepage;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.trail.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Activity mactivity;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            mactivity = getActivity();

            rootView.findViewById(R.id.telId1).setOnClickListener(onclick);
            rootView.findViewById(R.id.telId2).setOnClickListener(onclick);
            rootView.findViewById(R.id.telId3).setOnClickListener(onclick);
            rootView.findViewById(R.id.telId4).setOnClickListener(onclick);
            rootView.findViewById(R.id.telId5).setOnClickListener(onclick);


            return rootView;
        }


        public View.OnClickListener onclick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(mactivity, v.getTag().toString(), 1*1000).show();

            }
        };
    }
}
