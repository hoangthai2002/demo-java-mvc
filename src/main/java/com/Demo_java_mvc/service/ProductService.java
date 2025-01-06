package com.Demo_java_mvc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Demo_java_mvc.domain.Cart;
import com.Demo_java_mvc.domain.CartDetail;
import com.Demo_java_mvc.domain.Order;
import com.Demo_java_mvc.domain.OrderDetail;
import com.Demo_java_mvc.domain.Product;
import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.repository.CartDetailRepository;
import com.Demo_java_mvc.repository.CartRepository;
import com.Demo_java_mvc.repository.OrderDetailRepository;
import com.Demo_java_mvc.repository.OrderRepository;
import com.Demo_java_mvc.repository.ProductRepository;
import com.Demo_java_mvc.service.speficication.ProductSpecs;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository oderDetailRepository;

    public ProductService(
            ProductRepository productRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService,
            OrderRepository orderRepository,
            OrderDetailRepository oderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.oderDetailRepository = oderDetailRepository;

    }

    // Taoj mới sản phẩm
    public Product createProduct(Product pr) {
        return this.productRepository.save(pr);

    }

    // lấy tất cả sản phẩm lên
    public Page<Product> fetchProducts(Pageable page) {
        return this.productRepository.findAll(page);
    }

    // case1 tim name
    // public Page<Product> fetchProductsWithSpec(Pageable page, String name) {
    // return this.productRepository.findAll(page, ProductSpecs.nameLike(name));
    // }
    // case2:tim min price
    // public Page<Product> fetchProductsWithSpec(double min, Pageable page) {
    // return this.productRepository.findAll(ProductSpecs.minPrice(min), page);
    //
    // }
    // case3 tìm max price
    // public Page<Product> fetchProductsWithSpec(double max, Pageable page) {
    // return this.productRepository.findAll(ProductSpecs.maxPrice(max), page);
    // }
    // case4:tim factory
    // public Page<Product> fetchProductsWithSpec(String factory, Pageable page) {
    // return this.productRepository.findAll(ProductSpecs.matchFactory(factory),
    // page);
    // // tìm factory
    // }
    // case5:tim nhieu factory
    //
    // case6:tim khoang price
    public Page<Product> fetchProductsWithSpec(Pageable page, String price) {
        if (price.equals("10-toi-15trieu")) {
            double min = 1000000;
            double max = 1500000;
            return this.productRepository.findAll(ProductSpecs.matchPrice(min, max), page);
        } else if (price.equals("15-toi-30trieu")) {
            double mix = 1500000;
            double max = 3000000;
            return this.productRepository.findAll(ProductSpecs.matchPrice(mix, max), page);
        } else
            return this.productRepository.findAll(page);
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

    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user đã có Cart chưa ? nếu chưa -> tạo mới
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }

            // save cart_detail
            // tìm product by id

            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
                //
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(quantity);
                    this.cartDetailRepository.save(cd);

                    // update cart (sum);
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
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

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOder(
            User user,
            HttpSession session, String receiverName,
            String receiverAddress, String receiverPhone) {

        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                // crete oder
                Order order = new Order();
                order.setUser(user);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setReceiverName(receiverName);
                order.setStatus("PENDING");

                // crete oderDetail
                // b1 get cart by user
                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                for (CartDetail cd : cartDetails) {

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.oderDetailRepository.save(orderDetail);
                }

                // b2 delete cart=detail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }
                this.cartRepository.deleteById(cart.getId());
                // b3 update session
                session.setAttribute("sum", 0);
            }
        }

    }

}
