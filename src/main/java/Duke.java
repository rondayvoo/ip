import java.util.Scanner;

public class Duke {
    public static final int MAX_STORED_TASKS = 100;
    public static final int TODO_OFFSET = 5;
    public static final int DEADLINE_OFFSET = 9;
    public static final int EVENT_OFFSET = 6;

    public static void printGreeting() {
        printDividingLine();
        System.out.println("Greetings, human! I'm Duke. \nWhat can I do for you?");
        printDividingLine();
    }

    public static void printDividingLine() {
        System.out.println("________________________________________");
    }

    public static void printFarewell() {
        printDividingLine();
        System.out.println("Closing Duke. Have a nice day!");
        printDividingLine();
    }

    public static void manageTasks() {
        Task[] userTasks = new Task[MAX_STORED_TASKS];
        int taskIndex = 0;
        Scanner userInput = new Scanner(System.in);
        String userInputString = userInput.nextLine();

        while (!userInputString.equals("bye")) {
            printDividingLine();
            if (userInputString.equals("list")) {
                listTasks(taskIndex, userTasks);
            } else if (userInputString.startsWith("done")) {
                markTaskDone(userInputString, taskIndex, userTasks);
            } else {
                addNewTask(userInputString, taskIndex, userTasks);
                taskIndex++;
            }
            printDividingLine();
            userInputString = userInput.nextLine();
        }
    }

    public static void listTasks(int taskIndex, Task[] userTasks) {
        for (int i = 0; i < taskIndex; i++) {
            System.out.println((i + 1) + ". " + userTasks[i]);
        }
    }

    public static void markTaskDone(String userInputString, int taskIndex, Task[] userTasks) {
        String[] words = userInputString.split(" ");
        int completeIndex = Integer.parseInt(words[1]) - 1;
        if (completeIndex >= 0 && completeIndex < taskIndex) {
            userTasks[completeIndex].markComplete();
            System.out.println("Task " + userTasks[completeIndex].description + " marked as complete.");
        } else {
            System.out.println("Error: index outside range.");
        }
    }

    public static void addNewTask(String userInputString, int taskIndex, Task[] userTasks) {
        int slashIndex = userInputString.indexOf('/');
        String taskSubstring;
        String timeSubstring = userInputString.substring(slashIndex + 1);
        timeSubstring = timeSubstring.replaceFirst(" ", ": ");
        if (userInputString.startsWith("todo")) {
            taskSubstring = userInputString.substring(TODO_OFFSET);
            userTasks[taskIndex] = new Todo(taskSubstring);
        } else if (userInputString.startsWith("deadline") && slashIndex > 0) {
            taskSubstring = userInputString.substring(DEADLINE_OFFSET, slashIndex - 1);
            userTasks[taskIndex] = new Deadline(taskSubstring, timeSubstring);
        } else if (userInputString.startsWith("event") && slashIndex > 0) {
            taskSubstring = userInputString.substring(EVENT_OFFSET, slashIndex - 1);
            userTasks[taskIndex] = new Event(taskSubstring, timeSubstring);
        } else {
            userTasks[taskIndex] = new Todo(userInputString);
        }

        System.out.println("Gotcha. I've added this task:");
        System.out.println(userTasks[taskIndex]);
        System.out.println("You have a total of " + (taskIndex + 1) + " tasks now.");
    }

    public static void main(String[] args) {
        printGreeting();
        manageTasks();
        printFarewell();
    }
}
