package com.DAO;
import com.Bean.Agendamento;
import com.Bean.HorarioTrabalho;
import com.Bean.Profissional;
import com.Util.HibernateUtil;
import com.Util.MensagensErros;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Sergio
 */
public class AgendamentoDAO extends GenericDAO {
    private static final long serialVersionUID = 1L;
    private Session session;
	
    public AgendamentoDAO(Session session) {
        this.session = session;
    }

    public AgendamentoDAO() {
        this.session = getSession();
    }
    public int addAgendamento(Agendamento agendamento) {
        setAtualizar(false);
            saveOrUpdatePojo(agendamento);
            if (MensagensErros.isErro()) {
                return 0;
            } else {
                return agendamento.getAgeCodigo();
            }   
    }
    public Agendamento getAgendamento(int agendamentoID) {
        return getPojo(Agendamento.class, agendamentoID);
    }
    public void updateAgendamento(Agendamento agendamento) {
        setAtualizar(true);
        saveOrUpdatePojo(agendamento);
    }
    public void removeAgendamento(Agendamento agendamento) {
        removePojo(agendamento);
    }
    public List<Agendamento> getAgendamentos() {
        return getPureList(Agendamento.class, "from Agendamento ar");
    }

    //busca horarios disponiveis que não têm agendamento na data e horario com profissional selecionado.
    public List<HorarioTrabalho> listaHorarioDisponivelParaAgendamento(Profissional profissional, Date dataAgendamento) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            if (null == profissional || null == dataAgendamento) {
                return new ArrayList<>();
            }
            String sql = "SELECT h.* FROM horariotrabalho h \n" +
                    "WHERE  h.horTraProCodigo = " + profissional.getProCodigo() + "\n" +
                    "AND LEFT(H.`horTraHorario`,2) > "+new SimpleDateFormat("HH").format(new Date())+"\n" +
                    "AND NOT EXISTS(SELECT 1 FROM agendamento a \n" +
                    "      JOIN itensagendamento it ON a.`ageCodigo` = it.`itAgeAgeCodigo` \n" +
                    "      JOIN profissional p ON it.`itAgeProCodigo` = p.`proCodigo` \n" +
                    "      WHERE itAgeData  = '" + new SimpleDateFormat("yyyy-MM-dd").format(dataAgendamento) + "'\n" +
                    "      AND  itAgeProCodigo = " + profissional.getProCodigo() + "\n" +
                    "      AND it.`itAgesituacao` IN('AGUARDANDO','CONFIRMADO','EM ATENDIMENTO','CONCLUIDO')\n" +
                    "      AND LEFT(it.`itAgeHora`,5) = h.`horTraHorario`);";
            Query q = ses.createNativeQuery(sql).addEntity(HorarioTrabalho.class);
            if(q.list().size() > 0)
            return q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ses.close();
        }
        return new ArrayList<>();
    }

    //busca horarios disponiveis do profissional logado e data selecionada.
    public List<HorarioTrabalho> listaHorarioDisponivelProfissional(Profissional profissional, Date data) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            if (null == profissional || null == profissional.getProCodigo() ) {
                return new ArrayList<>();
            }
            String sql = "SELECT h.* FROM horariotrabalho h \n" +
                    "WHERE  h.horTraProCodigo = " + profissional.getProCodigo() + "\n" +
                    "AND NOT EXISTS(SELECT 1 FROM agendamento a \n" +
                    "      JOIN profissional p ON a.`ageProCodigo` = p.`proCodigo` \n" +
                    "      WHERE ageData  = '" + new SimpleDateFormat("yyyy-MM-dd").format(data) + "'\n" +
                    "      AND  ageProCodigo = " + profissional.getProCodigo() + "\n" +
                    "      AND a.`ageSituacao` IN('AGUARDANDO','CONFIRMADO','EM ATENDIMENTO','CONCLUIDO')\n" +
                    "      AND LEFT(a.`ageHoraInicio`,5) = h.`horTraHorario`);";
            Query q = ses.createNativeQuery(sql).addEntity(HorarioTrabalho.class);
            if(q.list().size() > 0)
            return q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ses.close();
        }
        return new ArrayList<>();
    }

    //verifica se já existe um agendamento com situação aguardando na semana da data selecionada,
    // caso o salao tenha mais de um profissional, busca o agendamento por profissional,
    // levando em conta que a semana começa numa segunda e termina no domingo
    public Agendamento agendamentoExistente(Agendamento agendamentoSelected) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String clausula = "true";
            if (null != agendamentoSelected.getAgeCodigo()) {
                clausula = "ageCodigo <> " + agendamentoSelected.getAgeCodigo();
            }
            String sql = "SELECT * FROM agendamento\n" +
                    "WHERE YEARWEEK(ageData, 1) = YEARWEEK('" + sdf.format(agendamentoSelected.getAgeData()) + "', 1)\n" +
                    "AND ageCliCodigo = " + agendamentoSelected.getAgeCliCodigo() + "\n" +
                    "AND ageData <> " + sdf.format(agendamentoSelected.getAgeData())+ "\n" +
                    "AND " + clausula + "\n" +
                    "ORDER BY ageCodigo ASC LIMIT 1";
            Query q = ses.createNativeQuery(sql).addEntity(Agendamento.class);
            if (q.list().size() > 0)
                return (Agendamento) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ses.close();
        }
        return null;
    }
}