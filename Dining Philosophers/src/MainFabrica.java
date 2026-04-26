public class MainFabrica {

    public static void main(String[] args) {
        Fabrica fabrica = new Fabrica();
        fabrica.iniciarProducao();

        ServidorFabrica servidor = new ServidorFabrica(5000, fabrica);
        servidor.iniciar();
    }
}