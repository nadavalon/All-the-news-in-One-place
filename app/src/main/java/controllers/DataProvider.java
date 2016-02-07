package controllers;

import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Objects.Article;

/**
 * this is a singletone which will provide the data from the web using volley
 * Created by Nadav Alon on 04/02/2016.
 */
public class DataProvider {

    private RequestQueue requestQueue;

    static DataProvider instance;

    public static enum Providers{
        YNET,WALLA
    }

    public interface OnArticlesReadyListener{
        void onReady(List<Article> list);
        void onError();
    }

    public static DataProvider getInstance(Context context) {
        if (instance == null) instance = new DataProvider(context);
        return instance;
    }

    private DataProvider(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void loadProvider(String url, final Providers provider, final OnArticlesReadyListener callback) {
        StringRequest stringRequest = new UTFStringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                YnetXmlParser parser = null;
                switch (provider){
                    case YNET:
                        parser = new YnetXmlParser();
                        break;
                    case WALLA:
                        //TODO
                        break;
                }
                try {
                    List<Article> listwithsrc = parser.parse(new ByteArrayInputStream(response.getBytes()),true);
                    List<Article> listwithdescription = parser.parse(new ByteArrayInputStream(response.getBytes()),false);
                    List<Article> fulllist = new ArrayList<>();
                    for(int i=0; i < listwithsrc.size(); i++){
                       Article itemwithsrc = listwithsrc.get(i);
                       Article itemwithdescription = listwithdescription.get(i);
                        itemwithsrc.description = itemwithdescription.getDescription();
                        fulllist.add(itemwithsrc);
                    }
                    callback.onReady(fulllist);
                    return;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.onError();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //ToDo
                Log.e("error",error.toString());
                callback.onError();
            }
        });
        requestQueue.add(stringRequest);
    }
}



