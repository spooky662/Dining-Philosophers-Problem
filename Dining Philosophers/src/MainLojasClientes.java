public class MainLojasClientes {

    public static void main(String[] args) {

        Loja[] lojas = new Loja[3];

        lojas[0] = new Loja(1, "localhost", 5000, 6001);
        lojas[1] = new Loja(2, "localhost", 5000, 6002);
        lojas[2] = new Loja(3, "localhost", 5000, 6003);

        for (int i = 0; i < 3; i++) {
            lojas[i].start();
        }
    }
}