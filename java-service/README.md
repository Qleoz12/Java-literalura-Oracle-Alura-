
# Literalura
![Badge](https://github.com/vitor-fidelis/literalura/blob/main/imagens/badge%20literalura.png)

**Literalura** is a Java/Spring Boot application for book lovers. This application allows users to search for books, list registered books, list authors, and many other features related to reading and organizing books.

## Features

1. **Search for books by title**: Queries the Gutendex API to search for books by title.
2. **List registered books**: Displays all books registered in the database.
3. **List registered authors**: Displays all authors of registered books.
4. **List authors alive in a specific year**: Lists authors who were alive in a specified year.
5. **List authors born in a specific year**: Lists authors born in a specified year.
6. **List authors by the year of their death**: Lists authors who died in a specified year.
7. **List books in a specific language**: Lists books registered in the database in a specified language.
8. **Close the application**: Shuts down the program.

## Technologies Used

- **Java 17**
- **Spring Boot 2.7**
- **Hibernate**
- **PostgreSQL**
- **Gutendex API**
- **Maven**

## Project Setup

### Prerequisites

- Java 17 or later
- Maven
- PostgreSQL

### Installation
*bonus jenv*
```bash
jenv use jdk17
set JAVA_HOME=D:\java\corretto-17.0.13-2
```

1. Clone the repository:
   ```bash
   git clone https://github.com/seu-usuario/literalura.git
   cd literalura
   ```

2. Configure the database in the `application.properties` file:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. Run the project:
   ```bash
   mvn spring-boot:run
   ```

## Project Structure

- `br.com.alura.literalura`: Main project package.
   - `principal`: Contains the `Principal` class, which manages application execution.
   - `model`: Contains model classes (`Book`, `Author`, `BookDTO`, `AuthorDTO`).
   - `repository`: Contains Spring Data JPA repository interfaces.
   - `service`: Contains service classes (`APIConsumer`, `DataConverter`).

## Usage

When the application starts, the main menu will be displayed with the available options. 
Follow the on-screen instructions to navigate through the features.

### Usage Example

1. **Search for books by title**:
   - Enter `1` and press Enter.
   - Provide the title of the book you want to search for.
   - The application will query the Gutendex API and display the found results.

2. **List registered books**:
   - Enter `2` and press Enter.
   - The application will list all books registered in the database.

3. **List registered authors**:
   - Enter `3` and press Enter.
   - The application will list all authors of the registered books.

4. **List authors alive in a specific year**:
   - Enter `4` and press Enter.
   - Provide the desired year.
   - The application will list authors who were alive in that year.

5. **List authors born in a specific year**:
   - Enter `5` and press Enter.
   - Provide the desired year.
   - The application will list authors born in that year.

6. **List authors by the year of their death**:
   - Enter `6` and press Enter.
   - Provide the desired year.
   - The application will list authors who died in that year.

7. **List books in a specific language**:
   - Enter `7` and press Enter.
   - Provide the language code (e.g., `en` for English, `pt` for Portuguese).
   - The application will list all books registered in the database in that language.

8. **Close the application**:
   - Enter `0` and press Enter.
   - The application will be shut down.

## Contribution

If you want to contribute to the project, follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b my-feature`.
3. Make your changes and commit them: `git commit -m 'My new feature'`.
4. Push to the original repository: `git push origin my-feature`.
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

## Contact

Feel free to reach out if you have any questions or suggestions.
