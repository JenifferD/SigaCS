package ec.com.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ec.com.modelo.Acceso;
import ec.com.modelo.AccesoDAO;
import ec.com.modelo.Menu;
import ec.com.modelo.MenuDAO;
import ec.com.modelo.Perfil;
import ec.com.modelo.PerfilDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class VistaPerfilesController {
	@FXML private TextField txt_codigo;
	@FXML private TextField txt_nombre;
	@FXML private TextField txt_descrip;
	@FXML private CheckBox chk_estado;
	@FXML private Button bt_guardar;
	@FXML private Button bt_nuevo;
	@FXML private Button bt_buscar;
	@FXML private Button btn_nuevo;
	@FXML private TextField txt_buscar;
	@FXML private Tab tb_registro;
	@FXML private Tab tb_listado;
	@FXML private TabPane tp_principal;
	@FXML private TableView<Perfil> tv_perfiles;
	
	ControllerHelper helper = new ControllerHelper();
	PerfilDAO perfilDAO = new PerfilDAO();
	MenuDAO menuDAO = new MenuDAO();
	AccesoDAO accesoDAO = new AccesoDAO();
	
	public void initialize(){
		limpiar();
		llenar_Datos("");
		chk_estado.setSelected(true);
		
		tv_perfiles.setRowFactory(tv -> {
            TableRow<Perfil> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	if(tv_perfiles.getSelectionModel().getSelectedItem() != null){
                		llenarDatos(tv_perfiles.getSelectionModel().getSelectedItem());
                		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
                		selectionModel.select(tb_registro);
                		
    				}
                }
            });
            return row ;
        });
		
		txt_buscar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			llenar_Datos(txt_buscar.getText().toString());	
			}
		});
		
		//acepta solo letras mayusculas 
				txt_nombre.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						String cadena = txt_nombre.getText().toUpperCase();
						txt_nombre.setText(cadena);
					}
				});
				
		//acepta solo letras mayusculas 
		txt_descrip.textProperty().addListener(new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			// TODO Auto-generated method stub
			String cadena = txt_descrip.getText().toUpperCase();
			txt_descrip.setText(cadena);
		}
		});
		
		txt_nombre.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            		txt_descrip.requestFocus();
	            }
	        }
	    });
	}
	

	public void grabar(){
		try {
			List<Menu> listaMenuPadre = menuDAO.getMenuPadre();
			boolean band = false;
			String estado;
			if(validarDatos() == false){
				return;
			}
			if(chk_estado.isSelected() == true)
				estado = "A";
			else
				estado = "I";
			Perfil perfil = new Perfil();
			perfil.setPerEstado(estado);
			perfil.setPerNombre(txt_nombre.getText());
			perfil.setPerDescripcion(txt_descrip.getText());
			
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				perfil.setPerEstado(estado);
				perfilDAO.getEntityManager().getTransaction().begin();
				if(txt_codigo.getText().equals("0")) {//inserta
					band = true;
					perfil.setPerId(null);
					perfilDAO.getEntityManager().persist(perfil);
				}else {//modifica
					perfil.setPerId(Integer.parseInt(txt_codigo.getText()));
					perfilDAO.getEntityManager().merge(perfil);
				}
				perfilDAO.getEntityManager().getTransaction().commit();
				
				if (band == true)
				{
					List<Perfil> ultimoPerfil = new ArrayList<Perfil>();
					ultimoPerfil = perfilDAO.getUltimoPerfil();
					accesoDAO.getEntityManager().getTransaction().begin();
					
					for(int i = 0 ; i < listaMenuPadre.size() ; i ++) {
						Acceso accesoAnadir = new Acceso();
						accesoAnadir.setMenu(listaMenuPadre.get(i));
						accesoAnadir.setPerfil(ultimoPerfil.get(0));
						accesoAnadir.setAccesoEstado("A");
						accesoAnadir.setAccesoId(null);
						accesoDAO.getEntityManager().persist(accesoAnadir);
					}
					accesoDAO.getEntityManager().getTransaction().commit();
				}
				
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				limpiar();
				llenar_Datos("");
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			perfilDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}
	
	boolean validarDatos() {
		try {
			
			if(txt_nombre.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del perfil", Context.getInstance().getStage());
				txt_nombre.requestFocus();
				return false;
			}
			if(txt_descrip.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar una descripcion", Context.getInstance().getStage());
				txt_descrip.requestFocus();
				return false;	
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	void limpiar() {
		txt_codigo.setText("0");
		txt_codigo.setEditable(false);
		txt_nombre.setText("");
		txt_descrip.setText("");
		chk_estado.setSelected(true);
		txt_nombre.requestFocus();
		
	}
	
	public void nuevo() throws IOException{
		limpiar();
	}
	
	public void nuevo_registro()
	{
		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
		selectionModel.select(tb_registro);
		
	}
	
	public void buscar()
	{
		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
		selectionModel.select(tb_listado);
		
	}
	
	
	void llenarDatos(Perfil datoSeleccionado){
		try {
			txt_codigo.setText(String.valueOf(datoSeleccionado.getPerId()));
			if(datoSeleccionado.getPerNombre() == null)
				txt_nombre.setText("");
			else
				txt_nombre.setText(datoSeleccionado.getPerNombre());

			if(datoSeleccionado.getPerDescripcion() == null)
				txt_descrip.setText("");
			else
				txt_descrip.setText(datoSeleccionado.getPerDescripcion());


			if(datoSeleccionado.getPerEstado().equals("A")) 
				chk_estado.setSelected(true);
			else
				chk_estado.setSelected(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	void llenar_Datos(String perNombre){
		try{
			//String estado = "";
			tv_perfiles.getColumns().clear();
			List<Perfil> listaPerfil;
			ObservableList<Perfil> datos = FXCollections.observableArrayList();
			listaPerfil = perfilDAO.getBuscarPerfil(perNombre);
			datos.setAll(listaPerfil);

			//llenar los datos en la tabla
			TableColumn<Perfil, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(60);
			idColum.setCellValueFactory(new PropertyValueFactory<Perfil, String>("perId"));
			
			TableColumn<Perfil, String> nombreColum = new TableColumn<>("Nombre");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(190);
			nombreColum.setCellValueFactory(new PropertyValueFactory<Perfil, String>("perNombre"));
			
			TableColumn<Perfil, String> descripColum = new TableColumn<>("Descripcion");
			descripColum.setMinWidth(10);
			descripColum.setPrefWidth(360);
			descripColum.setCellValueFactory(new PropertyValueFactory<Perfil, String>("perDescripcion"));
			
			TableColumn<Perfil, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(80);
			estadoColum.setCellValueFactory(new PropertyValueFactory<Perfil, String>("perEstado"));
			
			tv_perfiles.getColumns().addAll(idColum,nombreColum, descripColum,estadoColum);
			tv_perfiles.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	

}
