# Desktop User Management System (Java Swing & MySQL CRUD)

A comprehensive, interactive Desktop Graphical User Interface (GUI) application developed using Java Swing and connected to a MySQL database using JDBC. This project implements full CRUD (Create, Read, Update, Delete) operations and follows essential Software Engineering principles like Object-Oriented Programming (OOP) and Event-Driven Architecture.

## 🚀 Features
- **Create (Register):** Seamlessly adds new user records into the MySQL database with robust input validation.
- **Read (Display):** Automatically fetches and lists existing records inside a structured `JTable` component upon application startup and after every update.
- **Update:** Populates text fields dynamically via a table mouse-click event to modify and update existing database rows safely.
- **Delete:** Safely removes selected records from the database after a secure user confirmation prompt (`JOptionPane`).

## 🛠️ Tech Stack & Concepts Used
- **Language:** Core Java (JDK 8+)
- **GUI Framework:** Java Swing (`JFrame`, `JTable`, `JScrollPane`, `JButton`, `JTextField`)
- **Database:** MySQL (Relational Database Management System)
- **Connectivity:** JDBC (Java Database Connectivity) with MySQL Connector/J
- **OOP Principles:** - **Inheritance:** Extends `JFrame` to inherit window capabilities.
  - **Encapsulation:** Protects internal data components using `private` fields.
  - **Polymorphism:** Method overriding inside Event Listeners (`actionPerformed`).

## 📂 Project Structure
- `registration_form.java` - Houses the core application window, input components, event-driven action listeners, and complete backend database queries.
