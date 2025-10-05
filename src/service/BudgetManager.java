package service;

import entity.Purchase;
import entity.User;
import util.Menu;

import java.util.*;

public class BudgetManager {

    private Menu menu;
    private User user;
    Scanner scanner;
    
    public void initialize() {
        this.menu = new Menu();
        this.user = new User();
        this.scanner = new Scanner(System.in);
        initializePurchaseCategories();
        start();
    }

    void initializePurchaseCategories() {
        Map<String, String> purchaseCategories = new HashMap<>(menu.getPurchaseTypeOption());
        purchaseCategories.remove("5");
        for (String types: purchaseCategories.values()) {
            this.user.getPurchases().put(types, new ArrayList<Purchase>());
        }
    }
    
    void start() {
        int action;
        String selection;
        boolean inProgress = true;
        while (inProgress) {
            menu.printMainOption();
            action = menu.getAction();
            switch (action) {
                case 0:
                    System.out.println("Bye!\n");
                    inProgress = false;
                    break;
                case 1:
                    addIncome();
                    break;
                case 2:
                    while (true) {
                        menu.printPurchaseTypeOption();
                        selection = menu.getPurchaseType();
                        System.out.println();
                        if (!"No such selection.".equals(selection) && !"Back".equals(selection)) {
                            addPurchase(selection);
                        } else if ("Back".equals(selection)) {
                            break;
                        } else {
                            System.out.println(selection + "\n");
                        }
                    }
                    break;
                case 3:
                    while (true) {
                        menu.printShowPurchaseOption();
                        selection = menu.getShowPurchase();
                        System.out.println();
                        if (!"No such option.".equals(selection) && !"Back".equals(selection)) {
                            showPurchases(selection);
                        } else if ("Back".equals(selection)) {
                            break;
                        } else {
                            System.out.println(selection + "\n");
                        }
                    }
                    break;
                case 4:
                    showBalance();
                    break;
                case 5:
                    savePurchase();
                    break;
                case 6:
                    loadPurchase();
                    break;
                case 7:
                    while (true) {
                        menu.printSortOption();
                        selection = menu.getSortType();
                        System.out.println();
                        if ("Sort all purchases".equals(selection)) {
                            sortAllPurchase();
                        } else if ("Sort by type".equals(selection)) {
                            sortPurchaseByType();
                        } else if ("Sort certain type".equals(selection)) {
                            menu.printSortByCertainTypeOption();
                            selection = menu.getTypeToSort();
                            System.out.println();
                            sortPurchaseByCertainType(selection);
                        } else if ("Back".equals(selection)) {
                            break;
                        } else {
                            System.out.println(selection + "\n");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    System.out.println("");
                    break;
            }
        }
    }

     void addIncome() {
        System.out.println("Enter income:");
        try {
            user.addIncome(Double.parseDouble(scanner.nextLine()));
            System.out.println("Income was added!" + "\n");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input " + e.getMessage() + "\n");
        }
    }

    void addPurchase(String selection) {
        System.out.println("Enter purchase name:");
        String name = scanner.nextLine();
        System.out.println("Enter its price:");
        try {
            double price = Double.parseDouble(scanner.nextLine());
            if (price > user.getBalance()) {
                System.out.println("Not enough balance to add this purchase.\n");
            } else {
                user.addPurchase(selection, name, price);
                System.out.println("Purchase was added!" + "\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input " + e.getMessage() + "\n");
        }

    }

    void showPurchases(String selection) {
        if (user.getPurchases().isEmpty()) {
            System.out.println("The purchase list is empty!\n");
            return;
        }
        user.showPurchases(selection);
    }

    void showBalance() {
        user.showBalance();
    }

    void savePurchase() {
        user.savePurchase();
    }

    void loadPurchase() {
        user.loadPurchase();
    }

    void sortAllPurchase() {
        if (checkPurchaseIfEmpty(user.getAllPurchases())) {
            System.out.println("The purchase list is empty!\n");
            return;
        }
        List<Purchase> allPurchases = new ArrayList<>(user.getPurchases().values().stream()
                .flatMap(List::stream).toList());
        allPurchases.sort(Comparator.comparingDouble(Purchase::getPrice).reversed());
        System.out.println("All:");
        user.printPurchase(allPurchases);
    }

    void sortPurchaseByType() {
        List<Purchase> purchasesByType = new ArrayList<>();
        user.getPurchases().forEach((type, items) -> {
                double total = items.stream().mapToDouble(Purchase::getPrice).sum();
                purchasesByType.add(new Purchase(type, total));
            });
        purchasesByType.sort(Comparator.comparingDouble(Purchase::getPrice).reversed());
        System.out.println("Types:");
        user.printPurchase(purchasesByType, '-');
    }

    void sortPurchaseByCertainType(String type) {
        if (checkPurchaseIfEmpty(user.getPurchasesByType(type))) {
            System.out.println("The purchase list is empty!\n");
            return;
        }
        List<Purchase> purchasesByCertainType = new ArrayList<>(user.getPurchases().get(type));
        purchasesByCertainType.sort(Comparator.comparingDouble(Purchase::getPrice).reversed());
        System.out.printf("%s\n", type);
        user.printPurchase(purchasesByCertainType);
    }

    boolean checkPurchaseIfEmpty(List<Purchase> purchases) {
        return purchases.isEmpty();
    }
}
