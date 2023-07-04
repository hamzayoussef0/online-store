In this project i thought about an online store where data are stored into a relational database (postgresql),
using java language and spring boot framework.

In the database there are 5 principal tables:
1- Users
2- Stock managers
3- Products
4- Carts

- User table has: id,name, email, password, balance and a boolean variable to check if the user is logged or not.

- Stock manager table has: id, name, email, password and boolean variable to check if the user is logged or not.

- Product table has: id, name, quantity, price for a single product, @ManyToMany relation with carts.

- Cart table has: id, total price for all products inside, list of products quantities requested by the user,
  @OneToOne relation with a specific user, @ManyToMany relation with products.

When user is registered, own cart is created and to do any operation, user must log in before.

When stock manager is registered and to manage products in the store, stock manager must log in before.

Eich user can add products to their cart (if the product quantity requested is available).

products quantities in store will be updated only when a specific user confirm the order,in that case the user balance
it will be updated due to  the total price of products requested, and the quantity of product requested in the other carts
will be updated if the quantity requested is greater than the new quantities in store.
 



