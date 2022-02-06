package com.Converters;


import com.DAO.HorarioTrabalhoDAO;
import com.Bean.HorarioTrabalho;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value="HorarioTrabalhoConverter")
public class HorarioTrabalhoConverter  implements Converter{

    private HorarioTrabalhoDAO horarioTrabalhoDAO =new HorarioTrabalhoDAO();
    public HorarioTrabalhoConverter(){
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       if (value == null || value.length() == 0) {
            return null;
        }else{
          try{ 
              Integer idhorarioTrabalho = Integer.parseInt(value);
              return horarioTrabalhoDAO.getHorarioTrabalho(idhorarioTrabalho);
          }catch(NumberFormatException n){
          return null;    
          }
       }  
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        return ((HorarioTrabalho)value).getHorTraCodigo().toString();
    }

}
