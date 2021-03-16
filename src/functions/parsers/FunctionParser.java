package functions.parsers;

import functions.Function;
import functions.basic.*;
import functions.meta.*;

import java.util.Arrays;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class FunctionParser {

    private String[] funcNames = new String[]
            {"arccos", "arccot", "arcsin", "arctan", "cos", "ch", "ctg", "sin", "sh", "th", "tg", "cth"};

    private char[] ops = new char[]{'+', '-', '*', '/', '^'};

    private String expression_;

    private char variable_;

    public FunctionParser(String expression, char variable){
        expression_=expression;
        variable_=variable;
    }

    public Function parseFunction(){
        expression_ = expression_.replaceAll(" ", "");
        checkCorrect(expression_);
        System.out.println("good");
        return new Sin();
    }

    private void checkCorrect(String expression){
        boolean isLastAnOp = true;
        boolean isLastANum = false;
        if (!checkBrackets()){
            throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
        }
        for (int i = 0; i < expression.length(); ++i){//проход по символам

            if (expression.charAt(i)=='('){
                checkCorrect(expression.substring(i+1, findBracket(expression, i)-1));
                i=findBracket(expression, i)+1;
                if (i>=expression.length()){
                    return;
                }
            }
            if (!isLetter(expression.charAt(i))&&!isDigit(expression.charAt(i))&&!inOps(expression.charAt(i))){//если ! или тп
                throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
            }
            if (isLetter(expression.charAt(i)) && expression.charAt(i)!='e' &&
                    logCheck(expression, isLastAnOp, isLastANum, i)==0 &&
                    (expression.charAt(i)!=variable_||expression.length()-1!=i&&isLetter(expression.charAt(i+1)))
                    &&(expression.length()-i>=2&&!expression.substring(i, i+2).equals("pi")||expression.length()-i<2)){//блок для проверки функций
                if(isLastAnOp==false){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                int j =0;
                for (; j < funcNames.length; ++j){
                    if (funcNames[j].length()<expression.length()-i &&
                            funcNames[j].equals(expression.substring(i, i+funcNames[j].length()))){
                        i+=funcNames[j].length();
                        if (expression.charAt(i)!='('){
                            throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                        }
                        else {
                            checkCorrect(expression.substring(i+1, findBracket(expression, i)-1));
                            i=findBracket(expression, i);
                            if (i>=expression.length()){
                                return;
                            }
                            j = 20;
                        }
                    }
                    if (j==funcNames.length-1){
                        throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                    }

                }
            }
            else if(expression.length()-i>=2&&expression.substring(i, i+2).equals("pi"))// проверка на пи
            {
                if(isLastAnOp==false){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                isLastAnOp=false;
                isLastANum=false;
                i+=1;
                if (i>=expression.length()) {
                    return;
                }
            }
            else if (inOps(expression.charAt(i))){//проверка на операции
                if (i<expression.length()-1&&(i!=0||expression.charAt(i)=='-')){
                    isLastAnOp=true;
                    isLastANum=false;
                    checkCorrect(expression.substring(i+1, expression.length()));
                    return;
                }
                else{
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
            }
            else if (expression.charAt(i)=='e'||expression.charAt(i)==variable_){
                if(isLastAnOp==false){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                isLastAnOp = false;
                isLastANum=false;
            }
            else if (isDigit(expression.charAt(i))){
                if(isLastANum!=true &&isLastAnOp!=true){
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                StringBuilder num = new StringBuilder();
                while (i<expression.length()&&(isDigit(expression.charAt(i))||expression.charAt(i)=='.')){
                    num.append(expression.charAt(i));
                    ++i;
                }
                try {
                    double d = Double.parseDouble(num.toString());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                isLastAnOp = false;
                isLastANum=true;
            }
            else if (logCheck(expression, isLastAnOp, isLastANum, i)==0){
                throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
            }
            else{
                i+=logCheck(expression, isLastAnOp, isLastANum, i)-1;
                if (i>=expression.length()){
                    return;
                }
            }

        }
        return;
    }

    private int logCheck(String expression, boolean isLastAnOp, boolean isLastANum, int i){
        if (expression.length()-i>=8&&expression.substring(i, i+4).equals("log_")){
            i+=4;
            if (expression.charAt(i)=='e'&&expression.charAt(i+1)=='('){
                i+=2;
            }
            else if (expression.charAt(i)=='p'&&expression.charAt(i+1)=='i'&&expression.charAt(i+2)=='('){
                i+=3;
            }
            else if (!isDigit(expression.charAt(i))){
                throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
            }
            else{
                StringBuilder num = new StringBuilder();
                while (i<expression.length()&&expression.charAt(i)!='('){
                    num.append(expression.charAt(i));
                    ++i;
                }
                try {
                    double d = Double.parseDouble(num.toString());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Недопустимая функция! (Wrong function!)");
                }
                ++i;
            }

            checkCorrect(expression.substring(i, findBracket(expression, i)-1));
            return findBracket(expression, i)+1;
        }
        else {
            return 0;
        }
    }

    private boolean inOps(char s){
        for (int i =0; i<ops.length; ++i){
            if (s==ops[i]){
                return true;
            }
        }
        return false;
    }

    private boolean checkBrackets(){
        int brackets = 0;
        int lastInd = 0;
        for (int i = 0; i < expression_.length() && brackets>=0; ++i){
            if (expression_.charAt(i)=='('){
                ++brackets;
                lastInd = i;
            }
            if (expression_.charAt(i)==')'){
                --brackets;
                if (i-lastInd==1){
                    brackets=-1;
                }
            }
        }
        if (brackets==0){
            return true;
        }
        return false;
    }

    private int findBracket(String expression, int index){ // возвращает индекс закрывающей скобки, принимает индекс следующий после открывающей
        int brackets = 0;
        int i =1;
        for (; i < expression.length()-index && brackets>=0; ++i){
            if (expression.charAt(i+index)=='('){
                ++brackets;
            }
            if (expression.charAt(i+index)==')'){
                --brackets;
            }
        }
        return i+index;
    }



}
