package sk.upjs.ics.android.markdownr;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class PreviewFragment extends Fragment {

    public static final String URL_ENCODING = null;
    public static final String ARG_HTML_SOURCE = "HTML_SOURCE";

    private WebView previewWebView;

    public PreviewFragment() {
        // Required empty public constructor
    }

    public static PreviewFragment newInstance(String htmlSource) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_HTML_SOURCE, htmlSource);

        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_preview, container, false);

        this.previewWebView = (WebView) fragmentLayout.findViewById(R.id.previewWebView);
        this.previewWebView.loadData(getHtmlSource(), "text/html; charset=UTF-8", URL_ENCODING);

        return fragmentLayout;
    }

    private String getHtmlSource() {
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey(ARG_HTML_SOURCE)) {
            return arguments.getString(ARG_HTML_SOURCE);
        }
        return "<i>No Markdown source</i>";
    }
}
