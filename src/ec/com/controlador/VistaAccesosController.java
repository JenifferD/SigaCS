package ec.com.controlador;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaAccesosController {
	
	@FXML private ComboBox<Perfil> cb_perfil;
	@FXML private Button bt_guardar;
	@FXML private TableView<Menu> tv_menu;
	@FXML private TableView<Acceso> tv_acceso;
	@FXML private Button bt_añadir;
	@FXML private Button bt_remover;


	ControllerHelper helper = new ControllerHelper();
	PerfilDAO perfilDAO = new PerfilDAO();
	MenuDAO menuDAO = new MenuDAO();
	AccesoDAO accesoDAO = new AccesoDAO();
	
	public void initialize(){
		llenarComboPerfil();
		llenar_Datos();
		//tv_menu.setEditable(true);
	}


	public void grabar()
	{
		try {
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				accesoDAO.getEntityManager().getTransaction().begin();
				ObservableList<Acceso> datos = tv_acceso.getItems();
				System.out.println(datos.size());
				for(int i = 0 ; i < datos.size() ; i++) {
					if(datos.get(i).getAccesoId() == null)
						accesoDAO.getEntityManager().persist(datos.get(i));
					else
						accesoDAO.getEntityManager().merge(datos.get(i));	
				}
				accesoDAO.getEntityManager().getTransaction().commit();	
				helper.mostrarAlertaInformacion("Grabo Corectamente", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void llenarComboPerfil(){
		try{
			cb_perfil.setPromptText("Elija...");
			List<Perfil> listaPerfiles;
			listaPerfiles = perfilDAO.getListaPerfil();
			ObservableList<Perfil> datos = FXCollections.observableArrayList();

			datos.addAll(listaPerfiles);
			cb_perfil.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


	public void cargarAccesos() {
		try {
			cargarPermisosPerfil(cb_perfil.getSelectionModel().getSelectedItem().getPerId());
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}


	@SuppressWarnings({ "unchecked", "null" })
	public void cargarPermisosPerfil(int idPerfil) {
		try {
			// recupera todos los menus, exepto el asignado
			llenar_Datos();
			AccesoDAO accesoDAO = new AccesoDAO();
			List<Acceso> resultado = accesoDAO.getAcceso(idPerfil);
			
			if(resultado.size() > 0) {
				
				boolean bandera = false;
				
				ObservableList<Menu> datos = tv_menu.getItems();
				ObservableList<Menu> datosMenu = FXCollections.observableArrayList();;
				
				tv_menu.getColumns().clear();
				
				//verificar si el menu esta asignado a un perfil
				for(int i = 0 ; i < datos.size() ; i ++) {
					bandera = false;
					for(int j = 0 ; j < resultado.size() ; j ++) {
						if(datos.get(i).getMenuId().equals(resultado.get(j).getMenu().getMenuId()))
							bandera = true;
					}
					if(bandera == false)
						datosMenu.add(datos.get(i));
				}
				
				//llenar los datos en la tabla
				TableColumn<Menu, String> idColum = new TableColumn<>("Código");
				idColum.setMinWidth(10);
				idColum.setPrefWidth(80);
				idColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("menuId"));

				TableColumn<Menu, String> nombreColum = new TableColumn<>("Nombre del Menu");
				nombreColum.setMinWidth(10);
				nombreColum.setPrefWidth(320);
				nombreColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("menuDescripcion"));
				
				tv_menu.getColumns().addAll(idColum,nombreColum);
				tv_menu.setItems(datosMenu);
				
			}else {
				ObservableList<Menu> datos = tv_menu.getItems();
			    tv_menu.getColumns().clear();
				//llenar los datos en la tabla
				TableColumn<Menu, String> idColum = new TableColumn<>("Código");
				idColum.setMinWidth(10);
				idColum.setPrefWidth(80);
				idColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("menuId"));

				TableColumn<Menu, String> nombreColum = new TableColumn<>("Nombre del Menu");
				nombreColum.setMinWidth(10);
				nombreColum.setPrefWidth(320);
				nombreColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("menuDescripcion"));

				
				tv_menu.getColumns().addAll(idColum,nombreColum);
				tv_menu.setItems(datos);	
			}

			//recupera los asignados a esa persona
			if(resultado.size() > 0) {
				//llenar los datos en la tabla				
				  
				List<Acceso> ListaMenu = resultado;
				List<Menu> ListaMenuAll;
				ListaMenuAll = menuDAO.getListaMenu();

				for(int i = 0 ; i < ListaMenu.size() ; i ++) {
					for(int j = 0 ; j < ListaMenuAll.size() ; j ++) {
						if(ListaMenu.get(i).getMenu().getMenuIdpadre().equals(ListaMenuAll.get(j).getMenuId()))
							ListaMenu.get(i).getMenu().setMenuDescripcion(ListaMenuAll.get(j).getMenuDescripcion() + "/" + ListaMenu.get(i).getMenu().getMenuDescripcion());
					}
				}
				ObservableList<Acceso> datos = FXCollections.observableArrayList();
				datos.setAll(ListaMenu);
				
				tv_acceso.getColumns().clear();
				
				TableColumn<Acceso, String> idColum = new TableColumn<>("Código");
				idColum.setMinWidth(10);
				idColum.setPrefWidth(80);
				idColum.setCellValueFactory(new PropertyValueFactory<Acceso, String>("accesoId"));

				TableColumn<Acceso, String> nombreColum = new TableColumn<>("Nombre del Menu");
				nombreColum.setMinWidth(10);
				nombreColum.setPrefWidth(330);
				nombreColum.setCellValueFactory(new PropertyValueFactory<Acceso, String>("menu"));
				
				tv_acceso.getColumns().addAll(idColum,nombreColum);
				tv_acceso.setItems(datos);				
			}else {
				tv_acceso.getItems().clear();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public void añadir() {
		try {
			Menu menuSeleccionado = new Menu(); 
			menuSeleccionado = tv_menu.getSelectionModel().getSelectedItem();
			if(tv_menu.getSelectionModel().getSelectedItem() != null) 
	        {
				if (cb_perfil.getSelectionModel().getSelectedItem() != null)
				{
			tv_menu.getItems().remove(menuSeleccionado);
			ObservableList<Acceso> datos = FXCollections.observableArrayList();
			datos.setAll(tv_acceso.getItems());
				
			tv_acceso.getColumns().clear();
			Acceso nuevo = new Acceso();
			nuevo.setMenu(menuSeleccionado);
			nuevo.setPerfil(cb_perfil.getSelectionModel().getSelectedItem());
			nuevo.setAccesoEstado("A");
			datos.add(nuevo);
			
			
			TableColumn<Acceso, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(80);
			idColum.setCellValueFactory(new PropertyValueFactory<Acceso, String>("accesoId"));

			TableColumn<Acceso, String> nombreColum = new TableColumn<>("Nombre del Menu");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(330);
			nombreColum.setCellValueFactory(new PropertyValueFactory<Acceso, String>("menu"));
			
			tv_acceso.getColumns().addAll(idColum,nombreColum);
			tv_acceso.setItems(datos);
			
				}
	        }
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void remover()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	void llenar_Datos(){
		try{

			tv_menu.getColumns().clear();
			MenuDAO menuDAO = new MenuDAO();
			List<Menu> ListaMenu;
			List<Menu> ListaMenuAll;
			ListaMenu = menuDAO.getListaMenuAccesos();
			ListaMenuAll = menuDAO.getListaMenu();

			for(int i = 0 ; i < ListaMenu.size() ; i ++) {
				for(int j = 0 ; j < ListaMenuAll.size() ; j ++) {
					if(ListaMenu.get(i).getMenuIdpadre().equals(ListaMenuAll.get(j).getMenuId()))
						ListaMenu.get(i).setMenuDescripcion(ListaMenuAll.get(j).getMenuDescripcion() + "/" + ListaMenu.get(i).getMenuDescripcion());
				}
			}
			ObservableList<Menu> datos = FXCollections.observableArrayList();
			datos.setAll(ListaMenu);

			//llenar los datos en la tabla
			TableColumn<Menu, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(80);
			idColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("menuId"));

			TableColumn<Menu, String> nombreColum = new TableColumn<>("Nombre del Formulario");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(330);
			nombreColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("menuDescripcion"));


			tv_menu.getColumns().addAll(idColum,nombreColum);
			tv_menu.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


}

