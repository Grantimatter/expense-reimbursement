package com.revature.reimbursements.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursements.model.Reimbursement;
import com.revature.reimbursements.model.User;
import com.revature.reimbursements.model.dto.LoginDTO;
import com.revature.reimbursements.model.dto.UserDTO;
import com.revature.reimbursements.model.enumerator.Role;
import com.revature.reimbursements.service.ReimbursementService;
import com.revature.reimbursements.service.UserService;
import com.revature.reimbursements.util.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://www.localhost:4200")
public class UserController {

    private final UserService userService;
    private final ReimbursementService reimbursementService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LogManager.getLogger(UserController.class);

    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, ReimbursementService reimbursementService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Used to create new users
     *
     * @param userDTO The user DTO used to create a local User
     * @param request The HttpServletRequest used to automatically login a new user
     * @return Returns the newly created user object within a ResponseEntity
     */
    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        log.info("Posting a new user");
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = objectMapper.convertValue(userDTO, User.class);
        if (user != null) {
            if (user.getId() != 0) user.setId(0);
            User newUser = userService.createUser(user);
            newUser.setPassword("");
            SessionUtil.setupLoginSession(request, newUser);
            return ResponseEntity.ok(newUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Retrieves the given user from username or id
     *
     * @param filterParams Automatically populated map of query parameters
     * @param request      The HttpServletRequest needed to validate sessions
     * @return Returns the specified user object within a ResponseEntity if the same user is logged in, or if the logged in user is a manager
     */
    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam(required = false) Map<String, String> filterParams, HttpServletRequest request) {
        User sessionUser = SessionUtil.getUserFromSession(request);
        if (sessionUser == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (filterParams == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        User user = null;

        if (filterParams.containsKey("username")) {
            String username = filterParams.get("username");
            if (sessionUser.getRole().equals(Role.EMPLOYEE) && !username.equalsIgnoreCase(sessionUser.getUsername()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            user = userService.getUserByUsername(filterParams.get("username"));
        } else if (filterParams.containsKey("id")) {
            Integer id = Integer.parseInt(filterParams.getOrDefault("id", "0"));

            log.info(String.format("%d %s", id, id.getClass()));
            //user = userService.getUserById(id);
        }

        if (user != null) {
            user.setPassword("");
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Takes user credentials and creates a session if the credentials match an account stored on the database.
     *
     * @param loginDTO The login DTO that contains the login credentials
     * @param request  The HttpServletRequest needed to create a new session if the credentials match
     * @return Returns the user object in a ResponseEntity if the credentials match an account on the database.
     */
    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        loginDTO.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        User user = userService.loginUser(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());
        if (user != null) {
            if (SessionUtil.setupLoginSession(request, user)) {
                user.setPassword("");
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Gets the user that is currently logged in
     *
     * @param request The HttpServletRequest needed to create a new session if the credentials match
     * @return Returns the user object if there is a user logged in.
     */
    @GetMapping("/check")
    public ResponseEntity<User> checkUserLogin(HttpServletRequest request) {
        User sessionUser = SessionUtil.getUserFromSession(request);
        if (sessionUser != null) {
            sessionUser.setPassword("");
            return ResponseEntity.ok().body(sessionUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Logs the user out and ends the session
     *
     * @param request The HttpServletRequest needed to validate a current session
     * @return Returns a boolean within a ResponseEntity indicating if the user was logged out successfully
     */
    @GetMapping("/logout")
    public ResponseEntity<Boolean> userLogout(HttpServletRequest request) {
        return SessionUtil.logout(request) ? ResponseEntity.status(HttpStatus.OK).body(true) : ResponseEntity.status(HttpStatus.NO_CONTENT).body(false);
    }

    /**
     * Retrieves all reimbursements associated with a specific account
     *
     * @param userDTO The user DTO used to create a local instance of User
     * @param request The HttpServletRequest needed to validate sessions
     * @return Returns a list of all reimbursements created by the given user within a ResponseEntity
     */
    @GetMapping("/reimbursements")
    public ResponseEntity<List<Reimbursement>> getReimbursementsFromAuthor(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        User user = objectMapper.convertValue(userDTO, User.class);
        User sessionUser = SessionUtil.getUserFromSession(request);
        if (user == null || sessionUser == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (user.getId() == sessionUser.getId() || sessionUser.getRole().equals(Role.MANAGER)) {
            List<Reimbursement> reimbursementList = reimbursementService.getReimbursementsFromAuthor(user);
            if (reimbursementList != null && !reimbursementList.isEmpty()) {
                user.setPassword(null);
                return ResponseEntity.ok(reimbursementList);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
