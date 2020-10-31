package buzzardsView.calculator;

public class Light {

    private String name;
    private double price;
    private double watts;
    private double length;

    public double cost() {
        double cost = this.price * this.length;
        return cost;
    }

    public double wattage() {
        double wattage = this.watts * this.length;
        return wattage;
    }

    public Light() {

    }

    public Light(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Light(String name, double price, double watts) {
        this.name = name;
        this.price = price;
        this.watts = watts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWatts() {
        return watts;
    }

    public void setWatts(double watts) {
        this.watts = watts;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}


