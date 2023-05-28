import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Калькулятор");
        System.out.println("Введите два операнда и один оператор(пример 5 + 5) :   ");


        String input = scanner.nextLine();//ввод
        input = input.replaceAll("\\s", "");//Удаляем пробелы
        boolean sis = Calc.Scanner(input); //проверка
        String input1,input2, oper="+";
        int a=0,b=0;

        //System.out.println("SIS="+sis);
        if (sis){
            int indexOp=0;
            for (int i = 0; i<input.length();i++){//запись рима до и после
                if (input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='*'||input.charAt(i)=='/'){
                    indexOp=i;
                    oper=Character.toString(input.charAt(i));//из сар в стринг
                }
            }
            input1=input.substring(0,indexOp);//отделяем и назначаем первый операнд от второго с помощю индекса
            input2=input.substring(indexOp+1);
            a=Calc.RomanToString(input1);
            b=Calc.RomanToString(input2);

            String inp=Integer.toString(a);
            inp+=oper+b;
            input=inp;
        }

        String res=Calc.Calc(input);
        if (!sis){System.out.println("результат операции "+res);}
        else {
            if (Integer.parseInt(res)>=1){
                System.out.println("результат операции : "+Calc.IntToRoman(Integer.parseInt(res)));
            }
            else {
                throw new IOException("в римской системе цифры начинаются с 1 всё что меньще не может быть отображено!");
            }
        }





    }
}




//KLas
class Calc{
    public static String Calc(String input) throws IOException {


        int iOp = 0,a=0,b=0,c=0;// индекс опирации +-*/ и операнды a b результат с
        Operand operand=null;


        for(int i = 0; i < input.length(); i++) {//цикл проверкой на операнды и запись операторов

            switch (input.charAt(i)){//операторы

                case '+':
                    iOp = i;
                    if (operand == null) {
                        operand = Operand.plus;
                        break;
                        } else  throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) !");


                case '-':
                    iOp=i;
                    if (operand==null){
                    operand = Operand.minus;
                    break;
                    }else  throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) !");
                case '*':
                    iOp=i;
                    if (operand==null){
                        operand = Operand.multiply;
                    break;
                    }else  throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) !");
                case '/':
                    iOp=i;
                    if (operand==null){
                        operand = Operand.divide;
                    break;
                    }else  throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) !");

            }



        } if (operand==null){throw new IOException("строка не является математической операцией !");}//оператор +-*/


        //System.out.println("operation = "+operand);
        String str1,str2;
        str1=input.substring(0,iOp);//отделяем и назначаем первый операнд от второго с помощю индекса
        str2=input.substring(iOp+1);
        try {
        a=Integer.parseInt(str1);//переводим строку в цифры для операций с ним
        b=Integer.parseInt(str2);

        }catch (NumberFormatException e){
            throw new IOException("только цифры римские или арабские");
        }

        if(a>10||b>10){
            throw new IOException("число больше десяти !");
        }

        switch (operand){//выполнение опирации с получинными операндами
            case plus :
                c=a+b;
                break;
            case  minus:
                c=a-b;
                break;
            case multiply:
                c=a*b;
                break;
            case divide:
                c=a/b;
        }
        String str = Integer.toString(c);
        return str;
    }

    public static boolean Scanner(String input) throws IOException {
        boolean flag1=false,flag2=true;

        if (input.charAt(0)=='I'||input.charAt(0)=='V'||input.charAt(0)=='X') {
            flag1=true;}
        else { flag1=false; }
        for (int i=1;input.length()>i;i++){
           if (input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='*'||input.charAt(i)=='/'){i++;}
           if (i>=input.length()){throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) !");}
            if (input.charAt(i)=='I'||input.charAt(i)=='V'||input.charAt(i)=='X'){flag2=true;}
            else {flag2=false;}

            if (flag1!=flag2){
                throw new IOException("используются одновременно разные системы счисления !");}//Системы разные
        }


        return flag1;
    }
    ///////////////////////////////////////////////////////////ROMAN
    public static int RomanToString(String s){
        HashMap<Character,Integer> map = new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
//Логика такая берем VIII начинаем с конца I ключ а под ним 1 записываем в рез, сравниваем слева стоящим  если тот меньще минусуем от рез а если нет плюсуем

        int end = s.length()-1;
        char[] arr = s.toCharArray();
        int arab;
        int res =map.get(arr[end]);
        for (int i=end-1;i>=0;i--){
            //System.out.println("i="+i);
            arab=map.get(arr[i]);
            if (arab<map.get(arr[i+1])){
                res-= arab;
            }
            else {
                res+= arab;
            }
        }

      //  System.out.println(res);
    return res;
    }
    public static String IntToRoman(int s){
        int[] val = {100,90,50,40,10,9,5,4,1};
        String[] romans = {"C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder rins = new StringBuilder();
        for(int i=0;i<val.length;i++)
        {
            while(s >= val[i])
            {
                s-= val[i];
                rins.append(romans[i]);
            }
        }

        return rins.toString();
    }
}

