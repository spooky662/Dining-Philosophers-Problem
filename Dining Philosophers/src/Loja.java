import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Loja extends Thread {

    private final int id;
    private final BufferCircular esteiraLoja;

    private final String hostFabrica;
    private final int portaFabrica;
    private final int portaClientes;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Loja(int id, String hostFabrica, int portaFabrica, int portaClientes) {
        this.id = id;
        this.hostFabrica = hostFabrica;
        this.portaFabrica = portaFabrica;
        this.portaClientes = portaClientes;
        this.esteiraLoja = new BufferCircular(40);
    }

    public int getIdLoja() {
        return id;
    }

    private void conectar() throws IOException {
        socket = new Socket(hostFabrica, portaFabrica);

        // ⚠️ ordem importante pra evitar travamento
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Loja " + id + " conectada à fábrica.");
    }

    public Veiculo venderParaCliente(int clienteId) throws InterruptedException {
        Veiculo v = esteiraLoja.consumir();
        System.out.println(v.logVendaCliente(clienteId));
        return v;
    }

    private void iniciarServidorClientes() {
        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(portaClientes);
                System.out.println("Loja " + id + " esperando clientes na porta " + portaClientes);

                while (true) {
                    Socket cliente = server.accept();
                    new AtendenteCliente(cliente, this).start();
                }

            } catch (IOException e) {
                System.out.println("Erro no servidor da loja " + id);
            }
        }).start();
    }

    @Override
    public void run() {
        try {
            conectar();
            iniciarServidorClientes();

            while (true) {

                out.writeObject(id);
                out.flush();

                Object resposta = in.readObject();

                if (resposta instanceof Veiculo v) {

                    int posicao = esteiraLoja.produzir(v);
                    v.setLoja(id, posicao);

                    System.out.println(v.logRecebimentoLoja());
                }

                Thread.sleep(500);
            }

        } catch (IOException e) {
            System.out.println("Erro de conexão da loja " + id + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao receber objeto na loja " + id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar socket da loja " + id);
            }
        }
    }
}