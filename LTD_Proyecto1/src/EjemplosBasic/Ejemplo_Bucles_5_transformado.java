package EjemplosBasic;

public class Ejemplo_Bucles_5 {

    public static void main(String[] args) {
        int x = 0;
        char y = '0';
        System.out.println("Empieza bucle FOR anidado a otro FOR:");
        {
            x = 1;
            if (x <= 10) {
                Object[] result = metodo_1(x, y);
                x = (Integer) result[0];
                y = (Character) result[1];
            }
        }
        System.out.println();
        System.out.println("Empieza bucle WHILE anidado a otro WHILE:");
        x = 1;
        if (x <= 10) {
            Object[] result = metodo_2(x, y);
            x = (Integer) result[0];
            y = (Character) result[1];
        }
        System.out.println();
        System.out.println("Empieza bucle FOR anidado a bucle DO WHILE:");
        x = 1;
        {
            {
                System.out.print(" " + x);
                {
                    y = 'a';
                    if (y <= 'c') {
                        Object[] result = metodo_6(y);
                        y = (Character) result[0];
                    }
                }
                x++;
            }
            if (x <= 10) {
                Object[] result = metodo_3(x, y);
                x = (Integer) result[0];
                y = (Character) result[1];
            }
        }
        System.out.println();
    }

    public static Object[] metodo_1(int x, char y) {
        {
            System.out.print(" " + x);
            {
                y = 'a';
                if (y <= 'c') {
                    Object[] result = metodo_4(y);
                    y = (Character) result[0];
                }
            }
        }
        x++;
        if (x <= 10) return metodo_1(x, y);
        return new Object[] { x, y };
    }

    public static Object[] metodo_2(int x, char y) {
        {
            System.out.print(" " + x);
            y = 'a';
            if (y <= 'c') {
                Object[] result = metodo_5(y);
                y = (Character) result[0];
            }
            x++;
        }
        if (x <= 10) return metodo_2(x, y);
        return new Object[] { x, y };
    }

    public static Object[] metodo_3(int x, char y) {
        {
            System.out.print(" " + x);
            {
                y = 'a';
                if (y <= 'c') {
                    Object[] result = metodo_6(y);
                    y = (Character) result[0];
                }
            }
            x++;
        }
        if (x <= 10) return metodo_3(x, y);
        return new Object[] { x, y };
    }

    public static Object[] metodo_4(char y) {
        {
            System.out.print(" " + y);
        }
        y++;
        if (y <= 'c') return metodo_4(y);
        return new Object[] { y };
    }

    public static Object[] metodo_5(char y) {
        {
            System.out.print(" " + y);
            y++;
        }
        if (y <= 'c') return metodo_5(y);
        return new Object[] { y };
    }

    public static Object[] metodo_6(char y) {
        {
            System.out.print(" " + y);
        }
        y++;
        if (y <= 'c') return metodo_6(y);
        return new Object[] { y };
    }
}
