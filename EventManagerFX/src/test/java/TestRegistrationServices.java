
import com.ktpm.pojo.Event;
import com.ktpm.services.RegistrationServices;
import java.time.LocalDateTime;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author admin
 */
public class TestRegistrationServices {

    private static RegistrationServices s = new RegistrationServices();
    private static Event event = new Event();

    // ===== Test user đăng ký trùng sự kiện
    @Test
    public void testIsUserRegistered_true() {
        boolean result = s.isUserRegistered(2, 2);
        
        Assertions.assertTrue(result);
    }
    
    @Test
    public void testIsUserRegistered_false() {
        boolean result = s.isUserRegistered(7, 2);
        
        Assertions.assertFalse(result);
    }
    
    // ===== Test user đăng ký trùng giờ
    @Test
    public void testHasTimeConflict_true() {
        LocalDateTime startTime = LocalDateTime.of(2025, 5, 2, 18, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 2, 20, 30);
        boolean result = s.hasTimeConflict(2, startTime, endTime);
        
        Assertions.assertTrue(result);
    }
    
    @Test
    public void testHasTimeConflict_false() {
        LocalDateTime startTime = LocalDateTime.of(2025, 5, 2, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 2, 12, 30);
        boolean result = s.hasTimeConflict(2, startTime, endTime);
        
        Assertions.assertFalse(result);
    }
    
    // ===== Test hết vé thì không cho phép đăng ký thêm.
    @Test
    public void testHasAvailableTickets_has() {
        event.setAvailableTickets(5);
        
        boolean result = s.hasAvailableTickets(event);
        
        Assertions.assertTrue(result);
    }
    
    @Test
    public void testHasAvailableTickets_zero() {
        event.setAvailableTickets(0);
        
        boolean result = s.hasAvailableTickets(event);
        
        Assertions.assertFalse(result);
    }
    
    @Test
    public void testHasAvailableTickets_negative() {
        event.setAvailableTickets(-5);
        
        boolean result = s.hasAvailableTickets(event);
        
        Assertions.assertFalse(result);
    }
}
