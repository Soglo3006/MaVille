package com.maville.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntervenantTest {

    @Test
    public void testCityIdIsOkValid() {
        // Test case where the city ID is valid (8 digits)
        Intervenant intervenant = new Intervenant("John Doe", "john.doe@example.com", "password123", "Entreprise public", "12345678");
        
        // Assert that the city ID is validated correctly
        assertTrue(intervenant.villeIdEstOk(), "The city ID should be valid with exactly 8 digits.");
    }

    @Test
    public void testCityIdIsOkInvalidTooShort() {
        // Test case where the city ID is too short (less than 8 digits)
        Intervenant intervenant = new Intervenant("John Doe", "john.doe@example.com", "password123", "Entreprise public", "12345");
        
        // Assert that the city ID validation fails
        assertFalse(intervenant.villeIdEstOk(), "The city ID should be invalid if it contains less than 8 digits.");
    }

    @Test
    public void testCityIdIsOkInvalidTooLong() {
        // Test case where the city ID is too long (more than 8 digits)
        Intervenant intervenant = new Intervenant("John Doe", "john.doe@example.com", "password123", "Entreprise public", "123456789");
        
        // Assert that the city ID validation fails
        assertFalse(intervenant.villeIdEstOk(), "The city ID should be invalid if it contains more than 8 digits.");
    }

    @Test
    public void testCityIdIsOkInvalidNonNumeric() {
        // Test case where the city ID contains non-numeric characters
        Intervenant intervenant = new Intervenant("John Doe", "john.doe@example.com", "password123", "Entreprise public", "1234ABCD");
        
        // Assert that the city ID validation fails
        assertFalse(intervenant.villeIdEstOk(), "The city ID should be invalid if it contains non-numeric characters.");
    }
}
