package com.Converters;


import com.DAO.UsuarioDAO;
import com.Bean.Usuario;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value="UsuarioConverter")
public class UsuarioConverter  implements Converter{

    private UsuarioDAO usuarioDAO =new UsuarioDAO();
    public UsuarioConverter(){
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       if (value == null || value.length() == 0) {
            return null;
        }else{
          try{ 
              Integer idusuario = Integer.parseInt(value);
              return usuarioDAO.getUsuario(idusuario);
          }catch(NumberFormatException n){
          return null;    
          }
       }  
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        return ((Usuario)value).getUsuCodigo().toString();
    }

}
