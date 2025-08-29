package com.vit.bfhl;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class BfhlController {
    
    @PostMapping("/bfhl")
    public ResponseEntity<Map<String, Object>> processBfhl(@RequestBody Map<String, List<String>> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<String> data = request.get("data");
            if (data == null) {
                response.put("is_success", false);
                response.put("error", "Invalid input data");
                return ResponseEntity.badRequest().body(response);
            }
            
           
            response.put("is_success", true);
            response.put("user_id", "mangal_cm_23062004"); 
            response.put("email", "cmmangalofficial@gmail.com");    
            response.put("roll_number", "22BIT0229");    
            
            // Initialize arrays
            List<String> oddNumbers = new ArrayList<>();
            List<String> evenNumbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specialCharacters = new ArrayList<>();
            List<Character> allAlphabetChars = new ArrayList<>();
            
            int sum = 0;
            
           
            for (String item : data) {
                if (item == null || item.isEmpty()) continue;
              
                if (isNumeric(item)) {
                    int num = Integer.parseInt(item);
                    sum += num;
                    if (num % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                }
             
                else if (isAlphabetic(item)) {
                    alphabets.add(item.toUpperCase());
                
                    for (char c : item.toCharArray()) {
                        allAlphabetChars.add(c);
                    }
                }
             
                else {
                    specialCharacters.add(item);
               
                    for (char c : item.toCharArray()) {
                        if (Character.isLetter(c)) {
                            allAlphabetChars.add(c);
                        }
                    }
                }
            }
            
       
            response.put("odd_numbers", oddNumbers);
            response.put("even_numbers", evenNumbers);
            response.put("alphabets", alphabets);
            response.put("special_characters", specialCharacters);
            response.put("sum", String.valueOf(sum));
            response.put("concat_string", generateConcatString(allAlphabetChars));
            
            return ResponseEntity.ok(response);
            
        } catch (NumberFormatException e) {
            response.put("is_success", false);
            response.put("error", "Invalid number format");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("is_success", false);
            response.put("error", "Internal server error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
  
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
   
    private boolean isAlphabetic(String str) {
        if (str == null || str.isEmpty()) return false;
        return str.matches("[a-zA-Z]+");
    }
    
   
    private String generateConcatString(List<Character> alphabets) {
        if (alphabets.isEmpty()) return "";
        

        Collections.reverse(alphabets);
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < alphabets.size(); i++) {
            char c = alphabets.get(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        
        return result.toString();
    }
    

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "BFHL API is running");
        return ResponseEntity.ok(response);
    }
}
