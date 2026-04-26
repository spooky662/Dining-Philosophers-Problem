import java.io.Serializable;
import java.util.concurrent.Semaphore;

class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int contador = 0;
    private static final Semaphore mutexId = new Semaphore(1);

    private final int id;
    private final String cor;
    private final String tipo;

    private final int estacaoId;
    private final int funcionarioId;

    private int posicaoEsteiraFabrica;
    private int lojaId;
    private int posicaoEsteiraLoja;

    public Veiculo(int estacaoId, int funcionarioId) throws InterruptedException {
        this.id = gerarId();
        this.cor = gerarCor(this.id);
        this.tipo = gerarTipo(this.id);
        this.estacaoId = estacaoId;
        this.funcionarioId = funcionarioId;
    }

    private int gerarId() throws InterruptedException {
        mutexId.acquire();
        contador++;
        int novoId = contador;
        mutexId.release();
        return novoId;
    }

    private String gerarCor(int id) {
        String[] cores = {"VERMELHO", "VERDE", "AZUL"};
        return cores[(id - 1) % 3];
    }

    private String gerarTipo(int id) {
        return (id % 2 == 0) ? "SUV" : "SEDAN";
    }

    public void setPosicaoEsteiraFabrica(int posicaoEsteiraFabrica) {
        this.posicaoEsteiraFabrica = posicaoEsteiraFabrica;
    }

    public void setLoja(int lojaId, int posicaoEsteiraLoja) {
        this.lojaId = lojaId;
        this.posicaoEsteiraLoja = posicaoEsteiraLoja;
    }

    public int getId() {
        return id;
    }

    public int getEstacaoId() {
        return estacaoId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public String getCor() {
        return cor;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPosicaoEsteiraFabrica() {
        return posicaoEsteiraFabrica;
    }

    public int getLojaId() {
        return lojaId;
    }

    public int getPosicaoEsteiraLoja() {
        return posicaoEsteiraLoja;
    }

    public String logProducao() {
        return "LOG PRODUCAO -> Veiculo: " + id
                + " | Cor: " + cor
                + " | Tipo: " + tipo
                + " | Estacao: " + estacaoId
                + " | Funcionario: " + funcionarioId
                + " | Posicao na esteira da fabrica: " + posicaoEsteiraFabrica;
    }

    public String logVendaLoja() {
        return "LOG VENDA PARA LOJA -> Veiculo: " + id
                + " | Cor: " + cor
                + " | Tipo: " + tipo
                + " | Estacao: " + estacaoId
                + " | Funcionario: " + funcionarioId
                + " | Posicao na esteira da fabrica: " + posicaoEsteiraFabrica
                + " | Loja compradora: " + lojaId
                + " | Posicao na esteira da loja: " + posicaoEsteiraLoja;
    }

    public String logRecebimentoLoja() {
        return "LOG RECEBIMENTO LOJA -> Veiculo: " + id
                + " | Cor: " + cor
                + " | Tipo: " + tipo
                + " | Estacao: " + estacaoId
                + " | Funcionario: " + funcionarioId
                + " | Loja: " + lojaId
                + " | Posicao na esteira da loja: " + posicaoEsteiraLoja;
    }

    public String logVendaCliente(int clienteId) {
        return "LOG VENDA PARA CLIENTE -> Veiculo: " + id
                + " | Cliente: " + clienteId
                + " | Loja: " + lojaId
                + " | Cor: " + cor
                + " | Tipo: " + tipo;
    }
}