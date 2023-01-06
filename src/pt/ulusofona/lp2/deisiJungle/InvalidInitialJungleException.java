package pt.ulusofona.lp2.deisiJungle;

public class InvalidInitialJungleException extends Exception {
    String mensagem;

    boolean jogadorEInvalido;
    boolean comidaEInvalida;

    public InvalidInitialJungleException(String mensagem, boolean jogadorEInvalido, boolean comidaEInvalida) {
        this.mensagem = mensagem;
        this.jogadorEInvalido = jogadorEInvalido;
        this.comidaEInvalida = comidaEInvalida;
    }

    public String getMessage() {
        return mensagem;
    }

    public boolean isInvalidPlayer() {

        return jogadorEInvalido;

    }

    public boolean isInvalidFood() {

        return comidaEInvalida;
    }

}






