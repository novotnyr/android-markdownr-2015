package sk.upjs.ics.android.markdownr;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SourceFragment extends Fragment {
    private static final String PREFERENCES_KEY_DRAFT_SOURCE = "draftSource";
    public static final String DEFAULT_HTML_SOURCE = "";

    private EditText sourceEditText;

    public SourceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_source, container, false);
        sourceEditText = (EditText) getView().findViewById(R.id.sourceEditText);
        return frameLayout;
    }

    @Override
    public void onPause() {
        getPreferences().edit()
                .putString(PREFERENCES_KEY_DRAFT_SOURCE, sourceEditText.getText().toString())
                .commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        String htmlSource = getPreferences().getString(PREFERENCES_KEY_DRAFT_SOURCE, DEFAULT_HTML_SOURCE);
        sourceEditText.setText(htmlSource);
    }

    private SharedPreferences getPreferences() {
        return getActivity()
                .getPreferences(Activity.MODE_PRIVATE);
    }

}
