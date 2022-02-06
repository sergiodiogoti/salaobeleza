package com.Converters;


import com.DAO.AgendamentoDAO;
import com.Bean.Agendamento;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value="AgendamentoConverter")
public class AgendamentoConverter  implements Converter{

    private AgendamentoDAO agendamentoDAO =new AgendamentoDAO();
    public AgendamentoConverter(){
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       if (value == null || value.length() == 0) {
            return null;
        }else{
          try{ 
              Integer idagendamento = Integer.parseInt(value);
              return agendamentoDAO.getAgendamento(idagendamento);
          }catch(NumberFormatException n){
          return null;    
          }
       }  
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        return ((Agendamento)value).getAgeCodigo().toString();
    }

}
