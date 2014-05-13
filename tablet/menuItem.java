/*
 * menuItem class
 * contains member variables corresponding to columns of the menu_items table
 */

import java.math.BigDecimal;

public class menuItem {

    int menu_item_id;
    int subcategory_id;
    String name;
    String description;
    String picture_path;
    BigDecimal price;

    // Constructor with ID
    public menuItem (int menu_item_id, int subcategory_id, String name,
                     String description, String picture_path, BigDecimal price) {
        this.menu_item_id = menu_item_id;
        this.subcategory_id = subcategory_id;
        this.name = name;
        this.description = description;
        this.picture_path = picture_path;
        this.price = price;
    }

    // Constructor without ID
    public menuItem (int subcategory_id, String name, String description,
                     String picture_path, BigDecimal price) {
        this.subcategory_id = subcategory_id;
        this.name = name;
        this.description = description;
        this.picture_path = picture_path;
        this.price = price;
    }

    // TODO: Getter and Setter methods?
}
