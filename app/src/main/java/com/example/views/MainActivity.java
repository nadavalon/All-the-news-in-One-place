package com.example.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import Objects.Article;
import controllers.DataProvider;


public class MainActivity extends BaseActivity implements ArticlesListFrag.ArticlesListFragListener {

    private String action;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//create the fragments
                    ArticlesListFrag articlesListFrag = new ArticlesListFrag();

//get manager:
                    FragmentManager fm = getFragmentManager();
// add it to the container:
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.frag_container, articlesListFrag, "tag");

                    //commit the changes to add to the view
                    ft.commit();

             }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void moveToAnotherFrag(Article article) {
        //create the fragments
        ArticlePageFrag articlePageFrag = new ArticlePageFrag();
        Bundle bundle = new Bundle();
        bundle.putString("title", article.getTitle());
        bundle.putString("desc", article.getDescription());
        bundle.putString("image", article.getImage());
        articlePageFrag.setArguments(bundle);


//get manager:
        FragmentManager fm = getFragmentManager();
// add it to the container:
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_container, articlePageFrag, "tag");
        ft.addToBackStack("bsTag");

        //commit the changes to add to the view
        ft.commit();
    }

    @Override
    public void onReady(List<Article> list) {
        super.onReady(list);
        Fragment fragment = getFragmentManager().findFragmentByTag("tag");
        if(fragment instanceof DataProvider.OnArticlesReadyListener){
            ((DataProvider.OnArticlesReadyListener) fragment).onReady(list);
        }
    }

    @Override
    public void onError() {
        super.onError();
        Fragment fragment = getFragmentManager().findFragmentByTag("tag");
        if(fragment instanceof DataProvider.OnArticlesReadyListener){
            ((DataProvider.OnArticlesReadyListener) fragment).onError();
        }
    }
}
