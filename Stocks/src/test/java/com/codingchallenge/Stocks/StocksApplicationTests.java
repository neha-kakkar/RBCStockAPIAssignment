package com.codingchallenge.Stocks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StocksApplicationTests {

    @Autowired
    private MockMvc mockMvc;

	@MockBean
	private StockRepository stockRepository;

	@Test
	public void testSaveStockRecord() throws Exception {
		StockRecord sr = new StockRecord(500L, "2", "AA", "1/14/2011", "$16.71", "$16.71",
				"$15.64", "$15.97", "242963398", "-4.42849", "1.380223028", "239655616",
				"$16.19", "$15.79", "-2.47066", "19", "0.187852");

		String str = TestUtil.objectToJson(sr);
		mockMvc.perform(MockMvcRequestBuilders.post("/addStock").content(str)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
	}

	@Test
	public void testGetStockRecords() throws Exception {
		List<StockRecord> list = new ArrayList<>();
		StockRecord sr = new StockRecord(500L, "2", "AA", "1/14/2011", "$16.71", "$16.71",
				"$15.64", "$15.97", "242963398", "-4.42849", "1.380223028", "239655616",
				"$16.19", "$15.79", "-2.47066", "19", "0.187852");
		StockRecord sr1 = new StockRecord(501L, "1", "AA", "2/25/2011", "$16.98", "$17.15", "$15.96", "$16.68",
				"132981863", "-1.76678", "66.17769355", "80023895", "$16.81", "$16.58",
				"-1.36823","76", "0.179856");
		list.add(sr);
		list.add(sr1);

		Mockito.when(stockRepository.findByStockName("AA")).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/stockRecords/AA")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testUploadBulkRecordsSuccess() throws Exception {
		List<StockRecord> list = new ArrayList<>();
		StockRecord sr = new StockRecord(500L, "2", "AA", "1/14/2011", "$16.71", "$16.71",
				"$15.64", "$15.97", "242963398", "-4.42849", "1.380223028", "239655616",
				"$16.19", "$15.79", "-2.47066", "19", "0.187852");
		StockRecord sr1 = new StockRecord(501L, "1", "AA", "2/25/2011", "$16.98", "$17.15", "$15.96", "$16.68",
				"132981863", "-1.76678", "66.17769355", "80023895", "$16.81", "$16.58",
				"-1.36823","76", "0.179856");
		list.add(sr);
		list.add(sr1);

		Mockito.when(stockRepository.saveAll(anyList())).thenReturn(list);
		Path path = Paths.get("src/main/resources/static/testBulkUpload.csv");
		MockMultipartFile multipartFile = new MockMultipartFile("file",
				"testBulkUpload.csv", "text/plain", Files.readAllBytes(path));

		mockMvc.perform(MockMvcRequestBuilders.multipart("/stockRecords/bulkUpload").file(multipartFile))
				.andExpect(status().isOk());
	}

	@Test
	public void testUploadBulkRecordsFailure() throws Exception {
		List<StockRecord> list = new ArrayList<>();
		Mockito.when(stockRepository.saveAll(anyList())).thenReturn(list);
		Path path = Paths.get("src/main/resources/static/testBulkUpload.csv");
		MockMultipartFile multipartFile = new MockMultipartFile("file",
				"testBulkUpload.csv", "text/plain", Files.readAllBytes(path));

		mockMvc.perform(MockMvcRequestBuilders.multipart("/stockRecords/bulkUpload").file(multipartFile))
				.andExpect(status().isNotImplemented());
	}

}
