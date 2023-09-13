package org.example.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.App;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectView implements Initializable {

    @FXML
    private ComboBox<String> algo_select;

    @FXML
    private Label encryptImgLabel;

    @FXML
    private Label decryptImgLabel;
    @FXML
    private PasswordField decryptKey;
    @FXML
    private PasswordField encryptKey;
    @FXML
    private TextArea msgTxtArea;
    @FXML
    private TextArea encryptMsg;
    @FXML
    private Text encryptWarn;
    @FXML
    private Text decryptWarn;

    private List<String> lstFiles;
    private File encryptFile;
    private File decryptFile;

    App app = new App();

    public ProjectView() throws Exception {
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        algo_select.setItems(FXCollections.observableArrayList("AES", "RC6", "RSA"));
        algo_select.getSelectionModel().selectFirst();
        msgTxtArea.setEditable(false);

        lstFiles = new ArrayList<>();
        lstFiles.add("*.png");
    }

    @FXML
    void onEncryptImageSelect(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", lstFiles));
        File f = fc.showOpenDialog(null);
        encryptFile = f;
        if (f != null) {
            encryptImgLabel.setText(f.getAbsolutePath());
        }
    }

    @FXML
    void onDecryptImageSelect(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", lstFiles));
        File f = fc.showOpenDialog(null);
        decryptFile = f;
        if (f != null) {
            decryptImgLabel.setText(f.getAbsolutePath());
        }
    }

    @FXML
    void handleEncrypt(ActionEvent event) throws Exception {
        if (encryptFile == null) {
            encryptWarn.setText("Please select a image");
        } else {
            if (encryptKey.getText().isEmpty()){
                encryptWarn.setText("Please enter the encryption key");
            }else {
            app.setPassword(encryptKey.getText());

            BufferedImage originalImage = ImageIO.read(encryptFile);
//
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
            ImageWriter writer = writers.next();
//        // Get the original image metadata
            ImageReader reader = ImageIO.getImageReader(writer);
            reader.setInput(ImageIO.createImageInputStream(encryptFile));
            IIOMetadata metadata = reader.getImageMetadata(0);
//
//        // Create a FileImageOutputStream to write the image
            File outputFile = new File("/run/media/nims/Files/Dev/IdeaProjects/image-steganography/src/main/resources/modified-1.png");
            FileImageOutputStream output = new FileImageOutputStream(outputFile);

            // Embed the message
            BufferedImage modifiedImage = app.embedMessage(encryptMsg.getText(), originalImage);
            writer.setOutput(output);
//
//        // Create an IIOImage with metadata preservation
            IIOImage imageWithMetadata = new IIOImage(modifiedImage, null, metadata);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//        // Write the image without compression
            writer.write(null, imageWithMetadata, param);
//
//        // Close the output stream
            output.close();
            // Encryption logic
        }
        }
    }

    @FXML
    void handleDecrypt(ActionEvent event) throws Exception {
        if (decryptFile == null) {
            decryptWarn.setText("Please select a image");
        }else {
            if (decryptKey.getText().isEmpty()){
                decryptWarn.setText("Please enter the key");
            }else {
                // Decryption logic
                app.setPassword(decryptKey.getText());
                BufferedImage modImage = ImageIO.read(decryptFile);
                // Extract the message
                String extractedMessage = app.extractMessage(modImage);
                System.out.println("Extracted Message: " + extractedMessage);
                msgTxtArea.setText(extractedMessage);
            }
        }
    }
}
