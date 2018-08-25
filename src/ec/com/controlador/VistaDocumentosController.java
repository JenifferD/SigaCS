package ec.com.controlador;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import ec.com.modelo.CsDocumento;
import ec.com.modelo.Documentos;
import ec.com.util.Context;
import ec.com.util.ControllerHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class VistaDocumentosController {
	@FXML private TextField txt_codigo;
	@FXML private TextField txt_ruta;
	@FXML private ComboBox<Documentos> cb_documentos;
	@FXML private Button bt_guardar;
	@FXML private Button bt_nuevo;
	@FXML private Button bt_cargar;
	@FXML private CheckBox ch_estado;
	@FXML private Pane p_documento;
	ControllerHelper helper = new ControllerHelper();

	Documentos[] documento = Documentos.values();

	public void initialize(){
		ch_estado.setSelected(true);
		llenarComboDocumentos();
		
		if (Context.getInstance().getDocumentos() != null) {
			CsDocumento documentoSeleccionado = Context.getInstance().getDocumentos();
			txt_codigo.setEditable(false);
			recuperarDatos(documentoSeleccionado);	
			Context.getInstance().setDocumentos(null);
		}	
	}
	
	public void recuperarDatos(CsDocumento documentoSeleccionado)
	{
		try {
		txt_codigo.setText(String.valueOf(documentoSeleccionado.getDocId()));
		for(Documentos g : documento ){
			if(g.toString().equals(documentoSeleccionado.getDocNombre()))
				cb_documentos.getSelectionModel().select(g);
		}
		
		if(documentoSeleccionado.getDocEstado().equals("A")) 
			ch_estado.setSelected(true);
		else
			ch_estado.setSelected(false);
		
		} catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
		
	void llenarComboDocumentos(){
		try{
			ObservableList<Documentos> listaDocumentos = FXCollections.observableArrayList(Documentos.values());
			cb_documentos.setItems(listaDocumentos);
			cb_documentos.setPromptText("--- Seleccione ---");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() throws IOException{
		limpiar();
	}
	
	void limpiar() {
		txt_codigo.setText("");
		txt_codigo.setEditable(false);
		ch_estado.setSelected(true);
		cb_documentos.getSelectionModel().clearSelection();
		
	}
	
	
	public void examinar(){
		FileChooser buscador = new FileChooser();
		buscador.setTitle("Buscar PDF");
		
		buscador.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PDF", "*.pdf")
				);
		File archivo = buscador.showOpenDialog(Context.getInstance().getStage());
		if (archivo != null) {
			
			File file = new File(archivo.getAbsolutePath());
			txt_ruta.setText(file.toString());
		}
		else
		{
			helper.mostrarAlertaError("No se ha seleccionado ningun archivo", Context.getInstance().getStage());
	
		}
	}
	
	

}
