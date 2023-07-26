package com.carrito.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@Type(type ="uuid-char")
	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;

	@OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
	private List<Size> size = new ArrayList<>();
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private Set<ProductImage> images = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "product_category",joinColumns = @JoinColumn(name ="product_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="category_id",referencedColumnName = "id"))
	private Set<Category> category = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	

}
