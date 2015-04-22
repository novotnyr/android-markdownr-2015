package sk.upjs.ics.android.markdownr;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SourceFragment extends Fragment {
    private static final String PREFERENCES_KEY_DRAFT_SOURCE = "draftSource";
    public static final String DEFAULT_HTML_SOURCE = "";

    private EditText sourceEditText;

    private SourceChangedListener sourceChangedListener;

    public SourceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof SourceChangedListener) {
            setSourceChangedListener((SourceChangedListener) activity);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_source, container, false);
        sourceEditText = (EditText) frameLayout.findViewById(R.id.sourceEditText);
        addSourceTextWatcher();
        return frameLayout;
    }

    private void addSourceTextWatcher() {
        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                notifySourceChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // empty implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // empty implementation
            }
        });
    }

    private void notifySourceChanged() {
        PreviewFragment previewFragment = (PreviewFragment) getFragmentManager().findFragmentById(R.id.previewFragment);
        if(previewFragment == null) {
            return;
        }
        previewFragment.setHtmlSource(sourceEditText.getText().toString());
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

    public interface SourceChangedListener {
        public void onSourceChanged(String source);
    }

    public void setSourceChangedListener(SourceChangedListener sourceChangedListener) {
        this.sourceChangedListener = sourceChangedListener;
    }
}
