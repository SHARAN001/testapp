package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/working")
    public String working() {
        logger.info("Executing /working endpoint - saving and fetching items from testapp.items table");
        
        // Operation on testapp schema table
        Item newItem = new Item("WorkingTask_" + System.currentTimeMillis(), "SUCCESS");
        itemRepository.save(newItem);

        List<Item> items = itemRepository.findAll();
        logger.info("Retrieved {} items from testapp.items table", items.size());

        return "Service working normally! Table testapp.items contains " + items.size() + " items. Last added ID: " + newItem.getId();
    }

    @GetMapping("/breaking")
    public String breaking() {
        logger.error("Executing /breaking endpoint - attempting invalid table operation on testapp schema");

        try {
            // Intentionally broken query on non-existent table in testapp schema
            jdbcTemplate.execute("SELECT * FROM testapp.non_existent_table");
             
            } catch (Exception e) {
            logger.error("Database table operation failed on testapp schema: {}", e.getMessage(), e);
            throw new RuntimeException("Service broke! Table operation failed in testapp schema: " + e.getMessage(), e);
        }

        return "Service broke!";
    }

    @GetMapping("/working2/{name}")
    public String working2( @org.springframework.web.bind.annotation.PathVariable String name) {
        logger.info("Executing /working endpoint - saving and fetching items from testapp.items table");
        
        // Operation on testapp schema table
        Item newItem = new Item(name, "SUCCESS");
        // newItem.setName(name);
        itemRepository.save(newItem);

        Item item = itemRepository.findByName(name);


        return "Saved item: " + item.getName() + " with ID: " + item.getId();
    }
}
