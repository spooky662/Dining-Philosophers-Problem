class GaragemCliente {

    private final BufferCircular garagem;

    public GaragemCliente(int capacidade) {
        this.garagem = new BufferCircular(capacidade);
    }

    public void guardar(Veiculo v) throws InterruptedException {
        garagem.produzir(v);
    }
}