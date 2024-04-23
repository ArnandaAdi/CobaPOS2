package PointOfSales.ProjectPOS.Controller;

import PointOfSales.ProjectPOS.DTO.ProductDTO;
import PointOfSales.ProjectPOS.Entity.Products;
import PointOfSales.ProjectPOS.Service.ProductService;
import PointOfSales.ProjectPOS.Utils.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pos/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/listproduct")
    public List<Products> listAllProducts(
            @RequestParam(required = false) Long category_id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "id") String sort_by,
            @RequestParam(required = false, defaultValue = "asc") String sort_order) {

        List<Products> productList;
        if (category_id != null) {
            return productService.listProductsByCategory(category_id);
        } else if (title != null && !title.isEmpty()) {
            return productService.searchProductsByTitle(title);
        } else if (sort_by != null) {
            return productService.listAllProductsSorted(sort_by, sort_order);
        } else {
            return productService.getAllProducts();
        }

//        try {
//
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(Collections.emptyList());
//        }

//        return ResponseEntity.ok().body(productList);
    }


    @PostMapping("/addproduct")
    public ResponseEntity<ResponseMessage> addproductk(@Valid @RequestBody ProductDTO productDTO) {
        ResponseMessage responseMessage = productService.addProduct(productDTO);

        if (responseMessage.getStatus().equals("ok")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }



    @PutMapping("/updateproduct/{id}")
    public ResponseEntity<ResponseMessage> updateProduct(@PathVariable ("id") Long productId, @Valid @RequestBody ProductDTO updatedProductDTO) {
        ResponseMessage responseMessage = productService.updateProduct(productId, updatedProductDTO);

        if (responseMessage.getStatus().equals("ok")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }

    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                return ResponseEntity.ok().body(new ResponseMessage("ok", "success"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("error", "Produk tidak ditemukan"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage("error", e.getMessage()));
        }
    }

    @GetMapping("/detailproduct/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable Long id) {
        try {
            ProductDTO productDTO = productService.getDetailProduct(id);
            if (productDTO != null) {
                return ResponseEntity.ok().body(productDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage("error", e.getMessage()));
        }
    }
}
