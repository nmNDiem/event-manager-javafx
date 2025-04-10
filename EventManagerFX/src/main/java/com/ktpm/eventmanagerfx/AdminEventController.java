/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.Category;
import com.ktpm.pojo.Event;
import com.ktpm.pojo.Location;
import com.ktpm.services.CategoryServices;
import com.ktpm.services.EventServices;
import com.ktpm.services.LocationServices;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AdminEventController implements Initializable {

    @FXML
    TextField txtId;
    @FXML
    TextField txtName;
    @FXML
    ComboBox<Category> cbbCate;
    @FXML
    ComboBox<Location> cbbLocation;
    @FXML
    TextField txtTickeks;
    @FXML
    TextField txtPrice;
    @FXML
    TextArea txtDesc;
    @FXML
    ImageView eventImgView;
    @FXML
    DatePicker endDatePk;
    @FXML
    DatePicker startDatePk;
    @FXML
    Spinner<LocalTime> startTimeSpn;
    @FXML
    Spinner<LocalTime> endTimeSpn;
    @FXML
    TableView<Event> eventTable;
    @FXML
    Button btnAddEvent;
    @FXML
    Button btnUpdateEvent;
    @FXML
    Button btnClear;

    EventServices eventServices = new EventServices();
    CategoryServices categoryServices = new CategoryServices();
    LocationServices locationServices = new LocationServices();

    private static File selectedFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createColumns();
        loadTableData();
        loadComboBoxes();
        setupTimeSpinner(startTimeSpn);
        setupTimeSpinner(endTimeSpn);

        eventTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    try {
                        loadEventToForm(selectedEvent);
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminEventController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    private void createColumns() {
        TableColumn<Event, Integer> idCol = Utils.createColumn("ID", "id", 30);
        TableColumn<Event, Integer> categoryCol = Utils.createColumn("Thể loại", "categoryId", 80);
        TableColumn<Event, String> nameCol = Utils.createColumn("Tên sự kiện", "name", 200);
        TableColumn<Event, Integer> locationCol = Utils.createColumn("Địa điểm", "locationId", 130);
        TableColumn<Event, LocalDateTime> startCol = Utils.createColumn("Thời gian bắt đầu", "startTime", 150);
        TableColumn<Event, LocalDateTime> endCol = Utils.createColumn("Thời gian kết thúc", "endTime", 150);
        TableColumn<Event, Integer> ticketCol = Utils.createColumn("Vé còn", "availableTickets", 70);
        TableColumn<Event, BigDecimal> priceCol = Utils.createColumn("Giá vé", "price", 90);
        TableColumn<Event, String> imageCol = Utils.createColumn("Ảnh", "imageUrl", 100);
        TableColumn<Event, String> descCol = Utils.createColumn("Mô tả", "description", 200);

        eventTable.getColumns().addAll(
                idCol, categoryCol, nameCol, locationCol,
                startCol, endCol, ticketCol, priceCol,
                imageCol, descCol
        );
    }

    public void loadTableData() {
        try {
            eventTable.setItems(eventServices.getEvents());
        } catch (SQLException ex) {
            Utils.showAlert("Lỗi: Không thể tải danh sách sự kiện!");
            Logger.getLogger(AdminEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadComboBoxes() {
        try {
//            categoryList = categoryServices.getCates();
            cbbCate.setItems(categoryServices.getCates());
        } catch (SQLException ex) {
            Utils.showAlert("Lỗi: Không thể tải danh sách thể loại!");
            Logger.getLogger(AdminEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
//            locationList = locationServices.getLocations();
            cbbLocation.setItems(locationServices.getLocations());
        } catch (SQLException ex) {
            Utils.showAlert("Lỗi: Không thể tải danh sách địa điểm!");
            Logger.getLogger(AdminEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupTimeSpinner(Spinner<LocalTime> spinner) {
        spinner.setValueFactory(new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new StringConverter<>() {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

                    public String toString(LocalTime t) {
                        return t != null ? fmt.format(t) : "";
                    }

                    public LocalTime fromString(String s) {
                        return LocalTime.parse(s, fmt);
                    }
                });
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps * 15));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps * 15));
            }
        });

        spinner.setEditable(true);
    }

    public void loadEventToForm(Event e) throws SQLException {
        txtId.setText(String.valueOf(e.getId()));
        txtName.setText(e.getName());
        cbbCate.setValue(categoryServices.getCateById(e.getCategoryId()));
        cbbLocation.setValue(locationServices.getLocationById(e.getLocationId()));
        txtTickeks.setText(String.valueOf(e.getAvailableTickets()));
        txtPrice.setText(String.valueOf(e.getPrice()));
        txtDesc.setText(e.getDescription());

        // Set ngày và giờ (tách từ LocalDateTime)
        LocalDateTime start = e.getStartTime();
        LocalDateTime end = e.getEndTime();

        startDatePk.setValue(start.toLocalDate());
        startTimeSpn.getValueFactory().setValue(start.toLocalTime());

        endDatePk.setValue(end.toLocalDate());
        endTimeSpn.getValueFactory().setValue(end.toLocalTime());

        // Load ảnh
        String imgUrl = e.getImageUrl();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            try {
                eventImgView.setImage(new Image(imgUrl, true));
            } catch (Exception ex) {
                Utils.showAlert("Lỗi: Không thể hiển thị ảnh sự kiện!");
                eventImgView.setImage(new Image("file:images/upload-img.jpg"));
            }
        } else {
            eventImgView.setImage(new Image("file:images/upload-img.jpg"));
        }
    }

    @FXML
    public void addEventHandler() {
        if (selectedFile != null) {
            // sao chép ảnh vừa chọn từ máy vào resources
            String targetPath = "src/main/resources/images/" + selectedFile.getName();
            try {
                Files.copy(selectedFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Utils.showAlert("Lỗi: Lưu ảnh thất bại!");
                Logger.getLogger(AdminEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image image = new Image(selectedFile.toURI().toString());
            eventImgView.setImage(image);
        }

        // Lấy dữ liệu từ các ô input
        String name = txtName.getText();
        Category category = cbbCate.getValue();
        Location location = cbbLocation.getValue();
        LocalDate startDate = startDatePk.getValue();
        LocalDate endDate = endDatePk.getValue();
        LocalTime startTime = startTimeSpn.getValue();
        LocalTime endTime = endTimeSpn.getValue();
        String tickets = txtTickeks.getText();
        String price = txtPrice.getText();
        String imageUrl = eventImgView.getImage().getUrl();
        String description = txtDesc.getText();

        int availableTickets = Integer.parseInt(tickets);
        LocalDateTime startDatetime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        if (name.isEmpty() || description.isEmpty() || category == null || location == null
                || startDate == null || endDate == null || startTime == null || endTime == null
                || tickets.isEmpty() || price.isEmpty()) {
            Utils.showAlert("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Kiểm tra ngày tổ chức < ngày hiện tại
        if (startDatePk.getValue().isBefore(LocalDate.now())) {
            Utils.showAlert("Ngày tổ chức phải lớn hơn ngày hiện tại!");
            return;
        }

        // Kiểm tra trùng địa điểm và giờ
        if (eventServices.isEventTimeConflict(location.getId(), startDatetime, endDateTime)) {
            Utils.showAlert("Lỗi: Trùng địa điểm và giờ với sự kiện khác!");
            return;
        }

        // Kiểm tra số lượng khách không vượt quá sức chứa địa điểm
        if (availableTickets > location.getCapacity()) {
            Utils.showAlert("Số lượng khách không được vượt quá sức chứa địa điểm!");
            return;
        }

        Event event = new Event();
        event.setName(name);
        event.setCategoryId(category.getId());
        event.setLocationId(location.getId());
        event.setStartTime(startDatetime);
        event.setEndTime(endDateTime);
        event.setAvailableTickets(availableTickets);
        event.setPrice(new BigDecimal(price));
        event.setImageUrl(imageUrl);
        event.setDescription(description);

        if (eventServices.addEvent(event)) {
            Utils.showAlert("Thêm sự kiện thành công!");
            loadTableData();
        } else {
            Utils.showAlert("Lỗi: Thêm sự kiện thất bại!");
        }
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // mở hộp thoại chọn file
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            eventImgView.setImage(image);
        }
    }

}
