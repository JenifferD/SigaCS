package ec.com.controlador;


import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import ec.com.modelo.CsAfiliado;
import ec.com.modelo.CsAfiliadoDAO;
import ec.com.modelo.CsCargaFamiliar;
import ec.com.modelo.CsDocumento;
import ec.com.modelo.CsPersona;
import ec.com.modelo.CsVivienda;
import ec.com.modelo.DetParametrica;
import ec.com.modelo.DetParametricaDAO;
import ec.com.modelo.PersonaDAO;
import ec.com.modelo.ViviendaDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class VistaAfiliadosController {

	@FXML private Tab datos_generales;
	@FXML private Tab carga_familiar;
	@FXML private Tab vivienda;
	@FXML private Tab documentos;
	@FXML private TabPane tp_principal;
	@FXML private TextField txt_buscar;
	@FXML private TableView<CsPersona> tv_persona;
	@FXML private TableView<CsDocumento> tv_documentos;
	@FXML private TableView<CsCargaFamiliar> tv_cargas;
	@FXML private TextField txt_fecha_afil;
	@FXML private TextField txt_codigo_p;
	@FXML private TextField txt_codigo_a;
	@FXML private TextField txt_cedula;
	@FXML private TextField txt_nombre;
	@FXML private TextField txt_apellido;
	@FXML private TextField txt_direccion;
	@FXML private TextField txt_telefono;
	@FXML private TextField txt_nacimiento;
	@FXML private TextField txt_trabajo;
	@FXML private DatePicker txt_fecha;
	@FXML private TextField txt_cod_vivi;
	@FXML private TextField txt_num_manz;
	@FXML private TextField txt_num_dorm;
	@FXML private TextField txt_num_vivi;
	@FXML private TextField txt_edad;
	@FXML private CheckBox ch_estado;
	@FXML private CheckBox ch_calif;
	@FXML private CheckBox ch_estado_vivi;

	@FXML private CheckBox ch_serv_hig;
	@FXML private CheckBox ch_energia;
	@FXML private CheckBox ch_agua;

	@FXML private ComboBox<DetParametrica> cb_estado_vivi;
	@FXML private ComboBox<DetParametrica> cb_material;
	@FXML private ComboBox<DetParametrica> cb_tipo;

	@FXML private ComboBox<DetParametrica> cb_estado;
	@FXML private ComboBox<DetParametrica> cb_genero;
	@FXML private ComboBox<DetParametrica> cb_profesion;
	@FXML private ComboBox<DetParametrica> cb_barrio;
	@FXML private ComboBox<DetParametrica> cb_educacion;
	@FXML private Button bt_guardar;
	@FXML private Button bt_guardar_vivi;
	@FXML private Button bt_nuevo;
	@FXML private Button bt_nuevo_vivi;
	@FXML private Button bt_examinar;
	@FXML private Button bt_quitar;

	@FXML private Button bt_anadirdoc;
	@FXML private Button bt_eliminardoc;
	@FXML private Button bt_anadircargas;
	@FXML private Button bt_eliminarcargas;
	@FXML private ImageView iv_foto;

	ControllerHelper helper = new ControllerHelper();
	PersonaDAO personaDAO = new PersonaDAO();
	CsAfiliadoDAO csAfiliadoDAO = new CsAfiliadoDAO();
	DetParametricaDAO detparametricaDAO = new DetParametricaDAO();
	List<DetParametrica> datosTablaParametrica = detparametricaDAO.getListAll();
	DetParametrica seleccion = new DetParametrica();
	CsAfiliadoDAO afiliadoDAO = new CsAfiliadoDAO();
	ViviendaDAO viviendaDAO = new ViviendaDAO();
	CsAfiliado MostarAfiliado = new CsAfiliado();
	
	Date ahora = new Date();
	SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		
	public void initialize(){
		int maxLength = 10;
		llenarComboBarrios();
		llenarComboGeneros();
		llenarComboNiveles();
		llenarComboProfesion();
		llenarComboEstadosCiviles();
		llenarComboMaterial();
		llenarComboTipo();
		llenarComboEstadoVivi();
		llenar_Datos("");
		ch_estado.setSelected(true);
		ch_estado_vivi.setSelected(true);

        txt_fecha_afil.setText(formateador.format(ahora));
        
       
        
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
				
		
		tv_cargas.setRowFactory(tv -> {
			TableRow<CsCargaFamiliar> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					if(tv_cargas.getSelectionModel().getSelectedItem() != null){
						Context.getInstance().setCargaFamiliares(tv_cargas.getSelectionModel().getSelectedItem());
						helper.abrirPantallaModal("/comuneros/VistaPersona.fxml","Carga Familiar", Context.getInstance().getStage());
					}
				}
			});
			return row ;
		});

		tv_documentos.setRowFactory(tv -> {
			TableRow<CsDocumento> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					if(tv_documentos.getSelectionModel().getSelectedItem() != null){
						Context.getInstance().setDocumentos(tv_documentos.getSelectionModel().getSelectedItem());						
						helper.abrirPantallaModal("/comuneros/VistaDocumentos.fxml","Documentos", Context.getInstance().getStage());
					}
				}
			});
			return row ;
		});

		tv_persona.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				recuperarAfiliado(tv_persona.getSelectionModel().getSelectedItem().getPersId());
			}
		});

		txt_buscar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				llenar_Datos(txt_buscar.getText().toString());	
			}
		});

		txt_cedula.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.ENTER))
				{
					if (validarCedula(txt_cedula.getText()) == false)
					{
						helper.mostrarAlertaAdvertencia("El número de cedula es incorrecto!", Context.getInstance().getStage());
						txt_cedula.setText("");
						txt_cedula.requestFocus();	
					}
					else
					{
						txt_apellido.requestFocus();
					}

				}
			}
		});

		txt_telefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txt_telefono.getText().length() > maxLength) {
					String s = txt_telefono.getText().substring(0, maxLength);
					txt_telefono.setText(s);
				}
			}
		});

		
		txt_cedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txt_cedula.getText().length() > maxLength) {
					String s = txt_cedula.getText().substring(0, maxLength);
					txt_cedula.setText(s);
				}
			}
		});
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
		
		txt_fecha.setOnAction(new  EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				LocalDate fechNacimiento = txt_fecha.getValue();
				LocalDate ahora = LocalDate.now();
				Period periodo = Period.between(fechNacimiento, ahora);
				txt_edad.setText(String.valueOf(periodo.getYears()));
			}
		});
		
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

	private void llenarComboMaterial(){
		try{
			cb_material.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaMaterial = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("Mat01"))
					listaMaterial.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaMaterial);
			cb_material.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	private void llenarComboTipo(){
		try{
			cb_tipo.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaTipo = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("Tip01"))
					listaTipo.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaTipo);
			cb_tipo.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	private void llenarComboEstadoVivi(){
		try{
			cb_estado_vivi.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaEstadoVivi = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("EsV01"))
					listaEstadoVivi.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaEstadoVivi);
			cb_estado_vivi.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


	void llenar_Datos(String busqueda){
		try{
			//String estado = "";
			tv_persona.getColumns().clear();
			List<CsPersona> listaPersonas;
			ObservableList<CsPersona> datos = FXCollections.observableArrayList();
			listaPersonas = personaDAO.getListarPersona(busqueda);
			datos.setAll(listaPersonas);

			TableColumn<CsPersona, String> apellidosColum = new TableColumn<>("Apellidos");
			apellidosColum.setMinWidth(10);
			apellidosColum.setPrefWidth(150);
			apellidosColum.setCellValueFactory(new PropertyValueFactory<CsPersona, String>("persApellidos"));

			TableColumn<CsPersona, String> nombresColum = new TableColumn<>("Nombres");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(150);
			nombresColum.setCellValueFactory(new PropertyValueFactory<CsPersona, String>("persNombres"));

			tv_persona.getColumns().addAll(apellidosColum,nombresColum);
			tv_persona.setItems(datos);
		}catch(Exception ex){
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

	public void recuperarAfiliado(int idAfiliado)
	{
		CsPersona MostarPersona;
		
		MostarPersona = personaDAO.getPersona(idAfiliado);
		
		if(csAfiliadoDAO.getMostrarAfiliado(idAfiliado).size() == 0)
		{
			MostarAfiliado = null;
			Context.getInstance().setAfiliado(null);
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Persona no afiliada...¿Desea Afiliar?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
				selectionModel.select(datos_generales);
				txt_codigo_a.setText("");
				//limpiar_no_afiliado();
				//grabar afiliado

				CsAfiliado afiliadoGrabar = new CsAfiliado();
				CsPersona personaGrabar = tv_persona.getSelectionModel().getSelectedItem();
				CsAfiliadoDAO afiliadoDAO = new CsAfiliadoDAO();
				afiliadoGrabar.setAfilId(null);
				afiliadoGrabar.setAfilEstado("A");
				afiliadoGrabar.setAfilCalif("NC");				
				
				afiliadoGrabar.setAfilFecha(new Timestamp(System.currentTimeMillis()));
				
				afiliadoGrabar.setCsPersona(personaGrabar);
				personaGrabar.getCsAfiliados().add(afiliadoGrabar);
				
				afiliadoDAO.getEntityManager().getTransaction().begin();
				afiliadoDAO.getEntityManager().persist(afiliadoGrabar);
				afiliadoDAO.getEntityManager().getTransaction().commit();
				
				MostarAfiliado = csAfiliadoDAO.getMostrarAfiliado(idAfiliado).get(0);
				recuperarDatos(MostarAfiliado.getCsPersona());
				//llenar_Datos("");

			}
		}else 
		{ 
			MostarAfiliado = csAfiliadoDAO.getMostrarAfiliado(idAfiliado).get(0);
			recuperarDatos(MostarAfiliado.getCsPersona());
			txt_codigo_a.setText(MostarAfiliado.getAfilId().toString());
			System.out.println(MostarAfiliado.getAfilFecha());
			txt_fecha_afil.setText(String.valueOf(MostarAfiliado.getAfilFecha().toLocalDateTime().toLocalDate()));
			if(MostarAfiliado.getAfilCalif().equals("C")) 
				ch_calif.setSelected(true);
			else
				ch_calif.setSelected(false);
			//recupera carga familiar
			recuperarCargasFamiliares(MostarAfiliado);

			//recupera datos de vivienda
			recuperarDatosVivienda(MostarAfiliado);

			//recuperar documentos
			recuperarDatosDocumentos(MostarAfiliado);
		}
	}

	void limpiar_no_afiliado() {
		//txt_codigo_a.setText("");
		ch_calif.setSelected(false);	
		tv_cargas.getColumns().clear();
		tv_documentos.getColumns().clear();
		txt_cod_vivi.setText("");
		txt_num_manz.setText("");
		txt_num_vivi.setText("");
		txt_num_dorm.setText("");
		ch_energia.setSelected(false);
		ch_agua.setSelected(false);	
		ch_serv_hig.setSelected(false);	
		ch_estado_vivi.setSelected(false);
		cb_tipo.getSelectionModel().clearSelection();
		cb_estado_vivi.getSelectionModel().clearSelection();
		cb_material.getSelectionModel().clearSelection();
	}

	void recuperarDatos(CsPersona PersonaSeleccionada) {
		try {
			txt_codigo_p.setText(String.valueOf(PersonaSeleccionada.getPersId()));
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

			if(PersonaSeleccionada.getPersEstado().equals("A")) 
				ch_estado.setSelected(true);
			else
				ch_estado.setSelected(false);


		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void recuperarCargasFamiliares(CsAfiliado persona)
	{
		try
		{
			List<CsCargaFamiliar> listaCarga = persona.getCsCargaFamiliars();

			ObservableList<CsCargaFamiliar> datos = FXCollections.observableArrayList();
			tv_cargas.getColumns().clear();
			//tv_cargas.getItems().clear();
			datos.setAll(listaCarga);
			TableColumn<CsCargaFamiliar, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(100);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CsCargaFamiliar,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CsCargaFamiliar, String> param) {
					// TODO Auto-generated method stub
					return new SimpleObjectProperty<String>(param.getValue().getCsPersona().getPersCedula());
				}
			});

			TableColumn<CsCargaFamiliar, String> apellidosColum = new TableColumn<>("Apellidos");
			apellidosColum.setMinWidth(10);
			apellidosColum.setPrefWidth(160);
			apellidosColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CsCargaFamiliar,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CsCargaFamiliar, String> param) {
					// TODO Auto-generated method stub
					return new SimpleObjectProperty<String>(param.getValue().getCsPersona().getPersApellidos());
				}
			});

			TableColumn<CsCargaFamiliar, String> nombresColum = new TableColumn<>("Nombres");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(160);
			nombresColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CsCargaFamiliar,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CsCargaFamiliar, String> param) {
					// TODO Auto-generated method stub
					return new SimpleObjectProperty<String>(param.getValue().getCsPersona().getPersNombres());
				}
			});


			TableColumn<CsCargaFamiliar, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(50);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CsCargaFamiliar,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CsCargaFamiliar, String> param) {
					// TODO Auto-generated method stub
					return new SimpleObjectProperty<String>(param.getValue().getCargEstado());
				}
			});

			tv_cargas.getColumns().addAll(cedulaColum,apellidosColum,nombresColum,estadoColum);
			tv_cargas.setItems(datos);

		}catch(Exception ex) {
			tv_cargas.getColumns().clear();
			System.out.println(ex.getMessage());		
		}
	}

	public void recuperarDatosVivienda(CsAfiliado afiliado)
	{
		try {
			List<CsVivienda> listaVivienda = afiliado.getCsViviendas();

			txt_cod_vivi.setText(listaVivienda.get(0).getViviId().toString());
			txt_num_manz.setText(listaVivienda.get(0).getViviManz().toString());
			txt_num_vivi.setText(listaVivienda.get(0).getViviNum().toString());
			txt_num_dorm.setText(listaVivienda.get(0).getViviNumDorm().toString());

			if(listaVivienda.get(0).getViviServHig().equals(true)) 
				ch_serv_hig.setSelected(true);
			else
				ch_serv_hig.setSelected(false);

			if(listaVivienda.get(0).getViviAguaTub().equals(true)) 
				ch_agua.setSelected(true);
			else
				ch_agua.setSelected(false);

			if(listaVivienda.get(0).getViviEnerElec().equals(true)) 
				ch_energia.setSelected(true);
			else
				ch_energia.setSelected(false);

			if(listaVivienda.get(0).getViviEstado().equals("A"))
				ch_estado_vivi.setSelected(true);
			else
				ch_estado_vivi.setSelected(false);

			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(listaVivienda.get(0).getPgTpvivId()))
					seleccion = dato;
			}
			cb_tipo.getSelectionModel().select(seleccion);


			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(listaVivienda.get(0).getPgMtvivId()))
					seleccion = dato;
			}
			cb_material.getSelectionModel().select(seleccion);

			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(listaVivienda.get(0).getPgEtvivId()))
					seleccion = dato;
			}
			cb_estado_vivi.getSelectionModel().select(seleccion);
		}catch(Exception ex) {
			//limpiar_no_vivienda();
			System.out.println(ex.getMessage());
		}
	}

	public void recuperarDatosDocumentos(CsAfiliado afiliado)
	{
		try {
			List<CsDocumento> listaDocumentos = afiliado.getCsDocumentos();
			ObservableList<CsDocumento> datos = FXCollections.observableArrayList();
			tv_documentos.getColumns().clear();
			datos.setAll(listaDocumentos);
			TableColumn<CsDocumento, String> codigoColum = new TableColumn<>("Código");
			codigoColum.setMinWidth(10);
			codigoColum.setPrefWidth(90);
			codigoColum.setCellValueFactory(new PropertyValueFactory<CsDocumento, String>("docId"));

			TableColumn<CsDocumento, String> NombreColum = new TableColumn<>("Nombre del Documento");
			NombreColum.setMinWidth(10);
			NombreColum.setPrefWidth(300);
			NombreColum.setCellValueFactory(new PropertyValueFactory<CsDocumento, String>("docNombre"));

			TableColumn<CsDocumento, String> EstadoColum = new TableColumn<>("Estado");
			EstadoColum.setMinWidth(10);
			EstadoColum.setPrefWidth(100);
			EstadoColum.setCellValueFactory(new PropertyValueFactory<CsDocumento, String>("docEstado"));

			tv_documentos.getColumns().addAll(codigoColum,NombreColum,EstadoColum);
			tv_documentos.setItems(datos);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());		
		}
	}

	public void anadirDoc() {
		try{
			if(MostarAfiliado != null) {
				helper.abrirPantallaModal("/comuneros/VistaDocumentos.fxml","Documentos", Context.getInstance().getStage());
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void anadirCarga()
	{
		try{
			if(MostarAfiliado != null) {
				Context.getInstance().setAfiliado(MostarAfiliado);
				helper.abrirPantallaModal("/comuneros/VistaPersona.fxml","Carga Familiar", Context.getInstance().getStage());
				llenar_Datos("");
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	boolean validarCedula(String cedula) {
		int total = 0;  
		int tamanoLongitudCedula = 10;  
		int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2}; //jeniffer mechuda
		int numeroProviancias = 24;  
		int tercerdigito = 6;  
		if (cedula.matches("[0-9]*") && cedula.length() == tamanoLongitudCedula) {  
			int provincia = Integer.parseInt(cedula.charAt(0) + "" + cedula.charAt(1));  
			int digitoTres = Integer.parseInt(cedula.charAt(2) + "");  
			if ((provincia > 0 && provincia <= numeroProviancias) && digitoTres < tercerdigito) {  
				int digitoVerificadorRecibido = Integer.parseInt(cedula.charAt(9) + "");  
				for (int i = 0; i < coeficientes.length; i++) {  
					int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(cedula.charAt(i) + "");  
					total = valor >= 10 ? total + (valor - 9) : total + valor;  
				}  
				int digitoVerificadorObtenido = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10) : total;  
				if (digitoVerificadorObtenido == digitoVerificadorRecibido) {  
					return true;  
				}  
			}  
			return false;  
		}  
		return false;  
	}


	public void grabar(){
		try {
			String estado;
			String estado_cali;
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
			if(ch_estado.isSelected() == true)
				estado = "A";
			else
				estado = "I";
			csPersona.setPersEstado(estado);
			CsAfiliado afiliadoGrabar = new CsAfiliado();
			afiliadoGrabar.setAfilId(null);
			afiliadoGrabar.setAfilEstado("A");
			afiliadoGrabar.setAfilFecha(new Timestamp(System.currentTimeMillis()));

			if(ch_calif.isSelected() == true)
				estado_cali = "C";
			else
				estado_cali = "NC";
			afiliadoGrabar.setAfilCalif(estado_cali);

			afiliadoDAO.getEntityManager().getTransaction().begin();
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){

				if (txt_codigo_p.getText().equals("")) 
				{
					//Persona Nueva
					csPersona.setPersId(null);
					afiliadoGrabar.setAfilId(null);
					afiliadoGrabar.setCsPersona(csPersona);
					afiliadoDAO.getEntityManager().merge(afiliadoGrabar);
					llenar_Datos("");
				}else
				{	
				    csPersona.setPersId(Integer.parseInt(txt_codigo_p.getText()));
					afiliadoGrabar.setAfilId(Integer.parseInt(txt_codigo_a.getText()));
					afiliadoGrabar.setCsPersona(csPersona);
					afiliadoDAO.getEntityManager().merge(csPersona);
					afiliadoDAO.getEntityManager().merge(afiliadoGrabar);
					llenar_Datos("");
				}
				afiliadoDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
			}
			
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			afiliadoDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}


	public void grabar_vivienda()
	{
		try {
		String estado;
		boolean estadovi;
		CsAfiliado afiliadoRecuperado = tv_persona.getSelectionModel().getSelectedItem().getCsAfiliados().get(0);

		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
		if(result.get() == ButtonType.OK){
			
			if(txt_cod_vivi.getText().equals("")) 
			{
				CsVivienda csVivienda = new CsVivienda();
				csVivienda.setViviId(null);
				csVivienda.setViviManz(Integer.parseInt(txt_num_manz.getText()));
				csVivienda.setViviNum(Integer.parseInt(txt_num_vivi.getText()));
				csVivienda.setViviNumDorm(Integer.parseInt(txt_num_dorm.getText()));
				csVivienda.setPgEtvivId(cb_estado_vivi.getSelectionModel().getSelectedItem().getDetpId());
				csVivienda.setPgMtvivId(cb_material.getSelectionModel().getSelectedItem().getDetpId());
				csVivienda.setPgTpvivId(cb_tipo.getSelectionModel().getSelectedItem().getDetpId());
				if(ch_estado_vivi.isSelected() == true)
					estado = "A";
				else
					estado = "I";

				csVivienda.setViviEstado(estado);

				if(ch_energia.isSelected() == true)
					estadovi = true;
				else
					estadovi = false;

				csVivienda.setViviEnerElec(estadovi);

				if(ch_agua.isSelected() == true)
					estadovi = true;
				else
					estadovi = false;

				csVivienda.setViviAguaTub(estadovi);


				if(ch_serv_hig.isSelected() == true)
					estadovi = true;
				else
					estadovi = false;

				csVivienda.setViviServHig(estadovi);
				
				System.out.println("entra grabar nuevo");
				
				csVivienda.setViviId(null);
				csVivienda.setCsAfiliado(afiliadoRecuperado);
				
				List<CsVivienda> viviendas = new ArrayList<CsVivienda>();
				viviendas.add(csVivienda);
				afiliadoRecuperado.setCsViviendas(viviendas);
				afiliadoDAO.getEntityManager().getTransaction().begin();
				afiliadoDAO.getEntityManager().merge(afiliadoRecuperado);
				afiliadoDAO.getEntityManager().getTransaction().commit();
				llenar_Datos("");
			}
			else
			{
				afiliadoRecuperado.getCsViviendas().get(0).setViviId(Integer.parseInt(txt_cod_vivi.getText()));
				afiliadoRecuperado.getCsViviendas().get(0).setCsAfiliado(afiliadoRecuperado);
				afiliadoRecuperado.getCsViviendas().get(0).setViviManz(Integer.parseInt(txt_num_manz.getText()));
				afiliadoRecuperado.getCsViviendas().get(0).setViviNum(Integer.parseInt(txt_num_vivi.getText()));
				afiliadoRecuperado.getCsViviendas().get(0).setViviNumDorm(Integer.parseInt(txt_num_dorm.getText()));
				afiliadoRecuperado.getCsViviendas().get(0).setPgEtvivId(cb_estado_vivi.getSelectionModel().getSelectedItem().getDetpId());
				afiliadoRecuperado.getCsViviendas().get(0).setPgMtvivId(cb_material.getSelectionModel().getSelectedItem().getDetpId());
				afiliadoRecuperado.getCsViviendas().get(0).setPgTpvivId(cb_tipo.getSelectionModel().getSelectedItem().getDetpId());
				if(ch_estado_vivi.isSelected() == true)
					estado = "A";
				else
					estado = "I";

				afiliadoRecuperado.getCsViviendas().get(0).setViviEstado(estado);

				if(ch_energia.isSelected() == true)
					estadovi = true;
				else
					estadovi = false;

				afiliadoRecuperado.getCsViviendas().get(0).setViviEnerElec(estadovi);

				if(ch_agua.isSelected() == true)
					estadovi = true;
				else
					estadovi = false;

				afiliadoRecuperado.getCsViviendas().get(0).setViviAguaTub(estadovi);

				if(ch_serv_hig.isSelected() == true)
					estadovi = true;
				else
					estadovi = false;

				afiliadoRecuperado.getCsViviendas().get(0).setViviServHig(estadovi);
				
				afiliadoDAO.getEntityManager().getTransaction().begin();
				afiliadoDAO.getEntityManager().merge(afiliadoRecuperado.getCsViviendas().get(0));
				afiliadoDAO.getEntityManager().getTransaction().commit();
				llenar_Datos("");
			}
			helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
		}
			}catch(Exception ex) {
				helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			viviendaDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
			}
		}
	
	void limpiar_vivienda() {
		
		txt_num_manz.setText("");
		
	}
	}



