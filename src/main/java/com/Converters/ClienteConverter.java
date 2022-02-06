package com.Converters;


import com.DAO.ClienteDAO;
import com.Bean.Cliente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value="ClienteConverter")
public class ClienteConverter  implements Converter{

    private ClienteDAO clienteDAO =new ClienteDAO();
    public ClienteConverter(){
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       if (value == null || value.length() == 0) {
            return null;
        }else{
          try{ 
              Integer idcliente = Integer.parseInt(value);
              return clienteDAO.getCliente(idcliente);
          }catch(NumberFormatException n){
          return null;    
          }
       }  
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "";
        }
        return ((Cliente)value).getCliCodigo().toString();
    }

}
