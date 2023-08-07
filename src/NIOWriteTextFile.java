import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class NIOWriteTextFile {
    public static void main(String[] args)
    {
        ArrayList <String>recs = new ArrayList<>();
        recs.add("Sample fata for our file writing example");
        recs.add("My name is Irene Chau.");
        recs.add("I was born in 2001.");
        recs.add("I'm studying in University of Cincinnati.");
        recs.add("I'm majoring in Finance.");
        recs.add("I also have a minor in Mathematics");

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\SampleTextData.txt");

        try
        {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file,CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String rec:recs)
            { //Go through rec for each record.
                // For every record in recs (name of myArrayList)
                //Going to write it out, the first number is 0, where it starts the character, it’s an index
                //2nd parameter is how much to write, if you take the length -> means writing the whole thing
                writer.write(rec,0,rec.length());
                writer.newLine(); //Adds new line
            }
            writer.close();
            System.out.println("Data file written!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
