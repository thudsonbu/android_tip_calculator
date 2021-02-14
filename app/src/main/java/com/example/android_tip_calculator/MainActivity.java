package com.example.android_tip_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    // inputs
    private EditText checkAmount;
    private EditText people;
    private EditText tipPercentage; // stored as whole number ( will need conversion )

    // displays
    private TextView totalBill;
    private TextView totalPerPerson;
    private TextView totalTip;
    private TextView tipPerPerson;

    // calculate button
    private Button calculateButton;

    // formatter
    private static DecimalFormat df = new DecimalFormat("##,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign inputs
        checkAmount   = (EditText)findViewById(R.id.checkAmount);
        people        = (EditText)findViewById(R.id.people);
        tipPercentage = (EditText)findViewById(R.id.tipPercentage);

        // assign outputs
        totalBill      = (TextView)findViewById(R.id.totalBill);
        totalPerPerson = (TextView)findViewById(R.id.totalPerPerson);
        totalTip       = (TextView)findViewById(R.id.totalTip);
        tipPerPerson   = (TextView)findViewById(R.id.tipPerPerson);

        // assign button
        calculateButton = (Button)findViewById(R.id.calculateButton);

        // add button listener
        calculateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               double[] values = validateValues();

               if ( values.length == 0 ) return;

               double[] calculatedValues = calculateValues( values );

               setValues( calculatedValues );
            }
        });
    }

    /**
     * Validate the input by attempting to cast it to double. Sends a Toast if the
     * cast fails.
     *
     * @return       Array of type double made up of calculated values in order
     *               total bill, total per person, total tip, and tip per person
     *               or an empty double[] if failure.
     */
    private double[] validateValues() {
        try {
            double _checkAmount = Double.parseDouble(checkAmount.getText().toString());
            double _people = Double.parseDouble(people.getText().toString());
            double _tipPercentage = Double.parseDouble(tipPercentage.getText().toString());

            _people = _people < 1 ? 1 : _people; // people cannot be less then 1

            double[] out = { _checkAmount, _people, _tipPercentage };

            return out;

        } catch (Error err) {
            // Send message after invalid input
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
        }

        return new double[3]; // return empty if fail
    }

    /**
     * Calculate the values based on double[] input
     *
     * @param values Array of type double containing in order the check amount,
     *               the number of people paying the bill, and the tip percentage
     *               to be applied.
     * @return       Array of type double made up of calculated values in order
     *               total bill, total per person, total tip, and tip per person
     */
    private double[] calculateValues( double[] values ) {
        // make calculation
        double _totalTip = round(  values[0] * ( values[2] / 100 ) , 2 );
        double _totalBill = round( _totalTip + values[0] , 2 );
        double _totalPerPerson = round(  _totalBill / values[1] , 2 );
        double _tipPerPerson = round(  _totalTip / values[1] , 2 );
        // return values
        double[] out = { _totalBill, _totalPerPerson, _totalTip, _tipPerPerson };
        return out;
    }

    /**
     * Set the output displays to the inserted double[]
     *
     * @param values Array of type double containing in order the check amount,
     *               the number of people paying the bill, and the tip percentage
     *               to be applied.
     */
    private void setValues( double[] values ) {
        totalBill.setText( getString(R.string.dollar_formatter, df.format( values[0] )));
        totalPerPerson.setText( getString(R.string.dollar_formatter, df.format( values[1] )));
        totalTip.setText( getString(R.string.dollar_formatter, df.format( values[2] )));
        tipPerPerson.setText( getString(R.string.dollar_formatter, df.format( values[3] )));
    }

    /**
     * Rounding utility
     *
     * @param value     value to be rounded
     * @param places    number of places to be rounded to
     * @return          double rounded to specified places
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}