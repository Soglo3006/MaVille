package com.maville.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ResidentTest {
    @Test
    public void testIsOver16Valid() {
        // Test case where the resident's birth date makes them over 16
        Resident resident = new Resident("John Doe", "2005-01-01", "john.doe@example.com", "password123", "1234567890", "123 Main St", "H1A 2B3");
        
        // Assert that the resident is over 16
        assertTrue(resident.auDessusDe16(), "Resident should be over 16 years old.");
    }

    @Test
    public void testIsOver16NotOver16() {
        // Test case where the resident's birth date makes them under 16
        Resident resident = new Resident("John Doe", "2010-01-01", "john.doe@example.com", "password123", "1234567890", "123 Main St", "H1A 2B3");
        
        // Assert that the resident is not over 16
        assertFalse(resident.auDessusDe16(), "Resident should not be over 16 years old.");
    }

    @Test
    public void testIsValidDateValid() {
        // Test case where the birth date format is valid (yyyy-MM-dd)
        Resident resident = new Resident("John Doe", "2005-01-01", "john.doe@example.com", "password123", "1234567890", "123 Main St", "H1A 2B3");
        
        // Assert that the birth date is valid
        assertTrue(resident.estDateValide(), "The birth date should be valid in the format yyyy-MM-dd.");
    }

    @Test
    public void testIsValidDateInvalid() {
        // Test case where the birth date format is invalid
        Resident resident = new Resident("John Doe", "2005-13-01", "john.doe@example.com", "password123", "1234567890", "123 Main St", "H1A 2B3");
        
        // Assert that the birth date is invalid
        assertFalse(resident.estDateValide(), "The birth date should be invalid if the format is incorrect.");
    }

    @Test
    public void testIsValidPostalCodeValid() {
        // Test case where the postal code is valid (in the format A1A 1A1 or A1A1A1)
        Resident resident = new Resident("John Doe", "2005-01-01", "john.doe@example.com", "password123", "1234567890", "123 Main St", "H1A 2B3");
        
        // Assert that the postal code is valid
        assertTrue(resident.estCodePostalValide(), "The postal code should be valid if it matches the correct pattern.");
    }

    @Test
    public void testIsValidPostalCodeInvalid() {
        // Test case where the postal code is invalid (not matching the A1A 1A1 pattern)
        Resident resident = new Resident("John Doe", "2005-01-01", "john.doe@example.com", "password123", "1234567890", "123 Main St", "123456");
        
        // Assert that the postal code is invalid
        assertFalse(resident.estCodePostalValide(), "The postal code should be invalid if it does not match the correct pattern.");
    }
}
