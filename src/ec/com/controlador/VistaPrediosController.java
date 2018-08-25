package ec.com.controlador;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ec.com.modelo.CsPredio;
import ec.com.modelo.CsPredioDAO;
import ec.com.modelo.DetParametrica;
import ec.com.modelo.DetParametricaDAO;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaPrediosController {
	@FXML private Tab tb_registro_predio;
	@FXML private Tab tb_listado_predio;
	@FXML private TabPane tp_principal;
	
	@FXML private TextField txt_buscar;
	@FXML private TableView<CsPredio> tv_predios;
	
	@FXML private TextField txt_nombres;
	@FXML private TextField txt_codigo_predio;
	
	@FXML private Button bt_buscar;
	@FXML private Button bt_guardar;
	@FXML private Button bt_nuevo;
	@FXML private Button btn_nuevo;
	@FXML private TextField txt_area_total;
	@FXML private CheckBox ch_estado_predio;

	
	@FXML private ComboBox<DetParametrica> cb_tipo_predio;
	
	@FXML private TextField txt_norte;
	@FXML private TextField txt_sur;
	@FXML private TextField txt_este;
	@FXML private TextField txt_oeste;
	@FXML private TextField txt_norte_a;
	@FXML private TextField txt_sur_a;
	@FXML private TextField txt_este_a;
	@FXML private TextField txt_oeste_a;
	
	ControllerHelper helper = new ControllerHelper();
	DetParametricaDAO detparametricaDAO = new DetParametricaDAO();
	List<DetParametrica> datosTablaParametrica = detparametricaDAO.getListAll();
	DetParametrica seleccion = new DetParametrica();
	CsPredioDAO csPredioDAO = new CsPredioDAO();
	
	public void initialize(){
		llenarComboBarrios();
		llenar_Datos_tv_predio("");
		ch_estado_predio.setSelected(true);	
		
		txt_norte_a.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
					txt_sur_a.setText(newValue);
					DecimalFormat formato1 = new DecimalFormat("#.00");
					
					if (txt_este_a.getText().equals(""))
				    {
					txt_area_total.setText(newValue);
				    }
					else
					{
						Double valor1 = Double.parseDouble(txt_norte_a.getText());				
						Double valor2 = Double.parseDouble(txt_este_a.getText());
					    Double resultado = valor1 * valor2 ;
					    
					    txt_area_total.setText(resultado.toString());
					}		
			}
		});
		
		txt_este_a.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
					txt_oeste_a.setText(newValue);	
			}
		});
		
		txt_norte_a.setOnAction(new  EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

			}
		});
		
		
		
	}
	
	
	private void llenarComboBarrios(){
		try{
			cb_tipo_predio.setPromptText("--- Seleccione ---");
			List<DetParametrica> listaBarrios = new ArrayList<DetParametrica>();
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getCabParametrica().getCabpCod().equals("TiP01"))
					listaBarrios.add(dato);
			}
			ObservableList<DetParametrica> datos = FXCollections.observableArrayList();
			datos.addAll(listaBarrios);
			cb_tipo_predio.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		tv_predios.setRowFactory(tv -> {
            TableRow<CsPredio> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	if(tv_predios.getSelectionModel().getSelectedItem() != null){
                		llenarDatos(tv_predios.getSelectionModel().getSelectedItem());
                		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
                		selectionModel.select(tb_registro_predio);
    				}
                }
            });
            return row ;
        });
		
	}
	
	public void nuevo() throws IOException{
		limpiar();
	}
	
	void limpiar()
	{
		txt_codigo_predio.setText("");
		txt_nombres.setText("");
		cb_tipo_predio.getSelectionModel().clearSelection();
		ch_estado_predio.setSelected(true);	
		txt_norte.setText("");
		txt_sur.setText("");
		txt_este.setText("");
		txt_oeste.setText("");
		txt_norte_a.setText("");
		txt_sur_a.setText("");
		txt_este_a.setText("");
		txt_oeste_a.setText("");
		txt_area_total.setText("");
	}
	
	public void grabar()
	{
		try {
			String estado;
			//if(validarDatos() == false){
				//return;
			//}
			if(ch_estado_predio.isSelected() == true)
				estado = "A";
			else
				estado = "I";
			CsPredio csPredio = new CsPredio();
			
			csPredio.setPredEstado(estado);
			csPredio.setPgTpredio(cb_tipo_predio.getSelectionModel().getSelectedItem().getDetpId());
			csPredio.setPredNombre(txt_nombres.getText());
			csPredio.setPredLindnort(txt_norte.getText());
			csPredio.setPredLindsur(txt_sur.getText());
			csPredio.setPredLindeste(txt_este.getText());
			csPredio.setPredLindoeste(txt_oeste.getText());
			csPredio.setPredNortMts(Double.parseDouble(txt_norte_a.getText()));
			csPredio.setPredSurMts(Double.parseDouble(txt_sur_a.getText()));
			csPredio.setPredEsteMts(Double.parseDouble(txt_este_a.getText()));
			
			csPredio.setPredOesteMts(Double.parseDouble(txt_oeste_a.getText()));
			csPredio.setPredAreaTotal(Double.parseDouble(txt_area_total.getText()));
			
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				csPredio.setPredEstado(estado);
				csPredioDAO.getEntityManager().getTransaction().begin();
				if(txt_codigo_predio.getText().equals("")) {//inserta
					csPredio.setPredId(null);
					csPredioDAO.getEntityManager().persist(csPredio);
				}else {//modifica
					csPredio.setPredId(Integer.parseInt(txt_codigo_predio.getText()));
					csPredioDAO.getEntityManager().merge(csPredio);
				}
				
				csPredioDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			csPredioDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
	}
	}
	
	void llenarDatos(CsPredio datoSeleccionado){
		try {
			txt_codigo_predio.setText(String.valueOf(datoSeleccionado.getPredId()));
			if(datoSeleccionado.getPredNombre() == null)
				txt_nombres.setText("");
			else
				txt_nombres.setText(datoSeleccionado.getPredNombre());

			if(datoSeleccionado.getPredLindnort() == null)
				txt_norte.setText("");
			else
				txt_norte.setText(datoSeleccionado.getPredLindnort());
			if(datoSeleccionado.getPredEstado().equals("A")) 
				ch_estado_predio.setSelected(true);
			else
				ch_estado_predio.setSelected(false);
			
			for(DetParametrica dato : datosTablaParametrica) {
				if(dato.getDetpId().equals(datoSeleccionado.getPgTpredio()))
					seleccion = dato;
			}
			cb_tipo_predio.getSelectionModel().select(seleccion);
			
			if(datoSeleccionado.getPredLindsur() == null)
				txt_sur.setText("");
			else
				txt_sur.setText(datoSeleccionado.getPredLindsur());
			
			if(datoSeleccionado.getPredLindeste() == null)
				txt_este.setText("");
			else
				txt_este.setText(datoSeleccionado.getPredLindeste());
			
			if(datoSeleccionado.getPredLindoeste() == null)
				txt_oeste.setText("");
			else
				txt_oeste.setText(datoSeleccionado.getPredLindoeste());
			
			if(Double.toString(datoSeleccionado.getPredNortMts()) == null)
				txt_norte_a.setText("");
			else
				txt_norte_a.setText(Double.toString(datoSeleccionado.getPredNortMts()));
			
			if(Double.toString(datoSeleccionado.getPredSurMts()) == null)
				txt_sur_a.setText("");
			else
				txt_sur_a.setText(Double.toString(datoSeleccionado.getPredSurMts()));
			
			if(Double.toString(datoSeleccionado.getPredEsteMts()) == null)
				txt_este_a.setText("");
			else
				txt_este_a.setText(Double.toString(datoSeleccionado.getPredEsteMts()));
			
			if(Double.toString(datoSeleccionado.getPredOesteMts()) == null)
				txt_oeste_a.setText("");
			else
				txt_oeste_a.setText(Double.toString(datoSeleccionado.getPredOesteMts()));
			
			if(Double.toString(datoSeleccionado.getPredAreaTotal()) == null)
				txt_area_total.setText("");
			else
				txt_area_total.setText(Double.toString(datoSeleccionado.getPredAreaTotal()));
			
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void buscar_predios()
	{
		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
		selectionModel.select(tb_listado_predio);
		txt_buscar.requestFocus();
	}
	
	void llenar_Datos_tv_predio(String busqueda){
		try{
			//String estado = "";
			tv_predios.getColumns().clear();
			List<CsPredio> listaPredios;
			ObservableList<CsPredio> datos = FXCollections.observableArrayList();
			listaPredios = csPredioDAO.getBuscarPredio(busqueda);
			datos.setAll(listaPredios);

			//llenar los datos en la tabla
			TableColumn<CsPredio, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(60);
			idColum.setCellValueFactory(new PropertyValueFactory<CsPredio, String>("predId"));
			
			TableColumn<CsPredio, String> nombreColum = new TableColumn<>("Nombre del Predio");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(150);
			nombreColum.setCellValueFactory(new PropertyValueFactory<CsPredio, String>("predNombre"));

			TableColumn<CsPredio, Double> areaColum = new TableColumn<>("Área del Predio ");
			areaColum.setMinWidth(10);
			areaColum.setPrefWidth(180);
			areaColum.setCellValueFactory(new PropertyValueFactory<CsPredio, Double>("predAreaTotal"));
			
			TableColumn<CsPredio, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(50);
			estadoColum.setCellValueFactory(new PropertyValueFactory<CsPredio, String>("predEstado"));
			
			tv_predios.getColumns().addAll(idColum,nombreColum, areaColum,estadoColum);
			tv_predios.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo_predio()
	{
		SingleSelectionModel<Tab> selectionModel = tp_principal.getSelectionModel();
		selectionModel.select(tb_registro_predio);
		txt_nombres.requestFocus();
	}
	
	
}