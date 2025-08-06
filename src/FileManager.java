import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileManager {
    private String logPath = "sorceFiles/logs";

    public String readFile(String filePath) {
        try {
            Path path = Path.of(filePath);
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Не удалось прочитать файл!");

            if (Files.exists(Path.of(logPath))) {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    Path path = Path.of(logPath);
                    StringBuilder message = new StringBuilder(dtf.format(LocalDateTime.now()) + "\n" + e.getMessage() + "\n");
                    for (StackTraceElement el : e.getStackTrace()) {
                        message.append(el.toString() + "\n");
                    }
                    Files.writeString(path, message + "\n", StandardOpenOption.APPEND);
                } catch (IOException e1) {
                    System.out.println("Ошибка не записана в log!");
                }
            }

            return null;
        }
    }

    public void writeFile(String content, String filePath) {
        try {
            Path path = Path.of(filePath);
            if (content!=null)
                Files.writeString(path, new String(content.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Не удалось произвести записть в файл!");

            if (Files.exists(Path.of(logPath))) {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    Path path = Path.of(logPath);
                    StringBuilder message = new StringBuilder(dtf.format(LocalDateTime.now()) + "\n" + e.getMessage() + "\n");
                    for (StackTraceElement el : e.getStackTrace()) {
                        message.append(el.toString() + "\n");
                    }
                    Files.writeString(path, message + "\n", StandardOpenOption.APPEND);
                } catch (IOException e1) {
                    System.out.println("Ошибка не записана в log!");
                }
            }
        }
    }


}
