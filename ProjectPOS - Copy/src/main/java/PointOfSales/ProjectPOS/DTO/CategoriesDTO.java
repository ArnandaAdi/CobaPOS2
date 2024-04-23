package PointOfSales.ProjectPOS.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriesDTO {
    private Long id;

    @NotBlank(message = "Category name tidak boleh kosong")
    private String name;
}
