package sk.upjs.ics.android.markdownr;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MainActivity extends Activity {

    public static final String DEFAULT_BACKSTACK_NAME = null;

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

    private void showPreviewPane() {
        EditText sourceEditText = (EditText) findViewById(R.id.sourceEditText);
        if(sourceEditText == null) {
            Log.w(MainActivity.class.getName(), "HTML source fragment is not loaded");
            return;
        }

        String htmlSource = sourceEditText.getText().toString();

        PreviewFragment previewFragment = PreviewFragment.newInstance(htmlSource);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.singleFragmentLayout, previewFragment)
                .addToBackStack(DEFAULT_BACKSTACK_NAME)
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
        switch(id) {
            case R.id.sourceAction:
                showSourcePane();
                return true;
            case R.id.previewAction:
                showPreviewPane();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
