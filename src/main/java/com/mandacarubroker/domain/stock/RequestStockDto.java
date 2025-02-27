package com.mandacarubroker.domain.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * A data transfer object (DTO) representing a stock request.
 * This DTO is used to encapsulate the information required to create or update a stock.
 *
 * @author Ricardo Vilela
 */
public record RequestStockDto(
        @Pattern(regexp = "[A-Za-z]{2}\\d",
                message = "Symbol must be 3 letters followed by 1 number")
        String symbol,
        @NotBlank(message = "Company name cannot be blank")
        String companyName,
        @NotNull(message = "Price cannot be null")
        double price
) {
}
