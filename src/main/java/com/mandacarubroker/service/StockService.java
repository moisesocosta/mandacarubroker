package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * Service for managing stock-related operations.
 *
 * @author Ricardo Vilela.
 */
@Service
public class StockService {

  private final StockRepository stockRepository;

  public StockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public List<Stock> getAllStocks() {
    return stockRepository.findAll();
  }

  public Optional<Stock> getStockById(String id) {
    return stockRepository.findById(id);
  }

  /**
   * Creates a new stock based on the provided request data.
   * This method validates the request data using JSR-380 annotations
   * and throws a `ConstraintViolationException` if there are any errors.
   * If the data is valid, a new `Stock` object is created and saved to the database.
   *
   * @param data The request data containing stock information.
   * @return The saved `Stock` object.
   * @throws ConstraintViolationException If the request data is invalid.
   * @author Ricardo Vilela.
   */
  public Stock createStock(RequestStockDTO data) {
    Stock novaAcao = new Stock(data);
    validateRequestStockDto(data);
    return stockRepository.save(novaAcao);
  }

  /**
   * Updates an existing stock with the provided information.
   *
   * @param id           The ID of the stock to update.
   * @param updatedStock The updated stock information.
   * @return An Optional containing the updated stock, or empty if the stock was not found.
   * @author Ricardo Vilela.
   */
  public Optional<Stock> updateStock(String id, Stock updatedStock) {
    return stockRepository.findById(id)
            .map(stock -> {
              stock.setSymbol(updatedStock.getSymbol());
              stock.setCompanyName(updatedStock.getCompanyName());
              double newPrice = stock.changePrice(updatedStock.getPrice(), true);
              stock.setPrice(newPrice);

              return stockRepository.save(stock);
            });
  }

  /**
   * Deletes an existing action record in the database.
   *
   * @param id The ID of the action to be deleted.
   * @throws IllegalArgumentException If the supplied ID is null or empty.
   * @author Ricardo Vilela.
   */
  public void deleteStock(String id) {
    stockRepository.deleteById(id);
  }

  /**
   * Validates the given RequestStockDTO, ensuring that it meets all validation constraints.
   *
   * @param data The RequestStockDTO to validate.
   * @throws ConstraintViolationException If validation fails.
   * @author Ricardo Vilela.
   */
  public static void validateRequestStockDto(RequestStockDTO data) {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = factory.getValidator();
      Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

      if (!violations.isEmpty()) {
        StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

        for (ConstraintViolation<RequestStockDTO> violation : violations) {
          errorMessage.append(String.format("[%s: %s], ",
                  violation.getPropertyPath(), violation.getMessage()));
        }

        errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

        throw new ConstraintViolationException(errorMessage.toString(), violations);
      }
    }
  }

}
