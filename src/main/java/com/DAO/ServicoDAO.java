package com.DAO;
import com.Bean.Profissional;
import com.Bean.Servico;
import com.Util.HibernateUtil;
import com.Util.MensagensErros;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Sergio
 */
public class ServicoDAO extends GenericDAO {
    private static final long serialVersionUID = 1L;
    private Session session;
	
    public ServicoDAO(Session session) {
        this.session = session;
    }

    public ServicoDAO() {
        this.session = getSession();
    }
    public int addServico(Servico servico) {
        setAtualizar(false);
            saveOrUpdatePojo(servico);
            if (MensagensErros.isErro()) {
                return 0;
            } else {
                return servico.getSerCodigo();
            }   
    }
    public Servico getServico(int servicoID) {
        return getPojo(Servico.class, servicoID);
    }
    public void updateServico(Servico servico) {
        setAtualizar(true);
        saveOrUpdatePojo(servico);
    }
    public void removeServico(Servico servico) {
        removePojo(servico);
    }

    public List<Servico> getServicos(String param) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql;
            String clausula = "true";
            if (param != null) {
                clausula = "serDescricao LIKE '%" + param + "%'\n" +
                        "OR (serDuracao  LIKE '%" + param + "%')\n"+
                        "OR (serValor  LIKE '%" + param + "%')\n";
            }
            sql = "SELECT * FROM servico WHERE " + clausula + " ORDER BY serDescricao";
            Query q = ses.createNativeQuery(sql).addEntity(Servico.class);
            if (!q.list().isEmpty())
                return (List<Servico>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public boolean existeProfissional(String desc) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM servico where serDescricao LIKE '%" + desc + "%'";
            Query q = ses.createNativeQuery(sql).addEntity(Profissional.class);
            if (q.list().size() > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}