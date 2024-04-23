package PointOfSales.ProjectPOS.Service;

import PointOfSales.ProjectPOS.DTO.ProductDTO;
import PointOfSales.ProjectPOS.Entity.Categories;
import PointOfSales.ProjectPOS.Entity.Products;
import PointOfSales.ProjectPOS.Exception.GlobalExceptionHandler;
import PointOfSales.ProjectPOS.Repository.CategoryRepository;
import PointOfSales.ProjectPOS.Repository.ProductRepository;
import PointOfSales.ProjectPOS.Utils.ResponseMessage;
import PointOfSales.ProjectPOS.Utils.SpecialCharacterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Products> listAllProductsSorted(String sortBy, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        return productRepository.findAll(Sort.by(direction, sortBy));
    }


//    }
//        if (StringUtils.isEmpty(sortBy) || !isValidSortBy(sortBy)) {
//            throw new IllegalArgumentException("Invalid sortBy parameter");
//        }
//        return productRepository.findAll(Sort.by(direction, sortBy));
//    }
//
//    private boolean isValidSortBy(String sortBy) {
//        List<String> validSortByAttributes = List.of("id", "title", "price", "category");
//        return validSortByAttributes.contains(sortBy.toLowerCase());
//    }
    public List<Products> searchProductsByTitle(String title) {
        return productRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Products> listProductsByCategory(Long categoryId) {
        if (categoryId == null || categoryId <= 0 || !String.valueOf(categoryId).matches("\\d+")) {
            throw new IllegalArgumentException("Category ID harus disertakan dan harus berupa angka positif");
        }

        return productRepository.findByCategoryId(categoryId);
    }

    public ResponseMessage addProduct(ProductDTO productDTO) {
        Optional<Categories> optionalCategory = categoryRepository.findById(productDTO.getCategory_id());

        if (optionalCategory.isPresent()) {
            Categories category = optionalCategory.get();
            Boolean valid = GlobalExceptionHandler.isValidInput(productDTO.getTitle());
            if(!valid){
                throw new SpecialCharacterException("Tidak boleh berisi special character");
            }

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
        Optional<Products> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();

            product.setTitle(updatedProductDTO.getTitle());
            product.setImage(updatedProductDTO.getImage());
            product.setPrice(updatedProductDTO.getPrice());

            Optional<Categories> optionalCategory = categoryRepository.findById(updatedProductDTO.getCategory_id());

            if (optionalCategory.isPresent()) {
                Categories category = optionalCategory.get();
                product.setCategory(category);

                productRepository.save(product);

                return new ResponseMessage("ok", "succes");
            } else {
                return new ResponseMessage("error", "Category tidak ditemukan");
            }
        } else {
            return new ResponseMessage("error", "Product tidak ditemukan");
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
}



