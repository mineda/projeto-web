package br.gov.sp.fatec.projetoweb.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pro_professor")
public class Professor extends Usuario {
    
    @Column(name="pro_titulo")
    private String titulo;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "avaliador")
    private Set<Trabalho> trabalhosAvaliados;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Trabalho> getTrabalhosAvaliados() {
        return trabalhosAvaliados;
    }

    public void setTrabalhosAvaliados(Set<Trabalho> trabalhosAvaliados) {
        this.trabalhosAvaliados = trabalhosAvaliados;
    }
    
}
