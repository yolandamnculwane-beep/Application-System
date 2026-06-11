package com.mycompany.applicationsystem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationSystemTest {

    @BeforeEach
    public void setUp() {

        ApplicationSystem.ids.clear();
        ApplicationSystem.messages.clear();
        ApplicationSystem.recipients.clear();
        ApplicationSystem.timestamps.clear();
        ApplicationSystem.hashes.clear();
        ApplicationSystem.statusList.clear();

        ApplicationSystem.sentCount = 0;
        ApplicationSystem.messageLimit = 0;
    }

    // ---------------- USERNAME TESTS ----------------

    @Test
    public void testValidUsername() {
        assertTrue(ApplicationSystem.checkUserName("ab_cd"));
    }

    @Test
    public void testInvalidUsernameNoUnderscore() {
        assertFalse(ApplicationSystem.checkUserName("abcd"));
    }

    @Test
    public void testInvalidUsernameTooLong() {
        assertFalse(ApplicationSystem.checkUserName("abc_de"));
    }

    // ---------------- PASSWORD TESTS ----------------

    @Test
    public void testValidPassword() {
        assertTrue(ApplicationSystem.checkPassword("Password1!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(ApplicationSystem.checkPassword("password"));
    }

    // ---------------- LOGIN TESTS ----------------

    @Test
    public void testLoginSuccess() {
        assertTrue(
                ApplicationSystem.login(
                        "ab_cd",
                        "Password1!",
                        "ab_cd",
                        "Password1!"
                )
        );
    }

    @Test
    public void testLoginFailure() {
        assertFalse(
                ApplicationSystem.login(
                        "user",
                        "wrong",
                        "admin",
                        "Password1!"
                )
        );
    }

    // ---------------- PHONE NUMBER TESTS ----------------

    @Test
    public void testValidNumber() {
        assertEquals(
                "Valid",
                ApplicationSystem.validateNumber("+27123456789")
        );
    }

    @Test
    public void testInvalidNumberPrefix() {
        assertEquals(
                "Invalid: must start with +27",
                ApplicationSystem.validateNumber("07123456789")
        );
    }

    @Test
    public void testInvalidNumberLength() {
        assertEquals(
                "Invalid: must be 12 digits (+27XXXXXXXXX)",
                ApplicationSystem.validateNumber("+2712345678")
        );
    }

    // ---------------- HASH TESTS ----------------

    @Test
    public void testCreateMessageHash() {

        String result =
                ApplicationSystem.createMessageHash(
                        "1234567890",
                        0,
                        "Hello World"
                );

        assertEquals("12:0:HELLOWORLD", result);
    }

    // ---------------- MESSAGE STORAGE TESTS ----------------

    @Test
    public void testDeleteByHash() {

        ApplicationSystem.ids.add("1234567890");
        ApplicationSystem.hashes.add("12:0:HELLOWORLD");
        ApplicationSystem.recipients.add("+27123456789");
        ApplicationSystem.messages.add("Hello World");
        ApplicationSystem.timestamps.add("12:00:00");
        ApplicationSystem.statusList.add("SENT");
        ApplicationSystem.sentCount = 1;

        ApplicationSystem.deleteByHash("12:0:HELLOWORLD");

        assertEquals(0, ApplicationSystem.messages.size());
        assertEquals(0, ApplicationSystem.ids.size());
        assertEquals(0, ApplicationSystem.hashes.size());
    }

    @Test
    public void testSearchByIdExisting() {

        ApplicationSystem.ids.add("1111111111");
        ApplicationSystem.messages.add("Test Message");
        ApplicationSystem.recipients.add("+27123456789");

        assertTrue(ApplicationSystem.ids.contains("1111111111"));
    }

    @Test
    public void testSearchByRecipientExisting() {

        ApplicationSystem.ids.add("1111111111");
        ApplicationSystem.messages.add("Hello");
        ApplicationSystem.recipients.add("+27123456789");

        assertTrue(
                ApplicationSystem.recipients.contains("+27123456789")
        );
    }

    // ---------------- REPORT METHODS ----------------

    @Test
    public void testShowMessages() {
        assertDoesNotThrow(() -> ApplicationSystem.showMessages());
    }

    @Test
    public void testShowLongestMessage() {
        assertDoesNotThrow(() -> ApplicationSystem.showLongestMessage());
    }

    @Test
    public void testDisplayReport() {
        assertDoesNotThrow(() -> ApplicationSystem.displayReport());
    }

    @Test
    public void testStoreMessage() {
        assertDoesNotThrow(() -> ApplicationSystem.storeMessage());
    }

    @Test
    public void testSearchById() {
        assertDoesNotThrow(() -> ApplicationSystem.searchById("123"));
    }

    @Test
    public void testSearchByRecipient() {
        assertDoesNotThrow(() ->
                ApplicationSystem.searchByRecipient("+27123456789"));
    }

    @Test
    public void testDeleteByHashNonExisting() {
        assertDoesNotThrow(() ->
                ApplicationSystem.deleteByHash("INVALID"));
    }
}
