# Online Library Management System

## Description
The Online Library Management System is a application designed to manage the operations of a library. It allows users to search for books, borrow and return books, and manage their accounts. The system also provides administrative functionalities such as adding new books with respected author and publication details.

## Features
- **Book Search**: Users can search for books by title, author, or publisher.
- **Book Search and Sort**: Users can search and sort for books by title, author, or publisher

## Prerequisites
- [Java] (version 11 or higher)
- [MySql] (version  8.0.40 )
- [Maven] (version 3.8.6)

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/subbu-55/online-library-management-system.git
    ```
2. Navigate to the project directory:
    ```bash
    cd online-library-management-system
    ```
3. Install the dependencies:
    ```bash
    npm install
    ```
4. Set up the environment variables:
    - Create a `.env` file in the root directory.
    - Add the following variables to the `.env` file:
        ```plaintext
        MONGODB_URI=your_mongodb_uri
        PORT=your_port_number
        JWT_SECRET=your_jwt_secret
        ```

## Usage
1. Start the server:
    ```bash
    npm start
    ```
2. Open your browser and navigate to `http://localhost:your_port_number`.

## Examples
- **Search for a book**: Use the search bar on the homepage to find books by title, author, or genre.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributors
- [Sripada Sai Subrahmanya Sharma](https://github.com/yourusername) - Project Lead

## Acknowledgments
- Thanks to [OpenLibrary](https://openlibrary.org/) for providing book data.
- Special thanks to all the contributors and testers who helped improve this project.

