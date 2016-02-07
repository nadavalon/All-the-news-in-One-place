package com.example.views;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Objects.Article;
import Objects.Provider;
import controllers.DataProvider;

/**
 * this class will be a base activity that will be inherited to all other activities in order to have the drawer
 * Created by Nadav Alon on 04/02/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DataProvider.OnArticlesReadyListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void setContentView(int viewresource) {

       ViewGroup baseview = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_base, null, false);
        ViewGroup activityview = (ViewGroup) baseview.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(viewresource, activityview, true);
        super.setContentView(baseview);

        mDrawerLayout = (DrawerLayout) baseview.findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.open,
                R.string.close){


            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("News");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Providers");
            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setProviders();

    }

    private void setProviders() {
        ListView lvproviders = (ListView)findViewById(R.id.left_drawer);

        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider("http://www.ynet.co.il/Integration/StoryRss2.xml","Ynet",R.drawable.ynet_icon, DataProvider.Providers.YNET));
        lvproviders.setAdapter(new ProviderAdapter(this, providers));
        lvproviders.setOnItemClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView)findViewById(R.id.left_drawer);
        Provider provider = (Provider) lv.getAdapter().getItem(position);
        DataProvider.getInstance(this).loadProvider(provider.getUrl(), provider.getProviderType(), this);

    }

    @Override
    public void onReady(List<Article> list) {

    }

    @Override
    public void onError() {

    }


    private class ProviderAdapter extends ArrayAdapter<Provider>{

        public ProviderAdapter(Context context,List<Provider> providers) {
            super(context, R.layout.list_item_baseactivity,providers);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.list_item_baseactivity, parent,false);
                }
          Provider provider = getItem(position);

            // bind the data to the sub-views
            TextView name = (TextView)convertView.findViewById(R.id.name);
            name.setText(provider.getName());
            ImageView icon= (ImageView)convertView.findViewById(R.id.icon);
            icon.setImageResource(provider.getImage());

            return convertView;
        }
    }


}
