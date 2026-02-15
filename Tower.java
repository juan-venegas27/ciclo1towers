import java.util.ArrayList;

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
        for (int i = 0; i < items.size(); i++) {
            total +=items.get(i).getHeight();
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
     * @param number número de la taza
     * @param height altura de la taza
     */
    public void pushCup(int number, int height) {
    
        StackingItem cup = new StackingItem("cup", number, height);
    
        if (height() + cup.getHeight() <= maxHeight) {
            items.add(cup);
            ok = true;
        } else {
            ok = false;
        }
    }
    
    /**
     * @param number número de la tapa
     */
    public void pushLid(int number) {
    
        StackingItem lid = new StackingItem("lid", number, 1);
    
        if (height() + lid.getHeight() <= maxHeight) {
            items.add(lid);
            ok = true;
        } else {
            ok = false;
        }
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
     * Elimina la última taza agregada
     */
    public void popCup() {
    
        for (int i = items.size() - 1; i >= 0; i--) {
    
            if (items.get(i).getType().equals("cup")) {
                items.remove(i);
                ok = true;
                return;
            }
        }
    
        ok = false;
    }
    
    /**
     * Elimina la última tapa agregada
     */
    public void popLid() {
    
        for (int i = items.size() - 1; i >= 0; i--) {
    
            if (items.get(i).getType().equals("lid")) {
                items.remove(i);
                ok = true;
                return;
            }
        }
    
        ok = false;
    }
    
    /**
     * @param number número de la taza a eliminar
     */
    public void removeCup(int number) {
    
        for (int i = 0; i < items.size(); i++) {
    
            if (items.get(i).getType().equals("cup") &&
                items.get(i).getNumber() == number) {
    
                items.remove(i);
                ok = true;
                return;
            }
        }
    
        ok = false;
    }

        /**
     * @param number número de la tapa a eliminar
     */
    public void removeLid(int number) {
    
        for (int i = 0; i < items.size(); i++) {
    
            if (items.get(i).getType().equals("lid") &&
                items.get(i).getNumber() == number) {
    
                items.remove(i);
                ok = true;
                return;
            }
        }
    
        ok = false;
    }
    
    /**
     * Invierte el orden de los elementos en la torre
     */
    public void reverseTower() {
    
        ArrayList<StackingItem> nueva = new ArrayList<>();
    
        for (int i = items.size() - 1; i >= 0; i--) {
            nueva.add(items.get(i));
        }
    
        items = nueva;
        ok = true;
    }

    /**
     * Ordena las tazas de mayor a menor según su número
     */
    public void orderTower() {
    
        for (int i = 0; i < items.size() - 1; i++) {
    
            for (int j = 0; j < items.size() - 1 - i; j++) {
    
                if (items.get(j).getNumber() < items.get(j + 1).getNumber()) {
    
                    StackingItem temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
    
        ok = true;
    }
    
    /**
     * Hace visible la torre
     */
    public void makeVisible() {
        visible = true;
    }


    /**
     * Hace invisible la torre
     */
    public void makeInvisible() {
        visible = false;
    }


    /**
     * Elimina todos los elementos de la torre
     */
    public void exit() {
        items.clear();
        ok = true;
    }

}