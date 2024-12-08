# Metro POS System

The **Metro POS System** is a comprehensive point-of-sale (POS) application designed for managing the operations of Metro store branches. It includes role-based access for Super Admins, Branch Managers, Data Entry Operators, and Cashiers. The system handles sales, inventory management, vendor and product management, and supports both offline and online database synchronization.

## Features

### User Roles and Permissions
- **Super Admin**:
  - Add branches and assign Branch Managers.
  - View daily, weekly, and monthly sales reports for each branch.

- **Branch Manager**:
  - Add and manage Cashiers and Data Entry Operators.
  - View stock levels and sales reports for their branch.

- **Data Entry Operator**:
  - Add vendors and products for each vendor.
  - Manage products by adding new items or deleting existing ones.

- **Cashier**:
  - Process sales transactions.
  - Manage item returns.

### Database and Synchronization
- **Offline and Online MySQL Integration**:
  - The system uses a local (offline) MySQL database for quick access and storage.
  - It also integrates with an online MySQL database hosted on **Aiven Cloud Services**.
  - When a product is added, it is first stored in the offline database. If the internet is available, the data is synchronized to the online database.
  - If the internet is not available, the queries are stored in a `PendingQueries` table with a timestamp.
  - The system checks for an internet connection every 3 minutes and syncs pending queries based on their timestamp once a connection is established.

## Technology Stack

- **Backend**: Java
- **Frontend**: Java Swing (GUI)
- **Database**: MySQL (Local and Aiven Cloud-based)
- **Architecture**: MVC (Model-View-Controller)

### Prerequisites
- Java JDK (version 8 or higher)
- MySQL (Local database setup)
- IntelliJ IDEA (or any Java IDE)

