import javax.swing.text.html.parser.Entity;
import java.io.Console;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainApp {

    private static final char[] ALPHABET = {'о', 'ж', 'е', 'н', 'и', 'т', 'ь', 'б', ' ', 'з',
            'а', 'г', 'в', 'р', 'л', '.', 'с', 'ш', 'к', 'м',
            'п', '-', 'ч', 'д', 'у', 'ы', 'я', 'э', ',', '?', ';', '(', ')',
            '\"', 'щ', '\'', ':', 'ц', 'х', 'ф', '!', 'ъ', 'й', 'ё', 'ю', '*', '«', (char)13, (char)10, (char)8239
    };

    private static FileManager fileManager = new FileManager();
    private static Validator validator = new Validator();


    public static void main(String[] args) {
        getMenu();
    }

    public static void getMenu() {
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

            Cipher myChipher = new Cipher(ALPHABET);

            switch (command) {
                case "1":
                    System.out.println("Шифрование");
                    String pathReadEncrypt = inputPath("Введите путь к текстовому файлу, который нужно зашифровать:", console);
                    if (pathReadEncrypt != null) {

                        int keyEncrypt = inpuKey("Введите ключ со значением от 1 до " + (ALPHABET.length - 1) + ":", console);

                        if (keyEncrypt != -1) {

                            String pathWriteEncrypt = inputPath("Введите путь к текстовому файлу, в который нужно записать зашифрованный текст:", console);

                            if (pathWriteEncrypt != null) {
                                String readLine = fileManager.readFile(pathReadEncrypt);
                                String resultLine = myChipher.encrypt(readLine, keyEncrypt);
                                if (!readLine.isEmpty() && resultLine==null)
                                    System.out.println("Файл не удалось зашифровать!");
                                else {
                                    fileManager.writeFile(resultLine, pathWriteEncrypt);
                                    System.out.println("Файл зашифрован!");
                                }
                            }
                        }
                    } else {
                        System.out.println("Файл не удалось зашифровать!");
                    }
                    break;

                case "2":
                    System.out.println("Расшифровка с ключом");
                    String pathReadDecrypt = inputPath("Введите путь к текстовому файлу, который нужно расшифровать:", console);
                    if (pathReadDecrypt != null) {

                        int keyDecrypt = inpuKey("Введите ключ со значением от 1 до " + (ALPHABET.length - 1) + ":", console);

                        if (keyDecrypt != -1) {

                            String pathWriteDecrypt = inputPath("Введите путь к текстовому файлу, в который нужно записать расшифрованный текст:", console);

                            if (pathWriteDecrypt != null) {
                                String readLine = fileManager.readFile(pathReadDecrypt);
                                String resultLine = myChipher.decrypt(readLine, keyDecrypt);
                                if (!readLine.isEmpty() && resultLine == null) {
                                    System.out.println("Файл не удалось расшифровать!");
                                } else {
                                    fileManager.writeFile(resultLine, pathWriteDecrypt);
                                    System.out.println("Файл расшифрован!");
                                }
                            }
                        }
                    } else {
                        System.out.println("Файл не удалось расшифровать!");
                    }
                    break;

                case "3":
                    System.out.println("Brute force");

                    BruteForce bruteForce = new BruteForce();

                    String pathReadBF = inputPath("Введите путь к текстовому файлу, который нужно расшифровать:", console);

                    if (pathReadBF != null) {

                        String pathWriteBF = inputPath("Введите путь к текстовому файлу, в который нужно записать расшифрованный текст:", console);

                        if (pathWriteBF != null) {
                            String readLine = fileManager.readFile(pathReadBF);
                            String resultLine = bruteForce.decryptByBruteForce(readLine, ALPHABET);
                            if (!readLine.isEmpty() && resultLine == null) {
                                System.out.println("Файл не удалось расшифровать!");
                            } else {
                                fileManager.writeFile(resultLine, pathWriteBF);
                                System.out.println("Файл расшифрован!");
                            }
                        }

                    } else {
                        System.out.println("Файл не удалось расшифровать!");
                    }
                    break;

                case "4":
                    System.out.println("Статистический анализ");
                    StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer();

                    String pathReadSA = inputPath("Введите путь к текстовому файлу, который нужно расшифровать:", console);

                    if (pathReadSA != null) {

                        String pathReadRepresentSA = inputPath("Введите путь к текстовому файлу c примером текста:", console);

                        if (pathReadRepresentSA != null) {

                            String pathWriteSA = inputPath("Введите путь к текстовому файлу, в который нужно записать расшифрованный текст:", console);

                            if (pathWriteSA != null) {
                                String readLine = fileManager.readFile(pathReadSA);
                                int bestKey = statisticalAnalyzer.findMostLikelyShift(fileManager.readFile(pathReadSA), ALPHABET, fileManager.readFile(pathReadRepresentSA));
                                String resultLine = myChipher.decrypt(readLine, bestKey);
                                if (!readLine.isEmpty() && bestKey==-1) {
                                    System.out.println("Файл не удалось расшифровать!");
                                } else {
                                    fileManager.writeFile(resultLine, pathWriteSA);
                                    System.out.println("Файл расшифрован!");
                                }
                            }
                        }

                    } else {
                        System.out.println("Файл не удалось расшифровать!");
                    }
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


    private static String inputPath(String command, Scanner console) {
        System.out.println(command);
        String path = console.nextLine();
        String commandMenu;

        boolean isExit = false;

        if (!validator.isFileExists(path)) {

            do {
                System.out.println("************************");
                System.out.println("Файл не прошёл проверку, что делать дальше?");
                System.out.println("1. Выбрать другой файл");
                System.out.println("2. Вернуться в главное меню");
                System.out.println("************************");
                System.out.print("Введите пункт: ");
                commandMenu = console.nextLine();

                switch (commandMenu) {
                    case "1":
                        isExit = true;
                        return inputPath(command, console);
                    case "2":
                        isExit = true;
                        break;
                    default:
                        System.out.println("Выберите существующий пункт!");
                        break;
                }
            }
            while (!isExit);
            return null;
        }
        return path;
    }

    private static int inpuKey(String command, Scanner console) {
        System.out.println(command);

        while (!console.hasNextInt()) {
            System.out.println("Введите числовое значение!");
            console.nextLine();
        }
        int key = Integer.parseInt(console.nextLine());

        String commandMenu;
        boolean isExit = false;

        if (!validator.isValidKey(key, ALPHABET)) {

            do {
                System.out.println("************************");
                System.out.println("Ключ не прошёл проверку, что делать дальше?");
                System.out.println("1. Ввести другой ключ");
                System.out.println("2. Вернуться в гравное меню");
                System.out.println("************************");
                System.out.print("Введите пункт: ");
                commandMenu = console.nextLine();

                switch (commandMenu) {
                    case "1":
                        isExit = true;
                        return inpuKey(command, console);
                    case "2":
                        isExit = true;
                        break;
                    default:
                        System.out.println("Выберите существующий пункт!");
                        break;
                }
            }
            while (!isExit);
            return -1;
        }
        return key;
    }
}

