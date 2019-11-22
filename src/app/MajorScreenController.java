package app;

import app.Beans.KeyField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MajorScreenController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox mainPane;

    @FXML
    private Button addButton;

    @FXML
    private Text activity_label;

    private ArrayList<Node> existNodes;

    @FXML
    private void initialize() {
        Main.majorScreenController = this;
        if(Main.active) {
            setActivity_label(true);
        }
        existNodes = new ArrayList<>();
        fillScrollList();
    }

    private void createNewNode(String label , String date ,String key) {
        int nodeId = existNodes.isEmpty() ? 0: existNodes.size()+ 1;
        Node keyIcon = createKeyIcon(key);
        keyIcon.setId("key_icon_"+nodeId);
        GridPane pane = new GridPane();
        //pane.setGridLinesVisible(true);
        pane.setId("node_pane_"+nodeId);
        pane.setPadding(new Insets(10,10,10,10));
        pane.setAlignment(Pos.CENTER);
        //pane.setMinWidth(633);
        pane.setVgap(3);
        pane.setHgap(3);


        Text textField = new Text();
        textField.setId("node_tf_"+nodeId);
        textField.setText(date);
        ScrollPane textFiledBox = new ScrollPane();
        textFiledBox.setStyle("-fx-border-color: grey;");
        textFiledBox.setMinWidth(474);
        textFiledBox.setMaxWidth(474);
        textFiledBox.setFitToWidth(true);
        textFiledBox.setContent(textField);

        Label labelText = new Label(label);
        labelText.setId("node_label_"+nodeId);



        Button buttonEdit = new Button();
        buttonEdit.setGraphic(new ImageView(new Image("/resources/edit.png")));
        buttonEdit.setId("node_button_edit_"+nodeId);

        Button buttonDel = new Button();
        buttonDel.setGraphic(new ImageView(new Image("/resources/trash.png")));
        buttonDel.setId("node_button_del_"+nodeId);

        buttonDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button pressedButton = (Button) event.getTarget();
                String buttonIdString = pressedButton.getId();
                String id = getIdFromString(buttonIdString);

                if(!id.isEmpty()) {
                    deleteNode(id);
                }
            }
        });

        pane.add(labelText,0,0);
        pane.add(textFiledBox,0,1, 1,2);
        pane.add(keyIcon,1,1,1,2);
        pane.add(buttonEdit,2,1);
        pane.add(buttonDel, 2,2);
        pane.setMargin(keyIcon,new Insets(0,10,0,10));
        pane.setMargin(buttonDel,new Insets(5,0,0,0));

        mainPane.getChildren().add(pane);
        existNodes.add(pane);
    }

    @FXML
    private void showAddModal() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewWindow.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);

        EventHandler<WindowEvent> eventHandler = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(event.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) { ;
                    fillScrollList();
                }
            }
        };

        stage.setOnCloseRequest(eventHandler);
        stage.show();
    }

    public void fillScrollList() {
        mainPane.getChildren().clear();
        HashMap<String,KeyField> storage = Db.getStorage();
        for(Map.Entry<String,KeyField> entry : storage.entrySet() ) {
            createNewNode(entry.getValue().getFieldName(),entry.getValue().getFieldData(),entry.getValue().getKeySymbol());
        }
    }

    public void deleteNode(String id) {
        String nodeId = "node_pane_"+id;
        Node node = mainPane.lookup("#"+nodeId);
        mainPane.getChildren().removeAll(node);
        Iterator<Node> iterator = existNodes.iterator();

        Node keyIcon = node.lookup("#key_icon_"+id);
        String buttonText = getButtonText(keyIcon).getText();

        HashMap<String,KeyField> storage = Db.getStorage();
        storage.remove(buttonText);
        Db.saveStorage(storage);
        Main.localStorage.remove(buttonText);

        while (iterator.hasNext()) {
            Node existNode = iterator.next();

            if(existNode.getId().equals(nodeId)) {
                iterator.remove();
            }
        }
    }



    //Helpers

    public Node createKeyIcon(String keySymbol) {
        ImageView keyImage = new ImageView(new Image("/resources/key2.png"));
        HBox keyText = new HBox();
        keyText.setAlignment(Pos.CENTER);
        keyText.getChildren().add(new Text(keySymbol));
        StackPane keyIconWrapper = new StackPane();
        keyIconWrapper.getChildren().addAll(keyImage,keyText);

        return keyIconWrapper;
    }

    public Text getButtonText(Node keyIcon) {
        StackPane stackPane = (StackPane) keyIcon;
        HBox hBox = (HBox) stackPane.getChildren().get(1);
        Text text = (Text)hBox.getChildren().get(0);
        return text;
    }

    public ImageView getButtonImage(Node keyIcon) {
        StackPane stackPane = (StackPane) keyIcon;
        ImageView imageView = (ImageView) stackPane.getChildren().get(0);
        return imageView;
    }

    public void setActivity_label(Boolean active) {

        if(active) {
            activity_label.setText("Замены работают!");
            activity_label.setFill(Color.RED);
        } else {
            activity_label.setText("Замены отключены.");
            activity_label.setFill(Color.GREEN);
        }
    }

    public String getIdFromString(String text) {
        String id = "";
        Pattern pattern = Pattern.compile("\\d{1,}");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
           id = matcher.group();
        }

        return id;
    }


}
