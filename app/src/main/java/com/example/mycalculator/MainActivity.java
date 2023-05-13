package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText inputarea;
    String temp="";
    Integer count=0;
    private int curpos;
    private int expLen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputarea = findViewById(R.id.expressionenter);
        inputarea.setShowSoftInputOnFocus(false);

        inputarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getString(R.string.expressionenter).equals(inputarea.getText().toString())){
                    inputarea.setText("");
                }
            }
        });
    }


    private void expressionchange(String text){
        curpos = inputarea.getSelectionStart();
        if(getString(R.string.expressionenter).equals(inputarea.getText().toString())){
            inputarea.setText("");
            inputarea.setSelection(curpos);
//            return;
        }
        String exp = inputarea.getText().toString();
        String LPart = exp.substring(0,curpos);
        String RPart = exp.substring(curpos,exp.length());
        inputarea.setText(String.format("%s%s%s",LPart,text,RPart));
        inputarea.setSelection(curpos + 1);
        temp=inputarea.toString();
    }


    public void ZeroFunc(View view) {
        expressionchange("0");
    }
    public void OneFunc(View view) {
        expressionchange("1");
    }
    public void TwoFunc(View view) {
        expressionchange("2");
    }
    public void ThreeFunc(View view) {
        expressionchange("3");
    }
    public void FourFunc(View view) {
        expressionchange("4");
    }
    public void FiveFunc(View view) {
        expressionchange("5");
    }
    public void SixFunc(View view) {
        expressionchange("6");
    }
    public void SevenFunc(View view) {
        expressionchange("7");
    }
    public void EightFunc(View view) {
        expressionchange("8");
    }
    public void NineFunc(View view) {
        expressionchange("9");
    }


    public void DivideFunc(View view) {
        expressionchange("÷");
    }
    public void MultiplyFunc(View view) {
        expressionchange("×");
    }
    public void MinusFunc(View view) {
        expressionchange("-");
    }
    public void PlusFunc(View view) {
        expressionchange("+");
    }


    public void ParenthesisFunc(View view) {
        curpos = inputarea.getSelectionStart();
        int noofLeftBrackets = 0;
        int noofRightBrackets = 0;
        expLen = inputarea.getText().length();
        for(int i=0 ; i<curpos ; i++){
            if(inputarea.getText().toString().substring( i, i+1).equals("(")){
                noofLeftBrackets = noofLeftBrackets + 1;
            }
            if(inputarea.getText().toString().substring( i, i+1).equals(")")){
                noofRightBrackets = noofRightBrackets + 1;
            }
        }
        if(noofLeftBrackets == noofRightBrackets || inputarea.getText().toString().substring(expLen - 1, expLen).equals("(")){
            expressionchange("(");
            inputarea.setSelection(curpos + 1);
        }
        else if(noofLeftBrackets > noofRightBrackets && !inputarea.getText().toString().substring(expLen - 1, expLen).equals("(")){
            expressionchange(")");
            inputarea.setSelection(curpos + 1);
        }
    }
    public void ExponentFunc(View view) {
        expressionchange("^");
    }
    public void ModFunc(View view) {
        expressionchange("%");
    }
    public void FactorialFunc(View view) {
        expressionchange("!");
    }
    public void SquareFunc(View view) {
        expressionchange("^2");
        curpos = inputarea.getSelectionStart();
        inputarea.setSelection(curpos + 1);
    }
    public void CubeFunc(View view) {
        expressionchange("^3");
        curpos = inputarea.getSelectionStart();
        inputarea.setSelection(curpos + 1);
    }
    public void Square_Root_Func(View view) {
        expressionchange("√");
    }
    public void Cube_Root_Func(View view) {
        expressionchange("∛");
    }
    public void DecimalFunc(View view) {
        expressionchange(".");
    }
    void num_of_mod(){

        for (int i=0;i<temp.length();i++){
            if (temp.charAt(i)=='%'){
                count++;
            }
        }
    }
    void modchange(Integer i){
        String leftnum = "";
        String rightnum = "";
        String leftstr ="";
        String rep="";
        Integer len=temp.length();
        Integer x = i - 1;
        Integer y = i + 1;
        while(x != -1 && Character.isDigit(temp.charAt(x))){
            leftnum += temp.charAt(x);
            x--;
            if(leftnum.length() > 0){
                leftstr = new StringBuilder(leftnum).reverse().toString();
            }
        }
        while(y != temp.length() && Character.isDigit(temp.charAt(y))){
            rightnum += temp.charAt(y);
            y++;
        }
        int ln = Integer.parseInt(leftstr);
        int rn = Integer.parseInt(rightnum);
        int result = ln % rn;
        rep = String.valueOf(result);
//                input = input.substring(0,x + 1)+rep.toString()+input.substring(y,input.length());
        temp = temp.replace(leftstr + '%' + rightnum,rep);

    }
    void calc_mod(){
        String input = inputarea.getText().toString();
        input = input.replaceAll("÷","/");
        input = input.replaceAll("×","*");
        temp=input;
        //Integer len = input.length();
        Integer i=0;

        Integer x = 0;
        temp.length();
        for(i=0;i<temp.length();i++){
            if(temp.charAt(i) == '%'){
                modchange(i);
            }
        }
        inputarea.setText(temp);
    }
    public void EqualFunc(View view) {
        calc_mod();
        Expression tosolve = new Expression(temp);
        String output = String.valueOf(tosolve.calculate());
        if(output.equals("NaN")){
            inputarea.setText("Invalid Expression");
        }
        else{
            inputarea.setText(output);
            inputarea.setSelection(output.length());
        }
    }


    public void ClearFunc(View view) {
        inputarea.setText("");
    }
    public void DelFunc(View view) {
        curpos = inputarea.getSelectionStart();
        expLen = inputarea.getText().length();

        if(curpos == 0 || expLen == 0)
        {
            return;
        }
        else{
            SpannableStringBuilder T = (SpannableStringBuilder) inputarea.getText();
            T.replace(curpos - 1, curpos, "");
            inputarea.setText(T);
            inputarea.setSelection(curpos - 1);
        }
    }

}