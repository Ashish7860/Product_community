# Product_community
# Product Community Website

## Problem Statement
The goal is to develop a **Product Community Website** where customers can register, browse products, request reviews, and post reviews. The solution comprises two applications: a **REST API backend** for functionality and an **Angular-based frontend** for user interaction. 

---

## Applications Overview

### 1. **Backend Application (REST API – No UI)**
Provides the core functionality of the system. The following APIs are to be developed:

#### **User Authentication API**
- Validates user credentials.
- Supports both successful and unsuccessful authentication with proper responses.

#### **User Registration API**
- Registers new users with mandatory fields like name, email, and password.
- Validates email format and password policies.

#### **Product Search API**
- Allows users to search for products using one or more parameters:
  - Product name
  - Brand
  - Product code

#### **Post Review API**
- Enables users to post a new review for a product with:
  - Ratings (1-5)
  - Heading
  - Detailed review (20–400 characters)

#### **[BONUS] Approve Review API**
- Allows an admin to approve or reject user-submitted reviews.
- Only approved reviews are visible on the storefront.

#### **Request Review API**
- Allows users to request reviews for a product by submitting details like:
  - Product name
  - Product code
  - Brand
- Ensures product codes are unique; if a product exists, it redirects to the product review page.

#### **Stats API**
- Provides statistics for the homepage:
  - Total number of registered users
  - Total products
  - Total reviews

---

### 2. **Frontend Application (Angular)**

#### **Homepage**
- Displays:
  - Links for Registration and Login.
  - Key statistics (e.g., registered users, total products, total reviews).
- For logged-in users:
  - Provides a search box to find products.

#### **Login Page**
- Users can log in with their credentials.
- Handles invalid authentication with proper error messages.

#### **Registration Page**
- Allows new users to register.
- Validates:
  - Email address format.
  - Password policies.
  - Mandatory fields like name.

#### **Product Search Page**
- Lets users search products using:
  - Name
  - Code
  - Brand
- Provides filtering options.
- Displays search results including:
  - Average ratings
  - Number of reviews
- Shows appropriate error messages if no results are found.

#### **Results Page**
- Displays search results using backend APIs.
- Allows further filtering by name, code, or brand.

#### **Product Details Page**
- Shows all reviews for a selected product.
- Includes:
  - Ratings
  - Review headings
  - Detailed reviews.

#### **Ask for Reviews**
- Enables users to raise requests for product reviews through a form.
- Handles duplicate product codes:
  - Shows an error message if a product already exists.
  - Redirects to the product review page after 30 seconds.

#### **Post a Review**
- Lets users post a review through a form with fields:
  - Ratings (1-5)
  - Heading
  - Review (20–400 characters).

#### **Admin Panel**
- Administrators can:
  - View new reviews.
  - Approve or reject reviews.
- Only approved reviews are displayed in the storefront.
- Average ratings are calculated based on approved reviews.

#### **Logout**
- Provides users with an option to log out securely.

---

## Technology Stack

### **Backend**
- Java 8, Spring Boot
- MySQL for database
- RESTful APIs
- Maven for dependency management

### **Frontend**
- Angular 11 (or later)
- Responsive design with SASS/LESS (optional)
- Angular CLI for project scaffolding
- npm for dependency management

---

This project is designed to offer a complete MVP for a product community platform.
