/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Conexao.ConexaoMySQL;
import org.apache.commons.io.IOUtils;


/**
 *
 * @author Sergio
 */
public class CriarBeanDaoFaces {

    //    colocar no array o nome das tabelas criadas no banco: os nome deverão ser os mesmos do banco de dados,
//    porem deve-se colocar as letras maiusculas no seus lugaress.
//    =  que devera ser alterado para @ManyToOne.
//    Após colocar todos os parametros, pressionar Shift+F6 para executar o arquivo
    private static String[] tabelas = new String[]{"ItensAgendamento"};
    private static List<String[]> campos = new ArrayList<String[]>();
    private static List<String[]> camposFK = new ArrayList<String[]>();
    private static String caminhoBean = "D:\\Web\\SalaoBeleza\\src\\main\\java\\com\\Bean\\";
    private static String caminhoDao = "D:\\Web\\SalaoBeleza\\src\\main\\java\\com\\DAO\\";
    private static String caminhoFaces = "D:\\Web\\SalaoBeleza\\src\\main\\java\\com\\ManagedBeans\\";
    private static String caminhoConverter = "D:\\Web\\SalaoBeleza\\src\\main\\java\\com\\Converters\\";
    private static String caminhoHibernateUtil = "D:\\Web\\SalaoBeleza\\src\\main\\java\\com\\Util\\HibernateUtil.java";
    private static String autor = "Sergio";

    public static void main(String[] args) throws IOException {
        Connection connection = com.Conexao.ConexaoMySQL.getConexaoMySQL();
        try {
            Statement statement = connection.createStatement();
            ResultSet results = null;
            String sql = "";
            for (String tabela : tabelas) {
                results = statement.executeQuery("desc " + tabela.toLowerCase());
                while (results.next()) {
                    campos.add(new String[]{results.getString(1), results.getString(2)});
                    if("MUL".equals(results.getString(4)))
                    camposFK.add(new String[]{results.getString(1), results.getString(2)});
                }
                createBean(tabela, campos,camposFK,connection);
                criarDAO(tabela, campos);
                createFaces(tabela, campos);
                createConverter(tabela, campos);
                campos = new ArrayList<String[]>();
                camposFK = new ArrayList<String[]>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void createBean(String tabela, List<String[]> campos, List<String[]> camposFK, Connection con) throws IOException {
        String getSet = "";
        String font = "package com.Bean;\n"
                + "\n"
                + "import java.util.Date;\n"
                + "import java.util.List;\n"
                + "import javax.persistence.*;\n"
                + "/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */\n";
        font += "\n@Entity\n"
                + "@Table(name = \"" + tabela.toLowerCase() + "\")";
        font += "\n";
        font += "public class " + tabela + " implements java.io.Serializable {";
        font += "\n";
        font += "private static final long serialVersionUID = 1L;";
        font += "\n";
        font += "\n";
        int index = 0;
        String charUpper = "";
        boolean existeFK = false;
        for (String[] campo : campos) {
            charUpper = campo[0].substring(0, 1).toUpperCase() + campo[0].substring(1);
            if (index == 0) {
                font += "@Id\n"
                        + "    @GeneratedValue(strategy = GenerationType.IDENTITY)\n"
                        + "    @Column(name=\"" + campo[0] + "\", nullable=false)\n"
                        + "    private Integer " + campo[0] + ";";

                getSet = "\n\n";

                getSet += "public Integer get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Integer " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";

                index++;
                continue;
            }
            font += "\n";
            font += "\n";

            existeFK = false;
            for (String[] campoFK : camposFK) {
                if(campoFK[0].equals(campo[0])){
                    String tabelaFK = "", colunaFK = "";
                    try{
                        Statement statement = con.createStatement();
                        ResultSet resultSet = null;
                        String sql = "SELECT\n" +
                                "   CONCAT(UPPER(LEFT(referenced_table_name, 1)),SUBSTRING(referenced_table_name, 2))AS tabela_origem, \n" +
                                "   referenced_column_name AS coluna_origem\n" +
                                "FROM information_schema.KEY_COLUMN_USAGE\n" +
                                "WHERE column_name = '"+ campoFK[0] +"'";
                        resultSet = statement.executeQuery(sql);
                        if(resultSet.next()){
                            tabelaFK = resultSet.getString(1);
                            colunaFK = resultSet.getString(2);
                            existeFK = true;
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                    font += "@ManyToOne\n";
                    font += "@JoinColumn(name=\"" + campo[0] + "\",referencedColumnName=\"" + colunaFK + "\",nullable=true)\n"
                            + "    private "+ tabelaFK +" " + campo[0] + ";";

                    getSet += "\n\n";

                    getSet += "public "+ tabelaFK +" get" + charUpper + "() {\n"
                            + "        return " + campo[0] + ";\n"
                            + "    }\n"
                            + "\n"
                            + "    public void set" + charUpper + "("+ tabelaFK +" " + campo[0] + ") {\n"
                            + "        this." + campo[0] + " = " + campo[0] + ";\n"
                            + "    }";
                }
            }
            if(existeFK){
                index++;
                continue;
            }
            font += "\n";
            font += "\n";
            if (campo[1].equals("int(11)")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    private Integer " + campo[0] + ";";

                getSet += "\n\n";

                getSet += "public Integer get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Integer " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";

            } else if (campo[1].contains("char")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    private String " + campo[0] + ";";
                getSet += "\n\n";

                getSet += "public String get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(String " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";

            } else if (campo[1].contains("tinyint")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    private Boolean " + campo[0] + ";";

                getSet += "\n\n";

                getSet += "public Boolean is" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Boolean " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";

            } else if (campo[1].contains("double")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    private Double " + campo[0] + ";";

                getSet += "\n\n";

                getSet += "public Double get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Double " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";
            } else if (campo[1].contains("decimal")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    private Double " + campo[0] + ";";

                getSet += "\n\n";

                getSet += "public Double get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Double " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";
            } else if (campo[1].equals("datetime")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    @Temporal(TemporalType.TIMESTAMP)\n"
                        + "    private Date " + campo[0] + ";";

                getSet += "\n\n";

                getSet += "public Date get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Date " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";
            } else if (campo[1].equals("time")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    private String " + campo[0] + ";";

                getSet += "\n\n";

                getSet += "public String get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(String " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";
            } else if (campo[1].equals("date")) {
                font += "@Column(name = \"" + campo[0] + "\")\n"
                        + "    @Temporal(TemporalType.DATE)\n"
                        + "    private Date " + campo[0] + ";";
                getSet += "\n\n";

                getSet += "public Date get" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Date " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";
            } else if (campo[1].equals("boolean")) {
                font += "@Column(name=\"" + campo[0] + "\")\n"
                        + "    private Boolean " + campo[0] + ";";
                getSet += "\n\n";

                getSet += "public Boolean is" + charUpper + "() {\n"
                        + "        return " + campo[0] + ";\n"
                        + "    }\n"
                        + "\n"
                        + "    public void set" + charUpper + "(Boolean " + campo[0] + ") {\n"
                        + "        this." + campo[0] + " = " + campo[0] + ";\n"
                        + "    }";
            }
//            System.out.println(campo[0].substring(0, 1).toUpperCase() + campo[0].substring(1));
            index++;
        }

        font += "\n";
        font += "\n";
        font += "public " + tabela + "() {\n"
                + "        setDataHoraAtualizacao(new Date());\n"
                + "    }";
        font += "\n";
        font += "\n";


        font += getSet;

        font += "\n";
        font += "@Override\n"
                + "    public boolean equals(Object obj) {\n"
                + "        if (obj == null) {\n"
                + "            return false;\n"
                + "        }\n"
                + "        if (getClass() != obj.getClass()) {\n"
                + "            return false;\n"
                + "        }\n"
                + "        final " + tabela + " other = (" + tabela + ") obj;\n"
                + "        if (this." + campos.get(0)[0] + " != other." + campos.get(0)[0] + " && (this." + campos.get(0)[0] + " == null || !this." + campos.get(0)[0] + ".equals(other." + campos.get(0)[0] + "))) {\n"
                + "            return false;\n"
                + "        }\n"
                + "        return true;\n"
                + "    }";

        font += "\n";
        font += "@Override\n"
                + "    public int hashCode() {\n"
                + "        int hash = 5;\n"
                + "        hash = 41 * hash + (this." + campos.get(0)[0] + " != null ? this." + campos.get(0)[0] + ".hashCode() : 0);\n"
                + "        return hash;\n"
                + "    }";

        font += "\n";
        font += "@Override\n"
                + "  public String toString(){\n"
                + "    return " + campos.get(0)[0] + "+\"\";\n"
                + "  }";
        font += "\n";
        font += "}";

        PrintWriter pw = null;
        String pathFile = caminhoBean + tabela + ".java";
        pw = new PrintWriter(new FileWriter(pathFile));
        pw.write(font);
        pw.close();

        FileInputStream inputStream = new FileInputStream(caminhoHibernateUtil);
        String everything = IOUtils.toString(inputStream);

        everything = everything.replace(".buildSessionFactory();", ".addAnnotatedClass(" + tabela + ".class)\n.buildSessionFactory();");

        pathFile = caminhoHibernateUtil;
        pw = new PrintWriter(new FileWriter(pathFile));
        pw.write(everything);
        pw.close();

    }

    public static void criarDAO(String tabela, List<String[]> campos) throws IOException {
        String tabelaLow = tabela.substring(0, 1).toLowerCase() + tabela.substring(1);
        String charUpper = campos.get(0)[0].substring(0, 1).toUpperCase() + campos.get(0)[0].substring(1);
        String font = "package com.DAO;\n"
                + "import com.Bean." + tabela + ";\n"
                + "import com.Util.MensagensErros;\n"
                + "import java.util.List;\n"
                + "import org.hibernate.Session;\n"
                + "\n"
                + "/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */\n"
                + "public class " + tabela + "DAO extends GenericDAO {\n"
                + "    private static final long serialVersionUID = 1L;\n"
                + "    private Session session;\n"
                + "	\n"
                + "    public " + tabela + "DAO(Session session) {\n"
                + "        this.session = session;\n"
                + "    }\n"
                + "\n"
                + "    public " + tabela + "DAO() {\n"
                + "        this.session = getSession();\n"
                + "    }\n"
                + "    public int add" + tabela + "(" + tabela + " " + tabelaLow + ") {\n"
                + "        setAtualizar(false);\n"
                + "            saveOrUpdatePojo(" + tabelaLow + ");\n"
                + "            if (MensagensErros.isErro()) {\n"
                + "                return 0;\n"
                + "            } else {\n"
                + "                return " + tabelaLow + ".get" + charUpper + "();\n"
                + "            }   \n"
                + "    }\n"
                + "    public " + tabela + " get" + tabela + "(int " + tabelaLow + "ID) {\n"
                + "        return getPojo(" + tabela + ".class, " + tabelaLow + "ID);\n"
                + "    }\n"
                + "    public void update" + tabela + "(" + tabela + " " + tabelaLow + ") {\n"
                + "        setAtualizar(true);\n"
                + "        saveOrUpdatePojo(" + tabelaLow + ");\n"
                + "    }\n"
                + "    public void remove" + tabela + "(" + tabela + " " + tabelaLow + ") {\n"
                + "        removePojo(" + tabelaLow + ");\n"
                + "    }\n"
                + "    public List<" + tabela + "> get" + tabela + "s() {\n"
                + "        return getPureList(" + tabela + ".class, \"from " + tabela + " ar\");\n"
                + "    }\n"
                + "}";

//        System.out.println(font);
        PrintWriter pw = null;
        String pathFile = caminhoDao + tabela + "DAO.java";
//        String pathFile = "C:\\Web\\Elion\\src\\java\\com\\Bean\\" + tabela + ".java";
        pw = new PrintWriter(new FileWriter(pathFile));
        pw.write(font);
        pw.close();
    }

    public static void createFaces(String tabela, List<String[]> campos) throws IOException {
        String charUpper = campos.get(0)[0].substring(0, 1).toUpperCase() + campos.get(0)[0].substring(1);
        String tabelaLow = tabela.substring(0, 1).toLowerCase() + tabela.substring(1);
        String font = "package com.ManagedBeans;\n"
                + "\n"
                + "import com.Bean.Setor;\n"
                + "import com.DAO." + tabela + "DAO;\n"
                + "import com.Bean." + tabela + ";\n"
                + "import com.Bean.Usuario;\n"
                + "import com.DAO.SetorDAO;\n"
                + "import com.DAO.UsuarioDAO;\n"
                + "import java.util.Date;\n"
                + "import java.util.LinkedList;\n"
                + "import java.util.List;\n"
                + "import java.io.Serializable;\n"
                + "import javax.faces.context.FacesContext;\n"
                + "import javax.faces.model.SelectItem;\n"
                + "import com.Util.MensagensErros;\n"
                + "import javax.inject.Named;\n"
                + "import javax.enterprise.context.SessionScoped;\n"
                + "\n"
                + "/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */\n"
                + "@Named\n"
                + "@SessionScoped\n"
                + "public class " + tabela + "Faces implements Serializable{\n"
                + "\n"
                + "    private List<" + tabela + "> cached" + tabela + "s = null;\n"
                + "    private " + tabela + "DAO " + tabelaLow + "DAO = new " + tabela + "DAO();\n"
                + "    private " + tabela + " " + tabelaLow + "Selected;\n"
                + "    private Usuario usuario;\n"
                + "    private Setor setor;\n"
                + "\n"
                + "    public Setor getSetor() {\n"
                + "        return setor;\n"
                + "    }\n"
                + "\n"
                + "    public void setSetor(Setor setor) {\n"
                + "        this.setor = setor;\n"
                + "    }\n"
                + "\n"
                + "    public Usuario getUsuario() {\n"
                + "        return usuario;\n"
                + "    }\n"
                + "\n"
                + "    public void setUsuario(Usuario usuario) {\n"
                + "        this.usuario = usuario;\n"
                + "    }\n"
                + "\n"
                + "    public " + tabela + "DAO get" + tabela + "DAO() {\n"
                + "        return " + tabelaLow + "DAO;\n"
                + "    }\n"
                + "\n"
                + "    public void set" + tabela + "DAO(" + tabela + "DAO " + tabelaLow + "DAO) {\n"
                + "        this." + tabelaLow + "DAO = " + tabelaLow + "DAO;\n"
                + "    }\n"
                + "\n"
                + "    public " + tabela + " get" + tabela + "Selected() {\n"
                + "        return " + tabelaLow + "Selected;\n"
                + "    }\n"
                + "\n"
                + "    public void set" + tabela + "Selected(" + tabela + " " + tabelaLow + "Selected) {\n"
                + "        this." + tabelaLow + "Selected = " + tabelaLow + "Selected;\n"
                + "    }\n"
                + "\n"
                + "    public List<" + tabela + "> getCached" + tabela + "s() {\n"
                + "        if (cached" + tabela + "s == null) {\n"
                + "            this.cached" + tabela + "s = " + tabelaLow + "DAO.get" + tabela + "s();\n"
                + "        }\n"
                + "        return cached" + tabela + "s;\n"
                + "    }\n"
                + "\n"
                + "    public String doAdd" + tabela + "() {\n"
                + "        int setor = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(\"setor\").toString());\n"
                + "        int usuario = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(\"usuario\").toString());\n"
                + "        " + tabelaLow + "Selected = new " + tabela + "(setor, usuario);\n"
                + "        return \"" + tabelaLow + "New\";\n"
                + "    }\n"
                + "\n"
                + "    public String dofinishAdd" + tabela + "() {\n"
                + "        Date date = new Date();\n"
                + "        " + tabelaLow + "Selected.setDataHoraAtualizacao(date);\n"
                + "        " + tabelaLow + "DAO.add" + tabela + "(" + tabelaLow + "Selected);\n"
                + "        cached" + tabela + "s = null;\n"
                + "        if (MensagensErros.isErro()) {\n"
                + "            return \"\";\n"
                + "        } else {\n"
                + "            return \"" + tabelaLow + "List\";\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public String doRemove" + tabela + "() {\n"
                + "        " + tabelaLow + "DAO.remove" + tabela + "(" + tabelaLow + "Selected);\n"
                + "        cached" + tabela + "s = null;\n"
                + "        if (MensagensErros.isErro()) {\n"
                + "            return \"\";\n"
                + "        } else {\n"
                + "            return \"" + tabelaLow + "List\";\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public String doUpdate" + tabela + "() {\n"
                + "        UsuarioDAO usuDAO = new UsuarioDAO();\n"
                + "        usuario = usuDAO.getUsuario(" + tabelaLow + "Selected.getCodUsuario());\n"
                + "        SetorDAO posDAO = new SetorDAO();\n"
                + "        setor = posDAO.getSetor(" + tabelaLow + "Selected.getCodSetor());\n"
                + "        return \"" + tabelaLow + "Edit\";\n"
                + "    }\n"
                + "\n"
                + "    public String dofinishUpdate" + tabela + "() {\n"
                + "        Date date = new Date();\n"
                + "        int codUsuario = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(\"usuario\").toString());\n"
                + "        " + tabelaLow + "Selected.setCodUsuario(codUsuario);\n"
                + "        int codSetor = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(\"setor\").toString());\n"
                + "        " + tabelaLow + "Selected.setCodSetor(codSetor);\n"
                + "        " + tabelaLow + "Selected.setDataHoraAtualizacao(date);\n"
                + "        " + tabelaLow + "DAO.update" + tabela + "(" + tabelaLow + "Selected);\n"
                + "        cached" + tabela + "s = null;\n"
                + "        if (MensagensErros.isErro()) {\n"
                + "            return \"\";\n"
                + "        } else {\n"
                + "            return \"" + tabelaLow + "List\";\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public String doCancel" + tabela + "() {\n"
                + "        cached" + tabela + "s = null;\n"
                + "        return \"" + tabelaLow + "List\";\n"
                + "    }\n"
                + "}";

        PrintWriter pw = null;
        String pathFile = caminhoFaces + tabela + "Faces.java";
//        String pathFile = "C:\\Web\\Elion\\src\\java\\com\\Bean\\" + tabela + ".java";
        pw = new PrintWriter(new FileWriter(pathFile));
        pw.write(font);
        pw.close();
    }

    public static void createConverter(String tabela, List<String[]> campos) throws IOException {
        String charUpper = campos.get(0)[0].substring(0, 1).toUpperCase() + campos.get(0)[0].substring(1);
        String tabelaLow = tabela.substring(0, 1).toLowerCase() + tabela.substring(1);
        String font = "package com.Converters;\n"
                + "\n"
                + "\n"
                + "import com.DAO." + tabela + "DAO;\n"
                + "import com.Bean." + tabela + ";\n"
                + "import javax.faces.component.UIComponent;\n"
                + "import javax.faces.context.FacesContext;\n"
                + "import javax.faces.convert.Converter;\n"
                + "import javax.faces.convert.FacesConverter;\n"
                + "\n"
                + "/**\n"
                + " *\n"
                + " * @author " + autor + "\n"
                + " */\n"
                + "@FacesConverter(value=\"" + tabela + "Converter\")\n"
                + "public class " + tabela + "Converter  implements Converter{\n"
                + "\n"
                + "    private " + tabela + "DAO " + tabelaLow + "DAO =new " + tabela + "DAO();\n"
                + "    public " + tabela + "Converter(){\n"
                + "    }\n"
                + "\n"
                + "    public Object getAsObject(FacesContext context, UIComponent component, String value) {\n"
                + "       if (value == null || value.length() == 0) {\n"
                + "            return null;\n"
                + "        }else{\n"
                + "          try{ \n"
                + "              Integer id" + tabelaLow + " = Integer.parseInt(value);\n"
                + "              return " + tabelaLow + "DAO.get" + tabela + "(id" + tabelaLow + ");\n"
                + "          }catch(NumberFormatException n){\n"
                + "          return null;    \n"
                + "          }\n"
                + "       }  \n"
                + "    }\n"
                + "\n"
                + "    public String getAsString(FacesContext context, UIComponent component, Object value) {\n"
                + "        if(value == null){\n"
                + "            return \"\";\n"
                + "        }\n"
                + "        return ((" + tabela + ")value).get"+charUpper+"().toString();\n"
                + "    }\n"
                + "\n"
                + "}\n"
                + "";

        PrintWriter pw = null;
        String pathFile = caminhoConverter + tabela + "Converter.java";
        pw = new PrintWriter(new FileWriter(pathFile));
        pw.write(font);
        pw.close();
    }
}
