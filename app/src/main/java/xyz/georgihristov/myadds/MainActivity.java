package xyz.georgihristov.myadds;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)TabLayout tabs;
    @BindView(R.id.pager)ViewPager pager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title)TextView toolbarTitle;
    private Ad ad;
    //private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(),this);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        setupTabs();

    }

    private void setupTabs() {
        tabs.getTabAt(0).setIcon(R.drawable.ic_my_ads_prs);
        tabs.getTabAt(1).setIcon(R.drawable.ic_place_ads);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabs.getTabAt(0) == tab){
                    tabs.getTabAt(0).setIcon(R.drawable.ic_my_ads_prs);
                }if(tabs.getTabAt(1) ==  tab){

                    tabs.getTabAt(1).setIcon(R.drawable.ic_place_ads_prs);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tabs.getTabAt(0) == tab){
                    tabs.getTabAt(0).setIcon(R.drawable.ic_my_ads);

                }if(tabs.getTabAt(1) ==  tab){
                    tabs.getTabAt(1).setIcon(R.drawable.ic_place_ads);


                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    /*DELETE REALM INSTANCE HERE !*/

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        realm = Realm.getDefaultInstance();
        realm.close();
        Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Ad.class);
            }
        });
    }*/
}
