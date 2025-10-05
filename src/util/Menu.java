package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    private final Map<String, String> mainOption = Map.of(
            "1", "Add income",
            "2", "Add purchase",
            "3", "Show list of purchases",
            "4", "Balance",
            "5", "Save",
            "6", "Load",
            "7", "Analyze (Sort)",
            "0", "Exit"
    );

    private final Map<String, String> purchaseTypeOption = Map.of(
            "1", "Food",
            "2", "Clothes",
            "3", "Entertainment",
            "4", "Other",
            "5", "Back"
    );

    private final Map<String, String> showPurchaseOption = Map.of(
            "1", "Food",
            "2", "Clothes",
            "3", "Entertainment",
            "4", "Other",
            "5", "All",
            "6", "Back"
    );

    private final Map<String, String> sortOption = Map.of(
            "1", "Sort all purchases",
            "2", "Sort by type",
            "3", "Sort certain type",
            "4", "Back"
    );

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public Map<String, String> getMainOption() {
        return this.mainOption;
    }

    public Map<String, String> getPurchaseTypeOption() {
        return this.purchaseTypeOption;
    }

    public Map<String, String> getShowPurchaseOption() {
        return this.showPurchaseOption;
    }

    public Map<String, String> getSortOption() {
        return this.sortOption;
    }

    public Map<String, String> getTypesToSort() {
        Map<String, String> typesToSort = new HashMap<>(getPurchaseTypeOption());
        typesToSort.remove("5");
        return typesToSort;
    }

    public void printMainOption() {
        System.out.println("Choose your action:");
        printOption(mainOption, true);
    }

    public void printPurchaseTypeOption() {
        System.out.println("Choose the type of purchase:");
        printOption(purchaseTypeOption, false);
    }

    public void printShowPurchaseOption() {
        System.out.println("Choose the type of purchase:");
        printOption(showPurchaseOption, false);
    }

    public void printSortOption() {
        printOption(sortOption, false);
    }

    public void printSortByCertainTypeOption() {
        System.out.println("Choose the type of purchase:");
        printOption(getTypesToSort(), false);
    }

    public int getAction() {
        int action = 0;
        try {
            action = Integer.parseInt(this.scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input " + e.getMessage() + "\n");
        }
        System.out.println();
        return action;
    }

    public String getPurchaseType() {
        return purchaseTypeOption.getOrDefault(scanner.nextLine(), "No such option.");
    }

    public String getShowPurchase() {
        return showPurchaseOption.getOrDefault(scanner.nextLine(), "No such option.");
    }

    public String getSortType() {
        return sortOption.getOrDefault(scanner.nextLine(), "No such option.");
    }

    public String getTypeToSort() {
        return getTypesToSort().getOrDefault(scanner.nextLine(), "No such option.");
    }

    private static void printOption(Map<String, String> option, boolean isMain) {
        for (int i = 1; i <= option.size(); i++) {
            String id = String.valueOf(i);
            if (option.containsKey(id)) {
                System.out.printf("%s) %s\n", id, option.get(id));
            }
        }
        if (isMain) {
            System.out.printf("%s) %s\n", "0", option.get("0"));
        }
    }
}
