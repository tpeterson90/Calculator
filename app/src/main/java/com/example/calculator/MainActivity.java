package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * This is the main activity of the calculator app.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Variables
    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonPlus, buttonMinus, buttonTimes, buttonDivide;
    MaterialButton buttonEquals, buttonDot, buttonAc;


    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                            previously being shut down then this Bundle contains the data it most
     *                            recently supplied in {@link #onSaveInstanceState}. Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);


//        Number Buttons
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);

//        Operation Buttons
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonMinus, R.id.button_minus);
        assignID(buttonTimes, R.id.button_times);
        assignID(buttonEquals, R.id.button_equals);
        assignID(buttonDot, R.id.button_dot);
        assignID(buttonAc, R.id.button_ac);
        assignID(buttonC, R.id.button_c);
        assignID(buttonBracketOpen, R.id.button_open_bracket);
        assignID(buttonBracketClose, R.id.button_close_bracket);
        assignID(buttonDivide, R.id.button_divide);

    }

    /**
     * Assigns ID to the buttons and sets the onclick listener
     * @param btn MaterialButton to be assigned
     * @param id ID of the button
     */
    void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    /**
     * Is the view that shows the result of the calculation
     * Clears the solution and the result when AC is pressed
     * Clears the last character when C is pressed
     * Evaluates the expression and displays the result when = is pressed
     * @param view The view that was clicked.
     *
     */

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();

        if(buttonText.equals("AC")){
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }

        if(buttonText.equals("=")){
            solutionTV.setText(resultTV.getText());
            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        } else {
            dataToCalculate = dataToCalculate+buttonText;
        }

        solutionTV.setText(dataToCalculate);

        String finalResult = getResults(dataToCalculate);
        if(!finalResult.equals("Error")){
            resultTV.setText(finalResult);
        }
    }

    /**
     * Takes the string expression and evaluates it and returns the result
     * @param data String expression to be evaluated
     * @return String expression result
     */

    String getResults(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
        }catch (Exception e){
            return "Error";
        }
    }
}