package entity;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class User {

    private Double balance;
    private File filePurchase;
    private Map<String, List<Purchase>> purchases;

    public User() {
        this.balance = 0.00;
        this.filePurchase = new File(".\\purchases.txt");
        this.purchases = new HashMap<>();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Map<String, List<Purchase>> getPurchases() {
        return this.purchases;
    }

    public void setPurchases(String purchaseType, List<Purchase> purhasedItems) {
        this.purchases.put(purchaseType, purhasedItems);
    }

    public void addIncome(Double amount) {
        this.balance += amount;
    }

    public void addPurchase(String option, String item, Double price) {
        this.purchases.computeIfAbsent(option, k -> new ArrayList<Purchase>())
                .add(new Purchase(item, price));
        this.balance -= price;
    }

    public void showBalance() {
        System.out.printf("Balance: $%.2f\n\n", this.balance);
    }

    public void showPurchases(String type) {
        List<Purchase> purchasedItems;
        if ("All".equals(type)) {
            System.out.println(type + ":");
            purchasedItems = getAllPurchases();
        } else {
            System.out.println(type + ":");
            purchasedItems = getPurchasesByType(type);
        }
        printPurchase(purchasedItems);
    }

    public void savePurchase() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(this.filePurchase, false))) {
            printWriter.printf("%.2f\n", this.balance);
            for (String purchaseType: this.purchases.keySet()) {
                List<Purchase> purchasedItems = this.purchases.get(purchaseType);
                if (purchasedItems.isEmpty()) {
                    continue;
                }
                for (Purchase purchase: purchasedItems) {
                    printWriter.printf("%s,%s,%.2f\n",
                            purchaseType, purchase.getItem(), purchase.getPrice());
                }
            }
            System.out.println("Purchases were saved!\n");
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    public void loadPurchase() {
        try (Scanner scanner = new Scanner(this.filePurchase)) {
            if (scanner.hasNextLine()) {
                setBalance(Double.parseDouble(scanner.nextLine()));
            }
            while (scanner.hasNext()) {
                String[] input = scanner.nextLine().split(",");
                String purchaseType = input[0];
                String item = input[1];
                Double price = Double.parseDouble(input[2]);
                if (this.purchases.containsKey(purchaseType)) {
                    this.purchases.get(purchaseType).add(new Purchase(item, price));
                }
            }
            System.out.println("Purchases were loaded!\n");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    public void printPurchase(List<Purchase> purchasedItems) {
        try {
            double sum = purchasedItems.stream().mapToDouble(Purchase::getPrice).sum();
            purchasedItems.forEach(purchase ->
                            System.out.printf("%s $%.2f\n", purchase.getItem(), purchase.getPrice()));
            System.out.println("Total sum: $" + String.format("%.2f", sum) + "\n");
        } catch (Exception e) {
            System.out.println("The purchase list is empty\n");
        }
    }

    public void printPurchase(List<Purchase> purchasedItems, char separator) {
        try {
            double sum = purchasedItems.stream().mapToDouble(Purchase::getPrice).sum();
            purchasedItems.forEach(purchase ->
                    System.out.printf("%s %c $%.2f\n", purchase.getItem(), separator, purchase.getPrice()));
            System.out.println("Total sum: $" + String.format("%.2f", sum) + "\n");
        } catch (Exception e) {
            System.out.println("The purchase list is empty\n");
        }
    }

    public List<Purchase> getAllPurchases() {
        return this.purchases.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Purchase> getPurchasesByType(String type) {
        return this.purchases.get(type);
    }
}
