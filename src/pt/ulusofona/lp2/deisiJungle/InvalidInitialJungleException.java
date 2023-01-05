package pt.ulusofona.lp2.deisiJungle;

public class InvalidInitialJungleException extends Exception {
    String message;

    boolean playerIsValid;
    boolean foodIsValid;

    public InvalidInitialJungleException(String message, boolean playerIsValid, boolean foodIsValid) {
        this.message = message;
        this.playerIsValid = playerIsValid;
        this.foodIsValid = foodIsValid;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInvalidPlayer() {

        return playerIsValid;

    }

    public boolean isInvalidFood() {

        return foodIsValid;
    }

}






