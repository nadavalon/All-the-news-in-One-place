package com.example.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Objects.Article;
import controllers.DataProvider;

/**
 * Created by Nadav Alon on 03/02/2016.
 */
public class ArticlesListFrag extends Fragment implements AdapterView.OnItemClickListener, DataProvider.OnArticlesReadyListener{
    public List list;
    private ArticlesListFragListener listener;
    private ListView lv;
    private ArticlesAdapter adapter;

    private List<Article> data;

    //a listener to communicate with the activity
    public interface ArticlesListFragListener {
        void moveToAnotherFrag(Article article);

    }

    // new instance of this fragment.
    public static ArticlesListFrag newInstance() {
        // create a fragment:
        ArticlesListFrag articlesListFrag = new ArticlesListFrag();

        // return the prepared fragment:
        return articlesListFrag;

    }

    // The fragment attach to the activity
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);


        // try to set the activity as the listener:
        try {
            listener = (ArticlesListFragListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement ArticlesListFragListener!");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_frag, container, false);

        //getting the list
        lv = (ListView) view.findViewById(R.id.articles_list);
        //setting the click listener on the list items
        lv.setOnItemClickListener(this);

        return view;
    }

    //pressing any item on the list will trigger the main activity to show the second frag
    @Override
    public void onItemClick(AdapterView<?> list, View itemview, int pos, long id) {

        //send request to the main activity to move to another fragment
        if(listener != null)listener.moveToAnotherFrag(data.get(pos));
    }

    // The Adapter for the list
    class ArticlesAdapter extends ArrayAdapter<Article>{

        public ArticlesAdapter(Context context, List<Article> list) {
            super(context, R.layout.list_item_articles, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //create the view
            View view = convertView;
            if (view == null){
                view = getActivity().getLayoutInflater().inflate(R.layout.list_item_articles, parent,false);
            }

            Article article = getItem(position);

            // bind the data to the sub-views
            TextView tvlistheadline = (TextView) view.findViewById(R.id.list_item_article_headline);
            tvlistheadline.setText(article.getTitle());
            TextView tvlistpreview = (TextView) view.findViewById(R.id.list_item_article_preview);
            tvlistpreview.setText(article.getDescription());
            ImageView listimage = (ImageView) view.findViewById(R.id.list_image);
            Picasso.with(getActivity()).load(article.getImage()).into(listimage);

            return view;

        }
    }

    @Override
    public void onReady(List<Article> list) {
        data = list;
        lv.setAdapter(new ArticlesAdapter(getActivity(), data));
    }

    @Override
    public void onError() {
        lv.setAdapter(null);
    }
}
