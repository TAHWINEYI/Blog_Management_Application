# Lisbon Blog Project

This repository contains the codebase for the Lisbon Blog, a blogging platform built using Spring Boot. The platform provides users with features to create, read, update, and delete blog posts, as well as manage user accounts. Below is an overview of the key controllers implemented in this project.

---

## Prerequisites

To run this application, ensure you have the following installed on your system:

* Java 17 or higher
* Maven 3.8+
* PostgreSQL database

---

## Features

### UsersController

The `UsersController` handles operations related to user management.

#### Endpoints

1. **GET /api/users**

   * Fetch all users in the system.
   * Caching is enabled for better performance.
   * Response: `List<UsersDTO>`

2. **GET /api/users/{user\_id}**

   * Retrieve details of a specific user by their ID.
   * Caching enabled.
   * Response: `Optional<UsersDTO>`

3. **POST /api/users/register**

   * Register a new user.
   * Request: `CreateUserDTO`
   * Response: `Users`

4. **PUT /api/users/updateUser/{user\_id}**

   * Update an existing user by their ID.
   * Request: `CreateUserDTO`
   * Caching enabled.
   * Response: `Users`

5. **DELETE /api/users/deleteUser/{user\_id}**

   * Delete a user by their ID.
   * Only accessible to users with the `ADMIN` role.
   * Caching evicted on successful deletion.
   * Response: Success message.

6. **POST /api/login**

   * Verify user login credentials.
   * Request: `Users`
   * Response: Verification status as `String`.

---

### PostsController

The `PostsController` manages blog post-related operations.

#### Endpoints

1. **GET /api/posts**

   * Fetch all blog posts.
   * Response: `List<PostsDTO>`

2. **GET /api/posts/{post\_id}**

   * Retrieve a specific post by its ID.
   * Response: `Optional<PostsDTO>`

3. **POST /api/posts/newPost**

   * Create a new blog post.
   * Request: `PostsDTO`
   * Response: `Posts`

4. **DELETE /api/posts/deletePost/{post\_id}**

   * Delete a blog post by its ID.
   * Response: Success message.

---

## Technologies Used

* **Spring Boot**: Framework for building Java applications.
* **Jakarta Validation**: To validate request inputs.
* **Spring Security**: For securing endpoints and handling roles.
* **Spring Cache**: For improving performance by caching data.
* **Lombok**: To reduce boilerplate code.
* **PostgreSQL**: Database for persisting data.

---

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/username/lisbon_blog.git
   ```
2. Navigate to the project directory:

   ```bash
   cd lisbon_blog
   ```
3. Update `application.properties` with your PostgreSQL database credentials.
4. Run the application:

   ```bash
   mvn spring-boot:run
   ```
5. Access the application at [http://localhost:8080](http://localhost:8080).

---

## Contributing

Feel free to fork this repository and submit pull requests. Please ensure your code adheres to the project’s style and is properly tested.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.
