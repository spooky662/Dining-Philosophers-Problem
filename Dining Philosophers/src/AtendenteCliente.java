import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class AtendenteCliente extends Thread {

    private final Socket socket;
    private final Loja loja;

    public AtendenteCliente(Socket socket, Loja loja) {
        this.socket = socket;
        this.loja = loja;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            String pedido = in.readLine();

            if (pedido != null && pedido.startsWith("COMPRAR")) {

                int clienteId = Integer.parseInt(pedido.split(":")[1]);

                Veiculo v = loja.venderParaCliente(clienteId);

                if (v != null) {
                    out.println("OK " + v.getId());
                } else {
                    out.println("SEM_ESTOQUE");
                }
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("Erro no atendimento do cliente.");
        }
    }
}