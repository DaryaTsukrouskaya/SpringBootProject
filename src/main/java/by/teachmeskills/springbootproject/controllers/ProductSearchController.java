package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/search")
@SessionAttributes({"searchParams"})
public class ProductSearchController {
    private final ProductService productService;

    @Autowired
    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getSearchPage() {
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView searchByNameOrDescription(@ModelAttribute("searchParams") SearchParams searchParams, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @GetMapping("/pagination/{pageNumber}")
    public ModelAndView searchByNameOrDescriptionPaginated(@PathVariable int pageNumber, @SessionAttribute("searchParams") SearchParams searchParams, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(pageNumber);
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
}
