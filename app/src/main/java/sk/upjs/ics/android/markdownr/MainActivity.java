package sk.upjs.ics.android.markdownr;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.StringReader;
import java.io.StringWriter;


public class MainActivity extends Activity implements SourceFragment.SourceChangedListener{

    public static final String DEFAULT_BACKSTACK_NAME = null;
    public static final String FRAGMENT_TAG_SOURCE = "SourceFragment";

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
        SourceFragment sourceFragment = (SourceFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG_SOURCE);
        if(sourceFragment == null) {
            sourceFragment = new SourceFragment();
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.singleFragmentLayout, sourceFragment, FRAGMENT_TAG_SOURCE)
                .commit();
    }

    private void showPreviewPane() {
        EditText sourceEditText = (EditText) findViewById(R.id.sourceEditText);
        if(sourceEditText == null) {
            Log.w(MainActivity.class.getName(), "HTML source fragment is not loaded");
            return;
        }

        String htmlSource = toHtml(sourceEditText.getText().toString());

        PreviewFragment previewFragment = PreviewFragment.newInstance(htmlSource);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.singleFragmentLayout, previewFragment)
                .addToBackStack(DEFAULT_BACKSTACK_NAME)
                .commit();
    }

    private boolean isSourceFragmentShown() {
        return findViewById(R.id.sourceEditText) != null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem sourceActionItem = menu.findItem(R.id.sourceAction);
        MenuItem previewActionItem = menu.findItem(R.id.previewAction);

        if(isSinglePane()) {
            if(isSourceFragmentShown()) {
                sourceActionItem.setVisible(false);
            } else {
                previewActionItem.setVisible(false);
            }
        } else {
            sourceActionItem.setVisible(false);
            previewActionItem.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.sourceAction:
                showSourcePane();
                invalidateOptionsMenu();
                return true;
            case R.id.previewAction:
                showPreviewPane();
                invalidateOptionsMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSourceChanged(String source) {
        PreviewFragment previewFragment = (PreviewFragment) getFragmentManager()
                .findFragmentById(R.id.previewFragment);
        if(previewFragment == null) {
            return;
        }
        previewFragment.setHtmlSource(toHtml(source));
    }

    public String toHtml(String markdown) {
        try {
            StringReader input = new StringReader(markdown);
            StringWriter output = new StringWriter();

            Markdown markdownConverter = new Markdown();
            markdownConverter.transform(input, output);

            return output.toString();
        } catch (ParseException e) {
            return "Syntax error";
        }
    }
}
