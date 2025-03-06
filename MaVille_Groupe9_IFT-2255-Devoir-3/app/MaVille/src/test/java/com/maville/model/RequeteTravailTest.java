package com.maville.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RequeteTravailTest {
    @Test
    public void testIsValidDateValid() {
        // Test case for a valid date format (yyyy-MM-dd)
        RequeteTravail requete = new RequeteTravail(
                "Test Title", "Description", "Type", "2024-01-01", "2024-12-31", "Pending", null, null);
        assertTrue(requete.estDateValide("2024-01-01"), "The date '2024-01-01' should be valid.");
    }

    @Test
    public void testIsValidDateInvalid() {
        // Test case for an invalid date format
        RequeteTravail requete = new RequeteTravail(
                "Test Title", "Description", "Type", "2024-01-01", "2024-12-31", "Pending", null, null);
        assertFalse(requete.estDateValide("2024-13-01"), "The date '2024-13-01' should be invalid.");
    }

    @Test
    public void testIsValidDateEmpty() {
        // Test case for an empty date
        RequeteTravail requete = new RequeteTravail(
                "Test Title", "Description", "Type", "", "2024-12-31", "Pending", null, null);
        assertFalse(requete.estDateValide(""), "An empty date should be invalid.");
    }
}
 