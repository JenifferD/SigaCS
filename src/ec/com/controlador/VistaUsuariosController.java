package ec.com.controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.com.modelo.Perfil;
import ec.com.modelo.PerfilDAO;
import ec.com.modelo.Usuario;
import ec.com.modelo.UsuarioDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import ec.com.util.Encriptado;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class VistaUsuariosController {
	
	@FXML private TextField txt_codigo;
	@FXML private TextField txt_cedula;
	@FXML private TextField txt_nombres;
	@FXML private TextField txt_apellidos;
	@FXML private CheckBox chkEstado;
	@FXML private ComboBox<Perfil> cbo_perfil;
	@FXML private TextField txt_telefono;
	@FXML private TextField txt_direccion;
	@FXML private TextField txt_email;
	@FXML private TextField txt_usuario;
	@FXML private PasswordField txt_clave;
	@FXML private ImageView iv_usuario;
	@FXML private Button bt_guardar;
	@FXML private Button bt_nuevo;
	@FXML private Button bt_examinar;
	@FXML private Button bt_quitar;
	@FXML private Button bt_buscar;
	@FXML private Button btn_nuevo;
	@FXML private TextField txt_buscar;
	@FXML private Tab tb_registro_usu;
	@FXML private Tab tb_listado_usu;
	@FXML private TabPane tp_principal;
	@FXML private TableView<Usuario> tv_usuarios;
	
	ControllerHelper helper = new ControllerHelper();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	PerfilDAO perfilDAO = new PerfilDAO();
	Encriptado encriptado = new Encriptado();
	
	public void initialize(){
		int maxLength = 10;
		txt_cedula.requestFocus();
		chkEstado.setSelected(true);
		limpiar();
		llenarComboPerfil();
		llenar_Datos("");
		
		//repetir cedula en usuario y contraseña
		txt_cedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
					txt_usuario.setText(newValue);
					txt_clave.setText(newValue);	
			}
		});
		
		//valida que solo se ingresen numeros
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

		//evento para validar el enter y la que la cedula exista
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
	    				txt_usuario.setText("");
	    				txt_clave.setText("");
	    			}
	            	else
	            	{
	            		txt_nombres.requestFocus();
	            	}
	            	
	            }
	        }
	    });
		
        //evento para validar que el correo que se ingrese sea válido
		txt_email.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	if (ValidarEmail(txt_email.getText()) == false)
	    			{
	    				helper.mostrarAlertaAdvertencia("El email es incorrecto!", Context.getInstance().getStage());
	    				txt_email.requestFocus();
	    			}
	            }
	        }
	    });
		
        //busca la persona que se escribe en el txt
		txt_buscar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
			llenar_Datos(txt_buscar.getText().toString());	
			}
		});
		
		//acepta solo letras mayusculas 
		txt_nombres.textProperty().addListener(new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txt_nombres.getText().toUpperCase();
				txt_nombres.setText(cadena);
			}
		});
		
		txt_apellidos.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txt_apellidos.getText().toUpperCase();
				txt_apellidos.setText(cadena);
			}
		});
		
		txt_direccion.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txt_direccion.getText().toUpperCase();
				txt_direccion.setText(cadena);
			}
		});
  
		txt_nombres.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            		txt_apellidos.requestFocus();
	            }
	        }
	    });

		txt_apellidos.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            		cbo_perfil.requestFocus();
	            }
	        }
	    });
		
		cbo_perfil.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            		txt_direccion.requestFocus();
	            }
	        }
	    });
		
		txt_direccion.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            		txt_email.requestFocus();
	            }
	        }
	    });

		txt_email.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            		txt_telefono.requestFocus();
	            }
	        }
	    });
		
		//al dar doble clic sobre una fila de la tabla me recupera los datos en la pestaña de registro de usuarios
		tv_usuarios.setRowFactory(tv -> {
            TableRow<Usuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	if(tv_usuarios.getSelectionModel().getSelectedItem() != null){
                		llenarDatos(tv_usuarios.getSelectionModel().getSelectedItem());
                		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
                		selectionModel.select(tb_registro_usu);
                		bt_quitar.setDisable(false);
    				}
                }
            });
            return row ;
        });
	}
	
	boolean validarCedula(String cedula) {
		int total = 0;  
	     int tamanoLongitudCedula = 10;  
	     int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};  
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

	boolean ValidarEmail (String correo) {
	        Pattern pat = null;
	        Matcher mat = null;        
	        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
	        mat = pat.matcher(correo);
	        if (mat.find()) {
	            return true;
	        }else{
	            return false;
	        }        
	    }
	
	boolean validarDatos() {
		try {
			if(txt_cedula.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar cedula del Usuario", Context.getInstance().getStage());
				txt_cedula.requestFocus();
				return false;
			}
			if(txt_nombres.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txt_nombres.requestFocus();
				return false;
			}
			if(txt_apellidos.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txt_apellidos.requestFocus();
				return false;
			}
			if(txt_direccion.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txt_direccion.requestFocus();
				return false;
			}
			if(txt_email.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txt_email.requestFocus();
				return false;
			}
			if(txt_telefono.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txt_telefono.requestFocus();
				return false;
			}
			if(txt_usuario.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar un Usuario", Context.getInstance().getStage());
				txt_usuario.requestFocus();
				return false;	
			}
			if(txt_clave.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar una clave para el usuario", Context.getInstance().getStage());
				txt_clave.requestFocus();
				return false;	
			}
			if(cbo_perfil.getValue() == null) {
				helper.mostrarAlertaAdvertencia("Debe Seleccionar un perfil", Context.getInstance().getStage());
				cbo_perfil.requestFocus();
				return false;	
			}
			if(validarUsuario() == true) {
				helper.mostrarAlertaAdvertencia("El usuario ya existe!!", Context.getInstance().getStage());
				txt_usuario.requestFocus();
				return false;	
			}
			
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	boolean validarUsuario() {
		try {
			List<Usuario> listaUsuario;
			listaUsuario = usuarioDAO.getValidarUsuario(txt_usuario.getText(), Integer.parseInt(txt_codigo.getText()));
			if(listaUsuario.size() != 0)
				return true;
			else
				return false;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public void nuevo() throws IOException{
		limpiar();
	}
	
	void limpiar() {
		txt_codigo.setText("0");
		txt_codigo.setEditable(false);
		txt_cedula.setText("");
		txt_nombres.setText("");
		txt_apellidos.setText("");
		chkEstado.setSelected(true);
		cbo_perfil.getSelectionModel().clearSelection();
		txt_email.setText("");
		txt_telefono.setText("");
		txt_direccion.setText("");
		txt_usuario.setText("");
		txt_clave.setText("");
		Image img = new Image("/usuario.png");
		iv_usuario.setImage(img);
		txt_cedula.requestFocus();
		bt_quitar.setDisable(true);
	}
	
	public void buscar()
	{
		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
		selectionModel.select(tb_listado_usu);
		txt_buscar.requestFocus();
	}
	
	public void nuevo_registro()
	{
		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
		selectionModel.select(tb_registro_usu);
		txt_cedula.requestFocus();
	}
	
	public void quitar() {
		Image img = new Image("/usuario.png");
		iv_usuario.setImage(img);
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
				iv_usuario.setImage(image);
			}
			bt_quitar.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void grabar(){
		try {
			String estado;
			if(validarDatos() == false){
				return;
			}
			if(chkEstado.isSelected() == true)
				estado = "A";
			else
				estado = "I";
			Usuario usuario = new Usuario();
			usuario.setUsuEstado(estado);
			usuario.setPerfil(cbo_perfil.getSelectionModel().getSelectedItem());
			usuario.setUsuCedula(txt_cedula.getText());
			usuario.setUsuNombres(txt_nombres.getText());
			usuario.setUsuApellidos(txt_apellidos.getText());
			usuario.setUsuDireccion(txt_direccion.getText());
			usuario.setUsuTelefono(txt_telefono.getText());
			usuario.setUsuEmail(txt_email.getText());
			usuario.setUsuUsuario(txt_usuario.getText());
			usuario.setUsuClave(encriptado.Encriptar(txt_clave.getText()));
			usuario.setUsuFoto(helper.encodeFileToBase64Binary(iv_usuario.getImage()).getBytes());
			
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				usuario.setUsuEstado(estado);
				usuarioDAO.getEntityManager().getTransaction().begin();
				if(txt_codigo.getText().equals("0")) {//inserta
					usuario.setUsuId(null);
					usuarioDAO.getEntityManager().persist(usuario);
				}else {//modifica
					usuario.setUsuId(Integer.parseInt(txt_codigo.getText()));
					usuarioDAO.getEntityManager().merge(usuario);
				}
				usuarioDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				limpiar();
				llenar_Datos("");
				bt_quitar.setDisable(true);
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			usuarioDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}

	
	
	
	void llenarDatos(Usuario datoSeleccionado){
		try {
			txt_codigo.setText(String.valueOf(datoSeleccionado.getUsuId()));
			if(datoSeleccionado.getUsuCedula() == null)
				txt_cedula.setText("");
			else
				txt_cedula.setText(datoSeleccionado.getUsuCedula());

			if(datoSeleccionado.getUsuNombres() == null)
				txt_nombres.setText("");
			else
				txt_nombres.setText(datoSeleccionado.getUsuNombres());

			if(datoSeleccionado.getUsuApellidos() == null)
				txt_apellidos.setText("");
			else
				txt_apellidos.setText(datoSeleccionado.getUsuApellidos());

			if(datoSeleccionado.getUsuEstado().equals("A")) 
				chkEstado.setSelected(true);
			else
				chkEstado.setSelected(false);
			cbo_perfil.getSelectionModel().select(datoSeleccionado.getPerfil());

			if(datoSeleccionado.getUsuEmail() == null)
				txt_email.setText("");
			else
				txt_email.setText(datoSeleccionado.getUsuEmail());

			if(datoSeleccionado.getUsuTelefono() == null)
				txt_telefono.setText("");
			else
				txt_telefono.setText(datoSeleccionado.getUsuTelefono());

			if(datoSeleccionado.getUsuDireccion() == null)
				txt_direccion.setText("");
			else
				txt_direccion.setText(datoSeleccionado.getUsuDireccion());

			txt_usuario.setText(datoSeleccionado.getUsuUsuario());
			txt_clave.setText(encriptado.Desencriptar(datoSeleccionado.getUsuClave()));
			if(datoSeleccionado.getUsuFoto() != null) {
				String imgString = new String(datoSeleccionado.getUsuFoto(), "UTF-8");
				iv_usuario.setImage(helper.getImageFromBase64String(imgString).getImage());
			}
			else {
				Image img = new Image("/usuario.png");
				iv_usuario.setImage(img);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void llenarComboPerfil(){
		try{
			cbo_perfil.setPromptText("Seleccione Perfil");
			List<Perfil> listaPerfiles;
			listaPerfiles = perfilDAO.getListaPerfil();
			ObservableList<Perfil> datos = FXCollections.observableArrayList();
		
			datos.addAll(listaPerfiles);
			cbo_perfil.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	void llenar_Datos(String busqueda){
		try{
			//String estado = "";
			tv_usuarios.getColumns().clear();
			List<Usuario> listaUsuarios;
			ObservableList<Usuario> datos = FXCollections.observableArrayList();
			listaUsuarios = usuarioDAO.getBuscarUsuario(busqueda);
			datos.setAll(listaUsuarios);

			//llenar los datos en la tabla
			TableColumn<Usuario, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(60);
			idColum.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuId"));
			
			TableColumn<Usuario, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(75);
			cedulaColum.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuCedula"));
			
			TableColumn<Usuario, String> nombreColum = new TableColumn<>("Nombre");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(180);
			nombreColum.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuNombres"));
			
			TableColumn<Usuario, String> apellidosColum = new TableColumn<>("Apellidos");
			apellidosColum.setMinWidth(10);
			apellidosColum.setPrefWidth(180);
			apellidosColum.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuApellidos"));
			
			TableColumn<Usuario, String> direccionColum = new TableColumn<>("Direccion");
			direccionColum.setMinWidth(10);
			direccionColum.setPrefWidth(255);
			direccionColum.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuDireccion"));
			
			TableColumn<Usuario, String> telefonoColum = new TableColumn<>("Telefono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(75);
			telefonoColum.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuTelefono"));
			
			TableColumn<Usuario, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(50);
			estadoColum.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuEstado"));
			
			tv_usuarios.getColumns().addAll(idColum,cedulaColum, nombreColum,apellidosColum,direccionColum,telefonoColum,estadoColum);
			tv_usuarios.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
		
	
}