package br.gov.sp.fatec.projetoweb.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="alu_aluno")
public class Aluno extends Usuario {
    
    @Column(name="alu_ra")
    private Long ra;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "alunos")
    private Set<Trabalho> trabalhosEntregues;

    public Long getRa() {
        return ra;
    }

    public void setRa(Long ra) {
        this.ra = ra;
    }

    public Set<Trabalho> getTrabalhosEntregues() {
        return trabalhosEntregues;
    }

    public void setTrabalhosEntregues(Set<Trabalho> trabalhosEntregues) {
        this.trabalhosEntregues = trabalhosEntregues;
    }
    
}
