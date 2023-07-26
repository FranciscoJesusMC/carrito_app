package com.carrito.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrito.dto.SizeDTO;
import com.carrito.dto.StockDTO;
import com.carrito.entity.Product;
import com.carrito.entity.Size;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.SizeMapper;
import com.carrito.mapper.SizeMapperImpl;
import com.carrito.repository.ProductRepository;
import com.carrito.repository.SizeRepository;
import com.carrito.service.SizeService;

@Service
public class SizeServiceImpl implements SizeService {

	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SizeMapper mapper = new SizeMapperImpl();

	@Override
	public SizeDTO addSizeToProduct(UUID productId, SizeDTO sizeDTO) {
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
		
		Size size = mapper.sizeDTOtoSize(sizeDTO);
		
		size.setName(sizeDTO.getName().toUpperCase());
		size.setProduct(product);
		sizeRepository.save(size);
		
		product.getSize().add(size);
		
		productRepository.save(product);
		
		return mapper.sizeToSizeDTO(size);
		
	}

	@Override
	public void removeSizeToProduct(UUID productId, long sizeId) {
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
		
		Size size = sizeRepository.findById(sizeId).orElseThrow(()-> new ResourceNotFoundException("Size", "id", sizeId));
		
		List<Size> listSize = sizeRepository.findByProductId(productId);
		
		for( Size findSize : listSize) {
			if(findSize.equals(size)) {
				product.getSize().remove(size);
				productRepository.save(product);
			}else {
				throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "Size not found for : " + sizeId);
			}
		}
		
	}

	@Override
	public List<SizeDTO> getSizesToProduct(UUID productId) {
		List<Size> sizes =  sizeRepository.findByProductId(productId);
		if(sizes.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "sizes not found for product :" +productId);
		}
		
		return sizes.stream().map(size ->mapper.sizeToSizeDTO(size)).collect(Collectors.toList());
	}

	@Override
	public void updateStockToSize(UUID productId,long sizeId, StockDTO stockDTO) {
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
		
		Size size = sizeRepository.findById(sizeId).orElseThrow(()-> new ResourceNotFoundException("Size", "id", sizeId));
		
		List<Size> listSize = sizeRepository.findByProductId(product.getId());
		
		for(Size findSize : listSize) {
			if(!findSize.equals(size)){
				throw new ShoppingCartException(HttpStatus.BAD_REQUEST, "Size not found");
			}
		}
		
		size.setStock(stockDTO.getAmount());
		sizeRepository.save(size);
			
	}
}
