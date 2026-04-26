import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class AtendenteLoja extends Thread {

    private final Socket socket;
    private final Fabrica fabrica;

    public AtendenteLoja(Socket socket, Fabrica fabrica) {
        this.socket = socket;
        this.fabrica = fabrica;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object obj = in.readObject();

                if (obj instanceof Integer lojaId) {

                    Veiculo v = fabrica.fornecerVeiculoParaLoja(lojaId);

                    out.writeObject(v);
                    out.flush();
                }
            }

        } catch (Exception e) {
            System.out.println("Conexao com loja encerrada.");
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexao da loja.");
            }
        }
    }
}