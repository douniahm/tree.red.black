package ArbreRouge_Noire;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class FXMLController implements Initializable{
	ArbreRN arbre = new ArbreRN();
    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;
    
    @FXML
    private TextField champ_text;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_delete;
    
    @FXML
    private Button btn_deleteAll;
    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private ImageView image;

    @FXML
    private Label monNom;

    @FXML
    private Label maFilière;

    @FXML
    void handleButtonAction(ActionEvent event) {
    	
    	//Ajout
        if(event.getSource().equals(btn_add)){
            int numbre=Integer.parseInt(champ_text.getText());
            arbre.ajout(numbre);
            label1.setText("Number well added");
            champ_text.setText("");
            champ_text.requestFocus();
            tracerArbre(arbre.racine,null);      
        }
        
    	//Recherche
        else if(event.getSource().equals(btn_search)){
        int numbre=Integer.parseInt(champ_text.getText());
        if(arbre.racine.recherche(numbre)){
        	tracerArbre(arbre.racine,numbre);
             label1.setText("Number found");
             champ_text.setText("");
             champ_text.requestFocus();
        }
        else {
        	label1.setText("Inexistant number");
        	champ_text.setText("");
        	champ_text.requestFocus();
        }
         
        
    	//Suppression d'un noeud
     }else if(event.getSource().equals(btn_delete)){
              int numbre=Integer.parseInt(champ_text.getText());
              if(arbre.racine.recherche(numbre)){
            	  arbre.supprimer(numbre);
            	  tracerArbre(arbre.racine,null);
                   label1.setText("Well deleted");
                   champ_text.setText("");
                   champ_text.requestFocus();
              }
              else { label1.setText("Inexistant number");     
              champ_text.setText("");
              champ_text.requestFocus();

              }
            }
     else if(event.getSource().equals(btn_deleteAll)){
         arbre = new ArbreRN();
         pane2.getChildren().clear();
         label1.setText("Tree is deleted");
         champ_text.setText("");
         champ_text.requestFocus();
      }
       
    }
    
    //Nombre de feuille 
    private int getNbrFeuille(Noeud r){
        if(r.isSentinelle()) return 1;
        else return (getNbrFeuille(r.gauche)+getNbrFeuille(r.droit));
    }
    /*-------------------------------------------------------------------------------------------------------------*/
    //Tracer Rectangle
    private void tracerRectangle(float x,float y){
        Rectangle rec = new Rectangle(20, 20, Color.BLACK);
        rec.setLayoutX(x);
        rec.setLayoutY(y);
        pane2.getChildren().add(rec);
   }
    
    //Tracer Droite
    private void tracer_droite(float x1,float y1,float x2,float y2){
        Line line = new Line();
           line.setStartX(x1);
           line.setStartY(y1);
           line.setEndX(x2);
           line.setEndY(y2);
           pane2.getChildren().add(line);
   }
    
    //tracer cercle
   private void tracerCercle(float x,float y,Color c,Comparable info){
        
        Circle cercle=new Circle(x,y,15,c);
        Label label = new Label(""+info);
        if(Integer.parseInt(info.toString())<100)
        	label.setLayoutX(x-6);
        else  label.setLayoutX(x-9);
        	label.setLayoutY(y-9);
        label.setTextFill(Color.WHITE);
        pane2.getChildren().add(cercle);
        pane2.getChildren().add(label);  
    }
   
   //Tracer un disque auteur du noeud cherché
   private float tracerSearch(float x1,float x2,Noeud rac,float y,Comparable o){
       float xd=0,xg=0;
      int nbFeuille = getNbrFeuille(rac);
      int nbFeuilleG = getNbrFeuille(rac.gauche);
      float x = (nbFeuilleG*100/nbFeuille)*(x2-x1)/100 + x1;
      if(rac.info.equals(o)) {
          Circle c = new Circle(x, y,21, Color.BLACK);
          Circle c2 = new Circle(x, y,17.5, Color.WHITE);
          pane2.getChildren().addAll(c,c2); 
      }
      tracerCercle(x,y,rac.couleur,rac.info); 
      if(! rac.droit.isSentinelle()){
           xd =tracerSearch(x, x2, rac.droit, y+50,o);
           tracer_droite(x+15, y+8, xd, y+35);
      }
      else{
           if(! rac.gauche.isSentinelle()) tracer_droite(x+15, y+8, x+20, y+50);
          else tracer_droite(x, y+15, x+20, y+50);
          
           tracerRectangle(x+13, y+40);
      } 
      
      if(! rac.gauche.isSentinelle()){
          xg =tracerSearch(x1, x, rac.gauche, y+50,o);
          tracer_droite(x-15, y+8, xg, y+35);
      }
      else{
          if(! rac.droit.isSentinelle()) tracer_droite(x-15, y+8, x-20, y+50);
          else tracer_droite(x, y+15, x-20, y+50);
          tracerRectangle(x-27, y+40);
      }
      return x;
  }
    //Tracer Arbre
   private float tracerArbre(float x1,float x2,Noeud rac,float y){
       float xd=0,xg=0;
       
       int nbFeuille = getNbrFeuille(rac);
       int nbFeuilleG = getNbrFeuille(rac.gauche);
       float x = (nbFeuilleG*100/nbFeuille)*(x2-x1)/100 + x1;
       tracerCercle(x,y,rac.couleur,rac.info); 
       
       if(! rac.droit.isSentinelle()){
            xd =tracerArbre(x, x2, rac.droit, y+50);
            tracer_droite(x+15, y+8, xd, y+35);
       }
       else{
           if(! rac.gauche.isSentinelle()) tracer_droite(x+15, y+8, x+20, y+50);
           else tracer_droite(x, y+15, x+20, y+50);
           tracerRectangle(x+13, y+40);
       } 
       
       if(! rac.gauche.isSentinelle()){
           xg =tracerArbre(x1, x, rac.gauche, y+50);
           tracer_droite(x-15, y+8, xg, y+35);
       }
       else{
           
           if(! rac.droit.isSentinelle()) tracer_droite(x-15, y+8, x-20, y+50);
           else tracer_droite(x, y+15, x-20, y+50);
           tracerRectangle(x-27, y+40);
       }
       return x;
      
   }
   
   //Tracer Arbre 
   private void tracerArbre(Noeud rac,Comparable o){
       if(!rac.isSentinelle()){
           pane2.getChildren().clear();
           if(o==null) 
        	   tracerArbre(0,(float)pane2.getWidth(),rac,20);
           else
        	   tracerSearch(0,(float)pane2.getWidth(),rac,20,o);
       }
   }
    
    
	@Override
   public void initialize(URL url, ResourceBundle rb) {
        
       pane2.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
            
            }
        });
     
    }  

  
    
}
