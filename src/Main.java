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

        input = input.replaceAll("\\s", "");//заменяем пробелы на пустой символ(т.е удаляем пробелы)

        boolean sis = Calc.Scanner(input); //проверка коректности строки и возврат цифр fals==arab true==rim

        String input1,input2, oper="+";
        int a=0,b=0;


        if (sis){
            int indexOp=0;
            for (int i = 0; i<input.length();i++){//запись рима до и после
                if (input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='*'||input.charAt(i)=='/'){
                    indexOp=i;                                       //запись индекса оператора (+-*/)
                    oper=Character.toString(input.charAt(i));        //из чар в стринг
                }
            }
            //после цикла
            input1=input.substring(0,indexOp);//отделяем и назначаем первый операнд в input1 c помощю индекса
            input2=input.substring(indexOp+1);// второй операнд с помощю индекса
            a=Calc.RomanToString(input1);// из рима в арабскую
            b=Calc.RomanToString(input2);

            String inp=Integer.toString(a);//собераем всё в строку так как метод кальк принимает только строку
            inp+=oper+b;
            input=inp;
        }

        String res=Calc.Calc(input);//вычесляем

        //в соответствии с системой выводим в арабской сразу или после перевода в рим
        if (!sis){System.out.println("результат операции "+res);}
        else {
            if (Integer.parseInt(res)>=1){//проверка результата на отрицательные значения или 0 так как нет в риме 0 или отрицательных чисел
                //выводим результат после перевода в рим
                System.out.println("результат операции : "+Calc.IntToRoman(Integer.parseInt(res)));//строку в инт и передаём в метод инт ту роман
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
        Operand operand=null;   //создаём перечисление обект операнд из класса Операнд для определения типа обекта перечисленные в классе Операнд +-*/


        for(int i = 0; i < input.length(); i++) {//цикл проверкой на операнды и запись операторов

            switch (input.charAt(i)){//кейсы с операторами
                  //если проверяемый чар буква то кейсы не срабатывают
                case '+':
                    iOp = i;//запись индекса оператора
                    if (operand == null) {// проверка на оператор если он уже есть то выводим исключение
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

        }
        if (operand==null){throw new IOException("строка не является математической операцией !");}//если оператора нет


        String str1,str2;
        str1=input.substring(0,iOp);//отделяем и назначаем первый операнд от второго с помощю индекса
        str2=input.substring(iOp+1);

        a=Integer.parseInt(str1);//переводим строку в цифры для операций с ним
        b=Integer.parseInt(str2);



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
        //начальная проверка на римские
        if (input.charAt(0)=='I'||input.charAt(0)=='V'||input.charAt(0)=='X') {//
            flag1=true;}
        for (int i=1;input.length()>i;i++){

           if (input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='*'||input.charAt(i)=='/'){i++;}//если символ (i) операция то перескакиваем дальше i++

           //ниже проверка если указан операнд в конце вырожения например V+V+ то из-за прошлой проверки i станет больше длины строки изза операции i++
           if (i>=input.length()){throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) !");}
           //проверка внутри цикла на римские
           if (input.charAt(i)=='I'||input.charAt(i)=='V'||input.charAt(i)=='X'){flag2=true;}
            else {flag2=false;}


        }
        if (flag1!=flag2){//если системы разные выводим ошибку
            throw new IOException("используются одновременно разные системы счисления !");}//Системы разные

        return flag1;
    }
    ///////////////////////////////////////////////////////////ROMAN->int
    public static int RomanToString(String s){
        //карта с ключами например V(Character) это ключ а под ним значение 5(Integer)
        HashMap<Character,Integer> map = new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);

//Логика такая берем VIII начинаем с конца I ключ а под ним 1 записываем в рез, сравниваем слева стоящим  если тот меньще минусуем от рез а если нет плюсуем

        int end = s.length()-1;
        char[] arr = s.toCharArray();//s это String принемаемый записываем его в массив чар
        int arab;
        int res =map.get(arr[end]);      //начинаем с конца I ключ а под ним 1 записываем в рез
        for (int i=end-1;i>=0;i--){      //цикл наоборот с конца(-1) к началу
            arab=map.get(arr[i]);        // записываем то что левее для сравнения
            if (arab<map.get(arr[i+1])){ //сравниваем
                res-= arab;              //если тот меньще минусуем
            }
            else {
                res+= arab;              //а если нет плюсуем
            }
        }

    return res;
    }

    public static String IntToRoman(int s){//для вывода рима из инт
        //алгоритм такой берем самое большое число в вал что соответствует в римской для записи, минусуем пока вал меньше или равно s и добаляем в rins соответсвуюшую из массива роман
        //и так пока не дойдем до нуля
        int[] val = {100,90,50,40,10,9,5,4,1};
        String[] romans = {"C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder rins = new StringBuilder();//для записи рез
        for(int i=0;i<val.length;i++)//пройтись по массиву вал
        {
            while(s >= val[i])  //минусуем пока вал меньше или равно s
            {
                s-= val[i];
                rins.append(romans[i]); //добаляем в rins соответсвуюшую из массива роман
            }
        }

        return rins.toString();
    }
}

