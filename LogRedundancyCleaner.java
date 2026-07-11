import java.util.HashSet;

public class LogRedundancyCleaner {
    public static void main(String[] args) {
        // Simulated live network data packets
        String[] webAccessLogs = {
            "66.249.65.107 - - [11/Jul/2026:10:00:01] \"GET /index.html HTTP/1.1\" 200",
            "192.168.1.50 - - [11/Jul/2026:10:00:02] \"POST /login HTTP/1.1\" 200",
            "66.249.65.107 - - [11/Jul/2026:10:00:01] \"GET /index.html HTTP/1.1\" 200", // Duplicate
            "192.168.1.50 - - [11/Jul/2026:10:00:02] \"POST /login HTTP/1.1\" 200",    // Duplicate
            "185.220.101.5 - - [11/Jul/2026:10:00:05] \"GET /api/data HTTP/1.1\" 401"
        };

        HashSet<String> uniqueLogs = new HashSet<>();
        int totalRecords = 0;
        int redundantCount = 0;

        System.out.println("--- Cloud Data Redundancy Removal System ---");

        for (String logLine : webAccessLogs) {
            totalRecords++;
            if (!uniqueLogs.add(logLine)) {
                System.out.println("[REDUNDANT PACKET BLOCKED]: " + logLine);
                redundantCount++;
            }
        }

        System.out.println("\n--- Cloud Deduplication Metrics Summary ---");
        System.out.println("Total Network Streams Processed : " + totalRecords);
        System.out.println("Redundant Storage Logs Destroyed: " + redundantCount);
        System.out.println("Clean Unique Database Logs Saved: " + uniqueLogs.size());
    }
}
