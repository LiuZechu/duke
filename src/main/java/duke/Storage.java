package duke;

import task.Task;
import task.TaskType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Deals with storage related functionality, such as writing to a file or loading tasks from a file.
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Writes a string to the file path of this duke.Storage object.
     *
     * @param textToAdd String that the user wants to write to a file.
     * @throws IOException
     */
    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Loads strings from a text file and parse them into an ArrayList of Tasks.
     *
     * @return ArrayList of Tasks stored in the file
     * @throws FileNotFoundException
     */
    public ArrayList<Task> loadTasksFromFile() throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        Scanner scanner = new Scanner(f); // create a Scanner using the File as the source
        ArrayList<Task> result = new ArrayList<>();
        while (scanner.hasNext()) {
            String keyword = scanner.next();
            switch (keyword) {
            case "T":
                String[] todoLine = scanner.nextLine().split(" \\| ");
                boolean isDoneTodo = Boolean.parseBoolean(todoLine[1]);
                String todoDescription = todoLine[2];
                Task todoTask = new Task(todoDescription, TaskType.TODO);
                if (isDoneTodo) {
                    todoTask.markAsDone();
                }
                result.add(todoTask);
                break;
            case "D":
                String[] deadlineLine = scanner.nextLine().split(" \\| ");
                boolean isDoneDeadline = Boolean.parseBoolean(deadlineLine[1]);
                String deadlineDescription = deadlineLine[2];
                String deadlineTime = deadlineLine[3];
                Task deadlineTask = new Task(deadlineDescription, TaskType.DEADLINE);
                Calendar deadlineCalendar = new Calendar.Builder().setInstant(Long.parseLong(deadlineTime)).build();
                deadlineTask.setTime(deadlineCalendar);
                if (isDoneDeadline) {
                    deadlineTask.markAsDone();
                }
                result.add(deadlineTask);
                break;
            case "E":
                String[] eventLine = scanner.nextLine().split(" \\| ");
                boolean isDoneEvent = Boolean.parseBoolean(eventLine[1]);
                String eventDescription = eventLine[2];
                String eventTime = eventLine[3];
                Task eventTask = new Task(eventDescription, TaskType.EVENT);
                Calendar eventCalendar = new Calendar.Builder().setInstant(Long.parseLong(eventTime)).build();
                eventTask.setTime(eventCalendar);
                if (isDoneEvent) {
                    eventTask.markAsDone();
                }
                result.add(eventTask);
                break;
            }
        }

        return result;
    }
}