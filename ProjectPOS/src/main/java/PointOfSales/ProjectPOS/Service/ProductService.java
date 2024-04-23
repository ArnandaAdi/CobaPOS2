package PointOfSales.ProjectPOS.Service;

import PointOfSales.ProjectPOS.DTO.ProductDTO;
import PointOfSales.ProjectPOS.Entity.Categories;
import PointOfSales.ProjectPOS.Entity.Products;
import PointOfSales.ProjectPOS.Repository.CategoryRepository;
import PointOfSales.ProjectPOS.Repository.ProductRepository;
import PointOfSales.ProjectPOS.Utils.CategoryNotFoundException;
import PointOfSales.ProjectPOS.Utils.ResourceNotFoundException;
import PointOfSales.ProjectPOS.Utils.ResponseMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    public List<ProductDTO> getAllProducts() {
        List<Products> products = productRepository.findAll();
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> listAllProductsSorted(String sortBy, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        if (StringUtils.isEmpty(sortBy) || !isValidSortBy(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy parameter");
        }
        List<Products> products = productRepository.findAll(Sort.by(direction, sortBy));
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private boolean isValidSortBy(String sortBy) {
        List<String> validSortByAttributes = List.of("id", "title", "price", "category");
        return validSortByAttributes.contains(sortBy.toLowerCase());
    }
    public List<ProductDTO> searchProductsByTitle(String title) {
        List<Products> products = productRepository.findByTitleContainingIgnoreCase(title);
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> listProductsByCategory(Long categoryId) {
        if (categoryId == null || categoryId <= 0 || !String.valueOf(categoryId).matches("\\d+")) {
            throw new IllegalArgumentException("Category ID harus disertakan dan harus berupa angka positif");
        }
        List<Products> products = productRepository.findByCategoryId(categoryId);

        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ResponseMessage addProduct(ProductDTO productDTO) {
        Optional<Categories> optionalCategory = categoryRepository.findById(productDTO.getCategory_id());

        if (optionalCategory.isPresent()) {
            Categories category = optionalCategory.get();

            Products product = new Products();
            product.setTitle(productDTO.getTitle());
            product.setImage(productDTO.getImage());
            product.setPrice(productDTO.getPrice());
            product.setCategory(category);
            productRepository.save(product);

            return new ResponseMessage("ok", "success");
        } else {
            return new ResponseMessage("error", "Category not found");
        }
    }

    public ResponseMessage updateProduct(Long productId, ProductDTO updatedProductDTO) {
        // Temukan produk yang ingin diperbarui berdasarkan ID
        Optional<Products> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            // Jika produk ditemukan, dapatkan objek produk dari Optional
            Products product = optionalProduct.get();

            // Perbarui atribut produk dengan nilai yang diberikan dari updatedProductDTO
            product.setTitle(updatedProductDTO.getTitle());
            product.setImage(updatedProductDTO.getImage());
            product.setPrice(updatedProductDTO.getPrice());

            // Temukan kategori yang sesuai dengan ID yang diberikan dari updatedProductDTO
            Optional<Categories> optionalCategory = categoryRepository.findById(updatedProductDTO.getCategory_id());

            if (optionalCategory.isPresent()) {
                // Jika kategori ditemukan, atur kategori produk
                Categories category = optionalCategory.get();
                product.setCategory(category);

                // Simpan perubahan ke dalam repository
                productRepository.save(product);

                // Mengembalikan pesan sukses
                return new ResponseMessage("ok", "Product updated successfully");
            } else {
                // Jika kategori tidak ditemukan, kembalikan pesan kesalahan
                return new ResponseMessage("error", "Category not found");
            }
        } else {
            // Jika produk tidak ditemukan, kembalikan pesan kesalahan
            return new ResponseMessage("error", "Product not found");
        }
    }




    public boolean deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID produk harus disertakan dan harus berupa angka positif");
        }

        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public ProductDTO getDetailProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID produk harus disertakan dan harus berupa angka positif");
        }

        Optional<Products> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setTitle(product.getTitle());
            productDTO.setImage(product.getImage());
            productDTO.setPrice(product.getPrice());
            if (product.getCategory() != null) {
                productDTO.setCategory_id(product.getCategory().getId());
                productDTO.setCategory_name(product.getCategory().getName());
            }
            return productDTO;
        } else {
            return null;
        }
    }


    public ProductDTO toDTO(Products product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setImage(product.getImage());
        dto.setPrice(product.getPrice());

        Categories category = product.getCategory();
        if (category != null) {
            dto.setCategory_id(category.getId());
            dto.setCategory_name(category.getName());
        }
        return dto;
    }
}



