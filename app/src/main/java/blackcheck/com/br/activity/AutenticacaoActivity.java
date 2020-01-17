package blackcheck.com.br.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import blackcheck.com.br.R;
import blackcheck.com.br.helper.ConfiguraçãoFirebase;
import blackcheck.com.br.model.Usuario;

public class AutenticacaoActivity extends AppCompatActivity {

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();



        inicializaComponentes();

        autenticacao = ConfiguraçãoFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();



        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email =  campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();



                if(!email.isEmpty()){
                    if(!senha.isEmpty()){

                            usuario = new Usuario();
                            usuario.setEmail(email);
                            usuario.setSenha(senha);

                            validarLogin();

                            }
                    }else{
                        Toast.makeText(AutenticacaoActivity.this,"Preencha a Senha!", Toast.LENGTH_SHORT).show();
                    }
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btRegistrar(View view){
        startActivity(new Intent(this, RegistroActivity.class));
    }

    private void verificarUsuarioLogado(){
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        autenticacao.signOut();
        if( usuarioAtual != null){
            abrirTelaPrincipal();
        }
    }



    private void abrirTelaPrincipal(){

        String email =  campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(email.equals("dimitri.cunha@gmail.com") && senha.equals("senha123")){
            startActivity(new Intent(getApplicationContext(), EmpresaActivity.class));
        }else{
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

    }

    public void validarLogin() {

        autenticacao = ConfiguraçãoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    abrirTelaPrincipal();

                }else{

                    String excecao = "";

                    try{
                        throw task.getException();

                    }catch(FirebaseAuthInvalidUserException e){
                        excecao = "Usuário não esta cadastrado!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado!";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(AutenticacaoActivity.this,excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void inicializaComponentes(){
        campoEmail = findViewById(R.id.campoEmail);
        campoSenha = findViewById(R.id.campoSenha);
        botaoAcessar = findViewById(R.id.botaoAcessar);
        //tipoAcesso = findViewById(R.id.switchAcesso);

    }
}
