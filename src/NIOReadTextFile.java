import javax.swing.*;
import java.io.*;// for File selectedFile
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

//uses the JFileChooser class, which is a component in Swing, a Java GUI toolkit. The JFileChooser allows users to browse and select files from their system.
public class NIOReadTextFile {
    public static void main(String[] args) {
        //initialized "chooser",represented the file chooser dialog that will be displayed to the user.
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec ="";

        try {
            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Typically, we want the user to pick the file so we use a file chooser
            chooser.setCurrentDirectory(workingDirectory);

            // if statement checks if the user has chosen a file and clicked the "Open" or "OK" button.
            // If the condition is true, the block of code inside the if statement will be executed, meaning the code will proceed with the selected file.
            // Otherwise, if the user cancels the dialog or closes it without selecting a file, the code inside the if block will be skipped, run else

            //This line opens the file chooser dialog for the user to select a file
            //The method showOpenDialog(null) displays the dialog and blocks the program's execution until the user either selects a file or cancels the dialog.
            //The null argument indicates that the dialog's parent component is set to null, meaning the dialog will be centered on the screen.
            //JFileChooser.APPROVE_OPTION is returned if the user chooses a file and clicks the "Open" or "OK" button in the dialog.
            //representing the result of the user action when using the file chooser dialog.
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {//After the user selects a file and the dialog is closed, this line retrieves the selected file and stored in the "selectedFile"
                selectedFile = chooser.getSelectedFile();

                //This line convert the "selectedFile" to a path
                Path file = selectedFile.toPath();

                //newInputStream needs "Catch" clause
                //open an input stream for the selected file using Files.newInputStream()
                //It creates a buffered input stream for efficient reading of data from the line.
                //The CREATE constant is a variable that holds a value, indicating the file should be created if it does not exist
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                //Create a buffered InputStreamReader from the previous InputStream to read the content of the file
                //=> converts bytes from the input stream to characters, and then wraps it with a "BufferedReader
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int lineCount = 0; // initialize variable "lineCount ~ rec" to keep track of line number while reading
                int word = 0;
                int characters = 0;

                while (reader.ready())// while loop reads the file line by line using "readLine() method of the "BufferedReader" class
                // the while loop continues looping til the end of the file is reached(when reader.ready() returns "False"
                {
                    rec = reader.readLine();// Reads the content of current line of the file into the rec variable
                    lineCount++;
                    characters += rec.length();
                    word += countWords(rec);
                    //print the line number and line content
                    //%4d -> the line number should be printed as an integer with a width of 4 character
                    //%-60s specifies that the content of the line should be printed as a left-justified string with a width of 60 characters.
                    System.out.printf("\nLine%2d: %-60s",lineCount,rec);
                }
                reader.close();//must close the BufferReader to seal it and flush buffer
                System.out.println("\n\nData file read!");
                System.out.println("\nSummary Report:");
                System.out.printf("File name: %s%n", file.getFileName());
                System.out.println("Number of lines: " + lineCount);
                System.out.println("Number of words: " + word);
                System.out.println("Number of characters: " + characters);
            } else // If the user cancels the file chooser dialog or closes it without selecting a file, this block of code will be executed
            {
                System.out.println("No file selected! Run the program again and select a file.");
            }
        } catch (FileNotFoundException e)//handle a specific exception that the file is not found
        {
            //specified file does not exist or cannot be opened for reading, this block will catch the FileNotFoundException and handle it.
            //will print an error message, and also print the stack trace to help with debugging.
            System.out.println("File not found!");
            e.printStackTrace();
        }
        ////used to handle other IO-related exceptions that may occur during file operations.
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int countWords(String rec) {
        if (rec == null || rec.isEmpty()) {
            return 0;
        }
        //countWords() method to trim the line and then split it to count words correctly.
        String[] words = rec.split(" ");
        return words.length;
    }
}