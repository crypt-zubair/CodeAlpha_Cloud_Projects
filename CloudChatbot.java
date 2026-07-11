import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CloudChatbot {
    private final Map<String, String> answers = new HashMap<>();

    public CloudChatbot() {
        answers.put("pricing", "Our plans cost $29 per month for standard users.");
        answers.put("uptime", "Our cloud servers run smoothly 99.99% of the time.");
        answers.put("security", "All user files are fully encrypted and kept safe.");
        answers.put("support", "We offer customer support 24 hours a day, 7 days a week.");
    }

    public String respond(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "Please type a valid question.";
        }
        
        String cleanInput = userInput.toLowerCase().trim();
        
        if (cleanInput.contains("unlock") || cleanInput.contains("pro") || cleanInput.contains("full access") || cleanInput.contains("premium")) {
            return "\n[🔒 PREMIUM VERSION LOCKED]\n" +
                   "Payment is required to view advanced settings.\n" +
                   "👉 View fake pricing page here: https://crypt-zubair.github.io/CodeAlpha_CloudComputing/billing_portal.html\n\n" +
                   "⚠️ [PROTOTYPE NOTICE]\n" +
                   "This chatbot is a prototype for an internship project. Do not enter any real credit card details.";
        }
        
        for (String key : answers.keySet()) {
            if (cleanInput.contains(key)) {
                return answers.get(key);
            }
        }
        
        return "\n⚠️ [Unknown Question]\n" +
               "Please ask about: [pricing, uptime, security, support]\n" +
               "Or type 'unlock pro' to open the hidden premium options.";
    }

    public static void main(String[] args) {
        CloudChatbot bot = new CloudChatbot();
        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================================");
        System.out.println("===         CLOUD CUSTOMER CHATBOT LIVE            ===");
        System.out.println("======================================================");
        System.out.println("💬 HOW TO TEST THIS PROGRAM:");
        System.out.println("1. Ask standard questions : Type 'pricing', 'uptime', 'security', or 'support'.");
        System.out.println("2. Trigger Premium Mode   : Type 'unlock pro' or 'premium' to see the paywall.");
        System.out.println("3. Trigger Wrong Input    : Type any random gibberish to see the help menu.");
        System.out.println("4. Close Chatbot session  : Type 'exit' to close the program.");
        System.out.println("------------------------------------------------------");

        while (true) {
            System.out.print("\nYou: ");
            if (!scanner.hasNextLine()) break; 
            String input = scanner.nextLine();

            if (input.trim().equalsIgnoreCase("exit")) {
                System.out.println("Shutting down the chatbot. Goodbye!");
                break;
            }

            String reply = bot.respond(input);
            System.out.println("Bot: " + reply);
        }

        scanner.close();
    }
}
