package EjemplosBasic;

public class Ejemplo_Bucles_1 {

    public static void main22(String[] args) {
        System.out.println("Empieza bucle WHILE:");
        int x = 1;
        while (x <= 10) {
            System.out.print(" " + x);
            x++;
        }
        System.out.println();
        if (x > 0) x++;
        while (x > 0) {
            x++;
        }
    }
}
