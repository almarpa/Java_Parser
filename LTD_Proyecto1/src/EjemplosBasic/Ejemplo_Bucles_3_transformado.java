package EjemplosBasic;

public class Ejemplo_Bucles_3 {

    public static void main(String[] args) {
        int x;
        System.out.println("Empieza bucle FOR:");
        {
            x = 1;
            if (x <= 10) {
                Object[] result = metodo_1(x);
                x = (Integer) result[0];
            }
        }
        System.out.println();
        System.out.println("Empieza bucle WHILE:");
        x = 1;
        if (x <= 10) {
            Object[] result = metodo_2(x);
            x = (Integer) result[0];
        }
        System.out.println();
        System.out.println("Empieza bucle DO WHILE:");
        x = 1;
        {
            {
                System.out.print(" " + x);
                x++;
            }
            if (x <= 10) {
                Object[] result = metodo_3(x);
                x = (Integer) result[0];
            }
        }
        System.out.println();
    }

    public static Object[] metodo_1(int x) {
        {
            System.out.print(" " + x);
        }
        x++;
        if (x <= 10) return metodo_1(x);
        return new Object[] { x };
    }

    public static Object[] metodo_2(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
        if (x <= 10) return metodo_2(x);
        return new Object[] { x };
    }

    public static Object[] metodo_3(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
        if (x <= 10) return metodo_3(x);
        return new Object[] { x };
    }
}
