package sorting;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PrintStream outputStream = System.out;


    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        String sortingType = "natural";
        String dataType = "word";


        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-sortingType":
                        try {
                            sortingType = argsList.get(argsList.indexOf("-sortingType") + 1);
                            i++;
                        } catch (Exception e) {
                            System.out.println("No sorting type defined!");
                        }
                        break;
                    case "-dataType":
                        try {
                            dataType = argsList.get(argsList.indexOf("-dataType") + 1);
                            i++;
                        } catch (Exception e) {
                            System.out.println("No data type defined!");
                        }
                        break;
                    case "-inputFile":
                        if (i + 1 < args.length) {
                            try {
                                InputStream inputStream = new FileInputStream(args[i + 1]);
                                scanner = new Scanner(inputStream);
                            } catch (FileNotFoundException exception) {
                                exception.printStackTrace();
                            }
                        }
                        i++;
                        break;

                    case "-outputFile":
                        if (i + 1 < args.length) {
                            try {
                                outputStream = new PrintStream(new FileOutputStream(args[i + 1]));
                            } catch (FileNotFoundException exception) {
                                exception.printStackTrace();
                            }
                        }
                        i++;
                        break;
                    default:
                        System.out.printf("\"%s\" is not a valid parameter. It will be skipped.", args[i]);
                }
            }
        }


        if (sortingType.equals("byCount")) {
            switch (dataType) {
                case "long":
                    longSeparatorByCount();
                    break;
                case "line":
                    lineSeparatorByCount();
                    break;
                default:
                    wordSeparatorByCount();
            }
        } else {
            switch (dataType) {
                case "long":
                    longSeparatorNatural();
                    break;
                case "line":
                    lineSeparatorNatural();
                    break;
                default:
                    wordSeparatorNatural();
            }
        }
        scanner.close();
    }

    private static void wordSeparatorNatural() {
        int count = 0;
        String word;
        ArrayList<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            word = scanner.next();
            list.add(word);
        }

        Collections.sort(list);
        outputStream.printf("Total numbers: %d.%n", list.size());
        outputStream.print("Sorted data: ");
        list.stream().map(value -> value + " ").forEach(s -> outputStream.print(s));
    }

    private static void lineSeparatorNatural() {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        outputStream.printf("Total lines: %d\n", lines.size());
        lines.sort((s1, s2) -> -Integer.compare(s1.length(), s2.length()));
        outputStream.println("Sorted data:");
        lines.forEach(line -> outputStream.println(line));
    }

    private static void longSeparatorNatural() {
        long number;
        ArrayList<Long> longs = new ArrayList<>();
        while (scanner.hasNextLong()) {
            longs.add(scanner.nextLong());
        }
        outputStream.printf("Total numbers: %d\n", longs.size());
        longs.sort(Comparator.naturalOrder());
        String sortedData = longs.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        outputStream.printf("Sorted data: %s %n", sortedData);

    }

    // by count

    private static void wordSeparatorByCount() {
        List<String> words = new ArrayList<>();
        // Pattern pattern = Pattern.compile("\\s+");
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        outputStream.printf("Total words: %d\n", words.size());
        var map = words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final int totalCount = words.size();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    outputStream.printf("%s: %d time(s), %d%%%n",
                            entry.getKey(), entry.getValue(),
                            (int) Math.round((double) entry.getValue() / totalCount * 100.0)
                    );
                });

    }

    private static void lineSeparatorByCount() {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        outputStream.printf("Total lines: %d\n", lines.size());
        var map = lines.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final int totalCount = lines.size();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    outputStream.printf("%s: %d time(s), %d%%%n",
                            entry.getKey(), entry.getValue(),
                            (int) Math.round((double) entry.getValue() / totalCount * 100.0));
                });


    }

    private static void longSeparatorByCount() {

        List<Long> longs = new ArrayList<>();
        while (scanner.hasNext()) {
            String token = scanner.next();
            try {
                longs.add(Long.parseLong(token));
            } catch (NumberFormatException e) {
                outputStream.printf("\"%s\" isn't a long. It's skipped.%n", token);
            }
        }
        outputStream.printf("Total numbers: %d\n", longs.size());
        var map = longs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final int totalCount = longs.size();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    outputStream.printf("%d: %d time(s), %d%%%n",
                            entry.getKey(), entry.getValue(),
                            (int) Math.round((double) entry.getValue() / totalCount * 100.0)
                    );
                });
    }

}