import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CloudChatbot {
    private final Map<String, String> knowledgeBase = new HashMap<>();

    public CloudChatbot() {
        knowledgeBase.put("pricing", "Standard tier is $29/mo. Enterprise custom deployments start at $499/mo.");
        knowledgeBase.put("uptime", "Our cloud architecture guarantees a 99.99% SLA uptime across all regions.");
        knowledgeBase.put("security", "All data layers are encrypted at rest using AES-256 and TLS 1.3 in transit.");
        knowledgeBase.put("support", "24/7/365 premium engineer support is available via the corporate dashboard.");
    }

    public String respond(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "System alert: Received empty query stream.";
        }
        
        String cleanInput = input.toLowerCase().trim();
        
        for (String keyword : knowledgeBase.keySet()) {
            if (cleanInput.contains(keyword)) {
                return knowledgeBase.get(keyword);
            }
        }
        
        return "Query does not match our cloud matrix keywords (pricing, uptime, security, support).";
    }

    public static void main(String[] args) {
        CloudChatbot bot = new CloudChatbot();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Live Cloud Retrieval Chatbot Ready ===");
        System.out.println("Type your query below (or type 'exit' to quit):");
        System.out.println("----------------------------------------");

        while (true) {
            System.out.print("\nYou: ");
            String userInput = scanner.nextLine();

            if (userInput.trim().equalsIgnoreCase("exit")) {
                System.out.println("Shutting down chatbot pipeline...");
                break;
            }

            String botResponse = bot.respond(userInput);
            System.out.println("Bot: " + botResponse);
        }

        scanner.close();
    }
}
