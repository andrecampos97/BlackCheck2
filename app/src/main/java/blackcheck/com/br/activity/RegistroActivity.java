package blackcheck.com.br.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import blackcheck.com.br.R;
import blackcheck.com.br.helper.Base64Custom;
import blackcheck.com.br.helper.ConfiguraçãoFirebase;
import blackcheck.com.br.model.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private EditText campoNome, campoCPF, campoSenha, campoEmail, campoTelefone, campoConfirmarSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        campoNome = findViewById(R.id.campoNome);
        campoEmail = findViewById(R.id.campoEmail);
        campoCPF = findViewById(R.id.campoCPF);
        campoSenha = findViewById(R.id.campoSenha);
        campoConfirmarSenha = findViewById(R.id.campoConfirmarSenha);
        campoTelefone = findViewById(R.id.campoTelefone);
        botaoCadastrar = findViewById(R.id.botaoCadastro);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = campoNome.getText().toString();
                String textoCPF = campoCPF.getText().toString();
                String textoSenha = campoSenha.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoConfirmarSenha = campoConfirmarSenha.getText().toString();
                String textoTelefone = campoTelefone.getText().toString();

                //Validar se os campos foram preenchidos

                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoCPF.isEmpty()){
                            if(!textoSenha.isEmpty()){
                               if(!textoConfirmarSenha.isEmpty()){
                                   if(!textoTelefone.isEmpty()){

                                    usuario = new Usuario();
                                    usuario.setNome(textoNome);
                                    usuario.setCPF(textoCPF);
                                    usuario.setEmail(textoEmail);
                                    usuario.setSenha(textoSenha);
                                    usuario.setConfirmarSenha(textoConfirmarSenha);
                                    usuario.setTelefone(textoTelefone);

                                    cadastrarUsuario();

                                   }else{
                                       Toast.makeText(RegistroActivity.this, "Preencha o telefone!", Toast.LENGTH_SHORT).show();
                                   }
                               }else{
                                   Toast.makeText(RegistroActivity.this, "Preencha o confirmar senha!", Toast.LENGTH_SHORT).show();
                               }
                            }else{
                                Toast.makeText(RegistroActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegistroActivity.this, "Preencha o CPF!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegistroActivity.this, "Preencha o Email!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Preencha o nome!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguraçãoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String u = "u";
                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.setTipo(u);
                    usuario.salvar();
                    finish();

                }else{

                    String excecao = "";

                    try{
                        throw task.getException();

                    }catch(FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um e-mail válido!";
                    }catch(FirebaseAuthUserCollisionException e ){
                        excecao = "Essa conta ja foi cadastrada!";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(RegistroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
