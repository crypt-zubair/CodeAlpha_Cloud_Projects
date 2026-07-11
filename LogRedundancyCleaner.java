import java.util.HashSet;
import java.util.Set;

public class LogRedundancyCleaner {
    public static void main(String[] args) {
        String[] rawStream = {
            "66.249.65.107 - - [11/Jul/2026:10:00:01] \"GET /index.html HTTP/1.1\" 200",
            "192.168.1.50 - - [11/Jul/2026:10:00:02] \"POST /login HTTP/1.1\" 200",
            "66.249.65.107 - - [11/Jul/2026:10:00:01] \"GET /index.html HTTP/1.1\" 200", 
            "  ", // Corrupted line edge-case
            "192.168.1.50 - - [11/Jul/2026:10:00:02] \"POST /login HTTP/1.1\" 200",    
            "185.220.101.5 - - [11/Jul/2026:10:00:05] \"GET /api/data HTTP/1.1\" 401"
        };

        Set<String> seenLogs = new HashSet<>();
        int linesProcessed = 0;
        int duplicatesDropped = 0;

        System.out.println(">>> Initializing log deduplication stream listener...");

        for (String log : rawStream) {
            if (log == null || log.trim().isEmpty()) {
                continue; // Skip malformed or empty packets
            }
            
            linesProcessed++;
            if (!seenLogs.add(log.trim())) {
                System.err.println("[DUP_BLOCKED] -> " + log.trim());
                duplicatesDropped++;
            }
        }

        System.out.println("\n--- Pipeline Job Run Summary ---");
        System.out.printf("Total Evaluated : %d\n", linesProcessed);
        System.out.printf("Dropped (Dups)  : %d\n", duplicatesDropped);
        System.out.printf("Committed Cache : %d\n", seenLogs.size());
    }
}
