package xyz.georgihristov.myadds;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by gohv on 05.03.17.
 */

public abstract class Validator implements TextWatcher{
    private EditText editText;

    public Validator(EditText editText) {
        this.editText = editText;
    }

    public abstract void validate(EditText editText,String text);

    @Override
    public void afterTextChanged(Editable s) {
        String text = editText.getText().toString();
        validate(editText,text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //not used
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //not used
    }
}
