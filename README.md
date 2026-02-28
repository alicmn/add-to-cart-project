# ADD TO CART APP

## PROBLEM STATEMENT

On a retail website, the following discounts apply:

1. If the user has a gold card of the store, he gets a 30% discount.
2. If the user has a silver card of the store, he gets a 20% discount.
3. If the user is an affiliate of the store, he gets a 10% discount.
4. If the user has been a customer for over 2 years, he gets a 5% discount. 
5. For every $200 on the bill, there would be a $5 discount (e.g., for $950, you get $20 as a discount).
6. The percentage-based discounts do not apply to phones.
7. A user can get only one of the percentage-based discounts on a bill.

Write a program in Java such that given a bill, it finds the net payable amount. 

What we care about:

Required Activities:
- Object-oriented programming approach.
- Code to be generic and simple.
- Leverage today's best coding practices.
- Clear README.md that explains how the code and the tests can be run.

---

## SOLUTION & ARCHITECTURE

The application uses **Java 10**, **Maven**, and **JUnit 4**.

Total discount is a combination of discount on the product and discount offered to the USER.
The problem statement logic is encapsulated using the **Strategy Design Pattern** and **Dependency Injection** principles. 

### Fixed Business Logic
The initial algorithm missed specific constraints which have been corrected:
1. **Rule 6 Correction:** The user's percentage-based discount (e.g., 30% for Gold) is **only calculated on non-phone products**. The phone product totals are accumulated independently without receiving the generic user discount.
2. **Rule 5 Correction:** A `$5` discount is explicitly deducted from the final calculated subtotal for every `$200` accumulated.

### How to Run the Interactive Application (CLI)

The application has a robust, interactive Command Line Interface built inside `Main.java` allowing dynamic shopping.

Open your terminal and execute:
```bash
mvn clean compile exec:java -Dexec.mainClass="org.ak.billing.Main"
```
Follow the on-screen prompts to define the user type, choose products dynamically from the inventory, set quantities, and print a final detailed invoice.

### How to Run the Tests

The application utilizes **JUnit** for its unit testing, with verified mathematical calculations representing the rules above.

To run the unit tests:
```bash
mvn clean test
```

### Coverage & Assertions Example

Assume the shopper purchases specific items with the following base pricing:
- Clothing & Cosmetics & Electronics & Stationery (Total affected by user discount)
- Phones (Prices excluded from user percentage cuts)

The tests (`ShoppingApplicationTest.java`) systematically verify the net payable amounts for:
- Affiliate User: Evaluated to `$75.61`
- Gold Cart User: Evaluated to `$59.25`
- Silver Cart User: Evaluated to `$67.43`
- Recurring Customer: Evaluated to `$79.71`
- Standard Customer: Evaluated to `$83.80`
