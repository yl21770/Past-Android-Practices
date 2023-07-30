package iss.workshop.webview;

import android.content.Intent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest resourceRequest){
        String hostname = resourceRequest.getUrl().getHost();
        if("www.iss.nus.edu.sg".equals(hostname))
            return false;

        Intent intent = new Intent(Intent.ACTION_VIEW, resourceRequest.getUrl());
        view.getContext().startActivity(intent);
        return true;
    }
}
