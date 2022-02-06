package com.DAO;
import com.Bean.Cliente;
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
public class ProfissionalDAO extends GenericDAO {
    private static final long serialVersionUID = 1L;
    private Session session;
	
    public ProfissionalDAO(Session session) {
        this.session = session;
    }

    public ProfissionalDAO() {
        this.session = getSession();
    }
    public int addProfissional(Profissional profissional) {
        setAtualizar(false);
            saveOrUpdatePojo(profissional);
            if (MensagensErros.isErro()) {
                return 0;
            } else {
                return profissional.getProCodigo();
            }   
    }
    public Profissional getProfissional(int profissionalID) {
        return getPojo(Profissional.class, profissionalID);
    }
    public void updateProfissional(Profissional profissional) {
        setAtualizar(true);
        saveOrUpdatePojo(profissional);
    }
    public void removeProfissional(Profissional profissional) {
        removePojo(profissional);
    }

    public List<Profissional> getProfissionais(String param) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql;
            String clausula = "true";
            if (param != null) {
                clausula = "proNome LIKE '%" + param + "%'\n" +
                        "OR (proCPF  LIKE '%" + param + "%')\n";
            }
            sql = "SELECT * FROM profissional WHERE " + clausula + " ORDER BY proNome";
            Query q = ses.createNativeQuery(sql).addEntity(Profissional.class);
            if (!q.list().isEmpty())
                return (List<Profissional>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //verifica se existe algum profissional cadastrado no banco com o cpf passado no parametro.
    public boolean existeProfissional(String cpf) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM profissional where proCPF LIKE '%" + cpf + "%'";
            Query q = ses.createNativeQuery(sql).addEntity(Profissional.class);
            if (q.list().size() > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}