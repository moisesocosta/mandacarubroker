package com.mandacarubroker.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a stock entity in the database.
 *
 * @author Ricardo Vilela
 */
@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String symbol;
  private String companyName;
  private double price;

  /**
   * Creates a new Stock instance based on the provided RequestStockDTO data.
   *
   * @param requestStockDto the RequestStockDTO containing the stock information
   * @author Ricardo Vilela
   */
  public Stock(RequestStockDTO requestStockDto) {
    this.symbol = requestStockDto.symbol();
    this.companyName = requestStockDto.companyName();
    this.price = changePrice(requestStockDto.price(), true);
  }

  /**
   * Changes the price of the stock based on the provided amount and increase flag.
   *
   * @param amount   The amount to change the price by.
   * @param increase If true, the price will be increased. Otherwise, it will be decreased.
   * @return The new price of the stock.
   * @author Ricardo Vilela
   */
  public double changePrice(double amount, boolean increase) {
    if (increase) {
      if (amount < this.price) {
        return increasePrice(amount);
      } else {
        return decreasePrice(amount);
      }
    } else {
      if (amount > this.price) {
        return increasePrice(amount);
      } else {
        return this.decreasePrice(amount);
      }
    }
  }

  public double increasePrice(double amount) {
    return this.price + amount;
  }

  public double decreasePrice(double amount) {
    return this.price - amount;
  }

}