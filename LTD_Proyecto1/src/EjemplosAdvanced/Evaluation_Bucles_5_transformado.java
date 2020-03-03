package EjemplosAdvanced;

public class Evaluation_Bucles_5 {

    public static void main(String[] args) {
        int x = 10;
        if (x > 1) {
            System.out.println("Empieza bucle WHILE:");
            if (x > 2) {
                Object[] result = metodo_1(x);
                x = (Integer) result[0];
            }
        }
    }

    public static Object[] metodo_1(int x) {
        {
            System.out.println("Empieza bucle WHILE:");
            if (x > 3) {
                Object[] result = metodo_2(x);
                x = (Integer) result[0];
            }
            x--;
        }
        if (x > 2) {
            return metodo_1(x);
        }
        return new Object[] { x };
    }

    public static Object[] metodo_2(int x) {
        {
            if (x > 4) {
                if (x > 100) {
                    Object[] result = metodo_3(x);
                    x = (Integer) result[0];
                }
            }
            x--;
            System.out.print(" x: " + x);
        }
        if (x > 3) {
            return metodo_2(x);
        }
        return new Object[] { x };
    }

    public static Object[] metodo_3(int x) {
        {
        }
        if (x > 100) {
            return metodo_3(x);
        }
        return new Object[] { x };
    }
}
