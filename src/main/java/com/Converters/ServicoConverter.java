package com.Converters;


import com.DAO.ServicoDAO;
import com.Bean.Servico;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value="ServicoConverter")
public class ServicoConverter  implements Converter{

    private ServicoDAO servicoDAO =new ServicoDAO();
    public ServicoConverter(){
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       if (value == null || value.length() == 0) {
            return null;
        }else{
          try{ 
              Integer idservico = Integer.parseInt(value);
              return servicoDAO.getServico(idservico);
          }catch(NumberFormatException n){
          return null;    
          }
       }  
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        return ((Servico)value).getSerCodigo().toString();
    }

}
