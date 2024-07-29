package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    private String currentNumber = "";
    private String operator = "";
    private double firstNumber = 0;
    private double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.result_text);
        setNumberButtonListeners();
        setOperationButtonListeners();
        setSpecialFunctionListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberIds = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_dot};

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    currentNumber += button.getText().toString();
                    resultText.setText(currentNumber);
                }
            });
        }
    }

    private void setOperationButtonListeners() {
        int[] opIds = {R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide};

        for (int id : opIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    if (!currentNumber.isEmpty()) {
                        firstNumber = Double.parseDouble(currentNumber);
                        currentNumber = "";
                        operator = button.getText().toString();
                    }
                }
            });
        }

        findViewById(R.id.btn_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty() && !operator.isEmpty()) {
                    calculateResult();
                    resultText.setText(String.valueOf(result));
                    currentNumber = String.valueOf(result);
                    operator = "";
                }
            }
        });
    }

    private void setSpecialFunctionListeners() {
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = "";
                operator = "";
                firstNumber = 0;
                result = 0;
                resultText.setText("0");
            }
        });

        int[] trigIds = {R.id.btn_sin, R.id.btn_cos, R.id.btn_tan};
        for (int id : trigIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    if (!currentNumber.isEmpty()) {
                        double number = Double.parseDouble(currentNumber);
                        switch (button.getText().toString()) {
                            case "sin":
                                result = Math.sin(Math.toRadians(number));
                                break;
                            case "cos":
                                result = Math.cos(Math.toRadians(number));
                                break;
                            case "tan":
                                result = Math.tan(Math.toRadians(number));
                                break;
                        }
                        resultText.setText(String.valueOf(result));
                        currentNumber = String.valueOf(result);
                    }
                }
            });
        }
    }

    private void calculateResult() {
        double secondNumber = Double.parseDouble(currentNumber);
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    resultText.setText("Error");
                    return;
                }
                break;
        }
    }
}