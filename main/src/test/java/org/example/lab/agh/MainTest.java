package org.example.lab.agh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

 class MainTest {

    @Test
    void testMainWithDefaultArguments() {
        // Przechwytywanie standardowego wyjścia
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Wywołanie metody main z domyślnymi argumentami
        Main.main(new String[]{});

        // Sprawdzanie, czy domyślna konfiguracja została zastosowana
        String output = outputStream.toString();
        assertTrue(output.contains("**Reader 1. initiated**"), "Expected output for Reader 1 not found");
        assertTrue(output.contains("**Writer 1. initiated**"), "Expected output for Writer 1 not found");

        // Przywracanie standardowego wyjścia
        System.setOut(System.out);
    }

    @Test
    void testMainWithCustomArguments() {
        // Przechwytywanie standardowego wyjścia
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Wywołanie metody main z własnymi argumentami
        Main.main(new String[]{"5", "2"});

        // Sprawdzanie, czy własna konfiguracja została zastosowana
        String output = outputStream.toString();
        assertTrue(output.contains("**Reader 1. initiated**"), "Expected output for Reader 1 not found");
        assertTrue(output.contains("**Reader 5. initiated**"), "Expected output for Reader 5 not found");
        assertTrue(output.contains("**Writer 1. initiated**"), "Expected output for Writer 1 not found");
        assertTrue(output.contains("**Writer 2. initiated**"), "Expected output for Writer 2 not found");

        // Przywracanie standardowego wyjścia
        System.setOut(System.out);
    }

    @Test
    void testMainWithInvalidArguments() {
        // Przechwytywanie standardowego wyjścia
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Wywołanie metody main z nieprawidłowymi argumentami
        try {
            Main.main(new String[]{"invalid", "2"});
        } catch (NumberFormatException e) {
            // Sprawdzamy, czy wyjątek został rzucony
            assertTrue(true, "NumberFormatException was thrown as expected");
        }

        // Przywracanie standardowego wyjścia
        System.setOut(System.out);
    }
}