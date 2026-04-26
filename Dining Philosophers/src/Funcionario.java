import java.util.concurrent.Semaphore;

class Funcionario extends Thread {

    private final int id;
    private final int estacaoId;

    private final Semaphore ferramentaEsquerda;
    private final Semaphore ferramentaDireita;
    private final Semaphore controle;

    private final BufferCircular esteiraVeiculos;
    private final Semaphore estoquePecas;
    private final Semaphore esteiraPecas;

    public Funcionario(
            int id,
            int estacaoId,
            Semaphore ferramentaEsquerda,
            Semaphore ferramentaDireita,
            Semaphore controle,
            BufferCircular esteiraVeiculos,
            Semaphore estoquePecas,
            Semaphore esteiraPecas) {

        this.id = id;
        this.estacaoId = estacaoId;
        this.ferramentaEsquerda = ferramentaEsquerda;
        this.ferramentaDireita = ferramentaDireita;
        this.controle = controle;
        this.esteiraVeiculos = esteiraVeiculos;
        this.estoquePecas = estoquePecas;
        this.esteiraPecas = esteiraPecas;
    }

    @Override
    public void run() {
        try {
            while (true) {
                descansar();
                pegarFerramentas();
                solicitarPeca();
                produzirVeiculo();
                liberarFerramentas();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void descansar() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 800));
    }

    private void pegarFerramentas() throws InterruptedException {
        controle.acquire();
        ferramentaEsquerda.acquire();
        ferramentaDireita.acquire();
    }

    private void solicitarPeca() throws InterruptedException {
        estoquePecas.acquire();

        esteiraPecas.acquire();
        Thread.sleep((long) (Math.random() * 200));
        esteiraPecas.release();
    }

    private void produzirVeiculo() throws InterruptedException {
        Veiculo veiculo = new Veiculo(estacaoId, id);

        int posicao = esteiraVeiculos.produzir(veiculo);
        veiculo.setPosicaoEsteiraFabrica(posicao);

        System.out.println(veiculo.logProducao());

        Thread.sleep((long) (Math.random() * 500));
    }

    private void liberarFerramentas() {
        ferramentaDireita.release();
        ferramentaEsquerda.release();
        controle.release();
    }
}