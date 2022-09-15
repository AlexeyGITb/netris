package com.alexey.netris.controller;

import com.alexey.netris.entity.CctvRequest;
import com.alexey.netris.entity.SourceData;
import com.alexey.netris.entity.TokenData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MainController mainController;

    @Test
    void getSetOfCctvResponseReturnsStatusCreated() throws Exception{
            this.mockMvc.perform(get("/response_entity"))
                    .andExpect(status().isCreated());
        }

    @Test
    void getListOfCctvTempReturnsList() {
        List<CctvRequest> listOfCctvTemp = mainController.getListOfCctvRequests();
        Assert.notEmpty(listOfCctvTemp, "Пустой результат в listOfCctvTemp()");
    }

    @Test
    void getDataForResponseTest_VerifyCall() {
        MainController mainController1 = mock(MainController.class);
        List<CctvRequest> test = Collections.singletonList(new CctvRequest(1L,
                "http://www.mocky.io/v2/5c51b230340000094f129f5d",
                "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s"));
        mainController1.getDataForResponse(test);
        verify(mainController1,times(1)).getDataForResponse(test);
    }

    @Test
    void fetchDataTest_SourceDataClassCast() {
        String url = "http://www.mocky.io/v2/5c51b230340000094f129f5d";
        mainController.fetchData(url, SourceData.class);
        assertThat(mainController.fetchData(url, SourceData.class), instanceOf(SourceData.class));
    }

    @Test
    void fetchDataTest_TokenDataClassCast() {
        String url = "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s";
        mainController.fetchData(url, TokenData.class);
        assertThat(mainController.fetchData(url, TokenData.class), instanceOf(TokenData.class));
    }

}