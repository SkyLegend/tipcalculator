package ca.practice.tipcalculator;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI(findViewById(R.id.parent));

        /* To Remove Logo after loading
        *
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);


        final Button calculate = (Button) findViewById(R.id.calculateButton);

        calculate.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                calculate();
            }
        });
        */
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

    public void calculate(){
        double amt;
        double tip;
        double percent;

        DecimalFormat df = new DecimalFormat("0.00");
        EditText amount = (EditText)findViewById(R.id.amountText);
        EditText percentage = (EditText)findViewById(R.id.percentText);
        TextView result = (TextView)findViewById(R.id.tipTextView);
        TextView warning = (TextView)findViewById(R.id.warningTextView);

        try{
            amt = Double.parseDouble(amount.getText().toString());
            percent = Double.parseDouble((percentage.getText().toString()));
            percent /= 100;
            tip = amt * percent;

            warning.setText("");

            result.setText("$" + df.format(tip));

        }catch(NumberFormatException ex){
            warning.setText(R.string.warning_text);
            result.setText(R.string.zero_start);
        }
    }
}
