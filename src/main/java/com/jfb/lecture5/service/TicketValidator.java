package com.jfb.lecture5.service;

import com.jfb.lecture5.model.BusTicket;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TicketValidator {
    public static int startDateViolation = 0;
    public static int priceViolation = 0;
    public static int ticketTypeViolation = 0;
    public static int validTickets = 0;

    public static void validate(BusTicket ticket) {
        boolean isValid = true;
        if (!validByDate(ticket)) {
            startDateViolation++;
            isValid = false;
        }
        if (!validByPrice(ticket)) {
            priceViolation++;
            isValid = false;
        }
        if (!validByType(ticket)) {
            ticketTypeViolation++;
            isValid = false;
        }
        if (isValid) {
            validTickets++;
        }
    }


    private static boolean validByDate(BusTicket ticket) {
        if (ticket.getTicketType() != null && ticket.getTicketType().equals("MONTH") && ticket.getStartDate().isEmpty()) {
            return true;
        }
        if (ticket.getStartDate() == null || ticket.getStartDate().isEmpty()) {
            return false;
        }
        return !LocalDate.parse(ticket.getStartDate()).isAfter(LocalDate.now());
    }

    private static boolean validByType(BusTicket ticket) {
        if (ticket.getTicketType() == null) {
            return false;
        }
        return switch (ticket.getTicketType()) {
            case "MONTH", "DAY", "YEAR", "WEEK" -> true;
            default -> false;
        };
    }

    private static boolean validByPrice(BusTicket ticket) {
        if (ticket.getPrice() == null) {
            return false;
        }
        BigDecimal price = new BigDecimal(ticket.getPrice());
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return price.remainder(BigDecimal.valueOf(2)).compareTo(BigDecimal.ZERO) == 0;
    }

    public static String getMostPopularViolation() {
        if (startDateViolation >= priceViolation && startDateViolation >= ticketTypeViolation) {
            return "start date";
        } else if (priceViolation >= startDateViolation && priceViolation >= ticketTypeViolation) {
            return "price";
        } else {
            return "ticket type";
        }
    }
}
