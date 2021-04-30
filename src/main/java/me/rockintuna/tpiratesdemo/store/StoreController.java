package me.rockintuna.tpiratesdemo.store;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    @ResponseBody
    public StoreDto.DetailResponse detail(@NonNull @RequestParam("id") Long id) {
        return storeService.getDetailById(id);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> create(@NonNull @RequestBody StoreDto.Info storeDto) {
        Store store = new Store(storeDto);
        storeService.addStore(store);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/holiday")
    @ResponseBody
    public ResponseEntity<?> holiday(@NonNull @RequestBody StoreDto.HolidayRequest holidayDto) {
        storeService.addHolidays(holidayDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @ResponseBody
    public List<StoreDto.ListResponse> list() {
        return storeService.getStores();
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> remove(@NonNull @RequestParam("id") Long id) {
        storeService.removeStore(id);
        return ResponseEntity.ok().build();
    }
}
