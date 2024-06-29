### Database Setup

-- Create the database if it does not already exist
CREATE DATABASE IF NOT EXISTS pass_manager;

-- Switch to the pass_manager database
USE pass_manager;

-- Create the apps table if it does not already exist
CREATE TABLE IF NOT EXISTS apps (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sys_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    app_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    Notes TEXT
);

-- Retrieve all records from the apps table to verify the setup
SELECT * FROM apps;

### App Usage:-

# App Usage

To use the `pass_gen` application, follow these steps:

## Setup and Launch the Application

1. **Ensure Database Setup**
    - Make sure you have a MySQL server running.
    - The database `pass_manager` should be set up as described in the Database Setup section.

2. **Save the Java Code**
    - Save the provided code into a file named `main.java`.

3. **Compile the Java Application**
    - Open a terminal or command prompt.
    - Navigate to the directory containing `main.java`.

4. **Run the Java Application**
    - After successful compilation, run the application:


## Using the Application

1. **Generate a Password**
    - In the application window, enter the desired lengths for the total password, alphabets, digits, and symbols in their respective fields.
    - Click the "Generate Password" button to create a password. The generated password will appear in the text area below.

2. **Save the Password**
    - Enter the application name in the "App Name" field.
    - Optionally, enter a passphrase in the "Passphrase" field for encryption.
    - Enter any additional notes in the notes area.
    - Click the "Save" button to store the password and notes in the database.

By following these steps, you will be able to generate and store passwords securely using the `pass_gen` application.

