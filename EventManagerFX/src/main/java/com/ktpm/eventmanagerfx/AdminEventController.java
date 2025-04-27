/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.Category;
import com.ktpm.pojo.Event;
import com.ktpm.pojo.Location;
import com.ktpm.pojo.Registration;
import com.ktpm.pojo.User;
import com.ktpm.services.CategoryServices;
import com.ktpm.services.EventServices;
import com.ktpm.services.LocationServices;
import com.ktpm.services.NotiServices;
import com.ktpm.services.RegistrationServices;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
    @FXML
    Button btnUploadImg;

    EventServices eventServices = new EventServices();
    CategoryServices categoryServices = new CategoryServices();
    LocationServices locationServices = new LocationServices();
    RegistrationServices regServices = new RegistrationServices();
    NotiServices notiServices = new NotiServices();

    private static Event selectedEvent;
    private static File selectedFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createColumns();
        loadTableData();
        createDeleteColumn();
        createCancelColumn();
        loadComboBoxes();
        setupTimeSpinner(startTimeSpn);
        setupTimeSpinner(endTimeSpn);
        setVisibleImg(false, true);

        // Nếu event bị hủy => dòng có background xám
        eventTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (event != null && !event.isActive()) {
                    setStyle("-fx-background-color: #6e6c6c;");
                } else {
                    setStyle("");
                }
            }
        });

        // xử lý khi click vào 1 event trong table
        eventTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectedEvent = eventTable.getSelectionModel().getSelectedItem();
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

    public void createColumns() {
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

    public void createDeleteColumn() {
        TableColumn<Event, Void> actionCol = Utils.createButtonColumn("", "Xóa", Color.ORANGERED, Color.WHITE, event -> {
            Optional<ButtonType> result = Utils.showConfirmAlert("Xác nhận xóa sự kiện [" + event.getName() + "]?");

            if (result.isPresent() && result.get() == ButtonType.YES) {
                if (eventServices.deleteEventById(event.getId())) {
                    eventTable.getItems().remove(event);
                    Utils.showAlert("Xóa sự kiện thành công!");
                } else {
                    Utils.showAlert("Lỗi: Xóa sự kiện thất bại!");
                }
            }
        });

        eventTable.getColumns().add(0, actionCol);
    }

    public void createCancelColumn() {
        TableColumn<Event, Void> toggleCol = Utils.createToggleButtonColumn(
                "Trạng thái",
                Event::isActive,
                "Hủy sự kiện", "Khôi phục",
                "orange", "green",
                event -> {
                    boolean newStatus = !event.isActive();

                    if (event.getEndTime().isBefore(LocalDateTime.now())) {
                        Utils.showAlert("Không thể thay đổi trạng thái sự kiện đã kết thúc!");
                        return;
                    }

                    Optional<ButtonType> result = Utils.showConfirmAlert(
                            newStatus ? "Xác nhận khôi phục sự kiện này?"
                                    : "Xác nhận hủy sự kiện này và hoàn tiền cho tất cả người tham gia?");

                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        if (eventServices.setEventActive(event.getId(), newStatus)) {
                            event.setActive(newStatus);
                            Utils.showAlert(newStatus ? "Khôi phục sự kiện thành công!" : "Đã hủy sự kiện!");

                            // nếu hủy sự kiện => thông báo + hoàn tiền
                            if (!newStatus) {
                                sendCancelNoti(event);
                                refund(event.getId());
                            }
                        } else {
                            Utils.showAlert("Cập nhật trạng thái thất bại!");
                        }
                    }

                }
        );

        eventTable.getColumns().add(1, toggleCol);
    }

    public void refund(int eventId) {
        List<Registration> regList = regServices.getRegsByEventId(eventId, "PAID");

        for (Registration r : regList) {
            if (regServices.updatePaymentStatus(r)) {
                r.setPaymentStatus("REFUNDED");
            } else {
                Utils.showAlert("Xảy ra lỗi khi hoàn tiền cho user " + r.getUserId());
            }
        }
        Utils.showAlert("Đã hoàn tất việc hoàn tiền!");
    }

    public void loadComboBoxes() {
        try {
            cbbCate.setItems(categoryServices.getCates());
        } catch (SQLException ex) {
            Utils.showAlert("Lỗi: Không thể tải danh sách thể loại!");
            Logger.getLogger(AdminEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
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

                    @Override
                    public String toString(LocalTime t) {
                        return t != null ? fmt.format(t) : "";
                    }

                    @Override
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
                eventImgView.setImage(new Image(getClass().getResource(imgUrl).toExternalForm()));
                setVisibleImg(true, false);
            } catch (Exception ex) {
                Utils.showAlert("Lỗi: Không thể hiển thị ảnh sự kiện!");
                eventImgView.setImage(null);
                setVisibleImg(false, true);
            }
        } else {
            Utils.showAlert("Lỗi: Ảnh không tồn tại!");
            eventImgView.setImage(null);
            setVisibleImg(false, true);
        }
    }

    @FXML
    public void addEventHandler() {
        // lấy dữ liệu từ các ô input
        String name = txtName.getText().trim();
        Category category = cbbCate.getValue();
        Location location = cbbLocation.getValue();
        LocalDate startDate = startDatePk.getValue();
        LocalDate endDate = endDatePk.getValue();
        LocalTime startTime = startTimeSpn.getValue();
        LocalTime endTime = endTimeSpn.getValue();
        String tickets = txtTickeks.getText().trim();
        String price = txtPrice.getText().trim();
        Image image = eventImgView.getImage();
        String description = txtDesc.getText().trim();

        if (name.isEmpty() || description.isEmpty() || category == null || location == null
                || startDate == null || endDate == null || startTime == null || endTime == null
                || tickets.isEmpty() || price.isEmpty() || image == null) {
            Utils.showAlert("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        LocalDateTime startDatetime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        // Kiểm tra ngày tổ chức < ngày hiện tại
        if (startDatePk.getValue().isBefore(LocalDate.now())) {
            Utils.showAlert("Ngày tổ chức phải lớn hơn ngày hiện tại!");
            return;
        }

        // Kiểm tra trùng địa điểm và giờ
        if (eventServices.isEventLocationTimeConflict(0, location.getId(), startDatetime, endDateTime)) {
            Utils.showAlert("Lỗi: Trùng địa điểm và giờ với sự kiện khác!");
            return;
        }

        int availableTickets = Integer.parseInt(tickets);
        // Kiểm tra số lượng khách không vượt quá sức chứa địa điểm
        if (availableTickets > location.getCapacity()) {
            Utils.showAlert("Số lượng khách không được vượt quá sức chứa địa điểm!");
            return;
        }

        String imageUrl;
        // nếu chọn ảnh mới thì lưu vào db với url này
        if (selectedFile != null) {
            imageUrl = "/images/" + selectedFile.getName();
        } else {
            imageUrl = selectedEvent.getImageUrl();
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
            if (selectedFile != null) {
                copyImgIntoResources();
            }
            Utils.showAlert("Thêm sự kiện thành công!");
            clearForm();
            loadTableData();
        } else {
            Utils.showAlert("Lỗi: Thêm sự kiện thất bại!");
        }
    }

    @FXML
    public void updateEventHandler() {
        if (selectedEvent == null) {
            Utils.showAlert("Vui lòng chọn sự kiện để cập nhật!");
            return;
        }

        // Lấy dữ liệu từ các ô input
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        Category category = cbbCate.getValue();
        Location location = cbbLocation.getValue();
        LocalDate startDate = startDatePk.getValue();
        LocalDate endDate = endDatePk.getValue();
        LocalTime startTime = startTimeSpn.getValue();
        LocalTime endTime = endTimeSpn.getValue();
        String tickets = txtTickeks.getText().trim();
        String price = txtPrice.getText().trim();
        Image image = eventImgView.getImage();
        String description = txtDesc.getText().trim();

        if (name.isEmpty() || description.isEmpty() || category == null || location == null
                || startDate == null || endDate == null || startTime == null || endTime == null
                || tickets.isEmpty() || price.isEmpty() || image == null) {
            Utils.showAlert("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        LocalDateTime startDatetime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        // Kiểm tra ngày tổ chức < ngày hiện tại
        if (!eventServices.isStartDateAfterNow(startDatePk.getValue())) {
            Utils.showAlert("Ngày tổ chức phải lớn hơn ngày hiện tại!");
            return;
        }

        int eventId = Integer.parseInt(id);
        // Kiểm tra trùng địa điểm và giờ
        if (eventServices.isEventLocationTimeConflict(eventId, location.getId(), startDatetime, endDateTime)) {
            Utils.showAlert("Lỗi: Trùng địa điểm và giờ với sự kiện khác!");
            return;
        }

        int availableTickets = Integer.parseInt(tickets);
        // Kiểm tra số lượng khách không vượt quá sức chứa địa điểm
        if (availableTickets > location.getCapacity()) {
            Utils.showAlert("Số lượng khách không được vượt quá sức chứa địa điểm!");
            return;
        }

        Duration duration = Duration.between(LocalDateTime.now(), startDatetime);
        // Thông báo thay đổi thời gian phải gửi trước 24h
        if (duration.toHours() <= 24) {
            Utils.showAlert("Thời gian bắt đầu phải cách hiện tại hơn 24h!");
            return;
        }
        boolean isTimeChanged = !startDatetime.equals(selectedEvent.getStartTime());

        String imageUrl;
        // nếu chọn ảnh mới thì lưu vào db với url này
        if (selectedFile != null) {
            imageUrl = "/images/" + selectedFile.getName();
        } else {
            imageUrl = selectedEvent.getImageUrl();
        }

        Event event = new Event();
        event.setId(eventId);
        event.setName(name);
        event.setCategoryId(category.getId());
        event.setLocationId(location.getId());
        event.setStartTime(startDatetime);
        event.setEndTime(endDateTime);
        event.setAvailableTickets(availableTickets);
        event.setPrice(new BigDecimal(price));
        event.setImageUrl(imageUrl);
        event.setDescription(description);

        if (eventServices.updateEvent(event)) {
            if (selectedFile != null) {
                copyImgIntoResources();
            }
            Utils.showAlert("Cập nhật sự kiện thành công!");

            // Gửi thông báo thay đổi thời gian
            if (isTimeChanged) {
                sendChangeNoti(event);
            }

            clearForm();
            loadTableData();
        } else {
            Utils.showAlert("Lỗi: Cập nhật sự kiện thất bại!");
        }
    }

    public void sendChangeNoti(Event e) {
        List<Registration> regList = regServices.getRegsByEventId(e.getId(), "PAID");
        for (Registration r : regList) {
            if (!notiServices.createTimeChangedNoti(e, r.getUserId())) {
                Utils.showAlert("Xảy ra lỗi khi gửi thông báo thay đổi thời gian!");
                return;
            }
        }
        Utils.showAlert("Đã gửi thông báo thay đổi thời gian cho tất cả người tham gia!");
    }

    public void sendCancelNoti(Event e) {
        List<Registration> regList = regServices.getRegsByEventId(e.getId(), "PAID");
        for (Registration r : regList) {
            if (!notiServices.createCancelNoti(e, r.getUserId())) {
                Utils.showAlert("Xảy ra lỗi khi gửi thông báo hủy sự kiện!");
                return;
            }
        }
        Utils.showAlert("Đã gửi thông báo hủy sự kiện cho tất cả người tham gia!");
    }

    @FXML
    public void clearForm() {
        txtId.clear();
        txtName.clear();
        cbbCate.setValue(null);
        cbbLocation.setValue(null);
        startDatePk.setValue(null);
        endDatePk.setValue(null);
        startTimeSpn.getValueFactory().setValue(LocalTime.of(0, 0));
        endTimeSpn.getValueFactory().setValue(LocalTime.of(0, 0));
        txtTickeks.clear();
        txtPrice.clear();
        txtDesc.clear();
        eventImgView.setImage(null);

        setVisibleImg(false, true);
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
            setVisibleImg(true, false);
        }
    }

    @FXML
    public void copyImgIntoResources() {
        String targetPath = "src/main/resources/images/" + selectedFile.getName();

        // copy ảnh vừa chọn từ máy vào resources
        try {
            Files.copy(selectedFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Utils.showAlert("Lỗi: Lưu ảnh thất bại!");
            Logger.getLogger(AdminEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVisibleImg(boolean imgView, boolean btnUpload) {
        eventImgView.setVisible(imgView);
        btnUploadImg.setVisible(btnUpload);
    }

}
