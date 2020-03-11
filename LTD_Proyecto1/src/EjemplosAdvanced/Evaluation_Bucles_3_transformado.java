package EjemplosAdvanced;

public class Evaluation_Bucles_3 {

    public static void main(String[] args) throws Exception {
        int x = 5;
        if (x > 0) {
            Object[] result = metodo_1(x);
            x = (Integer) result[0];
        }
    }

    public static Object[] metodo_1(int x) throws Exception {
        {
            System.out.println("1");
            if (x > 50) {
                x--;
                System.out.println("2");
            } else if (x > 0) {
                System.out.println("3");
                if (x > 100) {
                    x++;
                    System.out.println("4");
                } else {
                    x--;
                    System.out.println("5");
                    if (x > 1) {
                        Object[] result = metodo_2(x);
                        x = (Integer) result[0];
                    }
                    System.out.println("7");
                }
            }
        }
        if (x > 0) return metodo_1(x);
        return new Object[] { x };
    }

    public static Object[] metodo_2(int x) throws Exception {
        {
            x--;
            System.out.println("6");
            Exception e = new ArrayIndexOutOfBoundsException();
            throw e;
        }
    }
}
