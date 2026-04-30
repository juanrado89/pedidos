package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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
    @Size(min = 0,max = 120)
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 0,max = 3000)
    @Basic
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Basic
    @Column(name = "image", nullable = true)
    private String image;

    @NotNull
    @Basic
    @Column(name = "sku", nullable = false)
    private String sku;

    @NotNull
    @Size(min = 0,max = 100)
    @Basic
    @Column(name = "price", nullable = false)
    private String price;

    @NotNull
    @Size(min = 0,max = 80)
    @Basic
    @Column(name = "category", nullable = true)
    private String category;

    @NotNull
    @Size(min = 0,max = 10)
    @Basic
    @Column(name = "stock", nullable = true)
    private String stock;

    @NotNull
    @Size(min = 0,max = 4)
    @Basic
    @Column(name = "discount", nullable = true)
    private String discount;

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
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", sku='").append(sku).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", stock='").append(stock).append('\'');
        sb.append(", discount='").append(discount).append('\'');
        sb.append('}');
        return sb.toString();
    }
}