package com.Converters;


import com.DAO.ItensAgendamentoDAO;
import com.Bean.ItensAgendamento;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value="ItensAgendamentoConverter")
public class ItensAgendamentoConverter  implements Converter{

    private ItensAgendamentoDAO itensAgendamentoDAO =new ItensAgendamentoDAO();
    public ItensAgendamentoConverter(){
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       if (value == null || value.length() == 0) {
            return null;
        }else{
          try{ 
              Integer iditensAgendamento = Integer.parseInt(value);
              return itensAgendamentoDAO.getItensAgendamento(iditensAgendamento);
          }catch(NumberFormatException n){
          return null;    
          }
       }  
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        return ((ItensAgendamento)value).getItAgeCodigo().toString();
    }

}
