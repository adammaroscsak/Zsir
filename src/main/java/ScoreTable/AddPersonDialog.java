/* 
 * Copyright (C) 2018 Adam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ScoreTable;

import static Controller.MainApp.homeWindow;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A stage for add a person into the score tabel.
 *
 * @author adam
 */
public class AddPersonDialog extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(AddPersonDialog.class);

    /**
     * The add person stage.
     */
    private static final AddPersonDialog addpersonstage = new AddPersonDialog();

    /**
     * Contructs the add person stage.
     */
    private AddPersonDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addPersonWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            setScene(scene);
            setResizable(false);
            initOwner(homeWindow);
            initStyle(StageStyle.UTILITY);
            initModality(Modality.WINDOW_MODAL);
        } catch (IOException ex) {
            logger.error("An error occured during the loading of the person adder window.", ex);
        }
    }

    /**
     * Gets the add person stage.
     *
     * @return the add person stage
     */
    public static AddPersonDialog getAddpersonstage() {
        return addpersonstage;
    }

}

