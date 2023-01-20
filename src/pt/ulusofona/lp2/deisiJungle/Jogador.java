package pt.ulusofona.lp2.deisiJungle;


public class Jogador {

    /**
     * Numero maximo de bananas ingeridas por jogador
     * se ingerir mais do que uma banana o jogador perde energia
     */
    private static final int MAX_BANANAS = 1;

    private int id;
    private int energia;
    private String nome;
    private Especie especie;
    private int posicaoAtual;
    private int posicaoPercorrida = 0;
    private int somarComida = 0;
    private int qtdDeBananasIngeridas;

    public Jogador() {
    }

    public Jogador(int id, String nome, Especie especie, int posicaoAtual) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.energia = especie.buscarEnergiaInicial();
        this.posicaoAtual = posicaoAtual;
        this.qtdDeBananasIngeridas = 0;
    }

    public int buscarId() {
        return id;
    }

    public String buscarNome() {
        return nome;
    }

    public Especie buscarEspecie() {
        return especie;
    }

    public int buscarQtdDeBananasIngeridas() {
        return qtdDeBananasIngeridas;
    }

    public int buscarPosicaoPercorrida() {
        return this.posicaoPercorrida;
    }

    public int buscarSomarComida() {
        return this.somarComida;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public void setPosicaoAtual(int posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public void setQtdDeBananasIngeridas(int qtdDeBananasIngeridas) {
        this.qtdDeBananasIngeridas = qtdDeBananasIngeridas;
    }

    boolean validarNome() {
        if (nome == null) {
            return false;
        }
        return nome.length() > 0;
    }

    int buscarPosicaoAtual() {
        return posicaoAtual;
    }

    int buscarEnergia() {

        if (this.energia < 0) {
            return this.energia * -1;
        }

        if (this.energia > 200) {
            return this.energia = 200;
        }
        return this.energia;
    }

    MovementResultCode mover(int nrSquares, boolean verificacao) {

        if (this.energia < this.especie.consumoEnergia * Math.abs(nrSquares)) {
            return MovementResultCode.NO_ENERGY;
        }

        if (((Math.abs(nrSquares) < especie.buscarVelocidadeMinima()) || (Math.abs(nrSquares) >
                especie.buscarVelocidadeMaxima())) && verificacao) {
            return MovementResultCode.INVALID_MOVEMENT;

        } else {
            this.energia -= especie.consumoEnergia * Math.abs(nrSquares);
            this.posicaoAtual += nrSquares;
            this.posicaoPercorrida += Math.abs(nrSquares);
            return MovementResultCode.VALID_MOVEMENT;
        }


    }

    void ficar() {
        this.energia += this.especie.buscarGanhoEnergiaEmDescanso();
        if (this.energia > 200) {
            this.energia = 200;
        }
    }

    void removeEnergia(int valor) {
        this.energia -= valor;
    }

    void adicionaEnergiaUnicornios(){

            this.energia += 2;
    }

    void atualizarPosicao(int casaPretendida) {
        this.posicaoAtual = casaPretendida;
    }

    String buscarNomeJogador() {
        return this.nome;
    }

    String buscarNomeEspecie() {
        return especie.buscarNome();
    }

    boolean consumirAlimento(Alimento a, int turno, Especie especie) {

        if(especie.eMitologico()){
            return false;
        }

        switch (a.buscarIdentificadorAlimento()) {
            case 'c':
                if (especie.eCarnivoro() || especie.eOmnivoro()) {
                    ingereCarne(turno);
                    this.somarComida++;
                    return true;
                } else {
                    return false;
                }
            case 'b':
                ingereCachoDeBananas((CachoDeBananas) a);
                this.somarComida++;
                return true;
            case 'm':
                ingereCogumelosMagicos(turno, (CogumeloMagico) a);
                this.somarComida++;
                return true;
            case 'e':
                ingereErva();
                this.somarComida++;
                return true;
            case 'a':
                ingereAgua();
                this.somarComida++;
                return true;
            default:
                return false;
        }
    }

    void ingereErva() {

        if (especie.eHerbivoro() || especie.eOmnivoro()) {
            if (this.energia > 200) {
                this.energia = 200;
            }
            this.energia += 20;
        } else {
            this.energia -= 20;
        }

    }

    void ingereAgua() {

        if (especie.eCarnivoro() || especie.eHerbivoro()) {
            if (this.energia > 200) {
                this.energia = 200;
            }
            this.energia += 15;
        } else {
            this.energia = (int) (this.energia * 0.20) + this.energia;
        }

    }

    void ingereCarne(int turno) {

        Carne carne = new Carne();

        if (!carne.eToxica(turno)) {
            if (especie.eCarnivoro() || especie.eOmnivoro()) {
                if (this.energia > 200) {
                    this.energia = 200;
                }
                this.energia += 50;
            }
        } else {
            this.energia -= (this.energia / 2); // carne toxica
        }
    }

    void ingereCogumelosMagicos(int turno, CogumeloMagico cogumelosMagicos) {

        int valor = (int) ((this.energia / 100.0f) * cogumelosMagicos.buscarNrCogumelo());

        if (turno % 2 == 0) {
            this.energia += valor;
            if (this.energia > 200) {
                this.energia = 200;
            }
        } else {
            this.energia -= valor;
            if (this.energia < 0) {
                this.energia = 0;
            }
        }
    }

    void ingereCachoDeBananas(CachoDeBananas cachoDeBananas) {

        if (cachoDeBananas.buscarBananasNoCacho()) {
            qtdDeBananasIngeridas++;
            int energiaASomar = 0;

            if (this.energia > 200) {
                this.energia = 200;

            } else if (qtdDeBananasIngeridas <= MAX_BANANAS) {
                energiaASomar = 40;
            } else {
                energiaASomar = -40;
            }
            this.energia += energiaASomar;
        }
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", energia=" + energia +
                        ", nome='" + nome + '\'' +
                        ", especie=" + especie +
                        ", posicaoAtual=" + posicaoAtual +
                        ", qtdDeBananasIngeridas=" + qtdDeBananasIngeridas +
                        '}' + "\n";
    }
}

