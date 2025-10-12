package start;

public class Main {
    public static void main(String[] args) {
        // Begrüßung ausgeben
        // TODO: Erstelle ein Objekt der Klasse HalloWelt
        // und rufe eine passende Methode auf, um eine Begrüßung auszugeben.
        // Beispielausgabe im Terminal: Hallo Christine!


        // Verbindungsdaten für die Datenbank (müssen zu docker-compose.yml passen)
    String url = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";
        String user = "user";
        String password = "password";

        // Datenbankverbindung testen
        DatabaseConnector connector = new DatabaseConnector(url, user, password);
        connector.connectAndQuery();
    }
}