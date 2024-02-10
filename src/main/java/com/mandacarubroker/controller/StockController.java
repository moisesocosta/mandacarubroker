package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for managing stocks.
 */
@RestController
@RequestMapping("/stocks")
public class StockController {

  private final StockService stockService;

  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  /**
   * Gets all available stocks.
   *
   * @return a list of all stocks.
   * @author Ricardo Vilela.
   */
  @GetMapping
  public List<Stock> getAllStocks() {
    return stockService.getAllStocks();
  }

  /**
   * Gets a specific stock by its ID.
   *
   * @param id the ID of the stock to retrieve.
   * @return the stock with the specified ID, or null if not found.
   * @author Ricardo Vilela.
   */
  @GetMapping("/{id}")
  public Stock getStockById(@PathVariable String id) {
    return stockService.getStockById(id).orElse(null);
  }

  /**
   * Creates a new stock.
   *
   * @param data the details of the new stock to create.
   * @return the newly created stock with a 201 Created status code.
   * @author Ricardo Vilela.
   */
  @PostMapping
  public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data) {
    Stock createdStock = stockService.createStock(data);
    return ResponseEntity.ok(createdStock);
  }

  /**
   * Updates an existing stock.
   *
   * @param id           the ID of the stock to update.
   * @param updatedStock the updated data for the stock.
   * @return the updated stock, or null if not found.
   * @author Ricardo Vilela.
   */
  @PutMapping("/{id}")
  public Stock updateStock(@PathVariable String id, @RequestBody Stock updatedStock) {
    return stockService.updateStock(id, updatedStock).orElse(null);
  }

  /**
   * Deletes a stock by its ID.
   *
   * @param id the ID of the stock to delete.
   * @author Ricardo Vilela.
   */
  @DeleteMapping("/{id}")
  public void deleteStock(@PathVariable String id) {
    stockService.deleteStock(id);
  }

}
