package tw.edu.ncku.csie.javaminesweeper;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class StartSceneController {
    @FXML
    private VBox root;

    @FXML
    private Label difficultyLabel;

    @FXML
    private MFXButton leftArrow, rightArrow;

    @FXML
    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void handleStartGameButton() throws IOException {
        Main.addFxml("gamescene");
    }

    @FXML
    protected void onSettingClicked(ActionEvent event) throws IOException {
        VBox pane = (VBox) Main.loadFxml("settingscene");
        pane.setStyle("-fx-background-color: white");
        Main.addNode(pane);
    }

    @FXML
    protected void handleChangeDifficultyLeft() {
        if (Level.getLevelString() == "Easy") {
            return;
        }
        if (Level.getLevelString() == "Medium") {
            leftArrow.setVisible(false);
        }
        rightArrow.setVisible(true);
        Level.changeLevel(2);
        difficultyLabel.setText(Level.getLevelString());
    }

    @FXML
    protected void handleChangeDifficultyRight() {
        if (Level.getLevelString() == "Hard") {
            return;
        }
        if (Level.getLevelString() == "Medium") {
            rightArrow.setVisible(false);
        }
        leftArrow.setVisible(true);
        Level.changeLevel(1);
        difficultyLabel.setText(Level.getLevelString());
    }

    @FXML
    protected void handleScoreBoardButton() {
        Rank.showRanking();
    }

    @FXML
    protected void handleAuthorButton() throws IOException {
        Main.addFxml("authorscene");
    }
}