import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class MarkupEditor {

    public static void main(String[] args) {
        ArrayList<String> output = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        label:
        while (true) {
            System.out.print("Choose a formatter: ");
            String q = scanner.nextLine();

            switch (q) {
                case "!help":
                    System.out.println("Available formatters: plain bold italic header link inline-code ordered-list unordered-list new-line");
                    System.out.println("Special commands: !help !done");
                    break;
                case "!done":
                    try {
                        FileWriter writer = new FileWriter("README.md");
                        for (String el : output) {
                            writer.write(el);
                        }
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("Error: IOException");
                    }
                    break label;
                case "plain":
                case "bold":
                case "italic":
                case "header":
                case "link":
                case "inline-code":
                case "ordered-list":
                case "unordered-list":
                case "new-line":
                    switch (q) {
                        case "plain", "bold", "italic", "header", "inline-code" -> {
                            System.out.print("Text: ");
                            String text = scanner.nextLine();
                            if (q.equals("header") && (text.isEmpty() || text.length() > 6)) {
                                System.out.println("The level should be within the range of 1 to 6");
                                continue;
                            }
                            if (q.equals("header")) {
                                int lvl = Integer.parseInt(text);
                                output.add("#".repeat(lvl) + " " + scanner.nextLine() + "\n");
                            } else {
                                output.add(formatText(q, text));
                            }
                        }
                        case "link" -> {
                            System.out.print("Label: ");
                            String lbl = scanner.nextLine();
                            System.out.print("URL: ");
                            String url = scanner.nextLine();
                            output.add("[" + lbl + "](" + url + ")");
                        }
                        case "ordered-list", "unordered-list" -> {
                            while (true) {
                                System.out.print("Number of rows: ");
                                int num = Integer.parseInt(scanner.nextLine());
                                if (num > 0) {
                                    for (int i = 0; i < num; i++) {
                                        System.out.print("Row #" + (i + 1) + ": ");
                                        String row = scanner.nextLine();
                                        output.add((q.equals("ordered-list") ? (i + 1) + ". " : "* ") + row + "\n");
                                    }
                                    break;
                                } else {
                                    System.out.println("The number of rows should be greater than zero");
                                }
                            }
                        }
                        default -> output.add("\n");
                    }
                    break;
                default:
                    System.out.println("Unknown formatting type or command");
                    break;
            }

            if (!output.isEmpty()) {
                for (String line : output) {
                    System.out.print(line);
                }
            }
            System.out.println();
        }
        scanner.close();
    }

    public static String formatText(String format, String text) {
        return switch (format) {
            case "plain" -> text;
            case "bold" -> "**" + text + "**";
            case "italic" -> "*" + text + "*";
            case "inline-code" -> "`" + text + "`";
            default -> "";
        };
    }
}
