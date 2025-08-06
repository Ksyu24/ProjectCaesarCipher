import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

public class Validator {
    private FileManager fm = new FileManager();
    private String logPath = "sorceFiles/logs";

    public boolean isValidKey(int key, char[] alphabet) {
        // Проверка ключа
        try {
            if (key <= 0 || key > alphabet.length - 1) {
                throw new KeyOutOfRangeException(("ОШИБКА " + ErrorCodes.KEY_OUT_OF_RANGE_ERROR.getCode() +
                        ". Ключ " + key + " выходит за диапазон допустимых ключей (от 1 до " + (alphabet.length - 1) + ")\n"),
                        new Throwable(), ErrorCodes.KEY_OUT_OF_RANGE_ERROR);

            } else {
                return true;
            }
        } catch (KeyOutOfRangeException e) {
            writeLogAndPrintException(e);
            return false;
        }

    }

    public boolean isFileExists(String filePath) {
        // Проверка существования и читаемости файла
        try {

            if (!Files.exists(Path.of(filePath))) {
                throw new FileNotFoundException("ОШИБКА " + ErrorCodes.NOT_FOUND_FILE_ERROR.getCode() +
                        ". Файл "+Path.of(filePath).getFileName()+" не найден по пути \""+filePath+"\"");
            }

            String fileName = Path.of(filePath).getFileName().toString();
            int indexDot = fileName.lastIndexOf('.');
            String fileType=null;
            if(indexDot>0) {
                fileType = fileName.substring(indexDot+1);
            }
            if (indexDot<1 || !fileType.equals("txt"))
            {
                throw new FileNotFoundException("ОШИБКА " + ErrorCodes.NOT_FOUND_FILE_ERROR.getCode() +
                        ". \""+Path.of(filePath).getFileName()+"\" имеет не подходящий формат!");
            }
            return true;
        } catch (FileNotFoundException e) {
            writeLogAndPrintException(e);
            return false;
        } catch (RuntimeException e) {
            writeLogAndPrintException(e);
            return false;
        }

    }

    public boolean isElementExists(char el, char[] alphabet) {
        // Проверка существования элемента справочника
        try {
            boolean isElementInArray = false;
            for(char currentEl : alphabet)
            {
                if (el==currentEl)
                {
                    isElementInArray=true;
                    break;
                }
            }
            if (!isElementInArray) {
                throw new NotFoundElementException("ОШИБКА " + ErrorCodes.NOT_FOUND_ELEMENT_ERROR.getCode() + ". Символ \'"+el+"\' не найден в алфавите!");
            }
            return true;
        } catch (NotFoundElementException e) {
            writeLogAndPrintException(e);
            return false;
        }
    }

    public boolean isElementExists(Character keyEl, Character valueEl, HashMap<Character, Character> alphabetMap) {
        // Проверка существования элемента справочника
        try {
            if(keyEl!=null && !alphabetMap.containsKey(keyEl))
            {
                throw new NotFoundElementException("ОШИБКА " + ErrorCodes.NOT_FOUND_ELEMENT_ERROR.getCode() + ". Символ \'"+keyEl+"\' не найден в алфавите!");
            } else if (valueEl!=null && !alphabetMap.containsValue(valueEl)) {
                throw new NotFoundElementException("ОШИБКА " + ErrorCodes.NOT_FOUND_ELEMENT_ERROR.getCode() + ". Символ \'"+valueEl+"\' не найден в алфавите!");
            }
            return true;
        } catch (NotFoundElementException e) {
            writeLogAndPrintException(e);
            return false;
        }
    }

    private void writeLogAndPrintException(Exception e)
    {
        System.out.println(e.getMessage());

        if (Files.exists(Path.of(logPath)))
        {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                Path path = Path.of(logPath);
                StringBuilder message = new StringBuilder(dtf.format(LocalDateTime.now())+"\n"+e.getMessage()+"\n");
                for (StackTraceElement el : e.getStackTrace())
                {
                    message.append(el.toString()+"\n");
                }
                Files.writeString(path, message+"\n", StandardOpenOption.APPEND);
            }
            catch (IOException e1) {
                System.out.println("Ошибка не записана в log!");
            }
        }
    }
}
