package com.DAO;
import com.Bean.Cliente;
import com.Bean.Usuario;
import com.Util.HibernateUtil;
import com.Util.MensagensErros;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.faces.context.FacesContext;

/**
 *
 * @author Sergio
 */
public class UsuarioDAO extends GenericDAO {
    private static final long serialVersionUID = 1L;
    private Session session;
	
    public UsuarioDAO(Session session) {
        this.session = session;
    }

    public UsuarioDAO() {
        this.session = getSession();
    }
    public int addUsuario(Usuario usuario) {
        setAtualizar(false);
            saveOrUpdatePojo(usuario);
            if (MensagensErros.isErro()) {
                return 0;
            } else {
                return usuario.getUsuCodigo();
            }   
    }
    public Usuario getUsuario(int usuarioID) {
        return getPojo(Usuario.class, usuarioID);
    }
    public void updateUsuario(Usuario usuario) {
        setAtualizar(true);
        saveOrUpdatePojo(usuario);
    }
    public void removeUsuario(Usuario usuario) {
        removePojo(usuario);
    }
    //verifica se existe algum usuario cadastrado no banco com o email passado no parametro.
    public boolean existeUsuario(String campo, String parametro) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String clausula = "true";
            if("email".equals(parametro)) {
                clausula = "where usuEmail LIKE '%" + campo + "%'";
            }
            if("login".equals(parametro)) {
                clausula = "where usuLogin LIKE '%" + campo + "%'";
            }

            String sql = "SELECT * FROM usuario where " + clausula + "%'";
            Query q = ses.createNativeQuery(sql).addEntity(Usuario.class);
            if (q.list().size() > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //busca usuarios, caso foi preenchido o parametro de busca na tela, busca pelo parametro, se não busca todos os usuarios cadastrados.
    public List<Usuario> getUsuarios(String param) {
        try (Session ses = HibernateUtil.getSessionFactory().openSession()) {
            String sql;
            String clausula = "true";
            if (param != null) {
                clausula = "usuNome LIKE '%" + param + "%'\n" +
                        "OR (usuTelefone  LIKE '%" + param + "%')\n" +
                        "OR (usuDataNasc  LIKE '%" + param + "%')";
            }
            sql = "SELECT * FROM usuario WHERE " + clausula + " ORDER BY usuLogin";
            Query q = ses.createNativeQuery(sql).addEntity(Usuario.class);
            if (!q.list().isEmpty())
                return (List<Usuario>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    //validando o usuario
    public Usuario usuarioValidado(String email, String pass) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try{
            String sql = "SELECT * FROM usuario " +
                    "WHERE usuEmail LIKE '%" + email + "%' OR (usuLogin  LIKE '%" + email + "%')\n" +
                    "AND usuSenha = '" + pass + "' LIMIT 1";
            Query q = ses.createNativeQuery(sql).addEntity(Usuario.class);
            if (!q.list().isEmpty()) {
                Usuario usuario = (Usuario) q.uniqueResult();
                return usuario;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ses.close();
        }
        return null;
    }
}