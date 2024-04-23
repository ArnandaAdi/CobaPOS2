package PointOfSales.ProjectPOS.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO {
    @NotBlank(message = "Judul tidak boleh kosong")
    @Size(max = 100, min = 1, message = "title tidak boleh lebih dari 100 karakter")
    private String title;

    @NotBlank(message = "URL gambar tidak boleh kosong")
    private String image;

    @NotNull(message = "Harga tidak boleh kosong")
    @Positive(message = "Harga harus lebih besar dari 0")
    private Integer price;

    @NotNull(message = "ID kategori tidak boleh kosong")
    private Long category_id;

    private String category_name;

    private Long id;
}