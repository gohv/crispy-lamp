package xyz.georgihristov.myadds;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * Created by gohv on 03.03.17.
 */

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.categorySpinner)Spinner categorySpinner;
    @BindView(R.id.subCategorySpinner)Spinner subCategorySpinner;
    @BindView(R.id.button) Button button;
    private ArrayAdapter<CharSequence> categoryAdapter;
    private ArrayAdapter<CharSequence> subCategoryAdapter;
    private String category;
    private String subCategory;
    private Ad dataToSend;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dataToSend = (Ad) getIntent().getParcelableExtra(PlaceAdFragment.MY_AD);

        categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array,
                android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);
        subCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCategory = parent.getItemAtPosition(position).toString();
                dataToSend.setSubCategory(subCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
        dataToSend.setCategory(category);
        if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Please Select Main Category")){
            Toast.makeText(this, "Please Select Main Category", Toast.LENGTH_SHORT).show();
            setCategory(R.array.empty_array);
            button.setEnabled(false);
        }else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Services")){
            setCategory(R.array.services_array);
            enableButton();
        }else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Vehicles")){
            setCategory(R.array.vehicles_array);
            enableButton();
        }else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("For Sale")){
            setCategory(R.array.for_sale_array);
            enableButton();
        }else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Property")){
            setCategory(R.array.property_array);
            enableButton();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
       //not used
    }
    private void setCategory(int id){
        subCategoryAdapter = ArrayAdapter.createFromResource(this,
                id,
                android.R.layout.simple_spinner_item);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategorySpinner.setAdapter(subCategoryAdapter);
    }
    @OnClick(R.id.button) void next(){
        Intent intent = new Intent(this,ContactsActivity.class);
        intent.putExtra(PlaceAdFragment.MY_AD,dataToSend);
        Log.d("TEST",dataToSend.toString());

        startActivity(intent);
    }

    private void enableButton(){
        button.setEnabled(true);
    }

}
