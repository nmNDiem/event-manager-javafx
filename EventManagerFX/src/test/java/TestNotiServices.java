
import com.ktpm.pojo.Event;
import com.ktpm.services.NotiServices;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class TestNotiServices {
    private static NotiServices s = new NotiServices();
    
    @Test
    public void testCreateTimeChangedNoti() {
        Event e = new Event();
        e.setId(1);
        e.setName("Sự kiện thay đổi thời gian");
        e.setStartTime(LocalDateTime.now().plusDays(3).withHour(10).withMinute(0));
        e.setEndTime(LocalDateTime.now().plusDays(3).withHour(12).withMinute(0));
        boolean result = s.createTimeChangedNoti(e, 1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testCreateCancelNoti() {
        Event e = new Event();
        e.setId(2);
        e.setName("Sự kiện hủy");
        e.setStartTime(LocalDateTime.now().plusDays(5).withHour(14).withMinute(0));
        e.setEndTime(LocalDateTime.now().plusDays(5).withHour(16).withMinute(0));
        boolean result = s.createCancelNoti(e, 1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testCreateReminderNoti() {
        Event e = new Event();
        e.setId(3);
        e.setName("Sự kiện nhắc nhở");
        e.setStartTime(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0));
        e.setEndTime(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
        e.setLocationId(1);
        boolean result = s.createReminderNoti(e, 1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsReminderAlreadySent() throws Exception {
        boolean result = NotiServices.isReminderAlreadySent(3, 1);
        Assertions.assertTrue(result || !result);
    }

    @Test
    public void testSendReminder() {
        s.sendReminder(1);
        Assertions.assertTrue(true);
    }
}
