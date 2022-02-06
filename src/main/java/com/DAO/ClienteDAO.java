package com.DAO;

import com.Bean.Cliente;
import com.Bean.Usuario;
import com.ManagedBeans.UsuarioFaces;
import com.Util.HibernateUtil;
import com.Util.MensagensErros;

import java.util.ArrayList;
import java.util.List;

import com.Util.UtilFaces;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * @author Sergio
 */
public class ClienteDAO extends GenericDAO {
    private static final long serialVersionUID = 1L;
    private final Session session;

    public ClienteDAO(Session session) {
        this.session = session;
    }

    public ClienteDAO() {
        this.session = getSession();
    }

    public int addCliente(Cliente cliente) {
        setAtualizar(false);
        saveOrUpdatePojo(cliente);
        if (MensagensErros.isErro()) {
            return 0;
        } else {
            return cliente.getCliCodigo();
        }
    }

    public Cliente getCliente(int clienteID) {
        return getPojo(Cliente.class, clienteID);
    }

    public void updateCliente(Cliente cliente) {
        setAtualizar(true);
        saveOrUpdatePojo(cliente);
    }

    public void removeCliente(Cliente cliente) {
        removePojo(cliente);
    }

    //busca clientes, caso foi preenchido o parametro de busca na tela, busca pelo parametro, se não busca todos os clientes cadastrados.
    public List<Cliente> getClientes(String param) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql;
            String clausulaParam = "true";
            String clausulaCliente = "true";
            if (param != null) {
                clausulaParam = "cliNome LIKE '%" + param + "%'\n" +
                        "OR (cliCPF  LIKE '%" + param + "%')\n" +
                        "OR (cliCelular  LIKE '%" + param + "%')";
            }
            UsuarioFaces usuarioFaces = UtilFaces.getManagedBean(UsuarioFaces.class);
            Usuario usuario = usuarioFaces.getUsuarioLogadoBean();
            Cliente cliente = usuarioFaces.getUsuarioLogadoBean().getUsuCliCodigo();
            if("EXTERNO".equals(usuario.getUsuTipo())){
                if(null != cliente)
                clausulaCliente = "cliCodigo = "+usuarioFaces.getUsuarioLogadoBean().getUsuCliCodigo().getCliCodigo();
            }else{

            }
            sql = "SELECT * FROM cliente " +
                  "WHERE " + clausulaCliente +"\n" +
                  "AND "+ clausulaParam + " ORDER BY CliNome";
            Query q = ses.createNativeQuery(sql).addEntity(Cliente.class);
            if (!q.list().isEmpty())
                return (List<Cliente>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //verifica se existe algum cliente cadastrado no banco com o cpf passado no parametro.
    public boolean existeCliente(String cpf) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM cliente where cliCPF LIKE '%" + cpf + "%'";
            Query q = ses.createNativeQuery(sql).addEntity(Cliente.class);
            if (q.list().size() > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}