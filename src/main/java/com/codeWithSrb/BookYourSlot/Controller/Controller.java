package com.codeWithSrb.BookYourSlot.Controller;

import com.codeWithSrb.BookYourSlot.Model.AuthUser;
import com.codeWithSrb.BookYourSlot.Model.BookingDetails;
import com.codeWithSrb.BookYourSlot.Model.UserInfo;
import com.codeWithSrb.BookYourSlot.Service.BookingService;
import com.codeWithSrb.BookYourSlot.Service.JwtService;
import com.codeWithSrb.BookYourSlot.Service.UserDetailsServiceImpl;
import com.codeWithSrb.BookYourSlot.common.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class Controller {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String registerNewUser(@RequestBody UserInfo userInfo) {
        userDetailsServiceImpl.registerNewUser(userInfo);
        return "New user registration is successful";
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<BookingDetails> retrieveAllBooking() {
        return bookingService.retrieveAllBooking();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public BookingDetails retrieveBooking(@PathVariable (value = "id") int id) {
        BookingDetails bookingDetails = null;
        try {
            bookingDetails = bookingService.retrieveBooking(id);
            System.out.println(bookingDetails.getUserFirstName());
        } catch (InvalidRequestException e) {
            System.out.println("Exception occurred while retrieving the booking");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.printf("An exception occurred while retrieving the booking for user id: %s", id);
        }
        return bookingDetails;
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public String createBooking(@RequestBody BookingDetails bookingDetails) {
        return bookingService.createBooking(bookingDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public String deleteBooking(@PathVariable (value = "id") int id) {
        return bookingService.deleteBooking(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public String updateExistingBooking(@PathVariable (value = "id") int id, @RequestBody BookingDetails bookingDetails) {
        return bookingService.updateExistingBooking(bookingDetails, id);
    }

    @PostMapping("/authenticate")
    public String returnRegisterUserToken(@RequestBody AuthUser authUser) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser.getUserName(), authUser.getPassword()));

        if(authenticate.isAuthenticated()) {
            return jwtService.generateToken(authUser.getUserName());
        } else {
            throw new UsernameNotFoundException("Invalid username and password ");
        }
    }
}