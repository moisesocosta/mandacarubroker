package com.mandacarubroker.domain.stock;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockTest {
  /**
   * Method under test: {@link Stock#changePrice(double, boolean)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testChangePrice() {
    // Arrange, Act and Assert
    assertEquals(-10.0d, (new Stock()).changePrice(10.0d, true), 0.0);
    assertEquals(-0.5d, (new Stock()).changePrice(-0.5d, true), 0.0);
    assertEquals(10.0d, (new Stock()).changePrice(10.0d, false), 0.0);
  }

  /**
   * Method under test: {@link Stock#changePrice(double, boolean)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testChangePrice2() {
    // Arrange
    Stock stock = new Stock();
    stock.setCompanyName("Company Name");
    stock.setId("42");
    stock.setPrice(10.0d);
    stock.setSymbol("Symbol");

    // Act and Assert
    assertEquals(0.0d, stock.changePrice(10.0d, false), 0.0);
  }

  /**
   * Method under test: {@link Stock#increasePrice(double)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testIncreasePrice() {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new Stock()).increasePrice(10.0d), 0.0);
  }

  /**
   * Method under test: {@link Stock#decreasePrice(double)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testDecreasePrice() {
    // Arrange, Act and Assert
    assertEquals(-10.0d, (new Stock()).decreasePrice(10.0d), 0.0);
  }

  /**
   * Method under test: {@link Stock#Stock(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testNewStock() {
    // Arrange and Act
    Stock actualStock = new Stock(new RequestStockDto("Symbol", "Company Name", 10.0d));

    // Assert
    assertEquals("Company Name", actualStock.getCompanyName());
    assertEquals("Symbol", actualStock.getSymbol());
    assertEquals(-10.0d, actualStock.getPrice(), 0.0);
  }

  /**
   * Method under test: {@link Stock#Stock(RequestStockDto)}
   *
   * @author Moisés Oliveira
   */
  @Test
  public void testNewStock2() {
    // Arrange and Act
    Stock actualStock = new Stock(new RequestStockDto("Symbol", "Company Name", -0.5d));

    // Assert
    assertEquals("Company Name", actualStock.getCompanyName());
    assertEquals("Symbol", actualStock.getSymbol());
    assertEquals(-0.5d, actualStock.getPrice(), 0.0);
  }
}
