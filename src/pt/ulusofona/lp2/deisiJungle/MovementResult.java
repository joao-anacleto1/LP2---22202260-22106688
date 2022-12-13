package pt.ulusofona.lp2.deisiJungle;

public class MovementResult {

    MovementResultCode code;
    String message;

    String message(){
        return this.message;
    }

    MovementResultCode code(){
        code = MovementResultCode.CAUGHT_FOOD;
        return code;
    }

}
