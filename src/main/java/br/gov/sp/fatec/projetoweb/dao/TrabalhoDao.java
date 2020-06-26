package br.gov.sp.fatec.projetoweb.dao;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import br.gov.sp.fatec.projetoweb.entity.Aluno;
import br.gov.sp.fatec.projetoweb.entity.Trabalho;

public class TrabalhoDao {
    
    private EntityManager manager;
    private AlunoDao alunoDao;
    private ProfessorDao professorDao;

    public TrabalhoDao() {
        this(PersistenceManager.getInstance().getEntityManager());
    }
    
    public TrabalhoDao(EntityManager manager) {
        this.manager = manager;
        // Usamos o mesmo manager nos outros DAOs para manter
        // tudo no mesmo contexto
        alunoDao = new AlunoDao(manager);
        professorDao = new ProfessorDao(manager);
    }
    
    
    public Trabalho buscar(Long id) {
        return manager.find(Trabalho.class, id);
    }
    
    public void salvar(Trabalho trabalho) throws RollbackException {
        try {
            manager.getTransaction().begin();
            salvarSemCommit(trabalho);
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
    
    public void salvarSemCommit(Trabalho trabalho) {
        for(Aluno aluno: trabalho.getAlunos()) {
            // Um aluno sem id nao esta no BD
            if(aluno.getId() == null) {
                // Salvamos o aluno
                // Usamos esse metodo para nao commitar
                alunoDao.salvarSemCommit(aluno);;
            }
        }
        // Se o trabalho possui avaliador e ele nao possui id
        if(trabalho.getAvaliador() != null && 
                trabalho.getAvaliador().getId() == null) {
            // Salvamos o professor
            // Usamos esse metodo para nao commitar
            professorDao.salvarSemCommit(trabalho.getAvaliador());
        }
        if(trabalho.getId() == null) {
            manager.persist(trabalho);
        }
        else {
            manager.merge(trabalho);
        }
        
    }
    
    public void excluir(Long id) throws RollbackException {
        Trabalho trabalho = manager.find(Trabalho.class, id);
        try {
            manager.getTransaction().begin();
            manager.remove(trabalho);
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
    
}
