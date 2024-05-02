import java.util.*;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 6 + 6) или ( X + V) :");
        String input = scanner.nextLine();

        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректный формат ввода");
        }

        String firstToken = tokens[0].toUpperCase();
        String operator = tokens[1];
        String secondToken = tokens[2].toUpperCase();

        int a, b;
        boolean isRoman = false;

        if (isRomanNumeral(firstToken) && isRomanNumeral(secondToken)) {
            isRoman = true;
            a = romanToArabic(firstToken);
            b = romanToArabic(secondToken);
        } else {
            a = Integer.parseInt(firstToken);
            b = Integer.parseInt(secondToken);
        }

        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Неверная операция");
        }

        if (isRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Отрицательные числа и ноль не могут быть выражены римскими цифрами");
            }
            System.out.println(arabicToRoman(result));
        } else {
            System.out.println(result);
        }
    }

    private static boolean isRomanNumeral(String str) {
        return str.matches("^[IVXLCDM]+$");
    }

    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int previousValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(roman.charAt(i));
            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            previousValue = currentValue;
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        if (number == 0) {
            return "N";
        }
        if (number < 0) {
            throw new IllegalArgumentException("Ноль и отрицательные числа не могут быть выражены римскими цифрами");
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < romanSymbols.length; i++) {
            while (number >= arabicValues[i]) {
                number -= arabicValues[i];
                result.append(romanSymbols[i]);
            }
        }

        return result.toString();
    }
}