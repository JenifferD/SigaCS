package ec.com.controlador;


import java.io.IOException;
import java.util.Optional;

import ec.com.modelo.Usuario;
import ec.com.modelo.UsuarioDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import ec.com.util.Encriptado;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class VistaCambiarContrasenaController {
	
	@FXML private TextField txt_codigo;
	@FXML private PasswordField txt_cont_actual;
	@FXML private PasswordField txt_cont_nueva;
	@FXML private PasswordField txt_contr_conf;
	@FXML private CheckBox chk_mostrar1;
	@FXML private CheckBox chk_mostrar2;
	@FXML private CheckBox chk_mostrar;
	@FXML private Button bt_guardar;
	@FXML private Button bt_nuevo;
	
	Encriptado encriptado = new Encriptado();
	
	ControllerHelper helper = new ControllerHelper();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	Usuario usuarioLogeado = new Usuario();
	
	public void initialize(){
		
		try {
		txt_cont_actual.setText(encriptado.Desencriptar(Context.getInstance().getUsuarios().getUsuClave()));
		txt_codigo.setText(Integer.toString(Context.getInstance().getCodigo()));
		
		usuarioLogeado = Context.getInstance().getUsuarios();
		 }catch(Exception ex) {
				System.out.println(ex.getMessage());
		 }
		
		txt_cont_nueva.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	txt_contr_conf.requestFocus();
	            }
	        }
	    });
		
		
	}
	
	public void onEnter(ActionEvent ae)
	{
		if(txt_cont_nueva.getText().equals(txt_contr_conf.getText()))
		{
			System.out.println("Contraseñas iguales");	
		}
		else
		{
			helper.mostrarAlertaAdvertencia("Las contraseñas no coinciden", Context.getInstance().getStage()); ;
		}
	}
	
	
	public void grabar()
	{
		try{
		
		usuarioLogeado.setUsuClave(encriptado.Encriptar(txt_contr_conf.getText()));
		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
		if(result.get() == ButtonType.OK){
			
			usuarioDAO.getEntityManager().getTransaction().begin();
			usuarioDAO.getEntityManager().merge(usuarioLogeado);
			usuarioDAO.getEntityManager().getTransaction().commit();
			helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
		}
	
      }catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			usuarioDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() throws IOException{
		limpiar();
	}
	
	void limpiar() {
		txt_cont_nueva.setText("");
		txt_contr_conf.setText("");
	}
	
}
