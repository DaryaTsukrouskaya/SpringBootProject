package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/search")
@SessionAttributes({"searchParams", "paginationParams"})
public class ProductSearchController {
    private final ProductService productService;

    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getSearchPage() {
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView searchByNameOrDescription(@RequestParam("keyWords") String keyWords, @ModelAttribute("searchParams") SearchParams searchParams, @ModelAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        searchParams.setKeyWords(keyWords);
        searchParams.setCategoryName(null);
        searchParams.setPriceTo(null);
        searchParams.setPriceFrom(null);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @GetMapping("/pagination/{pageNumber}")
    public ModelAndView searchPaginated(@PathVariable int pageNumber, @SessionAttribute("searchParams") SearchParams searchParams, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(pageNumber);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @PostMapping("/applyFilter")
    public ModelAndView applyFilter(@RequestParam(value = "keyWords", required = false) String keyWords, @RequestParam(value = "categoryName", required = false) String categoryName, @RequestParam(value = "priceFrom", required = false) BigDecimal priceFrom, @RequestParam(value = "priceTo", required = false) BigDecimal priceTo, @SessionAttribute("searchParams") SearchParams searchParams, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        if (Optional.ofNullable(categoryName).isPresent()) {
            searchParams.setCategoryName(categoryName);
        }
        searchParams.setPriceFrom(priceFrom);
        searchParams.setPriceTo(priceTo);
        searchParams.setKeyWords(keyWords);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @GetMapping("/setPageSize/{pageSize}")
    public ModelAndView changePageSize(@PathVariable int pageSize, @SessionAttribute("searchParams") SearchParams searchParams, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageSize(pageSize);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @ModelAttribute("searchParams")
    public SearchParams setSearchParams() {
        return new SearchParams();
    }

    @ModelAttribute("paginationParams")
    public PaginationParams setPaginationParams() {
        return new PaginationParams();
    }
}
