package blackcheck.com.br.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import blackcheck.com.br.helper.ConfiguraçãoFirebase;

public class Usuario {

    private String idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private String Senha;
    private String ConfirmarSenha;
    private String CPF;
    private String tipo;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebase = ConfiguraçãoFirebase.getFirebaseDataBase();
        firebase.child("usuario").child(this.idUsuario).setValue(this);

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Exclude
    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    @Exclude
    public String getConfirmarSenha() {
        return ConfirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        ConfirmarSenha = confirmarSenha;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
