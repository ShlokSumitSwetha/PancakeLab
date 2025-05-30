# Pancake Lab

## Description

Our Coding Dojo uses Pancake Lab, a pancake shop software solution for ordering pancakes.
The software solution can be used to order pancakes to the disciples' rooms and also
by the Sensei's Chef and Delivery service to prepare and deliver the pancakes.

### Use case

1. In the first step the Disciple creates an Order and specifies the building and the room number.
2. After that the Disciple can add pancakes from the menu.
3. The Disciple can choose to complete or cancel the Order, if cancelled the Order is removed from the database.
4. If the Disciple completes the Order the Chef can prepare the pancakes.
5. After the Chef prepares the Order it can be delivered.
6. After the Order is sent for delivery it is removed from the database.

## Problem statement

At first all was well but soon Disciples started to demand various kinds of pancakes that the application
didn't support or anyone could ever imagine.
The evil Dr. Fu Man Chu, the main Villain fighting the Sensei in an endless confrontation, took the chance
to bring havoc against his hated Dojo.
He hacked the system to add mustard with milk-chocolate and whipped cream pancake.
Some errors were also reported, disciples reported that pancakes were missing, delivery reported that they were
sent to a building that does not exist, etc.

# Assignment

The Sensei proposed that the application should be refactored. You, his best Disciple, have been tasked
to save the pancakes production in the Dojo.

The Sensei has put forth some conditions:

- the solution and the client API should be based on pure Java only, without usage of any frameworks and/or external
  dependencies
- the solution should be based on Object-oriented programming
- TDD should be followed
- a Design pattern should be used to avoid hardcoding recipes for pancakes and to allow disciples to choose the
  ingredients
- input data should be validated
- possible data-race issues should also be addressed
- documentation in the form of UML diagrams would be beneficial

Start now your assignment and save the Dojo.

# Solution:
# 🥞 Pancake Order System

A modular Java application built using object-oriented design patterns such as
**Builder**,
**Template Method**,
and **Decorator** to manage pancake orders.

This system supports the creation, preparation, delivery, and cancellation of pancake orders in a structured and
extensible way.

## 🧱 Design Patterns Used

### ✅ 1. Decorator Pattern

Used to dynamically add ingredients to pancakes.

```java
Pancake pancake = new DarkChocolate(new WhippedCream(new BasePancake()));
```
### ✅ 2.  Builder Pattern
Used to construct pancakes flexibly:

```java
Pancake pancake = new PancakeBuilder()
                    .addIngredient("Dark Chocolate")
                    .addIngredient("Whipped Cream")
                    .build();
```

### ✅ 3.  Template Method Pattern
Encapsulates the order processing steps.

```java
OrderProcessTemplate.processOrder(UUID orderId):
    ├── prepare(orderId)
    ├── deliver(orderId)
    └── complete(orderId)
 ``` 
Implemented by PancakeOrderProcess.


### Project Structure
![Pancake Diagram](images/project_structure.png)

## 📦 Modules
- **model**: Contains data models such as `Order`, `Pancake`, and ingredients.
- **service**: Business logic for managing orders, pancakes, and delivery.
- **builder**: Builder pattern implementation for creating pancakes with ingredients.
- **template**: Template method pattern for processing orders in a consistent way.
- **logging**: Logging operations for tracking order lifecycle events.
- **constant**: Enum definitions such as `OrderStatus` and `Ingredient`.


🔧 OrderService
- `Manages the lifecycle of orders.`
- ` Creates, prepares, cancels, completes, and deletes orders.`
- `Tracks order statuses (CREATED, PREPARED, COMPLETED, CANCELLED).`

📦 PancakeService
- `Manages pancake creation and association with orders.`

🚚 DeliveryService
- `Handles the delivery of prepared orders.`

🧰 PancakeBuilder
- `Builds customizable pancakes using a builder pattern.`

🧬 OrderProcessTemplate
- ` Abstract process flow (Template Method Pattern).`

🧬 PancakeOrderProcess
- `Concrete implementation of the process.`
- `Defines the high-level process to fulfill an order.`
- `Ensures consistent order lifecycle management.`

---

## Activity Diagram for Pancake Creation Process

This diagram will show the steps involved in building a pancake using the builder pattern.

![Pancake Diagram](images/class_diagram.png)

### Class Diagram

This diagram will represent the relationship between the PancakeBuilder, Pancake, Ingredient, and various decorator classes.


Key Classes:
* PancakeBuilder
* Pancake (abstract base class)
* BasePancake (concrete class)
* DarkChocolate, MilkChocolate, Hazelnut, WhippedCream (decorator classes)
* Ingredient enum

![Pancake Diagram](images/complete_class_diagram.png)

### Installation Instructions

Follow these steps to set up the Pancake Order System on your local machine:
1.	Unzip the project directory
2.  Import the project into the IDE
3.  cd PancakeLab
4. Install dependencies using Maven (ensure you have Maven installed): mvn install
5. Build the project: After the dependencies are installed, compile and package the application using Maven:mvn package
6. 

### Running the Application

	1.	Run the application:
Once the project is set up, you can run the main application via the following Maven command:


### Step-by-Step Input Flow
The system will guide you through the process of creating and managing a pancake order. Here’s how it works:


### 1. Create an Order
```java
Welcome to Pancake Factory!

Please select an option:
1. Place an Order
2. Exit
👉 Enter your choice (1-3): 
    1  
👉 Enter your name:
    Sumith
👉 Enter the building number:
    (Note: We provide delivery to Building numbers from 1-10 only)
     1
👉 Enter the room number:
Note: We provide delivery from room number 1-100 only
     23
```
### 2 Build your pancake
```java
🥞 Build your pancake by choosing ingredients:

1. Milk Chocolate ($3.0)

2. Dark Chocolate ($3.0)

3. Whipped Cream ($2.0)

4. Hazelnut ($2.0)

👉 Enter ingredient numbers (comma-separated):
1,3,4

✅ Creating Pancake with ingredients:  Milk Chocolate Whipped Cream Hazelnut

👉 How many pancakes would you like?  
 5
```

### 3 Order Summary
```java
---******** Your Order Summary ******---
Name:Sumith
Pancake: Milk Chocolate Whipped Cream Hazelnut
Quantity:5
Total Price: $35.0

------ ****************************** ---------

```
### 4 Confirm Order

```java
Please select an option to confirm your order:
1. Proceed with the order
2. Cancel the order
👉 Enter your choice : 
1

Your order is successful and pancake will be delivered in 15 minutes.
```

### 5  Cancel Order
```java
Please select an option to confirm your order:
1. Proceed with the order
2. Cancel the order

 👉 Enter your choice :
 2
Your order is cancelled.
```

### Logs

```java
2025-05-26 19:51:49 INFO  org.pancakelab.logging.OrderLog - 🧇 Added pancake ' Milk Chocolate Whipped Cream Hazelnut' to order 363b408c-88ab-4dc3-a2da-cc8e134feb9b (1 pancakes) [Building 1, Room 1]
2025-05-26 20:09:34 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order 6f700d38-f304-4534-ac77-c1c7e54967a6 (3 pancakes left) [Building 1, Room 101]
2025-05-26 20:09:35 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 3569ff8c-3cb4-492d-810b-2e9f9f0e45c4 (0 pancakes left) [Building 1, Room 101]
2025-05-26 21:24:32 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 7c0c829a-3eb2-4e81-af26-300716a4758e with 0 pancakes [Building 8, Room 808]
2025-05-26 21:24:32 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 328e3315-a399-451b-bf24-677cc2a21c77 with 0 pancakes [Building 3, Room 303]
2025-05-26 21:24:32 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 9c721c5d-69ab-4a29-b45d-5cc8ee69eb45 with 0 pancakes [Building 2, Room 202]
2025-05-26 21:29:57 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order fcb2b6e8-d668-40ab-9b68-3f15f5dc4227 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:29:57 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order a300370a-9c69-4f55-a168-13a0def6be7a with 0 pancakes [Building 1, Room 101]
2025-05-26 21:29:57 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 695cbb67-d834-4f98-931d-f4cce2fb3b3b with 0 pancakes [Building 1, Room 101]
2025-05-26 21:29:57 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 695cbb67-d834-4f98-931d-f4cce2fb3b3b with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:47 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 5b7dd18f-f2e0-4972-a51f-c6fa74a04791 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:50 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 7c3d0198-fd65-45f5-93ca-210225ce9b87 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:50 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 2738b853-9079-454c-8965-539be5c9c53f with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:50 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order e29f2985-fd37-47d6-b051-c0cd760fcbc5 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:50 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order e29f2985-fd37-47d6-b051-c0cd760fcbc5 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:58 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order cea2634e-2b66-4f10-903b-866b4c5dbbf0 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:58 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order e1c6cc7a-129f-4eaa-96d9-d5966f4c902a with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:58 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 9571acb8-e937-4e18-bc8a-bd43c0ed7797 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:30:58 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 9571acb8-e937-4e18-bc8a-bd43c0ed7797 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:06 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order fa6c7128-bf28-4eb5-a690-f4f7ee217660 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:06 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order cd909b41-1d6c-498d-899e-fe630c0b82fc with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:06 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 7eca759c-d708-44bb-9155-da65d2a8199c with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:06 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 7eca759c-d708-44bb-9155-da65d2a8199c with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:14 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 49038c85-5156-4d7f-9ecc-f1cf4b3a265d with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:14 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 8d48d924-196b-4fa7-a8b5-8afbd1d4f1e5 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:14 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order ed37483c-7938-49e2-9a8d-ef9de28d5142 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:14 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order ed37483c-7938-49e2-9a8d-ef9de28d5142 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order c5699cb8-a4bb-4496-adf8-72f9b97a453d with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 08d81a89-33dc-48bc-9d9d-91acb62d11c3 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 307fb526-9f22-41c3-ae05-bf82411a41b4 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 307fb526-9f22-41c3-ae05-bf82411a41b4 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:43 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 82581241-ccce-43d9-b11a-e283a4315ede with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:43 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 862478d7-98fc-4929-bcab-8f8956a35faf with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:43 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order c386d77d-1545-4547-aa5b-6a06138e0d15 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:31:43 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order c386d77d-1545-4547-aa5b-6a06138e0d15 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:04 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order f77662ae-4a57-4c66-9bf7-285d5e38ba2f with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:04 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order dd21a2a4-f119-4b06-82d0-7f537196fb95 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:04 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 65b32ea3-01dc-4ad8-8aa8-085517501f1a with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 30b9c946-ab25-4f60-a600-fc3ec1438afe with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order ef0220d4-2486-4423-8004-d61fdff8e2ed with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 13f2f35a-d1c9-4b9f-8906-6bff612067b7 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:30 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order b9c7add2-85a5-4d6a-945b-8d7ae7bae8e8 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:42 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order f6673bf2-4e4a-41ac-80d5-5666be4d170e with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:42 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order fd705fc8-36ef-446d-9cb4-863c43687fbb with 0 pancakes [Building 1, Room 101]
2025-05-26 21:32:42 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 53625445-cd92-484d-86e3-65f79d86361b with 0 pancakes [Building 1, Room 101]
2025-05-26 21:33:01 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 9b05ac33-eef5-4353-b49e-fa3911d39e7d with 0 pancakes [Building 8, Room 808]
2025-05-26 21:33:01 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order b366181b-6b6a-412c-8968-b455aa29458e with 0 pancakes [Building 3, Room 303]
2025-05-26 21:33:01 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 0f2c5f80-0e36-45bc-9da5-e3c8e5bd86cc with 0 pancakes [Building 2, Room 202]
2025-05-26 21:33:01 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order f69c0542-777c-43bd-a457-1239a926a7b3 (3 pancakes left) [Building 1, Room 101]
2025-05-26 21:33:01 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 4c481ee3-78f3-493b-bbbb-5ae5850447c1 (0 pancakes left) [Building 1, Room 101]
2025-05-26 21:33:01 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 55ca78c0-2f86-4124-bc52-8f010c003f15 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:33:01 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order c1fbb897-6650-4771-9da9-c10d93ce1b7e with 0 pancakes [Building 1, Room 101]
2025-05-26 21:33:01 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 303a8afa-32d9-4092-bce1-7ccc41ae4bee with 0 pancakes [Building 1, Room 101]
2025-05-26 21:33:46 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order c260a7cf-8703-4c1c-801a-278597b38c6d with 0 pancakes [Building 8, Room 808]
2025-05-26 21:33:46 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 22722d97-2829-48da-90a5-0a7d7c2ab750 with 0 pancakes [Building 3, Room 303]
2025-05-26 21:33:46 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order e9c3cc1f-743a-4079-ac99-7201d809af9e with 0 pancakes [Building 2, Room 202]
2025-05-26 21:33:46 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order 6b431285-1280-453c-a3b6-9d78e6a783bf (3 pancakes left) [Building 1, Room 101]
2025-05-26 21:33:46 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 9623dbb2-4d3f-4c30-9b30-e464d08aa167 (0 pancakes left) [Building 1, Room 101]
2025-05-26 21:33:47 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 752c8205-c751-44bf-b813-5c5aa6995840 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:33:47 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 2ab8f11b-e964-4e09-9d2a-fc236fae1e65 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:33:47 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 8b5a995f-3fee-4f8a-8406-c11bcf63c165 with 0 pancakes [Building 1, Room 101]
2025-05-26 21:35:29 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order e923a5ec-c441-49bd-be78-9fcfee88732d with 0 pancakes [Building 8, Room 808]
2025-05-26 21:35:29 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 0bf07222-ff7b-4cc8-82da-7e6db4840588 with 0 pancakes [Building 3, Room 303]
2025-05-26 21:35:29 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order b21591a4-b54e-4390-a948-0cb8193b1b2c with 0 pancakes [Building 2, Room 202]
2025-05-26 21:35:29 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order 3f689d02-f475-430e-ab5a-88f9be3a317d (3 pancakes left) [Building 1, Room 101]
2025-05-26 21:35:29 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 1e33bf11-22c8-45ae-b7fd-5bc141b15601 (0 pancakes left) [Building 1, Room 101]
2025-05-26 21:35:29 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order d7ec0e17-9f98-4c39-88ee-fa516e120bec with 0 pancakes [Building 1, Room 101]
2025-05-26 21:35:29 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 6a200eb2-8540-43f8-912d-d1f0633342ae with 0 pancakes [Building 1, Room 101]
2025-05-26 21:35:29 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 85deb5e1-5191-4394-95bb-47680bcc0ba8 with 0 pancakes [Building 1, Room 101]
2025-05-26 22:04:31 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 70c26783-019a-4b2e-9be0-76c837cd91c8 with 0 pancakes [Building 8, Room 808]
2025-05-26 22:04:31 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order d10a401c-e1ca-4605-b6e7-8bb179deb0b2 with 0 pancakes [Building 3, Room 303]
2025-05-26 22:04:31 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 2c3b8d1a-e696-4ac3-b763-a8dca60534df with 0 pancakes [Building 2, Room 202]
2025-05-26 22:04:31 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order b107f37a-8d1c-4d15-ae76-fe92b64ae469 (3 pancakes left) [Building 1, Room 101]
2025-05-26 22:04:31 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order fbf1156d-27fb-4967-ae7f-de435a4c47ca (0 pancakes left) [Building 1, Room 101]
2025-05-26 22:04:31 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order ab822fb9-83a3-4923-968b-946df684747f with 0 pancakes [Building 1, Room 101]
2025-05-26 22:04:31 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 81d5c760-31e5-4084-aab3-7f9abbd4c4f8 with 0 pancakes [Building 1, Room 101]
2025-05-26 22:04:31 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 1620fc06-26e1-4490-83d5-3b70270dac23 with 0 pancakes [Building 1, Room 101]
2025-05-26 23:16:40 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 948f4ea7-34f5-45ef-9f5c-8874ffaf21e0 with 0 pancakes [Building 8, Room 808]
2025-05-26 23:16:40 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order fe8e2dd3-d12e-420c-88ac-e174885c7a2a with 0 pancakes [Building 3, Room 303]
2025-05-26 23:16:40 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 6d349495-b8e8-46d5-b349-6c2cd1b50b9c with 0 pancakes [Building 2, Room 202]
2025-05-26 23:16:40 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 64e2ef7d-223a-481c-8021-5c231756b540 with 0 pancakes [Building 1, Room 101]
2025-05-26 23:16:41 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 6b4dd7a7-fc0c-41d6-8a1f-fd59db2f784d with 0 pancakes [Building 1, Room 101]
2025-05-26 23:16:41 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 74123c44-be70-452b-8c23-9fa5f89d279d with 0 pancakes [Building 1, Room 101]
2025-05-26 23:16:41 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order c73acf54-3b77-4b9e-a2ca-34a8f43c7907 (3 pancakes left) [Building 1, Room 101]
2025-05-26 23:16:41 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 42f24c51-d139-47c3-8a9d-90a9ae4eaefc (0 pancakes left) [Building 1, Room 101]
2025-05-26 23:16:58 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order d84c62dd-7c42-481e-ad48-04210bbe1f7a with 0 pancakes [Building 8, Room 808]
2025-05-26 23:16:58 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order edcdcf6b-c83d-419c-9d81-dfcd8ec01328 with 0 pancakes [Building 3, Room 303]
2025-05-26 23:16:58 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 9f0082d1-531d-4917-8445-39bc14c0bfb3 with 0 pancakes [Building 2, Room 202]
2025-05-26 23:16:58 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 6995b81d-9366-44b4-b852-34762268900d with 0 pancakes [Building 1, Room 101]
2025-05-26 23:16:58 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 637f4d6a-816a-4200-a0d4-975df8eb7c7d with 0 pancakes [Building 1, Room 101]
2025-05-26 23:16:58 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 4c344549-490d-4265-b3b8-ad98ea82621a with 0 pancakes [Building 1, Room 101]
2025-05-26 23:16:58 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order 0b0b3e9e-1a9b-433b-b459-2582e8784ff6 (3 pancakes left) [Building 1, Room 101]
2025-05-26 23:16:58 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 1db26a84-0965-44b2-ae15-17f75aad8389 (0 pancakes left) [Building 1, Room 101]
2025-05-26 23:18:59 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 6dffc438-3ed9-4744-b74a-99f8fe99ef5f with 0 pancakes [Building 8, Room 808]
2025-05-26 23:18:59 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order ee548f55-5f37-4489-a719-77d56b5f3c7a with 0 pancakes [Building 3, Room 303]
2025-05-26 23:18:59 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 864d8534-e91c-4d14-bd7f-397c4f447acf with 0 pancakes [Building 2, Room 202]
2025-05-26 23:18:59 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order c7020eb3-dc47-416f-b072-4e1e17dc0dbe with 0 pancakes [Building 1, Room 101]
2025-05-26 23:18:59 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 9e950900-0c21-4897-8c88-41814791c61b with 0 pancakes [Building 1, Room 101]
2025-05-26 23:18:59 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order a7459127-04c9-48ca-bda8-a3db3d305791 with 0 pancakes [Building 1, Room 101]
2025-05-26 23:18:59 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order 019a9cef-b999-45e5-922b-c8f3afda4bc3 (3 pancakes left) [Building 1, Room 101]
2025-05-26 23:18:59 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 880fae38-f22c-49fd-8ab2-8308f326e3f4 (0 pancakes left) [Building 1, Room 101]
2025-05-26 23:20:00 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 526219e5-7e62-4ac6-95bd-7d007c57a363 with 0 pancakes [Building 8, Room 808]
2025-05-26 23:20:00 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order f91cf98d-cd52-4682-ad7e-cc102038712c with 0 pancakes [Building 3, Room 303]
2025-05-26 23:20:00 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 223e513d-9777-4177-ad6f-0c90945601ca with 0 pancakes [Building 2, Room 202]
2025-05-26 23:20:00 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 9de3fbf6-2177-491c-9c43-6040ac49431e with 0 pancakes [Building 1, Room 101]
2025-05-26 23:20:00 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order 4a8c1dbf-887b-4266-bb5a-82a582cbd8a0 with 0 pancakes [Building 1, Room 101]
2025-05-26 23:20:00 INFO  org.pancakelab.logging.OrderLog - 🚚 Delivered order e7633080-3be6-484c-8469-e7138386636e with 0 pancakes [Building 1, Room 101]
2025-05-26 23:20:00 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 5 pancake(s) from order 0461dac1-5898-46cc-bf0b-c31610373b28 (3 pancakes left) [Building 1, Room 101]
2025-05-26 23:20:00 INFO  org.pancakelab.logging.OrderLog - 🗑️ Removed 2 pancake(s) from order 4d789f60-a8d6-41c5-8a11-f80269a7cada (0 pancakes left) [Building 1, Room 101]
2025-05-27 06:04:01 WARN  org.pancakelab.logging.OrderLog - ❌ Cancelled order 159af001-67f9-47ef-8b3d-096ed7f350eb with 1 pancakes [Building 1, Room 1]
```