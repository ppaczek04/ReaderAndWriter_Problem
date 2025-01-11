package org.example.lab.agh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * The {@code Library} class represents a shared resource where readers and writers
 * interact. It manages access to the library for readers and writers using semaphores
 * and synchronization mechanisms to ensure proper handling of concurrent access.
 *
 * Readers can access the library simultaneously, but writers require exclusive access.
 * This class maintains lists to track the current state of readers and writers,
 * including those waiting, active, and completed.
 */
public class Library {

    /**
     * The number of readers in the system.
     */
    private int numReaders;

    /**
     * The number of writers in the system.
     */
    private int numWriters;


    /**
     * The count of currently active readers in the library.
     */
    private int readcnt = 0;

    /**
     * A list of readers currently waiting to enter the library.
     */
    List<Integer> waitingReaders = new ArrayList<>();

    /**
     * A list of writers currently waiting to enter the library.
     */
    List<Integer> waitingWriters = new ArrayList<>();

    /**
     * A list of readers currently active in the library.
     */
    List<Integer> activeReaders = new ArrayList<>();

    /**
     * A list of writers currently active in the library.
     */
    List<Integer> activeWriters = new ArrayList<>();

    /**
     * Constructs a new {@code Library} instance with the specified number of readers and writers.
     *
     * @param nReaders the number of readers
     * @param nWriters the number of writers
     */
    public Library(int nReaders, int nWriters){
        numReaders = nReaders;
        numWriters = nWriters;

    }

    /**
     * Starts the application by creating and starting reader and writer threads.
     * Initializes semaphores to manage synchronization between threads.
     */
    public void runApp(){

        Semaphore mutex = new Semaphore(1);
        Semaphore writ = new Semaphore(1);
        Semaphore queue = new Semaphore(1);

        Reader[] readers = new Reader[numReaders];
        Writer[] writers = new Writer[numWriters];

        for(int i = 0; i<numReaders; i++){
            System.out.printf("**Reader %d. initiated**%n",i+1);
            readers[i] = new Reader(i+1, mutex, writ, queue, this);
        }
        for(int i = 0; i<numWriters; i++){
            System.out.printf("**Writer %d. initiated**%n",i+1);
            writers[i] = new Writer(i+1, mutex, writ,  queue, this);
        }

        Arrays.stream(writers).forEach(Thread::start);
        Arrays.stream(readers).forEach(Thread::start);

    }

    /**
     * Handles the entry of a reader into the library.
     * Removes the reader from the waiting list and adds them to the active list.
     *
     * @param reader the {@code Reader} instance entering the library
     */
    public void startReading(Reader reader) {
        synchronized (this) {
            waitingReaders.remove(reader.getIDofReader());
            activeReaders.add(reader.getIDofReader());
            System.out.printf(
                    "Reader %d entered the library | Active readers: %s | Waiting readers: %s | Waiting writers: %s.%n",
                    reader.getIDofReader(), activeReaders, waitingReaders, waitingWriters
            );
        }
    }

    /**
     * Handles the exit of a reader from the library.
     * Removes the reader from the active list and prints the updated state.
     *
     * @param reader the {@code Reader} instance leaving the library
     */
    public void stopReading(Reader reader) {
        synchronized (this) {
            activeReaders.remove(reader.getIDofReader());
            System.out.printf(
                    "Reader %d left the library | Active readers: %s | Waiting readers: %s | Waiting writers: %s.%n",
                    reader.getIDofReader(), activeReaders, waitingReaders, waitingWriters
            );
        }
    }

    /**
     * Handles the entry of a writer into the library.
     * Removes the writer from the waiting list and adds them to the active list.
     *
     * @param writer the {@code Writer} instance entering the library
     */
    public void startWriting(Writer writer) {
        synchronized (this) {
            waitingWriters.remove(writer.getIDofWriter());
            activeWriters.add(writer.getIDofWriter());
            System.out.printf(
                    "Writer %d entered the library | Active writers: %s | Waiting readers: %s | Waiting writers: %s.%n",
                    writer.getIDofWriter(), activeWriters, waitingReaders, waitingWriters
            );
        }
    }

    /**
     * Handles the exit of a writer from the library.
     * Removes the writer from the active list and prints the updated state.
     *
     * @param writer the {@code Writer} instance leaving the library
     */
    public void stopWriting(Writer writer) {
        synchronized (this) {
            activeWriters.remove(writer.getIDofWriter());
            System.out.printf(
                    "Writer %d left the library | Active writers: %s | Waiting readers: %s | Waiting writers: %s.%n",
                    writer.getIDofWriter(), activeWriters, waitingReaders, waitingWriters
            );
        }
    }

    /**
     * Gets the current count of active readers in the library.
     *
     * @return the number of active readers
     */
    public int getReadcnt() {
        return readcnt;
    }

    /**
     * Sets the current count of active readers in the library.
     *
     * @param readcnt the new count of active readers
     */
    public void setReadcnt(int readcnt) {
        this.readcnt = readcnt;
    }

    /**
     * Gets the total number of writers configured for the library.
     *
     * @return the total number of writers
     */
    public int getNumWriters() {
        return numWriters;
    }

    /**
     * Sets the total number of writers configured for the library.
     *
     * @param numWriters the new total number of writers
     */
    public void setNumWriters(int numWriters) {
        this.numWriters = numWriters;
    }

    /**
     * Gets the total number of readers configured for the library.
     *
     * @return the total number of readers
     */
    public int getNumReaders() {
        return numReaders;
    }

    /**
     * Sets the total number of readers configured for the library.
     *
     * @param numReaders the new total number of readers
     */
    public void setNumReaders(int numReaders) {
        this.numReaders = numReaders;
    }
}
