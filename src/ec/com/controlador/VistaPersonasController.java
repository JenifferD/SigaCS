
package ec.com.controlador;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ec.com.modelo.CsAfiliado;
import ec.com.modelo.CsAfiliadoDAO;
import ec.com.modelo.CsCargaFamiliar;
import ec.com.modelo.CsCargaFamiliarDAO;
import ec.com.modelo.CsPersona;
import ec.com.modelo.DetParametrica;
import ec.com.modelo.DetParametricaDAO;
import ec.com.modelo.PersonaDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class VistaPersonasController {
	@FXML private TextField txt_codigo;
	@FXML private TextField txt_cedula;
	@FXML private TextField txt_nombre;
	@FXML private TextField txt_apellido;
	@FXML private TextField txt_direccion;
	@FXML private TextField txt_telefono;
	@FXML private TextField txt_nacimiento;
	@FXML private TextField txt_trabajo;
	@FXML private DatePicker txt_fecha;
	@FXML private TextField txt_edad;
	@FXML private CheckBox ch_estado_carga;
	@FXML private ComboBox<DetParametrica> cb_estado;
	@FXML private ComboBox<DetParametrica> cb_genero;
	@FXML private ComboBox<DetParametrica> cb_profesion;
	@FXML private ComboBox<DetParametrica> cb_barrio;
	@FXML private ComboBox<DetParametrica> cb_educacion;
	@FXML private ComboBox<DetParametrica> cb_parentesco;
	@FXML private Button bt_guardar;
	@FXML private Button bt_nuevo;
	@FXML private Button bt_examinar;
	@FXML private Button bt_quitar;
	@FXML private ImageView iv_foto;
	
	ControllerHelper helper = new ControllerHelper();
	DetParametricaDAO detparametricaDAO = new DetParametricaDAO();
	List<DetParametrica> datosTablaParametrica = detparametricaDAO.getListAll();
	DetParametrica seleccion = new DetParametrica();
	PersonaDAO personaDAO = new PersonaDAO();
	
	LocalDate hoy = LocalDate.now();
	CsAfiliadoDAO afiliadoDAO = new CsAfiliadoDAO();
	int idPersonaSeleccionada;
	
	public void initialize(){
		int maxLength = 10;
		llenarComboBarrios();
		llenarComboEstadosCiviles();
		llenarComboGeneros();
		llenarComboNiveles();
		llenarComboProfesion();
		llenarComboParentesco();
		ch_estado_carga.setSelected(true);
		
		txt_cedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txt_cedula.setText(oldValue);
				}
			}
		});
	
		//valida que solo se ingresen numeros 
		txt_telefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txt_telefono.setText(oldValue);
				}
			}
		});
        
		//valida que solo se ingresen 10 numeros en el campo telefono
		txt_telefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
		public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txt_telefono.getText().length() > maxLength) {
					String s = txt_telefono.getText().substring(0, maxLength);
					txt_telefono.setText(s);
				}
			}
		 });
				
		//valida que solo se ingresen 10 numeros en el campo cedula
		txt_cedula.textProperty().addListener(new ChangeListener<String>() {
		@Override
		public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txt_cedula.getText().length() > maxLength) {
					String s = txt_cedula.getText().substring(0, maxLength);
					txt_cedula.setText(s);
				}
			}
		});
				
		if (Context.getInstance().getCargaFamiliares() != null) {
			
			CsCargaFamiliar cargaFamiliarSeleccionada = Context.getInstance().getCargaFamiliares();
			CsPersona PersonaSeleccionada = Context.getInstance().getCargaFamiliares().getCsPersona();
			
			txt_cedula.setEditable(false);
			
			for(DetParametrica dato : datosTablaParametrica) {
    			if(dato.getDetpId().equals(cargaFamiliarSeleccionada.getPgParent())) {
    				seleccion = dato;
    			}
    		}
            cb_parentesco.getSelectionModel().select(seleccion);
            if(cargaFamiliarSeleccionada.getCargEstado().equals("A")) 
				ch_estado_carga.setSelected(true);
			else
				ch_estado_carga.setSelected(false);
			recuperarDatos(PersonaSeleccionada);	
		}
		else
		{
			//txt_codigo_afiliado.setText(Context.getInstance().getAfiliado());
			//txt_codigo.setText("0");
		}
	}
	
	private void llenarComboBarrios(){
		try{
			cb_barrio.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaBarrios = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("Bar01"))
					listaBarrios.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaBarrios);
			cb_barrio.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private void llenarComboGeneros(){
		try{
			cb_genero.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaGeneros = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("Gen01"))
					listaGeneros.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaGeneros);
			cb_genero.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private void llenarComboNiveles(){
		try{
			cb_educacion.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaNiveles = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("Niv01"))
					listaNiveles.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaNiveles);
			cb_educacion.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private void llenarComboProfesion(){
		try{
			cb_profesion.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaProfesion = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("Pro01"))
					listaProfesion.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaProfesion);
			cb_profesion.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	private void llenarComboEstadosCiviles(){
		try{
			cb_estado.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaEstadoCiviles = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("EsC01"))
					listaEstadoCiviles.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaEstadoCiviles);
			cb_estado.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	
	private void llenarComboParentesco(){
		try{
			cb_parentesco.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaParentesco = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("Par01"))
					listaParentesco.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaParentesco);
			cb_parentesco.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	void recuperarDatos(CsPersona PersonaSeleccionada ) {
		try {
			idPersonaSeleccionada = PersonaSeleccionada.getPersId();
			txt_codigo.setText(String.valueOf(PersonaSeleccionada.getCsCargaFamiliars().get(0).getCargId()));
			txt_cedula.setText(PersonaSeleccionada.getPersCedula());
			txt_apellido.setText(PersonaSeleccionada.getPersApellidos());
			txt_nombre.setText(PersonaSeleccionada.getPersNombres());
			txt_fecha.setValue(PersonaSeleccionada.getPersFechNac().toLocalDateTime().toLocalDate());
			txt_edad.setText(String.valueOf(PersonaSeleccionada.getPersEdad()));
			txt_nacimiento.setText(PersonaSeleccionada.getPersLugarNac());
			txt_direccion.setText(PersonaSeleccionada.getPersDireccion());
			txt_telefono.setText(PersonaSeleccionada.getPersTelefono());
			txt_trabajo.setText(PersonaSeleccionada.getPersLugarTrabajo());
            
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(PersonaSeleccionada.getPgGenero()))
					seleccion = dato;
			}
            cb_genero.getSelectionModel().select(seleccion);
            
            for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(PersonaSeleccionada.getPgBarrio()))
					seleccion = dato;
			}
            cb_barrio.getSelectionModel().select(seleccion);
            
            for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(PersonaSeleccionada.getPgNivEdu()))
					seleccion = dato;
			}
            cb_educacion.getSelectionModel().select(seleccion);
			
            for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(PersonaSeleccionada.getPgEstCivil()))
					seleccion = dato;
			}
            cb_estado.getSelectionModel().select(seleccion);
			
            for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(PersonaSeleccionada.getPgProfesion()))
					seleccion = dato;
			}
            cb_profesion.getSelectionModel().select(seleccion);
			
            
			if(PersonaSeleccionada.getPersFoto() != null) {
				String imgString = new String(PersonaSeleccionada.getPersFoto(), "UTF-8");
				iv_foto.setImage(helper.getImageFromBase64String(imgString).getImage());
			}
			else {
				Image img = new Image("/usuario.png");
				iv_foto.setImage(img);
			}
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public void quitar() {
		Image img = new Image("/usuario.png");
		iv_foto.setImage(img);
		bt_quitar.setDisable(true);
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
				iv_foto.setImage(image);
			}
			bt_quitar.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() throws IOException{
		limpiar();
	}
	
	void limpiar() {
		txt_codigo.setText("");
		txt_codigo.setEditable(false);
		txt_cedula.setText("");
		txt_nombre.setText("");
		txt_apellido.setText("");
		ch_estado_carga.setSelected(true);
		txt_telefono.setText("");
		txt_direccion.setText("");
		txt_trabajo.setText("");
		txt_nacimiento.setText("");
		txt_edad.setText("");
		txt_fecha.setValue(null);
		Image img = new Image("/usuario.png");
		iv_foto.setImage(img);
		txt_cedula.requestFocus();
		bt_quitar.setDisable(true);
		cb_genero.getSelectionModel().clearSelection();
		cb_barrio.getSelectionModel().clearSelection();
		cb_educacion.getSelectionModel().clearSelection();
		cb_estado.getSelectionModel().clearSelection();
		cb_profesion.getSelectionModel().clearSelection();
		cb_parentesco.getSelectionModel().clearSelection();
		txt_cedula.setEditable(true);
	}
	
	
	
	boolean validarDatos() {
		try {
			if(txt_cedula.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar cedula del Usuario", Context.getInstance().getStage());
				txt_cedula.requestFocus();
				return false;
			}
			
			if(txt_nombre.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txt_nombre.requestFocus();
				return false;
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public void grabar(){
		try {
			String estado;

			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				
				if(txt_codigo.getText().equals("")) 
				{
					CsAfiliado afiliadoRecuperado = new CsAfiliado();
					afiliadoRecuperado = Context.getInstance().getAfiliado();
					CsCargaFamiliar cargarFamiliarGrabar = new CsCargaFamiliar();//objeto principal
					cargarFamiliarGrabar.setCargId(null);
					cargarFamiliarGrabar.setPgParent(cb_parentesco.getSelectionModel().getSelectedItem().getDetpId());
					if(ch_estado_carga.isSelected() == true)
						estado = "A";
					else
						estado = "I";
					cargarFamiliarGrabar.setCargEstado(estado);
					
					CsPersona csPersona = new CsPersona();
					
					csPersona.setPersCedula(txt_cedula.getText());
					csPersona.setPersNombres(txt_nombre.getText());
					csPersona.setPersApellidos(txt_apellido.getText());
					csPersona.setPersLugarNac(txt_nacimiento.getText());
					csPersona.setPersLugarTrabajo(txt_trabajo.getText());
					csPersona.setPersDireccion(txt_direccion.getText());
					csPersona.setPersTelefono(txt_telefono.getText());
					csPersona.setPersEdad(Integer.parseInt(txt_edad.getText()));
					csPersona.setPersFechNac(Timestamp.valueOf(txt_fecha.getValue().atStartOfDay()));		
					csPersona.setPgBarrio(cb_barrio.getSelectionModel().getSelectedItem().getDetpId());
					csPersona.setPgEstCivil(cb_estado.getSelectionModel().getSelectedItem().getDetpId());
					csPersona.setPgGenero(cb_genero.getSelectionModel().getSelectedItem().getDetpId());
					csPersona.setPgNivEdu(cb_educacion.getSelectionModel().getSelectedItem().getDetpId());
					csPersona.setPgProfesion(cb_profesion.getSelectionModel().getSelectedItem().getDetpId());
					csPersona.setPersFoto(helper.encodeFileToBase64Binary(iv_foto.getImage()).getBytes());
					csPersona.setPersEstado("A");
					
					System.out.println("entra grabar nuevo");
					cargarFamiliarGrabar.setCargId(null);
					cargarFamiliarGrabar.setCsAfiliado(null);
					cargarFamiliarGrabar.setCsPersona(csPersona);
					cargarFamiliarGrabar.setCsAfiliado(afiliadoRecuperado);
					
					List<CsCargaFamiliar> cargaF = new ArrayList<CsCargaFamiliar>();
					cargaF.add(cargarFamiliarGrabar);
					csPersona.setCsCargaFamiliars(cargaF);
					afiliadoRecuperado.setCsCargaFamiliars(cargaF);
					afiliadoDAO.getEntityManager().getTransaction().begin();
					afiliadoDAO.getEntityManager().merge(afiliadoRecuperado);
					afiliadoDAO.getEntityManager().getTransaction().commit();
				}else {
					CsCargaFamiliar cargarFamiliarGrabar = new CsCargaFamiliar();//objeto principal
					cargarFamiliarGrabar = Context.getInstance().getCargaFamiliares();
					Context.getInstance().setCargaFamiliares(null);
					cargarFamiliarGrabar.getCsPersona().setPersCedula(txt_cedula.getText());
					cargarFamiliarGrabar.getCsPersona().setPersNombres(txt_nombre.getText());
					cargarFamiliarGrabar.getCsPersona().setPersApellidos(txt_apellido.getText());
					cargarFamiliarGrabar.getCsPersona().setPersLugarNac(txt_nacimiento.getText());
					cargarFamiliarGrabar.getCsPersona().setPersLugarTrabajo(txt_trabajo.getText());
					cargarFamiliarGrabar.getCsPersona().setPersDireccion(txt_direccion.getText());
					cargarFamiliarGrabar.getCsPersona().setPersTelefono(txt_telefono.getText());
					cargarFamiliarGrabar.getCsPersona().setPersEdad(Integer.parseInt(txt_edad.getText()));
					cargarFamiliarGrabar.getCsPersona().setPersFechNac(Timestamp.valueOf(txt_fecha.getValue().atStartOfDay()));		
					cargarFamiliarGrabar.getCsPersona().setPgBarrio(cb_barrio.getSelectionModel().getSelectedItem().getDetpId());
					cargarFamiliarGrabar.getCsPersona().setPgEstCivil(cb_estado.getSelectionModel().getSelectedItem().getDetpId());
					cargarFamiliarGrabar.getCsPersona().setPgGenero(cb_genero.getSelectionModel().getSelectedItem().getDetpId());
					cargarFamiliarGrabar.getCsPersona().setPgNivEdu(cb_educacion.getSelectionModel().getSelectedItem().getDetpId());
					cargarFamiliarGrabar.getCsPersona().setPgProfesion(cb_profesion.getSelectionModel().getSelectedItem().getDetpId());
					cargarFamiliarGrabar.getCsPersona().setPersFoto(helper.encodeFileToBase64Binary(iv_foto.getImage()).getBytes());
					cargarFamiliarGrabar.getCsPersona().setPersEstado("A");
					System.out.println(cargarFamiliarGrabar.getCsAfiliado().getAfilId());
					//System.out.println("carga: " + cargarFamiliarGrabar);
					afiliadoDAO.getEntityManager().getTransaction().begin();
					afiliadoDAO.getEntityManager().merge(cargarFamiliarGrabar.getCsPersona());
					afiliadoDAO.getEntityManager().getTransaction().commit();
				}
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			afiliadoDAO.getEntityManager().getTransaction().rollback();
		    System.out.println(ex.getMessage());
		}
	}
}

