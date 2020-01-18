package blackcheck.com.br.model;

import com.google.firebase.database.DatabaseReference;

import java.sql.Date;

import blackcheck.com.br.helper.ConfiguraçãoFirebase;

public class Cupom {

    private String idCupom;
    private String nome;
    private String rua;
    private String cidade;
    private String cep;
    private String urlImagem;
    private String redeSocial;
    private String telefone;

    public String getValidadeInicio() {
        return validadeInicio;
    }

    public void setValidadeInicio(String validadeInicio) {
        this.validadeInicio = validadeInicio;
    }

    public String getValidadeFim() {
        return validadeFim;
    }

    public void setValidadeFim(String validadeFim) {
        this.validadeFim = validadeFim;
    }

    private String validadeInicio;
    private String validadeFim;

    public Cupom() {
    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguraçãoFirebase.getFirebaseDataBase();
        DatabaseReference cupomRef = firebaseRef.child("cupom").child(getIdCupom());
        cupomRef.setValue(this);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(String idCupom) {
        this.idCupom = idCupom;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getRedeSocial() {
        return redeSocial;
    }

    public void setRedeSocial(String redeSocial) {
        this.redeSocial = redeSocial;
    }


}
