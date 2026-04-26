import java.io.*;
import java.net.Socket;
import java.util.Random;

class ClienteRemoto extends Thread {

    private final int id;
    private final int[] portas;
    private final Random random;

    public ClienteRemoto(int id, int[] portas) {
        this.id = id;
        this.portas = portas;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            int quantidadeCompras = random.nextInt(3) + 1;

            for (int i = 0; i < quantidadeCompras; i++) {

                int portaEscolhida = portas[random.nextInt(portas.length)];

                Socket socket = new Socket("localhost", portaEscolhida);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("COMPRAR:" + id);

                String resposta = in.readLine();

                if (resposta != null && resposta.startsWith("OK")) {
                    String[] partes = resposta.split(" ");
                    int idVeiculo = Integer.parseInt(partes[1]);

                    System.out.println("Cliente " + id +
                            " comprou veiculo " + idVeiculo +
                            " na porta " + portaEscolhida);
                } else {
                    System.out.println("Cliente " + id + " nao conseguiu comprar (sem estoque)");
                }

                socket.close();

                Thread.sleep((long) (Math.random() * 1500));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}