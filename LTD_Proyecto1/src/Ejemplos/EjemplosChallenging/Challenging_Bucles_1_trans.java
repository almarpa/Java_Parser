package Ejemplos.EjemplosChallenging;

public class Challenging_Bucles_1_trans {

    public static void main(String[] args) {
        int resultado = sumatorio(1);
        System.out.println(resultado);
    }

    public static int sumatorio(int x) {
        System.out.println("Empieza bucle WHILE:");
        if (x <= 10) {
            Object[] result = metodo_1(x);
            x = (Integer) result[0];
        }
        return x;
    }

    public static Object[] metodo_1(int x) {
        {
            x++;
            return x;
        }
        if (x <= 10) return metodo_1(x);
        return new Object[] { x };
    }
}
