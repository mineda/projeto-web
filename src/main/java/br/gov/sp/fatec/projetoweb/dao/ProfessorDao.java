package br.gov.sp.fatec.projetoweb.dao;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import br.gov.sp.fatec.projetoweb.entity.Professor;

public class ProfessorDao {
    
    private EntityManager manager;

    public ProfessorDao() {
        manager = PersistenceManager
                .getInstance().getEntityManager();
    }
    
    public ProfessorDao(EntityManager manager) {
        this.manager = manager;
    }
        
    public Professor buscar(Long id) {
        return manager.find(Professor.class, id);
    }
    
    public void salvar(Professor professor) throws RollbackException {
        try {
            manager.getTransaction().begin();
            salvarSemCommit(professor);
            manager.flush();
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
    
    public void salvarSemCommit(Professor professor) {
        if(professor.getId() == null) {
            manager.persist(professor);
        }
        else {
            manager.merge(professor);
        }
    }
    
    public Professor buscarPorNome(String nome) {
        String consulta = "select p from Professor p where nomeUsuario = :nome";
        TypedQuery<Professor> query = manager.createQuery(consulta, Professor.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }
    
    public void excluir(Long id) throws RollbackException {
        Professor professor = buscar(id);
        try {
            manager.getTransaction().begin();
            manager.remove(professor);
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
    
}
