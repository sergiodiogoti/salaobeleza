package com.DAO;
import com.Bean.ItensAgendamento;
import com.Util.MensagensErros;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Sergio
 */
public class ItensAgendamentoDAO extends GenericDAO {
    private static final long serialVersionUID = 1L;
    private Session session;
	
    public ItensAgendamentoDAO(Session session) {
        this.session = session;
    }

    public ItensAgendamentoDAO() {
        this.session = getSession();
    }
    public int addItensAgendamento(ItensAgendamento itensAgendamento) {
        setAtualizar(false);
            saveOrUpdatePojo(itensAgendamento);
            if (MensagensErros.isErro()) {
                return 0;
            } else {
                return itensAgendamento.getItAgeCodigo();
            }   
    }
    public ItensAgendamento getItensAgendamento(int itensAgendamentoID) {
        return getPojo(ItensAgendamento.class, itensAgendamentoID);
    }
    public void updateItensAgendamento(ItensAgendamento itensAgendamento) {
        setAtualizar(true);
        saveOrUpdatePojo(itensAgendamento);
    }
    public void removeItensAgendamento(ItensAgendamento itensAgendamento) {
        removePojo(itensAgendamento);
    }
    public List<ItensAgendamento> getItensAgendamentos() {
        return getPureList(ItensAgendamento.class, "from ItensAgendamento ar");
    }
}