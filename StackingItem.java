/**
 * Representa un elemento que puede apilarse en la torre
 */
public class StackingItem {

    private String type;
    private int number;
    private int height;
    private String color;
    private Rectangle shape;

    private int currentX;
    private int currentY;

    /**
     * @param type tipo del elemento
     * @param number número del elemento
     * @param height altura del elemento
     * @param color color del elemento
     */
    public StackingItem(String type, int number, int height, String color) {

        this.type = type;
        this.number = number;
        this.height = height;
        this.color = color;

        shape = new Rectangle();
        shape.changeSize(height, 40);
        shape.changeColor(color);

        currentX = 90;   
        currentY = 10;
    }

    /**
     * @return tipo del elemento
     */
    public String getType() {
        return type;
    }

    /**
     * @return número del elemento
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return altura del elemento
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return color del elemento
     */
    public String getColor() {
        return color;
    }

    /**
     * @return figura gráfica del elemento
     */
    public Rectangle getShape() {
        return shape;
    }

    /**
     * Mueve el elemento a una posición dada
     */
    public void moveTo(int newX, int newY) {

        shape.moveHorizontal(newX - currentX);
        shape.moveVertical(newY - currentY);

        currentX = newX;
        currentY = newY;
    }

    /**
     * Hace visible el elemento
     */
    public void makeVisible() {
        shape.makeVisible();
    }

    /**
     * Hace invisible el elemento
     */
    public void makeInvisible() {
        shape.makeInvisible();
    }
}
