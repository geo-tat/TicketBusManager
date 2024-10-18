package com.jfb.lecture5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfb.lecture5.model.BusTicket;
import com.jfb.lecture5.service.TicketValidator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
  public static void main(String[] args) throws JsonProcessingException {

    List<BusTicket> tickets = new ArrayList<>();


    List<String> input = getInput();
    for (String s : input) {
      BusTicket busTicket = new ObjectMapper().readValue(s, BusTicket.class);
      tickets.add(busTicket);
      TicketValidator.validate(busTicket);
    }
    System.out.println("Total = " + tickets.size());
    System.out.println("Valid = " + TicketValidator.validTickets);
    System.out.println("Most popular violation = " + TicketValidator.getMostPopularViolation());
  }

  private static List<String> getInput() {
    String PATH = "src/main/resources/ticketData.txt";
    ArrayList<String> list = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)))) {
      String newTicket;
      while ((newTicket = br.readLine()) != null) {
        newTicket = newTicket.replace("“", "\"");
        list.add(newTicket);
      }
      return list;
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found at: " + PATH, e);
    } catch (IOException e) {
      throw new RuntimeException("Reading error: " + PATH, e);
    }
  }
}
