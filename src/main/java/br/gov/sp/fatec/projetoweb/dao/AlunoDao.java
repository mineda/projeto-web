package br.gov.sp.fatec.projetoweb.dao;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import br.gov.sp.fatec.projetoweb.entity.Aluno;

public class AlunoDao {
    
    private EntityManager manager;

    public AlunoDao() {
        manager = PersistenceManager
                .getInstance().getEntityManager();
    }
    
    public AlunoDao(EntityManager manager) {
        this.manager = manager;
    }
    
    public Aluno buscar(Long id) {
        return manager.find(Aluno.class, id);
    }
    
    public void salvar(Aluno aluno) throws RollbackException {
        try {
            manager.getTransaction().begin();
            salvarSemCommit(aluno);
            manager.flush();
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
    
    public void salvarSemCommit(Aluno aluno) {
        if(aluno.getId() == null) {
            manager.persist(aluno);
        }
        else {
            manager.merge(aluno);
        }
    }
    
    public Aluno buscarPorNome(String nome) {
        String consulta = "select a from Aluno a where nomeUsuario = :nome";
        TypedQuery<Aluno> query = manager.createQuery(consulta, Aluno.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }
    
    public void excluir(Long id) throws RollbackException {
        Aluno aluno = manager.find(Aluno.class, id);
        try {
            manager.getTransaction().begin();
            manager.remove(aluno);
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
    
}
