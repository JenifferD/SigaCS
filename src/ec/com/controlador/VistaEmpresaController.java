package ec.com.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import ec.com.modelo.Comuna;
import ec.com.modelo.ComunaDAO;
import ec.com.modelo.CsPersona;
import ec.com.modelo.DetParametrica;
import ec.com.modelo.Perfil;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class VistaEmpresaController {
	
	@FXML private TextField txt_codigo;
	@FXML private TextField txt_ruc;
	@FXML private TextField txt_razon;
	@FXML private TextField txt_descrip;
	@FXML private TextField txt_barrio;
	@FXML private CheckBox chkEstado;
	@FXML private TextField txt_direccion;
	@FXML private TextField txt_telefono;
	@FXML private TextField txt_email;
	@FXML private TextField txt_ruta;
	@FXML private ImageView iv_logo;
	@FXML private Button bt_guardar;
	@FXML private Button bt_nuevo;
	@FXML private Button bt_examinar;
	@FXML private Button bt_quitar;
	ControllerHelper helper = new ControllerHelper();
    ComunaDAO comunaDAO = new ComunaDAO();
    Comuna comuna = new Comuna();
    public void initialize()
    {
    	txt_ruc.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	recuperarDatos(txt_ruc.getText());
	            }
	            	
	        }
	    });
    }
    
	
	public void grabar()
	{
		try {
			String estado;
		
			if(validarDatos() == false){
				return;
			}
			if(chkEstado.isSelected() == true)
				estado = "A";
			else
				estado = "I";
			Comuna comuna = new Comuna();
			comuna.setComuEstado(estado);
			comuna.setComuRuc(txt_ruc.getText());
			comuna.setComuRazonSoc(txt_razon.getText());
			comuna.setComuDescrip(txt_descrip.getText());
			comuna.setComuBarrio(txt_barrio.getText());
			comuna.setComuDireccion(txt_direccion.getText());
			comuna.setComuTelefono(txt_telefono.getText());
			comuna.setComuEmail(txt_email.getText());
			
			comuna.setComuLogo(txt_ruta.getText());
			
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				comuna.setComuEstado(estado);
				
				comunaDAO.getEntityManager().getTransaction().begin();
				if(txt_codigo.getText().equals("")) {//inserta
					comuna.setComuId(null);
					comunaDAO.getEntityManager().persist(comuna);
				}else {//modifica
					comuna.setComuId(Integer.parseInt(txt_codigo.getText()));
					
					comunaDAO.getEntityManager().merge(comuna);
				}
				comunaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				limpiar();
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			comunaDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
		
	}
	
	public void nuevo() throws IOException{
		limpiar();
	}
	
	void limpiar() {
		txt_codigo.setEditable(false);
		txt_razon.setText("");
		txt_ruc.setText("");
		txt_descrip.setText("");
		txt_barrio.setText("");
		chkEstado.setSelected(false);
		txt_direccion.setText("");
		txt_email.setText("");
		txt_ruc.setText("");
		Image img = new Image("/logo.png");
		iv_logo.setImage(img);
		txt_ruc.requestFocus();
	}
	
	
	public void quitar() {
		Image img = new Image("/logo.png");
		iv_logo.setImage(img);
		
	}
	public void examinar(){
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Buscar Imagen");
			// Agregar filtros para facilitar la busqueda
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Imagen jpg", "*.jpg"),
					new FileChooser.ExtensionFilter("Imagen png", "*.png")
					);
			// Obtener la imagen seleccionada
			File imgFile = fileChooser.showOpenDialog(Context.getInstance().getStage());
			// Mostar la imagen
			if (imgFile != null) {
				Image image = new Image("file:" + imgFile.getAbsolutePath());
			    String ruta = imgFile.getAbsolutePath();
			    txt_ruta.setText(ruta);
			    iv_logo.setImage(image);
			}
			bt_quitar.setDisable(false);
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}

	boolean validarDatos() {
		try {
			if(txt_ruc.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar ruc de la comuna", Context.getInstance().getStage());
				txt_ruc.requestFocus();
				return false;
			}
			
			if(txt_razon.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar razon social de la comuna", Context.getInstance().getStage());
				txt_razon.requestFocus();
				return false;
			}
			if(txt_descrip.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar una descripcion de la comuna", Context.getInstance().getStage());
				txt_descrip.requestFocus();
				return false;	
			}
			if(txt_barrio.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el barrio", Context.getInstance().getStage());
				txt_barrio.requestFocus();
				return false;	
			}
			if(txt_direccion.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar la direccion de la comuna", Context.getInstance().getStage());
				txt_direccion.requestFocus();
				return false;	
			}
			if(txt_telefono.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el telefono de la comuna", Context.getInstance().getStage());
				txt_telefono.requestFocus();
				return false;	
			}
			if(txt_email.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el e-mail de la comuna", Context.getInstance().getStage());
				txt_email.requestFocus();
				return false;	
			}
			if(chkEstado.isSelected()== false) {
				helper.mostrarAlertaAdvertencia("Debe Seleccionar el estado", Context.getInstance().getStage());
				chkEstado.requestFocus();
				return false;	
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	
	void recuperarDatos(String ruc) {
		try {
			List<Comuna> listaComuna;
			listaComuna = comunaDAO.getMostrarComuna(ruc);
			
			if (txt_ruc.getText().equals(listaComuna.get(0).getComuRuc()));
			{
			txt_codigo.setText(listaComuna.get(0).getComuId().toString());
			}

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
}

	
