package pt.ulusofona.lp2.deisiJungle

enum class CommandType{
    GET,POST
}


fun tipoGet(manager:GameManager, lista:ArrayList<String>) : String {

    return ""
}

fun tipoPost(manager:GameManager, lista:ArrayList<String>): String{

    return ""
}

fun tipoComando(comando:CommandType, lista: ArrayList<String>) :  CommandType  {

    if(comando == CommandType.GET){
        return tipoGet(,lista);
    } else{
        return tipoPost(comando,lista);
    }

}
fun router(comando:CommandType, lista: ArrayList<String>): Function {
    
    return tipoComando(comando,lista)
}
