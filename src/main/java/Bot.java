import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Bot {
    private static String botHelp = "This is a bot-reminder." +
            "\r\nFunctions:\r\n\t-help -- show help" +
            "\r\n\t-echo <args> -- print <args>" +
            "\r\n\t-authors -- print authors" +
            "\r\n\t-date -- print current date and time" +
            "\r\n\t-new -- create new note" +
            "\r\n\t-remove -- remove note" +
            "\r\n\t-all -- show all your notes" +
            "\r\n\t-stop -- exit chat bot";
    private static String welcomeText = "Welcome. This is bot-reminder v0.3 alpha";
    private static String authors = "Tolstoukhov Daniil, Gorbunova Sofia, 2019"; //TODO вынести весь текст в json или отдельный класс
    private static UserIO userIO = new ConsoleIO();

    public static void main(String[] args) {
        userIO.showMessage(welcomeText);
        NoteMaker noteMaker = new NoteMaker(new ConsoleIO(), 60);
        Map<String, Consumer<String>> commands = new HashMap<>();
        commands.put("-new", noteMaker::addNote);
        commands.put("-remove", noteMaker::removeNote);
        commands.put("-all", noteMaker::showUserNotes);
        commands.put("-stop", Bot::exit);
        commands.put("-help", Bot::help);
        commands.put("-authors", Bot::authors);
        commands.put("-echo", Bot::echo);
        commands.put("-date", Bot::date);

        String currentCommand = "";
        while (true) {
            currentCommand = userIO.getUserText(null);
            if (!currentCommand.replaceAll("\\s+", "").equals("") && commands.containsKey(currentCommand.split(" ")[0])) { // TODO: userId
                commands.get(currentCommand.split(" ")[0]).accept(currentCommand);
            }
        }
    }

    private static void exit(String userId) {
        System.exit(0);
    }

    private static void help(String userId) {
        userIO.showMessage(botHelp);
    }

    private static void authors(String _s) {
        userIO.showMessage(authors);
    }

    private static void date(String _s) {
        userIO.showMessage(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    private static void echo(String s){
        if (s.length() > 6) {
            userIO.showMessage(s.substring(6));
        }
    }
}
