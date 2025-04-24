/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.User;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 *
 * @author admin
 */
public class Utils {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? DEFAULT_DATE_TIME_FORMATTER.format(dateTime) : "";
    }

    public static String formatCurrency(BigDecimal amount) {
        DecimalFormat formatter = new DecimalFormat("#,###₫");
        return formatter.format(amount);
    }

    public static void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        return alert.showAndWait();
    }

    public static <S, T> TableColumn<S, T> createColumn(
            String title,
            String fieldName,
            double width) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        col.setPrefWidth(width);

        return col;
    }

    public static <T> TableColumn<T, Void> createButtonColumn(
            String title,
            String btnText,
            Color btnBackgroundColor,
            Color btnTextColor,
            Consumer<T> handler // hàm xử lý khi click button
    ) {
        TableColumn<T, Void> col = new TableColumn<>(title);

        col.setCellFactory(param -> new TableCell<>() {
            Button btn = new Button(btnText);

            {
                BackgroundFill bgFill = new BackgroundFill(btnBackgroundColor, new CornerRadii(5), null);
                Background bg = new Background(bgFill);
                btn.setBackground(bg);
                btn.setTextFill(btnTextColor);

                btn.setOnAction(event -> {
                    T item = getTableView().getItems().get(getIndex());
                    handler.accept(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn); // hiện button nếu không rỗng
            }
        });

        col.setPrefWidth(80);
        return col;
    }

    public static <T> TableColumn<T, Void> createToggleButtonColumn(
            String title,
            Predicate<T> condition, // Hàm kiểm tra trạng thái
            String textWhenTrue,
            String textWhenFalse,
            String bgColorWhenTrue,
            String bgColorWhenFalse,
            Consumer<T> handler // Hàm xử lý khi click button
    ) {
        TableColumn<T, Void> col = new TableColumn<>(title);

        col.setCellFactory(param -> new TableCell<>() {
            Button btn = new Button();

            {
                btn.setOnAction(event -> {
                    T item = getTableView().getItems().get(getIndex());
                    handler.accept(item);
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setStyle("");
                } else {
                    T currentItem = getTableView().getItems().get(getIndex());
                    boolean isActive = condition.test(currentItem);

                    // set button
                    btn.setText(isActive ? textWhenTrue : textWhenFalse);
                    btn.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white;",
                            isActive ? bgColorWhenTrue : bgColorWhenFalse));

                    setGraphic(btn);
                }
            }
        });

        col.setPrefWidth(100);
        return col;
    }
}
