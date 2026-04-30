package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import rado.alberto.org.variables.ProductCategory;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 120)
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(max = 3000)
    @Basic
    @Column(name = "description", nullable = false)
    private String description;

    @Basic
    @Column(name = "image")
    private String image;

    @NotNull
    @Basic
    @Column(name = "sku", nullable = false)
    private String sku;

    @NotNull
    @Size(max = 100)
    @Basic
    @Column(name = "price", nullable = false)
    private String price;

    @NotNull
    @Size(max = 80)
    @Basic
    @Column(name = "category", nullable = false)
    private ProductCategory category;

    @NotNull
    @Size(max = 10)
    @Basic
    @Column(name = "stock", nullable = false)
    private String stock;

    @Size(max = 4)
    @Basic
    @Column(name = "discount")
    private String discount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other)) return false;
        return id != 0 && id.equals(other.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id = ").append(id);
        sb.append("\nname = ").append(name);
        sb.append("\ndescription = ").append(description);
        if(this.image != null && !this.image.isEmpty() && !this.image.isBlank()){
            sb.append("\nimage = ").append(image);
        }
        sb.append("\nsku = ").append(sku);
        sb.append("\nprice = ").append(price);
        sb.append("\ncategory = ").append(category);
        sb.append("\nstock = ").append(stock);
        if(this.discount != null && !this.discount.isEmpty() && !this.discount.isBlank()){
            sb.append("\ndiscount = ").append(discount);
        }
        sb.append('}');
        return sb.toString();
    }
}