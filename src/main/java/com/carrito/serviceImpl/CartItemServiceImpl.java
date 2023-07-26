package com.carrito.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.carrito.dto.CartItemDTO;
import com.carrito.entity.CartItem;
import com.carrito.entity.Product;
import com.carrito.entity.Size;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.CartItemMapper;
import com.carrito.mapper.CartItemMapperImpl;
import com.carrito.repository.CartItemRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.repository.SizeRepository;
import com.carrito.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private ProductRepository productRepository;
		
	@Autowired
	private CartItemMapper mapper = new CartItemMapperImpl();


	@Override
	public CartItemDTO updateCartItem(UUID cartItemId,long sizeId, CartItemDTO cartItemDTO) {
		
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("CartItem", "id", cartItemId));
		
		Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow(()-> new ResourceNotFoundException("Product", "id", cartItemDTO.getProduct().getId()));
		
		Size size = sizeRepository.findById(sizeId).orElseThrow(()-> new ResourceNotFoundException("Size", "id", sizeId));
		
		List<Size> listOfSizes = sizeRepository.findByProductId(product.getId());
		
		for(Size findSize : listOfSizes) {
			if(!findSize.equals(size)) {
				throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "Size :" +size.getName()+ " not found for product");
			}
		}
		
		if(cartItemDTO.getQuantity() <= size.getStock()) {

			int newStock = (size.getStock() - cartItemDTO.getQuantity());
			
			size.setStock(newStock);
			sizeRepository.save(size);
			
			BigDecimal newTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
			
			cartItem.setQuantity(cartItemDTO.getQuantity());
			cartItem.setPrice(product.getPrice());
			cartItem.setSubtotal(newTotal);
			
			CartItem updateCartItem = cartItemRepository.save(cartItem);
			
			CartItemDTO saveCartItem = mapper.cartItemToCartItemDTO(updateCartItem);
			
			return saveCartItem;
			
		}else {

			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "not enough stock");
			
		}
		
	}

	
	
	
	


}
