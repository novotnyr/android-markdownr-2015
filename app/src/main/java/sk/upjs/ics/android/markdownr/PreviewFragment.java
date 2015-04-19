package sk.upjs.ics.android.markdownr;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class PreviewFragment extends Fragment {

    public static final String URL_ENCODING = null;

    private WebView previewWebView;

    public PreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_preview, container, false);

        this.previewWebView = (WebView) fragmentLayout.findViewById(R.id.previewWebView);
        this.previewWebView.loadData("<i>No Markdown source</i>", "text/html; charset=UTF-8", URL_ENCODING);

        return fragmentLayout;
    }
}
