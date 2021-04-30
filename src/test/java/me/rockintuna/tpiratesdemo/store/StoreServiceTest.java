package me.rockintuna.tpiratesdemo.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void getStore() {
        Store mockStore = new Store();
        mockStore.setName("참치월드");
        mockStore.setOwner("홍길동");
        Store saved = storeRepository.save(mockStore);

        Store store = storeService.getStore(saved.getId()).get();

        assertThat(store.getName()).isEqualTo("참치월드");
    }

    @Test
    public void addStore() {
        Store store = new Store();
        store.setName("참치월드");
        store.setOwner("홍길동");
        store.setDescription("인천소래포구 종합어시장 갑각류센터 참치월드");
        store.setLevel(2);
        store.setAddress("인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1 층 2 호");
        store.setPhone("010-1111-2222");
        List<BusinessTime> businessTimes = new ArrayList<>();
        businessTimes.add(new BusinessTime("Monday", "13:00", "23:00"));
        businessTimes.add(new BusinessTime("Tuesday", "13:00", "23:00"));
        businessTimes.add(new BusinessTime("Wednesday", "09:00", "18:00"));
        businessTimes.add(new BusinessTime("Thursday", "09:00", "23:00"));
        businessTimes.add(new BusinessTime("Friday", "09:00", "23:00"));
        store.setBusinessTimes(businessTimes);

        Long id = storeService.addStore(store).getId();
        assertThat(storeRepository.findById(id).get().getName()).isEqualTo("참치월드");
    }

    @Test
    public void holiday() {
        Store mockStore = new Store();
        mockStore.setName("참치월드");
        mockStore.setOwner("홍길동");
        Store saved = storeRepository.save(mockStore);
        Long id = saved.getId();

        List<String> holidays = new ArrayList<>();
        holidays.add("2020-10-24");
        holidays.add("2020-10-25");

        StoreDto.HolidayRequest holidayDto = new StoreDto.HolidayRequest(id, holidays);

        storeService.addHolidays(holidayDto);

        assertThat(storeRepository.findById(id).get().getHolidays().get(0).getDate())
                .isEqualTo(LocalDate.of(2020,10,24));
    }

    @Test
    public void getDetailById() {
        Store store = new Store();
        store.setName("참치월드");
        store.setOwner("홍길동");
        store.setDescription("인천소래포구 종합어시장 갑각류센터 참치월드");
        store.setLevel(2);
        store.setAddress("인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1 층 2 호");
        store.setPhone("010-1111-2222");
        List<BusinessTime> businessTimes = new ArrayList<>();
        businessTimes.add(new BusinessTime("Monday", "13:00", "23:00"));
        businessTimes.add(new BusinessTime("Tuesday", "13:00", "23:00"));
        businessTimes.add(new BusinessTime("Wednesday", "09:00", "18:00"));
        businessTimes.add(new BusinessTime("Thursday", "09:00", "23:00"));
        businessTimes.add(new BusinessTime("Friday", "09:00", "23:00"));
        store.setBusinessTimes(businessTimes);

        Long id = storeService.addStore(store).getId();

        StoreDto.DetailResponse detailById = storeService.getDetailById(id);
        assertThat(detailById.getName()).isEqualTo("참치월드");
    }

    @Test
    public void removeStore() {
        Store store = new Store();
        store.setName("참치월드");
        store.setOwner("홍길동");
        store.setDescription("인천소래포구 종합어시장 갑각류센터 참치월드");
        store.setLevel(2);
        store.setAddress("인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1 층 2 호");
        store.setPhone("010-1111-2222");
        List<BusinessTime> businessTimes = new ArrayList<>();
        businessTimes.add(new BusinessTime("Monday", "13:00", "23:00"));
        businessTimes.add(new BusinessTime("Tuesday", "13:00", "23:00"));
        businessTimes.add(new BusinessTime("Wednesday", "09:00", "18:00"));
        businessTimes.add(new BusinessTime("Thursday", "09:00", "23:00"));
        businessTimes.add(new BusinessTime("Friday", "09:00", "23:00"));
        store.setBusinessTimes(businessTimes);

        Long id = storeService.addStore(store).getId();

        storeService.removeStore(id);
        assertThat(storeService.getStore(id)).isEmpty();
    }
}