/**
 * Representa un elemento que puede apilarse en la torre
 */
public class StackingItem {

    private String type;
    private int number;
    private int height;

    /**
     * @param type tipo del elemento
     * @param number número del elemento
     * @param height altura del elemento
     */
    public StackingItem(String type, int number, int height) {
        this.type = type;
        this.number = number;
        this.height = height;
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
}
    