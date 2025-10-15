package dbdemo;

// Importiere benötigte Klassen für JDBC
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        // Lese DB-Host aus Umgebungsvariable (ermöglicht localhost für Schüler/Codespace
        // und "db" innerhalb des docker-compose Netzwerks)
        String dbHost = System.getenv("DB_HOST");
        if (dbHost == null || dbHost.isBlank()) {
            dbHost = "localhost";
        }

        // JDBC-Verbindungsparameter
    // Erweitere die JDBC-URL um allowPublicKeyRetrieval, damit der Connector sich beim
    // initialen Authentifizierungs-Handshake verbinden kann (sicher in lokalem Übungs-Setup).
    String jdbcUrl = "jdbc:mysql://" + dbHost + ":3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "user";
        String password = "password";

        System.out.println("Verwende DB-Host: " + dbHost);

        // Erzeuge ein Objekt der Verbindungsklasse
        DatabaseConnector connector = new DatabaseConnector(jdbcUrl, username, password);

        // Warte auf die Datenbank: versuche mehrfach, bevor das Programm aufgibt
        int maxSeconds = 30;
        int waited = 0;
        int intervalMs = 2000; // 2 Sekunden
        boolean ok = false;
        while (waited < maxSeconds * 1000) {
            System.out.println("Versuch, mit der DB zu verbinden (warten: " + waited/1000 + "s)...");
            if (connector.tryConnect()) {
                ok = true;
                break;
            }
            try {
                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            waited += intervalMs;
        }

        if (!ok) {
            System.err.println("Konnte innerhalb von " + maxSeconds + "s keine Verbindung zur DB herstellen. Beende.");
        }
    }
}
