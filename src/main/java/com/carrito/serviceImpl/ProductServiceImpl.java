package com.carrito.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import com.carrito.dto.ProductDTO;
import com.carrito.dto.ProductResponseDTO;
import com.carrito.entity.Brand;
import com.carrito.entity.Product;
import com.carrito.exceptions.ResourceNotFoundException;
import com.carrito.exceptions.ShoppingCartException;
import com.carrito.mapper.ProductMapper;
import com.carrito.mapper.ProductMapperImpl;
import com.carrito.mapper.ProductReponseMapper;
import com.carrito.mapper.ProductReponseMapperImpl;
import com.carrito.repository.BrandRepository;
import com.carrito.repository.ProductRepository;
import com.carrito.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
		
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ProductMapper mapper = new ProductMapperImpl();
	
	@Autowired
	private ProductReponseMapper mapperResponse = new ProductReponseMapperImpl();

	@Override
	public List<ProductResponseDTO> listAllProducts() {
		List<Product> products = productRepository.findAll();
		
		if(products.isEmpty()) {
			throw new ShoppingCartException(HttpStatus.NO_CONTENT, "List of products is empty");
		}
		
		return products.stream().map(product -> mapperResponse.productToProductResponseDTO(product)).collect(Collectors.toList());
	
	}

	@Override
	public ProductDTO createProduct(long brandId,ProductDTO productDTO) {
		Product product = mapper.productDTOtoProduct(productDTO);
		
		Brand brand = brandRepository.findById(brandId).orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandId));
	
		product.setBrand(brand);

		Product newProduct = productRepository.save(product);
		
		ProductDTO saveProduct = mapper.producToProductoDTO(newProduct);
		
		return saveProduct;
		
	}

	@Override
	public ProductDTO getProductById(UUID productId) {
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
		
		return mapper.producToProductoDTO(product);
	}

	@Override
	public ProductDTO updateProduct(UUID productId, ProductDTO productDTO) {
		
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
		
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		
		Product updateProduct = productRepository.save(product);
		
		ProductDTO saveProduct = mapper.producToProductoDTO(updateProduct);
		

		return saveProduct;
	}

	@Override
	public void deleteProduct(UUID productId) {
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
		productRepository.delete(product);
		
	}

	@Override
	public Page<ProductResponseDTO> paginationByCategoryAndBrandAndSize(String category, String brand, String size,
			Pageable pageable) {
		
		if(category !=null && !category.isEmpty() && brand !=null && !brand.isEmpty() && size !=null && !size.isEmpty()) {
			
			Page<Product> products = productRepository.findByCategoryNameContainingAndBrandNameContainingAndSizeNameContaining(category, brand, size, pageable);
			
			return products.map(product -> mapperResponse.productToProductResponseDTO(product));
			
		}else if(category !=null && !category.isEmpty()) {
			Page<Product> products = productRepository.findByCategoryNameContaining(category, pageable);
			return products.map(product -> mapperResponse.productToProductResponseDTO(product));

		}else if(brand !=null && !brand.isEmpty()) {
			Page<Product> products = productRepository.findByBrandNameContaining(brand, pageable);
			return products.map(product -> mapperResponse.productToProductResponseDTO(product));
			
		}else if(size !=null && !size.isEmpty()) {
			Page<Product> products = productRepository.findBySizeNameContaining(size, pageable);
			return products.map(product -> mapperResponse.productToProductResponseDTO(product));
		}
		
		Page<Product> products = productRepository.findAll(pageable);
		
		return products.map(product -> mapperResponse.productToProductResponseDTO(product));
	}

}
