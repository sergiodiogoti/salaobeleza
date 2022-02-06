package com.DAO;
import com.Bean.HorarioTrabalho;
import com.Bean.Profissional;
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
public class HorarioTrabalhoDAO extends GenericDAO {
    private static final long serialVersionUID = 1L;
    private Session session;
	
    public HorarioTrabalhoDAO(Session session) {
        this.session = session;
    }

    public HorarioTrabalhoDAO() {
        this.session = getSession();
    }
    public int addHorarioTrabalho(HorarioTrabalho horarioTrabalho) {
        setAtualizar(false);
            saveOrUpdatePojo(horarioTrabalho);
            if (MensagensErros.isErro()) {
                return 0;
            } else {
                return horarioTrabalho.getHorTraCodigo();
            }   
    }
    public HorarioTrabalho getHorarioTrabalho(int horarioTrabalhoID) {
        return getPojo(HorarioTrabalho.class, horarioTrabalhoID);
    }
    public void updateHorarioTrabalho(HorarioTrabalho horarioTrabalho) {
        setAtualizar(true);
        saveOrUpdatePojo(horarioTrabalho);
    }
    public void removeHorarioTrabalho(HorarioTrabalho horarioTrabalho) {
        removePojo(horarioTrabalho);
    }

    public List<HorarioTrabalho> getHorarioTrabalhos(Profissional pro) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql;
            String clausula = "true";
            if (pro != null) {
                clausula = "horTraProCodigo = " + pro.getProCodigo() + "\n";
            }
            sql = "SELECT * FROM horariotrabalho WHERE " + clausula + " ORDER BY horTraCodigo";
            Query q = ses.createNativeQuery(sql).addEntity(HorarioTrabalho.class);
            if (!q.list().isEmpty())
                return (List<HorarioTrabalho>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}