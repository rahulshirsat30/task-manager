package org.product.taskmanager.controller; 
import com.example.demo.model.User; 
import com.example.demo.service.UserService; 
import com.example.demo.service.VulnerabilityReporter; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.*; 
import java.util.List; 
import java.util.Optional; 
@RestController 
@RequestMapping("/api/users") 
public class UserController { 
    @Autowired 
    private UserService userService; 
    @Autowired 
    private VulnerabilityReporter vulnerabilityReporter; 
    @GetMapping 
    public List<User> getAllUsers() { 
        return userService.getAllUsers(); 
    } 
    @GetMapping("/{id}") 
    public Optional<User> getUserById(@PathVariable Long id) { 
        return userService.getUserById(id); 
    } 
    @PostMapping 
    public User createUser(@RequestBody User user) { 
        return userService.createUser(user); 
    } 
    @PutMapping("/{id}") 
    public Optional<User> updateUser(@PathVariable Long id, @RequestBody User user) { 
        return userService.updateUser(id, user); 
    } 
    @DeleteMapping("/{id}") 
    public boolean deleteUser(@PathVariable Long id) { 
        return userService.deleteUser(id); 
    } 
    @GetMapping("/check-vulnerabilities") 
    public ResponseEntity<String> checkVulnerabilities() { 
        try { 
            List<String> vulnerabilities = vulnerabilityReporter.checkForVulnerabilities(); 
            return ResponseEntity.ok(String.join(", ", vulnerabilities)); 
        } catch (Exception e) { 
            logger.error("Error checking vulnerabilities: ", e); 
            return ResponseEntity.status(500).body("Error checking vulnerabilities"); 
        } 
    } 
    @GetMapping("/vulnerability-check-ui") 
    public String showVulnerabilityCheckUI(Model model) { 
        try { 
            List<String> vulnerabilities = vulnerabilityReporter.checkForVulnerabilities(); 
            model.addAttribute("vulnerabilities", vulnerabilities); 
            return "vulnerabilityCheck"; // This corresponds to the HTML file created above 
        } catch (Exception e) { 
            logger.error("Error displaying vulnerability check UI: ", e); 
            return "error"; // Return an error page or handle accordingly 
        } 
    } 
}