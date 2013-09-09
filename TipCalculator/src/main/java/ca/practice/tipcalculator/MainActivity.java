package ca.practice.tipcalculator;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity {

    public DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI(findViewById(R.id.parent));

        final EditText amount = (EditText)findViewById(R.id.amountText);
        final EditText percentage = (EditText)findViewById(R.id.percentText);
        final TextView result = (TextView)findViewById(R.id.tipTextView);
        final TextView warning = (TextView)findViewById(R.id.warningTextView);


        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                calculate(amount, percentage, warning, result);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                calculate(amount, percentage, warning, result);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */

    /*
     * Code is very useful to remove the soft keyboard when the edit text loses focus.
     */
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public void calculate(EditText amount, EditText percentage, TextView warn, TextView result){
        double amt;
        double tip;
        double percent;

        try{
            amt = Double.parseDouble(amount.getText().toString());
            percent = Double.parseDouble((percentage.getText().toString()));
            percent /= 100;
            tip = amt * percent;

            warn.setText("");

            result.setText("$" + df.format(tip));

        }catch(NumberFormatException ex){
            warn.setText(R.string.warning_text);
            result.setText(R.string.zero_start);
        }
    }
}
