package ArbreRouge_Noire;


import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApplication extends Application{

	@Override
	public void start(Stage window) throws Exception {
		
	        Parent root = FXMLLoader.load(getClass().getResource("arbreRN.fxml"));
	        Scene scene = new Scene(root);
	        window.setScene(scene);
	        window.setTitle("Arbre rouge et noir");
	        window.show();
	}
	
	 public static void main(String[] args) {
	        launch(args);
	    }

}
