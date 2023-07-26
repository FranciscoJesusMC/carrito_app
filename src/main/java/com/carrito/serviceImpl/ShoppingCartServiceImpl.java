package com.carrito.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrito.dto.CartItemDTO;
import com.carrito.dto.ShoppingCartDTO;
import com.carrito.entity.CartItem;
import com.carrito.entity.Product;
import com.carrito.entity.ShoppingCart;
import com.carrito.entity.Size;
import com.carrito.entity.User;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.CartItemMapper;
import com.carrito.mapper.CartItemMapperImpl;
import com.carrito.mapper.ShoppingCartMapper;
import com.carrito.mapper.ShoppingCartMapperImpl;
import com.carrito.repository.CartItemRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.repository.ShoppingCartRepository;
import com.carrito.repository.SizeRepository;
import com.carrito.repository.UserRepository;
import com.carrito.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SizeRepository sizeRepository;
			
	@Autowired
	private ShoppingCartMapper mapper = new ShoppingCartMapperImpl();
	
	@Autowired
	private CartItemMapper mapperCart = new CartItemMapperImpl();
	
	
	@Override
	public void addCartItemToShoppinCart(long userId, UUID productId,long sizeId, CartItemDTO cartItemDTO) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
	
		Size size = sizeRepository.findById(sizeId).orElseThrow(()-> new ResourceNotFoundException("Size", "id", sizeId));

		List<Size> listOfSizes = sizeRepository.findByProductId(productId);
				
		CartItem cartItem = mapperCart.cartItemDTOtoCartItem(cartItemDTO);
		
		if(!listOfSizes.contains(size)) {
				throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "Size :" +size.getId()+ " not found for product");
		}
		
			
		if(cartItemDTO.getQuantity() <= size.getStock()) {
			
			int newStock = (size.getStock() - cartItemDTO.getQuantity());
			
			size.setStock(newStock);
			sizeRepository.save(size);
			
			BigDecimal newTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
			
			cartItem.setProduct(product);
			cartItem.setPrice(product.getPrice());
			cartItem.setSubtotal(newTotal);
			cartItem.setSize(size);
			cartItem.setShoppingCart(shoppingCart);
			cartItemRepository.save(cartItem);
			
			
			BigDecimal totalAmount =  updateCartTotal(shoppingCart.getId());

			shoppingCart.setTotal(totalAmount);
			shoppingCart.setStatus("waiting for payment..");
			shoppingCartRepository.save(shoppingCart);
				
		}else {
			
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "Not enough stock");
		}
		
	
	}
	

	@Override
	public void removeCartItem(long userId, UUID cartItemId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

		ShoppingCart shoppingCart = user.getShoppingCart();
		
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("CartItem", "id", cartItemId));
		
		Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow(()-> new ResourceNotFoundException("Product", "id", cartItem.getProduct().getId()));
		
		Size size = sizeRepository.findById(cartItem.getSize().getId()).orElseThrow(()-> new ResourceNotFoundException("Size", "id", cartItem.getSize().getId()));
		
		List<Size> listOfSizes = sizeRepository.findByProductId(product.getId());
		
		
		if(!shoppingCart.getCartItem().contains(cartItem)) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "CartItem :" + cartItemId + " not included in shoppingCart");
		}
		
		for(Size findSize : listOfSizes) {
			if(!findSize.equals(size)) {
				throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "Size :" + size.getName()+ " not found for product");
			}
		}

		int newStock = (size.getStock() + cartItem.getQuantity());
		size.setStock(newStock);
		sizeRepository.save(size);
		
		
		shoppingCart.getCartItem().remove(cartItem);
		cartItemRepository.delete(cartItem);
		
		BigDecimal totalAmount =  updateCartTotal(shoppingCart.getId());
		
		shoppingCart.setTotal(totalAmount);
		shoppingCartRepository.save(shoppingCart);
	}

	

	@Override
	public ShoppingCartDTO getShoppingCartByUser(long userId) {
		ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
		
		if(shoppingCart.getCartItem().isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "ShoppingCart is empty");
		}
		
		return mapper.shoppingCartToShoppingCartDTO(shoppingCart);
	}


	@Override
	public BigDecimal updateCartTotal(UUID shoppingCartId) {		
		List<CartItem> listCartItems = cartItemRepository.findByShoppingCartId(shoppingCartId);
		
		BigDecimal total =BigDecimal.ZERO;
		for(CartItem carItem : listCartItems) {
			total = total.add(carItem.getSubtotal());
		}
		
		return total;
		
	}
}
