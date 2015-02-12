package jp.ouroboros.userdictionaryapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView mListView;
    private View.OnClickListener mAddBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private SimpleCursorAdapter mSimpleCursorAdapter;
    private ArrayList<String> mWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.imageButton).setOnClickListener(mAddBtnListener);
        load();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void load() {
        getSupportLoaderManager().initLoader(0, null, this);
        String[] from = new String[]{
                UserDictionary.Words.SHORTCUT,
                UserDictionary.Words.WORD
        };
        int[] to = new int[]{
                R.id.index,
                R.id.word
        };
        mSimpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item, null,
                from, to,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mListView.setAdapter(mSimpleCursorAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                UserDictionary.Words.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Cursor old = mSimpleCursorAdapter.swapCursor(cursor);
        if (old != null) {
            old.close();
        }
        if (cursor == null) {
            mWordList = null;
            return;
        }
        mWordList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                mWordList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        Cursor old = mSimpleCursorAdapter.swapCursor(null);
        if (old != null) {
            old.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(0);
    }
}
