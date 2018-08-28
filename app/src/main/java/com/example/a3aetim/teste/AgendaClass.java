package com.example.a3aetim.teste;

import java.util.Date;

public class AgendaClass {
    public int Id;
    public String Titulo;
    public String Msg;
    public int IdUser;
    public Date Data;

    public AgendaClass(int Id, String Titulo, String Msg, int IdUser, Date Data){
        this.Id = Id;
        this.Titulo = Titulo;
        this.Msg = Msg;
        this.IdUser = IdUser;
        this.Data = Data;
    }
}
