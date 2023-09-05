package by.teachmeskills.springbootproject.utils.actuator;

import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;

@Component
@Endpoint(id = "statistic")
public class EshopStatisticEndpoint {
    private final ProductService productService;

    @Autowired
    public EshopStatisticEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @ReadOperation
    ModelAndView getProductsStatistic() throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        productService.read();
        stopWatch.stop();
        modelMap.addAttribute("timeInMillis", stopWatch.getTotalTimeSeconds());
        return new ModelAndView(PagesPathEnum.STATISTIC_PAGE.getPath(), modelMap);
    }
}
