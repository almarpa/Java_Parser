package EjemplosBasic;

public class Ejemplo_Bucles_1 {

    public static void main22(String[] args) {
        System.out.println("Empieza bucle WHILE:");
        int x = 1;
        if (x <= 10) {
            Object[] result = metodo_1(x);
            x = (Integer) result[0];
        }
        System.out.println();
        if (x > 0) x++;
        if (x > 0) {
            Object[] result = metodo_2(x);
            x = (Integer) result[0];
        }
    }

    public static Object[] metodo_1(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
        if (x <= 10) {
            return metodo_1(x);
        }
        return new Object[] { x };
    }

    public static Object[] metodo_2(int x) {
        {
            x++;
        }
        if (x > 0) {
            return metodo_2(x);
        }
        return new Object[] { x };
    }
}
