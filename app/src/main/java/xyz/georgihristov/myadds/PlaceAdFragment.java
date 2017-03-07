package xyz.georgihristov.myadds;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static xyz.georgihristov.myadds.R.id.button;

/**
 * Created by gohv on 02.03.17.
 */
public class PlaceAdFragment extends Fragment{

    public static final String MY_AD = "MY_AD";
    @BindView(R.id.nameEditText)EditText nameEditText;
    @BindView(R.id.descriptionEditText)EditText descriptionEditText;
    @BindView(R.id.priceEditText)EditText priceEditText;
    @BindView(R.id.button) Button button;
    private String name;
    private String description;
    private String price;
    private Ad dataToSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ad_info_fragment_view,container,false);
        ButterKnife.bind(this,view);
        dataToSend = new Ad();
        name = "";
        if(name.length() == 0){
            button.setEnabled(false);
        }


                /*EDIT TEXT CHANGE LISTENERS :*/
        nameEditText.addTextChangedListener(new Validator(nameEditText) {
            @Override
            public void validate(EditText editText, String text) {
                name = editText.getText().toString();
                if(name.length() < 5){
                    button.setEnabled(false);
                }else{
                    button.setEnabled(true);
                    dataToSend.setProductName(name);
                }
            }
        });
        descriptionEditText.addTextChangedListener(new Validator(descriptionEditText) {
            @Override
            public void validate(EditText editText, String text) {
                description = editText.getText().toString();
                if (description.length() > 512){
                    Toast.makeText(getContext(), "No more than 512 Symbols", Toast.LENGTH_SHORT).show();
                    button.setEnabled(false);
                }else{
                    dataToSend.setProductDescription(description);
                }
            }
        });
        priceEditText.addTextChangedListener(new Validator(priceEditText) {
            @Override
            public void validate(EditText editText, String text) {
                price = editText.getText().toString();
                if(price.startsWith("0")){
                    Toast.makeText(getContext(), "Price will be invisible", Toast.LENGTH_SHORT).show();
                    price = " ";
                    dataToSend.setProductPrice(price);
                }else {
                    dataToSend.setProductPrice(price);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddPhotos.class);
                intent.putExtra(MY_AD,dataToSend);
                Log.d("TEST",dataToSend.toString());
                startActivity(intent);
            }
        });
        return view;
    }

}