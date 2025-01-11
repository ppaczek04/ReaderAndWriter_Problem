package org.example.lab.agh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class WriterTest {

    private Library library;
    private Semaphore mutex;
    private Semaphore writ;
    private Semaphore queue;
    private Writer writer;

    @BeforeEach
    void setUp() {
        // Tworzymy wspólne zasoby do testów
        library = new Library(5, 3);
        mutex = new Semaphore(1);
        writ = new Semaphore(1);
        queue = new Semaphore(1);

        // Tworzymy instancję Writer do testowania
        writer = new Writer(1, mutex, writ, queue, library);
    }

    @Test
    void testWriterInitialization() {
        assertEquals(1,(int) writer.getIDofWriter(), "Writer ID should be initialized correctly.");
        assertEquals(mutex, writer.getMutex(), "Writer should have the correct mutex.");
        assertEquals(writ, writer.getWrit(), "Writer should have the correct writ semaphore.");
        assertEquals(queue, writer.getQueue(), "Writer should have the correct queue semaphore.");
        assertEquals(library, writer.getLibrary(), "Writer should be associated with the correct library.");
    }

    @Test
    void testSetAndGetIDofWriter() {
        writer.setIDofWriter(2);
        assertEquals(2,(int) writer.getIDofWriter(), "Writer ID should be updated correctly.");
    }

    @Test
    void testSetAndGetMutex() {
        Semaphore newMutex = new Semaphore(1);
        writer.setMutex(newMutex);
        assertEquals(newMutex, writer.getMutex(), "Writer should use the updated mutex.");
    }

    @Test
    void testSetAndGetWrit() {
        Semaphore newWrit = new Semaphore(1);
        writer.setWrit(newWrit);
        assertEquals(newWrit, writer.getWrit(), "Writer should use the updated writ semaphore.");
    }

    @Test
    void testSetAndGetQueue() {
        Semaphore newQueue = new Semaphore(1);
        writer.setQueue(newQueue);
        assertEquals(newQueue, writer.getQueue(), "Writer should use the updated queue semaphore.");
    }

    @Test
    void testSetAndGetLibrary() {
        Library newLibrary = new Library(3, 2);
        writer.setLibrary(newLibrary);
        assertEquals(newLibrary, writer.getLibrary(), "Writer should be associated with the updated library.");
    }

    @Test
    void testRunAddsWriterToWaitingListAndImmediatelyToActiveList() throws InterruptedException {
        // Symulacja wątku pisarza
        Thread writerThread = new Thread(writer);
        writerThread.start();

        // Krótkie opóźnienie na synchronizację
        Thread.sleep(100);

        synchronized (library) {
            assertTrue(library.activeWriters.contains(1), "Writer should be added to the waiting list.");
        }

        // Przerywamy wątek po zakończeniu testu
        writerThread.interrupt();
    }

    @Test
    void testRunAcquiresAndReleasesWrit() throws InterruptedException {
        // Symulacja wątku pisarza
        Thread writerThread = new Thread(writer);
        writerThread.start();

        // Krótkie opóźnienie na synchronizację
        Thread.sleep(100);

        synchronized (library) {
            assertTrue(library.activeWriters.contains(1), "Writer should be in the active writers list.");
            assertFalse(library.waitingWriters.contains(1), "Writer should be removed from the waiting writers list.");
        }

        // Przerywamy wątek po zakończeniu testu
        writerThread.interrupt();
    }
}
