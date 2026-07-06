# 📝 Aufgabenstellung für Schüler

1. Erstelle im Verzeichnis `src/dbdemo/` eine neue Klasse `HalloWelt.java`.
	- Die Klasse soll eine Methode `public String sayHello(String name)` enthalten.
	- Die Methode gibt einen Begrüßungstext wie z.B. `Hallo, <name>!` zurück.

2. Ergänze in der Datei `Main.java` in der `main`-Methode einen einfachen Unit-Test:
	- Erzeuge ein Objekt der Klasse `HalloWelt`.
	- Rufe die Methode `sayHello(String name)` mit einem beliebigen Namen auf.
	- Gib das Ergebnis mit `System.out.println(...)` aus.

**Beispiel:**

```java
HalloWelt hw = new HalloWelt();
String begruessung = hw.sayHello("Anna");
System.out.println(begruessung); // Erwartete Ausgabe: Hallo, Anna!
```

**Hinweis:**
- Die Datenbankfunktionalität bleibt erhalten und kann parallel genutzt werden.
- Ziel ist es, das Prinzip von Unit-Tests und Methodenaufrufen in Java zu üben.

# 🐳 Java HalloWelt + MySQL mit Docker

Dieses Projekt zeigt, wie man eine einfache Java-HalloWelt-Anwendung mit einer MySQL-Datenbank in Docker kombiniert.

## 📂 Projektstruktur

- `src/dbdemo/`: Java-Quellcode (HalloWelt-Beispiel)
- `Dockerfile`: Bauanleitung für Java-Anwendung
- `docker-compose.yml`: Startet Java + Datenbank zusammen
- `.dockerignore`: Ausschluss für unnötige Dateien im Container

### 📄 Lokales Kompilieren und Testen (für Schüler / Codespace)

Hinweis: Das Projekt enthält den MySQL JDBC-Treiber in `lib/mysql-connector-j-8.4.0.jar`.


1. Kompiliere alle Java-Dateien (Projekt-Root):

```bash
javac -cp lib/mysql-connector-j-8.4.0.jar -d out src/dbdemo/*.java
```


2. Starte die Anwendung lokal gegen eine lokale MySQL-Instanz (Standard: localhost):

```bash
# wenn DB lokal auf Port 3306 läuft
java -cp out:lib/mysql-connector-j-8.4.0.jar dbdemo.HalloWelt
```


Die Anwendung liest `DB_HOST` aus der Umgebung; wenn die Variable nicht gesetzt ist, benutzt sie `localhost`.

Hinweis: Die JDBC-URL enthält `allowPublicKeyRetrieval=true` in diesem Übungsprojekt. Das ist
für lokale Entwicklungs- und Lehrzwecke zulässig, weil der MySQL-Container selbst gehostet wird.
In Produktionsumgebungen solltest du sichere Authentifizierungsmechanismen und TLS/SSL verwenden.


3. Oder: Starte beide Services per Docker Compose (empfohlen für die Übung):

```bash
# im Projekt-Root
docker-compose up --build
```

Im Docker-Setup wird die Umgebungsvariable `DB_HOST=db` für den Java-Container gesetzt, daher verwendet die Anwendung dort den internen Service-Namen `db`.

## ▶️ Ausführen im Codespace

1. Wechsele ins main-Verzeichnis:
```bash (Terminal)
cd ..
```
2. Starten
```bash
docker-compose up --build
```

## ▶️ Starten (lokal)

Voraussetzung: Docker und docker-compose müssen installiert sein.

```bash
docker-compose up --build
```

## ✅ Ergebnis

- Verbindung zur Datenbank wird aufgebaut
- "Hallo Welt" und die aktuelle Zeit aus MySQL-Datenbank werden ausgegeben

## 🖥️ Terminal & Logs

Wenn du bereits `docker-compose up` ausgeführt hast und wieder in die Logs oder in einen Container gelangen willst, nutze diese Befehle im Projekt-Root:

```bash
# Logs live verfolgen
docker-compose logs -f

# Logs nur eines Services (z. B. java-app)
docker-compose logs -f java-app

# Interaktiven Shell-Zugang in den laufenden Java-Container
docker-compose exec java-app sh

# Liste laufender Container
docker ps

# Alle Services stoppen und Netzwerk aufräumen
docker-compose down
```

## 🛠️ Troubleshooting / VS Code Tipps

Wenn der `java-app`-Container sofort beendet (Exit) oder Probleme beim Verbinden auftreten, helfen diese Schritte beim schnellen Debuggen:

1) Logs prüfen (schnellster Einstieg):

```bash
# Letzte 200 Zeilen des java-app Logs
docker-compose logs --tail=200 java-app

# Logs live verfolgen
docker-compose logs -f java-app
```

2) Container‑Status prüfen:

```bash
docker-compose ps
docker ps -a
```

3) Wenn die App wegen einer nicht erreichbaren DB beendet wurde:

```bash
# Starte nur die DB (im Hintergrund)
docker-compose up -d db

# Danach die App erneut starten (foreground, damit ihr die Logs seht)
docker-compose up --build java-app
```

4) Interaktive Shell in einem laufenden Container (nur möglich, wenn er noch läuft):

```bash
docker-compose exec java-app sh
```

5) Interaktives Debugging, falls der Container sofort exitet: starte einen temporären Container mit derselben Umgebung und öffne eine Shell:

```bash
docker-compose run --rm --service-ports java-app sh
```


6) Lokales Testen ohne Compose (falls du schnell debuggen willst):

```bash
javac -cp lib/mysql-connector-j-8.4.0.jar -d out src/dbdemo/*.java
DB_HOST=127.0.0.1 java -cp out:lib/mysql-connector-j-8.4.0.jar dbdemo.HalloWelt
```

7) Wenn die App wegen Auth‑Problemen (z. B. "Public Key Retrieval is not allowed") fehlschlägt: die JDBC-URL im Beispiel enthält `allowPublicKeyRetrieval=true` — das ist für die Übung gesetzt. In echten Umgebungen solltest du stattdessen TLS/secure auth verwenden.

8) VS Code: Öffne das integrierte Terminal (View → Terminal), führe obige Befehle aus. Alternativ bietet die Remote‑Containers/DevContainers-Extension eine direkte Umgebung, dort kannst du ebenfalls `docker-compose up --build` ausführen und das Terminal an den DevContainer binden.

Wenn du mir das Log des `java-app` zeigst (z. B. mit `docker-compose logs --tail=200 java-app`), helfe ich dir gezielt beim nächsten Schritt.

## 💾 Commit & Push (Schritt-für-Schritt)

Nutze die folgenden Befehle, um alle vorgenommenen Änderungen ins entfernte Repository zu pushen.

1) Prüfe den aktuellen Git-Status:

```bash
git status
```

2) Erstelle ggf. einen neuen Branch (empfohlen, statt direkt auf main zu pushen):

```bash
# Ersetze my-feature durch einen aussagekräftigen Namen
git switch -c my-feature
```

3) Füge alle Änderungen hinzu, committe und pushe den Branch:

```bash
git add -A
git commit -m "Docs: README Erweiterungen; fix: retry + JDBC auth for demo"
git push --set-upstream origin my-feature
```

4) (Optional) Erstelle eine Pull Request per GitHub CLI:

```bash
# wenn die gh CLI installiert und authentifiziert ist
gh pr create --title "README + DB/connect fixes" --body "README ergänzt; Retry-Logic und JDBC-URL angepasst" --base main
```

Falls du lieber direkt auf `main` pushen willst (nicht empfohlen für gemeinschaftliche Repos), dann:

```bash
git add -A
git commit -m "Apply changes"
git push origin main
```

Wenn du willst, führe ich jetzt die Git-Befehle hier im Container aus und pushe die Änderungen. Soll ich das tun oder möchtest du selber pushen? 

<!-- CUSTOM_LICENSE_NOTICE_START -->
## License

This repository is licensed under a custom license.

- Attribution required: Christine Janischek - https://emotionalspirit.de
- Non-commercial use only
- Use only within state school systems
- Any other use requires explicit prior written permission
<!-- CUSTOM_LICENSE_NOTICE_END -->
