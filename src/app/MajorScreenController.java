package app;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MajorScreenController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox mainPane;

    @FXML
    private Button addButton;

    private ArrayList<Node> existNodes;

    @FXML
    private void initialize() {
        existNodes = new ArrayList<>();
        createNewNode("Фамилия","Test", "1");
        createNewNode("Дата рождения","13.10.2013", "S");
    }

    private void createNewNode(String label , String date ,String key) {
        int nodeId = existNodes.isEmpty() ? 0: existNodes.size()+ 1;
        Node keyIcon = createKeyIcon(key);
        GridPane pane = new GridPane();
        pane.setId("node_pane_"+nodeId);
        pane.setPadding(new Insets(10,10,10,10));
        pane.setMinWidth(633);
        pane.setVgap(2);
        pane.setHgap(4);


        TextField textField = new TextField();
        textField.setId("node_tf_"+nodeId);
        textField.setPrefWidth(474);
        textField.setPrefHeight(16);
        textField.setText(date);

        Label labelText = new Label(label);
        labelText.setId("node_label_"+nodeId);



        Button buttonEdit = new Button();
        buttonEdit.setGraphic(new ImageView(new Image("/resources/edit.png")));
        buttonEdit.setId("node_button_edit_"+nodeId);

        Button buttonDel = new Button();
        buttonDel.setGraphic(new ImageView(new Image("/resources/trash.png")));
        buttonDel.setId("node_button_del_"+nodeId);

        pane.add(labelText,0,0);
        pane.add(textField,0,1);
        pane.add(buttonEdit,1,1);
        pane.add(buttonDel, 2,1);
        pane.add(keyIcon,3,1,2,1);
        pane.setMargin(keyIcon,new Insets(0,0,0,10));

        mainPane.getChildren().add(pane);
        existNodes.add(pane);
    }

    //Helpers

    private Node createKeyIcon(String keySymbol) {
        ImageView keyImage = new ImageView(new Image("/resources/key.png"));
        HBox keyText = new HBox();
        keyText.setAlignment(Pos.CENTER);
        keyText.getChildren().add(new Text(keySymbol));
        StackPane keyIconWrapper = new StackPane();
        keyIconWrapper.getChildren().addAll(keyImage,keyText);

        return keyIconWrapper;
    }


}
