package org.example.lab.agh;

import java.util.concurrent.Semaphore;

/**
 * The {@code Reader} class represents a reader thread in the library simulation.
 * Readers attempt to access the library for reading, following synchronization
 * constraints defined by semaphores and shared state in the {@code Library}.
 *
 * Each reader is identified by a unique ID and interacts with the library
 * to manage its access and state transitions.
 */
public class Reader extends  Thread {
    /**
     * The unique identifier of the reader.
     */
    Integer iD;

    /**
     * A semaphore used to synchronize access to the shared read count and other critical sections.
     */
    Semaphore mutex;

    /**
     * A semaphore ensuring exclusive access for writers to the library.
     * Readers acquire this semaphore when no writers are active.
     */
    Semaphore writ;

    /**
     * A semaphore used as a queue mechanism to manage entry order for both readers and writers.
     */
    Semaphore queue;

    /**
     * A reference to the shared {@code Library} object that manages readers and writers.
     */
    Library library;

    /**
     * Constructs a new {@code Reader} instance with the given ID and semaphores.
     *
     * @param idk   the unique ID of the reader
     * @param mut   the semaphore for synchronizing access to shared variables
     * @param wri   the semaphore for controlling writer access to the library
     * @param que, the semaphore for queue management
     * @param librar   the shared {@code Library} instance
     */
    public Reader(Integer idk, Semaphore mut, Semaphore wri, Semaphore que, Library librar){
        iD = idk;
        mutex = mut;
        writ = wri;
        library = librar;
        queue = que;
    }

    /**
     * The main execution method of the reader thread.
     * Readers repeatedly:
     * <ul>
     *     <li>Add themselves to the waiting list of readers.</li>
     *     <li>Attempt to acquire access to the library through semaphores.</li>
     *     <li>Read for a specified duration.</li>
     *     <li>Release access and wait before the next reading attempt.</li>
     * </ul>
     * Synchronization is achieved through semaphores and interaction
     * with the {@code Library}.
     */
    @Override
    public void run() {
        for (;;) {
            try {
                synchronized (library) {
                    library.waitingReaders.add(iD); // Dodajemy do listy oczekujących
                    System.out.printf("Reader %d is waiting. Waiting readers: %s. Waiting writers: %s.%n",
                            iD, library.waitingReaders, library.waitingWriters);
                }

                queue.acquire(); // Czytelnik wchodzi do kolejki
                mutex.acquire(); // Wejście do sekcji krytycznej
                library.setReadcnt(library.getReadcnt() + 1);
                if (library.getReadcnt() == 1) {
                    writ.acquire(); // Pierwszy czytelnik blokuje pisarzy
                }
                mutex.release(); // Wyjście z sekcji krytycznej
                queue.release(); // Czytelnik opuszcza kolejkę

                library.startReading(this);
                System.out.println("READING");
                sleep(Constants.TIME_SPENT_READING);
                library.stopReading(this);

                mutex.acquire();
                library.setReadcnt(library.getReadcnt() - 1);
                if (library.getReadcnt() == 0) {
                    writ.release();
                }
                mutex.release();

                sleep(Constants.TIME_SPENT_NOT_READING);
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: {}" + e.getMessage() + e);
            }
        }
    }

    /**
     * Gets the ID of the reader.
     *
     * @return the reader's ID
     */
    public Integer getIDofReader() {
        return iD;
    }

    /**
     * Sets the ID of the reader.
     *
     * @param iD the new ID for the reader
     */
    public void setIDofReader(Integer iD) {
        this.iD = iD;
    }

    /**
     * Gets the semaphore used for synchronizing access to shared variables.
     *
     * @return the mutex semaphore
     */
    public Semaphore getMutexByReader() {
        return mutex;
    }

    /**
     * Sets the semaphore used for synchronizing access to shared variables.
     *
     *
     * @param mutex the new mutex semaphore
     */
    public void setMutexByReader
    (Semaphore mutex) {
        this.mutex = mutex;
    }

    /**
     * Gets the semaphore ensuring exclusive access for writers.
     *
     * @return the writing semaphore
     */
    public Semaphore getWritByReader() {
        return writ;
    }

    /**
     * Sets the semaphore ensuring exclusive access for writers.
     *
     * @param writ the new writing semaphore
     */
    public void setWritByReader(Semaphore writ) {
        this.writ = writ;
    }

    /**
     * Gets the semaphore used for managing the reader's queue.
     *
     * @return the queue semaphore
     */
    public Semaphore getQueue() {
        return queue;
    }

    /**
     * Sets the semaphore used for managing the reader's queue.
     *
     * @param queue the new queue semaphore
     */
    public void setQueue(Semaphore queue) {
        this.queue = queue;
    }

    /**
     * Gets the library associated with this reader.
     *
     * @return the library instance
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets the library associated with this reader.
     *
     * @param library the new library instance
     */
    public void setLibrary(Library library) {
        this.library = library;
    }
}
