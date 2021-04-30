package me.rockintuna.tpiratesdemo.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {

        StoreDto.Info storeDto = new StoreDto.Info();
        storeDto.setName("참치월드");
        storeDto.setOwner("홍길동");
        storeDto.setDescription("인천소래포구 종합어시장 갑각류센터 참치월드");
        storeDto.setLevel(2);
        storeDto.setAddress("인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1 층 2 호");
        storeDto.setPhone("010-1111-2222");
        List<BusinessTime> businessTimes = new ArrayList<>();
        businessTimes.add(new BusinessTime("Monday", "13:00", "23:00"));
        businessTimes.add(new BusinessTime("Tuesday", "09:00", "23:00"));
        businessTimes.add(new BusinessTime("Wednesday", "09:00", "18:00"));
        businessTimes.add(new BusinessTime("Thursday", "09:00", "23:00"));
        businessTimes.add(new BusinessTime("Friday", "09:00", "23:00"));
        storeDto.setBusinessTimes(businessTimes);

        String jsonStore = objectMapper.writeValueAsString(storeDto);
        System.out.println(jsonStore);

        mvc.perform(post("/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStore))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void holiday() throws Exception {

        Store mockStore = new Store();
        mockStore.setName("참치월드");
        mockStore.setOwner("홍길동");
        Store store = storeService.addStore(mockStore);

        List<String> holiDays = new ArrayList<>();
        holiDays.add("2020-10-04");
        holiDays.add("2020-10-05");

        StoreDto.HolidayRequest holidayDto = new StoreDto.HolidayRequest(1L, holiDays);

        String jsonStore = objectMapper.writeValueAsString(holidayDto);
        System.out.println(jsonStore);

        mvc.perform(post("/store/holiday")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStore))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void list() throws Exception {
        Store mockStore1 = new Store();
        mockStore1.setName("참치월드");
        mockStore1.setOwner("홍길동");
        mockStore1.setLevel(2);
        storeService.addStore(mockStore1);

        Store mockStore2 = new Store();
        mockStore2.setName("거북선");
        mockStore2.setOwner("이순신");
        mockStore1.setLevel(1);
        storeService.addStore(mockStore2);

        mvc.perform(get("/store/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void remove() throws Exception {
        Store mockStore1 = new Store();
        mockStore1.setName("참치월드");
        mockStore1.setOwner("홍길동");
        mockStore1.setLevel(2);
        storeService.addStore(mockStore1);

        Store mockStore2 = new Store();
        mockStore2.setName("거북선");
        mockStore2.setOwner("이순신");
        mockStore1.setLevel(1);
        storeService.addStore(mockStore2);

        mvc.perform(delete("/store")
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}