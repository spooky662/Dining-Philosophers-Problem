import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorLoja {

    private int porta;
    private Loja loja;

    public ServidorLoja(int porta, Loja loja) {
        this.porta = porta;
        this.loja = loja;
    }

    public void iniciar() {
        try {
            ServerSocket serverSocket = new ServerSocket(porta);
            System.out.println("Loja ouvindo na porta " + porta);

            while (true) {
                Socket socket = serverSocket.accept();
                new AtendenteCliente(socket, loja).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}