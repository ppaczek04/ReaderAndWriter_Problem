package org.example.lab.agh;

import java.util.concurrent.Semaphore;

/**
 * The {@code Writer} class represents a writer thread in the library simulation.
 * Writers attempt to gain exclusive access to the library for writing, respecting
 * synchronization constraints defined by semaphores and shared state in the {@code Library}.
 *
 * Each writer is identified by a unique ID and interacts with the library
 * to manage its access and state transitions.
 */
public class Writer extends Thread {

    /**
     * The unique identifier of the writer.
     */
    Integer iD;

    /**
     * A semaphore used to synchronize access to the shared read count and other critical sections.
     */
    Semaphore mutex;

    /**
     * A semaphore ensuring exclusive access for writers to the library.
     */
    Semaphore writ;

    /**
     * A semaphore used as a queue mechanism to manage entry order.
     */
    Semaphore queue;

    /**
     * A reference to the shared {@code Library} object that manages readers and writers.
     */
    Library library;

    /**
     * Constructs a new {@code Writer} instance with the given ID and semaphores.
     *
     * @param idk   the unique ID of the writer
     * @param one   the semaphore for synchronizing access to shared variables
     * @param two   the semaphore for controlling writer access to the library
     * @param three the semaphore for queue management
     * @param lib   the shared {@code Library} instance
     */
    public Writer(Integer idk, Semaphore one, Semaphore two, Semaphore three, Library lib){
        iD = idk;
        mutex = one;
        writ = two;
        queue = three;
        library = lib;
    }

    /**
     * The main execution method of the writer thread.
     * Writers repeatedly:
     * <ul>
     *     <li>Join the waiting list.</li>
     *     <li>Attempt to acquire access to the library.</li>
     *     <li>Write for a specified duration.</li>
     *     <li>Release access and wait before the next writing attempt.</li>
     * </ul>
     * Synchronization is achieved through semaphores and interaction
     * with the {@code Library}.
     */
    @Override
    public void run(){
        for (;;) {
            try {
                synchronized (library) {
                    library.waitingWriters.add(iD); // Dodajemy do listy oczekujących
                    System.out.printf("Writer %d is waiting. Waiting writers: %s. Waiting readers: %s.%n",
                            iD, library.waitingWriters, library.waitingReaders);
                }

                queue.acquire(); // Pisarz wchodzi do kolejki
                writ.acquire();
                queue.release(); // Pisarz opuszcza kolejkę
                library.startWriting(this);
                System.out.println("WRITING");
                sleep(Constants.TIME_SPENT_WRITING);
                library.stopWriting(this);
                writ.release();
                sleep(Constants.TIME_SPENT_NOT_WRITING);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted: {}" + e.getMessage() + e);
                return;
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: {}" + e.getMessage() + e);
            }
        }
    }

    /**
     * Gets the ID of the writer.
     *
     * @return the writer's ID
     */
    public Integer getIDofWriter() {
        return iD;
    }

    /**
     * Sets the ID of the writer.
     *
     * @param iD the new ID for the writer
     */
    public void setIDofWriter(Integer iD) {
        this.iD = iD;
    }

    /**
     * Gets the semaphore used for synchronizing access to shared variables.
     *
     * @return the mutex semaphore
     */
    public Semaphore getMutex() {
        return mutex;
    }

    /**
     * Sets the semaphore used for synchronizing access to shared variables.
     *
     * @param mutex the new mutex semaphore
     */
    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }

    /**
     * Gets the semaphore ensuring exclusive access for writers.
     *
     * @return the writing semaphore
     */
    public Semaphore getWrit() {
        return writ;
    }

    /**
     * Sets the semaphore ensuring exclusive access for writers.
     *
     * @param writ the new writing semaphore
     */
    public void setWrit(Semaphore writ) {
        this.writ = writ;
    }

    /**
     * Gets the semaphore used for managing the writer's queue.
     *
     * @return the queue semaphore
     */
    public Semaphore getQueue() {
        return queue;
    }

    /**
     * Sets the semaphore used for managing the writer's queue.
     *
     * @param queue the new queue semaphore
     */
    public void setQueue(Semaphore queue) {
        this.queue = queue;
    }

    /**
     * Gets the library associated with this writer.
     *
     * @return the library instance
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets the library associated with this writer.
     *
     * @param library the new library instance
     */
    public void setLibrary(Library library) {
        this.library = library;
    }
}
