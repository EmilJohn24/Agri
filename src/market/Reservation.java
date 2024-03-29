package market;

import com.sun.istack.internal.NotNull;
import javafx.scene.image.Image;
import management.Account;

import javax.swing.text.html.ImageView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {
    private Account seller;
    private Account buyer;
    private LocalDate date;
    private Product product;
    private Integer amount;
    private Image image;
    public Reservation(@NotNull Account seller, @NotNull Account buyer, @NotNull LocalDate date,@NotNull Product product,@NotNull Integer amount) {
        this.seller = seller;
        this.buyer = buyer;
        this.date = date;
        this.product = product;
        this.amount = amount;

    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Account getSeller() {
        return seller;
    }

    public Account getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
