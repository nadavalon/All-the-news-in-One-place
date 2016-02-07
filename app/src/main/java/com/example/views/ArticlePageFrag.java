package com.example.views;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * this class will show us a the item from the list,including: the headliner,preview to the article and image.
 * Created by Nadav Alon on 03/02/2016.
 */
public class ArticlePageFrag extends Fragment{

    private ArticlesListFragListener listener;


    public interface ArticlesListFragListener {

    }

    // new instance of this fragment.
    public static ArticlePageFrag newInstance() {
        // create a fragment:
        ArticlePageFrag articlesPageFrag = new ArticlePageFrag();

        // return the prepared fragment:
        return articlesPageFrag;

    }

    // The fragment attach to the activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        // try to set the activity as the listener:
        try {
            listener = (ArticlesListFragListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement ArticlePageFragListener!");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_page_frag, container, false);
        // get the subviews and adding the data to them
        ImageView articlePageImage = (ImageView)view.findViewById(R.id.article_image);
        Picasso.with(getActivity()).load(getArguments().getString("image")).into(articlePageImage);

        TextView tvArticlePageHeadline = (TextView)view.findViewById(R.id.article_headline);
        tvArticlePageHeadline.setText(getArguments().getString("title"));
        TextView tvArticlePagePreview = (TextView)view.findViewById(R.id.article_preview);
        tvArticlePagePreview.setText(getArguments().getString("desc"));

        return view;
    }

    

}
