package me.rockintuna.tpiratesdemo.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Optional<Store> getStore(Long id) {
        return storeRepository.findById(id);
    }

    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    public void addHolidays(StoreDto.HolidayRequest holidayDto) {
        Store store = getStore(holidayDto.getId()).get();
        List<HoliDay> holiDays = new ArrayList<>();
        for (String holiday : holidayDto.getHolidays()) {
            holiDays.add(new HoliDay(LocalDate.parse(holiday), store));
        }
        store.addHolidays(holiDays);
    }

    public List<StoreDto.ListResponse> getStores() {
        return storeListToListResponseList(storeRepository.findAllOrderByLevel());
    }

    private List<StoreDto.ListResponse> storeListToListResponseList(List<Store> stores) {
        List<StoreDto.ListResponse> responses = new ArrayList<>();
        for (Store store : stores) {
            responses.add(new StoreDto.ListResponse(store));
        }
        return responses;
    }

    public StoreDto.DetailResponse getDetailById(Long id) {
        Store store = getStore(id).get();
        return new StoreDto.DetailResponse(store);
    }

    public void removeStore(Long id) {
        storeRepository.delete(getStore(id).get());
    }
}
