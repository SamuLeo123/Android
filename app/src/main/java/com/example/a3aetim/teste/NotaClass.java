package com.example.a3aetim.teste;

public class NotaClass {
    public int IdNota;
    public String Titulo;
    public String Texto;
    public int IdUser;

    public NotaClass(int Id, String Titulo, String Text, int IdUser){
        this.IdNota = Id;
        this.Titulo = Titulo;
        this.Texto = Text;
        this.IdUser = IdUser;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getTexto() {
        return Texto;
    }

    public int getIdNota() {
        return IdNota;
    }

    public int getIdUser() {
        return IdUser;
    }
}
