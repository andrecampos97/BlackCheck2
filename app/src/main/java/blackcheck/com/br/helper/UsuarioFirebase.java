package blackcheck.com.br.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioFirebase {

    public static String getIdUsuario(){

        FirebaseAuth autenticacao = ConfiguraçãoFirebase.getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();
    }

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfiguraçãoFirebase.getFirebaseAutenticacao();

        return usuario.getCurrentUser();
    }

    public static boolean atualizarTipoUsuario (String tipo){

        return true;
    }

}
