package pt.ulusofona.lp2.deisiJungle

enum class CommandType{
    GET,POST
}


fun tipoGet(game:GameManager, lista:List<String>) : String {

    return ""
}

fun tipoPost(game:GameManager, lista:List<String>): String{

    return ""
}

fun tipoComando(comando:CommandType, lista: List<String>) : CommandType {

    if(comando == CommandType.GET){
        return CommandType.GET; //está mal é so para n dar erro
    } else {
        return CommandType.POST; //está mal é so para n dar erro
    }

}

/*
fun router(comando:CommandType, lista: ArrayList<String>): Function { //dá erro ainda, ainda nao está feito

    return tipoComando(comando, lista)

}

/* fun router (): Function1<CommandType, Function2<GameManager, List<String>, String?>> {
    return tipoComando(comando, lista)
} */

 */

//FUNCOES PARA DEPOIS COLOCAR NO TIPOGET





//FUNCOES PARA DEPOIS COLOCAR NO TIPOPOST
