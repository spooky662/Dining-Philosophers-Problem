import java.util.concurrent.Semaphore;

class Fabrica {

    private static final int NUM_ESTACOES = 4;
    private static final int FUNCIONARIOS_POR_ESTACAO = 5;

    private final Semaphore estoquePecas;
    private final Semaphore esteiraPecas;
    private final BufferCircular esteiraVeiculos;

    public Fabrica() {
        this.estoquePecas = new Semaphore(500);
        this.esteiraPecas = new Semaphore(5);
        this.esteiraVeiculos = new BufferCircular(40);
    }

    public void iniciarProducao() {
        for (int estacaoId = 0; estacaoId < NUM_ESTACOES; estacaoId++) {
            Semaphore[] ferramentas = new Semaphore[FUNCIONARIOS_POR_ESTACAO];

            for (int i = 0; i < FUNCIONARIOS_POR_ESTACAO; i++) {
                ferramentas[i] = new Semaphore(1);
            }

            // evita deadlock no estilo jantar dos filósofos adaptado
            Semaphore controle = new Semaphore(FUNCIONARIOS_POR_ESTACAO - 1);

            for (int i = 0; i < FUNCIONARIOS_POR_ESTACAO; i++) {
                Semaphore esquerda = ferramentas[i];
                Semaphore direita = ferramentas[(i + 1) % FUNCIONARIOS_POR_ESTACAO];

                Funcionario funcionario = new Funcionario(
                        i,
                        estacaoId,
                        esquerda,
                        direita,
                        controle,
                        esteiraVeiculos,
                        estoquePecas,
                        esteiraPecas
                );

                funcionario.start();
            }
        }
    }

    public Veiculo fornecerVeiculoParaLoja(int lojaId) throws InterruptedException {
        return esteiraVeiculos.consumir();
    }
}