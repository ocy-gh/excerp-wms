package tech.excerp.wms.entity.po;

public class Shipping {
    private String order_id;

    public Shipping(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
