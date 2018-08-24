package ec.com.main;


import ec.com.controlador.VistaInicioSesionController;
import ec.com.util.Encriptado;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		try {
			System.out.println(Encriptado.Encriptar("jenny"));
			FXMLLoader root = new FXMLLoader();
			root.setLocation(getClass().getResource("/Principal/VistaInicioSesion.fxml"));
			AnchorPane page = (AnchorPane) root.load();
			Scene scene = new Scene(page);
			stage.setScene(scene);
			stage.setTitle("Inicio de Sesion");
			VistaInicioSesionController inicio = (VistaInicioSesionController) root.getController();
			inicio.setDialogStage(stage);
			stage.setMaximized(true);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
