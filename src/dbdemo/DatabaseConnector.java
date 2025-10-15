package dbdemo;

import java.sql.*;

// Diese Klasse verwaltet die Verbindung zur MySQL-Datenbank
public class DatabaseConnector {
    private final String url;
    private final String user;
    private final String password;

    // Konstruktor: übergibt Verbindungsdaten beim Erstellen des Objekts
    public DatabaseConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Diese Methode stellt die Verbindung her und führt eine einfache Abfrage aus
    // Versucht, sich mit der DB zu verbinden und gibt true zurück, wenn erfolgreich
    public boolean tryConnect() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Datenbankverbindung erfolgreich!");

            // SQL-Anweisung erstellen und ausführen
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW()");

            // Ergebnis anzeigen
            while (rs.next()) {
                System.out.println("Aktuelle Zeit in der DB: " + rs.getString(1));
            }
            return true;
        } catch (Exception e) {
            // Fehlerbehandlung - Rückgabe false, damit der Aufrufer neu versuchen kann
            System.err.println("Fehler bei der DB-Verbindung:");
            // Kurze Fehlermeldung ausgeben (voller Stacktrace kann beim Retry stören)
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
}
