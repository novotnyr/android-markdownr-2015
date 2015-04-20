package sk.upjs.ics.android.markdownr;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            if (isSinglePane()) {
                showSourcePane();
            }
        }
    }

    public boolean isSinglePane() {
        return findViewById(R.id.singleFragmentLayout) != null;
    }

    private void showSourcePane() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.singleFragmentLayout, new SourceFragment())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
