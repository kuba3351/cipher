package com.urz.cipher.gui;

import com.urz.cipher.encryption.engine.Cipher;
import com.urz.cipher.encryption.engine.CipherList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private TextArea textToEncrypt;

    @FXML
    private TextField encryptionKey;

    @FXML
    private TextArea encryptedText;

    @FXML
    private ChoiceBox<Cipher> cipher;

    public void onDecryptButtonClick() throws IOException {
        Cipher selectedCipher = cipher.getValue();
        if (!selectedCipher.isKeyValid(encryptionKey.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cipher");
            alert.setHeaderText("Błąd deszyfrowania!");
            alert.setContentText("Coś jest nie tak z kluczem szyfrującym!");
            alert.show();
            return;
        }
        if (!selectedCipher.isTextValid(encryptedText.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cipher");
            alert.setHeaderText("Błąd deszyfrowania!");
            alert.setContentText("Coś jest nie tak z zaszyfrowanym tekstem!");
            alert.show();
            return;
        }
        String preparedText = selectedCipher.prepareText(encryptionKey.getText(), encryptedText.getText());
        String preparedKey = selectedCipher.prepareKey(encryptionKey.getText());
        String decrypted = selectedCipher.decrypt(preparedKey, preparedText);
        textToEncrypt.setText(decrypted);
    }

    public void onEncryptButtonClick() {
        Cipher selectedCipher = cipher.getValue();
        if (!selectedCipher.isKeyValid(encryptionKey.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cipher");
            alert.setHeaderText("Błąd szyfrowania!");
            alert.setContentText("Coś jest nie tak z kluczem szyfrującym!");
            alert.show();
            return;
        }
        if (!selectedCipher.isTextValid(textToEncrypt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cipher");
            alert.setHeaderText("Błąd szyfrowania!");
            alert.setContentText("Coś jest nie tak z szyfrogramem!");
            alert.show();
            return;
        }
        String preparedText = selectedCipher.prepareText(encryptionKey.getText(), textToEncrypt.getText());
        String preparedKey = selectedCipher.prepareKey(encryptionKey.getText());
        String encrypted = selectedCipher.encrypt(preparedKey, preparedText);
        encryptedText.setText(encrypted);
    }

    public void onEncryptedReadButtonClick() throws FileNotFoundException {
        encryptedText.setText(readFileToString());
    }

    private String readFileToString() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        bufferedReader.lines().forEach(builder::append);
        return builder.toString();
    }


    public void onEncryptedWriteButtonClick() throws IOException {
        BufferedWriter bufferedWriter = writeStreamToFile();
        bufferedWriter.write(encryptedText.getText());
        bufferedWriter.flush();
    }

    private BufferedWriter writeStreamToFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        return new BufferedWriter(new FileWriter(file));
    }


    public void onPlainReadButtonClick() throws FileNotFoundException {
        textToEncrypt.setText(readFileToString());
    }

    public void onPlainWriteButtonClick() throws IOException {
        BufferedWriter bufferedWriter = writeStreamToFile();
        bufferedWriter.write(textToEncrypt.getText());
        bufferedWriter.flush();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        cipher.getItems().addAll(CipherList.getCipherList());
        cipher.getSelectionModel().selectFirst();
    }
}
