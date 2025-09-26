import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // & File Variables
    public static final File NOTE_FILE = new File("Notes.txt");
    public static int AllNotes = 0;

    // & Program Variables
    public static boolean RunFlag = true;
    public static Scanner Input = new Scanner(System.in);

    // ^ Initializing Everything.
    static {
        try {
            if (!NOTE_FILE.exists()) {
                NOTE_FILE.createNewFile();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void WriteNote(String note) {
        try (FileWriter writer = new FileWriter(NOTE_FILE, true)) {
            for (char c : (note.trim() + "\n").toCharArray()) {
                writer.append(c);
            }
            AllNotes++;
            System.out.println("Operation Done Successfully.");            
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Operation Failed.");
        }
    }

    public static String ReadNote(int number) {
        int target = number;
        int current = 1;
        StringBuilder line = new StringBuilder();

        try (FileReader reader = new FileReader(NOTE_FILE)) {
            int i;
            char c;
            boolean isLineEmpty = true;

            while ((i = reader.read()) != -1) {
                c = (char) i;

                if (c == '\n') {
                    if (line.toString().trim().isEmpty()) {
                        line.setLength(0);
                        isLineEmpty = true;
                        continue;
                    }

                    if (current == target) {
                        return line.toString();
                    }

                    line.setLength(0);
                    current++;
                    isLineEmpty = true;
                } else {
                    line.append(c);
                    isLineEmpty = false;
                }
            }

            if (!isLineEmpty && current == target) {
                return line.toString();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return "Operation Failed.";
        }

        return "__NO_NOTE__";
    }

    public static void DeleteString(int index) {
        ArrayList<String> Lines = new ArrayList<>();
        try (FileReader reader = new FileReader(NOTE_FILE)) {
            StringBuilder dummy = new StringBuilder();
            int i; char c; while ((i = reader.read()) != -1) {
                c = (char) i;
                if (c == '\n') {
                    Lines.add(dummy.toString());
                    dummy = new StringBuilder();
                } else {
                    dummy.append(c);
                }
            }
            Lines.add(dummy.toString());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Operation Failed.");
        }

        Lines.remove(index);
        try (FileWriter writer = new FileWriter(NOTE_FILE)) {
            for (String line : Lines) {
                for (char c : (line + "\n").toCharArray()) {
                    writer.write(c);
                }
            }
            System.out.println("Operation Done Successfully.");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Operation Failed.");
        }
    }

    public static void main(String[] args) throws IOException {
        // & Reading all previously written notes.
        try (FileReader reader = new FileReader(NOTE_FILE)) {
            int i; char c; while ((i = reader.read()) != -1) {
                c = (char) i;
                if (c == '\n') {
                    AllNotes++;
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        // & Running the Program.
        System.out.println("Welcome to Note Reader and Writer");
        while (RunFlag) {
            System.out.println(
            """
            Press the Corresponding number to initiate that operation:
            1) Enter a note.
            2) Delete a note.
            3) Print a note.
            4) Print all notes.
            5) Exit.
            """
            );

            System.out.print("Enter Operation: ");
            int option = Input.nextInt();
            switch (option) {
                case 1 -> {
                    System.out.print("Enter a note: ");
                    Input.nextLine(); // Consume leftover newline
                    String note = Input.nextLine();
                    WriteNote(note);
                }
                
                case 2 -> {
                    System.out.print("Enter note index: ");
                    int note_index = Input.nextInt();
                    DeleteString(note_index);
                }

                case 3 -> {
                    System.out.print("Enter note index: ");
                    int note_index = Input.nextInt();
                    System.out.println(ReadNote(note_index));
                }
                
                case 4 -> {
                    System.out.println("File: ");
                    System.out.println("---------------------");
                    try (FileReader reader = new FileReader(NOTE_FILE)) {
                        int i; char c; while ((i = reader.read()) != -1) {
                            c = (char) i;
                            System.out.print(c);
                        }
                    } catch (IOException exception) {
                        System.out.println(exception.getMessage());
                        System.out.println("Operation Failed.");
                    }
                    System.out.println("---------------------");
                }

                case 5 -> {
                    RunFlag = false;
                }

                default -> {
                    System.out.println("Invalid Operation.");
                }
            }
        }

        Input.close();
    }
}