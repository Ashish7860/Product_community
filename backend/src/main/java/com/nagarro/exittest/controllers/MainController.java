package com.nagarro.exittest.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.exittest.impl.ProductServiceImpl;
import com.nagarro.exittest.impl.ReviewServiceImpl;
import com.nagarro.exittest.impl.UserServiceImpl;
import com.nagarro.exittest.models.Product;
import com.nagarro.exittest.models.Review;
import com.nagarro.exittest.models.Role;
import com.nagarro.exittest.models.Status;
import com.nagarro.exittest.models.User;
import com.nagarro.exittest.models.UserRole;

@RestController
@CrossOrigin("*")
public class MainController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ReviewServiceImpl reviewService;

	@PostMapping("/user/register")
	@CrossOrigin("*")
	public User register(@RequestBody User user) throws Exception {
		try {

			// Encode the password
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			Set<UserRole> roles = new HashSet<>();
			Role role = new Role();
			if (user.getEmail().equalsIgnoreCase("yagyasaini9453932298@gmail.com")) {
				role.setRoleId(44L);
				role.setRoleName("ADMIN");
			} else {
				role.setRoleId(45L);
				role.setRoleName("NORMAL");

			}

			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			roles.add(userRole);
			return this.userService.createUser(user, roles);
		} catch (Exception e) {
			throw new Exception("User with email " + user.getEmail() + " Already exists!!");
		}
	}


	@PostMapping("/allProducts")
	public Status saveProduct(@Valid @RequestBody Product product) {
		return productService.addProduct(product);
	}


	@GetMapping("/products")
	@CrossOrigin("*")
	public List<Product> product(@RequestParam String query) throws Exception {

		try {
			List<Product> products = this.productService.fetchProductByProductNameOrBrandOrProductCode(query);

			return products;
		} catch (Exception e) {
			throw new Exception("Product Not Found!");
		}
	}


	@GetMapping("/allProducts")
	@CrossOrigin("*")
	public List<Product> products() throws Exception {

		try {
			List<Product> products = this.productService.findAll();

			return products;
		} catch (Exception e) {
			throw new Exception("Product Not Found!");
		}
	}


	@GetMapping("/user/users")
	public List<User> showUser() {
		return this.userService.findAll();
	}

	
	@GetMapping("/user/products")
	@CrossOrigin("*")
	public List<Product> showProducts() {
		return this.productService.findAll();
	}


	@GetMapping("/user/reviews")
	public List<Review> showReviews() {
		return this.reviewService.findAllReviews();
	}


	@GetMapping("/admin/reviews")
	public List<Review> showAllReviews() {
		return this.reviewService.findAll();
	}


	@CrossOrigin("*")
	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product product) throws Exception {
		try {
			System.out.println(product);
			return this.productService.saveProduct(product);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	


	@CrossOrigin("*")
	@PostMapping("/addReview")
	public Review addReview(@RequestBody Review review) throws Exception {
		try {
			System.out.println(review);
			return this.reviewService.addReview(review);
		} catch (Exception e) {
			throw new Exception("Bad Data");
		}
	}


	@CrossOrigin("*")
	@GetMapping("products/{productId}/showReviews")
	public List<Review> showProductReview(@PathVariable Long productId) throws Exception {
		try {
			return this.reviewService.showProductReview(productId);
		} catch (Exception e) {
			throw new Exception("Product Not Found");
		}
	}


	@CrossOrigin("*")
	@GetMapping("home/stats")
	public List<Integer> showStates() {
		List<User> users = this.userService.findAll();
		int totalUsers = users.size();
		int posts = this.reviewService.findAllReviews().size();
		int products = this.productService.getNumberofProducts();
		int onlineUsers = 0;
		for (User u : users) {
			if (u.getEnabled() == true) {
				onlineUsers++;
			}
		}
		List<Integer> stats = new ArrayList<>();
		stats.add(totalUsers);
		stats.add(posts);
		stats.add(onlineUsers);
		stats.add(products);
		return stats;
	}


	@PutMapping("review/approve")
	@CrossOrigin("*")
	public Boolean approved(@RequestBody Review review) throws Exception {
		try {
			review.setApproved(true);
			this.reviewService.save(review);
			return true;
		} catch (Exception e) {
			throw new Exception("Something went wrong!!");
		}
	}
	

	@PutMapping("review/cancel")
	@CrossOrigin("*")
	public Boolean cancel(@RequestBody Review review) throws Exception {
	    try {
	        review.setCancel(false);
	        this.reviewService.delete(review);
	        return true;
	    } catch (Exception e) {
	        throw new Exception("Something went wrong!!");
	    }
	}



	@PutMapping("user/active")
	@CrossOrigin("*")
	public User isActive(@RequestBody User user) throws Exception {
		try {
			if (user.getEnabled() == false)
				user.setEnabled(true);
			else
				user.setEnabled(false);
			return this.userService.save(user);
		} catch (Exception e) {
			throw new Exception("Something went wrong!!");
		}
	}


	
	@PostMapping("review/request")
	@CrossOrigin("*")
	public ResponseEntity<List<Review>> requestReview(@RequestBody Product product) {
	    Product p = this.productService.findByProductCode(product.getProductCode());
	    if (p != null) {
	        List<Review> reviews = this.reviewService.findByProductId(p.getProductId());
	        return ResponseEntity.ok(reviews);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	
	@DeleteMapping({"/delete/{productId}"})
    public void deleteproduct(@PathVariable("productId") Long prouductId){
        productService.deleteproduct(prouductId);        

    }
	
}
