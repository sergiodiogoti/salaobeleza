package com.Converters;


import com.DAO.ProfissionalDAO;
import com.Bean.Profissional;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value="ProfissionalConverter")
public class ProfissionalConverter  implements Converter{

    private ProfissionalDAO profissionalDAO =new ProfissionalDAO();
    public ProfissionalConverter(){
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       if (value == null || value.length() == 0) {
            return null;
        }else{
          try{ 
              Integer idprofissional = Integer.parseInt(value);
              return profissionalDAO.getProfissional(idprofissional);
          }catch(NumberFormatException n){
          return null;    
          }
       }  
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        return ((Profissional)value).getProCodigo().toString();
    }

}
