
# ADD TO CART APP

## PROBLEM STATEMENT


On a retail website, the following discounts apply:

1. If the user has gold card of the store, he gets a 30% discount,
2. If the user has silver card of the store, he gets a 20% discount,
3. If the user is an affiliate of the store, he gets a 10% discount,
4. If the user has been a customer for over 2 years, he gets a 5% discount. 
5. For every $200 on the bill, there would be a $ 5 discount (e.g. for $ 950, you get $ 20
as a discount).

6. The percentage based discounts do not apply on phones.

7. A user can get only one of the percentage based discounts on a bill.

Write a program in java such that given a
bill, it finds the net payable amount. 
User interface is not required. 

What we care about:

Required Activities
• Object oriented programming approach, provide a high level UML class diagram of
all the key classes in your solution. This can either be on paper or using a CASE tool
• Code to be generic and simple
• Leverage today's best coding practices
• Clear README.md that explains how the code and the test can be run and how the
coverage reports can be generated


## solution


Total discount is a combination of discount on product and discount offered to USER.

Depending on the their type (AFFILIATE, GOLD_CART, SILVER_CART, OLD/NEW CUSTOMER), the user gets (10, 30, 5) percent discount.
Depending on the product purchased the user gets either 2.5% discount if the product is non-phone product or no discount on the product.
A generic formula for computation of product price can be given as: 

DISCOUNTED_TOTAL_BILL = QUANTITY x ( (1-USER_DISCOUNT) x ( (1-PRODUCT_DISCOUNT) x (SUM of per unit non-PHONE product prices) + (SUM of per unit PHONE product prices) ) )


Testcase:

We're assuming the shopper purchases 2 item with unit prices as below:

($19.99 $4.99 14.99 0.99) -- (PHONE $1.99)

CASE 1 >> AFFILIATE (0.9) 2( 0.9(0.975*(1.99+14.99+4.99+19.99) + 0.99) ) = 75.42 discount = 10.48

CASE 2 >> GOLD_CART (0.7) 2( 0.7(0.975*(1.99+14.99+4.99+19.99) + 0.99) ) = 58.66 discount = 27.24

CASE 2 >> SILVER (0.8) 2( 0.8(0.975*(1.99+14.99+4.99+19.99) + 0.99) ) = 67.04 discount = 18.86


CASE 4 >> CUSTOMER (0.95) (Since more than two years) 2( 0.95(0.975*(1.99+14.99+4.99+19.99) + 0.99) ) = 79.61 discount = 6.29

CASE 5 >> CUSTOMER (0.95) (Since less than two years) 2( 0.975(1.99+14.99+4.99+19.99) + 0.99 ) = 83.8 discount = 2.1








