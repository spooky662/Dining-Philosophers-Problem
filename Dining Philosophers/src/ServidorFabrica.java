import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServidorFabrica {

    private final int porta;
    private final Fabrica fabrica;

    public ServidorFabrica(int porta, Fabrica fabrica) {
        this.porta = porta;
        this.fabrica = fabrica;
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor da fabrica iniciado na porta " + porta);

            while (true) {
                Socket socketLoja = serverSocket.accept();
                System.out.println("Loja conectada: " + socketLoja.getInetAddress());

                AtendenteLoja atendente = new AtendenteLoja(socketLoja, fabrica);
                atendente.start();
            }

        } catch (IOException e) {
            System.out.println("Erro no servidor da fabrica: " + e.getMessage());
        }
    }
}