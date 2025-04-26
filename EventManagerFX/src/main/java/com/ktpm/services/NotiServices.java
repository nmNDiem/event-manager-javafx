/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.eventmanagerfx.Utils;
import com.ktpm.pojo.Event;
import com.ktpm.pojo.JdbcUtils;
import com.ktpm.pojo.Notification;
import com.ktpm.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class NotiServices {
    LocationServices locaServices = new LocationServices();

    public boolean sendReminderNoti(Event e, int userId) {
        String sql = "INSERT INTO notification (event_id, user_id, subject, message, sent_at, type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql);) {
            
            String startTime = Utils.formatDateTime(e.getStartTime());
            String location = locaServices.getLocationById(e.getLocationId()).getName();
            String subject = "[" + e.getName() + "] Nhắc nhở tham gia";
            String message = "Sự kiện '" + e.getName() + "' sẽ diễn ra vào "
                    + startTime + " (ngày mai) tại " + location
                    + ".\nHẹn gặp bạn vào ngày mai.";
            
            stm.setInt(1, e.getId());
            stm.setInt(2, userId);
            stm.setString(3, subject);
            stm.setString(4, message);
            stm.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stm.setString(6, "REMINDER");

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean sendTimeChangedNoti(Event e, int userId) {
        String sql = "INSERT INTO notification (event_id, user_id, subject, message, sent_at, type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql);) {
            
            String newStartTime = Utils.formatDateTime(e.getStartTime());
            String newEndTime = Utils.formatDateTime(e.getEndTime());
            String subject = "[" + e.getName() + "] Thông báo thay đổi thời gian";
            String message = "Thời gian tổ chức sự kiện '" + e.getName() + "' đã được thay đổi.\n"
                    + "\nThời gian mới: " + newStartTime + " đến " + newEndTime
                    + "\nChúng tôi thành thật xin lỗi vì sự bất tiện này và chân thành cảm ơn sự ủng hộ của bạn.";
            
            stm.setInt(1, e.getId());
            stm.setInt(2, userId);
            stm.setString(3, subject);
            stm.setString(4, message);
            stm.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stm.setString(6, "CHANGE");

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean sendCancelNoti(Event e, int userId) {
        String sql = "INSERT INTO notification (event_id, user_id, subject, message, sent_at, type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql);) {
            
            String subject = "[" + e.getName() + "] Thông báo hủy sự kiện";
            String message = "Sự kiện '" + e.getName() + "' sẽ được hủy bỏ vì lý do phát sinh ngoài ý muốn."
                    + "\nMọi khoản phí sẽ được hoàn lại trong thời gian sớm nhất."
                    + "\nChúng tôi thành thật xin lỗi vì sự bất tiện này và chân thành cảm ơn sự ủng hộ của bạn.";
            
            stm.setInt(1, e.getId());
            stm.setInt(2, userId);
            stm.setString(3, subject);
            stm.setString(4, message);
            stm.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stm.setString(6, "CANCEL");

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
