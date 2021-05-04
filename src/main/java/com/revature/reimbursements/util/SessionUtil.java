package com.revature.reimbursements.util;

import com.revature.reimbursements.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static boolean setupLoginSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("userLoggedIn", user);
        return getUserFromSession(req) != null;
    }

    public static User getUserFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("userLoggedIn");

            if (user != null) {
                return user;
            } else {
                session.invalidate();
            }

        }
        return null;
    }

    public static boolean logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session != null) session.invalidate();
        return true;
    }
}
