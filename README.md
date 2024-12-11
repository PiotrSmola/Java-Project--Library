# Library Management System

**This project is a course requirement for Object-Oriented Programming at university.**

## Overview

The Library Management System is a desktop application designed to assist library employees in managing book collections and reader information efficiently. The application aims to improve the workflow by providing tools for book lending, returns, and database management.

### Features

- **User Authentication**: Library staff can log in using credentials provided by the administrator.
- **Book Management**: Add, remove, and search for books in the library collection.
- **Reader Management**: Add or remove reader information from the system.
- **Borrowing and Returning**: Efficiently handle book lending and return processes.
- **Data Export/Import**: Support for CSV-based export and import of book and reader data.
- **User Notifications**: Informative messages on successful or failed operations.

## Technologies Used

- **Java**: Primary programming language.
- **Swing**: For creating the graphical user interface (GUI).
- **Maven**: For project build management and dependency handling.
- **OpenCSV**: For handling CSV operations.

## Minimum System Requirements

- **Processor**: Intel Core i3 or equivalent.
- **RAM**: 4 GB (8 GB recommended).
- **Disk Space**: 250 MB minimum.
- **Operating System**: Windows 10 or later with Java Runtime Environment (JRE) installed.

## Project Structure

The application is structured into the following key components:

- `Library`: Main entry point of the application.
- `LoginManager`: Handles user authentication.
- `BookDatabase` and `ReaderDatabase`: Manage book and reader data.
- `BorrowInfo`: Stores information about borrow and return transactions.
- GUI components: 
  - `Login` (login window)
  - `Dashboard` (main window)
  - Additional windows for adding/removing books, searching books, and managing readers.

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/PiotrSmola/Java-Project--Library.git
2. Open the project in an IDE (e.g., IntelliJ IDEA).
3. Ensure the necessary dependencies (e.g., OpenCSV) are resolved using Maven.
4. Build and run the project.

## Future Improvements

- **Enhanced GUI Design**: Develop a more advanced and intuitive user interface with improved responsiveness and customization options.
- **Integration with External Systems**: Add support for integration with external library management systems and electronic cataloging tools.
- **Reporting and Analytics**: Implement detailed reporting features to provide insights into borrowing trends, user behavior, and system usage.
- **Professional Database Integration**: Replace the file-based database with a professional RDBMS like PostgreSQL to improve scalability and reliability.
- **Enhanced Security**: Strengthen data protection with additional security layers, especially for personal data and user authentication.

All rights reserved © Piotr Smoła 2024
