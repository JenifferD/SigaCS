package ec.com.controlador;


import java.util.List;
import java.util.Optional;

import ec.com.modelo.Usuario;
import ec.com.modelo.UsuarioDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import ec.com.util.Encriptado;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class VistaInicioSesionController {
	Tooltip toolTip;
	@FXML private Button bt_cancelar;
	@FXML private Button bt_entrar;
	@FXML private TextField txt_usuario;
	@FXML private PasswordField txt_contraseña;
	ControllerHelper helper = new ControllerHelper();
	Encriptado encriptado = new Encriptado();

	private Stage stage;
	
	public void setDialogStage(Stage stage) {
        this.stage = stage;
    }
	
	public void aceptar() {
	    if(validarDatos() == false)
			return;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		List<Usuario> usuario;
		usuario = usuarioDAO.getUsuario(txt_usuario.getText(),encriptado.Encriptar(txt_contraseña.getText()));
		for(int i = 0 ; i < usuario.size() ; i ++) {
			System.out.println(usuario.get(i).getUsuNombres());
		}
		if(usuario.size() == 1){
			Context.getInstance().setUsuarios(usuario.get(0));
			Context.getInstance().setPerfil(usuario.get(0).getPerfil().getPerNombre());
			Context.getInstance().setIdPerfil(usuario.get(0).getPerfil().getPerId());
			Context.getInstance().setUsuario(usuario.get(0).getUsuNombres()+ " " + usuario.get(0).getUsuApellidos());
			Context.getInstance().setClave(usuario.get(0).getUsuClave());
			Context.getInstance().setCodigo(usuario.get(0).getUsuId());
			helper.abrirPantallaPrincipal("Principal","/Principal/VistaPrincipal.fxml",stage);
		}
		else{
			helper.mostrarAlertaError("Clave o Usuario Incorrecto!!!",stage);
		}
	}
	
	boolean validarDatos() {
		boolean bandera = false;
		if(txt_usuario.getText().equals("")) {
			helper.mostrarAlertaAdvertencia("Debe de Ingresar Usuario", Context.getInstance().getStage());
			txt_usuario.requestFocus();
			return false;
		}
		if(txt_contraseña.getText().equals("")) {
			helper.mostrarAlertaAdvertencia("Debe de Ingresar Clave", Context.getInstance().getStage());
			txt_contraseña.requestFocus();
			return false;
		}
		bandera = true;
		return bandera;
	}
	
	public void cancelar(){
		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Realmente Desea Salir?",stage);
		if(result.get() == ButtonType.OK)
			System.exit(0);
	}
	public void initialize(){
		
		txt_usuario.requestFocus();
		toolTip = new Tooltip("Ingrese Nombre de usuario");
		txt_usuario.setTooltip(toolTip);
		toolTip = new Tooltip("Ingrese Clave del usuario");
		txt_contraseña.setTooltip(toolTip);
		toolTip = new Tooltip("Entrar");
		bt_entrar.setTooltip(toolTip);
		toolTip = new Tooltip("Cancelar");
		bt_cancelar.setTooltip(toolTip);
		
		txt_usuario.setText("jenny");
		txt_contraseña.setText("1234");
		
		txt_usuario.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
		            	txt_contraseña.requestFocus();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		
		txt_contraseña.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
						aceptar();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	            
	            }
	        }
	    });
		
		bt_entrar.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
						aceptar();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		
	}
}
