import java.util.concurrent.Semaphore;

class BufferCircular {

    private final Veiculo[] buffer;
    private final int tamanho;

    private int entrada;
    private int saida;

    private final Semaphore vazio;
    private final Semaphore cheio;
    private final Semaphore mutex;

    public BufferCircular(int tamanho) {
        this.tamanho = tamanho;
        this.buffer = new Veiculo[tamanho];
        this.entrada = 0;
        this.saida = 0;

        this.vazio = new Semaphore(tamanho);
        this.cheio = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public int produzir(Veiculo v) throws InterruptedException {
        vazio.acquire();
        mutex.acquire();

        int posicaoInserida = entrada;
        buffer[entrada] = v;

        entrada = (entrada + 1) % tamanho;

        mutex.release();
        cheio.release();

        return posicaoInserida;
    }

    public Veiculo consumir() throws InterruptedException {
        cheio.acquire();
        mutex.acquire();

        Veiculo v = buffer[saida];
        buffer[saida] = null;

        saida = (saida + 1) % tamanho;

        mutex.release();
        vazio.release();

        return v;
    }
}