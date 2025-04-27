/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ktpm.pojo.Location;
import com.ktpm.services.EventServices;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author admin
 */
public class TestEventServices {
    private static EventServices s = new EventServices();
    
    // ======= Test ngày tổ chức phải lớn hơn ngày hiện tại.
    @Test
    public void testIsStartDateAfterNow_after() {
        LocalDate startDate = LocalDate.of(2025, 5, 10);
        boolean result = s.isStartDateAfterNow(startDate);
        
        Assertions.assertTrue(result);
    }
    
    @Test
    public void testIsStartDateAfterNow_before() {
        LocalDate startDate = LocalDate.of(2025, 4, 10);
        boolean result = s.isStartDateAfterNow(startDate);
        
        Assertions.assertFalse(result);
    }
    
    @Test
    public void testIsStartDateAfterNow_same() {
        LocalDate startDate = LocalDate.of(2025, 4, 28);
        boolean result = s.isStartDateAfterNow(startDate);
        
        Assertions.assertFalse(result);
    }
    
    //=========== Test tạo sự kiện không trùng địa điểm và giờ với sự kiện khác.
    @Test
    public void testIsEventLocationTimeConflict_noConflict() {
        LocalDateTime startTime = LocalDateTime.of(2025, 5, 20, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 21, 10, 0);
        boolean result = s.isEventLocationTimeConflict(0, 2, startTime, endTime);
        
        Assertions.assertFalse(result);
    }
    // Sự kiện cũ nằm trong sự kiện mới
    @Test
    public void testIsEventLocationTimeConflict_cover() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 27, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 30, 22, 0);
        boolean result = s.isEventLocationTimeConflict(0, 2, startTime, endTime);
        
        Assertions.assertTrue(result);
    }
    // Sự kiện mới nằm trong sự kiện cũ
    @Test
    public void testIsEventLocationTimeConflict_within() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 28, 17, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 28, 22, 0);
        boolean result = s.isEventLocationTimeConflict(0, 2, startTime, endTime);
        
        Assertions.assertTrue(result);
    }
    // Sự kiện mới giống sự kiện cũ
    @Test
    public void testIsEventLocationTimeConflict_same() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 28, 7, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 29, 17, 30);
        boolean result = s.isEventLocationTimeConflict(0, 2, startTime, endTime);
        
        Assertions.assertTrue(result);
    }
    // Thời gian bắt đầu của sự kiện mới nằm trong sự kiện cũ
    @Test
    public void testIsEventLocationTimeConflict_startWithin() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 28, 17, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 30, 22, 0);
        boolean result = s.isEventLocationTimeConflict(0, 2, startTime, endTime);
        
        Assertions.assertTrue(result);
    }
    // Thời gian kết thúc của sự kiện mới nằm trong sự kiện cũ
    @Test
    public void testIsEventLocationTimeConflict_endWithin() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 27, 17, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 28, 22, 0);
        boolean result = s.isEventLocationTimeConflict(0, 2, startTime, endTime);
        
        Assertions.assertTrue(result);
    }
    
    // ========= Test số lượng khách không vượt quá sức chứa địa điểm.
    @Test
    public void testTicketLimit_less() {
        Location location = new Location();
        location.setCapacity(100);
        
        boolean result = s.checkTicketLimit(90, location);
        
        Assertions.assertTrue(result);
    }
    
    @Test
    public void testTicketLimit_more() {
        Location location = new Location();
        location.setCapacity(100);
        
        boolean result = s.checkTicketLimit(150, location);
        
        Assertions.assertFalse(result);
    }
    
    @Test
    public void testTicketLimit_same() {
        Location location = new Location();
        location.setCapacity(100);
        
        boolean result = s.checkTicketLimit(100, location);
        
        Assertions.assertFalse(result);
    }
}
