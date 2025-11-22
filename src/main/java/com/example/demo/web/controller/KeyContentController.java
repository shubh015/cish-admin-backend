package com.example.demo.web.controller;

import com.example.demo.service.KeyContentService;
import com.example.demo.web.models.content.KeyContent;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/content")
@CrossOrigin("*")
public class KeyContentController {

    private final KeyContentService service;

    public KeyContentController(KeyContentService service) {
        this.service = service;
    }

    /**
     * ✅ POST /api/content/save
     * Each request contains only one key (like keyResearch, jobs, tenders, etc.)
     */
   @PostMapping("/save")
@CrossOrigin("*")
public String saveContent(@RequestBody Map<String, List<Map<String, Object>>> payload) {
    if (payload.isEmpty()) {
        return "❌ No content provided!";
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    payload.forEach((key, value) -> {
        List<KeyContent> contents = value.stream().map(item -> {

            // Handle images — can be a List or a single String
            String imageUrl = null;
            Object imagesObj = item.get("images");
            if (imagesObj instanceof List<?>) {
                List<?> imagesList = (List<?>) imagesObj;
                if (!imagesList.isEmpty()) {
                    imageUrl = String.valueOf(imagesList.get(0)); // take first image
                }
            } else if (imagesObj instanceof String) {
                imageUrl = (String) imagesObj;
            }

              // 📄 Handle resultDocuments (convert list to JSON or comma-separated)
            String resultDocumentsStr = null;
            Object resultDocsObj = item.get("resultDocuments");
            if (resultDocsObj instanceof List<?>) {
                List<?> docList = (List<?>) resultDocsObj;
                if (!docList.isEmpty()) {
                    resultDocumentsStr = String.join(",", docList.stream()
                            .map(Object::toString)
                            .toList());
                }
            } else if (resultDocsObj instanceof String) {
                resultDocumentsStr = (String) resultDocsObj;
            }



           
            Boolean flag = true;
            if(item.get("backtocreator") instanceof Boolean){
               flag = Boolean.parseBoolean( item.get("backtocreator").toString());
            }

            if(flag == false){

               return KeyContent.builder()
                    .id(item.get("id") != null ?  Long.valueOf(item.get("id").toString()) : null)
                    .title((String) item.get("title"))
                    .description((String) item.get("description"))
                    .imageUrl(imageUrl)
                    .resultDocuments(resultDocumentsStr)
                    .link((String) item.get("link"))
                    .date(item.get("date") != null ? LocalDate.parse((String) item.get("date"), formatter) : null)
                    .postDate(item.get("postDate") != null ? LocalDate.parse((String) item.get("postDate"), formatter) : null)
                    .lastDate(item.get("lastDate") != null ? LocalDate.parse((String) item.get("lastDate"), formatter) : null)
                    .backtocreator(flag)
                    .build(); 
            } else{

                return KeyContent.builder()
                    .id(item.get("id") != null ? Long.valueOf(item.get("id").toString()) : null)
                    .title((String) item.get("title"))
                    .description((String) item.get("description"))
                    .imageUrl(imageUrl)
                    .resultDocuments(resultDocumentsStr)
                    .link((String) item.get("link"))
                    .date(item.get("date") != null ? LocalDate.parse((String) item.get("date"), formatter) : null)
                    .postDate(item.get("postDate") != null ? LocalDate.parse((String) item.get("postDate"), formatter) : null)
                    .lastDate(item.get("lastDate") != null ? LocalDate.parse((String) item.get("lastDate"), formatter) : null)
                    .build();
            }  
        }).toList();

        service.saveContents(key, contents);
    });

    return "✅ Content saved successfully!";
}


    /**
     * ✅ GET /api/content/{key}
     */
    @GetMapping("/{key}")
     @CrossOrigin("*")
    public List<KeyContent> getContentByKey(@PathVariable String key,@RequestParam(required = false) String role) {
        return service.getContents(key,role);
    }


        @PostMapping("/status")
    @CrossOrigin("*")
    public ResponseEntity<String> updateKeyContentStatus(@RequestBody Map<String, Object> payload) {
        try {
            String key = (String) payload.get("key");
            if (key == null || !payload.containsKey("ids")) {
                return ResponseEntity.badRequest().body("❌ Missing 'key' or 'ids' in request.");
            }

            // ✅ Safely convert ids to List<Long>
            List<Long> ids = ((List<?>) payload.get("ids"))
                    .stream()
                    .map(id -> Long.valueOf(String.valueOf(id)))
                    .toList();

            if (ids.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ 'ids' list cannot be empty.");
            }

            switch (key.toLowerCase()) {
                case "publish" -> service.updateStatus(ids, true, true, false);
                case "delete" -> service.updateStatus(ids, null, false, null);
                case "backtocreator" -> service.updateStatus(ids, false, null, true);
                default -> {
                    return ResponseEntity.badRequest()
                            .body("❌ Invalid key. Use 'publish', 'delete', or 'backToCreator'.");
                }
            }

            return ResponseEntity.ok("✅ Status updated for " + ids.size() + " record(s) with action: " + key);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error: " + e.getMessage());
        }
    }
}

