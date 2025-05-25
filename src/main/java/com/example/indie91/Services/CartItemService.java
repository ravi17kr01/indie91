package com.example.indie91.Services;

import com.example.indie91.Enums.OrderStatus;
import com.example.indie91.Models.CartItems;
import com.example.indie91.Models.Order;
import com.example.indie91.Models.Product;
import com.example.indie91.Models.User;
import com.example.indie91.Repositories.CartItemsRepository;
import com.example.indie91.Repositories.OrderRepository;
import com.example.indie91.Repositories.ProductRepository;
import com.example.indie91.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemService {
    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    //Return cart items for a particular user
    public List<CartItems> getCartByUserId(){
        return cartItemsRepository.findAll();
    }


    // Add product to cart
    // Steps:

    /*
      Step 1: Validate user
              -> Check if user with userId exists in the database
              -> If not, return 404 "User not found"

    // Step 2: Validate product
    // -> Check if product with productId exists in the database
    // -> If not, return 404 "Product not found"

    // Step 3: Check if there's an active order with PENDING status for the user
    // -> If no such order exists, create a new order with PENDING status and save it

    // Step 4: Check if cart already has the product in the current order
    // -> If not, create a new CartItems entry with the quantity and price (unit price)
    // -> If yes, update:
    //     - Quantity: add the incoming quantity
    //     - Price: newQuantity * unitPrice
    // -> Save the cart item

    // Step 5: Return success response (200 OK) with the active order object
    */

    public ResponseEntity<?> addProductToCart(UUID userId, UUID productId, Long quantity){
        //Check user exists or not
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        User user = userOptional.get();

        //Check product exists or not
        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        Product product = productOptional.get();

        Order activeOrder = orderRepository.findByUserIdAndStatus(userId, OrderStatus.PENDING);

        if(activeOrder == null){
            //create a new entry in db
            Order newOrder = new Order();
            newOrder.setStatus(OrderStatus.PENDING);
            newOrder.setUser(user);

            //save in db
            activeOrder = orderRepository.save(newOrder);
        }

        Optional<CartItems> activeCartItem = cartItemsRepository.findByUserIdAndProduct_ProductIdAndOrderId(userId, productId, activeOrder.getId());

        if(activeCartItem.isEmpty()){
            //create a new entry in db
            CartItems newCartItem = new CartItems();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setOrder(activeOrder);
            newCartItem.setQuantity(quantity);
            newCartItem.setPrice(product.getPrice());

            //save in db
            cartItemsRepository.save(newCartItem);
        }else{
            //first update the quantity and then price
            CartItems existingCartItem = activeCartItem.get();

            BigDecimal unitPrice = product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO;
            long newQuantity = existingCartItem.getQuantity() + quantity;

            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(unitPrice.multiply(BigDecimal.valueOf(newQuantity)));

            cartItemsRepository.save(existingCartItem);
        }

        return ResponseEntity.ok(activeOrder);
    }
}
