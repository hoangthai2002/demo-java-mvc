package com.Demo_java_mvc.service;

import java.util.List;
import java.util.Optional;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;
import org.springframework.stereotype.Service;

import com.Demo_java_mvc.domain.Cart;
import com.Demo_java_mvc.domain.CartDetail;
import com.Demo_java_mvc.domain.Product;
import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.repository.CartDetailRepository;
import com.Demo_java_mvc.repository.CartRepository;
import com.Demo_java_mvc.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(
            ProductRepository productRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;

    }

    // Taoj mới sản phẩm
    public Product createProduct(Product pr) {
        return this.productRepository.save(pr);

    }

    // lấy tất cả sản phẩm lên
    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    // lấy id
    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    // xóa theo id
    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    // public List<Product> searchProduct(String keyword) {
    // return productRepository.searchProduct(keyword);
    // }

    public void handleAddProductToCart(String email, Long productId, HttpSession session) {

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user đã có cart chưa
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // nếu chưa -->tạo mới
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }
            // save cart detail
            // tìm product by id
            Optional<Product> product = this.productRepository.findById(productId);
            if (product.isPresent()) {
                Product realProduct = product.get();
                // check product có hay chưa
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {
                    // tạo mới
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(1);
                    // lưu cart_detail
                    this.cartDetailRepository.save(cartDetail);

                    // update cart (sum)
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);

                } else {
                    // nếu có product tăng quantity lên
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }
        }

    }

    // tìm giở hàng theo user
    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        // tìm cart theo id(id từ view gửi lên)
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            // từ cart-detail này lấy ra đối tượng cart tương ứng
            CartDetail cartDetail = cartDetailOptional.get();
            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            } else {
                // datele cart sum=1
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }

        }

    }

}
