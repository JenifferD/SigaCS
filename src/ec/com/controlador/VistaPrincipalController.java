package ec.com.controlador;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ec.com.modelo.Acceso;
import ec.com.modelo.AccesoDAO;
import ec.com.modelo.Menu;
import ec.com.modelo.MenuDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class VistaPrincipalController {
	private @FXML AnchorPane PaginaPrincipal;
	private @FXML SplitPane sp_principal;
	private @FXML Label lb_perfil;
	private @FXML Label lb_usuario;
	private @FXML Label Lb_fecha;
	private @FXML AnchorPane ap_derecha;
	private @FXML AnchorPane ap_izquierda;
	private @FXML AnchorPane ap_botones;
	private @FXML Accordion acd_menu;
	Calendar fecha = new GregorianCalendar();
	int anio,mes,dia;
	ControllerHelper helper = new ControllerHelper();
	int contador = 0,mayor = 0;
	AccesoDAO accesoDAO = new AccesoDAO();
	public void initialize(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		PaginaPrincipal.setPrefWidth(screenSize.width);
		PaginaPrincipal.setPrefHeight(screenSize.height);
		sp_principal.setDividerPositions(0.15,0.90);
		lb_usuario.setText(Context.getInstance().getUsuario());
		lb_perfil.setText(Context.getInstance().getPerfil());
		anio = fecha.get(Calendar.YEAR);
        mes = fecha.get(Calendar.MONTH);
        dia = fecha.get(Calendar.DAY_OF_MONTH);
		
        Lb_fecha.setText(""+ dia + "/" + (mes+1) + "/" + anio);
        
        helper.mostrarVentanaContenedor("/Principal/VistaPortada.fxml", ap_derecha);
        llenarMenu();
        //acd_menu.setMaxHeight(mayor*100);
	}
	
	void llenarMenu(){
		try{
			int contador;
			List<Acceso> listaAcceso = accesoDAO.getAccesoPerfil(Context.getInstance().getIdPerfil());
			List<Menu> menuList;
			
			//MenuDAO menuDAO = new MenuDAO();;
			TitledPane tp;
			List<Menu> menuListTitle = new ArrayList<Menu>();
			List<Menu> menuListCont = new ArrayList<Menu>();
			
			for(int i = 0 ; i < listaAcceso.size() ; i ++) {
				menuListTitle.add(listaAcceso.get(i).getMenu());
				menuListCont.add(listaAcceso.get(i).getMenu());	
			}
			
			
			for(int i = 0 ; i < menuListTitle.size() ; i ++) {
				
				if(menuListTitle.get(i).getMenuIdpadre() == 0 && menuListTitle.get(i).getMenuEstado().equals("A")) {
					contador = 0;
					ListView<Menu> lvMenu = new ListView<Menu>();
					menuList = new ArrayList<Menu>();
					tp = new TitledPane();
					tp.setText(menuListTitle.get(i).getMenuDescripcion());
					for(int j = 0 ; j < menuListCont.size() ; j ++) {
						if(menuListCont.get(j).getMenuIdpadre() == menuListTitle.get(i).getMenuId()) {
							Menu menu = new Menu();
							menu = menuListCont.get(j);
							menuList.add(menu);
							contador ++;
						}
					}
					if(contador != 0) {
						ObservableList<Menu> listaobservableMenu = FXCollections.observableArrayList(menuList);
						lvMenu.setItems(listaobservableMenu);
						lvMenu.setPrefHeight(contador * 30);
						lvMenu.setOnMouseClicked(new EventHandler<Event>() {
							@Override
							public void handle(Event event) {
								System.out.println(lvMenu.getSelectionModel().getSelectedItem().getMenuDescripcion());
								helper.mostrarVentanaContenedor(lvMenu.getSelectionModel().getSelectedItem().getMenuNombreForm(), ap_derecha);
							}
						});
						tp.setContent(lvMenu);
					}
					acd_menu.getPanes().add(tp);
				}
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}