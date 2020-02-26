package EjemplosBasic;

public class Ejemplo_Bucles_1 {

    public static void main22(String[] args) {
        System.out.println("Empieza bucle WHILE:");
        int x = 1;
        if (x <= 10) {
            Object[] result = method1(x);
            x = (Integer) result[0];
        }
        System.out.println();
        if (x > 0) x++;
        if (x > 0) {
            Object[] result = method2(x);
            x = (Integer) result[0];
        }
    }

    void method1(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
    }

    void method2(int x) {
        {
            x++;
        }
    }
}
