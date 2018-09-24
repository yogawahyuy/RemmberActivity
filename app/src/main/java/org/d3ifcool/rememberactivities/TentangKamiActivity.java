package org.d3ifcool.rememberactivities;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class TentangKamiActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<TentangKami>>{
    private static final String JSON_RESPONSE="https://api.myjson.com/bins/1dkc0z";
    private TentangKami mtentangKamiAdapter;
    private TextView mEmptyStateTextView;
    private static final int SAVE_LOADER_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_kami);
    }

    @Override
    public Loader<List<TentangKami>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<TentangKami>> loader, List<TentangKami> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<TentangKami>> loader) {

    }
}
