import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Simula una torre de elementos apilables
 */
public class Tower {

    private int width;
    private int maxHeight;
    private ArrayList<StackingItem> items;
    private boolean visible;
    private boolean ok;

    /**
     * @param width ancho de la torre
     * @param maxHeight altura máxima permitida
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.items = new ArrayList<>();
        this.visible = true;
        this.ok = true;
    }
    
    /**
     * @return altura total de los elementos apilados
     */
    public int height() {
        int total = 0;
        for (StackingItem item : items) {
            total += item.getHeight();
        }
        return total;
    }
    
    /**
     * @return estado de la última operación
     */
    public boolean ok() {
        return ok;
    }

    /**
     * Agrega una taza si el color no está repetido y no se supera la altura máxima.
     * @param number número identificador de la taza
     * @param height altura de la taza
     * @param color color de la taza
     */
    public void pushCup(int number, int height, String color) {

        if (colorCupExists(color)) {
            ok = false;
            showMessage("Ya existe una taza con ese color");
            return;
        }
    
        StackingItem cup = new StackingItem("cup", number, height, color);
    
        if (height() + cup.getHeight() <= maxHeight) {
            items.add(cup);
            ok = true;
        } else {
            ok = false;
            showMessage("No se puede agregar la taza, supera la altura máxima");
        }
    
        redraw();
    }

    
    /**
     * Agrega una tapa si existe una taza del mismo color
     * y no se supera la altura máxima.
     * @param number número identificador de la tapa
     * @param color color de la tapa
     */
    public void pushLid(int number, String color) {

        if (!colorCupExists(color)) {
            ok = false;
            showMessage("No existe una taza con ese color para colocar la tapa");
            return;
        }
    
        StackingItem lid = new StackingItem("lid", number, 1, color);
    
        if (height() + lid.getHeight() <= maxHeight) {
            items.add(lid);
            ok = true;
        } else {
            ok = false;
            showMessage("No se puede agregar la tapa, supera la altura máxima");
        }
    
        redraw();
    }

            
    
    /**
     * @return matriz con tipo y número de los elementos apilados
     */
    public String[][] stackingItems() {

        String[][] rta = new String[items.size()][2];

        for (int i = 0; i < items.size(); i++) {
            rta[i][0] = items.get(i).getType();
            rta[i][1] = String.valueOf(items.get(i).getNumber());
        }

        return rta;
    }

    /**
     * Elimina la última taza agregada junto con su tapa si tiene
     */
    public void popCup() {

        for (int i = items.size() - 1; i >= 0; i--) {

            if (items.get(i).getType().equals("cup")) {

                if (i + 1 < items.size() &&
                    items.get(i + 1).getType().equals("lid") &&
                    items.get(i + 1).getColor().equals(items.get(i).getColor())) {

                    items.get(i + 1).makeInvisible();
                    items.remove(i + 1);
                }

                items.get(i).makeInvisible();
                items.remove(i);

                ok = true;
                redraw();
                return;
            }
        }

        ok = false;
        showMessage("No hay taza para eliminar");
    }

    /**
     * Elimina la última tapa agregada
     */
    public void popLid() {

        for (int i = items.size() - 1; i >= 0; i--) {

            if (items.get(i).getType().equals("lid")) {

                items.get(i).makeInvisible();
                items.remove(i);

                ok = true;
                redraw();
                return;
            }
        }

        ok = false;
        showMessage("No hay tapa para eliminar");
    }
    
    /**
     * Elimina la taza con el número indicado junto con su tapa si tiene
     * @param nuúmero de la taza que se quiere eliminar
     */
    public void removeCup(int number) {

        for (int i = 0; i < items.size(); i++) {

            if (items.get(i).getType().equals("cup") &&
                items.get(i).getNumber() == number) {

                if (i + 1 < items.size() &&
                    items.get(i + 1).getType().equals("lid") &&
                    items.get(i + 1).getColor().equals(items.get(i).getColor())) {

                    items.get(i + 1).makeInvisible();
                    items.remove(i + 1);
                }

                items.get(i).makeInvisible();
                items.remove(i);

                ok = true;
                redraw();
                return;
            }
        }

        ok = false;
        showMessage("No existe la taza con ese número");
    }

    /**
     * Elimina la tapa con el número indicado
     * @ param numero de la tapa que se quiere eliminar
     */
    public void removeLid(int number) {

        for (int i = 0; i < items.size(); i++) {

            if (items.get(i).getType().equals("lid") &&
                items.get(i).getNumber() == number) {

                items.get(i).makeInvisible();
                items.remove(i);

                ok = true;
                redraw();
                return;
            }
        }

        ok = false;
        showMessage("No existe la tapa con ese número");
    }
    
    
    /**
     * Invierte el orden de la torre
     */
    public void reverseTower() {

        ArrayList<StackingItem> nueva = new ArrayList<>();

        int i = items.size() - 1;

        while (i >= 0) {

            StackingItem actual = items.get(i);

            if (actual.getType().equals("lid") &&
                i - 1 >= 0 &&
                items.get(i - 1).getType().equals("cup") &&
                items.get(i - 1).getColor().equals(actual.getColor())) {

                nueva.add(items.get(i - 1));
                nueva.add(actual);
                i -= 2;
            } else {
                nueva.add(actual);
                i--;
            }
        }

        items = nueva;
        ok = true;
        redraw();
    }

    /**
     * Ordena las tazas de mayor a menor junto con sus tapas si tienen
     */
    public void orderTower() {

        ArrayList<ArrayList<StackingItem>> bloques = new ArrayList<>();
        int i = 0;

        while (i < items.size()) {

            ArrayList<StackingItem> bloque = new ArrayList<>();
            StackingItem actual = items.get(i);
            bloque.add(actual);

            if (i + 1 < items.size() &&
                actual.getType().equals("cup") &&
                items.get(i + 1).getType().equals("lid") &&
                actual.getColor().equals(items.get(i + 1).getColor())) {

                bloque.add(items.get(i + 1));
                i += 2;
            } else {
                i++;
            }

            bloques.add(bloque);
        }

        for (int a = 0; a < bloques.size() - 1; a++) {
            for (int b = 0; b < bloques.size() - 1 - a; b++) {

                if (bloques.get(b).get(0).getNumber() <
                    bloques.get(b + 1).get(0).getNumber()) {

                    ArrayList<StackingItem> temp = bloques.get(b);
                    bloques.set(b, bloques.get(b + 1));
                    bloques.set(b + 1, temp);
                }
            }
        }

        ArrayList<StackingItem> nueva = new ArrayList<>();
        for (ArrayList<StackingItem> bloque : bloques) {
            nueva.addAll(bloque);
        }

        items = nueva;
        ok = true;
        redraw();
    }

    /**
     * @return arreglo con los números de las tazas tapadas
     */
    public int[] liddedCups() {

        ArrayList<Integer> lista = new ArrayList<>();

        for (int i = 0; i < items.size() - 1; i++) {

            StackingItem actual = items.get(i);
            StackingItem siguiente = items.get(i + 1);

            if (actual.getType().equals("cup") &&
                siguiente.getType().equals("lid") &&
                actual.getColor().equals(siguiente.getColor())) {

                lista.add(actual.getNumber());
            }
        }

        int[] rta = new int[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            rta[i] = lista.get(i);
        }

        return rta;
    }
    
    /**
     * Redibuja la torre completa
     */
    private void redraw() {

        if (!visible) return;

        int alturaActual = 0;
        int base = 250;

        for (StackingItem item : items) {

            int x = 150;
            int y = base - alturaActual - item.getHeight();

            item.moveTo(x, y);
            item.makeVisible();

            alturaActual += item.getHeight();
        }
    }

    /**
     * Hace visible la torre
     */
    public void makeVisible() {
        visible = true;
        redraw();
    }

    /**
     * Hace invisible la torre
     */
    public void makeInvisible() {
        visible = false;
        for (StackingItem item : items) {
            item.makeInvisible();
        }
    }

    /**
     * Elimina todos los elementos de la torre
     */
    public void exit() {
        for (StackingItem item : items) {
            item.makeInvisible();
        }
        items.clear();
        ok = true;
    }
    
    /**
     * Muestra un mensaje si la torre está visible
     * @param message mensaje que se quiere mostrar 
     */
    private void showMessage(String message) {
        if (visible) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
    /**
     * Verifica si ya existe una taza con el mismo color
     * @param color de la taza que se quiere verificar
     */
    private boolean colorCupExists(String color) {
    
        for (StackingItem item : items) {
    
            if (item.getType().equals("cup") &&
                item.getColor().equals(color)) {
    
                return true;
            }
        }
    
        return false;
    }
}
