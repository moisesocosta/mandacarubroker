package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDto;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {StockController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class StockControllerTest {
  @Autowired
  private StockController stockController;

  @MockBean
  private StockService stockService;

  /**
   * Method under test: {@link StockController#getAllStocks()}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testGetAllStocks() throws Exception {
    // Arrange
    when(stockService.getAllStocks()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stocks");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(stockController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link StockController#getAllStocks()}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testGetAllStocks2() throws Exception {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");

    ArrayList<Stock> stockList = new ArrayList<>();
    stockList.add(stock);
    when(stockService.getAllStocks()).thenReturn(stockList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stocks");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(stockController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string("[{\"id\":\"42\",\"symbol\":\"Symbol\",\"companyName\":\"Company Name\",\"price\":10.0}]"));
  }

  /**
   * Method under test: {@link StockController#getStockById(String)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testGetStockById() throws Exception {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    Optional<Stock> ofResult = Optional.of(stock);
    when(stockService.getStockById(Mockito.<String>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stocks/{id}", "42");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(stockController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string("{\"id\":\"42\",\"symbol\":\"Symbol\",\"companyName\":\"Company Name\",\"price\":10.0}"));
  }

  /**
   * Method under test: {@link StockController#createStock(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testCreateStock() throws Exception {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    when(stockService.createStock(Mockito.<RequestStockDto>any())).thenReturn(stock);
    MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/stocks")
            .contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
            .content(objectMapper.writeValueAsString(new RequestStockDto("Symbol", "Company Name", 10.0d)));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(stockController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string("{\"id\":\"42\",\"symbol\":\"Symbol\",\"companyName\":\"Company Name\",\"price\":10.0}"));
  }

  /**
   * Method under test: {@link StockController#updateStock(String, Stock)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testUpdateStock() throws Exception {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    Optional<Stock> ofResult = Optional.of(stock);
    when(stockService.updateStock(Mockito.<String>any(), Mockito.<Stock>any())).thenReturn(ofResult);

    Stock stock2 = new Stock();
    stock2.setCompanyName("Company Name");
    stock2.setId("42");
    stock2.setPrice(10.0d);
    stock2.setSymbol("Symbol");
    String content = (new ObjectMapper()).writeValueAsString(stock2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/stocks/{id}", "42")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(stockController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string("{\"id\":\"42\",\"symbol\":\"Symbol\",\"companyName\":\"Company Name\",\"price\":10.0}"));
  }

  /**
   * Method under test: {@link StockController#deleteStock(String)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testDeleteStock() throws Exception {
    // Arrange
    doNothing().when(stockService).deleteStock(Mockito.<String>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/stocks/{id}", "42");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(stockController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link StockController#deleteStock(String)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testDeleteStock2() throws Exception {
    // Arrange
    doNothing().when(stockService).deleteStock(Mockito.<String>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/stocks/{id}", "42");
    requestBuilder.contentType("https://example.org/example");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(stockController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
