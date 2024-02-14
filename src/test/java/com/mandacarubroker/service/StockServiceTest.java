package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDto;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {StockService.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class StockServiceTest {
  @MockBean
  private StockRepository stockRepository;

  @Autowired
  private StockService stockService;

  /**
   * Method under test: {@link StockService#getAllStocks()}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testGetAllStocks() {
    // Arrange
    ArrayList<Stock> stockList = new ArrayList<>();
    when(stockRepository.findAll()).thenReturn(stockList);

    // Act
    List<Stock> actualAllStocks = stockService.getAllStocks();

    // Assert
    verify(stockRepository).findAll();
    assertTrue(actualAllStocks.isEmpty());
    assertSame(stockList, actualAllStocks);
  }

  /**
   * Method under test: {@link StockService#getAllStocks()}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testGetAllStocks2() {
    // Arrange
    when(stockRepository.findAll()).thenThrow(new ConstraintViolationException(new HashSet<>()));

    // Act and Assert
    assertThrows(ConstraintViolationException.class, () -> stockService.getAllStocks());
    verify(stockRepository).findAll();
  }

  /**
   * Method under test: {@link StockService#getStockById(String)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testGetStockById() {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    Optional<Stock> ofResult = Optional.of(stock);
    when(stockRepository.findById(Mockito.any())).thenReturn(ofResult);

    // Act
    Optional<Stock> actualStockById = stockService.getStockById("42");

    // Assert
    verify(stockRepository).findById(Mockito.any());
    assertTrue(actualStockById.isPresent());
    assertSame(ofResult, actualStockById);
  }

  /**
   * Method under test: {@link StockService#getStockById(String)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testGetStockById2() {
    // Arrange
    when(stockRepository.findById(Mockito.any())).thenThrow(new ConstraintViolationException(new HashSet<>()));

    // Act and Assert
    assertThrows(ConstraintViolationException.class, () -> stockService.getStockById("42"));
    verify(stockRepository).findById(Mockito.any());
  }

  /**
   * Method under test: {@link StockService#createStock(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testCreateStock() {
    // Arrange
    RequestStockDto request1 = new RequestStockDto("Symbol", "Company Name", 10.0d);
    RequestStockDto request2 = new RequestStockDto("$", "Company Name", 10.0d);

    // Act and Assert
    try {
      stockService.createStock(request1);
      fail("Expected ConstraintViolationException for request1");
    } catch (ConstraintViolationException e) {
      // Expected exception
    }

    try {
      stockService.createStock(request2);
      fail("Expected ConstraintViolationException for request2");
    } catch (ConstraintViolationException e) {
      // Expected exception
    }
  }

  /**
   * Method under test: {@link StockService#createStock(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testCreateStock2() {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    when(stockRepository.save(Mockito.any())).thenReturn(stock);

    // Act
    Stock actualCreateStockResult = stockService.createStock(new RequestStockDto("UU9", "Company Name", 10.0d));

    // Assert
    verify(stockRepository).save(Mockito.any());
    assertSame(stock, actualCreateStockResult);
  }

  /**
   * Method under test: {@link StockService#createStock(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testCreateStock3() {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    when(stockRepository.save(Mockito.any())).thenReturn(stock);

    // Act
    Stock actualCreateStockResult = stockService.createStock(new RequestStockDto("UU9", "Company Name", -0.5d));

    // Assert
    verify(stockRepository).save(Mockito.any());
    assertSame(stock, actualCreateStockResult);
  }

  /**
   * Method under test: {@link StockService#createStock(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testCreateStock4() {
    // Arrange
    when(stockRepository.save(Mockito.any())).thenThrow(new ConstraintViolationException(new HashSet<>()));

    // Act
    RequestStockDto request = new RequestStockDto("UU9", "Company Name", 10.0d);

    // Assert
    assertThrows(ConstraintViolationException.class, () -> stockService.createStock(request));
    verify(stockRepository).save(Mockito.any());
  }

  /**
   * Method under test: {@link StockService#updateStock(String, Stock)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testUpdateStock() {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    Optional<Stock> ofResult = Optional.of(stock);

    Stock stock2 = new Stock();
    stock2.setCompanyName("Company Name");
    stock2.setId("42");
    stock2.setPrice(10.0d);
    stock2.setSymbol("Symbol");
    when(stockRepository.save(Mockito.any())).thenReturn(stock2);
    when(stockRepository.findById(Mockito.any())).thenReturn(ofResult);

    Stock updatedStock = new Stock();
    updatedStock.setCompanyName("Company Name");
    updatedStock.setId("42");
    updatedStock.setPrice(10.0d);
    updatedStock.setSymbol("Symbol");

    // Act
    Optional<Stock> actualUpdateStockResult = stockService.updateStock("42", updatedStock);

    // Assert
    verify(stockRepository).findById(Mockito.any());
    verify(stockRepository).save(Mockito.any());
    assertTrue(actualUpdateStockResult.isPresent());
  }

  /**
   * Method under test: {@link StockService#updateStock(String, Stock)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testUpdateStock2() {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    Optional<Stock> ofResult = Optional.of(stock);
    when(stockRepository.save(Mockito.any())).thenThrow(new ConstraintViolationException(new HashSet<>()));
    when(stockRepository.findById(Mockito.any())).thenReturn(ofResult);

    Stock updatedStock = new Stock();
    updatedStock.setCompanyName("Company Name");
    updatedStock.setId("42");
    updatedStock.setPrice(10.0d);
    updatedStock.setSymbol("Symbol");

    // Act and Assert
    assertThrows(ConstraintViolationException.class, () -> stockService.updateStock("42", updatedStock));
    verify(stockRepository).findById(Mockito.any());
    verify(stockRepository).save(Mockito.any());
  }

  /**
   * Method under test: {@link StockService#updateStock(String, Stock)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testUpdateStock3() {
    // Arrange
    Stock stock = mock(Stock.class);
    when(stock.changePrice(anyDouble(), anyBoolean())).thenReturn(10.0d);
    doNothing().when(stock).setCompanyName(Mockito.any());
    doNothing().when(stock).setId(Mockito.any());
    doNothing().when(stock).setPrice(anyDouble());
    doNothing().when(stock).setSymbol(Mockito.any());
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    Optional<Stock> ofResult = Optional.of(stock);

    Stock stock2 = new Stock();
    stock2.setCompanyName("Company Name");
    stock2.setId("42");
    stock2.setPrice(10.0d);
    stock2.setSymbol("Symbol");
    when(stockRepository.save(Mockito.any())).thenReturn(stock2);
    when(stockRepository.findById(Mockito.any())).thenReturn(ofResult);

    Stock updatedStock = new Stock();
    updatedStock.setCompanyName("Company Name");
    updatedStock.setId("42");
    updatedStock.setPrice(10.0d);
    updatedStock.setSymbol("Symbol");

    // Act
    Optional<Stock> actualUpdateStockResult = stockService.updateStock("42", updatedStock);

    // Assert
    verify(stock).changePrice(anyDouble(), anyBoolean());
    verify(stock, atLeast(1)).setCompanyName(Mockito.any());
    verify(stock).setId(Mockito.any());
    verify(stock, atLeast(1)).setPrice(anyDouble());
    verify(stock, atLeast(1)).setSymbol(Mockito.any());
    verify(stockRepository).findById(Mockito.any());
    verify(stockRepository).save(Mockito.any());
    assertTrue(actualUpdateStockResult.isPresent());
  }

  /**
   * Method under test: {@link StockService#updateStock(String, Stock)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testUpdateStock4() {
    // Arrange
    Optional<Stock> emptyResult = Optional.empty();
    when(stockRepository.findById(Mockito.any())).thenReturn(emptyResult);

    Stock updatedStock = new Stock();
    updatedStock.setCompanyName("Company Name");
    updatedStock.setId("42");
    updatedStock.setPrice(10.0d);
    updatedStock.setSymbol("Symbol");

    // Act
    Optional<Stock> actualUpdateStockResult = stockService.updateStock("42", updatedStock);

    // Assert
    verify(stockRepository).findById(Mockito.any());
    assertFalse(actualUpdateStockResult.isPresent());
    assertSame(emptyResult, actualUpdateStockResult);
  }

  /**
   * Method under test: {@link StockService#updateStock(String, Stock)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testUpdateStock5() {
    // Arrange
    Stock stock = mock(Stock.class);
    when(stock.changePrice(anyDouble(), anyBoolean())).thenReturn(10.0d);
    doNothing().when(stock).setCompanyName(Mockito.any());
    doNothing().when(stock).setId(Mockito.any());
    doNothing().when(stock).setPrice(anyDouble());
    doNothing().when(stock).setSymbol(Mockito.any());
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");
    Optional<Stock> ofResult = Optional.of(stock);

    Stock stock2 = new Stock();
    stock2.setCompanyName("Company Name");
    stock2.setId("42");
    stock2.setPrice(10.0d);
    stock2.setSymbol("Symbol");
    when(stockRepository.save(Mockito.any())).thenReturn(stock2);
    when(stockRepository.findById(Mockito.any())).thenReturn(ofResult);
    Stock updatedStock = mock(Stock.class);
    when(updatedStock.getPrice()).thenReturn(10.0d);
    when(updatedStock.getCompanyName()).thenReturn("Company Name");
    when(updatedStock.getSymbol()).thenReturn("Symbol");
    doNothing().when(updatedStock).setCompanyName(Mockito.any());
    doNothing().when(updatedStock).setId(Mockito.any());
    doNothing().when(updatedStock).setPrice(anyDouble());
    doNothing().when(updatedStock).setSymbol(Mockito.any());
    updatedStock.setCompanyName("Company Name");
    updatedStock.setId("42");
    updatedStock.setPrice(10.0d);
    updatedStock.setSymbol("Symbol");

    // Act
    Optional<Stock> actualUpdateStockResult = stockService.updateStock("42", updatedStock);

    // Assert
    verify(stock).changePrice(anyDouble(), anyBoolean());
    verify(updatedStock).getCompanyName();
    verify(updatedStock).getPrice();
    verify(updatedStock).getSymbol();
    verify(updatedStock).setCompanyName(Mockito.any());
    verify(stock, atLeast(1)).setCompanyName(Mockito.any());
    verify(stock).setId(Mockito.any());
    verify(updatedStock).setId(Mockito.any());
    verify(updatedStock).setPrice(anyDouble());
    verify(stock, atLeast(1)).setPrice(anyDouble());
    verify(updatedStock).setSymbol(Mockito.any());
    verify(stock, atLeast(1)).setSymbol(Mockito.any());
    verify(stockRepository).findById(Mockito.any());
    verify(stockRepository).save(Mockito.any());
    assertTrue(actualUpdateStockResult.isPresent());
  }

  /**
   * Method under test: {@link StockService#deleteStock(String)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testDeleteStock() {
    // Arrange
    doNothing().when(stockRepository).deleteById(Mockito.any());

    // Act
    stockService.deleteStock("42");

    // Assert that nothing has changed
    verify(stockRepository).deleteById(Mockito.any());
  }

  /**
   * Method under test: {@link StockService#deleteStock(String)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testDeleteStock2() {
    // Arrange
    doThrow(new ConstraintViolationException(new HashSet<>())).when(stockRepository).deleteById(Mockito.any());

    // Act and Assert
    assertThrows(ConstraintViolationException.class, () -> stockService.deleteStock("42"));
    verify(stockRepository).deleteById(Mockito.any());
  }

  /**
   * Method under test:
   * {@link StockService#validateRequestStockDto(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testValidateRequestStockDto() {
    // First test case
    try {
      StockService.validateRequestStockDto(new RequestStockDto("Symbol", "Company Name", 10.0d));
    } catch (ConstraintViolationException e) {
      // Expected exception
    }

    // Second test case
    try {
      StockService.validateRequestStockDto(new RequestStockDto("$", "Company Name", 10.0d));
    } catch (ConstraintViolationException e) {
      // Expected exception
    }
  }
}
