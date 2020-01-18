package blackcheck.com.br.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Date;

import blackcheck.com.br.R;
import blackcheck.com.br.helper.ConfiguraçãoFirebase;
import blackcheck.com.br.model.Cupom;

public class EmpresaActivity extends AppCompatActivity {

    private EditText editNome, editRua, editTelefone, editCidade, editCep, editValidadeInicio, editValidadeFim, editRedeSocial;
    private ImageView imagemCupom;
    private FirebaseAuth autenticacao;
    private Button botaoCadastro;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private String urlImagemSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        getSupportActionBar().hide();

        inicializarComponentes();
        storageReference = ConfiguraçãoFirebase.getReferenciaStorage();

        imagemCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        );
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK){
            Bitmap imagem = null;

            try{
                switch (requestCode){
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagem);
                    break;
                }

                if(imagem != null){
                    imagemCupom.setImageBitmap(imagem);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    String nome = editNome.getText().toString();

                    StorageReference imagemRef = storageReference.child("imagens").child("cupons").child(nome + "jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EmpresaActivity.this,"Erro ao fazer upload da imagem", Toast.LENGTH_SHORT ).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            urlImagemSelecionada = taskSnapshot.getDownloadUrl().toString();

                            Toast.makeText(EmpresaActivity.this,"Sucesso ao fazer upload da imagem", Toast.LENGTH_SHORT ).show();

                        }
                    });


                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void validarDadosCupom(View view){

        String nome = editNome.getText().toString();
        String rua = editRua.getText().toString();
        String telefone = editTelefone.getText().toString();
        String cidade = editCidade.getText().toString();
        String cep = editCep.getText().toString();
        String validadeInicio = editValidadeInicio.getText().toString();
        String validadeFim = editValidadeFim.getText().toString();
        String redeSocial = editRedeSocial.getText().toString();

        if(!nome.isEmpty()){
            if(!rua.isEmpty()){
                if(!telefone.isEmpty()){
                    if(!cidade.isEmpty()){
                        if(!cep.isEmpty()){
                            if(!validadeInicio.isEmpty()){
                                if(!validadeFim.isEmpty()){
                                    if(!redeSocial.isEmpty()){
                                        Cupom cupom = new Cupom();

                                        cupom.setIdCupom("1");
                                        cupom.setNome(nome);
                                        cupom.setCep(cep);
                                        cupom.setRua(rua);
                                        cupom.setTelefone(telefone);
                                        cupom.setRedeSocial(redeSocial);
                                        cupom.setCidade(cidade);
                                        cupom.setValidadeInicio(validadeInicio);
                                        cupom.setValidadeFim(validadeFim);
                                        cupom.setUrlImagem(urlImagemSelecionada);
                                        cupom.salvar();
                                        finish();


                                    }else{
                                        exibirMensagem("Insira uma Rede Social");
                                    }
                                }else{
                                    exibirMensagem("Insira uma data de validade final");
                                }
                            }else {
                                exibirMensagem("Insira uma data de validade inicial");
                            }
                        }else{
                            exibirMensagem("Insira um cep");
                        }
                    }else{
                        exibirMensagem("Insira uma cidade");
                    }
                }else{
                    exibirMensagem("Insira um telefone");
                }
            }else{
                exibirMensagem("Insira uma rua");
            }
        }else{
            exibirMensagem("Insira um nome");
        }

    }


    private void exibirMensagem(String texto){
        Toast.makeText(this,texto,Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes(){
        editNome = findViewById(R.id.campoNome);
        editRua = findViewById(R.id.campoRua);
        editCidade = findViewById(R.id.campoCidade);
        editCep = findViewById(R.id.campoCep);
        editValidadeFim = findViewById(R.id.campoValidadeFinal);
        editValidadeInicio = findViewById(R.id.campoValidadeInicio);
        editTelefone = findViewById(R.id.campoTelefone);
        editRedeSocial = findViewById(R.id.campoRedeSocial);
        imagemCupom = findViewById(R.id.imagemCupom);
        botaoCadastro = findViewById(R.id.botaoCadastro);

    }

}
