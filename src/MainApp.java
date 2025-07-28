import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainApp {

    private static final char[] ALPHABET = {'о', 'ж', 'е', 'н', 'и', 'т', 'ь', 'б', ' ', 'з',
            'а', 'г', 'в', 'р', 'л', '.', 'с', 'ш', 'к', 'м',
            'п', '-', 'ч', 'д', 'у', 'ы', 'я', 'э', ',', '?',
            '"', 'щ', '\'', ':', 'ц', 'х', 'ф', '!', 'ъ', 'й', 'ё'
    };

    public static void main(String[] args) {
        Cipher myChipher = new Cipher(ALPHABET);
        System.out.println(myChipher.encrypt("Хочу домой!", 2));
        //getMenu();
    }
    public static void getMenu()
    {
        Scanner console = new Scanner(System.in);
        String command;
        do {
            System.out.println("Меню");
            System.out.println("1. Шифрование");
            System.out.println("2. Расшифровка с ключом");
            System.out.println("3. Brute force");
            System.out.println("4. Статистический анализ");
            System.out.println("0. Выход");
            System.out.println("************************");
            System.out.print("Введите пункт меню: ");
            command = console.nextLine();

            switch (command) {
                case "1":
                    System.out.println("Шифрование");
                    break;
                case "2":
                    System.out.println("Расшифровка с ключом");
                    break;
                case "3":
                    System.out.println("Brute force");
                    break;
                case "4":
                    System.out.println("Статистический анализ");
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Выберите существующий пункт меню!");
                    break;
            }

            System.out.println("------------------------");
        }
        while (!command.equals("0"));
    }
}