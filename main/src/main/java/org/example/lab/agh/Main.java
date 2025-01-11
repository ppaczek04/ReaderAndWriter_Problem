package org.example.lab.agh;

/**
 * The {@code Main} class serves as the entry point for the library simulation application.
 * It initializes the number of readers and writers, either from command-line arguments
 * or default values, and starts the simulation by creating an instance of the {@code Library}.
 */
public class Main {

    /**
     * The main method, which is the entry point for the Java application.
     * <p>
     * The method accepts two optional command-line arguments:
     * <ul>
     *     <li>The first argument specifies the number of readers. If not provided, defaults to 10.</li>
     *     <li>The second argument specifies the number of writers. If not provided, defaults to 3.</li>
     * </ul>
     * It then creates an instance of the {@code Library} class with the specified or default
     * numbers of readers and writers and starts the simulation.
     *
     *
     * @param args the command-line arguments specifying the number of readers and writers
     *             (optional, in order: number of readers, number of writers)
     */
    public static void main(String[] args) {
        int numberOfReaders = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        int numberOfWriters = args.length > 1 ? Integer.parseInt(args[1]) : 3;

        new Library(numberOfReaders, numberOfWriters).runApp();
    }
}