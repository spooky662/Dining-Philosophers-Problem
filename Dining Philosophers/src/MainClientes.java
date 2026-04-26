public class MainClientes {

    public static void main(String[] args) {

        int[] portas = {6001, 6002, 6003};

        for (int i = 0; i < 20; i++) {
            new ClienteRemoto(i, portas).start();
        }
    }
}